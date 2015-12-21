$("#InstanceModalContainer").on("hidden", function() {
			$(this).removeData("modal");
			$(this).children().remove();
		});

$("#wizard").bwizard({
			backBtnText : "",
			nextBtnText : ""
		});

getPoolList();

$('.li-disable').unbind();
$('ol').removeClass("clickable");
$('.hidden-phone').css("cursor", "default");
$('.hidden-phone').attr("href", "javascript:void(0)");

$('.btn-back').on('click', function(event) {
			event.preventDefault();
			$("#wizard").bwizard("back");
		});

$('.btn-next').on('click', function(event) {
			event.preventDefault();
			if ($("#volumelist").html() == "") {
				getVolumeList();
			}
			$("#wizard").bwizard("next");
		});

$('.btn-second-next').on('click', function(event) {
			event.preventDefault();
			if ($("#htyiso").html() != "")
				$("#wizard").bwizard("next");
		});

$('.btn-first-next').on('click', function(event) {
			event.preventDefault();
			if ($('#basicinfo-form').valid()) {
				getISOList();
				$("#wizard").bwizard("next");
			}
		});

function getPoolList() {
	$('#poollist').html("");
	$.ajax({
		type : 'post',
		url : 'PoolAction/AllPool',
		dataType : 'json',
		success : function(array) {
			var tableStr = "";
			$.each(array, function(index, obj) {
						var pooluuid = obj.poolid;
						var poolname = decodeURIComponent(obj.poolname);
						if (index == 0) {
							tableStr = tableStr
									+ '<div class="pool-item selected" pooluuid="'
									+ pooluuid + '">' + poolname + '</div>';
							$("#htypool").html(poolname);
							$("#htypool").attr("pooluuid", pooluuid);
						} else {
							tableStr = tableStr
									+ '<div class="pool-item" pooluuid="'
									+ pooluuid + '">' + poolname + '</div>';
						}

					});
			$('#poollist').html(tableStr);
		}
	});
}

$('.poollist').on('click', '.pool-item', function(event) {
			event.preventDefault();
			$('div', $('#poollist')).removeClass('selected');
			$(this).addClass('selected');
			var poolname = $(this).text();
			var pooluuid = $(this).attr("pooluuid");
			$("#htypool").html(poolname);
			$("#htypool").attr("pooluuid", pooluuid);
		});

$('#instance_name').on('focusout', function() {
			var name = $('#instance_name').val();
			if (name.length > 20) {
				name = name.substring(0, 20) + "...";
			}
			$('#htyinstance').html(name);
		});

function getISOList() {
	var poolUuid = $("#htypool").attr("pooluuid");
	$("#select-iso").hide();
	$("#loadinggif").show();
	$.ajax({
		type : "post",
		url : "VMAction/ISOList",
		data : {
			poolUuid : poolUuid
		},
		dataType : "json",
		success : function(array) {
			$("#loadinggif").hide();
			$("#select-iso").show();
			$("#isoselect").empty();
			$.each(array, function(index, json) {
				if (index == 0) {
					$("#htyiso").html(json.name);
					$("#htyiso").attr("isouuid", json.uuid);
				}
				$("#isoselect").append('<option value="'
						+ json.uuid + '">' + json.name
						+ '</option>');
			});
		}
	});
}

$("#isoselect").on("change", function() {
	$("#htyiso").html($("#isoselect option:selected").text());
	$("#htyiso").attr("isouuid", $(this).val());
});

$("#isotypeselect").on("change", function(){
	$("#isotype").html($("#isotypeselect option:selected").text());
	$("#isotype").attr("typevlaue", $(this).val());
});

$('#basicinfo-form').validate({
	rules : {
		instance_name : {
			required : true,
			maxlength : 20,
			legal : true
		}
	},
	messages : {
		instance_name : {
			required : "<span class='unit'>主机名称不能为空</span>",
			maxlength : "<span class='unit'>主机名称不能超过20个字符</span>",
			legal : "<span class='unit'>主机名称包含非法字符</span>"
		}
	}
});

$('.cpu').on('click', '.cpu-options', function(event) {
			event.preventDefault();
			$('div', $('.cpu')).removeClass('selected');
			$(this).addClass('selected');
			var cputext = $('.cpu').find('.selected').attr("core");
			$('#htycore').html(cputext + "&nbsp;核");
			$("#htycore").attr("cpu", cputext);
		});

$('.memory').on('click', '.memory-options', function(event) {
			event.preventDefault();
			$('div', $('.memory')).removeClass('selected');
			$(this).addClass('selected');
			var captext = $('.memory').find('.selected').attr("capacity");
			$('#htycap').html(captext + "&nbsp;G");
			$("#htycap").attr("cap", captext);
		});

