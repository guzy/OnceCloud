$("#AlarmModalContainer").on("hidden", function() {
	$(this).removeData("modal");
	$(this).children().remove();
});

$("#wizard").bwizard({
	backBtnText : "",
	nextBtnText : ""
});

$('.btn-back').on('click', function(event) {
	event.preventDefault();
	$("#wizard").bwizard("back");
});

$('.first-next').on('click', function(event) {
	event.preventDefault();
	if ($('#paramsetting-form').valid()) {
		$("#wizard").bwizard("next");
	}
});

$('.li-disable').unbind();
$('ol').removeClass("clickable");
$('.hidden-phone').css("cursor", "default");
$('.hidden-phone').attr("href", "javascript:void(0)");

$('.second-next').on('click', function(event) {
	event.preventDefault();
	$("#wizard").bwizard("next");
});

$('#createAlarmAction').on('click',function(event) {
	event.preventDefault();
	var alarmUuid = uuid.v4();
	var alarmName = $("#alarm_name").val();
	var alarmRules = '[';
	var flag = false;
	$.each($("#rules li"), function() {
	var alarmRuleName = $(this).find("#meter").val();
	var alarmRuleTerm = $(this).find("#condition_type").val();
	var alarmRuleThreshold = $(this).find("#count").val();
	alarmRules += '{"ruleName":' + alarmRuleName
	+ ',"ruleTerm":' + alarmRuleTerm
	+ ',"ruleThreshold":' + alarmRuleThreshold + '},';
	
	});
	alarmRules += ']';
	var valid = $("#rulessetting-form").valid();
	if (valid) {
	createAlarm(alarmUuid, alarmName, alarmRules);}
	});

function createAlarm(alarmUuid, alarmName,alarmRules){
	$.ajax({
				type : 'post',
				url : '/AlarmAction/Create',
				data : {
					alarmUuid : alarmUuid,
					alarmName : alarmName,
					alarmRules : alarmRules,
				},
				dataType : 'text',
				success : function() {
					$('#AlarmModalContainer').modal('hide');
					var showuuid = "al-"
				        + alarmUuid.substring(0, 8);
				    var showstr = "<a class='id'>" + showuuid
				        + '</a>';
				    $("#tablebody")
					    	.prepend('<tr rowid="'
							        + alarmUuid
							        + '"><td class="rcheck"><input type="checkbox" name="alrow"></td><td name="console">'
							        + showstr
							        + '</td><td name="alarmName">'
							        + alarmName
							        + '</td><td name="alarmCount">'
							        + 0
							        + '</td><td name="createtime" class="time">1分钟以内</td></tr>');
					/*bootbox.dialog({
	                    message: '<div class="alert alert-success" style="margin:10px"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;添加规则成功</div>',
	                    title: "提示",
	                    buttons: {
	                        main: {
	                            label: "确定",
	                            className: "btn-primary",
	                            callback: function () {
	                            	var showuuid = "al-"
	            				        + alarmUuid.substring(0, 8);
	            				    var showstr = "<a class='id'>" + showuuid
	            				        + '</a>';
	            				    $("#tablebody")
	            					    	.prepend('<tr rowid="'
	            							        + alarmUuid
	            							        + '"><td class="rcheck"><input type="checkbox" name="alrow"></td><td name="console">'
	            							        + showstr
	            							        + '</td><td name="alarmName">'
	            							        + alarmName
	            							        + '</td><td name="alarmCount">'
	            							        + 0
	            							        + '</td><td name="createtime" class="time">1分钟以内</td></tr>');
	            				}
	                        }
	                    }
					});*/
				}
			});
} 

$("#rulessetting-form").validate({
    rules: {
    	count: {
            required: true,
            digits: true,
        }
    },
    messages: {
    	count: {
            required: "<span class='alarm-unit'>请填写阈值</span>",
            digits: "<span class='alarm-unit'>阈值应为正整数</span>",
        }
    }
});

$('#paramsetting-form').validate({
	rules : {
		alarm_name : {
			required : true,
			maxlength : 20,
			legal : true
		}
	},
	messages : {
		alarm_name : {
			required : "<span class='unit'>名称不能为空</span>",
			maxlength : "<span class='unit'>名称不能超过20个字符</span>",
			legal : "<span class='unit'>名称包含非法字符</span>"
		}
	}
});



function removeAllOptions() {
	$("#meter").empty();
	$("#rules li").not(":first").remove();
}



$("#add-rule").on(
		'click',
		function(event) {
			event.preventDefault();
			$("#rules li:first").clone(true).appendTo("#rules");
			$("#rules li").not(":first").find("#remove-rule").show();
			$("#rules li:first").find("#remove-rule").hide();
			var str = $("#rules li:last").find("#meter").val();
			if (str == 0 || str == 1 || str == 2) {
				$("#rules li:last").find("#unit").text("%");
			} else {
				$("#rules li:last").find("#unit").text("Mbps");
			}
		});

$("#remove-rule").on('click', function(event) {
	event.preventDefault();
	if ($("#rules li").length > 1) {
		$(this).parent().remove();
	}
});

$("#meter").change(function() {
	var str = $(this).val();
    if (str == 0 || str == 1 || str == 2) {
		$(this).parent().parent().find("#alarm-unit").text("%");
    } else {
    	$(this).parent().parent().find("#alarm-unit").text("Mbps");
    }
});

$(":radio").click(function() {
	if ($(this).val() == 1) {
		$("#advanced-options").show();
	} else {
		$("#advanced-options").hide();
	}
});
