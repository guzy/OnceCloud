
loaddetaillist();
function loaddetaillist(){
	$("#typetablebody").html("");
	$.ajax({
        type: 'get',
        url: '../ProfitDetailAction/profitdetaillist',
        data:{
        	userid:$.trim($('#typetablebody').attr('userid'))
        },
        dataType: 'json',
        success: function (json) {
        	var array=json.profitdetaillist;
        	if(array.length>0){
        		var htm='';
        		for (var i = 0; i < array.length; i++) {
					var obj=array[i];
        			htm+='<tr>';
        			htm+='<td>'+obj.detailname+'</td>';
        			htm+='<td>'+obj.typename+'</td>';
        			htm+='<td>'+obj.detailprofit+'</td>';
        			if(json.starttime==''){
						htm+='<td>'+json.endtime+'</td>';
					}else{
						htm+='<td>'+json.starttime+'至'+json.endtime+'</td>';
					}
        			htm+='</tr>';
        		}
        		$('#typetablebody').html(htm);
        	}
        }
    });
}