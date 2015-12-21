reloadList(1);

/*页面初始化信息*/
function reloadList(page) {
	//成本类别
	var type_limit = $("#type_limit").val();
    var type_search = $('#type_search').val();
    var detail_limit = $("#detail_limit").val();
    //成本详细信息
    var detail_search = $('#detail_search').val();
    var limit = $("#limit").val();
    var search = $('#search').val();
    var type = getAccountType();
    var radio_type=$('input:radio[name=optionsRadios]:checked').val();
    tabchange(type);
    if(type==1){
    	//getcosttype(page,type_limit,type_search);
    }else if(type == 2){
    	//getcosttype(page,detail_limit,detail_search);
    }else if(type ==3 || type ==4){
    	getProfit(page, limit, search, type,radio_type);
    }
}

//Tab显示切换
function tabchange(type){
	if(type==0){
		$("#finance").show();
		$("#cost_type").hide();
    	$("#cost_detail").hide();
    	$("#profit").hide();
    	$("#radio_div").hide();
    	$('#water_condiv').hide();
    	$("#typecost").hide();
    	load_prifitChart();
    }else if(type == 1){
    	$("#finance").hide();
    	$("#cost_type").show();
    	$("#cost_detail").hide();
    	$("#profit").hide();
    	$("#radio_div").hide();
    	$('#water_condiv').hide();
    	$("#typecost").hide();
    }else if(type == 2){
    	$("#finance").hide();
    	$("#cost_type").hide();
    	$("#cost_detail").show();
    	$("#profit").hide();
    	$("#radio_div").hide();
    	$('#water_condiv').hide();
    	$("#typecost").hide();
    }else if(type == 3) {
    	$("#finance").hide();
    	$("#cost_type").hide();
    	$("#cost_detail").hide();
    	$("#profit").show();
    	$("#radio_div").hide();
    	$('#water_condiv').show();
    	$("#typecost").hide();
    }else if(type == 4){
    	$("#finance").hide();
    	$("#cost_type").hide();
    	$("#cost_detail").hide();
    	$("#profit").show();
    	$("#radio_div").show();
    	$('#water_condiv').hide();
    	$("#typecost").hide();
    }else{
    	$("#finance").hide();
    	$("#cost_type").hide();
    	$("#cost_detail").hide();
    	$("#profit").hide();
    	$("#radio_div").hide();
    	$("#typecost").show();
    	$('#water_condiv').hide();
    }
}

function costByType(){
	var radio_type=$('input:radio[name=typecostRadios]:checked').val();
	if(radio_type == 1){
		$('#costtypetablebody').show();
		$('#cost').hide();
		$('#cost').css('visibility','hidden');
		$('#profit_info').hide();
	}else{
		$('#costtypetablebody').hide();
		$('#cost').show();
		$('#cost').css('visibility','visible');
		$('#profit_info').show();
	}
}

//初始化成本类型
function getcosttype(page,limit,search){
	$('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'url',
        data: { page: page, limit: limit, search: search},
        dataType: 'json',
        success: function (array) {
            if (array.length >= 1) {
            }
        }
    });
}
//初始化成本信息
function getcostdetail(page,limit,search){
	$('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'url',
        data: { page: page, limit: limit, search: search},
        dataType: 'json',
        success: function (array) {
            if (array.length >= 1) {
            }
        }
    });
}
//初始化收益信息
function getProfit(page, limit, search, type,radio_type){
	var cpu = "";
    var memory = "";//内存
    var volume = "";//硬盘
    var snapshot = "";//备份
    $.ajax({
		type: 'get',
        async: true,
        //url: 'unitPriceAction/priceList',
        url: 'CostMonthAction/priceList',
        dataType: 'json',
        success: function (obj) {
           /* if (array.length > 1) {
                for(var i = 0; i < array.length; i++){
                	switch(array[i].pricetype){
            		case 'cpu':
            			cpu = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CPU   :   ￥"+array[i].pricevalue+"&nbsp;元/1个/天";
            			break;
            		case 'mem':
            			memory = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内存   :   ￥"+array[i].pricevalue+"&nbsp;元/1GB/天";
            			break;
            		case 'volume':
            			volume = " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;硬盘   :   ￥"+array[i].pricevalue+"&nbsp;元/10GB/天";
            			break;
            		case 'snapshot':
            			snapshot = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备份   :   ￥"+array[i].pricevalue+"&nbsp;元/1个/天";
            			break;
                	}
                }
            }
            $('#once-tab-title').html("价格信息："+cpu+memory+volume+snapshot);*/
            
			cpu = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CPU   :   ￥"+obj.cpu+"&nbsp;元";
			memory = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内存   :   ￥"+obj.mem+"&nbsp;元";
			volume = " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;硬盘   :   ￥"+obj.volume+"&nbsp;元";
	        $('#once-tab-title').html("分摊成本("+obj.start+"~~"+obj.end+")："+cpu+memory+volume+snapshot);
        }
	});
    getAccountList(page, limit, search, type,radio_type);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
}

