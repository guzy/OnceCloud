$('#delete').on('click', function(event) {
	event.preventDefault();
	var infoList = "";
	$('input[name="imagerow"]:checked').each(function() {
		infoList += "[" + $(this).parent().parent().attr("imageName")
				+ "]&nbsp;";
	});
	bootbox.dialog({
		message : '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;删除映像&nbsp;'
				+ infoList + '?</div>',
		title : "提示",
		buttons : {
			main : {
				label : "确定",
				className : "btn-primary",
				callback : function() {
					$('input[name="imagerow"]:checked').each(function() {
						deleteImage($(this).parent().parent().attr("imageUId"),
								$(this).parent().parent().attr("imageName"));
					});
					removeAllCheck();
				}
			},
			cancel : {
				label : "取消",
				className : "btn-default",
				callback : function() {
					removeAllCheck();
				}
			}
		}
	});
});

function deleteImage(imageId, imageName) {
	$.ajax({
				type : 'post',
				url : '/ImageAction/Delete',
				data : {
					imageId : imageId,
					imageName : imageName
				},
				dataType : 'json',
				success : function(obj) {
					if (obj.result) {
						var thistr = $("#tablebody").find('[imageUId="'
								+ imageId + '"]');
						$(thistr).remove();
					}
				}
			});
}