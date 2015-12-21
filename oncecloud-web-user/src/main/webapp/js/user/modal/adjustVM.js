$('.cpu').on('click', '.cpu-options', function (event) {
    event.preventDefault();
    $('div', $('.cpu')).removeClass('selected');
    $(this).addClass('selected');
});

$('.memory').on('click', '.memory-options', function (event) {
    event.preventDefault();
    if (!$(this).hasClass('disabled')) {
        $('div', $('.memory')).removeClass('selected');
        $(this).addClass('selected');
    }
});

$('#adjust-vm').on('click', function (event) {
	event.preventDefault();
	var uuid = "";
	$('input[name="vmrow"]:checked').each(function () {
    	uuid = $(this).parent().parent().attr("rowid");
    });
    var cpu = $('.cpu').find('.selected').attr("core");
    var mem = $('.memory').find('.selected').attr("capacity");
    var content = "<div class='alert alert-warning'>正在更改主机配置</div>";
    var conid = showMessageNoAutoClose(content);
    $.ajax({
    	type: 'post',
    	url: "/VMAction/VMAdjust",
    	data: {uuid:uuid, cpu:cpu, mem:mem, content:content, conid:conid},
    	dataType: "json",
    	success: function(obj) {
    		if (obj) {
//				reloadList(1);
    		}
    	}
    });
    $('#InstanceModalContainer').modal('hide');
});