/*获取计费资源列表*/
function getAccountList(page, limit, search, type,radio_type) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'CostMonthAction/profitMonthList',
        dataType: 'json',
        success: function (array) {
              if (array.length >= 1) {
                var tableStr = "";
                var type_0_total="";
                var type_1_total="";
                var type_2_total="";
                var type_3_total="";
                var date="";
                for (var i = 0; i < array.length; i++) {
                   var obj=array[i];
                   type_0_total = Number((type_0_total+obj.cpu));
                   type_1_total = Number((type_1_total+obj.mem));
                   type_2_total = Number((type_2_total+obj.volume));
                   type_3_total = Number((type_3_total+obj.total));
                   date=obj.date;
                    var mytr = '<tr userid="'+obj.userid+'" username="'+obj.username+'">'
                        + '<td>' + obj.username + '</a></td>'
                        + '<td>￥' + obj.cpu + '</td>'
                        + '<td>￥' + obj.mem + '</td>'
                        + '<td>￥' + obj.volume + '</td>'
                        + '<td>￥' + obj.total.toFixed(2) + '</td>'
                    	+ '<td>' + obj.date + '</td>';
                    mytr += '</tr>';
                    tableStr = tableStr + mytr;
                    
                }
                var mytr = '<tr >'
                    + '<td align="center"><b>合计:</b></td>'
                    + '<td>￥' + type_0_total.toFixed(2) + '</td>'
                    + '<td>￥' + type_1_total.toFixed(2) + '</td>'
                    + '<td>￥' + type_2_total.toFixed(2) + '</td>'
                    + '<td>￥' + type_3_total.toFixed(2) + '</td>'
                    + '<td>' + date + '</td>';
                mytr += '</tr>';
                tableStr+=mytr;
                $('#tablebody').html(tableStr);
                
                
                var water='';
                for (var i = 0; i < array.length; i++) {
                   var obj = array[i];
                    var userId = obj.userid;
                    var userName = obj.username;
                    water+='<div class="water" userid='+userId+' username="'+userName+'">';
                    water+='<p class="water_title">总价（元）</p>';
                    water+='<div class="water_number">';
//                    var tol_sumprice=Math.round(Number(total));
//                	var numArray=String(tol_sumprice).split('');
                	var numArray=String(obj.total.toFixed(2)).split('');
                	var p_water='';
                    if(numArray.length<6){
                    	 for (var j = 0; j <6-numArray.length; j++) {
                         	p_water+='<p>0</p>';
     					}
                    }
                	for (var j = 0; j < numArray.length; j++) {
                    	p_water+='<p>'+numArray[j]+'</p>';
					}
                	water+=p_water;
                    
                    water+='</div>';
                    water+='<div class="zhuji">';
                    water+='<p>cpu</p>';
                    water+='<p>'+obj.cpu+'</p>';
                    water+='</div>';
                    water+='<div class="yingpan">';
                    water+='<p>内存</p>';
                    water+='<p>'+obj.mem+'</p>';
                    water+='</div>';
                    water+='<div class="kuandai">';
                    water+='<p>硬盘</p>';
                    water+='<p>'+obj.volume+'</p>';
                    water+='</div>';
                    water+='<p class="water_name">'+userName+'</p>';
                    water+='</div>';
                }
                
                $('#waterDiv').html(water);
            }
        }
    });
}

