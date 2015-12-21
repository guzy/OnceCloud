
loaddetaillist();

function loaddetaillist(){
	var start=$.trim($('#start_date').val());
	var end=$.trim($('#end_date').val());
	var userid=$.trim($('#typetablebody').attr('userid'));
	if(!dateCompare(start,end)){
		alert("终止时间不能大于开始时间！");
		return;
	}
	
	$("#typetablebody").html("");
	$.ajax({
        type: 'get',
        url: '../ProfitAction/profitList',
        data:{
        	startTime:start,
        	endTime:end,
        	userid:$.trim($('#typetablebody').attr('userid'))
        },
        dataType: 'json',
        success: function (json) {
        	var numArray=String(json.sum_price).split('');
        	var p_water='';
            if(numArray.length<6){
            	 for (var j = 0; j <6-numArray.length; j++) {
                 	p_water+='<p>0</p>';
					}
            }
        	for (var j = 0; j < numArray.length; j++) {
            	p_water+='<p>'+numArray[j]+'</p>';
			}
        	$('#water_number').html(p_water);
        	$('#water_instance').text(json.sum_instance);
        	$('#water_volume').text(json.sum_volume);
        	$('#water_snapshot').text(json.sum_snapshot);
        	
//        	double sum_instance=0;
//    		double sum_volume=0;
//    		double sum_snapshot=0;
    		
        	
        	var array=json.vmlist;
        	var detaillist=json.detaillist;
        	var htm='';
        	
        	if(array.length>0){
        		for (var i = 0; i < array.length; i++) {
					var obj=array[i];
        			htm+='<tr>';
        			if(i==0){
        				htm+='<td rowspan="'+array.length+'" align="center">硬件资源</td>';
        			}
        			if(obj.vmType==0){
        				htm+='<td>主机-'+obj.vmId.substring(0,8)+'</td>';
        			}else if(obj.vmType==1){
        				htm+='<td>硬盘-'+obj.vmId.substring(0,8)+'</td>';
        			}else if(obj.vmType==2){
        				htm+='<td>备份-'+obj.vmId.substring(0,8)+'</td>';
        			}
        			
        			htm+='<td>￥'+obj.vmPrice+'</td>';
        			if(obj.vmStatus==0){
        				htm+='<td>已经销毁</td>';
        			}else if(obj.vmStatus==1){
        				htm+='<td>正常运行</td>';
        			}else{
        				if(obj.vmType==1){
        					if(obj.volumeStatus==0){
        						htm+='<td>已删除</td>';
        					}else if(obj.volumeStatus==1){
        						htm+='<td>可用</td>';
        					}else if(obj.volumeStatus==2){
        						htm+='<td>创建中</td>';
        					}else if(obj.volumeStatus==3){
        						htm+='<td>安装中</td>';
        					}else if(obj.volumeStatus==4){
        						htm+='<td>使用中</td>';
        					}else if(obj.volumeStatus==5){
        						htm+='<td>卸载中</td>';
        					}else if(obj.volumeStatus==6){
        						htm+='<td>删除中</td>';
        					}else{
        						htm+='<td>--</td>';
        					}
        				}else{
        					htm+='<td>--</td>';
        				}
        			}
        			htm+='<td>'+obj.startTime+'</td>';
        			htm+='<td>'+obj.endTime+'</td>';
        			htm+='<td>'+obj.usedTime+'</td>';
        			htm+='</tr>';
        		}
        	}
        	/*if(detaillist.length>0){
        		for (var i = 0; i < detaillist.length; i++) {
					var obj=detaillist[i];
					htm+='<tr>';
        			if(i==0){
        				htm+='<td rowspan="'+detaillist.length+'" align="center">IDC资源</td>';
        			}
    				htm+='<td>'+obj.name+'</td>';
        			
        			htm+='<td>￥'+obj.sumPrice+'</td>';
        			htm+='<td>--</td>';
        			htm+='<td>'+obj.startTime+'</td>';
        			htm+='<td>'+obj.endTime+'</td>';
        			htm+='<td>'+obj.usedTime+'</td>';
        			htm+='</tr>';
        		}
        	}*/
        	
        	$('#typetablebody').html(htm);
        }
    });
}

$(function(){
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
	
	$('#searchBun').click(function(){
		loaddetaillist();
	})
	
	$('#flash_profitlist').click(function(){
		loaddetaillist();
	})
});

/**
 * 比较两日期的大小
 * @param startdate
 * @param enddate
 * @returns {Boolean}
 */
function dateCompare(startdate,enddate){   
	var arr=startdate.split("-");    
	var starttime=new Date(arr[0],arr[1],arr[2]);    
	var starttimes=starttime.getTime();   
	  
	var arrs=enddate.split("-");    
	var lktime=new Date(arrs[0],arrs[1],arrs[2]);    
	var lktimes=lktime.getTime();   
	  
	if(starttimes>lktimes){   
		return false;   
	}else{
		return true;   
	}
}  