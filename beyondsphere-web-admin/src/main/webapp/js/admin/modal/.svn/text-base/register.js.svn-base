$("#registercommit").on('click', function(event) {
			var ip = "";
			var hostUuid = "";
			$('input[name="hostrow"]:checked').each(function() {
						ip = $.trim($.trim($(this).parents('tr').attr("hostip")));
						hostUuid = $.trim($(this).parents('tr').attr("hostid"));
					});
			var key = $.trim($("#register_key").val());
			$.ajax({
						type : 'post',
						url : 'HostAction/Register',
						data : {
							key:key,
							ip :ip,
							hostUuid:hostUuid
						},
						dataType : 'json',
						success : function(obj) {
							reloadList(1);
						}
					});
			$('#ServerModalContainer').modal('hide');
		});