function getVolumeList() {
	var poolUuid = $("#htypool").attr("pooluuid");
	$("#loadinggifdisk").show();
	$.ajax({
		type : "post",
		url : "StorageAction/RealSRList",
		data : {
			poolUuid : poolUuid
		},
		dataType : "json",
		success : function(array) {
			$("#loadinggifdisk").hide();
			var tableStr = "";
			$.each(array, function(index, json) {
						if (index == 0) {
							tableStr = tableStr
									+ '<div class="disk-item selected" diskuuid="'
									+ json.uuid + '">' + json.name + '('
									+ json.type + ')</div>';
							$("#htystorage").html(json.name);
							$("#htystorage").attr("diskuuid", json.uuid);
						} else {
							tableStr = tableStr
									+ '<div class="disk-item" diskuuid="'
									+ json.uuid + '">' + json.name + '('
									+ json.type + ')</div>';
						}
					});
			$('#volumelist').html(tableStr);
		}
	});
}

$('.volumelist').on('click', '.disk-item', function(event) {
			event.preventDefault();
			$('div', $('#volumelist')).removeClass('selected');
			$(this).addClass('selected');
			var diskname = $(this).text();
			var diskuuid = $(this).attr("diskuuid");
			$("#htystorage").html(diskname);
			$("#htystorage").attr("diskuuid", diskuuid);
		});

$("#slider").slider({
			min : 10,
			max : 200,
			step : 10,
			slide : function(event, ui) {
				$("#size").val(ui.value);
				var vsize = parseInt(ui.value);
				$("#htyvolum").text(vsize + "G");
				$("#htyvolum").attr("volum", vsize);
			}
		});

$("#size").on('focusout', function(event) {
			event.preventDefault();
			var vsize = 10;
			if (/^[1-2]{0,1}[0-9]{0,1}[0-9]{1}$/.test($('#size').val())) {
				vsize = parseInt($('#size').val());
				if (vsize < 10) {
					$("#size").val(10);
				}
				if (vsize > 200) {
					$("#size").val(200);
				}
			} else {
				$("#size").val(10);
			}
			$("#slider").slider("option", "value", parseInt($('#size').val()));
			$("#htyvolum").text($('#size').val() + "G");
			$("#htyvolum").attr("volum", $('#size').val());
		});

$('#createvmAction').on('click', function(event) {
	event.preventDefault();
	if ($("#htystorage").html() != "") {
		var name = $("#htyinstance").html();
		var volum = $("#htyvolum").attr("volum");
		var pooluuid = $("#htypool").attr("pooluuid");
		var isouuid = $("#htyiso").attr("isouuid");
		var isotype = $("#isotype").attr("typevlaue");
		var cpu = $("#htycore").attr("cpu");
		var cap = $("#htycap").attr("cap");
		var diskuuid = $("#htystorage").attr("diskuuid");
		var vmuuid = uuid.v4();
		preCreateVM(vmuuid, pooluuid, cpu, cap, name, isouuid, isotype, diskuuid, volum);
		$('#InstanceModalContainer').modal('hide');
	}
});

function preCreateVM(vmuuid, pooluuid, cpuCore1, memoryCapacity, vmName,
		isouuid, isotype, diskuuid, volum) {
	cpuCore = cpuCore1 + "&nbsp;核";
	var memoryInt = parseInt(memoryCapacity);
	var memoryStr = memoryInt + "&nbsp;GB";
	var showuuid = "i-" + vmuuid.substring(0, 8);
	var showstr = "<a class='id'>" + showuuid + '</a>';
	var basePath = basePath;
	var starArray = "";
	starArray += '<div id="star"><ul>';
	for (var j = 0; j < 5; j++) {
		starArray += '<li><a href="javascript:;"><span class="glyphicon glyphicon-star-empty"></span></a></li>';
	}
	$("#tablebody")
			.prepend('<tr rowid="'
					+ vmuuid
					+ '"><td class="rcheck"><input type="checkbox" name="vmrow"></td><td name="console">'
					+ showstr
					+ '</td><td name="userName">admin</td><td name="vmname">'
					+ vmName
					+ '</td><td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">创建中</span></td><td name="cpuCore">'
					+ cpuCore
					+ '</td><td name="memoryCapacity">'
					+ memoryStr
					+ '</td><td name="sip">vlan号-1</td><td name="createtime" class="time"><1分钟</td></tr>');
	createVM(vmuuid, pooluuid, cpuCore1, memoryCapacity, vmName, isouuid, isotype,
			diskuuid, volum);
}

function createVM(vmuuid, pooluuid, cpuCore1, memoryCapacity, vmName, isouuid, isotype,
		diskuuid, volum) {
	$('#status-check').prop('disabled', true);
	$.ajax({
		type : "post",
		url : "VMAction/ISOCreate",
		data : {
			pooluuid : pooluuid,
			cpu : cpuCore1,
			memory : memoryCapacity,
			vmName : vmName,
			isouuid : isouuid,
			isotype : isotype,
			vmUuid : vmuuid,
			diskuuid : diskuuid,
			volum : volum
		},
		dataType : "text",
		success : function() {
			$('#status-check').prop('disabled', false);
			reloadList(1);
		}
	});
}