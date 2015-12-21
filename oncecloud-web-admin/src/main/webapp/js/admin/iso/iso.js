reloadList(1);

function reloadList(page) {
    var limit = 123456;
    var search ="";
    getRoleList(page, limit, search);
}


$('#upfileModel').on('click', function (event) {
    event.preventDefault();
    $('#UserModalContainer').attr("type", "new");
    $('#UserModalContainer').load($(this).attr('url'), '', function () {
        $('#UserModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});


function getRoleList(page, limit, search) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'ISOAction/ISOList',
        data: {page: page, limit: limit, search: search},
        dataType: 'json',
        success: function (array) {
            if (array !=null && array[0]>= 1) {
          
                var tableStr = "";
                for (var i = 1; i < array.length; i++) {
                	var obj =array[i];
                    var isoName = decodeURIComponent(obj.isoName);
                    var poolName = decodeURIComponent(obj.poolName);
                    var thistr = '<tr>'
                        + '<td>' + isoName + '</td><td>' + poolName + '</td></tr>';
                    tableStr += thistr;
                }
                $('#tablebody').html(tableStr);
            }
        }
    });
}

