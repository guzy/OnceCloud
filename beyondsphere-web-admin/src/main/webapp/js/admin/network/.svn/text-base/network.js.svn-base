changeModelDiv(3);

/*页面初始化信息*/
function vlan_reloadList(page) {
    var limit = $("#limit").val();
    var search = $('#search').val();
    var type = getSwitchType();
    getNetworkList(page, limit, search, type);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
}

/*取消所有的多选框*/
function removeAllCheck() {
    $('input[name="switchrow"]').each(function () {
        $(this).attr("checked", false);
        $(this).change();
    });
}

/*选择表格多选框事件*/
$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='switchrow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='switchrow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});

function chk(obj){  
     var boxArray = document.getElementsByName('switchrow');  
     for(var i=0;i<=boxArray.length-1;i++){  
          if(boxArray[i]==obj && obj.checked){  
             boxArray[i].checked = true;  
          }else{  
             boxArray[i].checked = false;  
          }  
     }   
}  

/*获取网络的列表*/
function getNetworkList(page, limit, search, type) {
    $('#vlan_tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'NetworkAction/NetworkList',
        data: { page: page, limit: limit, search: search, type: type},
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
                var showid = '';
                for (var i = 1; i < array.length; i++) {
                    var obj = array[i];
                    var id = obj.netId;
                    var switchId = obj.switchId;
                    var switchType = obj.switchType;
                    var switchStatus = obj.switchStatus;
                    var vlanrouter="";
                    var routerid="";
                    if(obj.vlanRouter!=undefined||obj.vlanRouter==''){
                    	vlanrouter="rt-"+obj.vlanRouter.substring(0,8);
                    	routerid=obj.vlanRouter;
                    }
                    
                    var createTime = obj.createTime;
                    switchStatus = signToWords(switchStatus);
                    
                    var routerStr="";
                    if(switchType%2 == 0){
                    	$('#th_router').show();
                    	showid = "Vxlan-" + switchId;
                    	routerStr='<td>' + vlanrouter + '</td>';
                    }else{
                    	$('#th_router').hide();
                    	showid = "vlan-" + switchId;
                    }
                    
                    switchType = typeToWords(switchType);
                    var mytr = '<tr id='+id 
                        + ' switchType="'+switchType+'" routerid="'+routerid+'" showid="'+showid+'">'
                        + '<td class="rcheck"><input type="checkbox" name="switchrow"></td>'
                        + '<td><a class="id">' + showid + '</a></td>'
                        + '<td>' + switchStatus + '</td>'
                        + '<td>' + switchType + '</td>'
                        + routerStr
                        + '<td class="time">' + createTime + '</td>'
                    mytr += '</tr>';
                    tableStr = tableStr + mytr;
                }
                $('#vlan_tablebody').html(tableStr);
            }
        }
    });
}

/*启用标志转化成文字*/
function signToWords(sign){
	var words = ''
	if(sign%2 == 0){
		words = '<span class="icon-status icon-stopped" name="stateicon"></span>'
			+ '<span name="stateword">未启用</span>';
	}else{
		words = '<span class="icon-status icon-running" name="stateicon"></span>'
			+ '<span name="stateword">使用中</span>';
	}
	return words;
}

/*虚拟机类型转化成文字*/
function typeToWords(sign){
	var words = ''
	if(sign%2 == 0){
		words = '虚拟扩展局域网（Vxlan）';
	}else{
		words = '虚拟局域网（vlan）';
	}
	return words;
}

/*切换选项卡*/
$('.once-tab').on('click', '.tab-filter', function (event) {
    $('li', '.once-tab').removeClass('active');
    $(this).addClass('active');
    var type=getSwitchType();
   changeModelDiv(type);
});

