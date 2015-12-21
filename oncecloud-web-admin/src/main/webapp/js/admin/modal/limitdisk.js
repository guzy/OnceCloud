$(document).ready(function () {
	getDiskLimit();
	
	function getDiskLimit(){
		var vmUuid;
		$('input[name="vmrow"]:checked').each(function() {
			vmUuid = $(this).parent().parent().attr("rowid");
			});        
		$.ajax({
            type: 'post',
            url: 'VMAction/GetDiskLimit',
            data: {
            	vmUuid: vmUuid
            },
            dataType: 'json',
            success: function (result) {
            	if (result.readM == "-1") {
            		$("#readM").text("100");
            	} else {
            		$("#readM").text(result.readM);
            	}
            	if (result.readi == "-1") {
            		$("#readi").text("100");
            	} else {
            		$("#readi").text(result.readi);
            	}
            	if (result.writeM == "-1") {
            		$("#writeM").text("100");
            	} else {
            		$("#writeM").text(result.writeM);
            	}
            	if (result.writei == "-1") {
            		$("#writei").text("100");
            	} else {
            		$("#writei").text(result.writei);
            	}
            }
        });
	}
    $('#limitDisk').on('click', function (event) {
        event.preventDefault(); 
        if ($("#disk-form").valid()) { 
        var vmUuid;
    	$('input[name="vmrow"]:checked').each(function() {
    		vmUuid = $(this).parent().parent().attr("rowid");
    			});  
           
            $.ajax({
                type: 'post',
                url: 'VMAction/LimitDisk',
                data: {
                	speed:$("#speed").val(),
                	type:$("#type").val(),
                	ioType:$("#io_type").val(),
                	vmUuid:vmUuid
                },
                dataType: 'json',
                success: function (data) {
                }
            });
            $('#InstanceModalContainer').modal('hide');
        }
    });
    $("#disk-form").validate({
        rules: {
        	speed: {
                required: true,
                digits: true,
                min:50,
                max:1000,
                
            }
        },
        messages: {
        	speed: {
                required: "<span class='help'>请填写数值（单位：Mbps）</span>",
                digits: "<span class='help'>请填写整数</span>",
                min:"<span class='help'>限速范围不小于50Mbps</span>",
                max:"<span class='help'>限速范围超出物理机上限</span>",
            }
        }
    });
    
   
});
