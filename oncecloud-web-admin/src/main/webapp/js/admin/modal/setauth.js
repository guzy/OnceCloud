var setting = {
		check: {
			enable: true,
			chkboxType: { "Y" : "p", "N" : "s" }
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
 		
$(document).ready(function(){
	   var roleId = '';
       var roleName = '';
       var treeObj;

	$('input[name="rolerow"]:checked').each(function () {
		roleId = $(this).parent().parent().attr("roleid");
		roleName = $(this).parent().parent().attr("rolename");
    });
	$("#roleNameSpan").html(roleName);
	$("#roleIdinput").val(roleId);
	
	// 数据初始化
	 $.ajax({
         type: 'post',
         url: 'RoleAction/RoleData',
         data: {
        	 roleid: roleId,
         },
         dataType: 'json',
         success: function (data) {
        	 console.log(data);
         	if(data!=null && data.length>0){
         		  treeObj = $.fn.zTree.init($("#treeAuth"), setting, data);
         		  treeObj.expandAll(true);
         		}
         	else{
         		bootbox.alert("初始化数据失败！请关闭重试一下！");
         	}
         }
     });
	
	
	 $('#authset').on('click', function (event) {
	        event.preventDefault();
	        var nodes = treeObj.getCheckedNodes(true);
	        var actionids="";
	        $(nodes).each(function(index,item){
	        	actionids+=item.id+",";
	        })
            $.ajax({
                type: 'post',
                url: 'RoleAction/SetAuth',
                data: {
                	 roleid: roleId,
                	 actionids:actionids
                },
                dataType: 'json',
                success: function (array) {
                	  $('#UserModalContainer').modal('hide');
                }
            });
	    });
	
});