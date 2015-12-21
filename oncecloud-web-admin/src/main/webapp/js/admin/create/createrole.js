$(document).ready(function () {
  
    $('#createRoleAction').on('click', function (event) {
        event.preventDefault();
        var valid = $("#create-form").valid();
        var errorLen = $('#create-form').find('.oc-error').length;
        if (valid && errorLen == 0) {
            var roleName = $("#role_name").val();
            var roleIntroduce = $("#role_introduce").val();
            var roleRemarks = $("#role_remarks").val();
                $.ajax({
                    type: 'post',
                    url: 'RoleAction/Create',
                    data: {
                    	roleName: roleName,
                    	roleIntroduce: roleIntroduce,
                    	roleRemarks: roleRemarks
                    },
                    dataType: 'json',
                    success: function (array) {
                        console.log(array);
                        if (array!=null) {
                            var roleName = decodeURIComponent(array.roleName);
                            var roleId = array.roleId;
                            var roleIntroduce = decodeURIComponent(array.roleIntroduce);
                            var roleRemarks = decodeURIComponent(array.roleRemarks);
                          
                          
                            var thistr = '<tr roleid="' + roleId + '" roleName="' + roleName + '"><td class="rcheck"><input type="checkbox" name="rolerow"></td>'
                                + '<td><a class="roleName">' + roleName + '</a></td><td>' + roleIntroduce + '</td><td>' + roleRemarks + '</td></tr>';
                            $("#tablebody").prepend(thistr);
                        }
                        $('#UserModalContainer').modal('hide');
                    }
                }); 
        }
    });

    $('#role_name').on('focusout', function () {
        nameValid();
    });

    function nameValid() {
        $('#role_name').parent().find('.oc-error').remove();
        $.ajax({
            type: 'get',
            async: false,
            url: 'RoleAction/QueryRole',
            data: {roleName: $("#role_name").val()},
            dataType: 'json',
            success: function (array) {
                if (array!=null) {
                    if (array.roleId>0) {
                        $('#role_name').parent().append('<label class="oc-error"><span class="help">角色名已存在</span></label>');
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        });
    }

    $("#create-form").validate({
        rules: {
        	role_name: {
                required: true,
                maxlength: 20,
                legal: true
            }
        },
        messages: {
        	role_name: {
                required: "<span class='help'>角色名不能为空</span>",
                maxlength: "<span class='help'>角色名不能超过20个字符</span>",
                legal: "<span class='help'>角色名包含非法字符</span>"
            }
        }
    });
});
