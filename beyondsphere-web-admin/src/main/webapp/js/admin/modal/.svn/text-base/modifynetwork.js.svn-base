getmaclist();

function getmaclist() {
	var type = $("#modalcontent").attr("mtype");
	var uuid = $("#modalcontent").attr("uuid");
	$("#netbody").html("");
	$.ajax({
		type : "post",
		url : "VMAction/MacList",
		data : {
			uuid : uuid,
			type : type
		},
		dataType : "json",
		success : function(obj) {
			var tableStr = "";
			$.each(obj, function(index, json) {
				var thistr = '<tr rowid="' + json.vifuuid + '">';
				thistr += '<td class="rcheck"><input type="checkbox" name="vifrow"></td><td name="device">'
						+ json.device
						+ '</td><td name="mac">'
						+ json.mac
						+ '</td><td name="tag">'
						+ json.tag
						+ '</td><td name="physical">'
						+ json.physical
						+ '</td></tr>';
				tableStr += thistr;
			});
			$('#netbody').html(tableStr);
		}
	});
}

$("#refresh").on("click", function() {
			allDisable();
			getmaclist();
		});

function allDisable() {
	$("#delete").attr("disabled", true);
	$("#modifyvlan").attr("disabled", true);
	$("#modifynet").attr("disabled", true);
	$("#limitnetwork").attr("disabled", true);
}

function removeAllCheck() {
	$('input[name="vifrow"]:checked').each(function() {
				$(this)[0].checked = false;
				$(this).change();
			});
}

$('#netbody').on('change', 'input:checkbox', function(event) {
			event.preventDefault();
			allDisable();
			var total = 0;
			$('input[name="vifrow"]:checked').each(function() {
						total++;
					});
			var isrun = $("#modalcontent").attr("isrun");
			if (isrun == "run" && total == 1) {
				$("#modifyvlan").attr("disabled", false);
				$("#delete").attr("disabled", false);
				$("#limitnetwork").attr("disabled", false);
			} else if (isrun == "stop" && total == 1) {
				$("#modifynet").attr("disabled", false);
			}
		});

$("#create").on("click", function(event) {
			event.preventDefault();
			$('#ModifyModalContainer').load('mac/create', '', function() {
						$('#ModifyModalContainer').modal({
									backdrop : false,
									show : true
								});
					});
		});

$("#modifyvlan").on("click", function(event) {
			event.preventDefault();
			$('#ModifyModalContainer').load('admin/modal/joinvlan', '', function() {
				$('#ModifyModalContainer').modal({
					backdrop : false,
					show : true
				});
			});
		});

/*$("#modifynet").on("click", function(event) {
			event.preventDefault();
			$("#modalcontent").attr("modify", "physical");
			$('#ModifyModalContainer').load('mac/create', '', function() {
						$('#ModifyModalContainer').modal({
									backdrop : false,
									show : true
								});
					});
		});*/

$("#limitnetwork").on("click", function(event) {
	event.preventDefault();
	$('#ModifyModalContainer').load('admin/modal/limitnetwork', '', function() {
				$('#ModifyModalContainer').modal({
							backdrop : false,
							show : true
						});
			});
});

$("#delete").on("click", function(event) {
			event.preventDefault();
			var type = $("#modalcontent").attr("mtype");
			var uuid = $("#modalcontent").attr("uuid");
			var vifUuid;
			$('input[name="vifrow"]:checked').each(function() {
						vifUuid = $(this).parent().parent().attr("rowid");
					});
			$.ajax({
						type : "post",
						url : "VMAction/DeleteMac",
						data : {
							uuid : uuid,
							type : type,
							vifUuid : vifUuid
						},
						dataType : "json",
						success : function(obj) {
							if (obj) {
								getmaclist();
							}
						}
					});
		});