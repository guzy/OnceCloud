$(document).ready(function () {
    fillBlank();
    $("#wizard").bwizard({
		backBtnText : "",
		nextBtnText : ""
	});
	$('.li-disable').unbind();
	$('ol').removeClass("clickable");
	$("#btn_submit").hide();
	$("#btn_cancle").hide();
	$("#server_type").on('change',function(){
		var server_type = $("#server_type option:selected").val();
		if(server_type=="beyondCloud"){
			$("#btn_submit").hide();
			$("#btn_cancle").hide();
			$("#btn_next").show();
			$("#container").show();
		}
		if(server_type=="vSphere"){
			$("#btn_submit").show();
			$("#btn_cancle").show();
			$("#btn_next").hide();
			$("#container").hide();
		}
	});
    function fillBlank() {
        var type = $('#ServerModalContainer').attr('type');
        if ('edit' == type) {
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
            $('#hostItem').css('display','');
            $('#server_addr').val(hostip);
            $('#server_addr').attr('disabled', true).addClass('oc-disable');
            $('#pwditem').hide();
            $('#server_name').val(hostname);
            $('#server_desc').val(hostdesc);
            fillPower();
        }
    }
    
    function fillPower() {
        var hostid = '';
        var hostip = '';

        $('input[name="hostrow"]:checked').each(function () {
            hostid = $(this).parent().parent().attr("hostid");
            hostip = $(this).parent().parent().attr("hostip");
        });
        $('#modalcontent').attr('hostid', hostid);
        $('#host_uuid').val("host-"+hostid.substring(0, 8));
        $('#host_ip').val(hostip);
        $.ajax({
            type: 'get',
            url: 'PowerAction/PowerHost',
            data: {
            	hostid: hostid,
            },
            dataType: 'json',
            success: function (power) {
            	$("#power_uuid").val(power.powerUuid);
            	$("#motherboard_ip").val(power.motherboardIP);
            	$("#port").val(power.powerPort);
            	$("#power_username").val(power.powerUsername);
            	$("#power_pwd").val(power.powerPassword);
            	if(power.powerValid==0){
        	       $("#server_status").val("未验证/验证不通过");
        		}else{
            	   checkStatus(power.powerUuid,hostid,power.motherboardIP,power.powerPort,power.powerUsername,power.powerPassword);
        		}
            }
        });
    }
    
    $('.btn-back').on('click', function(event) {
		event.preventDefault();
		$("#wizard").bwizard("back");
	});

    $('.btn-next').on('click', function(event) {
		event.preventDefault();
		if($("#server-form").valid()){
			$("#host_ip").val($("#server_addr").val());
			$("#wizard").bwizard("next");
		}
	});
    
    $("#powercheck").click(function(){
   	 if($("#power-form").valid()){
   		 checkStatus($("#motherboard_ip").val(),$("#port").val(),$("#power_username").val(),$("#power_pwd").val());
	 }
   });
    
    function checkStatus(hostmotherip,hostport,hostusername,hostpsw){
	  $.ajax({
          type: 'post',
          url: 'PowerAction/CheckPowerStatus',
          data: {
        	  hostmotherip: hostmotherip,
        	  hostport:hostport,
        	  hostusername:hostusername,
        	  hostpsw:hostpsw,
          },
          dataType: 'json',
          success: function (powerstatus) {
        	$('#power_status').val(powerstatus);
          	if(powerstatus==-1){
      	       $("#server_status").val("验证不通过");
      		}
          	else{
          		if(powerstatus==0){
          			 $("#server_status").val("关机");
      			}
          		if(powerstatus==1){
          			 $("#server_status").val("运行中");
      			}
      		}
          }
      });
    }
    
    $('#addServerAction').on('click', function (event) {
        event.preventDefault();
        var errorLen = $('#server_addr').parent().find('.oc-error').length;
        if (errorLen == 0) {
            var serverName = document.getElementById("server_name").value;
            var serverDesc = document.getElementById("server_desc").value;
            var serverPwd = document.getElementById("server_pwd").value;
            var serverIp = document.getElementById("server_addr").value;
            var hosttype = $('#server_type').val();
            var powerid = $("#power_uuid").val();
            var hostmotherip = $("#motherboard_ip").val();
            var hostport = $("#port").val();
            var powername = $("#power_username").val();
            var powerpwd = $("#power_pwd").val();
            var powerStatus = $('#power_status').val();
            var type = $('#ServerModalContainer').attr('type');
            if ('new' == type) {
                $.ajax({
                    type: 'post',
                    url: 'HostAction/Create',
                    data: {
                        hostname: serverName,
                        hostpwd: serverPwd,
                        hostdesc: serverDesc,
                        hostip: serverIp,
                        hosttype: hosttype,
                        powerid: powerid,
                        powerStatus: powerStatus,
                        hostmotherip: hostmotherip,
                        hostport: hostport,
                        powername: powername,
                		powerpwd: powerpwd
                    },
                    dataType: 'json',
                    success: function (array) {
                        if (array.length == 1) {
                            var obj = array[0];
                            var hostname = decodeURIComponent(obj.hostname);
                            var hostid = obj.hostid;
                            var hostip = obj.hostip;
                            var createdate = obj.createdate;
                            var hostcpu = obj.hostcpu;
                            var hostdesc = obj.hostdesc;
                            var hostmem = obj.hostmem;
                            var showid = "host-" + hostid.substring(0, 8);
                            var srsize = obj.srsize;
                            var mytr = '<tr hostid="' + hostid + '" ' + 'hostname="' + hostname + '" hostip="' + hostip + '" hostdesc="' + hostdesc + '">'
                            		 + '<td class="rcheck"><input type="checkbox" name="hostrow"></td>'
                                     + '<td><a class="id">' + showid + '</a></td>'
                                     + '<td>' + hostname + '</td>'
                                     + '<td>' + hostip + '</td>'
                                     + '<td>' + hostcpu + '&nbsp;核</td>'
                                     + '<td>' + hostmem + '&nbsp;MB</td>'
                                     + '<td state="unload"></td>'
                                     + '<td><a class="srdetail" size=' + srsize + '><span class="glyphicon glyphicon-hdd"></span>&nbsp;&nbsp;' + srsize + '</a></td>'
                                     + '<td class="time">' + createdate + '</td></tr>';
                            $("#tablebody").prepend(mytr);
                            $('#ServerModalContainer').modal('hide');
                        } else {
                            bootbox.dialog({
                                className: "bootbox-large",
                                message: '<div class="alert alert-warning" style="margin:10px; color:#c09853"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;添加服务器失败，可能有下面几个原因:<br/><br/>1. 目标地址无法访问或未安装虚拟化服务；<br/>2. 服务器密码输入错误；<br/>3. 目标服务器地址已存在。</div>',
                                title: "提示",
                                buttons: {
                                    main: {
                                        label: "确定",
                                        className: "btn-primary",
                                        callback: function () {
                                        }
                                    },
                                    cancel: {
                                        label: "取消",
                                        className: "btn-default",
                                        callback: function () {
                                            $('#ServerModalContainer').modal('hide');
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            } else if ('edit' == type) {
                var hostid = $('#modalcontent').attr('hostid');
                $.ajax({
                    type: 'post',
                    url: 'HostAction/Update',
                    data: {hostid: hostid, hostname: serverName, hostdesc: serverDesc, rackUuid: rackUuid},
                    dataType: 'text',
                    success: function () {
                        var thistr = $("#tablebody").find('[hostid="' + hostid + '"]');
                        thistr.attr('hostname', serverName);
                        thistr.attr('hostdesc', serverDesc);
                        thistr.attr('rackid', rackUuid);
                        thistr.children('td').eq(2).html(serverName);
                        thistr.children('td').eq(7).html('<a>' + rackName + '</a>');
                    }
                });
                removeAllCheck();
                $('#ServerModalContainer').modal('hide');
            }
        }
    });
    $('#btn_submit').on('click', function (event) {
        event.preventDefault();
        var errorLen = $('#server_addr').parent().find('.oc-error').length;
        if (errorLen == 0) {
            var serverName = document.getElementById("server_name").value;
            var serverDesc = document.getElementById("server_desc").value;
            var serverPwd = document.getElementById("server_pwd").value;
            var serverIp = document.getElementById("server_addr").value;
            var hosttype = $('#server_type').val();
            var type = $('#ServerModalContainer').attr('type');
            if ('new' == type) {
                $.ajax({
                    type: 'post',
                    url: 'HostAction/Create',
                    data: {
                        hostname: serverName,
                        hostpwd: serverPwd,
                        hostdesc: serverDesc,
                        hostip: serverIp,
                        hosttype: hosttype
                    },
                    dataType: 'json',
                    success: function (array) {
                        if (array.length == 1) {
                            var obj = array[0];
                            var hostname = decodeURIComponent(obj.hostname);
                            var hostid = obj.hostid;
                            var hostip = obj.hostip;
                            var createdate = obj.createdate;
                            var hostcpu = obj.hostcpu;
                            var hostdesc = obj.hostdesc;
                            var hostmem = obj.hostmem;
                            var showid = "host-" + hostid.substring(0, 8);
                            var srsize = obj.srsize;
                            var mytr = '<tr hostid="' + hostid + '" ' + 'hostname="' + hostname + '" hostip="' + hostip + '" hostdesc="' + hostdesc + '">'
                            		 + '<td class="rcheck"><input type="checkbox" name="hostrow"></td>'
                                     + '<td><a class="id">' + showid + '</a></td>'
                                     + '<td>' + hostname + '</td>'
                                     + '<td>' + hostip + '</td>'
                                     + '<td>' + hostcpu + '&nbsp;核</td>'
                                     + '<td>' + hostmem + '&nbsp;MB</td>'
                                     + '<td state="unload"></td>'
                                     + '<td><a class="srdetail" size=' + srsize + '><span class="glyphicon glyphicon-hdd"></span>&nbsp;&nbsp;' + srsize + '</a></td>'
                                     + '<td class="time">' + createdate + '</td></tr>';
                            $("#tablebody").prepend(mytr);
                            $('#ServerModalContainer').modal('hide');
                        } else {
                            bootbox.dialog({
                                className: "bootbox-large",
                                message: '<div class="alert alert-warning" style="margin:10px; color:#c09853"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;添加服务器失败，可能有下面几个原因:<br/><br/>1. 目标地址无法访问或未安装虚拟化服务；<br/>2. 服务器密码输入错误；<br/>3. 目标服务器地址已存在。</div>',
                                title: "提示",
                                buttons: {
                                    main: {
                                        label: "确定",
                                        className: "btn-primary",
                                        callback: function () {
                                        }
                                    },
                                    cancel: {
                                        label: "取消",
                                        className: "btn-default",
                                        callback: function () {
                                            $('#ServerModalContainer').modal('hide');
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            } else if ('edit' == type) {
                var hostid = $('#modalcontent').attr('hostid');
                $.ajax({
                    type: 'post',
                    url: 'HostAction/Update',
                    data: {hostid: hostid, hostname: serverName, hostdesc: serverDesc, rackUuid: rackUuid},
                    dataType: 'text',
                    success: function () {
                        var thistr = $("#tablebody").find('[hostid="' + hostid + '"]');
                        thistr.attr('hostname', serverName);
                        thistr.attr('hostdesc', serverDesc);
                        thistr.attr('rackid', rackUuid);
                        thistr.children('td').eq(2).html(serverName);
                        thistr.children('td').eq(7).html('<a>' + rackName + '</a>');
                    }
                });
                removeAllCheck();
                $('#ServerModalContainer').modal('hide');
            }
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


    $("#server-form").validate({
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
    
    $("#power-form").validate({
        rules: {
        	motherboard_ip: {
                required: true,
            },
            port: {
                required: true,
                digits:true,
            },
            power_username: {
                required: true,
            },
            power_pwd: {
                required: true,
            }
        },
        messages: {
        	motherboard_ip: {
                required: "<span class='help'>请填写服务器主板IP</span>",
            },
            port: {
                required: "<span class='help'>请填写服务器端口</span>",
                digits:"<span class='help'>端口为整数</span>",
            },
            power_username: {
                required: "<span class='help'>请填写账号</span>",
            },
            power_pwd: {
                required: "<span class='help'>请填写密码</span>",
            }
        }
    });
});