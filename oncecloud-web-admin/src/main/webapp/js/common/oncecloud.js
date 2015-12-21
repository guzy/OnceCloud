$('a').on('click', function (event) {
    if ($(this).hasClass("btn-forbidden")) {
        event.stopImmediatePropagation();
        return false;
    }
});

calendar();

function calendar() {
    var date = new Date();
    var monthStr = new Array("Jan", "Feb", "March", "April", "May", "June", "July", "August", "Sep", "Oct", "Nov", "Dec");
    $("#avartar-top").html(monthStr[date.getMonth()]);
    $("#avartar-bottom").html(date.getDate());
}

var options = {
    bootstrapMajorVersion: 3,
    currentPage: 1,
    totalPages: 1,
    numberOfPages: 0,
    onPageClicked: function (e, originalEvent, type, page) {
        reloadList(page);
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
};

$('#pageDivider').bootstrapPaginator(options);

$('.btn-refresh').on('click', function (event) {
    reloadList(1);
});

$('#limit').on('focusout', function () {
    var limit = $("#limit").val();
    var reg = /^[0-9]*[1-9][0-9]*$/;
    if (!reg.test(limit)) {
        $("#limit").val(10);
    }
    reloadList(1);
});

$('#search').on('focusout', function () {
    reloadList(1);
});

$('#search').keypress(function (e) {
    var key = e.which;
    if (key == 13) {
        reloadList(1);
    }
});

function pageDisplayUpdate(current, total) {
    $('#currentP').html(current);
    $('#totalP').html(total);
}