$(document).ready(function () {
    $('#startHaAction').on('click', function (event) {
        /*event.preventDefault();
        if ($("#create-form").valid()) {
                $.ajax({
                    type: 'post',
                    url: '',
                    data: {
                    	poolUuid: $("#modalcontent").attr("poolid"),
                    	masterIP:$("#master_ip").val(),
                    	haPath:$("#ha_path").val(),
                    },
                    dataType: 'json',
                    complete: function (data) {
                      console.log(data);
             		   alert(data.responseText);
                    }
                });
            } */
    });
    
    $('#closeHaAction').on('click', function (event) {
        event.preventDefault();
        /*if ($("#create-form").valid()) {
           
                $.ajax({
                    type: 'post',
                    url: 'PoolAction/StopHa',
                    data: {
                    	poolUuid: $("#modalcontent").attr("poolid"),
                    	masterIP:$("#master_ip").val(),
                    	haPath:$("#ha_path").val(),
                    },
                    dataType: 'json',
                    complete: function (data) {
                    	
                		   alert(data.responseText);
           
                    }
                });
            } */
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