getUserList();
function getUserList() {
    $('#userbody').html("");
    var vmUid =$("#modalcontent").attr("vmUid");
    $.ajax({
        type: 'get',
        url: 'UserAction/UserListSave',
        data: {page: 1, limit: 10, search: ""},
        dataType: 'json',
        success: function (array) {
            if (array.length >= 1) {
                var tableStr = "";
                for (var i = 1; i < array.length; i++) {
                    var obj = array[i];
                    var username = decodeURIComponent(obj.username);
                    var userid = obj.userid;
                    var userlevel = obj.userlevel;
                    var userphone = obj.userphone;
                    var userrole =decodeURIComponent(obj.userrole);
                    var thistr = '';
                    if(vmUid==userid){
                    	thistr = '<tr userid="' + userid + '" username="' + username + '"><td class="rcheck"><input type="radio" name="userrow" checked="checked"></td>'
                        + '<td><a class="username">' + username + '</a></td><td>' + userphone + '</td><td>' + userrole + '</td></tr>';
                    }else{
                    	thistr = '<tr userid="' + userid + '" username="' + username + '"><td class="rcheck"><input type="radio" name="userrow"></td>'
                        + '<td><a class="username">' + username + '</a></td><td>' + userphone + '</td><td>' + userrole + '</td></tr>';
                    }
                    tableStr += thistr;
                }
                
                $('#userbody').html(tableStr);
            }
        }
    });
}
$("#submit_btn").on("click",function(){
	var vmuuid = $("#modalcontent").attr("vmuuid");
	var userid = $("input[name='userrow']:checked").parent().parent().attr("userid");
	$.ajax({
        type: 'post',
        url: 'VMAction/distributeVM2User',
        data: {vmuuid: vmuuid, userid: userid},
        dataType: 'json',
        success: function (obj) {
        	if(obj.issuccess){
        		$('#InstanceModalContainer').modal('hide');
        		reloadList(1);
        	}
        }
    });
});