$(document).ready(function () {
    getAlarmBasic();
    $('#createrule').on('click', function (event) {
        event.preventDefault();
        $('#RuleModalContainer').load($(this).attr('url'), function () {
            $('#RuleModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
    });

    $('#confirm').on('click', function (event) {
        event.preventDefault();
        var ruleId = "";
        var ruleName = "";
        var ruleterm = "";
        var rulethre = "";
        var unit = "";
        $('input[name="rulerow"]:checked').each(function () {
            ruleId = $(this).parent().parent().attr("ruleid");
            ruleName = $(this).parent().parent().find("#rulename").text();
            ruleterm = $(this).parent().parent().find("#term").text();
            rulethre = $(this).parent().parent().find("#threshold").text();
            unit = $(this).parent().parent().find("#unit").text();
        });
        if (ruleterm == "<") {
            ruleterm = "&lt;";
        } else {
            ruleterm = "&gt;";
        }
        var url = basePath + 'user/modal/modifyalarmrule';
        $('#RuleModalContainer').load(url, {"ruleId": ruleId, "ruleName": ruleName,
            "ruleterm": ruleterm, "rulethre": rulethre, "unit": unit}, function () {
            $('#RuleModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
    });

    $('#basic-modify').on('click', function (event) {
        event.preventDefault();
        var url = basePath + 'user/modal/modifyalarm';
        var alName = $('#alName').text();
        var alDesc = $('#alDesc').text();
        var alarmUuid = $('#platformcontent').attr("alarmUuid");
        $('#RuleModalContainer').load(url, {"modifyUuid": alarmUuid,
            "modifyName": alName, "modifyDesc": alDesc}, function () {
            $('#RuleModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
    });

    $('#basic-resource').on('click', function (event) {
        event.preventDefault();
        var url = basePath + 'user/modal/bindalarm';
        var alarmUuid = $('#platformcontent').attr("alarmUuid");
        $('#ResourceModalContainer').load(url, {"uuid": alarmUuid}, function () {
            $('#ResourceModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
    });

    function getInfoList() {
        var infoList = "";
        infoList += "[al-" + $('#platformcontent').attr("alarmUuid").substring(0, 8) + "]&nbsp;";
        return infoList;
    }

    function showbox() {
        var infoList = getInfoList();
        var showMessage = '';
        var showTitle = '';
        showMessage = '<div class="alert alert-info" style="margin:10px">'
            + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;确定要删除警告策略&nbsp;' + infoList + '?</div>';
        showTitle = '提示';

        bootbox.dialog({
            className: "oc-bootbox",
            message: showMessage,
            title: showTitle,
            buttons: {
                main: {
                    label: "确定",
                    className: "btn-primary",
                    callback: function () {
                        var uuid = $('#platformcontent').attr("alarmUuid");
                        destroyAlarm(uuid);
                        removeAllCheck();
                    }
                },
                cancel: {
                    label: "取消",
                    className: "btn-default",
                    callback: function () {
                        removeAllCheck();
                    }
                }
            }
        });
    }

    $('#basic-remove').on('click', function (event) {
        event.preventDefault();
        showbox();
    });

    function destroyAlarm(uuid) {
        $.ajax({
            type: 'get',
            url: '/AlarmAction/Destory',
            data: {alarmUuid: uuid},
            dataType: 'json',
            success: function (result) {
                if (result) {
                    window.location.href = basePath + "alarm";
                } else {
                    bootbox.dialog({
                        message: '<div class="alert alert-danger" style="margin:10px"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;该策略已绑定资源，请先卸载资源</div>',
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
            },
            error: function () {
            }
        });
    }

    $('#basic-unbind').on('click', function (event) {
        event.preventDefault();
        var showMessage = '';
        var showTitle = '';
        showMessage = '<div class="alert alert-info" style="margin:10px">'
            + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;确定要解除所有资源&nbsp;?</div>';
        showTitle = '提示';

        bootbox.dialog({
            className: "oc-bootbox",
            message: showMessage,
            title: showTitle,
            buttons: {
                main: {
                    label: "确定",
                    className: "btn-primary",
                    callback: function () {
                        $.each($("#rsTable div #remove-resource"), function () {
                            $(this).click();
                        });
                    }
                },
                cancel: {
                    label: "取消",
                    className: "btn-default",
                    callback: function () {
                        removeAllCheck();
                    }
                }
            }
        });
    });

    var options = {
        bootstrapMajorVersion: 3,
        currentPage: 1,
        totalPages: 1,
        numberOfPages: 0,
        onPageClicked: function (e, originalEvent, type, page) {
            var limit = $('#limit').val();
            var alarmUuid = $('#platformcontent').attr("alarmUuid");
            getRuleList(page, limit, "", alarmUuid);
        },
        shouldShowPage: function (type, page, current) {
            switch (type) {
                case "first":
                case "last":
                    return false;
                default:
                    return true;
            }
        }
    }
    $('#pageDivider').bootstrapPaginator(options);
    getRuleList(1, 10, "", $('#platformcontent').attr("alarmUuid"));

    $('#limit').on('focusout', function () {
        var limit = $('#limit').val();
        var reg = /^[0-9]*[1-9][0-9]*$/;
        if (!reg.test(limit)) {
            $('#limit').val(10);
        }
        reloadList();
    });

    function getAlarmBasic() {
        var alarmUuid = $('#platformcontent').attr("alarmUuid");
        $('#basic-list').html("");
        $.ajax({
            type: 'get',
            url: '/AlarmAction/GetAlarm',
            data: {alarmUuid: alarmUuid},
            dataType: 'json',
            success: function (obj) {
                var showid = "al-" + alarmUuid.substring(0, 8);
                var alarmName = decodeURIComponent(obj.alarmName);
                var alarmTime = decodeURIComponent(obj.alarmTime);
                alarmTime = alarmTime.substring(0, alarmTime.length - 2);
                alarmTime = alarmTime.replace(/%3A/g, ":");
                var rsTable = '';
                $.each(obj.alarmVM, function (index, json) {
                    rsTable += '<div style="padding-left:102px"><a>' + json.rsName +
                        '</a><a id="remove-resource" rsUuid="' + json.rsUuid + '"><span class="glyphicon glyphicon-remove delete-resource"></span></a></div>';
                });
                if (rsTable == '') {
                    rsTable = '<span class="none">尚无资源</span>';
                }
                var alarmDesc = decodeURIComponent(obj.alarmDesc);
                alarmDesc = alarmDesc.replace(/%2F/g, "/");
                $('#basic-list').html('<dt>ID</dt><dd><a href="javascript:void(0)">'
                    + showid + '</a></dd><dt>名称</dt><dd id="alName">'
                    + alarmName + '</dd><dt>描述</dt><dd id="alDesc">' + alarmDesc 
                    + '</dd><dt>资源列表</dt><dd id="rsTable">' + rsTable
                    + '</dd><dt>创建时间</dt><dd class="none">' + alarmTime + '</dd>');
            },
            error: function () {
            }
        });
    }

    $("#basic-list").on('click', '#remove-resource', function (event) {
        var rsUuid = $(this).attr("rsUuid");
        var othis = $(this);
        $.ajax({
            type: 'post',
            url: '/AlarmAction/UnBindAlarm',
            data: {rsUuid: rsUuid},
            dataType: 'text',
            success: function (obj) {
                othis.parent().remove();
                if ($("#rsTable").html() == "") {
                    $("#rsTable").html('<span class="none">尚无资源</span>');
                }
            }
        });
    });

    $('.basic-refresh').on('click', function (event) {
        event.preventDefault();
        getAlarmBasic();
    });

    $('.rule-refresh').on('click', function (event) {
        event.preventDefault();
        reloadList();
    });

    function reloadList() {
        var limit = $('#limit').val();
        var alarmUuid = $('#platformcontent').attr("alarmUuid");
        getRuleList(1, limit, "", alarmUuid);
        options = {
            currentPage: 1
        }
        $('#pageDivider').bootstrapPaginator(options);
    }

    function getRuleList(page, limit, search, alarmUuid) {
        $('#tablebody').html("");
        $.ajax({
            type: 'get',
            url: '/AlarmAction/RuleList',
            data: {alarmUuid: alarmUuid, page: page, limit: limit},
            dataType: 'json',
            success: function (array) {
                var totalnum = array[0];
                var totalp = 1;
                if (totalnum != 0) {
                    totalp = Math.ceil(totalnum / limit);
                }
                options = {
                    totalPages: totalp
                }
                $('#pageDivider').bootstrapPaginator(options);
                pageDisplayUpdate(page, totalp);
                var tableStr = "";
                for (var i = 1; i < array.length; i++) {
                    var obj = array[i];
                    var ruleId = obj.ruleId;
                    var ruleOrdinal = obj.ruleOrdinal;
                    var ruleName = decodeURIComponent(obj.ruleName);
                    var ruleThreshold = obj.ruleThreshold;
                    var ruleTerm =  decodeURIComponent(obj.ruleTerm);
                    var unit = "";
                    if (ruleOrdinal == 0 || ruleOrdinal == 1 || ruleOrdinal == 2) {
                        unit = "%";
                    } else {
                        unit = "Mbps";
                    }
                    var thistr = '<tr ruleid="' + ruleId + '"><td class="rcheck"><input type="checkbox" name="rulerow"></td><td name="rulename" id="rulename">'
                        + ruleName + '</td><td name="term" id="term">' + ruleTerm + '</td><td name="threshold"><span id="threshold">'
                        + ruleThreshold + '</span>&nbsp;<sapn id="unit">' + unit + '</span></td></tr>';
                    tableStr += thistr;
                }
                $('#tablebody').html(tableStr);
            },
            error: function () {
            }
        });
    }

    $('#deleterule').on('click', function (event) {
        event.preventDefault();
        $('input[name="rulerow"]:checked').each(function () {
            var ruleId = $(this).parent().parent().attr("ruleid");
            deleteRule(ruleId);
        });
        removeAllCheck();
    });

    function deleteRule(ruleId) {
        var thistr = $("#tablebody").find('[ruleid="' + ruleId + '"]');
        $.ajax({
            type: 'get',
            url: '/AlarmAction/DeleteRule',
            data: {ruleId: ruleId},
            dataType: 'text',
            complete: function () {
                $(thistr).remove();
                $('#deleterule').addClass('btn-disable').attr('disabled', true);
                getAlarmBasic();
            }
        });
    }

    $('#tablebody').on('change', 'input:checkbox', function (event) {
        event.preventDefault();
        var count = 0;
        $('input[name="rulerow"]:checked').each(function () {
            count++;
        });
        if (count == 0) {
            $('#deleterule').addClass('btn-disable').attr('disabled', true);
            $('#confirm').addClass('btn-disable').attr('disabled', true);
        } else if (count == 1) {
            $('#confirm').removeClass('btn-disable').attr('disabled', false);
            $('#deleterule').removeClass('btn-disable').attr('disabled', false);
        } else {
            $('#deleterule').removeClass('btn-disable').attr('disabled', false);
            $('#confirm').addClass('btn-disable').attr('disabled', true);
        }
    });

    function pageDisplayUpdate(current, total) {
        $('#currentP').html(current);
        $('#totalP').html(total);
    }

    function removeAllCheck() {
        $('input[name="rulerow"]:checked').each(function () {
            $(this)[0].checked = false;
            $(this).change();
        });
    }
});