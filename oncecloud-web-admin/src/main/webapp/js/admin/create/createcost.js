/**
 * 
 */
init();
function init(){
	$('#resourcenum').spinner({
		min:1,
		step:1
	});
	addcost();
	showtypelist();
}

$('#createcost').on('click', function(){
	var radio_type=$('input:radio[name=costRadios]:checked').val();
	if(radio_type == 1){
		addtype();
	}else if(radio_type ==2){
		addtypedetail();
	}else{
		adddetail();
	}
});

$("#cancel").on("click", function(){
	$('#CostModalContainer').modal('hide');
});

function addtype(){
	var typename=$.trim($('#typename').val());
	var typedesc=$.trim($('#descText').val());
	
	$.ajax({
		type: 'post',
		url: "CostAction/CreateType",
		data: {
			costTypeId:uuid.v4(),
			costTypeName: typename,
			costTypeDes:typedesc
		},
		dataType: 'json',
		async:false,
		success: function(result){
			$('#createcost').off('click');
			$('#CostModalContainer').modal('hide');
			if(result){
				load_typeList();
			}else{
				alert("添加失败！");
			}
		}
	});
}

function addtypedetail(){
	var typeid=$('#typelist').val();
	var costTypeDetailName=$.trim($('#typedetailname').val());
	var costTypeDetailDes=$.trim($('#descText').val());
	
	$.ajax({
		type: 'post',
		url: "CostAction/CreateTypeDetail",
		data: {
			costTypeDetailId:uuid.v4(),
			costTypeId:typeid,
			costTypeDetailName: costTypeDetailName,
			costTypeDetailDes:costTypeDetailDes
		},
		dataType: 'json',
		async:false,
		success: function(result){
			if(result){
				$('#createcost').off('click');
				$('#CostModalContainer').modal('hide');
				load_typeList();
			}else{
				alert("添加失败！");
			}
		}
	});
}

function adddetail(){
	var typeid=$('#typelist').val();
	var typedetailid=$('#typedetaillist').val();
	//资源名
	var resourcename=$.trim($('#resourcename').val());
	if(resourcename == ""){
		alert('资源名称不能为空！');
		return;
	}
	//资源类型
	var resourcetype=$.trim($('#resourcetype').val());
	//单价
	var unitprice=$.trim($('#unitprice').val());
	if(unitprice == ""){
		alert('单价不能为空！');
		return;
	}
	//收费类型
	var pricetype=$('input[name="priceRadios"]:checked').val();
	//资源个数
	var resourcenum=$.trim($('#resourcenum').val());
	//创建时间
	var createtime=$.trim($('#createtime').val());
	if(createtime == ""){
		alert("创建时间不能为空！");
		return;
	}
	//销毁时间
	var destorytime=$.trim($('#destorytime').val());
	if(pricetype != 2){
		if(destorytime == ""){
			alert('成本按月/年收费时，终止时间不能为空！');
			return;
		}
	}
	$.ajax({
		type: 'post',
		url: "CostAction/CreateDetail",
		data: {
			costDetailId:uuid.v4(),
			costTypeId:typeid,
			costTypeDetailId:typedetailid,
			costDetailName:resourcename,
			costDetailType:resourcetype,
			costUnitPrice:unitprice,
			costNumber:resourcenum,
			costPriceType:pricetype,
			costCreatetime:createtime,
			costDeletetime:destorytime	
			},
		dataType: 'json',
		async:false,
		success: function(result){
			if(result){
				$('#createcost').off('click'),
				$('#CostModalContainer').modal('hide'),
				load_typeList();
			}else{
				alert("添加失败！");
			}
		}
	});
}

