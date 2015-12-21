
loaddetaillist();
load_profitPie();
function loaddetaillist(){
//	var start=$.trim($('#start_date').val());
//	var end=$.trim($('#end_date').val());
	var userid=$.trim($('#typetablebody').attr('userid'));
	var type = getAccountType();
    tabchange(type);
//	if(!dateCompare(start,end)){
//		alert("终止时间不能大于开始时间！");
//		return;
//	}
	
	$("#typetablebody").html("");
	$.ajax({
        type: 'get',
        url: 'ProfitAction/profitList',
        data:{
//        	startTime:start,
//        	endTime:end,
        	userid:$.trim($('#typetablebody').attr('userid'))
        },
        dataType: 'json',
        success: function (json) {
        	//水表图数据
        	var numArray=String(json.total.toFixed(2)).split('');
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
        	$('#water_cpu').text(json.cpu);
        	$('#water_mem').text(json.mem);
        	$('#water_volume').text(json.volume);
        	
        	
        	 var mytr = '<tr>'
             + '<td>' + json.username + '</a></td>'
             + '<td>￥' + json.cpu + '</td>'
             + '<td>￥' + json.mem + '</td>'
             + '<td>￥' + json.volume + '</td>'
             + '<td>￥' + json.total.toFixed(2) + '</td>'
         	 + '<td>' + json.date + '</td>';
        	 +'</tr>';
        	$('#typetablebody').html(mytr);
        	//列表数据
        	/*var array=json.vmlist;
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
        	}*/
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

/*切换选项卡*/
$('.once-tab').on('click', '.tab-filter', function (event) {
    $('li', '.once-tab').removeClass('active');
    $(this).addClass('active');
    loaddetaillist()
});

function tabchange(type){
	if(type==0){
		$("#cost_overview").show();
		$("#cost_detail").hide();
		$("#cost_detail").css('visibility','hidden');
    }else{
    	$("#cost_overview").hide();
    	$("#cost_detail").show();
		$("#cost_detail").css('visibility','visible');
    }
}

/*获取计费管理类型*/
function getAccountType(){
	var accounttype = $('.once-tab').find('.active').attr("type");
	if(accounttype == 'cost_overview'){
		accounttype = 0;
	}else{
		accounttype = 1;
	}
	return accounttype;
}


function load_profitPie(){
    //收益变化统计
	 $.ajax({
       type: 'get',
       url: 'ProfitAction/profitList',
       data:{
//       	startTime:start,
//       	endTime:end,
       	userid:$.trim($('#typetablebody').attr('userid'))
       },
       dataType: 'json',
       async: true,
       success: function (obj) {
       	var YtitleArray= ['CPU','内存','硬盘'];
       	var YdataArray=[obj.cpu,obj.mem,obj.volume];
       	var data_yStr='[';
       	for (var i = 0; i < YtitleArray.length; i++) {
       		data_yStr+='["'+YtitleArray[i]+'",'+YdataArray[i]+']';
       		if(i!=(YtitleArray.length-1)){
       			data_yStr+=',';
       		}else{
       			data_yStr+=']';
       		}
			}
       	var data_y=eval(data_yStr);
       	var data={};
       	data.unit='元';
       	data.y=data_y;
       	
       	showPie("profitPie",data);
       }
   });
//    $.ajax({
//        type: 'get',
//        url: 'ProfitAction/profitPie',
//        dataType: 'json',
//        async: true,
//        success: function (obj) {
//        	var YtitleArray= ['主机','硬盘','备份'];
//        	var YdataArray=[obj.instance_y,obj.volume_y,obj.snapshot_y];
//        	var data_yStr='[';
//        	for (var i = 0; i < YtitleArray.length; i++) {
//        		data_yStr+='["'+YtitleArray[i]+'",'+YdataArray[i]+']';
//        		if(i!=(YtitleArray.length-1)){
//        			data_yStr+=',';
//        		}else{
//        			data_yStr+=']';
//        		}
//			}
//        	var data_y=eval(data_yStr);
//        	var data={};
//        	data.unit='元';
//        	data.y=data_y;
//        	
//        	showPie("profitPie",data);
//        }
//    });
}

function showPie(id,obj){
	 $('#'+id).highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: ''
	        },
	        tooltip: {
	     //       pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>',
	            formatter: function() {
		               return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 2) +'% ('+
		               this.y+obj.unit+')';
		          }
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.2f} %',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || '#666666'
	                    },
	                    connectorColor: 'silver'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: '百分比',
	            data: obj.y
	        }]
	    });
}




