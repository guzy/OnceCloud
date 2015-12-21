reloadList(1);
var haflag = false;
function reloadList(page) {
	var limit = $('#limit').val();
	var search = $('#search').val();
	getPoolList(page, limit, search);
	if (page == 1) {
		options = {
			currentPage : 1
		};
		$('#pageDivider').bootstrapPaginator(options);
	}
}

function removeAllCheck() {
	var boxes = document.getElementsByName("poolrow");
	for (var i = 0; i < boxes.length; i++) {
		boxes[i].checked = false;
		$(boxes[i]).change();
	}
}

/*启用高可用发生变化的事件*/
$('#ha-box').on('change',function(event){
	if($(this).is(':checked')){
		if(!haflag){
			showHamanager(haflag);
		}
		if($('#powerstate').val()=="1"){
			$('#ha-service').removeAttr('disabled');
		}
	}else{
		showHamanager(haflag);
		$('#ha-service').attr('disabled', "disabled");
	}
});

/*启用高可用的信息*/
function showHamanager(haflag){
	//添加电源管理校验的信息
	var pooluuid = '';
	var hostNumOfPool = 0;
    $('input[name="poolrow"]:checked').each(function () {
    	pooluuid = $(this).parent().parent().attr("poolid");
    });
    $.ajax({
    	type: 'post',
        url: 'HostAction/HostNumofPool',
        async: false,
        data: {
        	poolUuid: pooluuid,
        },
        dataType: 'json',
        success: function (num) {
        	hostNumOfPool = num;
        }
    });
    if(hostNumOfPool > 1){
    	$.ajax({
            type: 'post',
            url: 'HaAction/PoolPower',
            data: {
            	poolUuid: pooluuid,
            },
            dataType: 'json',
            success: function (power) {
            	$('#powerstate').val(power.result);
            	if(power.result==1){
            		$('#PoolModalContainer').attr('type', 'hamanager');
            		$('#PoolModalContainer').load('admin/modal/pool/hamanager', '', function() {
            			if(haflag){
            				$('#startHaAction').hide();
            				$('#closeHaAction').show();
            			}else{
            				$('#startHaAction').show();
            				$('#closeHaAction').hide();
            			}
            			$('#PoolModalContainer').modal({
            				backdrop : false,
            				show : true
            			});
            		});
            	}
            }
        });
    }else{
    	alert('资源池中只有一台服务器，不能开启HA功能');
    }
    
}

/*选择表格多选框事件*/
function chk(obj){  
     var boxArray = document.getElementsByName('poolrow');  
     var manager = document.getElementsByName("ha-manager");
     var count=0;
     for(var i=0;i<=boxArray.length-1;i++){  
          if(boxArray[i]==obj && obj.checked){  
             boxArray[i].checked = true;  
             $("#ha-box").removeAttr('disabled');
             if(manager[i].lastChild.innerHTML=='已启用'){
            	 $('#ha-box').prop("checked",true);
            	 $('#ha-service').removeAttr('disabled');
            	 haflag = true;
             }else{
            	 $('#ha-box').prop("checked",false);
            	 $('#ha-service').attr('disabled', "disabled");
            	 haflag = false;
             }
          }else{  
        	 count++;
             boxArray[i].checked = false;  
          }  
     }   
     if(count == boxArray.length){
    	 $('#ha-box').prop("checked",false);
    	 $('#ha-box').attr('disabled', "disabled");
    	 $('#ha-service').attr('disabled', "disabled");
     }
}  

/*获取资源池列表*/
function getPoolList(page, limit, search) {
	$.ajax({
		type : 'get',
		url : 'HaAction/PoolList',
		data : {
			page : page,
			limit : limit,
			search : search
		},
		dataType : 'json',
		success : function(array) {
			if (array.length >= 1) {
				var totalnum = array[0];
				var totalp = Math.ceil(totalnum / limit);
				if (totalp == 0) {
					totalp == 1;
				}
				options = {
					totalPages : totalp
				};
				$('#pageDivider').bootstrapPaginator(options);
				pageDisplayUpdate(page, totalp);
				initPoolList(array);
			}
		}
	});
}

