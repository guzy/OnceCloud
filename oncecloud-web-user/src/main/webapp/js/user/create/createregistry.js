$("#RegistryModalContainer").on("hidden", function() {
	$(this).removeData("modal");
	$(this).children().remove();
});

$("#wizard").bwizard({
	backBtnText : "",
	nextBtnText : ""
});

var options = {
	bootstrapMajorVersion : 3,
	currentPage : 1,
	totalPages : 1,
	numberOfPages : 0,
	onPageClicked : function(e, originalEvent, type, page) {
		var type = $('.provider').find('.selected').attr("type");
		getImageList(page, 8, "", type);
		
	},

	shouldShowPage : function(type, page, current) {
		switch (type) {
			case "first" :
			case "last" :
				return false;
			default :
				return true;
		}
	}
};
$('#tplpage').bootstrapPaginator(options);
getImageList(1, 8, "");

$('.li-disable').unbind();
$('ol').removeClass("clickable");
$('.hidden-phone').css("cursor", "default");
$('.hidden-phone').attr("href", "javascript:void(0)");

function getImageList(page, limit, search) {
	$('#imagelist').html("");
	$.ajax({
		type : 'get',
		url : '/RegistryAction/registryRepoList',
		data : {
			page : page,
			limit : limit,
			search : search
		},
		dataType : 'json',
		success : function(array) {
			var tableStr = "";
			if (array.length >= 1) {
				var totalnum = array[0];
				var totalp = 1;
				if (totalnum != 0) {
					totalp = Math.ceil(totalnum / limit);
				}
				options = {
					totalPages : totalp
				};
				$('#tplpage').bootstrapPaginator(options);
				modalPageUpdate(page, totalp);
				for (var i = 1; i < array.length; i++) {
					var obj = array[i];
					var repouuid = obj.repoid;
					var reponame = obj.reponame;
					var repoversion = obj.repoversion;
					var repoaddress = obj.repoaddress;
					var repoUrl=repoaddress+"/"+reponame+":"+repoversion;
						if (repoversion != null) {
							tableStr = tableStr
									+ '<div class="image-item" imageid="'
									+ repoUrl + '">' + repoUrl + '</div>';
							var s = document.getElementById("selectedImage");
							s.innerHTML = repoUrl;
						} else {
							tableStr = tableStr
									+ '<div class="image-item" imageid="'
									+ repoUrl + '">' + repoUrl + '</div>';
						}
					}
				$('#imagelist').html(tableStr);
			}
		}
	});
}

$('.provider').on('click', '.provider-filter', function(event) {
	event.preventDefault();
	$('a', $('.provider')).removeClass('selected');
	$(this).addClass('selected');
	var type = $('.provider').find('.selected').attr("type");
	getImageList(1, 8, "", type);
});

$('.imagelist').on('click', '.image-item', function(event) {
	event.preventDefault();
	$('div', $('#imagelist')).removeClass('selected');
	$(this).addClass('selected');
	$('#selectedImage').html($('#imagelist').find('.selected').text());
});

$('.btn-back').on('click', function(event) {
	event.preventDefault();
	$("#wizard").bwizard("back");
});

$('.btn-next').on('click', function(event) {
	event.preventDefault();
	var platform = $('#imagelist').find('.selected').attr("platform");
	if (platform == "WINDOWS") {
		$('#vmDefaultUser').val("Administrator");
		$('#vmDefaultUser').attr("disabled", true);
	} else {
		$('#vmDefaultUser').val("root");
	}
	$("#wizard").bwizard("next");
});

$('#createRegistryAction').on('click', function(event) {
	event.preventDefault();
	var valid = $('#basicinfo-form').valid();
	//var validateRegIP = "";
	var rega = $('#registry_ip_a').val();
	var regb = $('#registry_ip_b').val();
	var regc = $('#registry_ip_c').val();
	var regd = $('#registry_ip_d').val();
	var regIP = rega+"."+regb+"."+regc+"."+regd;
	//alert(regIP);
	if(!checkip(regIP)){
		$("#registry_ip").text("IP不合法");
		return;
	}else{
		$("#registry_ip").text("");
	}
	
	
	if (valid&&checkip(regIP)) {
		var imageUuid = $('.imagelist').find('.selected').attr("imageid");
		var regName = $('#registry_name').val();
	
		var regPort = $('#registry_port').val();
		$('#status-check').prop('disabled', true);
		$.ajax({
			type : 'post',
			url : '/RegistryAction/createRegistry',
			data : {
				imageUuid:imageUuid,
				regName:regName,
				regIP:regIP,
				regPort:regPort
			},
			dataType : 'json',
			complete : function(){
				$('#status-check').prop('disabled', false);
			}
		});
		$('#RegistryModalContainer').modal('hide');
	}
});

