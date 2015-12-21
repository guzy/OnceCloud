(function () {
	 var pooluuid = $('input[name="imagerow"]:checked').eq(0).attr("pooluuid");
	 var images = "";
	 $('input[name="imagerow"]:checked').each(function() {
	 	images += $(this).parents("tr").attr("imageUId") + ",";
	 });
	 images = images.substring(0, images.length - 1);
	 $.ajax({
	 	type: "post",
	 	url: "ImageAction/ShareImageList",
	 	data: {pooluuid:pooluuid,images:images},
	 	dataType: "json",
	 	success: function(obj) {
	 		if (obj.length > 0) {
		 		$.each(obj, function(index, json) {
		 			$("#htypool").append('<option value="'+json.pooluuid+'">'+json.poolname+'</option>');
		 		});
	 		} else {
	 			$("#htypool").append('<option value="empty">无符合条件的资源池</option>');
	 		}
	 	}
	 });
})();

$("#poolshareimage").on("click", function(event) {
	event.preventDefault();
	var sorpooluuid = $('input[name="imagerow"]:checked').eq(0).attr("pooluuid");
	var images = "";
	$('input[name="imagerow"]:checked').each(function() {
		images += $(this).parents("tr").attr("imageUId") + ",";
	});
	images = images.substring(0, images.length - 1);
	var despooluuid = $("#htypool").find("option:selected").val();
	$.ajax({
		type: "post",
	 	url: "ImageAction/ImageShare",
	 	data: {sorpooluuid:sorpooluuid,despooluuid:despooluuid,images:images},
	 	dataType: "json",
	 	success: function(response) {
	 		var tableStr = "";
	 		$.each(response, function(index, obj) {
				var imageName = decodeURIComponent(obj.imagename);
				var imageUId = obj.imageid;
				var imageSize = obj.imagesize;
				var imageplatform = decodeURIComponent(obj.imageplatform);
				var createDate = obj.createDate;
				var showid = "img-" + imageUId.substring(0, 8);
				$('#image-area').text("可见范围");
				var typeStr = "公有";
				var poolName = obj.poolname;
				var stateStr = '<td><span class="icon-status icon-running" name="stateicon">'
						+ '</span><span name="stateword">可用</span></td>';
				var ref = obj.reference;
				if (ref == null) {
					ref = 0;
				}
				var mytr = '<tr imageUId="'
						+ imageUId
						+ '" imageName="'
						+ imageName
						+ '" imageType="system"><td class="rcheck"><input type="checkbox" name="imagerow" imageRef="'
						+ ref + '" pooluuid="' + obj.pooluuid + '"></td>'
						+ '<td><a class="id">' + showid + '</a></td><td><a>'
						+ imageName + '</a></td><td>' + imageSize + '</td><td>'
						+ imageplatform + '</td>' + stateStr + '<td>' + typeStr
						+ '</td><td class="time">' + createDate + '</td><td>' + poolName + '</td></tr>';
				tableStr = tableStr + mytr;
			});
			$('#tablebody').prepend(tableStr);
	 		removeAllCheck();
	 	}
	});
	 $('#ImageModalContainer').modal('hide');
});