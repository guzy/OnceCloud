
/*$("#create-form").validate({
    rules: {
    	usbname: {
            required: true,
            digits: true,
            max:4096,
            
        },
        usbname: {
            required: true,
            digits: true,
            max:4096,
            
        }
    },
    messages: {
    	usbname: {
            required: "<span class='help'>请填写Vlan号</span>",
            digits: "<span class='help'>请填写整数</span>",
            max:"<span class='help'>必须小于等于4096</span>",
        }
    }
});*/

$(function(){
	loadhostusbList();
	
	$('#createUsb').click(function(){
		var uuid=$.trim($('#createUsbDiv').attr('uuid'));
		var type=$.trim($('#createUsbDiv').attr('mtype'));
		var usb_address=$('#vm_usb').val();
		 $.ajax({
		    	type: 'post',
		    	url: "VMAction/createUSB",
		    	data: {vmuuid:uuid,type:type,address:usb_address},
		    	dataType: 'json',
		    	success: function(obj) {
		    		if(obj.result){
		    			bootbox.alert('挂载usb成功！');
		    		}else{
		    			bootbox.alert('挂载usb失败！');
		    		}
		    		$('#createUsbContainer').modal('hide');
	    			getusblist();
		    	}
		    });
	})
})

function loadhostusbList() {
	var uuid=$.trim($('#createUsbDiv').attr('uuid'));
	var type=$.trim($('#createUsbDiv').attr('mtype'));
    $.ajax({
        type: 'get',
        async: false,
        url: 'VMAction/hostUSBlist',
        data:{vmuuid:uuid,type:type},
        dataType: 'json',
        success: function (array) {
            var vmlistHtml = '';
            if (0 == array.length) {
                vmlistHtml = '<option>没有USB可挂载</option>';
                $('#vm_usb').attr('disabled', true).addClass('oc-disable');
                $('#vm_usb').html(vmlistHtml);
                $('#createUsb').attr('disabled', true);
                $('#createUsb').addClass('btn-forbidden');
            } else {
                $('#vm_usb').attr('disabled', false);
                $('#createUsb').attr('disabled', false).removeClass('oc-disable');
                $('#createUsb').removeClass('btn-forbidden');
                for (var i = 0; i < array.length; i++) {
            		vmlistHtml = vmlistHtml + '<option value="' + array[i].key + '">'
            		+ array[i].key+ '</option>';
                }
                $('#vm_usb').html(vmlistHtml);
            }
        }
    });
}