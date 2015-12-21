
$('#tablebody').on('click', '.uid', function (event) {
    event.preventDefault();
    var userid = $(this).parent().parent().attr('userid');
    var username = $(this).parent().parent().attr('username');
    var form = $("<form></form>");
    form.attr("action", "profit/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="userid" value="' + userid + '" />');
    var nameinput = $('<input type="text" name="username" value="' + username + '" />');
    form.append(input);
    form.append(nameinput);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});
$('#waterDiv').on('click', '.water', function (event) {
    event.preventDefault();
    var userid = $(this).attr('userid');
    var username = $(this).attr('username');
    var form = $("<form></form>");
    form.attr("action", "profit/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="userid" value="' + userid + '" />');
    var nameinput = $('<input type="text" name="username" value="' + username + '" />');
    form.append(input);
    form.append(nameinput);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});