initResourceList();

$('#createsnapshotAction').on('click', function(event) {
	event.preventDefault();
	var valid = $("#backup-form").valid();
	if (valid) {
		var resourceUuid = $("#platformcontent").attr("resourceUuid");
		if (resourceUuid != null) {
			var resourceType = $("#platformcontent").attr("resourceType");
			var snapshotid = uuid.v4();
			syncsnapshot(snapshotid, $("#snapshot_name").val(), resourceUuid,
					resourceType);
		} else {
			var vmitems = document.getElementsByName("vm-select-item");
			for (var i = 0; i < vmitems.length; i++) {
				var snapshotid = uuid.v4();
				snapshot(snapshotid, $("#snapshot_name").val(), $(vmitems[i])
								.attr('uuid'), "instance");
			}
			var volumeitems = document.getElementsByName("volume-select-item");
			for (var i = 0; i < volumeitems.length; i++) {
				var snapshotid = uuid.v4();
				snapshot(snapshotid, $("#snapshot_name").val(),
						$(volumeitems[i]).attr('uuid'), "volume");
			}
			$('#VolumeModalContainer').modal('hide');
			$('#InstanceModalContainer').modal('hide');
		}
	}
});

$("#backup-form").validate({
			rules : {
				snapshot_name : {
					required : true,
					maxlength : 20,
					legal : true
				}
			},
			messages : {
				snapshot_name : {
					required : "<span class='unit'>名称不能为空</span>",
					maxlength : "<span class='unit'>名称不能超过20个字符</span>",
					legal : "<span class='unit'>名称包含非法字符</span>"
				}
			}
		});

function getNewChain() {
	var newchain = 0;
	$('input[name="vmrow"]:checked').each(function() {
				if ($(this).parent().parent().find(".glyphicon-camera").size() > 0) {
					newchain++;
				}
			});
	$('input[name="volumerow"]:checked').each(function() {
				if ($(this).parent().parent().find(".glyphicon-camera").size() > 0) {
					newchain++;
				}
			});
	return newchain;
}

function initResourceList() {
	var resourceUuid = $("#platformcontent").attr("resourceUuid");
	var rsid = $("#modal-dialog").attr("rsid");
	if (resourceUuid != null) {
		var resourceType = $("#platformcontent").attr("resourceType");
		var resourceName = $("#platformcontent").attr("resourceName");
		if (resourceType == "instance") {
			var showuuid = "i-" + resourceUuid.substring(0, 8);
			$('#resource-list')
					.append('<div name="vm-select-item" uuid="'
							+ resourceUuid
							+ '"><label class="inline" style="margin:0; padding:0 !important"><span class="glyphicon glyphicon-cloud"></span>&nbsp;'
							+ resourceName + '&nbsp;<a>(' + showuuid
							+ ')</a></label></div>');
		} else {
			var showuuid = "vol-" + resourceUuid.substring(0, 8);
			$('#resource-list')
					.append('<div name="volume-select-item" uuid="'
							+ resourceUuid
							+ '"><label class="inline" style="margin:0; padding:0 !important"><span class="glyphicon glyphicon-inbox"></span>&nbsp;'
							+ resourceName + '&nbsp;<a>(' + showuuid
							+ ')</a></label></div>');
		}
	} else if (rsid != "null") {
		var rstype = $("#modal-dialog").attr("rstype");
		var rsname = $("#modal-dialog").attr("rsname");
		if (rstype == "instance") {
			var showuuid = "i-" + rsid.substring(0, 8);
			$('#resource-list')
					.append('<div name="vm-select-item" uuid="'
							+ rsid
							+ '"><label class="inline" style="margin:0; padding:0 !important"><span class="glyphicon glyphicon-cloud"></span>&nbsp;'
							+ rsname + '&nbsp;<a>(' + showuuid
							+ ')</a></label></div>');
		} else {
			var showuuid = "vol-" + rsid.substring(0, 8);
			$('#resource-list')
					.append('<div name="volume-select-item" uuid="'
							+ rsid
							+ '"><label class="inline" style="margin:0; padding:0 !important"><span class="glyphicon glyphicon-inbox"></span>&nbsp;'
							+ rsname + '&nbsp;<a>(' + showuuid
							+ ')</a></label></div>');
		}
	} else {
		$('input[name="vmrow"]:checked').each(function() {
			var vmuuid = $(this).parent().parent().attr("rowid");
			var showuuid = "i-" + vmuuid.substring(0, 8);
			var vmName = $(this).parent().parent().find('[name="vmname"]')
					.text();
			$('#resource-list')
					.append('<div name="vm-select-item" uuid="'
							+ vmuuid
							+ '"><label class="inline" style="margin:0; padding:0 !important"><span class="glyphicon glyphicon-cloud"></span>&nbsp;'
							+ vmName + '&nbsp;<a>(' + showuuid
							+ ')</a></label></div>');
		});
		removeAllCheck("vmrow");
		$('input[name="volumerow"]:checked').each(function() {
			var volumeuuid = $(this).parent().parent().attr("rowid");
			var volumeName = $(this).parent().parent()
					.find('[name="volumename"]').text();
			var showuuid = "vol-" + volumeuuid.substring(0, 8);
			$('#resource-list')
					.append('<div name="volume-select-item" uuid="'
							+ volumeuuid
							+ '"><label class="inline" style="margin:0; padding:0 !important"><span class="glyphicon glyphicon-inbox"></span>&nbsp;'
							+ volumeName + '&nbsp;<a>(' + showuuid
							+ ')</a></label></div>');
		});
		removeAllCheck("volumerow");
	}
}