$('#instance_name').on('focusout', function() {
	var name = $('#instance_name').val();
	if (name.length > 20) {
		name = name.substring(0, 20) + "...";
	}
	$('#selectedName').html(name);
});

$('#count').on('focusout', function() {
	$('#selectedCount').html($('#count').val());
	priceDisplayUpdate();
});

$('#basicinfo-form').validate({
	rules : {
		registry_name : {
			required : true,
			maxlength : 20,
			legal : true
		}
		,
		registry_ip : {
			required : true,
			max:255,
			min:0
			
		}
        ,
		/*registry_ip_a : {
			required : true,
			max:255,
			min:0
			
		}
        ,
		registry_ip_b : {
			required : true,
			max:255,
			min:0
			
		}
        ,
		registry_ip_c : {
			required : true,
			max:255,
			min:0
			
		}
        ,
		registry_ip_d : {
			required : true,
			max:255,
			min:0
			
		}
        ,*/
		registry_port : {
			required : true,
			max:65535,
			min:0
		}
	},
	messages : {
		registry_name : {
			required : "<span class='unit'>名称不能为空</span>",
			maxlength : "<span class='unit'>名称不能超过20个字符</span>",
			legal : "<span class='unit'>名称包含非法字符</span>"
		}
	   ,
	   registry_ip : {
			required : "<span class='unit'>IP不能为空</span>",
			max : "<span class='unit'>IP地址不合法</span>",
			min : "<span class='unit'>IP地址不合法</span>"
		}
	   ,
		/*registry_ip_a : {
			required : "<span class='unit'>IP不能为空</span>",
			max : "<span class='unit'>IP只能为0~255</span>",
			min : "<span class='unit'>IP只能为0~255</span>"
		}
	   ,
		registry_ip_b : {
			required : "<span class='unit'>IP不能为空</span>",
			max : "<span class='unit'>IP只能为0~255</span>",
			min : "<span class='unit'>IP只能为0~255</span>"
		}
	   ,
		registry_ip_c : {
			required : "<span class='unit'>IP不能为空</span>",
			max : "<span class='unit'>IP只能为0~255</span>",
			min : "<span class='unit'>IP只能为0~255</span>"
		}
	   ,
		registry_ip_d : {
			required : "<span class='unit'>IP不能为空</span>",
			max : "<span class='unit'>IP只能为0~255</span>",
			min : "<span class='unit'>IP只能为0~255</span>"
		}
	   ,*/
		registry_port : {
			required : "<span class='unit'>端口号不能为空</span>",
			max : "<span class='unit'>端口号只能为0~65535</span>",
			min : "<span class='unit'>端口号只能为0~65535</span>"
		}
	}
});

function modalPageUpdate(current, total) {
	$('#currentPtpl').html(current);
	$('#totalPtpl').html(total);
}

function preCreateVM(vmuuid, imageuuid, cpuCore1, memoryCapacity, vmName,
		loginPwd) {
	cpuCore = cpuCore1 + "&nbsp;核";
	var memoryInt = parseInt(memoryCapacity);
	var memoryStr;
	if (memoryInt < 1) {
		memoryStr = "512&nbsp;MB";
	} else {
		memoryStr = memoryInt + "&nbsp;GB";
	}
	var showuuid = "i-" + vmuuid.substring(0, 8);
	var showstr = "<a class='id'>" + showuuid + '</a>';
	var backupStr = '<a class="glyphicon glyphicon-camera backup" url="'
			+ basePath + 'snapshot/create?rsid=' + vmuuid
			+ '&rstype=instance&rsname=' + vmName + '"></a>';
	$("#tablebody")
			.prepend('<tr rowid="'
					+ vmuuid
					+ '"><td class="rcheck"><input type="checkbox" name="vmrow"></td><td name="console">'
					+ showstr
					+ '</td><td name="vmname">'
					+ vmName
					+ '</td><td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">创建中</span></td><td name="cpuCore">'
					+ cpuCore
					+ '</td><td name="memoryCapacity">'
					+ memoryStr
					+ '</td><td name="mac"></td><td name="plateform"></td><td name="backuptime">'
					+ backupStr
					+ '</td><td name="createtime" class="time"><1分钟</td></tr>');
	createVM(vmuuid, imageuuid, cpuCore1, memoryCapacity, vmName, loginPwd);
}

function createVM(vmuuid, imageuuid, cpuCore, memoryCapacity, vmName, loginPwd) {
	$('#status-check').prop('disabled', true);
	$.ajax({
		type : 'post',
		url : '/ContainerAction/createContainer',
		data : {
			imageUuid : imageuuid,
			cpu : cpuCore,
			memory : memoryCapacity,
			vmName : vmName,
			password : loginPwd,
			vmUuid : vmuuid
		},
		dataType : 'json',
		complete : function(){
			$('#status-check').prop('disabled', false);
		}
	});
	
}

