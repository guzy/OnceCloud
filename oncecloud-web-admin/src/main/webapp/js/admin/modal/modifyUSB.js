getusblist();

//获取usb设备列表
function getusblist() {
	var type = $("#modalcontent").attr("mtype");
	var uuid = $("#modalcontent").attr("uuid");
	$("#usbbody").html("");
	$.ajax({
		type : "post",
		url : "VMAction/UsbList",
		data : {
			uuid : uuid,
			type : type
		},
		dataType : "json",
		success : function(array) {
			var tableStr = "";
			$.each(array, function(index, obj) {
				var thistr = '<tr rowid="' + obj.vbduuid + '">';
				thistr += '<td class="rcheck"><input type="checkbox" name="usbrow"></td><td name="device">'
						+ "USB-"+obj.vbduuid.substring(0,8)
						+ '</td><td name="mac">'
						+ obj.device
						+ '</td><td name="tag">'
						+ obj.type
						+ '</td><td name="physical">'
						+ obj.address
						+ '</td></tr>';
				tableStr += thistr;
			});
			$('#usbbody').html(tableStr);
		}
	});
}

$("#refresh").on("click", function() {
	allDisable();
	getusblist();
});

function allDisable() {
	$("#delete").attr("disabled", true);
}

function removeAllCheck() {
	$('input[name="vusbrow"]:checked').each(function() {
		$(this)[0].checked = false;
		$(this).change();
	});
}

$('#usbbody').on('change', 'input:checkbox', function(event) {
	event.preventDefault();
	allDisable();
	var total = 0;
	$('input[name="usbrow"]:checked').each(function() {
		total++;
	});
	if (total == 1) {
		$("#delete").attr("disabled", false);
	}
});

$("#create").on("click", function(event) {
	event.preventDefault();
	var uuid=$.trim($('#modalcontent').attr('uuid'));
	var type=$.trim($('#modalcontent').attr('mtype'));
	$('#createUsbContainer').load('usb/create', {"uuid":uuid,"type":type}, function() {
		$('#createUsbContainer').modal({
			backdrop : false,
			show : true
		});
	});
});


$("#delete").on("click", function(event) {
	event.preventDefault();
	var type = $("#modalcontent").attr("mtype");
	var uuid = $("#modalcontent").attr("uuid");
	var vbdUuid;
	$('input[name="usbrow"]:checked').each(function() {
		vbdUuid = $(this).parent().parent().attr("rowid");
	});
	$.ajax({
		type : "post",
		url : "VMAction/DeleteUSB",
		data : {
			vmuuid : uuid,
			type : type,
			usbuuid : vbdUuid
		},
		dataType : "json",
		success : function(obj) {
			if (obj) {
				alert("删除成功！");
				getusblist();
			}
		}
	});
});