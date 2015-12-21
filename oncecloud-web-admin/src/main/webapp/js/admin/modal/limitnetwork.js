$(document).ready(function () {
    $('#limitNetwork').on('click', function (event) {
        event.preventDefault();
        if ($("#net-form").valid()) { 
        var vifUuid;
        var vmUuid;
    	$('input[name="vifrow"]:checked').each(function() {
    		vifUuid = $(this).parent().parent().attr("rowid");
    			});  
    	$('input[name="vmrow"]:checked').each(function() {
    		vmUuid = $(this).parent().parent().attr("rowid");
    			}); 
            
            $.ajax({
                type: 'post',
                url: 'VMAction/LimitNetwork',
                data: {
                	netSpeed:$("#netSpeed").val(),
                	netchoice:$("#selnet").val(),
                	vifUuid:vifUuid,
                	vmUuid:vmUuid
                },
                dataType: 'json',
                success: function (data) {
                }
            });
           $('#ModifyModalContainer').modal('hide');
        }
    });
    $("#net-form").validate({
        rules: {
        	netSpeed: {
                required: true,
                digits: true,
                range:[100,10000],
                
            }
        },
        messages: {
        	netSpeed: {
                required: "<span class='help'>请填写数值（单位：Mbps）</span>",
                digits: "<span class='help'>请填写整数</span>",
                range:"<span class='help'>限速范围100Mbps-10000Mbps</span>",
            }
        }
    });
   
   
});
