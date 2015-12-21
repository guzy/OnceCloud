$("#updateImage").on('click', function(event) {
    event.preventDefault();
	var pwd = $("#imagepwd").val();
	var disk = $("#imagedisk").val();
	var desc = $("#imagedesc").val();
	var platform = $("#imageplatform option:selected").val();
	var uuid;
	$('input[name="imagerow"]:checked').each(function () {
		uuid = $(this).parents('tr').attr("imageUId");
	});
	
	$.ajax({
		type: "post",
		url: "ImageAction/ImageUpdate",
		data: {uuid:uuid, pwd:pwd, disk:disk, desc:desc, platform:platform},
		dateType: "json",
		success: function(obj) {
			if(obj) {
				 $('#ImageModalContainer').modal('hide');
				 bootbox.dialog({
                    message: '<div class="alert alert-success" style="margin:10px"><span class="glyphicon glyphicon-ok"></span>&nbsp;修改成功</div>',
                    title: "提示",
                    buttons: {
                        main: {
                            label: "确定",
                            className: "btn-primary",
                            callback: function () {
                            	reloadList(1);
                            }
                        }
                    }
                });
			}
		}
	});
});