

/*(function(){
	var mtype = $("#modalcontent").attr("modify");
	var type = $("#modalcontent").attr("mtype");
	var uuid = $("#modalcontent").attr("uuid");
	$.ajax({
		type: "post",
		url: "VMAction/NetList",
		data: {uuid:uuid, type:type},
		async: false,
		dataType: "json",
		success: function(obj){
			$.each(obj, function(index, json){
				$("#physical").append('<option>'+json.nets+'</option>');
			});
		}
	});
	if (mtype == "vlan") {
		//$("#mphysical").hide();
	} else if (mtype == "physical") {
		//$("#mvlan").hide();
		$.ajax({
			type: "post",
			url: "VMAction/NetList",
			data: {uuid:uuid, type:type},
			async: false,
			dataType: "json",
			success: function(obj){
				$.each(obj, function(index, json){
					$("#physical").append('<option>'+json.nets+'</option>');
				});
			}
		});
	} else {
		$.ajax({
			type: "post",
			url: "VMAction/NetList",
			data: {uuid:uuid, type:type},
			dataType: "json",
			success: function(obj){
				$.each(obj, function(index, json){
					$("#physical").append('<option>'+json.nets+'</option>');
				});
			}
		});
	}
})();
*/
function reloadswitchlist(){
	var vmid = '';
	$('input[name="vmrow"]:checked').each(function () {
		vmid = $(this).parent().parent().attr('rowid');
    });
	
}

/*切换选项卡*/
$('.once-tab').on('click', '.tab-filter', function (event) {
    $('li', '.once-tab').removeClass('active');
    $(this).addClass('active');
    reloadswitchlist();
});

$("#createMac").on("click", function(){
	var type = $("#modalcontent").attr("mtype");
	var uuid = $("#modalcontent").attr("uuid");
	var vnetid = -1;
	if ($("#netbody tr").length < 6) {
		$.ajax({
			type: "post",
			url: "VMAction/AddMac",
			data: {uuid:uuid, type:type, vnetid:vnetid},
			dataType: "json",
			success: function(obj){
				if(obj){
					$('#ModifyModalContainer').modal('hide');
					getmaclist();
				}	
			}
		});
	}
});



/*$("#createMac").on("click", function(){	
	if ($("#create-form").valid()) { 
		allDisable();
		var mtype = $("#modalcontent").attr("modify");
		var type = $("#modalcontent").attr("mtype");
		var uuid = $("#modalcontent").attr("uuid");
		if (mtype == "vlan") {
			var vifUuid;
			$('input[name="vifrow"]:checked').each(function () {
		    	vifUuid = $(this).parent().parent().attr("rowid");
			});
			var vnetid = -1;
			$.ajax({
				type: "post",
				url: "VMAction/ModifyVnet",
				data: {uuid:uuid, type:type, vifUuid:vifUuid, vnetid:vnetid},
				dataType: "json",
				success: function(obj){
					if(obj){
						$('#ModifyModalContainer').modal('hide');
						getmaclist();
					}
				}
			});
		} else if (mtype == "physical") {
			var vifUuid;
			$('input[name="vifrow"]:checked').each(function () {
		    	vifUuid = $(this).parent().parent().attr("rowid");
			});
			var physical = $("#physical option:selected").val();
			$.ajax({
				type: "post",
				url: "VMAction/ModifyPhysical",
				data: {uuid:uuid, type:type, vifUuid:vifUuid, physical:physical},
				dataType: "json",
				success: function(obj){
					if(obj){
						$('#ModifyModalContainer').modal('hide');
						getmaclist();
					}
				}
			});
		} else {
			var vnetid = $("#vlan").val();
			if (vnetid == "") {
				vnetid = -1;
			}
			var physical = $("#physical option:selected").val();
			if ($("#netbody tr").length < 6) {
				$.ajax({
					type: "post",
					url: "VMAction/AddMac",
					data: {uuid:uuid, type:type, vnetid:vnetid, physical:physical},
					dataType: "json",
					success: function(obj){
						if(obj){
							$('#ModifyModalContainer').modal('hide');
							getmaclist();
						}	
					}
				});
			} else {
				bootbox.dialog({
	                    message: '<div class="alert alert-danger" style="margin:10px"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;超过虚拟机绑定网卡上限</div>',
	                    title: "提示",
	                    buttons: {
	                        main: {
	                            label: "确定",
	                            className: "btn-primary",
	                            callback: function () {
	                            }
	                        }
	                    }
	                });
			}
		}
	}	 
});*/
$("#create-form").validate({
    rules: {
    	vlan: {
            required: true,
            digits: true,
            max:4096,
            
        }
    },
    messages: {
    	vlan: {
            required: "<span class='help'>请填写Vlan号</span>",
            digits: "<span class='help'>请填写整数</span>",
            max:"<span class='help'>必须小于等于4096</span>",
        }
    }
});

