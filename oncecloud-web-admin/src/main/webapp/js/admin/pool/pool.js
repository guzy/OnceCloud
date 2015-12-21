reloadList(1);
initPoolPage();

function reloadList(page) {
	var limit = $('#limit').val();
	var search = $('#search').val();
	getPoolList(page, limit, search);
	if (page == 1) {
		options = {
			currentPage : 1
		};
		$('#pageDivider').bootstrapPaginator(options);
	}
}

function removeAllCheck() {
	var boxes = document.getElementsByName("poolrow");
	for (var i = 0; i < boxes.length; i++) {
		boxes[i].checked = false;
		$(boxes[i]).change();
	}
}

$('#tablebody').on('change', 'input:checkbox', function(event) {
	event.preventDefault();
	$("#repool").addClass('btn-forbidden');
	var boxes = document.getElementsByName("poolrow");
	var count = 0;
	for (var i = 0; i < boxes.length; i++) {
		if (boxes[i].checked == true) {
			count++;
		}
	}
	if (count > 0) {
		$("#delete").removeClass('btn-forbidden');
		$("#consistency").removeClass('btn-forbidden');
		$('#accordance').prop('disabled', true);
	} else {
		$("#delete").addClass('btn-forbidden');
		$("#consistency").addClass('btn-forbidden');
		$('#accordance').prop('disabled', true);
	}
	if (1 == count) {
		$("#repool").removeClass('btn-forbidden');
		$('#update').removeClass('btn-forbidden');
		$('#accordance').prop('disabled', false);
	} else {
		$('#update').addClass('btn-forbidden');
		$('#accordance').prop('disabled', true);
	}
});

$('#create').on('click', function(event) {
	
	event.preventDefault();
	$('#PoolModalContainer').attr('type', 'new');
	$('#PoolModalContainer').load($(this).attr('url'), '', function() {
		
		$('#PoolModalContainer').modal({
			backdrop : false,
			show : true
		});
	});
});

$("#repool").on('click', function(event) {
	event.preventDefault();
	var poolUuid = "";
	$('input[name="poolrow"]:checked').each(function () {
        poolUuid = $(this).parent().parent().attr("poolid");
    });
    $.ajax({
    	type: "post",
    	url: "PoolAction/RePool",
    	data: {poolUuid:poolUuid},
    	dataType: "json",
    	success: function(obj) {
    		
    	}
    });
});
		
$('#accordance').on('click', function(event) {
	if ($("#accordance").attr("isac") === "true") {
		keepAccordance(0);
		$("#accordance").text("开启一致性");
		$("#accordance").attr("isac", "false");
		$("#accordance").attr("class", "btn btn-success");
	} else if ($("#accordance").attr("isac") === "false") {
		event.preventDefault();
		$('#PoolModalContainer').load($(this).attr('url'), '',
			function() {
				$('#PoolModalContainer').modal({
					backdrop : false,
					show : true
				});
			});
	}
});

function keepAccordance(flag) {
	var time_ = $("#ac_time").val();
	if (time_ === undefined) {
		time_ = 0;
	}
	$.ajax({
		type : 'get',
		url : 'PoolAction/ManageAc',
		data : {
			time : time_,
			flag : flag
		},
		dataType : 'json',
		complete : function(data) {
			console.log(data);
		}
	});
}

$('#update').on('click', function(event) {
	event.preventDefault();
	$('#PoolModalContainer').attr('type', 'edit');
	$('#PoolModalContainer').load($(this).attr('url'), '', function() {
		$('#PoolModalContainer').modal({
			backdrop : false,
			show : true
		});
	});
});

function initPoolPage() {
	if ($("#accordance").attr("isac") === "true") {
		$("#accordance").text("关闭一致性");
		$("#accordance").attr("class", "btn btn-danger");
	} else if ($("#accordance").attr("isac") === "false") {
		$("#accordance").text("开启一致性");
		$("#accordance").attr("class", "btn btn-success");
	}
}

