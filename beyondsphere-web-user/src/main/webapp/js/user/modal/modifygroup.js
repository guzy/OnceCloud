$(document).ready(function () {
	initgroup();
	function initgroup(){
		var groupUuid = $('#modalcontent').attr('groupUuid');
	    $.ajax({
	        type: 'get',
	        url: '/GroupAction/GetGroup',
	        data: {groupUuid:groupUuid},
	        dataType: 'json',
	        success: function (obj) {
	        	$('#groupName').val(obj.groupName);
	        	$('#color').val(obj.groupColor);
	        	$('#group_loc').val(obj.groupLoc);
	        	$('#group_des').text(obj.groupDes);
	        }
	    });
	}

	$('#modifyGroupAction').on('click', function(){
		var groupUuid = $('#modalcontent').attr('groupUuid');
		var groupName = $('#groupName').val();
		var groupColor = $('#color').val();
		var groupLoc = $('#group_loc').val();
		var groupDes = $('#group_des').val();
		if($('#create-form').valid()){
			$.ajax({
		        type: 'post',
		        url: '/GroupAction/ModifyGroup',
		        data: {groupUuid:groupUuid, groupName:groupName, groupColor : groupColor, groupLoc:groupLoc, groupDes:groupDes},
		        dataType: 'json',
		        success: function (obj) {
		        	$('#GroupModalContainer').modal('hide');
		        	reloadList(1);
		        }
		    });
		}
	});
	
	$("#create-form").validate({
	    rules: {
	    	groupName: {
	            required: true,
	            maxlength: 20,
	            legal: true
	        },
	        group_loc: {
	            legal:true
	        },
	        group_des: {
	            legal:true
	        }
	    },
	    messages: {
	    	groupName: {
	            required: "<span class='help'>分组类别不能为空</span>",
	            maxlength: "<span class='help'>分组类别不能超过20个字符</span>",
	            legal: "<span class='help'>分组类别包含非法字符</span>"
	        },
	        group_loc: {
	            legal: "<span class='help'>地点信息包含非法字符</span>"
	        },
	        group_des: {
	            legal: "<span class='help'>描述信息包含非法字符</span>"
	        }
	    }
	});
	
});

