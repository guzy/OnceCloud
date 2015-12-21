$(document).ready(function () {

    $("#RuleModalContainer").on("hidden", function () {
        $(this).removeData("modal");
        $(this).children().remove();
    });

    $("#createruleAction").on('click', function (event) {
        event.preventDefault();

        var valid = $("#createrule-form").valid();
        if (valid) {
            var ruleName = $("#rule_name").find("option:selected").val();
            var ruleNameStr = $("#rule_name").find("option:selected").text();
            var ruleTerm = $("#rule_term").find("option:selected").val();
            var ruleTermStr = $("#rule_term").find("option:selected").text();
            var ruleThreshold = $("#rule_threshold").val();
            var unit = $("#alarm_unit").val();
            var ruleId = uuid.v4();
            var alarmUuid = $('#platformcontent').attr("alarmUuid");
            $.ajax({
                type: 'post',
                url: '/AlarmAction/CreateRule',
                data: {
                	ruleName: ruleName,
                	ruleTerm:ruleTerm,
                	ruleThreshold: ruleThreshold,
                    ruleId: ruleId,
                    alarmUuid: alarmUuid
                },
                dataType: 'text',
                success: function () {
                    $("#tablebody").append('<tr ruleid="' + ruleId + '"><td class="rcheck"><input type="checkbox" name="rulerow"></td><td name="rulename" id="rulename">'
                        + ruleNameStr + '</td><td name="term" id="term">' + ruleTermStr + '</td><td name="threshold"><span id="threshold">'
                        + ruleThreshold + '</span>&nbsp;<span id="unit">' + $("#alarm-unit").text() + '</span></td></tr>');
                    $("#RuleModalContainer").modal('hide');
                },
                error: function () {
                }
            });
        }
    });

    $("#rule_name").change(function () {
        var str = $(this).val();
        if (str == 0 || str == 1 || str == 2) {
            $("#alarm-unit").text("%");
        } else {
            $("#alarm-unit").text("Mbps");
        }
    });
    
    $("#createrule-form").validate({
        rules: {
        	rule_threshold: {
                required: true,
                digits: true,
            }
        },
        messages: {
        	rule_threshold: {
                required: "<span class='unit'>请填写阈值</span>",
                digits: "<span class='unit'>阈值应为正整数</span>",
            }
        }
    });

});