/*创建交换机*/
$('#create').on('click', function (event) {
    event.preventDefault();
    var type=getSwitchType();
    if(type==3){
    	$('#RouterModalContainer').load('router/create', '', function () {
            $('#RouterModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
    }else{
    	$('#NetworkModalContainer').load('network/create', '', function () {
    		$('#NetworkModalContainer').modal({
    			backdrop: false,
    			show: true
    		});
    	});
    }
});

/*删除交换机*/
$('#delete').on('click', function(event){
	$('input[name="switchrow"]:checked').each(function () {
		var switchid = $(this).parent().parent().attr('id');
		var showid = $(this).parent().parent().attr('showid');
		//判断vlan是否有用户已使用
		var checkSwitch=false;
		if(switchid==1||switchid==2){
			alert("默认虚拟局域网不允许删除！");
			return;
		}else{
			$.ajax({
				type: 'get',
				url: "HostInformation/checkSwitch",
				async:false,
				dataType: 'json',
				data: {
						netid : switchid 
					   },
			    success: function(obj){
			    	if(obj.result){
			    		checkSwitch=true;
			    	}else{
			    		alert(showid+"已被用户"+obj.username+"使用，不能删除！");
			    	}
				}
			});
		}
		
		//如果验证通过，执行删除操作
		if(checkSwitch){
			$.ajax({
				type: 'post',
				url: "NetworkAction/destory",
				async:false,
				dataType: 'json',
				data: {
						netid : switchid 
					   },
			    complete: function(data){
				}
			});
		}
    });
	reloadSwitchList(1);
});


/*刷新列表*/
function reloadSwitchList(page){
	var limit = $("#limit").val();
    var search = $('#search').val();
    var type = getSwitchType();
	getNetworkList(page, limit, search, type);
}

/*获取交换机的类型*/
function getSwitchType(){
	var switchtype = $('.once-tab').find('.active').attr("type");
	if(switchtype == 'general'){
		switchtype = 1;
	}else if(switchtype=='distributed'){
		switchtype = 2;
	}else if(switchtype=='router'){
		switchtype = 3;
	}
	return switchtype;
}
function changeModelDiv(type){
	    $('#once-tab-title').css('text-indent','25px');
	    if(type ==1){
	    	$('#once-tab-title').text('虚拟局域网（vlan）是一组逻辑上的设备和用户，这些设备和用户并不受物理位置的限制，可以根据功能、部门及应用等因素将它们组织起来，'
	    			+ '相互之间的通信就好像它们在同一个网段中一样。私有网络之间是100%隔离的,但是需要物理交换机配置。');
	    	$('#addvm').parent().show();
	    	$('#link').parent().hide();
	    	$('#unlink').parent().hide();
			$('#delete').parent().show();
			$('#startup').parent().hide();
			$('#shutdown').parent().hide();
			$('#destroy').parent().hide();
			$('#router_table').hide();
			$('#vlan_table').show();
			
			vlan_reloadList(1);
		}else if(type==2){
			$('#addvm').parent().show();
	    	$('#link').parent().show();
	    	$('#unlink').parent().show();
			$('#delete').parent().show();
			$('#startup').parent().hide();
			$('#shutdown').parent().hide();
			$('#destroy').parent().hide();
			$('#router_table').hide();
			$('#vlan_table').show();
			$('#once-tab-title').text('虚拟扩展局域网（Vxlan）可以帮助用户为虚拟机创建一个逻辑网路，并允许这个逻辑网路中的虚拟机跨网路进行通讯。Vxlan之间是100%隔离的。');
			
			vlan_reloadList(1);
		}else if(type==3){
			$('#addvm').parent().hide();
	    	$('#link').parent().hide();
	    	$('#unlink').parent().hide();
			$('#delete').parent().hide();
			$('#startup').parent().show();
			$('#shutdown').parent().show();
			$('#destroy').parent().show();
			$('#router_table').show();
			$('#vlan_table').hide();
			$('#once-tab-title').text('路由器（Routers）用于私有网络之间互联，并提供以下附加服务：DHCP 服务、端口转发。如果希望路由器能接入互联网，请为路由器绑定公网IP。');
			
			reloadList(1);
		}
}