/*根据不同的选项，添加成本的不同内容*/
function addcost(){
	var radio_type=$('input:radio[name=costRadios]:checked').val();
	if(radio_type == 1){
		$('#typename').show();
		$('#typelist').hide();
		$('#detail').hide();
		$('#costdetailinfo').hide();
		$('#desc').show();
	}else if(radio_type ==2){
		$('#typename').hide();
		$('#typelist').show();
		$('#detail').show();
		$('#detailname').show();
		$('#typedetailname').show();
		$('#typedetaillist').hide();
		$('#costdetailinfo').hide();
		$('#desc').show();
	}else{
		$('#typename').hide();
		$('#detail').show();
		$('#detailname').show();
		$('#typelist').show();
		$('#typedetailname').hide();
		$('#typedetaillist').show();
		$('#costdetailinfo').show();
		$('#desc').hide();
	}
}

//弹出日期输入框
$('.start-time').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0
});
//弹出日期输入框
$('.end-time').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0
});
//清空起始日期输入框
$('.start-remove').on('click',function(event){
	$('.start-form').val('');
	$('#start_date').val('');
});
//清空终止日期输入框
$('.end-remove').on('click',function(event){
	$('.end-form').val('');
	$('#end_date').val('');
});


function showtypelist(){
	$.ajax({
        type: 'get',
        url: 'CostAction/TypeList',
        dataType: 'json',
		async:false,
        success: function (arry) {
        	if(arry.length>0){
        		var htm='';
        		for (var i = 0; i < arry.length; i++) {
					var obj=arry[i];
					htm+='<option value="'+obj.costTypeId+'">'+obj.costTypeName+'</option>';
				}
        		$('#typelist').html(htm);
        		
        		var typeid=$('#typelist').val();
        		showtypedetaillist(typeid);
        	}
        }
    });
}

function  showtypedetaillist(typeid){
	$.ajax({
        type: 'get',
        url: 'CostAction/TypeDetailList',
        data:{
        	costTypeId:typeid
        },
        dataType: 'json',
        success: function (data) {
        	var arry=data.typedetail;
        	if(arry.length>0){
        		var htm='';
        		for (var i = 0; i < arry.length; i++) {
					var obj=arry[i];
					htm+='<option value="'+obj.costTypeDetailId+'">'+obj.costTypeDetailName+'</option>';
				}
        		$('#typedetaillist').html(htm);
        	}
        }
    });
}

$(function(){
	$('#typelist').change(function(){
		var typeid=$(this).val();
		showtypedetaillist(typeid);
	});
})

$('#typename').on('focusout',function(){
	$('#typename').parent().find('.oc-error').remove();
	var name = $('#typename').val();
	if(valid_code(name)){
		$('#typename').parent().append('<label class="oc-error"><span class="help">不能包含特殊字符</span></label>');
	}
});

$('#typedetailname').on('focusout',function(){
	$('#typedetailname').parent().find('.oc-error').remove();
	var name = $('#typedetailname').val();
	if(valid_code(name)){
		$('#typedetailname').parent().append('<label class="oc-error"><span class="help">不能包含特殊字符</span></label>');
	}
});

$('#resourcename').on('focusout',function(){
	$('#resourcename').parent().find('.oc-error').remove();
	var name = $('#resourcename').val();
	if(valid_code(name)){
		$('#resourcename').parent().append('<label class="oc-error"><span class="help">不能包含特殊字符</span></label>');
	}
});

$('#resourcetype').on('focusout',function(){
	$('#resourcetype').parent().find('.oc-error').remove();
	var type = $('#resourcetype').val();
	if(valid_code(type)){
		$('#resourcetype').parent().append('<label class="oc-error"><span class="help">不能包含特殊字符</span></label>');
	}
});

$('#unitprice').on('focusout',function(){
    	$('#unitprice').parent().find('.oc-error').remove();
    	var price = $('#unitprice').val();
    	if(!valid_price(price)){
    		$('#unitprice').parent().append('<label class="oc-error"><span class="help">请输入非0的正整数</span></label>');
    	}
});

function valid_price(price){
	var unitPrice = '';
	unitPrice = new RegExp(/^\+?[1-9][0-9]*$/); 
	return unitPrice.test(price);
}
function valid_code(code){
	var resourcecode = '';
	resourcecode = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]") 
	return resourcecode.test(code);
}