/*获取计费资源列表*/
/*function getAccountList(page, limit, search, type,radio_type) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'AccountingAction/AccountingList',
        data: { page: page, limit: limit, search: search, type: type, radio:radio_type},
        dataType: 'json',
        success: function (array) {
              if (array.length >= 1) {
                var totalnum = array[0];
                var totalp = 1;
                if (totalnum != 0) {
                    totalp = Math.ceil(totalnum / limit);
                }
                options = {
                    totalPages: totalp
                };
                $('#pageDivider').bootstrapPaginator(options);
                pageDisplayUpdate(page, totalp);
                var tableStr = "";
                var type_0_total="";
                var type_1_total="";
                var type_2_total="";
                var type_3_total="";
                for (var i = 1; i < array.length; i++) {
                    var obj = array[i];
                    var userId = obj.userId;
                    var userName = obj.userName;
                    var type_0 = obj.type_0;
                    var type_1 = obj.type_1;
                    var type_2 = obj.type_2;
                    var total =  obj.total;
                    //一天
                    if(type==4&&radio_type==1){
                    	type_0 = type_0*24;
                    	type_1 = type_1*24;
                    	type_2 = type_2*24;
                    	total = total*24;
                    }
                    //一月
                    if(type==4&&radio_type==2){
                    	type_0 = type_0*24*30;
                    	type_1 = type_1*24*30;
                    	type_2 = type_2*24*30;
                    	total = total*24*30;
                    }
                    //一季度
                    if(type==4&&radio_type==3){
                    	type_0 = type_0*24*30*3;
                    	type_1 = type_1*24*30*3;
                    	type_2 = type_2*24*30*3;
                    	total = total*24*30*3;
                    }
                    //一年
                    if(type==4&&radio_type==4){
                    	type_0 = type_0*24*30*12;
                    	type_1 = type_1*24*30*12;
                    	type_2 = type_2*24*30*12;
                    	total = total*24*30*12;
                    }
                    type_0_total = Number((type_0_total+type_0));
                    type_1_total = Number((type_1_total+type_1));
                    type_2_total = Number((type_2_total+type_2));
                    type_3_total = Number((type_3_total+total));
                    var mytr = '<tr userid="'+userId+'" username="'+userName+'">'
//                        + '<td>' + userId + '</td>'
                        + '<td><a class="uid">' + userName + '</a></td>'
                        + '<td>￥' + type_0.toFixed(2) + '</td>'
                        + '<td>￥' + type_1.toFixed(2) + '</td>'
                        + '<td>￥' + type_2.toFixed(2) + '</td>'
                        + '<td>￥' + total.toFixed(2) + '</td>';
                    mytr += '</tr>';
                    tableStr = tableStr + mytr;
                    
                }
                var mytr = '<tr >'
                    + '<td align="center"><b>合计:</b></td>'
                    + '<td>￥' + type_0_total.toFixed(2) + '</td>'
                    + '<td>￥' + type_1_total.toFixed(2) + '</td>'
                    + '<td>￥' + type_2_total.toFixed(2) + '</td>'
                    + '<td>￥' + type_3_total.toFixed(2) + '</td>';
                mytr += '</tr>';
                tableStr+=mytr;
                $('#tablebody').html(tableStr);
                
                
                var water='';
                for (var i = 1; i < array.length; i++) {
                   var obj = array[i];
                    var userId = obj.userId;
                    var userName = obj.userName;
                    var type_0 = obj.type_0;
                    var type_1 = obj.type_1;
                    var type_2 = obj.type_2;
                    var total =  obj.total;
                    water+='<div class="water" userid='+userId+' username="'+userName+'">';
                    water+='<p class="water_title">总价（元）</p>';
                    water+='<div class="water_number">';
//                    var tol_sumprice=Math.round(Number(total));
//                	var numArray=String(tol_sumprice).split('');
                	var numArray=String(total.toFixed(2)).split('');
                	var p_water='';
                    if(numArray.length<6){
                    	 for (var j = 0; j <6-numArray.length; j++) {
                         	p_water+='<p>0</p>';
     					}
                    }
                	for (var j = 0; j < numArray.length; j++) {
                    	p_water+='<p>'+numArray[j]+'</p>';
					}
                	water+=p_water;
                    
                    water+='</div>';
                    water+='<div class="zhuji">';
                    water+='<p>主机</p>';
                    water+='<p>'+type_0.toFixed(2)+'</p>';
                    water+='</div>';
                    water+='<div class="yingpan">';
                    water+='<p>硬盘</p>';
                    water+='<p>'+type_1.toFixed(2)+'</p>';
                    water+='</div>';
                    water+='<div class="kuandai">';
                    water+='<p>备份</p>';
                    water+='<p>'+type_2.toFixed(2)+'</p>';
                    water+='</div>';
                    water+='<p class="water_name">'+userName+'</p>';
                    water+='</div>';
                }
                
                $('#waterDiv').html(water);
            }
        }
    });
}*/

/*切换选项卡*/
$('.once-tab').on('click', '.tab-filter', function (event) {
    $('li', '.once-tab').removeClass('active');
    $(this).addClass('active');
    reloadList(1);
});

