$('#cloneImageAction').on('click', function (event) {
    event.preventDefault();
    var valid = $("#create-form").valid();
    if (valid) {
        var imgaeName = document.getElementById("image_name").value;
        var imageDesc = document.getElementById("image_desc").value;
        var boxes = document.getElementsByName("vmrow");
        if (boxes.length > 0) {
            for (var i = 0; i < boxes.length; i++) {
                if (boxes[i].checked == true) {
                    var vmuuid = $(boxes[i]).parent().parent().attr("rowid");
                    $.ajax({
                        type: 'post',
                        url: '/ImageAction/Clone',
                        data: {imageName: imgaeName, imageDesc: imageDesc, vmUuid: vmuuid},
                        dataType: 'json'
                    });
                }
            }
        } else {
            var rsid = $("#modal-dialog").attr("rsid");
            $.ajax({
                type: 'post',
                url: '/ImageAction/Clone',
                data: {imageName: imgaeName, imageDesc: imageDesc, vmUuid: rsid},
                dataType: 'json'
            });
        }
        $('#InstanceModalContainer').modal('hide');
    }
    removeAllCheck();
});

$('#cancelAction').on('click', function (event) {
    event.preventDefault();
    $('#InstanceModalContainer').modal('hide');
    removeAllCheck();
});

$("#create-form").validate({
    rules: {
        image_name: {
            required: true,
            maxlength: 20,
            legal: true
        },
        image_desc: {
            required: true,
            maxlength: 80,
            legal: true
        }
    },
    messages: {
        image_name: {
            required: "<span class='help'>映像名称不能为空</span>",
            maxlength: "<span class='help'>映像名称不能超过20个字符</span>",
            legal: "<span class='help'>映像名称包含非法字符</span>"
        },
        image_desc: {
            required: "<span class='help'>映像备注不能为空</span>",
            maxlength: "<span class='help'>映像备注不能超过80个字符</span>",
            legal: "<span class='help'>映像备注包含非法字符</span>"
        }
    }
});

function removeAllCheck() {
    var boxes = document.getElementsByName("vmrow");
    for (var i = 0; i < boxes.length; i++) {
        boxes[i].checked = false;
        $(boxes[i]).change();
    }
}