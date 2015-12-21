 reloadTab1();
function reloadTab1() {
	$("div[id^='tab1_div']").show();
	$("div[id^='tab2_div']").hide();
	 // Radialize the colors
    Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
        return {
            radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
            stops: [
                [0, color],
                [1, Highcharts.Color(color).brighten(-0.6).get('rgb')] // darken
            ]
        };
    });


    //第一部分，总的数据展示
    $.ajax({
        type: 'get',
        url: 'overviewAction/dataSum',
        dataType: 'json',
        async: true,
        success: function (obj) {
        	$("#enterprises").text(obj.sum_enterprise);	//企业数
        	$("#VMs").text(obj.vmNumber);	//虚拟主机数
        	$("#colony").text(obj.poolNumber);	//集群
        	$("#pcs").text(obj.hostNumber);	//主机
        	$("#storage").text(obj.storageNumber);	//存储
        	$("#images").text(obj.imageNumber);  //模版
        }
    });
    
    //近期注册企业列表
    $.ajax({
        type: 'get',
        url: 'overviewAction/enterpriseList',
        dataType: 'json',
        async: true,
        success: function (arr) {
        	var html="";
        	for (var i = 0; i < arr.length; i++) {
				var obj = arr[i];
				var time=obj.createTime.substring(0,10).replace(/-/g,'/');
				var len = 13;
				var val = obj.usercom;
				for (var j = 0; j < val.length; j++) {
					if (val[j].match(/[^\x00-\xff]/ig) == null && len<25) //全角
					len += 0.3;
				} 
				if(val.length>len){
					val=val.substring(0,Math.floor(len))+"..."; 
				}
				
				html+='<tr class="info">';
				html+='<td title="'+obj.usercom+'">'+val+'</td><td>'+time+'</td>';
				html+='</tr>';
			}
        	$("#enterprisesList").html(html);
        }
    });
    
    //近期企业变化统计图
    $.ajax({
        type: 'get',
        url: 'overviewAction/enterprise3month',
        dataType: 'json',
        async: true,
        success: function (obj) {
        	var data={};
        	data.name='注册企业总数';
        	data.title='注册企业总数 (家)';
        	data.unit='家';
        	data.color='#808080';
        	data.x=obj.data_x.split(",");
        	data.y=eval('['+obj.data_y+']');
        	showChart("companycontainer",data);
        }
    });
    
    //注册虚拟机总数企业列表
    $.ajax({
        type: 'get',
        url: 'overviewAction/VMtop5',
        dataType: 'json',
        async: true,
        success: function (arr) {
        	var html="";
        	for (var i = 0; i < arr.length; i++) {
				var obj = arr[i];
				
				var len = 13;
				var val = obj.username;
				for (var j = 0; j < val.length; j++) {
					if (val[j].match(/[^\x00-\xff]/ig) == null) //全角
					len += 0.5;
				} 
				if(val.length>len){
					val=val.substring(0,Math.floor(len))+"..."; 
				}
				
				html+='<tr class="info">';
				html+='<td title="'+obj.username+'">'+val+'</td><td>'+obj.count+'</td>';
				html+='</tr>';
			}
        	$("#VMList").html(html);
        }
    });
    
    
  //近期虚拟机总数统计图
    $.ajax({
        type: 'get',
        url: 'overviewAction/VM3month',
        dataType: 'json',
        async: true,
        success: function (obj) {
        	var data={};
        	data.name='虚拟机总数';
        	data.title='虚拟机总数 (台)';
        	data.unit='台';
        	data.color='#808080';
        	data.x=obj.data_x.split(",");
        	data.y=eval('['+obj.data_y+']');
        	showChart("vmcontainer",data);
        }
    });
    
  //风险评估统计图
    $.ajax({
        type: 'get',
        url: 'overviewAction/riskpie',
        dataType: 'json',
        async: true,
        success: function (obj) {
        	showPie("container",obj);
        }
    });
    
  //近期收益变化统计图
    $.ajax({
        type: 'get',
        url: 'overviewAction/income',
        dataType: 'json',
        async: true,
        success: function (obj) {
        	var data={};
        	data.name='总收益';
        	data.title='总收益 (￥)';
        	data.unit='￥';
        	data.color='#808080';
        	data.x=obj.data_x.split(",");
        	data.y=eval('['+obj.data_y+']');
        	showChart("profitcontainer",data);
        }
    });
   
}
function showChart(id,data){
	 $('#'+id).highcharts({
	        title: {
	            text: '',
	           // x: -20 //center
	        },
	        subtitle: {
	            text: '',
	            x: -20
	        },
	        xAxis: {
	            categories: data.x
	        },
	        yAxis: {
	            title: {
	                text: data.title
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: data.color
	            }]
	        },
	        tooltip: {
	            valueSuffix: data.unit
	        },
	        legend: {
	            layout: 'vertical',
	            //align: 'right',
	            verticalAlign: 'bottom',
	            borderWidth: 0
	        },
	        series: [{
	            name: data.name,
	            data: data.y
	        }]
	    });
}

