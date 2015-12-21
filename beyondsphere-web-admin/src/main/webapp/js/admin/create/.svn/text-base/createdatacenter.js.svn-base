$(document).ready(function () {
    fillBlank();

    function fillBlank() {
        var type = $('#DCModalContainer').attr('type');
        if ('edit' == type) {
            $('#modaltitle').html('修改数据中心<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a>');

            var dcid = '';
            var dcname = '';
            var dclocation = '';
            var dcdesc = '';

            $('input[name="dcrow"]:checked').each(function () {
                dcid = $(this).parent().parent().attr("dcid");
                dcname = $(this).parent().parent().attr("dcname");
                dclocation = $(this).parent().parent().attr("dclocation");
                dcdesc = $(this).parent().parent().attr("dcdesc");
            });

            $('#modalcontent').attr('dcid', dcid);

            $('#dc_name').val(dcname);
            $('#dc_location').val(dclocation);
            $('#dc_desc').val(dcdesc);

        }
    }

    $('#createDCAction').on('click', function (event) {
        event.preventDefault();
        var valid = $("#create-form").valid();
        if (valid) {
            var dcName = document.getElementById("dc_name").value;
            var dcLocation = document.getElementById("dc_location").value;
            var dcDesc = document.getElementById("dc_desc").value;
            var type = $('#DCModalContainer').attr('type');
            if ('new' == type) {
                $.ajax({
                    type: 'post',
                    url: 'DatacenterAction/Create',
                    data: {dcname: dcName, dclocation: dcLocation, dcdesc: dcDesc},
                    dataType: 'json',
                    success: function (array) {
                        if (array.length == 1) {
                            var obj = array[0];
                            var dcname = decodeURIComponent(obj.dcname);
                            var dcdesc = decodeURIComponent(obj.dcdesc);
                            var dcid = obj.dcid;
                            var dclocation = decodeURIComponent(obj.dclocation);
                            var createdate = obj.createdate;
                            var showid = "dc-" + dcid.substring(0, 8);
                            var mytr = '<tr dcid="' + dcid + '" dcname="' + dcname + '" dclocation="' + dclocation + '" dcdesc="' + dcdesc + '"><td class="rcheck"><input type="checkbox" name="dcrow"></td>'
                                + '<td><a class="id">' + showid + '</a></td><td>' + dcname + '</td><td>' + dclocation + '</td><td>0</td><td>0</td><td class="time">' + createdate + '</td></tr>';
                            $("#tablebody").prepend(mytr);
                        }
                        $('#DCModalContainer').modal('hide');
                    }
                });
            } else {
                var dcid = $('#modalcontent').attr('dcid');
                $.ajax({
                    type: 'post',
                    url: 'DatacenterAction/Update',
                    data: {dcuuid: dcid, dcname: dcName, dclocation: dcLocation, dcdesc: dcDesc},
                    dataType: 'text',
                    success: function () {
                        var thistr = $("#tablebody").find('[dcid="' + dcid + '"]');
                        thistr.attr('dcname', dcName);
                        thistr.attr('dclocation', dcLocation);
                        thistr.attr('dcdesc', dcDesc);
                        thistr.children('td').eq(2).html(dcName);
                        thistr.children('td').eq(3).html(dcLocation);
                        $('#DCModalContainer').modal('hide');

                    }
                });
            }
        }
    });

    $("#create-form").validate({
        rules: {
            dc_name: {
                required: true,
                maxlength: 20,
                legal: true
            },
            dc_desc: {
                required: true,
                maxlength: 80,
                legal: true
            }
        },
        messages: {
            dc_name: {
                required: "<span class='help'>名称不能为空</span>",
                maxlength: "<span class='help'>名称不能超过20个字符</span>",
                legal: "<span class='help'>名称包含非法字符</span>"
            },
            dc_desc: {
                required: "<span class='help'>描述不能为空</span>",
                maxlength: "<span class='help'>描述不能超过80个字符</span>",
                legal: "<span class='help'>描述包含非法字符</span>"
            }
        }
    });
});