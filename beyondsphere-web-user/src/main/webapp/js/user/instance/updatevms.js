
/*===========instancec.jsp=========================*/
function getInfoList() {
    var infoList =[];
    $('input[name="vmrow"]:checked').each(function (i) {
        infoList[i]=$(this).parent().parent().attr("rowid");
    });
    
    return infoList;
}

$('#updateVMs').on('click', function () {
    updateVMs();
});

function updateVMs() {
	var vmids=getInfoList();
	 var url = $("#platformcontent").attr('platformBasePath') + 'user/modal/updateVMsip';
	 $('#InstanceModalContainer').load(url, {vmids:vmids.join(",")}, function () {
	        $('#InstanceModalContainer').modal({
	            backdrop: false,
	            show: true
	        });
	        load_function();
	    });
}



/*===========updateVMsip.jsp=========================*/
function load_function(){
	changeIP_end();
}

$('#confirm').click(function(){
	var vmids=$('#modalcontent').attr('instanceUuid');
	var ips=getJoinIP('modify_ip').split('.');
	
	var vmip=ips.splice(0,3).join(".");
	var ip_start=$.trim($('#ip_end1').val());
	var ip_end=$.trim($('#ip_end2').val());
	
	var netmask=getJoinIP('modify_netmask');
	var gateway=getJoinIP('modify_gateway');
	var dns=getJoinIP('modify_dns');
//	alert("ip:"+vmip+"netmask:"+netmask+"gateway:"+gateway+"dns:"+dns);
	
	$('#InstanceModalContainer').modal('hide');
	removeAllCheck();
	$.ajax({
		type : 'post',
		url : '/VMAction/updateVmips',
		data : {
			uuids:vmids, 
			vmip:vmip,
			ipstart:ip_start,
			ipend:ip_end,
			vmnetmask:netmask,
			vmgateway:gateway,
			vmdns:dns
			},
		dataType : 'json',
		success : function(obj) {
			if(obj){
				bootbox.alert("批量修改ip成功！");
			}else{
				bootbox.alert("批量修改ip失败！");
			}
		}
	});
})
$('#ip_end1').on('focusout', function() {
	changeIP_end();
});


function getJoinIP(id){
	var arr=[];
	var $inputs=$("#"+id).find(":text");
	if($inputs.length>0){
		$("#"+id).find(":text").each(function(i){
			var val=$.trim($(this).val());
			if(val!=''){
				arr[i]=$(this).val();
			}
		})
	}
	return arr.join(".");
}

//ip最后一位赋值
function changeIP_end(){
	var vmids=$('#modalcontent').attr('instanceUuid');
	var ip_end1=Number($.trim($('#ip_end1').val()));
	var num=0;
	if(vmids!=""){
		num=vmids.split(',').length;
	}
	$('#ip_end2').val(ip_end1+num-1);
}