function priceDisplayUpdate() {
	var cpuCore = $('.cpu').find('.selected').attr("core");
	var memoryCapacity = $('.memory').find('.selected').attr("capacity");
	var vmCount = $('#count').val();
	updatePrice(cpuCore, memoryCapacity, vmCount);
}

function updatePrice(cpuCore, memoryCapacity, vmCount) {
}

$('.types').on('click', '.types-item', function(event) {
	event.preventDefault();
	var selindex = $(this).index();
	var core = new Array(0, 0, 1, 1, 2, 2);
	var mem = new Array(1, 2, 2, 3, 5, 6);
	$('.types-item', $('.types')).removeClass('selected');
	$(this).addClass('selected');
	$('div', $('.cpu')).removeClass('selected');
	$('.cpu-options').eq(core[selindex]).addClass('selected');
	$('#selectedCore').html($('.cpu').find('.selected').attr("core")
			+ "&nbsp;核");
	$('div', $('.memory')).removeClass('selected');
	$('.memory-options').eq(mem[selindex]).addClass('selected');
	$('#selectedCap').html($('.memory').find('.selected').attr("capacity")
			+ "&nbsp;G");
	$('#selectedType').html($(this).find(".type-name").text());
});

$('.cpu').on('click', '.cpu-options', function(event) {
	event.preventDefault();
	// when click the selected element do nothing.
	var preselected = $("[class='types-options cpu-options selected']");
	if($(this).text() == preselected.text()){
		return;
	}
	$('div', $('.cpu')).removeClass('selected');
	$(this).addClass('selected');
	$('#selectedCore').html($('.cpu').find('.selected').attr("core")
			+ "&nbsp;核");
	$('.types-item', $('.types')).removeClass('selected');
	$('#selectedType').html("定制");
	priceDisplayUpdate();
});

$('.memory').on('click', '.memory-options', function(event) {
	event.preventDefault();
	// when click the selected element do nothing.
	var preselected = $("[class='types-options memory-options selected']");
	if($(this).attr("capacity") == preselected.attr("capacity")){
		return;
	}
	if (!$(this).hasClass('disabled')) {
		$('div', $('.memory')).removeClass('selected');
		$(this).addClass('selected');
		$('#selectedCap').html($('.memory').find('.selected').attr("capacity")
				+ "&nbsp;G");
		$('.types-item', $('.types')).removeClass('selected');
		$('#selectedType').html("定制");
		priceDisplayUpdate();
	}
});

$('#display-pwd').on('change', function(event) {
			event.preventDefault();
			if (this.checked == true) {
				$('#login_passwd').attr('type', 'text');
			} else {
				$('#login_passwd').attr('type', 'password');
			}
		});

function pwvalid() {
	var text = $('input[name=login_passwd]').val();
	if (/^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{8,}$/.test(text)) {
		return true;
	} else {
		return false;
	}
}

$('input[name=count]').on('input', function(e) {
	var text = $('input[name=count]').val();
	if (parseInt(text) > 1) {
		$('#count-alert').removeClass("alert-info");
		$('#count-alert').removeClass("alert-danger");
		$('#count-alert').addClass("alert-success");
	} else {
		$('#count-alert').removeClass("alert-info");
		$('#count-alert').removeClass("alert-success");
		$('#count-alert').addClass("alert-danger");
	}
});

$('input[name=login_passwd]').on('input', function(e) {
	var text = $('input[name=login_passwd]').val();
	if (/^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{8,}$/.test(text)) {
		$('#pw-alert').removeClass("alert-info");
		$('#pw-alert').removeClass("alert-danger");
		$('#pw-alert').addClass("alert-success");
	} else {
		$('#pw-alert').removeClass("alert-info");
		$('#pw-alert').removeClass("alert-success");
		
	}
});

$('.create-model').change(function(){
	var model = $("input[name='createmodel']:checked").val();
	$('#selectMode').attr('model', model);
	if(model == 1){
		$('#selectMode').html("快照创建");
		$('#model-alert').removeClass("alert-info");
		$('#model-alert').removeClass("alert-success");
		$('#model-alert').addClass("alert-info");
	}else if(model == 2){
		$('#selectMode').html("克隆创建");
		$('#model-alert').removeClass("alert-info");
		$('#model-alert').removeClass("alert-success");
		$('#model-alert').addClass("alert-success");
	}
});

function checkip(ip){
	var re =  /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;   
	return re.test(ip);   
}
