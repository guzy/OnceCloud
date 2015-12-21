reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    var search = $('#search').val();
    getAreaList(page, limit, search);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}

function removeAllCheck() {
    $('input[name="userrow"]').each(function () {
        $(this).attr("checked", false);
        $(this).change();
    });
}

function allDisable() {
	$("#updateArea").addClass('btn-forbidden');
    $("#delete").addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    allDisable();
    var count = 0;
    $('input[name="userrow"]:checked').each(function () {
        count++;
    });
    if (count = 1){
    	$("#updateArea").removeClass('btn-forbidden');
    }
    if (count > 0) {
        $("#delete").removeClass('btn-forbidden');
    }
});

//xiugai
$('#create').on('click', function (event) {
    event.preventDefault();
    $('#AreaModalContainer').attr("type", "new");
    $('#AreaModalContainer').load($(this).attr('url'), '', function () {
        $('#AreaModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

//xiugai
$('#updateArea').on('click', function (event) {
    event.preventDefault();
    var areaId = "";
    $('input[name="userrow"]:checked').each(function () {
    	//areaId =  $(this).parent().next().text();
    	areaId = $(this).parent().parent().attr("rowid")
    	areaName =  $(this).parent().next().next().text();
    	areaDomain =  $(this).parent().next().next().next().text();
    	areaDesc =  $(this).parent().next().next().next().next().text();
    });
   
   // alert(areaId+"----"+areaName);
   // $('#AreaModalContainer').attr("type", "edit");
    $('#AreaModalContainer').load('admin/modal/modifyArea', '', function () {
    	$('#area_id').val(areaId);
        $('#area_name').val(areaName);
        $('#area_domain').val(areaDomain);
        $('#area_desc').val(areaDesc);
        $('#AreaModalContainer').modal({
            backdrop: false,
            show: true
        });
        
    });
});

//xiugai 
function getAreaList(page, limit, search) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'AreaAction/AreaList',
        data: {page: page, limit: limit, search: search},
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
                    var areaId = obj.areaId;
                    areaIdStr = areaId.substring(0,8);
                    var areaName = obj.areaName;
                    var areaDomain = obj.areaDomain;
                    var areaDesc = obj.areaDesc;
                    var areaCreateTime = obj.areaCreateTime;
                    //var userrole =decodeURIComponent(obj.userrole);
                    
                    var thistr = '<tr rowid="' + areaId + '"><td class="rcheck"><input type="checkbox" name="userrow"></td>'
                    	+ '<td>' + areaIdStr + '</td><td>' + areaName + '</td><td>' + areaDomain + '</td><td>' + areaDesc
                        + '</td><td class="time">' + areaCreateTime + '</td></tr>';
                    tableStr += thistr;
                }
                $('#tablebody').html(tableStr);
            }
        }
    });
}

//xiugai 
$('#delete').on('click', function (event) {
    event.preventDefault();
    var infoList = "";
    $('input[name="userrow"]:checked').each(function () {
        infoList += "[" + $(this).parent().parent().attr("rowid") + "]&nbsp;";
    });
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除区域&nbsp;' + infoList + '?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="userrow"]:checked').each(function () {
                        deleteUser($(this).parent().parent().attr("rowid"));
                        
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

$('#tablebody').on('click', '.permit', function (event) {
    var thistd = $(this).parent();
    var userid = thistd.parent().attr("userid");
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px">'
            + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;同意该申请？</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $.ajax({
                        type: 'post',
                        url: '/VoucherAction/Confirm',
                        data: {userid: userid},
                        dataType: 'json',
                        success: function (obj) {
                            if (obj.result) {
                                thistd.html("");
                            }
                        },
                        error: function () {
                        }
                    });
                }
            },
            cancel: {
                label: "取消",
                className: "btn-default",
                callback: function () {
                }
            }
        }
    });
});

$('#tablebody').on('click', '.deny', function (event) {
    var thistd = $(this).parent();
    var userid = thistd.parent().attr('userid');
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px">'
            + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;拒绝该申请？</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $.ajax({
                        type: 'post',
                        url: '/VoucherAction/Deny',
                        data: {userid: userid},
                        dataType: 'json',
                        success: function (obj) {
                            if (obj.result) {
                                thistd.html('');
                            }
                        },
                        error: function () {
                        }
                    });
                }
            },
            cancel: {
                label: "取消",
                className: "btn-default",
                callback: function () {
                }
            }
        }
    });
});

//xiugai
$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='userrow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='userrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});

//xiugai
function deleteUser(areaId) {
    $.ajax({
        type: 'get',
        url: 'AreaAction/Delete',
        data: {areaId: areaId},
        dataType: 'json',
        success: function (result) {
            if (result == true) {
            	$('#AreaModalContainer').modal('hide');
                reloadList(1);
                //location.reload()
            }
        }
    });
}