$(function(){
	$('#confirm').on('click', function(event){
		
		var uuid = $('#modalcontent').attr('instanceUuid');
		var vmip =	getJoinIP('modify_IP');
		var vmnetmask = getJoinIP('modify_netmask');
		var vmgateway = getJoinIP('modify_gateway');
		var vmdns = getJoinIP('modify_dns');
//		alert(vmip+"==="+vmnetmask+"==="+vmgateway+"==="+vmdns);
		
		//验证网关地址
		var isvmgateway=false;
		if(isIP(vmgateway)){
			isvmgateway=true;
		}else{
			if("..."==vmgateway){
				vmgateway="";
				isvmgateway=true;
			}else{
				bootbox.alert("您输入的网关地址不合法，请重新输入");
			}
		}
		
		//验证DNS地址
		var isdns=false;
		if(isIP(vmdns)){
			isdns=true;
		}else{
			if(vmdns=="..."){
				vmdns="";
				isdns=true;
			}else{
				bootbox.alert("您输入的DNS地址不合法，请重新输入");
			}
		}
		
		//验证ip和子网掩码
		if(isIP(vmip)){
			if(isNetmask(vmnetmask)){
				if(isvmgateway&&isdns){
					$.ajax({
						type : 'post',
						url : '/VMAction/MountVmip',
						data : {
							uuid:uuid, 
							vmip:vmip,
							vmnetmask:vmnetmask,
							vmgateway:vmgateway,
							vmdns:vmdns
							},
						dataType : 'json',
						success : function(obj) {
							bootbox.alert(decodeURIComponent(obj.message));
							$('#IpModalContainer').modal('hide');
						}
					});
				}
			}else{
				bootbox.alert("您输入的子网掩码不合法，请重新输入");
				//$('#modify_netmask :text').val("");
			}
		}else{
			bootbox.alert("您输入的ip不合法，请重新输入");
			$('#modify_IP :text').val("");
		}
		
	});
	
});




function IsNum(e) {
    var k = window.event ? e.keyCode : e.which;
    if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
    } else {
        if (window.event) {
            window.event.returnValue = false;
        }
        else {
            e.preventDefault(); //for firefox 
        }
    }
}

function getJoinIP(id){
	var arr=[];
	$("#"+id).find(":text").each(function(i){
		arr[i]=$(this).val();
	})
	return arr.join(".");
}


function isIP(ip) {
	var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/; 
	var reg = ip.match(exp); 
	if(reg == null){
		return false;
	}else{
		return true;
	}
}

function isNetmask(netMask) {
	var exp=/^255\.255\.(255|0)\.(255|0)$/; 
	var reg = netMask.match(exp); 
	if(reg == null){
		return false;
	}else{
		return true;
	}
}