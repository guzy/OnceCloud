load();
function load(){
	$('#resourcenum').spinner({
		min:1,
		step:1
	});
	
}
$(function(){
	$("#cancel").on("click", function(){
		$('#DetailModalContainer').modal('hide');
	});
	
	/*$('#typedetailname').on('focusout',function(){
		checkNameContent('typedetailname');
	});*/
	$('#resourcename').on('focusout',function(){
		checkNameContent('resourcename');
	});
	$('#resourcetype').on('focusout',function(){
		checkNameContent('resourcetype');
	});
	$('#unitprice').on('focusout',function(){
		checkPrice('unitprice');
	});
	
	$('#modifydetail').click(function(){
		uipdatedetail();
	})
})



function uipdatedetail(){
	var detailid=$('#modify_detailid').attr('detailid');
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
	if(checkPrice(unitprice)){
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
		url: "../CostAction/UpdateDetail",
		data: {
			costDetailId:detailid,
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
				$('#modifydetail').off('click');
				$('#DetailModalContainer').modal('hide');
				bootbox.alert("修改成功！");
				init(1);
			}else{
				bootbox.alert("修改失败！");
			}
		}
	});
}






function checkNameContent(tid){
	var flag=false;
	$('#'+tid).parent().find('.oc-error').remove();
	var name = $.trim($('#'+tid).val());
	if(name==''){
		$('#'+tid).parent().append('<label class="oc-error"><span class="help">不能为空</span></label>');
	}else if(valid_code(name)){
		$('#'+tid).parent().append('<label class="oc-error"><span class="help">不能包含特殊字符</span></label>');
	}else{
		flag=true;
	}
	return flag;
}

function checkPrice(id){
	var flag=false;
	$('#'+id).parent().find('.oc-error').remove();
	var name = $.trim($('#'+id).val());
	if(name==''){
		$('#'+id).parent().append('<label class="oc-error"><span class="help">不能为空</span></label>');
	}else if(valid_code(name)){
		$('#'+id).parent().append('<label class="oc-error"><span class="help">请输入非0的正整数</span></label>');
	}else{
		flag=true;
	}
	return flag;
}

function valid_price(price) {
	var unitPrice = '';
	unitPrice = new RegExp(/^\+?[1-9][0-9]*$/);
	return unitPrice.test(price);
}
function valid_code(code) {
	var resourcecode = '';
	resourcecode = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
	return resourcecode.test(code);
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



function init(page){
	var limit = $('#limit').val();
    var search = $('#search').val();
    loaddetaillist(page, limit, search);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
}

function loaddetaillist(page, limit, search){
	$("#typetablebody").html("");

	$.ajax({
        type: 'get',
        url: '../CostAction/DetailList',
        data:{
        	page:page,
        	limit:limit,
        	search:search,
        	costTypeId:$.trim($('#typetablebody').attr('typeid'))
        },
        dataType: 'json',
        success: function (jso) {
        	var totalnum=jso.totalNum;
        	var arry=jso.detaillist;
        	
        	if(arry.length>0){
                var totalp = 1;
                if (totalnum != 0) {
                    totalp = Math.ceil(totalnum / limit);
                }
                options = {
                    totalPages: totalp
                };
                $('#pageDivider').bootstrapPaginator(options);
                pageDisplayUpdate(page, totalp);
        		
        		var htm='';
        		for (var i = 0; i < arry.length; i++) {
					var obj=arry[i];
					
					htm+='<tr detailid='+obj.costDetailId+' detailname='+obj.costDetailName+' typedetailname='+obj.costTypeDetailName
					+' detailtype='+obj.costDetailType+' pricetype='+obj.costPriceType+' unitprice='+obj.costUnitPrice+' number='+obj.costNumber
					+' createtime='+obj.costCreatetime+' deletetime='+obj.costDeletetime+'>';
					htm+='<td><input type="checkbox"></td>';
//					htm+='<td>c-'+obj.costDetailId.substring(0,8)+'</td>';
					htm+='<td>'+obj.costTypeDetailName+'</td>';
					htm+='<td>'+obj.costDetailName+'</td>';
					htm+='<td>'+obj.costDetailType+'</td>';
					htm+='<td>'+Number(obj.costUnitPrice)+'</td>';
					if(obj.costPriceType==0){
						htm+='<td>月</td>';
					}else if(obj.costPriceType==1){
						htm+='<td>年</td>';
					}else{
						htm+='<td>总价</td>';
					}
					htm+='<td>'+obj.costNumber+'</td>';
					htm+='<td>'+obj.costCreatetime+'</td>';
					htm+='<td>'+obj.costDeletetime+'</td>';
					htm+='</tr>';
				}
        		$("#typetablebody").html(htm);
        	}
        }
    });

}
