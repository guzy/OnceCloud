
reloadPoolInfos();

/*加载资源池*/
function reloadPoolInfos(){
	 if(getSwitchType() == 1){
		 $('#vlan-describition').text('加入到该私有网络的虚拟机能实现互联互通，前提是物理交换机要配置相应的truk。vlan的范围是1-4096');
		 $('#control-label').text('Vlan的ID');
		 $('#distributed').hide();
		 $('#switchid').show();
	 }else{
		 $('#vlan-describition').text('vxlan的范围是1-1048576');
		 $('#control-label').text('VXlan的ID');
		 $('#switchid').hide();
		 $('#distributed').show();
	 }
}

$("#createSwitch").on("click", function(){
	var valid = $("#create-form").valid();
	if(valid){
		var switchtype = getSwitchType();
		var switchid;
		if(switchtype == 1){
			switchid = $('#switchid').val();
		}else if(switchtype == 2){
			switchid = $('#distributed').val();
		}
		$.ajax({
			type: 'post',
			url: "NetworkAction/create",
			data: {
					switchtype : switchtype, 
					switchid : switchid, 
				   },
			dataType: 'json',
			complete: function(array){
				if(array.result == 'false'){
					bootbox.alert('vlan号已存在');
				}else{
					hideModal();
				    reloadSwitchList(1);
				}
			}
		});
	}
});

$("#cancel").on("click", function(){
	hideModal();
});

$("#create-form").validate({
	rules: {
		switchid: {
            required: true,
            digits: true,
            max:4096,
        },
        distributed: {
            required: true,
            digits: true,
            max:1048576,
        }
    },
    messages: {
    	switchid: {
            required: "<span class='help'>请填写交换机号</span>",
            digits: "<span class='help'>请填写整数</span>",
            max:"<span class='help'>必须小于等于4096</span>",
        },
        distributed: {
            required: "<span class='help'>请填写交换机号</span>",
            digits: "<span class='help'>请填写整数</span>",
            max:"<span class='help'>必须小于等于1048576</span>",
        }
    }
});

/*隐藏一个模态*/
function hideModal(){
	$('#NetworkModalContainer').modal('hide');
}


