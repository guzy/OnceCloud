$(document).ready(function () {
	$("#user_oldpwd").val("");
    //fillBlank();

  /*  function fillBlank() {
    	var userid = $("#modaltitle").attr("userid");
    	$.ajax({
	        type: 'get',
	        url: 'UserAction/OneUser',
	        data: {userid:userid},
	        dataType: 'json',
	        success: function (obj) {
	        	if(obj.length >= 1){
	        		$("#user_name").val(obj.username);
	        		$("#user_email").val(obj.usermail);
	        		$("#user_tel").val(obj.userphone);
		         }
	          }
		});
    	
    }*/
	
	$('#user_oldpwd').on('focusout', function () {
        oldpassValid();
    });
	$('#user_pwd').on('focusout', function () {
        pwdValid();
    });
	$('#user_rpwd').on('focusout', function () {
		pwdValid();
    });
	$('#user_email').on('focusout', function () {
        emailValid();
    });
	$('#user_tel').on('focusout', function () {
        telValid();
    });
	
    
    $('#modifyUserAction').on('click', function (event){
    	event.preventDefault();
    	
    	var userOPwd = $.trim($("#user_oldpwd").val());
    	var userNPwd = $.trim($("#user_pwd").val());
        var userEmail = $.trim($("#user_email").val());
        var userTel = $.trim($("#user_tel").val());
    	var flag=false;
    	if(emailValid()&&telValid()&&userNPwd==""){
    		flag=true;
    	}else if(emailValid()&&telValid()&&oldpassValid()&&userNPwd==""){
    		flag=true;
    	}else if(emailValid()&&telValid()&&oldpassValid()&&pwdValid()&&pwdValid()){
    		flag=true;
    	}
    	
    	if(flag){
    		$('#modifyUserAction').off("click");
    		var userNPwd = $("#user_pwd").val();
            var userEmail = $("#user_email").val();
            var userTel = $("#user_tel").val();
        	$.ajax({
                type: 'post',
                url: 'UserAction/UpdateAdmin',
                data: {
                    userPwd: userNPwd,
                    userEmail: userEmail,
                    userTelephone: userTel
                },
                dataType: 'json',
                success: function (result) {
                	$('#adminModalContainer').modal('hide');
                	if(result){
                		bootbox.alert('修改信息成功');
                	}
                    
                }
            });
    	}
        
    });
});


function oldpassValid() {
	var flag=false;
	
	var $point =$("#user_oldpwd").parent();
	$point.find('.oc-error').remove();
    var oldpwd = $.trim($("#user_oldpwd").val());
    var userNPwd = $.trim($("#user_pwd").val());
    
    if(oldpwd==""&&userNPwd==""){
    	flag=true;
    }else{
    	 $.ajax({
    	        type: 'post',
    	        async: false,
    	        url: 'UserAction/QueryAdminPass',
    	        data: {userPwd: oldpwd},
    	        dataType: 'json',
    	        success: function (flag) {
    	            if (flag) {
    	            	flag= true;
    	            } else {
    	            	$point.append('<label class="oc-error"><span class="help">密码错误，请重新填写！</span></label>');
    	            	$("#user_oldpwd").val("");
    	            }
    	        }
    	    });
    }
    return flag;
}


function pwdValid(){
	var flag=false;
	
	var $point =$("#user_pwd").parent();
	var userPwd = $.trim($("#user_pwd").val());
	var patten = new RegExp(/^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{6,}$/);
	$point.find('.oc-error').remove();
	
	var oldpwd=$.trim($("#user_oldpwd").val());
	if(userPwd==""){
		if(oldpassValid()||oldpwd==""){
			$point.append('<label class="oc-error"><span class="help">请填写原密码！</span></label>');
			flag= true;
		}
	}else{
		if (userPwd == "") {
	    	$point.append('<label class="oc-error"><span class="help">密码不能为空！</span></label>');
	    } else{
	    	 if(userPwd.length<6){
		    	$point.append('<label class="oc-error"><span class="help">密码不能低于6位！</span></label>');
		    } else if(userPwd.length>20){
		    	$point.append('<label class="oc-error"><span class="help">密码不能大于20位！</span></label>');
		    } else if(!patten.test(userPwd)){
		    	$point.append('<label class="oc-error"><span class="help">密码应包含数字和字母大小写！</span></label>');
		    } else{
		    	flag= true;
		    }
	    }
	}
	return flag;
}

//重复密码验证
function rpwdValid(){
	var $point=$('#user_rpwd').parent();
	$point.find('.oc-error').remove();
     var pwd = $('#user_pwd').val();
	 var rpwd = $("#user_rpwd").val();
	 if (pwd != rpwd) {
	     $point.append('<label class="oc-error"><span class="help">两次输入的密码不一致</span></label>');
	     return false;
	 } else {
	     return true;
	 }
}

//email信息验证
function emailValid(){
	var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
	var $point =$("#user_email").parent();
	$point.find('.oc-error').remove();
    var userMail = $.trim($("#user_email").val());
    if (userMail == "") {
    	$point.append('<label class="oc-error"><span class="help">邮箱不能为空！</span></label>');
    	return false;
    } else if(!patten.test(userMail)){
    	$point.append('<label class="oc-error"><span class="help">邮箱格式错误！</span></label>');
    	return false;
    }else{
    	return true;
    }
}

//电话信息验证
function telValid(){
	var userTel = $.trim($("#user_tel").val());
	var patten = '';
	if(userTel.length == 11){
		patten = new RegExp(/^1[3|4|5|8][0-9]\d{4,8}$/); 
	}else{
		patten = new RegExp(/^\d{3,4}-?\d{7,9}$/); //电话号码格式010-12345678 
	}

	var $point =$("#user_tel").parent();
	$point.find('.oc-error').remove();
    if (userTel == "") {
    	$point.append('<label class="oc-error"><span class="help">电话不能为空！</span></label>');
    	return false;
    } else if(!patten.test(userTel)){
    	$point.append('<label class="oc-error"><span class="help">电话格式错误！</span></label>');
    	return false;
    }else{
    	return true;
    }
}

