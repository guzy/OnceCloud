$(document).ready(function () {
    $('#startAcAction').on('click', function (event) {
        event.preventDefault();
        if ($("#time-form").valid()) {      	       	
            $('#PoolModalContainer').modal('hide');
            keepAccordance(1);
    		$("#accordance").text("关闭一致性");
    		$("#accordance").attr("isac", "true");
    		$("#accordance").attr("class","btn btn-danger");
           
            } 
    });
    $("#time-form").validate({
        rules: {
        	ac_time: {
                required: true,
                digits: true,
                min:30
            }
        },
        messages: {
        	ac_time: {
                required: "<span class='help'>请填写时间（单位：秒）</span>",
                digits: "<span class='help'>请填写整数</span>",
                min: "<span class='help'>请输入大于30的整数</span>"
            }
        }
    });
});