function snapshot(snapshotId, snapshotName, resourceUuid, resourceType) {
	$.ajax({
		type : 'post',
		url : '/SnapshotAction/CreateSnapshot',
		data : {
			snapshotId : snapshotId,
			snapshotName : snapshotName,
			resourceUuid : resourceUuid,
			resourceType : resourceType
		},
		dataType : 'json',
		success : function(obj) {
			if (obj != null && obj != "" && obj.result) {
				var thistd = $("#tablebody").find('[rowid="'
						+ resourceUuid + '"]')
						.find('[name="backuptime"]');
				thistd.text(decodeURIComponent(obj.backupDate));
			}
		}
	});
}

function syncsnapshot(snapshotId, snapshotName, resourceUuid, resourceType) {
	$.ajax({
		type : 'post',
		url : '/SnapshotAction/CreateSnapshot',
		async : false,
		data : {
			snapshotId : snapshotId,
			snapshotName : snapshotName,
			resourceUuid : resourceUuid,
			resourceType : resourceType
		},
		dataType : 'json',
		success : function(msg) {
			getSnapshotDetailList();
			getSnapshotBasicList();
			$('#SnapshotModalContainer').modal('hide');
		}
	});
}

function getSnapshotDetailList() {
	var resourceUuid = $("#platformcontent").attr("resourceUuid");
	var resourceType = $("#platformcontent").attr("resourceType");
	var btable = document.getElementById("snapshot-graph");
	btable.innerHTML = "";
	$.ajax({
		type : 'get',
		url : '/SnapshotAction/DetailList',
		data : {
			resourceUuid : resourceUuid,
			resourceType : resourceType
		},
		dataType : 'json',
		success : function(msg) {
			if (msg != null && msg != "") {
				var array = msg;
				var height = 16 + 32 * array.length;
				var svgStr = '<svg width="40" height="' + height
						+ '"><g transform="translate(20,16)">';
				var listStr = '<div class="snapshot-list" id="snapshot-list">';
				listStr = listStr
						+ '<a class="new-snapshot" id="backup" url="../create/createsnapshot.jsp"><span class="tip">新建备份</span></a>';
				for (var i = 0; i < array.length; i++) {
					var obj = array[i];
					var snapshotId = obj.snapshotId;
					var snapshotName = decodeURIComponent(obj.snapshotName);
					var snapshotSize = obj.snapshotSize;
					var backupDate = obj.backupDate;
					var showid = "ss-" + snapshotId.substring(0, 8);
					var title = '<ul snapshotId="' + snapshotId
							+ '" snapshotName="' + snapshotName + '">';
					if (i == 0) {
						svgStr = svgStr
								+ '<path d="M0,16L0,-10" class="is-head"></path><circle id="'
								+ showid + '" r="5" cx="0" cy="16"></circle>';
					} else {
						var current = 32 * i - 10;
						var step = 16 + 32 * i;
						svgStr = svgStr + '<path d="M0,' + current + 'L0,'
								+ step + '"></path><circle id="' + showid
								+ '" r="5" cx="0" cy="' + step + '"></circle>';
					}
					if (i % 2 == 1) {
						title = '<ul snapshotId="' + snapshotId
								+ '" class="odd">';
					}
					listStr = listStr
							+ title
							+ '<li class="background"></li><li class="id"><span>'
							+ showid + '</span></li><li class="time">'
							+ backupDate + '</li><li>'
							+ snapshotSize.toFixed(2)
							+ '&nbsp;GB</li><li><span>' + snapshotName
							+ '</span>&nbsp;</li></ul>';
				}
				btable.innerHTML = svgStr + '</g></svg>' + listStr + '</div>';
			}
		}
	});
}

function getSnapshotBasicList() {
	var resourceUuid = $("#platformcontent").attr("resourceUuid");
	var resourceType = $("#platformcontent").attr("resourceType");
	$("#basic-list").html("");
	$.ajax({
		type : 'get',
		url : '/SnapshotAction/BasicList',
		data : {
			resourceUuid : resourceUuid,
			resourceType : resourceType
		},
		dataType : 'json',
		success : function(obj) {
			if (obj != "") {
				var snapshotCount = obj.snapshotCount;
				var snapshotSize = obj.snapshotSize;
				var backupDate = obj.backupDate;
				var showid = "ss-" + resourceUuid.substring(0, 8);
				var showname;
				if (resourceType == "instance") {
					showname = '<span class="glyphicon glyphicon-cloud"></span>&nbsp;&nbsp;主机';
				} else {
					showname = '<span class="glyphicon glyphicon-inbox"></span>&nbsp;&nbsp;硬盘';
				}
				$("#basic-list")
						.html('<dt>备份链&nbsp;ID</dt><dd><a href="javascript:void(0)">'
								+ showid
								+ '</a></dd><dt>状态</dt><dd><span class="icon-status icon-running" name="stateicon"></span><span name="stateword">可用</span></dd>'
								+ '<dt>资源类型</dt><dd>'
								+ showname
								+ '</dd><dt>总量</dt><dd>'
								+ snapshotSize.toFixed(2)
								+ '&nbsp;GB</dd><dt>备份点</dt><dd>'
								+ snapshotCount
								+ '&nbsp;个</dd><dt>距上次备份时间</dt><dd>'
								+ decodeURIComponent(backupDate) + '</dd>');
			}
		}
	});
}

function removeAllCheck(namerow) {
	$('input[name="' + namerow + '"]:checked').each(function() {
				$(this)[0].checked = false;
				$(this).change();
			});
}