/*获取计费管理类型*/
function getAccountType(){
	var accounttype = $('.once-tab').find('.active').attr("type");
	if(accounttype == 'finance'){
		accounttype = 0;
	}else if(accounttype == 'costmanager'){
		accounttype = 1;
	}else if(accounttype == 'costaccount'){
		accounttype = 2;
	}else if(accounttype == 'overview'){
		accounttype = 3;
	}else if(accounttype == 'typecost'){
		accounttype = 5;
	}else{
		accounttype = 4;
	}
	return accounttype;
}


function load_allPriceList(){
	$("#costtablebody").html('');
	$.ajax({
        type: 'get',
        url: 'CostAction/allPriceList',
        dataType: 'json',
        success: function (arry) {
        	if(arry.length>0){
        		var sum_yearprice=0;
        		var sum_price=0;
        		
        		var htm='';
        		for (var i = 0; i < arry.length; i++) {
					var obj=arry[i];
					htm+='<tr typeid='+obj.typeId+'>';
					//htm+='<td><a class="id">c-'+obj.typeId.substring(0,8)+'</a></td>';
					htm+='<td><a class="id">'+obj.typeName+'</a></td>';
					htm+='<td>'+Number(obj.yearprice)+'</td>'
					htm+='<td>'+Number(obj.price)+'</td>';
					htm+='</tr>';
					
					sum_yearprice+=Number(obj.yearprice);
					sum_price+=Number(obj.price);
				}
        		htm+='<tr>';
        		htm+='<td class="lead"><strong>总计</strong></td>';
        		htm+='<td><strong>'+sum_yearprice+'</strong></td>';
        		htm+='<td><strong>'+sum_price+'</strong></td>';
        		$("#costtablebody").html(htm);
        	}
        }
    });
}


function load_typeList(){
	$("#typetablebody").html('');
	$.ajax({
        type: 'get',
        url: 'CostAction/TypeList',
        dataType: 'json',
        success: function (arry) {
        	if(arry.length>0){
        		var htm='';
        		for (var i = 0; i < arry.length; i++) {
					var obj=arry[i];
					htm+='<tr typeid='+obj.costTypeId+' typename='+obj.costTypeName+' typedesc='+obj.costTypeDes+'>';
					htm+='<td><input type="checkbox"></td>';
					//htm+='<td><a class="id">c-'+obj.costTypeId.substring(0,8)+'</a></td>';
					htm+='<td><a class="id">'+obj.costTypeName+'<a></td>';
					htm+='<td>'+obj.costTypeDes+'</td>';
					htm+='</tr>';
				}
        		$("#typetablebody").html(htm);
        	}
        }
    });
}
$(function(){
	load_allPriceList();
	load_typeList();
	
	//刷新costtype列表
	$('#stylelist_flash').click(function(){
		load_typeList();
	})
	$('#flash_allPriceList').click(function(){
		load_allPriceList();
	})
	
	
	load_costyearChart();
	load_prifitChart();
	load_profitPie();
	//每月成本投入统计列表
	load_costmonthdetail();
	//加载成本收益列表
	load_profitmonthlist();
	
})


function load_costyearChart(){
    //收益变化统计
    $.ajax({
        type: 'get',
        url: 'ProfitAction/costYearChart',
        dataType: 'json',
        async: false,
        success: function (obj) {
        	var list=obj.datalist;
        	var YtitleArray;
        	var YdataArray;
        	var data_yStr='[';
        	for (var i = 0; i < list.length; i++) {
        		var jso=list[i];
        		data_yStr+='{name:"'+jso.name+'",data:['+jso.data+']}'
        		if(i!=(list.length-1)){
        			data_yStr+=',';
        		}else{
        			data_yStr+=']';
        		}
			}
        	
        	var data_y=eval(data_yStr);
        	var data_x=obj.data_x;
        	
        	var data={};
        	data.text='年度成本分析';
        	data.title='成本投入';
        	data.unit='元';
        	data.color='#808080';
        	data.x=obj.data_x.split(",");
        	data.y=data_y;
        	showChart("cost_yearChart",data);
        }
    });
}

function load_prifitChart(){
    //收益变化统计
    $.ajax({
        type: 'get',
        url: 'ProfitAction/profitChart',
        dataType: 'json',
        async: true,
        success: function (obj) {
        	var YtitleArray= ['CPU','内存','硬盘'];
        	var YdataArray=[obj.cpu_y,obj.mem_y,obj.volume_y];
        	var data_yStr='[';
        	for (var i = 0; i < YtitleArray.length; i++) {
        		data_yStr+='{name:"'+YtitleArray[i]+'",data:['+YdataArray[i]+']}'
        		if(i!=(YtitleArray.length-1)){
        			data_yStr+=',';
        		}else{
        			data_yStr+=']';
        		}
			}
        	var data_y=eval(data_yStr);
        	var data_x=obj.data_x;
        	
        	var data={};
        	data.text='硬件资源收益分析';
        	data.title='硬件资源收益';
        	data.unit='元';
        	data.color='#808080';
        	data.x=obj.data_x.split(",");
        	data.y=data_y;
        	showChart("profitChart",data);
        }
    });
}

