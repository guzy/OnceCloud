$('#create, #image').on('click', function (event) {
    event.preventDefault();
    $('#RegistryModalContainer').load($(this).attr('url'), '', function () {
        $('#RegistryModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#adjust').on('click', function (event) {
	event.preventDefault();
    $('#RegistryModalContainer').load('user/modal/addreg', '', function () {
        $('#RegistryModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#platformcontent').on('click', '.backup', function (event) {
    event.preventDefault();
    var thisurl = $(this).attr('url');
    bootbox.dialog({
        className: "bootbox-large",
        message: '<div class="alert alert-warning" style="margin:10px; color:#c09853">'
            + '<span class="glyphicon glyphicon-warning-sign"></span>&nbsp;对正在运行的主机进行在线备份时，需要注意以下几点:<br/><br/>1. 备份只能捕获在备份任务开始时已经写入磁盘的数据，不包括当时位于内存里的数据；<br/>2. 为了保证数据的完整性，请在创建备份前暂停所有文件的写操作，或进行离线备份。</div>',
        title: "提示",
        buttons: {
            main: {
                label: "创建备份",
                className: "btn-primary",
                callback: function () {
                    $('#ContainerModalContainer').load(thisurl, '', function () {
                        $('#ContainerModalContainer').modal({
                            backdrop: false,
                            show: true
                        });
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