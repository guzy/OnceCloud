/**
 * 
 */
init(1);
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
					+' detailtype='+obj.costDetailType+' pricetype='+obj.costPriceType+' unitprice='+Number(obj.costUnitPrice)+' number='+obj.costNumber
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

$(function(){
	$('#flash_detaillist').click(function(){
		init(1);
	})
	
	$('#detailmodify').click(function(){
	    var boxs=$('#typetablebody :checkbox:checked');
	    if(boxs.length==0){
	    	alert("请选择分类！");
	    	return;
	    }else if(boxs.length==1){
	    	$('#DetailModalContainer').load('../admin/modal/modifycost?type=3', '', function () {
	            $('#DetailModalContainer').modal({
	                backdrop: false,
	                show: true
	            });
	            
	            var $box=$('#typetablebody :checkbox:checked').parent().parent();
	            $('#typedetailname').val($box.attr('typedetailname'));
	            $('#resourcename').val($box.attr('detailname'));
	            $('#resourcetype').val($box.attr('detailtype'));
	            $('#unitprice').val($box.attr('unitprice'));
	            $(':radio[name="priceRadios"][value="'+$box.attr('pricetype')+'"]').click();
	            $('#resourcenum').val($box.attr('number'));
	            $('#createtime').val($box.attr('createtime'));
	            if($box.attr('deletetime')=='--'){
	            	$('#destorytime').val('');
	            }else{
	            	$('#destorytime').val($box.attr('deletetime'));
	            }
	            $('#modify_detailid').attr('detailid',$box.attr('detailid'));
	        });
	    }else{
	    	alert("请选择单个分类进行操作！");
	    	return;
	    }
	});
	
	$('#detaildelete').on('click', function(event){
		var $boxs=$('#typetablebody :checkbox:checked');
		if($boxs.length==0){
	    	alert("请选择分类！");
	    	return;
	    }else{
	    	var ids='';
	    	$boxs.each(function(){
	    		ids+=$(this).parent().parent().attr('detailid')+',';
	    	});
	    	ids=ids.substring(0, ids.length-1);
	    	$.ajax({
	    		type: 'post',
	    		url: "../CostAction/DeleteDetail",
	    		data: {
	    			detailIds:ids
	    		},
	    		dataType: 'json',
	    		async:false,
	    		success: function(data){
	    			init(1);
	    		}
	    	});
	    }
	});
})











