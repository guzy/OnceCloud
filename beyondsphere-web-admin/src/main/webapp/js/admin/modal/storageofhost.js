$(document).ready(function () {

    $("#ServerModalContainer").on("hidden", function () {
        $(this).removeData("modal");
        $(this).children().remove();
    });

    $("#tablebodydetail").on('click', '.uninstall', function () {
        var hostUuid = $("#tablebodydetail").attr('hostuuid');
        var srUuid = $(this).parent().attr('sruuid');
        var showid = "sr-" + srUuid.substring(0, 8);
        bootbox.dialog({
            message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;卸载存储&nbsp;' + showid + '?</div>',
            title: "提示",
            buttons: {
                main: {
                    label: "确定",
                    className: "btn-primary",
                    callback: function () {
                        $.ajax({
                            type: 'get',
                            url: 'HostAction/UnbindSR',
                            data: {hostuuid: hostUuid, sruuid: srUuid},
                            dataType: 'json',
                            success: function (obj) {
                            	if(obj.result){
                            		var thistr = $("#tablebody").find('[hostid="' + hostUuid + '"]');
                                    var thisa = thistr.find('.srdetail');
                                    var size = thisa.attr('size');
                                    var currentsize = parseInt(size) - 1;
                                    thisa.attr('size', currentsize);
                                    thisa.html('<span class="glyphicon glyphicon-hdd"></span>&nbsp;&nbsp;' + currentsize);
                            	}
                                $('#ServerModalContainer').modal('hide');
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

    getSRList();

    function getSRList() {
        var hostUuid = $("#tablebodydetail").attr('hostuuid');
        var btable = document.getElementById("tablebodydetail");
        btable.innerHTML = "";
        var tableStr = "";
        $.ajax({
            type: 'get',
            url: 'HostAction/SRofhost',
            data: {hostUuid: hostUuid},
            dataType: 'json',
            success: function (array) {
                if (array.length == 0) {
                    $('.sr-pane').html("<div class='alert alert-info'>服务器未挂载存储</div>");
                } else {
                    for (var i = 0; i < array.length; i++) {
                        var obj = array[i];
                        var srUuid = obj.sruuid;
                        var sruuidDetail = "sr-" + srUuid.substring(0, 8);
                        var srip = obj.srip;
                        var srdir = obj.srdir;
                        var srtype = obj.srtype;
                        tableStr = tableStr + '<tr><td><a>' + sruuidDetail + '</a></td><td>' + srip
                            + '</td><td>' + srdir + '</td><td><a>' + srtype.toUpperCase() + '</a></td><td sruuid="' + srUuid + '"><a class="uninstall">卸载</a></td></tr>';
                    }
                    btable.innerHTML = tableStr;
                }
            }
        });
    }
});