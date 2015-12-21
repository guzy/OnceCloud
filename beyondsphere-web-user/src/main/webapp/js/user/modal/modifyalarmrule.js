$(document).ready(function () {
    initselect();

    function initselect() {
        var ruleName = $("#modal-type").attr("ruleName");
        var ruleterm = $("#modal-type").attr("ruleterm");
        var rulethre = $("#modal-type").attr("rulethre");
        var unit = $("#modal-type").attr("unit");
        $.each($("#rule_name option"), function () {
            if ($(this).text() == ruleName) {
                $(this).attr("selected", true);
            }
        });
        $.each($("#rule_term option"), function () {
            if ($(this).text() == ruleterm) {
                $(this).attr("selected", true);
            }
        });
        $("#rule_threshold").attr("value",rulethre);
        $("#alarm-unit").text(unit);
    }

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
            var ruleId = $("#modal-type").attr("ruleId");
            $.ajax({
                type: 'post',
                url: '/AlarmAction/ModifyRule',
                data: {
                	ruleName: ruleName, 
                	ruleTerm: ruleTerm, 
                	ruleThreshold: ruleThreshold, 
                	ruleId: ruleId
                	},
                dataType: 'text',
                success: function () {
                    $('input[name="rulerow"]:checked').each(function () {
                        $(this).parent().parent().find("#rulename").text(ruleNameStr);
                        $(this).parent().parent().find("#term").text(ruleTermStr);
                        $(this).parent().parent().find("#threshold").text(ruleThreshold);
                        $(this).parent().parent().find("#unit").text($("#alarm-unit").text());
                        $(this)[0].checked = false;
                        $(this).change();
                    });
                    $("#RuleModalContainer").modal('hide');
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