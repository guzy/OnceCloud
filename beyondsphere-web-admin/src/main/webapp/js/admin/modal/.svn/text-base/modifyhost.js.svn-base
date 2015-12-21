$(document).ready(function () {
    fillBlank();
    function fillBlank() {
        $('#modaltitle').html('修改服务器<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a>');

        var hostid = '';
        var hostip = '';
        var hostname = '';
        var hostdesc = '';

        $('input[name="hostrow"]:checked').each(function () {
            hostid = $(this).parent().parent().attr("hostid");
            hostip = $(this).parent().parent().attr("hostip");
            hostname = $(this).parent().parent().attr("hostname");
            hostdesc = $(this).parent().parent().attr("hostdesc");

        });
        $('#modalcontent').attr('hostid', hostid);

        $('#server_addr').val(hostip);
        $('#server_addr').attr('disabled', true).addClass('oc-disable');
        $('#pwditem').hide();
        $('#server_name').val(hostname);
        $('#server_desc').val(hostdesc);
    }

    $('#addServerAction').on('click', function (event) {
        event.preventDefault();
        var valid = $("#create-form").valid();

        var errorLen = $('#server_addr').parent().find('.oc-error').length;
        if (errorLen == 0) {
            var serverName = document.getElementById("server_name").value;
            var serverDesc = document.getElementById("server_desc").value;
            var serverPwd = document.getElementById("server_pwd").value;
            var serverIp = document.getElementById("server_addr").value;
            var hostid = $('#modalcontent').attr('hostid');
            var hostType = $('#server_type').val();
            $.ajax({
                type: 'post',
                url: 'HostAction/Update',
                data: {hostid: hostid, hostname: serverName, hostdesc: serverDesc, hostType: hostType},
                dataType: 'text',
                success: function () {
                    var thistr = $("#tablebody").find('[hostid="' + hostid + '"]');
                    thistr.attr('hostname', serverName);
                    thistr.attr('hostdesc', serverDesc);
                    thistr.children('td').eq(2).html(serverName);
                }
            });
            removeAllCheck();
            $('#ServerModalContainer').modal('hide');
        }
    });

    $('#server_addr').on('focusout', function () {
        addressValid();
    });

    function addressValid() {
        $('#server_addr').parent().find('.oc-error').remove();
        var ip = $('#server_addr').val();

        if (ip != "") {
            $.ajax({
                type: 'get',
                async: true,
                url: 'HostAction/QueryAddress',
                data: {address: ip},
                dataType: 'json',
                success: function (array) {
                    if (array.length == 1) {
                        var exist = array[0].exist;
                        if (exist == true) {
                            $('#server_addr').parent().append('<label class="oc-error"><span class="help">服务器地址已存在</span></label>');
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            });
        }
    }


    $("#create-form").validate({
        rules: {
            server_name: {
                required: true,
                maxlength: 20,
                legal: true
            },
            server_desc: {
                required: true,
                maxlength: 80,
                legal: true
            },
            server_pwd: {
                required: true,
                legal: true
            },
            server_addr: {
                required: true,
                ip: true,
                minlength: 7,
                maxlength: 15
            }
        },
        messages: {
            server_name: {
                required: "<span class='help'>服务器名称不能为空</span>",
                maxlength: "<span class='help'>服务器名称不能超过20个字符</span>",
                legal: "<span class='help'>服务器名称包含非法字符</span>"
            },
            server_desc: {
                required: "<span class='help'>服务器描述不能为空</span>",
                maxlength: "<span class='help'>服务器描述不能超过80个字符</span>",
                legal: "<span class='help'>服务器描述包含非法字符</span>"
            },
            server_pwd: {
                required: "<span class='help'>密码不能为空</span>",
                legal: "<span class='help'>密码包含非法字符</span>"
            },
            server_addr: {
                required: "<span class='help'>服务器地址不能为空</span>",
                ip: "<span class='help'>服务器地址格式不正确</span>",
                minlength: "<span class='help'>服务器地址不能少于7个字符</span>",
                maxlength: "<span class='help'>服务器地址不能超过15个字符</span>"
            }
        }
    });
});