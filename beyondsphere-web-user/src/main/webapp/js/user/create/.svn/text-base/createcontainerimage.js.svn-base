$('#cloneImageAction1').on('click', function (event) {
    event.preventDefault();
    var valid = $("#create-form").valid();
    if (valid) {
    	var imgaeAddress = document.getElementById("container_image_address").value;
        var imgaeName = document.getElementById("container_image_name").value;
        var imageVersion = document.getElementById("container_image_version").value;
        
        var boxes = document.getElementsByName("containerrow");
        alert(boxes.length);
        if (boxes.length > 0) {
            for (var i = 0; i < boxes.length; i++) {
                if (boxes[i].checked == true) {
                    var vmuuid = $(boxes[i]).parent().parent().attr("rowid");
                    $.ajax({
                        type: 'post',
                        url: 'ContainerAction/contianerMakeImage',
                        data: {imgaeAddress:imgaeAddress,imageName: imgaeName, imageVersion: imageVersion, containerId: vmuuid},
                        dataType: 'json'
                    });
                }
            }
        } else {
            var rsid = $("#modal-dialog").attr("rsid");
            $.ajax({
                type: 'post',
                url: 'ContainerAction/contianerMakeImage',
                data: {imgaeAddress:imgaeAddress,imageName: imgaeName, imageVersion: imageVersion, containerId: vmuuid},
                dataType: 'json'
            });
        }
        $('#ContainerModalContainer').modal('hide');
    }
    removeAllCheck();
});

$('#cancelAction').on('click', function (event) {
    event.preventDefault();
    $('#ContainerModalContainer').modal('hide');
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
    var boxes = document.getElementsByName("containerrow");
    for (var i = 0; i < boxes.length; i++) {
        boxes[i].checked = false;
        $(boxes[i]).change();
    }
}