function load_profitPie(){
    //收益变化统计
    $.ajax({
        type: 'get',
        url: 'ProfitAction/profitPie',
        dataType: 'json',
        async: true,
        success: function (obj) {
        	var YtitleArray= obj.typeNames.split(",");
        	var YdataArray=obj.typePrices.split(",");
        	var Ytypeids= obj.typeids.split(",");
        	
        	var data_yStr='[';
        	for (var i = 0; i < YtitleArray.length; i++) {
        		data_yStr+='{ name:"'+YtitleArray[i]+'",y:'+YdataArray[i]+',tid:"'+Ytypeids[i]+'"}'
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
}




function showChart(id,data){
	$('#'+id).highcharts({
	    chart: {
	        type: 'line'
	    },
	    title: {
	        text: ''
	    },
	    xAxis: {
            categories: data.x
        },
        yAxis: {
        	min:0,
            title: {
                text: data.title
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: data.color
            }],
            labels: {
                formatter: function() {
                    return this.value+data.unit;
                }
            }
        },
        tooltip: {
            valueSuffix: data.unit
        },
	    series: data.y
	});
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
		              /* return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 2) +'% ('+
		                            Highcharts.numberFormat(this.y, 0, ',') +obj.unit+')';*/
                    return '<b>'+ this.point.name +'</b>: 2015年-'+
                    Highcharts.numberFormat(this.y, 0, ',') +obj.unit+'';
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
	                },
	                events: {
                        click: function(e) {
                        	clickPietoDetail(e.point.tid);
                        }
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


function clickPietoDetail(costtypeid){
	    var form = $("<form></form>");
	    form.attr("action", "cost/detail");
	    form.attr('method', 'post');
	    var input = $('<input type="text" name="costid" value="' + costtypeid + '" />');
	    form.append(input);
	    form.css('display', 'none');
	    form.appendTo($('body'));
	    form.submit();
}

function load_costmonthdetail(){
	$.ajax({
        type: 'get',
        url: 'ProfitDetailAction/monthdetailList',
        dataType: 'json',
        success: function (data) {
        	var arry=data.detailcostlist;
        	
        	if(arry.length>0){
        		var htm='';
        		for (var i = 0; i < arry.length; i++) {
					var obj=arry[i];
					htm+='<tr>';
					htm+='<td>'+obj.detailname+'</td>';
					htm+='<td>'+obj.typename+'</td>';
					htm+='<td>'+obj.detailcost+'</td>';
					if(data.starttime==''){
						htm+='<td>'+data.endtime+'</td>';
					}else{
						htm+='<td>'+data.starttime+'至'+data.endtime+'</td>';
					}
					htm+='</tr>';
				}
        		htm+='<tr><td colspan="2">总计</td>';
        		htm+='<td colspan="2">'+data.sumprice+'</td></tr>';
        		$("#detailCost").html(htm);
        	}
        }
    });
}

function load_profitmonthlist(){
	$.ajax({
        type: 'get',
        url: 'ProfitDetailAction/profitmonthList',
        dataType: 'json',
        success: function (data) {
        	var arry=data.monthprofitlist;
        	
        	var sumprice=0;
        	if(arry.length>0){
        		var htm='';
        		for (var i = 0; i < arry.length; i++) {
					var obj=arry[i];
					sumprice+=obj.userprice;
					htm+='<tr userid='+obj.userid+' username='+obj.username+'>';
//					htm+='<td><a class="uid">'+obj.username+'</a></td>';
					htm+='<td>'+obj.username+'</a></td>';
					htm+='<td>'+obj.userprice+'</td>';
					if(data.starttime==''){
						htm+='<td>'+data.endtime+'</td>';
					}else{
						htm+='<td>'+data.starttime+'至'+data.endtime+'</td>';
					}
					htm+='</tr>';
				}
					htm+='<tr><td><strong>总计<strong></td>';
					htm+='<td>'+sumprice.toFixed(2)+'</td>';
					if(data.starttime==''){
						htm+='<td>'+data.endtime+'</td>';
					}else{
						htm+='<td>'+data.starttime+'至'+data.endtime+'</td>';
					}
					htm+='</tr>';
        		$("#profitDetail").html(htm);
        	}
        }
    });
}

