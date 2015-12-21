reloadList(1);

function reloadList(page) {
    var limit = $("#limit").val();
    var search = $('#search').val();
    var type = $('.once-tab').find('.active').attr("type");
    getImageList(page, limit, search, type);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}

function removeAllCheck() {
    $('input[name="imagerow"]').each(function () {
        $(this).attr("checked", false);
        $(this).change();
    });
}

function allDisable() {
    $("#delete").addClass('btn-forbidden');
    $("#share-image").addClass('btn-forbidden');
    $("#change-to-vm").addClass('btn-forbidden');
    $("#update-image").addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    allDisable();
    var count = 0;
    var flag = false;
    var noref = true;
    var pooluuid;
    $('input[name="imagerow"]:checked').each(function (index) {
        count++;
        if ($(this).attr("imageRef") != 0) {
        	noref = false;
        }
        if (index == 0) {
	        pooluuid = $(this).attr("pooluuid");
	        flag = true;
        } else {
        	if ($(this).attr("pooluuid") != pooluuid) {
        		flag = false;
        	}
        }
    });
    if (noref && flag) {
    	$("#share-image").removeClass('btn-forbidden');
	    if (count == 1) {
	    	$("#change-to-vm").removeClass('btn-forbidden');
	    	$("#update-image").removeClass('btn-forbidden');
	    }
    }
    if (count > 0) {
        $("#delete").removeClass('btn-forbidden');
    }
});

$('.once-tab').on('click', '.tab-filter', function (event) {
    $('li', '.once-tab').removeClass('active');
    $(this).addClass('active');
    reloadList(1);
});

$('#create').on('click', function (event) {
    event.preventDefault();
    $('#ImageModalContainer').load($(this).attr('url'), '', function () {
        $('#ImageModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#share-image').on('click', function (event) {
    event.preventDefault();
    $('#ImageModalContainer').load('admin/modal/imageShare', '', function () {
        $('#ImageModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#update-image').on('click', function (event) {
    event.preventDefault();
    $('#ImageModalContainer').load('admin/modal/imageUpdate', '', function () {
        $('#ImageModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

function getImageList(page, limit, search, type) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'ImageAction/ImageList',
        data: { page: page, limit: limit, search: search, type: type},
        dataType: 'json',
        success: function (array) {
            if (array.length >= 1) {
                var totalnum = array[0];
                var totalp = 1;
                if (totalnum != 0) {
                    totalp = Math.ceil(totalnum / limit);
                }
                options = {
                    totalPages: totalp
                };
                $('#pageDivider').bootstrapPaginator(options);
                pageDisplayUpdate(page, totalp);
                var tableStr = "";
                for (var i = 1; i < array.length; i++) {
                    var obj = array[i];
                    var imageName = decodeURIComponent(obj.imagename);
                    var imageUId = obj.imageid;
                    var imageSize = obj.imagesize;
                    var imageplatform = decodeURIComponent(obj.imageplatform);
                    var createDate = obj.createDate;
                    var showid = "img-" + imageUId.substring(0, 8);
                    var type = $('.once-tab').find('.active').attr("type");
                    $('#image-area').text("可见范围");
                    var typeStr = "公有";
                    var userId = $('input[name="hidden-area"]').val();
                    if (type == "user") {
                        if (userId == 1) {
                            typeStr = obj.imageuser;
                            $('#image-area').text("所属用户");
                        } else {
                            typeStr = "私有";
                        }
                    }
                    var poolName = obj.poolname; 
                    var stateStr = '<td><span class="icon-status icon-running" name="stateicon">'
                        + '</span><span name="stateword">可用</span></td>';
                    var ref = obj.reference;
                    if (ref == null) {
                    	ref = 0;
                    }
                    var mytr = '<tr imageUId="' + imageUId + '" imageName="' + imageName + '" imageType="' + type + '">'
                        + '<td class="rcheck"><input type="checkbox" name="imagerow" imageRef="'+ ref +'" pooluuid="'+obj.pooluuid+'"></td>'
                        + '<td><a class="id">' + showid + '</a></td>'
                        + '<td>' + imageName + '</td>'
                        + '<td>' + imageSize + '</td>'
                        + '<td>' + imageplatform + '</td>' + stateStr
                        + '<td>' + typeStr + '</td>'
                        + '<td class="time">' + createDate + '</td>';
                    var level = $('input[name="hidden-area"]').attr("level");
                    if (level == 0) {
                    	mytr += '<td>'+poolName+'</td>';
                    }
                    mytr += '</tr>';
                    tableStr = tableStr + mytr;
                }
                $('#tablebody').html(tableStr);
            }
        }
    });
}

$('#tablebody').on('click', '.id', function (event) {
    event.preventDefault();
    var imageuuid = $(this).parent().parent().attr('imageUId');
    var imagetype = $(this).parent().parent().attr('imageType');
    var form = $("<form></form>");
    form.attr("action", "image/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="imageUuid" value="' + imageuuid + '" />');
    var input1 = $('<input type="text" name="imageType" value="' + imagetype + '" />');
    form.append(input);
    form.append(input1);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#delete').on('click', function (event) {
    event.preventDefault();
    var infoList = "";
    $('input[name="imagerow"]:checked').each(function () {
        infoList += "[" + $(this).parent().parent().attr("imageName") + "]&nbsp;";
    });
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除映像&nbsp;' + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="imagerow"]:checked').each(function () {
                        deleteImage($(this).parent().parent().attr("imageUId"), $(this).parent().parent().attr("imageName"));
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

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='imagerow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='imagerow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});

function deleteImage(imageId, imageName) {
    $.ajax({
        type: 'post',
        url: 'ImageAction/Delete',
        data: {imageId: imageId, imageName: imageName},
        dataType: 'json',
        success: function (obj) {
            if (obj.result) {
                var thistr = $("#tablebody").find('[imageUId="' + imageId + '"]');
                $(thistr).remove();
            }
        }
    });
}

$("#change-to-vm").on('click', function(event){
	event.preventDefault();
	var uuid;
	$('input[name="imagerow"]:checked').each(function () {
		uuid = $(this).parents('tr').attr("imageUId");
	});
	$.ajax({
		type: "post",
		url: "VMAction/TemplateToVM",
		data: {uuid: uuid},
		dataType: "json",
		success: function (obj) {
			if(obj) {
				$('input[name="imagerow"]:checked').each(function () {
					$(this).parents('tr').remove();
				});
			}
		}
	});
});

