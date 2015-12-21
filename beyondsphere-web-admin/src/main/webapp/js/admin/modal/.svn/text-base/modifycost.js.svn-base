/**
 * 
 */

init();
function init(){
	var type = $('#type').val();
	modifycost(type);
	showtypelist();
}

$('#modifycost').on('click', function(){
	var type = $('#type').val();
	if(type == 1){
		modifytype();
	}else{
		modifytypedetail()
	}
});

$("#cancel").on("click", function(){
	$('#CostModalContainer').modal('hide');
	$('#CostDetailModalContainer').modal('hide');
	
});

function modifytype(){
	var $box=$('#typetablebody :checkbox:checked').eq(0).parent().parent();
	var typeid=$box.attr('typeid');
	var typename=$.trim($('#typename').val());
	var descText=$.trim($('#descText').val());
	//alert("typeid:"+typeid);
	$.ajax({
		type: 'post',
		url: "CostAction/UpdateType",
		data: {
			costTypeId:typeid,
			costTypeName:typename,
			costTypeDes:descText
		},
		dataType: 'json',
		success: function(data){
			
			if(data){
				$('#modifycost').off('click');
				$('#CostDetailModalContainer').modal('hide');
				$('#CostModalContainer').modal('hide');
				load_typeList();
			}else{
				alert("修改分类失败！");
			}
		}
	});
}

function modifytypedetail(){
	var $box=$('#typetablebody :checkbox:checked').eq(0).parent().parent();
	var typeid=$('#typelist').val();
	var typedetailid=$box.attr('tdid');
	var typedetailname=$.trim($('#typedetailname').val());
	var descText=$.trim($('#descText').val());
	//alert("typeid:"+typeid);
	$.ajax({
		type: 'post',
		url: "../CostAction/UpdateTypeDetail",
		data: {
			costTypeDetailId:typedetailid,
			costTypeId:typeid,
			costTypeDetailName:typedetailname,
			costTypeDetailDes:descText
		},
		dataType: 'json',
		success: function(data){
			if(data){
				$('#modifycost').off('click');
				$('#CostDetailModalContainer').modal('hide');
				$('#CostModalContainer').modal('hide');
				loadTypeDetailList();
			}else{
				alert("修改分类划分失败！");
			}
		}
	});
}

function modifycost(type){
	if(type == 1){
		$('#typename').show();
		$('#typelist').hide();
		$('#typeinfo').hide();
	}else{
		$('#typename').hide();
		$('#typelist').show();
		$('#typeinfo').show();
		$('#typedetaillist').hide();
	}
}
function showtypelist(){
	var typeid=$('#typetablebody').attr('typeid');

	$.ajax({
        type: 'get',
        url: '../CostAction/TypeList',
        dataType: 'json',
		async:false,
        success: function (arry) {
        	if(arry.length>0){
        		var htm='';
        		for (var i = 0; i < arry.length; i++) {
					var obj=arry[i];
					if(typeid==obj.costTypeId){
						htm+='<option value="'+obj.costTypeId+'" selected>'+obj.costTypeName+'</option>';
					}else{
						htm+='<option value="'+obj.costTypeId+'">'+obj.costTypeName+'</option>';
					}
				}
        		$('#typelist').html(htm);
        	}
        }
    });
}
