$(document).ready(function () {
    getHostlist();

    $('#createImageAction').on('click', function (event) {
        event.preventDefault();
        var valid = $("#create-form").valid();
        if (valid) {
            var imageUUId = $("#image_uuid").val();
            var imageName = $("#image_name").val();
            var imagePwd = $("#image_pwd").val();
            var imageServer = $("#image_server").val();
            var imageOs = $("#image_os").val();
            var imageDesc = $("#image_desc").val();
            $.ajax({
                type: 'post',
                url: 'ImageAction/Create',
                data: {
                    imageUUId: imageUUId,
                    imageName: imageName,
                    imageServer: imageServer,
                    imageOs: imageOs,
                    imageDesc: imageDesc,
                    hostUuid:'',
                    imagePwd: imagePwd
                },
                dataType: 'json',
                success: function (array) {
                    if (array.length = 1) {
                        var obj = array[0];
                        var imageSize = obj.imagesize;
                        var imageplatform = decodeURIComponent(obj.imageplatform);
                        var imagestr = obj.imagestr;
                        var createDate = obj.createDate;
                        var imagehost = obj.imagehost;
                        var showid = "img-" + imageUUId.substring(0, 8);
                        var hostname = decodeURIComponent(obj.hostname);
                        var typeStr = "公有";
                        var type = $('.once-tab').find('.active').attr("type");
                        var stateStr = '<td><span class="icon-status icon-running" name="stateicon">'
                            + '</span><span name="stateword">可用</span></td>';
                        var poolName = obj.poolname;
                        var ref = obj.reference;
                        if (ref == null) {
                        	ref = 0;
                        }
                        var mytr = '<tr imageUId="' + imageUUId + '" imageName="' + imageName + '" imageType="'
                            + type + '"><td class=“rcheck"><input type="checkbox" name="imagerow" imageRef="'+ ref +'" pooluuid="'+obj.pooluuid+'"></td>'
                            + '<td><a class="id">' + showid + '</a></td><td><a>' + imageName + '</a></td><td>' + imageSize + '</td><td>' + imageplatform + '</td>'
                            + stateStr + '<td>' + typeStr + '</td><td class="time">' + createDate + '</td></tr>';
                        $("#tablebody").prepend(mytr);
                    }
                },
                error: function () {
                }
            });
            $('#ImageModalContainer').modal('hide');
        }
        removeAllCheck();
    });

    $("#closeImageAction").on('click', function (event) {
        removeAllCheck();
    });
    
    $("#image_server").on('change', function(){
    	var poolUuid = $('#image_server').val();
    	$.ajax({
            type: 'get',
            url: 'HostAction/HostListInPool',
            data: {poolUuid:poolUuid},
            dataType: 'json',
            success: function (array) {
            	$("#image_host").empty();
                if (array.length > 0) {
                    $(array).each(function (index, item) {
                        $("#image_host").append("<option value='" + item.hostuuid + "'>" + decodeURIComponent(item.hostname) + "</option>");
                    });
                }
            },
            error: function () {
            }
        });
    });
    
    $("#create-form").validate({
        rules: {
            image_uuid: {
                required: true,
                minlength: 36,
                maxlength: 36
            },
            image_name: {
                required: true,
                maxlength: 20,
                legal: true
            },
            image_desc: {
                required: true,
                maxlength: 80,
                legal: true
            },
            image_pwd: {
                required: true,
                maxlength: 40,
                legal: true
            }
        },
        messages: {
            image_uuid: {
                required: "<span class='help'>映像UUID不能为空</span>",
                minlength: "<span class='help'>映像UUID必须为36个字符</span>",
                maxlength: "<span class='help'>映像UUID必须为36个字符</span>"
            },
            image_name: {
                required: "<span class='help'>映像名称不能为空</span>",
                maxlength: "<span class='help'>映像名称不能超过20个字符</span>",
                legal: "<span class='help'>映像名称包含非法字符</span>"
            },
            image_desc: {
                required: "<span class='help'>映像描述不能为空</span>",
                maxlength: "<span class='help'>映像描述不能超过80个字符</span>",
                legal: "<span class='help'>映像描述包含非法字符</span>"
            },
            image_pwd: {
                required: "<span class='help'>映像密码不能为空</span>",
                maxlength: "<span class='help'>映像密码不能超过40个字符</span>",
                legal: "<span class='help'>映像密码包含非法字符</span>"
            }
        }
    });

    function getHostlist() {
        $.ajax({
            type: 'post',
            url: 'PoolAction/AllPool',
            dataType: 'json',
            success: function (array) {
                if (array.length > 0) {
                    $(array).each(function (index, item) {
                        $("#image_server").append("<option value='" + item.poolid + "'>" + decodeURIComponent(item.poolname) + "</option>");
                    });
                }
            },
            error: function () {
            }
        });
    }
    

    function removeAllCheck() {
        var boxes = document.getElementsByName("imagerow");
        for (var i = 0; i < boxes.length; i++) {
            boxes[i].checked = false;
            $(boxes[i]).change();
        }
    }
});