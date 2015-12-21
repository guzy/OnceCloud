$("#VolumeModalContainer").on("hidden", function() {
			$(this).removeData("modal");
			$(this).children().remove();
		});

$("#slider").slider({
			min : 10,
			max : 1000,
			step : 10,
			slide : function(event, ui) {
				$("#size").val(ui.value);
				var vsize = parseInt(ui.value);
				
			}
		});

$("#size").on('focusout', function(event) {
			event.preventDefault();
			var vsize = 10;
			if (/^[1-5]{0,1}[0-9]{0,1}[0-9]{1}$/.test($('#size').val())) {
				vsize = parseInt($('#size').val());
				if (vsize < 10) {
					$("#size").val(10);
				}
				if (vsize > 500) {
					$("#size").val(500);
				}
			} else {
				$("#size").val(10);
			}
			$("#slider").slider("option", "value", parseInt($('#size').val()));
			var price = (vsize / 10 * 0.02).toFixed(2);
			$("#price-hour").text(price);
		});

$("#count").on('focusout', function(event) {
			event.preventDefault();
			$("#hour-number").text($('#count').val());
		});

$("#createinfo-form").validate({
			rules : {
				volume_name : {
					required : true,
					maxlength : 20,
					legal : true
				},
				count : {
					required : true,
					digits : true
				}
			},
			messages : {
				volume_name : {
					required : "<span class='unit'>名称不能为空</span>",
					maxlength : "<span class='unit'>名称不能超过20个字符</span>",
					legal : "<span class='unit'>名称包含非法字符</span>"
				},
				count : {
					required : "<span class='unit'>数量不能为空</span>",
					digits : "<span class='unit'>数量必须是整数</span>"
				}
			}
		});

$("#createvolumeAction").on('click', function(event) {
			event.preventDefault();
			var valid = $("#createinfo-form").valid();
			if (valid) {
				var volumeName = $('#volume_name').val();
				var volumeCount = parseInt($('#count').val(), 10);
				var volumeSize = parseInt($('#size').val(), 10);
				var volumeTotalSize = volumeSize * volumeCount;
				for (var i = 0; i < volumeCount; i++) {
					var volumeuuid = uuid.v4();
					createVolume(volumeuuid, volumeName, volumeSize);
				}
				$('#VolumeModalContainer').modal('hide');
			}
		});

function createVolume(volumeuuid, volumeName, volumeSize) {
	var showid = "vol-" + volumeuuid.substring(0, 8);
	var basePath = basePath;
	var backupStr = '<a class="glyphicon glyphicon-camera backup" url="'
			+ basePath + 'user/create/createsnapshot.jsp?rsid=' + volumeuuid
			+ '&rstype=instance&rsname=' + volumeName + '"></a>';
	var showstr = "<a class='id'>" + showid + '</a>';
	$("#tablebody")
			.prepend('<tr rowid="'
					+ volumeuuid
					+ '"><td class="rcheck"><input type="checkbox" name="volumerow"></td><td>'
					+ showstr
					+ '</td><td name="volumename">'
					+ volumeName
					+ '</td><td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">创建中</span></td><td vmuuid=""></td><td name="size">'
					+ volumeSize.toFixed(2) + '</th><td name="backuptime">'
					+ backupStr
					+ '</td><td name="createtime" class="time"><1分钟</td></tr>');
	$.ajax({
				type : 'post',
				url : '/VolumeAction/CreateVolume',
				data : {
					volumeUuid : volumeuuid,
					volumeName : volumeName,
					volumeSize : volumeSize
				},
				dataType : 'json',
				success : function(obj) {
				}
			});
}