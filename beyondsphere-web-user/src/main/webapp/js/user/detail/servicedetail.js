getQuestionDetail();
getReplyList();

function getQuestionDetail() {
    var qaId = $("#platformcontent").attr("qaId");
    $.ajax({
        type: 'get',
        url: '/QAAction/QuestionDetail',
        data: {qaId: qaId},
        dataType: 'json',
        success: function (array) {
            if (array.length == 1) {
                var obj = array[0];
                var qaTitle = decodeURIComponent(obj.qaTitle);
                var qaTime = obj.qaTime;
                var title = qaTitle + '<br/><small class="time">' + qaTime + '</small>';
                $('#qa-title').html(title);
                $('#qa-content').html(decodeURIComponent(obj.qaContent));
                var qaStatus = obj.qaStatus;
                if (qaStatus == 1) {
                    var appendStr = '<div class="once-pane" id="reply-area"><fieldset>'
                        + '<div class="item"><div class="control-label" style="width:30px">回复</div><div class="controls" style="margin-left:50px">'
                        + '<textarea rows="10" style="width:400px" id="qa-reply" name="qa-reply"></textarea></div></div>'
                        + '<button class="btn btn-primary" id="area-submit" style="margin-left:50px">提交</button>'
                        + '<button class="btn btn-default" id="area-clear" style="margin-left:5px">清空</button></fieldset></div>';
                    $('#platformcontent').append(appendStr);
                }
            }
        }
    });
}

$('#platformcontent').on('click', '#area-clear', function (event) {
    event.preventDefault();
    $('#qa-reply').val("");
});

$('#platformcontent').on('click', '#area-submit', function (event) {
    event.preventDefault();
    var content = $('#qa-reply').val();
    var qaId = $("#platformcontent").attr("qaId");
    var userName = $("#platformcontent").attr("userName");
    var userLevel = $("#platformcontent").attr("userLevel");
    $.ajax({
        type: 'post',
        url: '/QAAction/Reply',
        data: {qaId: qaId, content: content},
        dataType: 'json',
        success: function (array) {
            if (array.length == 1) {
                var obj = array[0];
                var qaId = obj.isSuccess;
                var imgStr = "../../img/user/avartar.png";
                if (userLevel > 0) {
                    imgStr = "../../img/user/user.png";
                }
                if (qaId > 0) {
                    var qaContent = decodeURIComponent(obj.qaContent);
                    var qaTime = obj.qaTime;
                    var innerStr = '<div class="attachments"></div><div class="reply-item">'
                        + '<div class="owner"><a class="avatar" href="#" style="background-image: url(' + imgStr + ')"></a></div>'
                        + '<div class="reply-content"><p>' + qaContent + '</p><p>' + userName + '</p><span class="time">' + qaTime + '</span></div></div>';
                    $('#replies').append(innerStr);
                    $('#qa-reply').val("");
                    var reply = parseInt($('#reply-num').attr("reply")) + 1;
                    $('#reply-num').html('所有回复&nbsp;(' + reply + ')');
                    $('#reply-num').attr("reply", reply);
                }
            }
        }
    });
});

function getReplyList() {
    var qaId = $("#platformcontent").attr("qaId");
    $.ajax({
        type: 'get',
        url: '/QAAction/ReplyList',
        data: {qaId: qaId},
        dataType: 'json',
        success: function (array) {
            var inner = '';
            $('#reply-num').html('所有回复&nbsp;(' + array.length + ')');
            $('#reply-num').attr("reply", array.length);
            for (var i = 0; i < array.length; i++) {
                var obj = array[i];
                var qaUser = obj.qaUser;
                var qaContent = decodeURIComponent(obj.qaContent);
                var qaStatus = obj.qaStatus;
                var qaTime = obj.qaTime;
                if (qaStatus == 2) {
                    var imgStr = "../../img/user/avartar.png";
                    var inner = inner + '<div class="attachments"></div><div class="reply-item">'
                        + '<div class="owner"><a class="avatar" href="#" style="background-image: url(' + imgStr + ')"></a></div>'
                        + '<div class="reply-content"><p>' + qaContent + '</p><p>' + qaUser + '</p><span class="time">' + qaTime + '</span></div></div>';
                } else {
                    var imgStr = "../../img/user/user.png";
                    var inner = inner + '<div class="attachments"></div><div class="reply-item">'
                        + '<div class="owner"><a class="avatar" href="#" style="background-image: url(' + imgStr + ')"></a></div>'
                        + '<div class="reply-content"><p>' + qaContent + '</p><p>' + qaUser + '</p><span class="time">' + qaTime + '</span></div></div>';
                }
            }
            $('#replies').html(inner);
        }
    });
}