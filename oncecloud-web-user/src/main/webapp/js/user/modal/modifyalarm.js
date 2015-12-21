$('#modifyinfomation').on('click', function (event) {
    event.preventDefault();
    var valid = $("#modify-form").valid();
    if (valid) {
        var modifyuuid = $('#modify-form').attr('uuid');
        var modifyname = $('#name').val();
        var modifydesc = $('#desc').val();
        $.ajax({
            type: 'post',
            url: '/AlarmAction/ModifyAlarm',
            data: {modifyUuid: modifyuuid, modifyName: modifyname, modifyDesc: modifydesc},
            dataType: 'text',
            success: function (response) {
                if (response == "true") {
                    if ("" == trim(modifydesc)) {
                        modifydesc = '&nbsp;';
                    }
                    $('#alName').text(modifyname);
                    $('#alDesc').html('<div>' + modifydesc + '</div>');
                }
            }
        });
        $('#RuleModalContainer').modal('hide');
    }
});

$("#modify-form").validate({
    rules: {
        name: {
            required: true,
            maxlength: 20,
            legal: true
        },
        desc: {
            maxlength: 80,
            legal: true
        }
    },
    messages: {
        name: {
            required: "<span class='unit'>名称不能为空</span>",
            maxlength: "<span class='unit'>名称不能多于20个字符</span>",
            legal: "<span class='unit'>名称包含非法字符</span>"
        },
        desc: {
            maxlength: "<span class='unit'>描述不能多于80个字符</span>",
            legal: "<span class='help'>描述包含非法字符</span>"
        }
    }
});

function trim(string) {
    return string.replace(/( +)$/g, "").replace(/^( +)/g, "");
}