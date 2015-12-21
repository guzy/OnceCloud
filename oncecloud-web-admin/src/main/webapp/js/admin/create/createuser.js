$(document).ready(function () {
    fillBlank();

    function fillBlank() {
        var type = $('#UserModalContainer').attr('type');
    	$.ajax({
	        type: 'get',
	        url: 'RoleAction/RoleList',
	        data: {page: 1, limit: 1234567, search: ""},
	        dataType: 'json',
	        success: function (array) {
	        	if(array.length >= 1){
		            $.each(array, function (index, obj){
						var roleId = obj.roleId;
						var roleName = decodeURIComponent(obj.roleName);
						$('#role_select').append('<option value="'+roleId+'">'+roleName+'</option>');
		            });
				    if ('edit' == type) {
			            $('#modaltitle').html('修改用户<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a>');
			            var userid = $('#platformcontent').attr('userid');
			            $('#modalcontent').attr('userid', userid);

			            var name = $('#username').text();
			            $('#modalcontent').attr('oldname', name);
			            var mail = $('#usermail').text();
			            var phone = $('#userphone').text();
			            var company = $('#usercom').text();
			            var level = $('#userlevel').attr('level');
			            $('#pwd').hide();
			            $('#user_name').val(name);
			            $('#user_name').attr('disabled', true).addClass('oc-disable');
			            $('#user_email').val(mail);
			            $('#user_tel').val(phone);
			            $('#user_company').val(company);
			            $('#user_level').val(level);
			            $('#pool').hide();
			            $('#role_select').val($('#userroleid').attr("userroleid"));
			         } else {
			        	$.ajax({
					        type: 'post',
					        url: 'PoolAction/AllPool',
					        dataType: 'json',
					        success: function (array) {
					            $.each (array, function (index, obj){
									var pooluuid = obj.poolid;
									var poolname = decodeURIComponent(obj.poolname);
									$('#pool_select').append('<option value="'+pooluuid+'">'+poolname+'</option>');
					            });
					        }
			    		});
			        	$.ajax({
			                type: 'get',
			                url: 'AreaAction/AreaAllList',
			                dataType: 'json',
			                success: function (array) {
			                	//$("#selectArea").html("");
			                    $.each(array, function (index, json) {
			                    	var areaId = json.areaId;
									var areaName = decodeURIComponent(json.areaName);
									$('#area_select').append('<option value="'+areaId+'">'+areaName+'</option>');
			                    });
			                    
			                }
			               });
			        }
		         }
	         }
		});
    }

    $('#createUserAction').on('click', function (event) {
        event.preventDefault();
        var valid = $("#create-form").valid();
        var errorLen = $('#create-form').find('.oc-error').length;
        var type = $('#UserModalContainer').attr('type');
        if (valid && errorLen == 0) {
            var userName = document.getElementById("user_name").value;
            var userPwd = document.getElementById("user_pwd").value;
            var userEmail = document.getElementById("user_email").value;
            var userTel = document.getElementById("user_tel").value;
            var userCom = document.getElementById("user_company").value;
            var userLevel = document.getElementById("user_level").value;
            var poolUuid = $("#pool_select option:selected").val();
            var roleUuid = $("#role_select option:selected").val();
            var roleName = $("#role_select option:selected").text();
            var areaId = $("#area_select option:selected").val();
            var areaName = $("#area_select option:selected").text();
            if ('new' == type) {
                $.ajax({
                    type: 'post',
                    url: 'UserAction/Create',
                    data: {
                        userName: userName,
                        userPassword: userPwd,
                        userEmail: userEmail,
                        userTelephone: userTel,
                        userCompany: userCom,
                        userLevel: userLevel,
                        poolUuid: poolUuid,
                        roleUuid: roleUuid,
                        areaId:areaId
                    },
                    dataType: 'json',
                    success: function (array) {
                        console.log(array);
                        if (array.length == 1) {
                            var obj = array[0];
                            var username = decodeURIComponent(obj.username);
                            var userid = obj.userid;
                            var usercom = decodeURIComponent(obj.usercom);
                            var userdate = obj.userdate;
                            var userlevel = obj.userlevel;
                            var usermail = obj.usermail;
                            var userphone = obj.userphone;
                            var levelstr = "<a><span class='glyphicon glyphicon-user' style='margin-right:7px'></span>平台用户</a>";
                            if (userlevel == 0) {
                                levelstr = "<a><span class='glyphicon glyphicon-star' style='margin-right:7px'></span>管理员</a>";
                            }
                            var mytr = '<tr userid="' + userid + '" username="' + username + '"><td class="rcheck"><input type="checkbox" name="userrow"></td>'
                                + '<td><a class="username">' + username + '</a></td><td>' + usermail + '</td><td>' + userphone + '</td><td>' + usercom + '</td><td>' + levelstr
                                + '</td><td class="time">' + userdate + '</td><td>'+roleName+'</td></tr>';
                            $("#tablebody").prepend(mytr);
                        }
                        $('#UserModalContainer').modal('hide');
                    }
                });
            } else if ('edit' == type) {
                var userId = $('#platformcontent').attr('userid');
                $.ajax({
                    type: 'post',
                    url: 'UserAction/Update',
                    data: {
                        userName: userName,
                        changeId: userId,
                        userEmail: userEmail,
                        userTelephone: userTel,
                        userCompany: userCom,
                        userLevel: userLevel,
                        roleUuid: roleUuid
                    },
                    dataType: 'text',
                    success: function () {
                        var levelstr = "<a><span id='userlevel' level='1' class='glyphicon glyphicon-user' style='margin-right:7px'></span>平台用户</a>";
                        if (userlevel == 0) {
                            levelstr = "<a><span id='userlevel' level='0' class='glyphicon glyphicon-star' style='margin-right:7px'></span>管理员</a>";
                        }
                        $('#username').text(userName);
                        $('#usermail').text(userEmail);
                        $('#userphone').text(userTel);
                        $('#usercom').text(userCom);
                        $('#leveltd').html(levelstr);
                        $('#userroleid').attr("userroleid",roleUuid);
                        $('#userroleid').text(roleName);
                        $('#UserModalContainer').modal('hide');
                    }
                });
            }
        }
    });

    $('#user_rpwd').on('focusout', function () {
        pwdValid();
    });

    $('#user_pwd').on('focusout', function () {
        pwdValid();
    });

    function pwdValid() {
        $('#user_rpwd').parent().find('.oc-error').remove();
        var pwd = document.getElementById("user_pwd").value;
        var rpwd = document.getElementById("user_rpwd").value;
        if (pwd != rpwd) {
            $('#user_rpwd').parent().append('<label class="oc-error"><span class="help">两次输入的密码不一致</span></label>');
            return false;
        } else {
            return true;
        }
    }

    $('#user_name').on('focusout', function () {
        nameValid();
    });
    
    $('#user_email').on('focusout', function () {
    	$('#user_email').parent().find('.oc-error').remove();
    	var email = $('#user_email').val();
    	if(email == ""){
    		$('#user_email').parent().append('<label class="oc-error"><span class="help">邮箱不能为空</span></label>');
    	}
    	if(email != "" && !valid_email(email)){
    		$('#user_email').parent().append('<label class="oc-error"><span class="help">邮箱格式不正确</span></label>');
    	}
    });
    
    function valid_email(email) {
		var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
		return patten.test(email);
	}
    
    $('#user_tel').on('focus',function(){
    	$('#user_tel').parent().find('.oc-error').remove();
    	$('#user_tel').parent().append('<label class="oc-error"><span class="help">固定电话格式：xxxx-xxxxxxx</span></label>');
    });
    
    $('#user_tel').on('focusout', function () {
    	$('#user_tel').parent().find('.oc-error').remove();
    	var tel = $('#user_tel').val();
    	if(!valid_tel(tel.length, tel)){
    		$('#user_tel').parent().append('<label class="oc-error"><span class="help">电话格式不正确</span></label>');
    	}
    });
    
    function valid_tel(length, telphone){
    	var tel = '';
    	if(length == 11){
    		tel = new RegExp(/^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/); 
    	}else{
    		tel = new RegExp(/^\d{3,4}-?\d{7,9}$/); //电话号码格式010-12345678 
    	}
    	return tel.test(telphone);
    }
    
    $('#user_company').on('focusout', function () {
    	$('#user_company').parent().find('.oc-error').remove();
    	if($('#user_company').val().length == 0){
    		$('#user_company').parent().append('<label class="oc-error"><span class="help">公司不能为空</span></label>');
    	}
    });

    function nameValid() {
        $('#user_name').parent().find('.oc-error').remove();
        var userName = document.getElementById("user_name").value;
        var oldname = $('#modalcontent').attr('oldname');
        if (userName == "") {
            $('#user_name').parent().append('<label class="oc-error"><span class="help">用户名不能为空</span></label>');
            return false;
        } else if (userName.length < 3) {
            $('#user_name').parent().append('<label class="oc-error"><span class="help">用户名不能少于3个字符</span></label>');
            return false;
        } else if (userName == oldname) {
            return true;
        } else {
            $.ajax({
                type: 'get',
                async: false,
                url: 'UserAction/QueryUser',
                data: {userName: userName},
                dataType: 'json',
                success: function (array) {
                    if (array.length == 1) {
                        var exist = array[0].exist;
                        if (exist == true) {
                            $('#user_name').parent().append('<label class="oc-error"><span class="help">用户名已存在</span></label>');
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
            user_pwd: {
                required: true,
                minlength: 6,
                maxlength: 20,
                legal: true
            }
        },
        messages: {
            user_pwd: {
                required: "<span class='help'>密码不能为空</span>",
                minlength: "<span class='help'>密码应该是6-20个字符</span>",
                maxlength: "<span class='help'>密码应该是6-20个字符</span>"
            }
        }
    });
});
