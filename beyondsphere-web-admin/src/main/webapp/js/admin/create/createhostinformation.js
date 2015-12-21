$(document).ready(function () {
    $('#createInformation').on('click', function (event) {
        event.preventDefault();
            var vn_user = $("#vn_user").val();
            var vn_net = $("#vn_vlan").val();
            var ipStr="";
            for(var i=1;i<4;i++){
            	var ipsid='vn_'+i+'net';
            	ipStr+=$("#"+ipsid).val()+'.';
            }
            var ipSegment=ipStr+$("#vn_start").val()+'/'+$("#vn_end").val();
            var routerIp=ipStr+$("#ip_end").val();
            var netmask = "255.255.255."+$("#vn_gate").val();
            
            
            var flag=checkRouterip(ipStr.substring(0,ipStr.length-1));
            if(flag){
            	// alert("ipSegment:"+ipSegment+'   routerIp:'+routerIp+'   netmask:'+netmask);
            	$.ajax({
            		type: 'post',
            		url: 'HostInformation/saveInformation',
            		data: {
            			userId: vn_user,
            			netId: vn_net,
            			ipSegment: ipSegment,
            			routerIp: routerIp,
            			routerNetmask: netmask,
            		},
            		dataType: 'json',
            		success: function (obj) {
            			var content = "";
            			if (obj) {
            				content = "<div class='alert alert-warning'>录入信息成功！</div>";
            				$('#createInformation').off('click');
            				reloadList(1);
            			}else{
            				content = "<div class='alert alert-warning'>录入信息失败！</div>";
            			}
            			showMessageNoAutoClose(content);
            		}
            	});
            	$('#CreateInfoModalContainer').modal('hide');
            }else{
            	alert("ip段冲突，请重新输入");
            	return false;
            }
            
    });

    


    $("#vn_3net").blur(function (e) {
        var net = $(this).val();
        $('#vn_3net1').val(net);
    });

    $("#vn_1net").blur(function (e) {
        var net = $(this).val();
        $('#vn_1net1').val(net);
    });
    $("#vn_2net").blur(function (e) {
        var net = $(this).val();
        $('#vn_2net1').val(net);
    });

    function checkaddr(addr, num1, num2) {
        if (addr == "") {
            return false;
        }
        if ($.isNumeric(addr)) {
            if (addr >= num1 && addr <= num2) {
                return true;
            }
        }
        return false;
    }
    
    loadUserList();
    loadVlanList();
});

function loadUserList() {
    $.ajax({
        type: 'get',
        async: false,
        url: 'HostInformation/unBindUsers',
        dataType: 'json',
        success: function (array) {
            var vmlistHtml = '';
            if (0 == array.length) {
                vmlistHtml = '<option>没有用户可以选择</option>';
                $('#vn_user').attr('disabled', true).addClass('oc-disable');
                $('#vn_user').html(vmlistHtml);
            } else {
                $('#vn_user').attr('disabled', false);
                for (var i = 0; i < array.length; i++) {
                    vmlistHtml = vmlistHtml + '<option value="' + array[i].userid + '">'
                       + decodeURIComponent(array[i].username) + '</option>';
                }
                $('#vn_user').html(vmlistHtml);
            }
        }
    });
}

function loadVlanList() {
    $.ajax({
        type: 'get',
        async: false,
        url: 'NetworkAction/NetworkVlanList',
        dataType: 'json',
        success: function (array) {
            var vmlistHtml = '';
            if (0 == array.length) {
                vmlistHtml = '<option>没有Vlan可以选择</option>';
                $('#vn_vlan').attr('disabled', true).addClass('oc-disable');
                $('#vn_vlan').html(vmlistHtml);
            } else {
                $('#vn_vlan').attr('disabled', false);
                for (var i = 0; i < array.length; i++) {
                    vmlistHtml = vmlistHtml + '<option value="' + array[i].netid + '">Vlan-'
                       + array[i].vlanid + '</option>';
                }
                $('#vn_vlan').html(vmlistHtml);
            }
        }
    });
}

///展示一个提示
function showMessageNoAutoClose(content) {
   var showid = $.sticky(content,{'autoclose':false});
   arrayid = localStorage.getItem('alertStr');
   if(arrayid == null)
   {
      arrayid="";
   }
   setTimeout("",500);
   arrayid += '<div class="sticky" id="'
			+ showid.id
			+ '" style="height: 38px; display: block;"><span class="close sticky-close" rel="'
			+ showid.id + '" title="Close">×</span><div class="sticky-note" rel="'
			+ showid.id
			+ '">'+ content +'</div></div>---';
   localStorage.setItem('alertStr',arrayid);
   return showid.id;
}

//验证ip段是否重复
function checkRouterip(strip){
	var checkresult=false;
	   $.ajax({
	        type: 'post',
			async: false,
			url: 'RouterAction/checkRouterip',
			data:{
				routerip:strip
			},
			dataType: 'json',
	        success: function (obj) {
	        	checkresult=obj.result;
	        }
	   });
	   return checkresult;
}