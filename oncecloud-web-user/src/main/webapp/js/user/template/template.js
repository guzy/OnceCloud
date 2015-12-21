var group = "all";
reloadList(1);

function reloadList(page) {
	var limit = $('#limit').val();
	var search = $('#search').val();
	getTemplateList(page, limit, search);
	if (page == 1) {
		options = {
			currentPage : 1
		};
		$('#pageDivider').bootstrapPaginator(options);
	}
	allDisable();
}

function removeAllCheck() {
	$('input[name="imgrow"]:checked').each(function() {
		$(this)[0].checked = false;
		$(this).change();
	});
}

function allDisable() {
	//$("#startup").attr("disabled", true).addClass('btn-disable');
	//$("#restart").addClass('btn-forbidden');
}

$('#upfileModel').on('click', function (event) {
    event.preventDefault();
    $('#TemplateModalContainer').attr("type", "new");
    $('#TemplateModalContainer').load($(this).attr('url'), '', function () {
        $('#TemplateModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#tablebody').on('change', 'input:checkbox', function(event) {
	event.preventDefault();
	allDisable();
	var running = 0;
	var process = 0;
	var stopped = 0;
	var total = 0;
	$('input[name="imgrow"]:checked').each(function() {
		var stateicon = $(this).parent().parent()
				.find('[name="stateicon"]');
		if (stateicon.hasClass('icon-running')) {
			running++;
		} else if (stateicon.hasClass('icon-stopped')) {
			stopped++;
		} else {
			process++;
		}
		total++;
	});
	$('#pushTemplate').removeClass('btn-forbidden');
	$('#deleteTemplate').removeClass('btn-forbidden');
});

//
$('#pushTemplate').on('click', function (event) {
    event.preventDefault();
    var infoList = "";
    $('input[name="imgrow"]:checked').each(function () {
        infoList += "[img-" + $(this).parent().parent().attr("rowid").substr(0, 7) + "]&nbsp;";
    });
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;发布模板&nbsp;' + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="imgrow"]:checked').each(function () {
                    	var $tds=$(this).parent().parent().find("td");
                    	var imageId =$tds.eq(1).text();
                    	var imageName=$tds.eq(2).text();
                    	var imageVersion =$tds.eq(3).text();

                    	pushTemplate(imageId,imageName,imageVersion);
                    });
                    removeAllCheck();
                }
            },
            cancel: {
                label: "取消",
                className: "btn-default",
                callback: function () {
                    removeAllCheck();
                }
            }
        }
    });
});

function pushTemplate(imageId,imageName,imageVersion) {
	repoId=uuid.v4();
    $.ajax({
        type: 'post',
        url: 'TemplateAction/PushTemplate',
        data: {
        	repoId:repoId,
        	imageId: imageId,
        	imageName:imageName,
        	imageVersion:imageVersion
              },
        dataType: 'json',
        success: function (result) {
            if (result == true) {
            	$('#TemplateModalContainer').modal('hide');
                reloadList(1);
            }
        }
    });
}

//
$('#deleteTemplate').on('click', function (event) {
    event.preventDefault();
    var infoList = "";
    $('input[name="imgrow"]:checked').each(function () {
        infoList += "[img-" + $(this).parent().parent().attr("rowid").substr(0, 7) + "]&nbsp;";
    });
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除模板&nbsp;' + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="imgrow"]:checked').each(function () {
                        deleteTemplate($(this).parent().parent().attr("rowid"));
                        
                    });
                    removeAllCheck();
                }
            },
            cancel: {
                label: "取消",
                className: "btn-default",
                callback: function () {
                    removeAllCheck();
                }
            }
        }
    });
});

//
function deleteTemplate(imageId) {
    $.ajax({
        type: 'get',
        url: 'TemplateAction/Delete',
        data: {imageId: imageId},
        dataType: 'json',
        success: function (result) {
            if (result == true) {
            	$('#TemplateModalContainer').modal('hide');
                reloadList(1);
            }
        }
    });
}

//
function getTemplateList(page, limit, search) {
	$('#tablebody').html("");
	$.ajax({
		type : 'get',
		url : '/TemplateAction/TemplateList',
		data : {
			page : page,
			limit : limit,
			search : search,
			groupUuid : group
		},
		dataType : 'json',
		success : function(array) {
			var totalnum = array[0];
			var totalp = 1;
			if (totalnum != 0) {
				totalp = Math.ceil(totalnum / limit);
			}
			options = {
				totalPages : totalp
			};
			$('#pageDivider').bootstrapPaginator(options);
			pageDisplayUpdate(page, totalp);
			var tableStr = "";
			for (var i = 0; i < array.length; i++) {
				var obj = array[i];
				var imageid = 'i-'+obj.imageid.substr(0,8);
				var imagename =  obj.imagename;
				var imageversion = obj.imageversion;
				var imagesize = (obj.imagesize/1000/1000).toFixed(2)+"M";
				var thistr = '<tr rowid="'
						+ obj.imageid
						+ '"><td class="rcheck"><input type="checkbox" name="imgrow"></td><td name="console">'
						+ imageid + '</td><td name="imagename">' + imagename
						+ '</td>' + '<td name="imageversion">'+ imageversion
						+ '</td>' + '<td name="imagesize">'+ imagesize +'</td></tr>';
				tableStr += thistr;
			}
			$('#tablebody').html(tableStr);
		}
	});
}

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='imgrow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='imgrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});