$("#recovercommit").on('click', function(event) {
			var ip = "";
			var hostUuid = "";
			$('input[name="hostrow"]:checked').each(function() {
						ip = $(this).parents('tr').attr("hostip");
						hostUuid = $(this).parents('tr').attr("hostid");
					});
			var username = $("#host_username").val();
			var password = $("#host_pwd").val();
			var content = "<div class='alert alert-warning'>正在修复服务器</div>";
			var conid = showMessageNoAutoClose(content);
			$.ajax({
				type : 'post',
				url : 'HostAction/Recover',
				data : {
					ip : ip,
					username : username,
					password : password,
					content : content,
					conid : conid,
					hostUuid : hostUuid
				},
				dataType : 'json',
				success : function(obj) {

				}
			});
			$('#ServerModalContainer').modal('hide');
		});