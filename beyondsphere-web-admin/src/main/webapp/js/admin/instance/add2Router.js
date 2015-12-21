load_function();

function load_function(){
	var rowid=$(':input[name="vmrow"]:checked').parent().parent().attr('rowid');
    $('#modalvmid').val(rowid);
	//显示第一页
	showpage(0);
	//加载路由器列表
	loadRouterList();
}


//加载路由器列表
function loadRouterList() {
    var vnetid = '';
    $('input[name="switchrow"]:checked').each(function () {
        vnetid = $(this).parent().parent().attr("id");
    });
    $('#modalcontent').attr('vnetid', vnetid);
    $.ajax({
        type: 'get',
        async: false,
        url: 'RouterAction/TableRTs',
        dataType: 'json',
        success: function (array) {
            var vmlistHtml = '';
            if (0 == array.length) {
                vmlistHtml = '<option>没有路由器可连接</option>';
                $('#vn_router').attr('disabled', true).addClass('oc-disable');
                $('#vn_router').html(vmlistHtml);
            } else {
                $('#vn_router').attr('disabled', false);
                
                for (var i = 0; i < array.length; i++) {
                    vmlistHtml = vmlistHtml + '<option value="' + array[i].uuid + '">rt-'
                        + array[i].uuid.substring(0, 8) + '&nbsp;(' + decodeURIComponent(array[i].rtname) + ')</option>';
                }
                $('#vn_router').html(vmlistHtml);
                var routerid= $.trim($('#vn_router').val());
                loadVxlanList(routerid);
            }
        }
    });
}

function loadVxlanList(routerid){
	$('#vxlanlist').html("");
	 $.ajax({
	        type: 'get',
	        async: false,
	        url: 'NetworkAction/Vxlanlist',
	        data:{
	        	routerid:routerid
	        },
	        dataType: 'json',
	        success: function (array) {
	            var vmlistHtml = '';
	            if (0 == array.length) {
	            	$('#one_next').attr("disabled",true);
	            } else {
	            	$('#one_next').attr("disabled",false);
	                for (var i = 0; i < array.length; i++) {
	                	var obj=array[i];
	                    vmlistHtml += '<div net='+obj.net+' gat='+obj.gate+' netid='+obj.netid+' class="image-item">Vxlan-'+obj.vlanid+'</div>';
	                }
	                $('#vxlanlist').html(vmlistHtml);
	                selectVxlan($('#vxlanlist>div').eq(0));
	                $('#vxlanlist>div').on('click',function(){
	                	selectVxlan($(this));
	                });
	            }
	        }
	    });
}
//选择路由后加载Vxlan列表
$('#vn_router').change(function(){
	var routerid= $.trim($(this).val());
    loadVxlanList(routerid);
});


//第一页下一步
$('#one_next').click(function(){
	showpage(1);
});
$('#two_up').click(function(){
	showpage(0);
});
//提交按钮
$('#insaddRouter').click(function(){
	var $vxlan=$('#vxlanlist>.selected');
	if($vxlan.length==0){
		return fase;
	}else{
		var vmid=$('#modalvmid').val();
		var routerid= $.trim($('#vn_router').val());
		var net=$vxlan.attr('net');
		var gate=$vxlan.attr('gat');
		var ip_end=$.trim($('#ip_end').val());
		var mask_end=$.trim($('#mask_end').val());
		var netid=$vxlan.attr('netid');
		
		 $.ajax({
		        type: 'post',
		        url: 'VMAction/add2Router',
		        data:{
		        	vmuuid:vmid,
		        	routerid:routerid,
		        	netid:netid,
		        	ip:'192.168.'+net+'.'+ip_end,
		        	netmask:'255.255.255.'+mask_end,
		        	gateway:'192.168.'+net+'.'+gate
		        },
		        dataType: 'json',
		        success: function (flag) {
		        	if(flag){
		        		$('#add2RouterModalContainer').modal('hide');
		        		alert("主机加入路由成功！");
		        	}else{
		        		alert("主机加入路由失败！");
		        	}
		        }
		 });
	}
});




function showpage(nom){
	$('#wizard>div').not(nom).hide();
	$('#wizard>div').eq(nom).show();
	$('#wizard>ol>li').removeClass('active').eq(nom).addClass('active');
}

function selectVxlan(obj){
	$('#vxlanlist>div').removeClass("selected");
	$(obj).addClass("selected");
	$(':input[name="net_val"]').val($(obj).attr('net'));
	$(':input[name="gate_val"]').val($(obj).attr('gat'));
}