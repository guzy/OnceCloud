reloadList(1);

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
		$("#").removeClass('btn-forbidden');
	} else {
		$("#delete").addClass('btn-forbidden');
	}
	if (1 == count) {
		$('#update').removeClass('btn-forbidden');
		$('#delete').removeClass('btn-forbidden');
		$("#repool").removeClass('btn-forbidden');
	} else {
		$('#update').addClass('btn-forbidden');
	}
});

$('#create').on('click', function(event) {
	event.preventDefault();
	$('#ClusterModalContainer').attr('type', 'new');
	$('#ClusterModalContainer').load($(this).attr('url'), '', function() {
		$('#ClusterModalContainer').modal({
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
    	url: "ClusterAction/RePool",
    	data: {poolUuid:poolUuid},
    	dataType: "json",
    	success: function(obj) {
    		
    	}
    });
});
		

$('#update').on('click', function(event) {
	event.preventDefault();
	$('#ClusterModalContainer').attr('type', 'edit');
	$('#ClusterModalContainer').load($(this).attr('url'), '', function() {
		$('#ClusterModalContainer').modal({
			backdrop : false,
			show : true
		});
	});
});

function getPoolList(page, limit, search) {
	$.ajax({
		type : 'get',
		url : 'ClusterAction/ClusterList',
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
		message : '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除集群&nbsp;'
				+ infoList + '?</div>',
		title : "提示",
		buttons : {
			main : {
				label : "确定",
				className : "btn-primary",
				callback : function() {
					for (var i = 0; i < boxes.length; i++) {
						if (boxes[i].checked == true) {
							deleteCluster($(boxes[i]).parent().parent()
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

function deleteCluster(poolid, poolname) {
	$.ajax({
		type : 'post',
		url : 'ClusterAction/Delete',
		data : {
			poolid : poolid,
			poolname : poolname
		},
		dataType : 'json',
		success : function(array) {
			if (array.length == 1) {
				var result = array[0].result;
				if (result) {
					$($("#tablebody").find('[poolid="' +poolid+ '"]')).remove();
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

