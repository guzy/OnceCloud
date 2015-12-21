///add by cyh 
function init() {
    $('.view-types').on('click', '.view-type', function (event) {
        event.preventDefault();
        $('a', $('.view-types')).removeClass('current');
        $(this).addClass('current');
        var type = $(this).attr("oc-type");
        if (type == 'text') {
            $("#textview").show();
            $("#imageview").hide();
        } else {
            $("#textview").hide();
            $("#imageview").show();
        }
    });

    $(".graph-actions a").mouseenter(function () {
        if (!$(this).hasClass("btn-forbidden")) {
            $(this).find(".text").show();
        }
    }).mouseleave(function () {
        $(this).find(".text").hide();
    });

    $('.component-router-sg').mouseenter(function () {
        $(this).find('.btn').show();
    }).mouseleave(function () {
        $(this).find('.btn').hide();
    });

    $('.component-instance-sg').mouseenter(function () {
        $(this).find('.btn').show();
    }).mouseleave(function () {
        $(this).find('.btn').hide();
    });

    $("#componentsId").on("click", "#instanceNetID", function () {
//    	event.preventDefault();
    	var url = $("#platformcontent").attr('platformBasePath')
         + 'user/modal/modify';
    	var instanceUuid = $("#platformcontent").attr("instanceUuid");
        $('#IpModalContainer').load(basePath + 'user/modal/mountVmip', {
            "modifyUuid": instanceUuid,
        },
            function () {
                $('#IpModalContainer').modal({
                    backdrop: false,
                    show: true
                });
        });
    });

    $("#instance-volumeDiv").on("mouseenter", ".component-instance-volume", function () {
        $(this).find(".btn-delete").show();
    });

    $("#instance-volumeDiv").on("mouseleave", ".component-instance-volume", function () {
        $(this).find(".btn-delete").hide();
    });

    $("#instance-volumeDiv").on("click", "#bindvolumes", function(event) {
		event.preventDefault();
		$('#ModifyModalContainer').load(basePath + 'user/modal/mountvolume', '', function() {
			$('#ModifyModalContainer').modal({
				backdrop : false,
				show : true
			});
		});
	});

    $("#instance-volumeDiv").on("click", ".btn-delete", function () {
        deletevolumes(this);
    });

    $('#uistart').on('click', function (event) {
        event.preventDefault();
        showbox(0);
    });
    $('#uirestart').on('click', function (event) {
        event.preventDefault();
        showbox(1);
    });
    $('#destroy').on('click', function (event) {
        event.preventDefault();
        showbox(2);
    });
    $('#uiclose').on('click', function (event) {
        event.preventDefault();
        showbox(3);
    });

    $('#createimage').on('click', function (event) {
        event.preventDefault();
        var thisurl = $(this).attr('url');
        thisurl = thisurl + '?rsid=' + $("#platformcontent").attr("instanceUuid");
        $('#InstanceModalContainer').load(thisurl, '', function () {
            $('#InstanceModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
    });

    $('#backup').on('click', function (event) {
        event.preventDefault();
        var thisurl = $(this).attr('url');
        thisurl = thisurl + '?rsid=' + $("#platformcontent").attr("instanceUuid") + '&rstype=instance&rsname=' + $("#instancename").text();
        bootbox.dialog({
            className: "bootbox-large",
            message: '<div class="alert alert-warning" style="margin:10px; color:#c09853">'
                + '<span class="glyphicon glyphicon-warning-sign"></span>&nbsp;对正在运行的主机进行在线备份时，需要注意以下几点:<br/><br/>1. 备份只能捕获在备份任务开始时已经写入磁盘的数据，不包括当时位于内存里的数据；<br/>2. 为了保证数据的完整性，请在创建备份前暂停所有文件的写操作，或进行离线备份。</div>',
            title: "提示",
            buttons: {
                main: {
                    label: "创建备份",
                    className: "btn-primary",
                    callback: function () {
                        $('#InstanceModalContainer').load(thisurl, '', function () {
                            $('#InstanceModalContainer').modal({
                                backdrop: false,
                                show: true
                            });
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

}


function destroyVM(uuid) {
    $("#basic-list2").find("[name='stateicon']").removeClass("icon-running").removeClass("icon-stopped").addClass('icon-process');
    $("#basic-list2").find("[name='stateword']").text('销毁中');
    $.ajax({
        type: 'get',
        url: '/VMAction/DeleteVM',
        data: {uuid: uuid},
        dataType: 'json',
        complete: function (array) {
            window.location.href = 'instance.jsp';
        }
    });
}

function shutdownVM(uuid, force) {
    $("#basic-list2").find("[name='stateicon']").removeClass("icon-running").addClass('icon-process');
    $("#basic-list2").find("[name='stateword']").text('关机中');
    $.ajax({
        type: 'get',
        url: '/VMAction/ShutdownVM',
        data: {uuid: uuid, force: force},
        dataType: 'json',
        complete: function (array) {
            getInstanceBasicList();
        }
    });
}

function startVM(uuid) {
    $("#basic-list2").find("[name='stateicon']").removeClass("icon-stopped").addClass('icon-process');
    $("#basic-list2").find("[name='stateword']").text('启动中');
    $.ajax({
        type: 'get',
        url: '/VMAction/StartVM',
        data: {uuid: uuid},
        dataType: 'json',
        complete: function (array) {
            getInstanceBasicList();
        }
    });
}

function restartVM(uuid) {
    $("#basic-list2").find("[name='stateicon']").removeClass("icon-running").addClass('icon-process');
    $("#basic-list2").find("[name='stateword']").text('重启中');
    $.ajax({
        type: 'get',
        url: '/VMAction/RestartVM',
        data: {uuid: uuid},
        dataType: 'json',
        complete: function (array) {
            getInstanceBasicList();
        }
    });
}

function bindvolumesshow() {
    var vmuuid = $("#platformcontent").attr("instanceUuid");
    $.ajax({
        type: 'get',
        url: '/VolumeAction/VolumesOfVM',
        data: {vmUuid: vmuuid},
        dataType: 'json',
        success: function (array) {
            var i = array.length;
            var j = 12 / (i + 1);
            $("#instance-volumeDiv").html("");
            if (array.length > 0) {
                $.each(array, function (index, item) {
                    $("#instance-volumeDiv").append('<div class="col-md-' + j + ' instance-volume component-instance-volume">\
			                  <a class="btn-delete" href="#" data-id="' + item.volumeId + '">\
			                  <span class="glyphicon glyphicon-remove"></span></a>\
			                  <span class="size">' + item.volumeSize + ' G</span>\
			                  <a class="volume-name"  data-permalink="">' + decodeURIComponent(item.volumeName) + '</a>');
                });
            }
            $("#instance-volumeDiv").append(' <div class="col-md-' + j + ' component-instance-volume none">\
                    <a class="btn" href="#" id="bindvolumes"><span class="glyphicon glyphicon-hdd"></span>\
                    <span class="text">加载硬盘</span></a>\
              </div>');
        },
        error: function () {
        }
    });
}

function bindvolumes() {
    
}

/*function changeNet() {
    var vmUuid = $("#platformcontent").attr('instanceUuid');
	event.preventDefault();
    $('#IpModalContainer').load('user/modal/mountVmip', '',
        function () {
            $('#IpModalContainer').modal({
                backdrop: false,
                show: true
            });
    });
}*/

function showbox(type) {
    var infoList = $("#instanceid").val();
    var infoArray = new Array("启动主机", "重启主机", "销毁主机", "关闭主机");
    var showMessage = '';
    var showTitle = '';
    if (type == 3) {
        showMessage = '<div class="alert alert-info" style="margin:10px 10px 0">1. 强制关机会丢失内存中的数据<br/>'
            + '2. 为保证数据的完整性，请在强制关机前暂停所有文件的写操作，或进行正常关机。</div>'
            + '<div class="item" style="margin:0"><div class="controls" style="margin-left:100px">'
            + '<label class="inline"><input type="checkbox" id="force">&nbsp;强制关机</label></div></div>';
        showTitle = infoArray[type] + '&nbsp;' + infoList + '&nbsp;?';
    } else {
        showMessage = '<div class="alert alert-info" style="margin:10px">'
            + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;'
            + infoArray[type] + '&nbsp;' + infoList + '&nbsp;?</div>';
        showTitle = '提示';
    }
    bootbox.dialog({
        className: "oc-bootbox",
        message: showMessage,
        title: showTitle,
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    var uuid = $("#platformcontent").attr("instanceUuid");
                    if (type == 0) {
                        startVM(uuid);
                    } else if (type == 1) {
                        restartVM(uuid);
                    } else if (type == 2) {
                        destroyVM(uuid);
                    } else if (type == 3) {
                        var force = $('#force')[0].checked;
                        shutdownVM(uuid, force);
                    }
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
}


///删除硬盘
function deletevolumes(obj) {
    var showMessage = '';
    var showTitle = '';

    showMessage = '<div class="alert alert-warning" style="margin:10px; color:#c09853">'
        + '<span class="glyphicon glyphicon-warning-sign"></span>&nbsp;物理卸载硬盘时，需要注意以下几点:'
        + '<br/><br/>1. 卸载硬盘时会丢失位于缓存中的数据；<br/>2. 为保证数据的完整性，最好确保该硬盘在主机的操作系统中处于非加载状态。</div>';
    showTitle = '卸载硬盘&nbsp;?';

    bootbox.dialog({
        className: "oc-bootbox",
        message: showMessage,
        title: showTitle,
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    var volumeuuid = $(obj).data("id");
                    $.ajax({
                        type: 'post',
                        url: '/VolumeAction/Unbind',
                        data: {volumeUuid: volumeuuid},
                        dataType: 'json',
                        complete: function (obj) {
                            bindvolumesshow();
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
}

function backfunction() {
    var state = $("#instancestate").val();
    if (state == 1) {
        ///正在运行
        $("#uistart").addClass("btn-forbidden");
        $("#uirestart").removeClass("btn-forbidden");
        $("#uiclose").removeClass("btn-forbidden");

    } else if (state == 0) {
        ///正常关机
        $("#uistart").removeClass("btn-forbidden");
        $("#uirestart").addClass("btn-forbidden");
        $("#uiclose").addClass("btn-forbidden");
    }
}

