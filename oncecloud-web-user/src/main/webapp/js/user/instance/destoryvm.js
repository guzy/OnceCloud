function getInfoList() {
	var infoList = "";
	$('input[name="vmrow"]:checked').each(function() {
		infoList += "[i-"
				+ $(this).parent().parent().attr("rowid").substring(0, 8)
				+ "]&nbsp;";
	});
	return infoList;
}

function deleteshow() {
	var infoList = getInfoList();
	var showMessage = '';
	var showTitle = '';
	showMessage = '<div class="alert alert-info" style="margin:10px">'
			+ '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;'
			+ '销毁主机&nbsp;' + infoList + '?</div>';
	showTitle = '提示';
	bootbox.dialog({
				className : "oc-bootbox",
				message : showMessage,
				title : showTitle,
				buttons : {
					main : {
						label : "确定",
						className : "btn-primary",
						callback : function() {
							$('input[name="vmrow"]:checked').each(function() {
								var uuid = $(this).parent().parent()
										.attr("rowid");
								destroyVM(uuid);
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
}

$('#destroy').on('click', function(event) {
			event.preventDefault();
			deleteshow();
		});

function destroyVM(uuid) {
	$('#status-check').prop('disabled', true);
	var thistr = $("#tablebody").find('[rowid="' + uuid + '"]');
	var thisicon = thistr.find('[name="stateicon"]');
	thisicon.removeClass("icon-stopped");
	thisicon.removeClass("icon-running");
	thisicon.addClass('icon-process');
	thistr.find('[name="stateword"]').text('销毁中');
	$.ajax({
		type : 'get',
		url : '/VMAction/DeleteVM',
		data : {
			uuid : uuid
		},
		dataType : 'json',
		complete : function(){
			$('#status-check').prop('disabled', false);
		}
	});
}

$('#tablebody').on('click', '.console', function(event) {
			event.preventDefault();
			var uuid = $(this).data("uuid");
			var vnc = $('#platformcontent').attr("novnc");
			var token = uuid.substring(0, 8);
			var url = vnc + "console.html?id=" + token;
			window.open(url, "novnc", 'height=600, width=810, top=0, left=0');
		});