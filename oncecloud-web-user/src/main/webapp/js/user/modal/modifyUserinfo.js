$(document).ready(function () {
    fillBlank();

    function fillBlank() {

    	$.ajax({
	        type: 'get',
	        url: basePath+'UserAction/OneUser',
	        dataType: 'json',
	        success: function (array) {
	        	if(array.length >= 1)
	        	{
		            $('#user_name').val(array[0].username);
		            $('#user_email').val(array[0].email);
		            $('#user_tel').val(array[0].tel);
		            $('#user_company').val(array[0].address);
		        }
	         }
		});
    }
    
    $('#modifyUserAction').on('click', function (event){
    	event.preventDefault();
    	var userName = $('#user_name').val();
    	var userPwd = $('#user_pwd').val();
    	var userNPwd = $('#user_npwd').val();
        var userEmail = $('#user_email').val();
        var userTel = $('#user_tel').val();
        var userCom = $('#user_company').val();
        if($('#create-form').valid()){
        	$.ajax({
                type: 'post',
                url: basePath+'UserAction/Update',
                data: {
                    userName: userName,
                    userPwd: userPwd,
                    userNPwd: userNPwd,
                    userEmail: userEmail,
                    userTelephone: userTel,
                    userCompany: userCom
                },
                dataType: 'text',
                success: function (result) {
                	if(result == 1){
                		bootbox.alert('修改信息成功');
                		$('#userModalContainer').modal('hide');
                	}
                    
                }
            });
        }
    });

    $("#create-form").validate({
        rules: {
            user_pwd: {
                required: true,
                minlength: 5,
                maxlength: 20,
                legal: true
            },
            user_email: {
                required: true
            },
            user_tel: {
                required: true
            },
            user_company: {
                required: true
            }
        },
        messages: {
            user_pwd: {
                required: "<span class='help'>密码不能为空</span>",
                minlength: "<span class='help'>密码不能超过20个字符</span>",
                maxlength: "<span class='help'>密码不能超过20个字符</span>",
                legal: "<span class='help'>密码包含非法字符</span>"
            },
            user_email: {
                required: "<span class='help'>邮箱不能为空</span>"
            },
            user_tel: {
                required: "<span class='help'>联系电话不能为空</span>"
            },
            user_company: {
                required: "<span class='help'>公司不能为空</span>"
            }
        }
    });
});
