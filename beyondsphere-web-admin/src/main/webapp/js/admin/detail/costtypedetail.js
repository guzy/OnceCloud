$('#typedetailmodify').on('click', function (event) {
    event.preventDefault();
    var $boxs=$('#typetablebody :checkbox:checked');
	if($boxs.length==0){
    	alert("请选择分类划分！");
    	return;
    }else if($boxs.length==1){
    	$('#CostDetailModalContainer').load('../admin/modal/modifycost?type=2', '', function () {
            $('#CostDetailModalContainer').modal({
                backdrop: false,
                show: true
            });
        	var $box=$('#typetablebody :checkbox:checked').eq(0).parent().parent();
        	$('#typedetailname').val($box.attr('tdname'));
        	$('#descText').val($box.attr('tddes'));
        });	
    }else{
    	alert("请选择单个分类划分进行操作！");
    	return;
    }
});

$('#typedetaildelete').on('click', function(event){
	var $boxs=$('#typetablebody :checkbox:checked');
	if($boxs.length==0){
    	alert("请选择分类划分！");
    	return;
    }else{
    	var ids='';
    	$boxs.each(function(){
    		ids+=$(this).parent().parent().attr('tdid')+',';
    	});
    	ids=ids.substring(0, ids.length-1);
    	$.ajax({
    		type: 'post',
    		url: "../CostAction/DeleteTypeDetail",
    		data: {
    			typeDetailIds:ids
    		},
    		dataType: 'json',
    		async:false,
    		success: function(data){
    			loadTypeDetailList();  
    		}
    	});
    }
});

$(function(){
	loadTypeDetailList();
	$('#flash_typeDetailList').click(function(){
		loadTypeDetailList();
	})
})

function loadTypeDetailList(){
	$("#typetablebody").html('');
	$.ajax({
        type: 'get',
        url: '../CostAction/TypeDetailList',
        data:{
        	costTypeId:$.trim($('#typetablebody').attr('typeid'))
        },
        dataType: 'json',
        success: function (data) {
        	var costtype=data.costtype;
        	var arry=data.typedetail;
        	if(arry.length>0){
        		var htm='';
        		for (var i = 0; i < arry.length; i++) {
					var obj=arry[i];
					htm+='<tr tdid='+obj.costTypeDetailId+' tdname='+obj.costTypeDetailName+' tddes='+obj.costTypeDetailDes+'>';
					htm+='<td><input type="checkbox"></td>';
					//htm+='<td>c-'+obj.costTypeDetailId.substring(0,8)+'</td>';
					htm+='<td>'+costtype.costTypeName+'</td>';
					htm+='<td>'+obj.costTypeDetailName+'</td>';
					htm+='<td>'+obj.costTypeDetailDes+'</td>';
					htm+='</tr>';
				}
        		$("#typetablebody").html(htm);
        	}
        }
    });
}