/*初始化资源池表格*/
function initPoolList(pools){
	var btable = document.getElementById("tablebody");
	var tableStr = '';
	var accessControlFlag = '';
	for (var i = 1; i < pools.length; i++) {
		var obj = pools[i];
		var poolname = decodeURIComponent(obj.poolname);
		var pooldesc = decodeURIComponent(obj.pooldesc);
		var poolid = obj.poolid;
		var mastername = decodeURIComponent(obj.mastername);
		var createdate = obj.createdate;
		var dcuuid = obj.dcuuid;
		var hastartflag = obj.hastartflag;
		var hostmonitor = obj.hostmonitor;
		var accessflag = obj.accessflag;
		var lefthost = obj.lefthost;
		var slotpolicy = obj.slotpolicy;
		var slotcpu = obj.slotcpu
		var slotmemory = obj.slotmemory;
		var cpupercent = obj.cpupercent
		var memorypercent = obj.memorypercent;
		var migratoryhostid = obj.migratoryhostid;
		
		//高可用启用标志与文字转换
		hastartflag = signToWords(hastartflag);
		//主机监控标志与文字转换
		hostmonitor = signToWords(hostmonitor);
		//接入控制策略与文字的转换
		accessControlFlag = accessPolicyToWords(accessflag);
		
		var dcname = obj.dcname;
		if (dcuuid == "") {
			statestr = '<td class="pod" state="unload"></td>';
		} else {
			statestr = '<td class="pod" state="loaded">'
					+ decodeURIComponent(dcname) + '</td>';
		}
		var totalcpu = obj.totalcpu;
		var totalmem = Math.round(obj.totalmem / 1024);
		var cpustr = totalcpu + "";
		if (totalcpu != 0) {
			cpustr = cpustr + "&nbsp;核";
		}
		var memstr = totalmem + "";
		if (totalmem != 0) {
			memstr = memstr + "&nbsp;GB";
		}
		var showid = "pool-" + poolid.substring(0, 8);
		var mytr = '<tr poolid="'
				+ poolid
				+ '" poolname="'
				+ poolname
				+ '" pooldesc="'
				+ pooldesc
				+ '" dcid="'
				+ dcuuid
				+ '" accessflag="'
				+ accessflag
				+ '" lefthost="'
				+ lefthost
				+ '" slotpolicy="'
				+ slotpolicy
				+ '" slotcpu="'
				+ slotcpu
				+ '" slotmemory="'
				+ slotmemory
				+ '" cpupercent="'
				+ cpupercent
				+ '" memorypercent="'
				+ memorypercent
				+ '" migratoryhostid="'
				+ migratoryhostid
				+ '"><td class="rcheck"><input type="checkbox" name="poolrow" onclick="chk(this);"></td>'
				+ '<td><a class="id">' + showid + '</a></td>'
				+ '<td>' + poolname + '</td>'
				+ '<td>' + mastername + '</td>' 
				+ statestr 
				+ '<td>' + cpustr + '</td>'
				+ '<td>' + memstr + '</td>'
				+ '<td name="ha-manager">'+ hastartflag +'</td>'
				+ '<td>'+ hostmonitor +'</td>'
				+ '<td>'+ accessControlFlag +'</td>'
				+ '<td class="time">' + createdate + '</td></tr>';
		tableStr = tableStr + mytr;
	}
	btable.innerHTML = tableStr;
}

/*启用标志转化成文字*/
function signToWords(sign){
	var words = ''
	if(sign%2 == 0){
		words = '<span class="icon-status icon-stopped" name="stateicon"></span>'
			+ '<span name="stateword">未启用</span>';
	}else{
		words = '<span class="icon-status icon-running" name="stateicon"></span>'
			+ '<span name="stateword">已启用</span>';
	}
	return words;
}


/*控制策略代码转换成文字*/
function accessPolicyToWords(accessflag){
	var accessControlFlag = '';
	switch(accessflag){
		case 0:
			accessControlFlag = '默认接入控制策略';
			break;
		case 1:
			accessControlFlag = '预留静态主机数量';
			break;
		case 2:
			accessControlFlag = '预留百分比资源';
			break;
		case 3:
			accessControlFlag = '切换专用故障主机';
			break;
		default:
			accessControlFlag = '未启用接入控制策略';
			break;
	
	}
	return accessControlFlag;
}

/*主机监控事件*/
$('#host-monitor').on('click', function(event) {
	event.preventDefault();
	$('#PoolModalContainer').attr('type', 'monitor');
	$('#PoolModalContainer').load($(this).attr('url'), '', function() {
				$('#PoolModalContainer').modal({
							backdrop : false,
							show : true
						});
			});
});

/*接入控制按钮事件*/
$('#access-control').on('click', function(event) {
	event.preventDefault();
	$('#PoolModalContainer').attr('type', 'access');
	$('#PoolModalContainer').load($(this).attr('url'), '', function() {
		$('#PoolModalContainer').modal({
			backdrop : false,
			show : true
		});
	});
});





