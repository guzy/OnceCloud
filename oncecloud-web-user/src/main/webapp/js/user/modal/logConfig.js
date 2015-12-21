
$('#confirm').on('click', function (event) {
	var days = $('#log-save-day').val();
	if(days == '' || days == 0){
		bootbox.alert('日志保存时限不能为0或者空');
		return;
	}else if(days < 0){
		bootbox.alert('日志保存时限不能小于0');
	}else if(days > 65536){
		bootbox.alert('日志保存时限不能大于65536');
	}
	$.ajax({
		type: 'post',
		async: false,
        url: '/LogAction/configSaveTime',
        data: {days : 10},
        dataType: 'json',
        success: function (flag) {
        	bootbox.alert('日志保存时限配置成功');
        	$('#LogModalContainer').modal('hide');
        }
	});
});
