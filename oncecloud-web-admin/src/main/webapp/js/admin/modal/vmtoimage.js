$(document).ready(function () {
    $('#confirm').on('click', function (event) {
    	event.preventDefault();
    	var uuid;
    	$('input[name="vmrow"]:checked').each(function () {
        	uuid = $(this).parents("tr").attr("rowid");
    	});
    	$.ajax({
    		type: "post",
    		url: "VMAction/VMToTemplate",
    		data: {uuid:uuid},
    		dataType: "json",
    		success: function (obj) {
    			if(obj) {
    				$('#InstanceModalContainer').modal('hide');
    				$('input[name="vmrow"]:checked').each(function () {
    					$(this).parents('tr').remove();
    				});
    			}
    		}
    	});
    });
    

});