function showPie(id,obj){
	 $('#'+id).highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        colors:[
		        '#ED2633',
	            '#63C050'
	        ],
	        title: {
	            text: ''
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
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
	            data: [
	                ['未加入安全组', Number(obj.N_vlanVM)],
	                ['已加入安全组', Number(obj.Y_vlanVM)],
	            ]
	        }]
	    });
}


/*获取资源消耗的列表*/
function getSourceUsedList() {
    $('#sourceUsedList').html("<b>正在努力加载...<b>");
    $.ajax({
        type: 'get',
        url: 'overviewAction/SourceUsedList',
        data: {},
        dataType: 'json',
        success: function (array) {
              if (array.length >= 1) {
                var tableStr = "";
                var obj = array[0];
                var totalCpu = obj.totalCpu;
                var usedCpu = obj.usedCpu;
                var leftCpu = obj.leftCpu;
                var cpuPercent = obj.cpuPercent;
                var totalMem = obj.totalMem;
                var usedMem = obj.usedMem;
                var leftMem = obj.leftMem;
                var memPercent = obj.memPercent;
                var obj1 = array[1];
                var totalDisk = obj1.totalDisk;
                var usedDisk = obj1.usedDisk;
                var leftDisk = obj1.leftDisk;
                var diskPercent = obj1.diskPercent;
                var mytr = '<tr >'
                    + '<td>CPU</td>'
                    + '<td>' + totalCpu + '    <b>核</b></td>'
                    + '<td>' + usedCpu + '    <b>核</b></td>'
                    + '<td>' + leftCpu + '    <b>核</b></td>'
                    + '<td>' + cpuPercent + '</td>'
                	+'</tr><tr >'
                    + '<td>内存</td>'
                    + '<td>' + totalMem + '    <b>G</b></td>'
                    + '<td>' + usedMem + '    <b>G</b></td>'
                    + '<td>' + leftMem + '    <b>G</b></td>'
                    + '<td>' + memPercent + '</td>'
                	+'</tr>><tr >'
                    + '<td>硬盘</td>'
                    + '<td>' + totalDisk + '    <b>G</b></td>'
                    + '<td>' + usedDisk + '    <b>G</b></td>'
                    + '<td>' + leftDisk + '    <b>G</b></td>'
                    + '<td>' + diskPercent + '</td>'
                	+'</tr>';
                tableStr =  mytr;    
                $('#sourceUsedList').html(tableStr);
                
                $("div[id='tab2_div_2']").show();
                //计算可创建主机
                $("#totalCpu").val(totalCpu);
            	$("#totalMem").val(totalMem);
                calculateFun();
            }
        }
    });
}
/*切换选项卡*/
$('.once-tab').on('click', '.tab-filter', function (event) {
    $('li', '.once-tab').removeClass('active');
    $(this).addClass('active');
    var type = getSwitchType();
   if(type==1){
	   $("div[id^='tab1_div']").show();
	   $("div[id^='tab2_div']").hide();
   }else if(type==2){
	   $("div[id^='tab1_div']").hide();
	   $("div[id='tab2_div_1']").show();
	   $("div[id='tab2_div_2']").hide();
	   getSourceUsedList();
   }
});

/*获取Tab页*/
function getSwitchType(){
	var switchtype = $('.once-tab').find('.active').attr("type");
	if(switchtype == 'general'){
		switchtype = 1;
	}else{
		switchtype = 2;
	}
	return switchtype;
}
/*计算可创建主机*/
function calculateFun(){
	var cpu = $("input:radio[name='cpuRadios']:checked").val();
	var mem = $("input:radio[name='memRadios']:checked").val();
	var totalCpu = $("#totalCpu").val();
	var totalMem = $("#totalMem").val();
	var temp=0;
	if(cpu!=0&&mem!=0){
		temp = totalCpu/cpu>totalMem/mem?totalMem/mem:totalCpu/cpu;
	}
	$("#createVM").val(Math.floor(temp));
}
