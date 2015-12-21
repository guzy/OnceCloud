reloadList(1);

function reloadList(page) {
    var limit = $('#limit').val();
    var search = '';
    getCompanyList(page, limit, search);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
}

function getCompanyList(page, limit, search) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: '../UserAction/UserList',
        data: {page: page, limit: limit, search: search},
        dataType: 'json',
        success: function (array) {
            if (array.length >= 1) {
                var totalnum = array[0];
                var totalp = 1;
                if (totalnum != 0) {
                    totalp = Math.ceil(totalnum / limit);
                }
                options = {
                    totalPages: totalp
                };
                $('#pageDivider').bootstrapPaginator(options);
                pageDisplayUpdate(page, totalp);
                var tableStr = "";
                for (var i = 1; i < array.length; i++) {
                    var obj = array[i];
                    var username = decodeURIComponent(obj.username);
                    var thistr = '<tr userid="' + obj.userid + '" username="' + username + '">'
                    		+ '<td class="rcheck"><input type="checkbox" name="userrow"></td>'
                    		+ '<td><a class="username">' + decodeURIComponent(obj.usercom) + '</a></td>'
                    		+ '<td>' + username + '</td>'
                    		+ '<td>' + obj.vmNum +'</td>'
                    		+ '<td>' + obj.usermail + '</td>'
                    		+ '<td>' + obj.userphone + '</td>'
                    		+ '<td class="time">' + obj.userdate + '</td></tr>';
                    tableStr += thistr;
                }
                $('#tablebody').html(tableStr);
            }
        }
    });
}

$('#tablebody').on('click', '.username', function (event) {
    event.preventDefault();
    var userid = $(this).parent().parent().attr('userid');
    var form = $("<form></form>");
    form.attr("action", $("#platformcontent").attr("basePath")+'instance');
    form.attr('method', 'get');
    var input = $('<input type="text" name="userid" value="' + userid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});