function getPoolList(page, limit, search) {
	$.ajax({
		type : 'get',
		url : 'PoolAction/PoolList',
		data : {
			page : page,
			limit : limit,
			search : search
		},
		dataType : 'json',
		success : function(array) {
			if (array.length >= 1) {
				var totalnum = array[0];
				var totalp = Math.ceil(totalnum / limit);
				if (totalp == 0) {
					totalp == 1;
				}
				options = {
					totalPages : totalp
				};
				$('#pageDivider').bootstrapPaginator(options);
				pageDisplayUpdate(page, totalp);
				var btable = document.getElementById("tablebody");
				var tableStr = "";
				for (var i = 1; i < array.length; i++) {
					var obj = array[i];
					var poolname = decodeURIComponent(obj.poolname);
					var pooldesc = decodeURIComponent(obj.pooldesc);
					var poolid = obj.poolid;
					var mastername = decodeURIComponent(obj.mastername);
					var createdate = obj.createdate;
					var dcuuid = obj.dcuuid;
					var dcname = obj.dcname;
					if (dcuuid == "") {
						statestr = '<td class="pod" state="unload"></td>';
					} else {
						statestr = '<td class="pod" state="loaded">'
								+ decodeURIComponent(dcname) + '</td>';
					}
					var totalcpu = obj.totalcpu;
					var totalmem = Math.round(obj.totalmem / 1024);
					var cpustr = totalcpu + "";
					if (totalcpu != 0) {
						cpustr = cpustr + "&nbsp;核";
					}
					var memstr = totalmem + "";
					if (totalmem != 0) {
						memstr = memstr + "&nbsp;GB";
					}
					var showid = "pool-" + poolid.substring(0, 8);
					var mytr = '<tr poolid="' + poolid + '" poolname="' + poolname + '" pooldesc="' + pooldesc + '" dcid="' + dcuuid + '">'
							+ '<td class="rcheck"><input type="checkbox" name="poolrow"></td>'
							+ '<td><a class="id">' + showid + '</a></td>'
							+ '<td>'+ poolname + '</td>'
							+ '<td>' + mastername + '</td>' + statestr 
							+ '<td>' + cpustr + '</td>'
							+ '<td>' + memstr + '</td>'
							+ '<td class="time">'+ createdate + '</td>'
							+ '</tr>';
					tableStr = tableStr + mytr;
				}
				btable.innerHTML = tableStr;
			}
		}
	});
}

$('#delete').on('click', function(event) {
	event.preventDefault();
	var boxes = document.getElementsByName("poolrow");
	var infoList = "";
	for (var i = 0; i < boxes.length; i++) {
		if (boxes[i].checked == true) {
			infoList += "[" + $(boxes[i]).parent().parent().attr("poolname")
					+ "]&nbsp;";
		}
	}
	bootbox.dialog({
		message : '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除资源池&nbsp;'
				+ infoList + '?</div>',
		title : "提示",
		buttons : {
			main : {
				label : "确定",
				className : "btn-primary",
				callback : function() {
					for (var i = 0; i < boxes.length; i++) {
						if (boxes[i].checked == true) {
							deletePool($(boxes[i]).parent().parent()
											.attr("poolid"), $(boxes[i])
											.parent().parent().attr("poolname"));
						}
					}
					removeAllCheck();
				}
			},
			cancel : {
				label : "取消",
				className : "btn-default",
				callback : function() {
					removeAllCheck();
				}
			}
		}
	});
});

function deletePool(poolid, poolname) {
	$.ajax({
		type : 'post',
		url : 'PoolAction/Delete',
		data : {
			poolid : poolid,
			poolname : poolname
		},
		dataType : 'json',
		success : function(array) {
			if (array.length == 1) {
				var result = array[0].result;
				if (result) {
					var thistr = $("#tablebody").find('[poolid="'
							+ poolid + '"]');
					$(thistr).remove();
				}
			}
		}
	});
}

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='poolrow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='poolrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});

$('#consistency').on('click', function(event) {
	event.preventDefault();
	var boxes = document.getElementsByName("poolrow");
	var infoList = "";
	for (var i = 0; i < boxes.length; i++) {
		if (boxes[i].checked == true) {
			infoList += "[" + $(boxes[i]).parent().parent().attr("poolname")
					+ "]&nbsp;";
		}
	}
	bootbox.dialog({
		message : '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;校验资源池的一致性&nbsp;'
				+ infoList + '?</div>',
		title : "提示",
		buttons : {
			main : {
				label : "确定",
				className : "btn-primary",
				callback : function() {
					for (var i = 0; i < boxes.length; i++) {
						if (boxes[i].checked == true) {
							consistencyPool($(boxes[i]).parent().parent()
									.attr("poolid"));
						}
					}
					removeAllCheck();
				}
			},
			cancel : {
				label : "取消",
				className : "btn-default",
				callback : function() {
				}
			}
		}
	});
});

function consistencyPool(poolUuid) {
	$.ajax({
		type : 'post',
		url : 'PoolAction/KeepAccordance',
		data : {
			poolUuid : poolUuid
		},
		dataType : 'json',
		success : function(array) {
		}
	});
}
