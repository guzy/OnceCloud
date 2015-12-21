reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    var search = $('#search').val();
    getQuestionList(page, limit, search);
    options = {
        currentPage: 1
    }
    $('#pageDivider').bootstrapPaginator(options);
}

$('#apply').on('click', function (event) {
    event.preventDefault();
    $('#ServiceModalContainer').load($(this).attr('url'), '',
        function () {
            $('#ServiceModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
});


function getQuestionList(page, limit, search) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'QAAction/QuestionList',
        data: {page: page, limit: limit, search: search},
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
                var qaId = obj.qaId;
                var qaTitle = decodeURIComponent(obj.qaTitle);
                var qaSummary = decodeURIComponent(obj.qaSummary);
                var qaStatus = obj.qaStatus;
                var qaReply = obj.qaReply;
                var qaTime = obj.qaTime;
                var qaStatusStr = '<td></td>';
                var qaCloseStr = '<td class="close-td"></td>';
                if (qaStatus == 1) {
                    if (qaReply > 0) {
                        qaStatusStr = '<td state="process"><span class="icon-status icon-running" name="stateicon"></span><span name="stateword">处理中</span></td>';
                    } else {
                        qaStatusStr = '<td state="create"><span class="icon-status icon-using" name="stateicon"></span><span name="stateword">新建</span></td>';
                    }
                    qaCloseStr = '<td class="close-td"><a href="javascript:void(0)" class="qa-close">关闭</a></td>';
                } else if (qaStatus == 0) {
                    qaStatusStr = '<td state="close"><span class="icon-status icon-close" name="stateicon"></span><span name="stateword">已关闭</span></td>';
                }
                var level = $('#platformcontent').attr("userLevel");
                if (level > 0) {
                    tableStr = tableStr + '<tr qaId="' + qaId
                        + '"><td><a class="view-detail">'
                        + decodeURIComponent(qaTitle) + '</a></td>' + '<td>'
                        + decodeURIComponent(qaSummary) + '</td><td>' + qaReply
                        + '</td>' + qaStatusStr + '<td class="time">'
                        + qaTime + '</td>' + qaCloseStr + '</tr>';
                } else {
                    var qaUser = obj.qaUserName;
                    tableStr = tableStr + '<tr qaId="' + qaId
                        + '"><td><a class="view-detail">'
                        + decodeURIComponent(qaTitle) + '</a></td>' + '<td>'
                        + qaUser + '</td><td>' + decodeURIComponent(qaSummary)
                        + '</td><td>' + qaReply + '</td>' + qaStatusStr
                        + '<td class="time">' + qaTime + '</td>'
                        + qaCloseStr + '</tr>';
                }
            }
            $('#tablebody').html(tableStr);
        }
    });
}

function closeQuestion(id) {
    $.ajax({
        type: 'get',
        url: 'QAAction/CloseQuestion',
        data: {qaid: id},
        dataType: 'json',
        success: function (array) {
            if (array.length == 1) {
                var result = array[0].isSuccess;
                if (result == true) {
                    var thistr = $("#tablebody").find('[qaId="' + id + '"]');
                    var thistd = thistr.find('[state]');
                    thistd.find('[name="stateicon"]').removeClass('icon-using');
                    thistd.find('[name="stateicon"]')
                        .removeClass('icon-running');
                    thistd.find('[name="stateicon"]').addClass('icon-close');
                    thistd.find('[name="stateword"]').text('已关闭');
                    thistd.attr("state", "close");
                    var closetd = thistr.find('.close-td');
                    closetd.html("");
                }
            }
        }
    });
}

$('#tablebody').on('click', '.qa-close', function (event) {
    event.preventDefault();
    var qaId = $(this).parent().parent().attr("qaId");
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;关闭表单&nbsp;?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    closeQuestion(qaId);
                }
            },
            cancel: {
                label: "取消",
                className: "btn-default",
                callback: function () {
                }
            }
        }
    });
});

$('#tablebody').on('click', '.view-detail', function (event) {
    event.preventDefault();
    var qaid = $(this).parent().parent().attr("qaId");
    var form = $("<form></form>");
    form.attr("action", "service/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="qaId" value="' + qaid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});
