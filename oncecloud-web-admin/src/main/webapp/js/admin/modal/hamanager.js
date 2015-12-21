$(document).ready(function () {

    fillBlank();
    function fillBlank() {
	    var poolid = '';
	    var poolname = '';
	    $('input[name="poolrow"]:checked').each(function () {
	    	poolid = $(this).parent().parent().attr("poolid");
	    	poolname = $(this).parent().parent().attr("poolname");
	    });
	    $('#modalcontent').attr('poolid', poolid);
	
	    $('#pool_uuid').val("pool-"+poolid.substring(0, 8));
	    $('#pool_name').val(poolname);
	    
	    $.ajax({
	        type: 'post',
	        url: 'HaAction/PoolHa',
	        data: {
	        	poolUuid: poolid,
	        },
	        dataType: 'json',
	        success: function (poolha) {
	        	$("#master_ip").val(poolha.masterip);
	        	$("#ha_path").val(poolha.hapath);
	        }
	    });
    }

    $('#startHaAction').on('click', function (event) {
        event.preventDefault();
        if ($("#create-form").valid()) {
                $.ajax({
                    type: 'post',
                    url: 'HaAction/StartHa',
                    data: {
                    	poolUuid: $("#modalcontent").attr("poolid"),
                    	masterIP:$("#master_ip").val(),
                    	haPath:$("#ha_path").val(),
                    },
                    dataType: 'json',
                    complete: function (data) {
                      console.log(data);
             		  reloadList(1);
                    }
                });
            } 
    });
    
    $('#closeHaAction').on('click', function (event) {
        event.preventDefault();
        if ($("#create-form").valid()) {
                $.ajax({
                    type: 'post',
                    url: 'HaAction/StopHa',
                    data: {
                    	poolUuid: $("#modalcontent").attr("poolid"),
                    	masterIP:$("#master_ip").val(),
                    	haPath:$("#ha_path").val(),
                    },
                    dataType: 'json',
                    complete: function (data) {
            		   $('#ha-box').prop("checked",false);
                       $('#ha-box').attr('disabled', "disabled");
                  	   $('#ha-service').attr('disabled', "disabled");
            		   reloadList(1);
                    }
                });
            } 
    });

    $('#cancel').on('click',function (event){
    	if($('#startHaAction').is(":visible")==true){
    		$('#ha-box').prop("checked",false);
        	$('#ha-service').attr('disabled', "disabled");
    	}else{
    		$('#ha-box').prop("checked",true);
        	$('#ha-service').removeAttr('disabled', "disabled");
    	}
    	
    });

    $("#create-form").validate({
        rules: {
        	ha_path: {
                required: true,
            }
        },
        messages: {
        	ha_path: {
                required: "<span class='help'>请填写高可用路径</span>",
            }
        }
    });
});