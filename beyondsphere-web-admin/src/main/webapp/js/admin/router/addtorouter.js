$(document).ready(function () {
    $('#linkRouter').on('click', function (event) {
        event.preventDefault();
//        var valid = $("#create-form").valid();
//        if (valid) {
            var vnetid = $('#modalcontent').attr('vnetid');
            var vn_router = $("#vn_router").val();
            var vn_net = $("#vn_net").val();
            var vn_gate = $("#vn_gate").val();
            var dhcp_status = $('input[name="dhcp_status"]:checked').val();
//            var vn_start = $("#vn_start").val();
//            var vn_end = $("#vn_end").val();
//            if (checkRepeat(vn_router, vn_net)) {
            	var content = "<div class='alert alert-warning'>私有网络正在加入路由器</div>";
            	var conid = showMessageNoAutoClose(content);
                $.ajax({
                    type: 'post',
                    url: 'NetworkAction/LinkRouter',
                    data: {
                        vnetuuid: vnetid,
                        routerid: vn_router,
                        net: vn_net,
                        gate: vn_gate,
                        start: 2,
                        end: 254,
                        dhcpState: 0,
                        content: content,
                        conid: conid
                    },
                    dataType: 'json',
                    success: function (obj) {
                        if (obj.result) {
		                    $.getScript($('#bath_url').val()+'js/admin/network/network.js', function() {
		                       	changeModelDiv(2);
		                    });
                        }else{
                        }
                        
                    }
                });
                $('#VnetModalContainer').modal('hide');
                $('input[name="vnrow"]:checked').each(function () {
                    $(this)[0].checked = false;
                    $(this).change();
                });
//            } else {
//                bootbox.dialog({
//                    className: "oc-bootbox",
//                    message: '<div class="alert alert-info" style="margin:10px">'
//                        + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;网络地址段重复</div>',
//                    title: '重复提示',
//                    buttons: {
//                        main: {
//                            label: "确定",
//                            className: "btn-primary",
//                            callback: function () {
//                            }
//                        }
//                    }
//                });
//            }
//        }
    });

    function checkRepeat(routerid, net) {
        var result = false;
        $.ajax({
            type: 'post',
            async: false,
            url: 'NetworkAction/CheckNet',
            data: {routerid: routerid, net: net},
            dataType: 'json',
            success: function (obj) {
                result = obj.result;
            }
        });
        return result;
    }

    $('input[name="dhcp_status"]').on('change', function (event) {
        event.preventDefault();
        var value = $(this).attr("value");
        if (value == "0") {
            $('#ip-range').hide();
        } else {
            $('#ip-range').show();
        }
    });

    $("#vn_net").blur(function (e) {
        var net = $(this).val();
        $('#vn_net1').val(net);
        $('#vn_net2').val(net);

        if (!checkaddr(net, 1, 254)) {
            $("#vn_net").parent().find('.oc-error').remove();
            $("#vn_net").parent().append('<label class="oc-error oc-error1"><span class="help">地址不合法</span></label>');
        } else {
            $("#vn_net").parent().find('.oc-error').remove();
        }
    });

    $("#vn_gate").blur(function (e) {
        if (!checkaddr($(this).val(), 0, 254)) {
            $("#vn_gate").parent().find('.oc-error').remove();
            $("#vn_gate").parent().append('<label class="oc-error oc-error2"><span class="help">地址不合法</span></label>');
        } else {
            $("#vn_gate").parent().find('.oc-error').remove();
        }
    });

    $("#vn_start").blur(function (e) {
        $("#vn_start").parent().find('.oc-error').remove();
        var start = $(this).val();
        var end = $('#vn_end').val();
        if (!checkaddr(start, 0, 254)) {
            $("#vn_start").parent().find('.oc-error').remove();
            $("#vn_start").parent().append('<label class="oc-error oc-error3"><span class="help">地址不合法</span></label>');
        } else {
            $("#vn_start").parent().find('.oc-error').remove();
        }
        if (!checkaddr(end, start, 254)) {
            $('#vn_end').val(254);
        }
    });

    $("#vn_end").blur(function (e) {
        var start = $('#vn_start').val();
        var end = $(this).val();
        if (!checkaddr(end, start, 254)) {
            $('#vn_end').val(254);
        }
    });


    function checkaddr(addr, num1, num2) {
        if (addr == "") {
            return false;
        }
        if ($.isNumeric(addr)) {
            if (addr >= num1 && addr <= num2) {
                return true;
            }
        }
        return false;
    }

    $("#create-form").validate({
        rules: {
            vn_net: {
                required: true
            },
            vn_gate: {
                required: true
            },
            vn_start: {
                required: true
            },
            vn_end: {
                required: true
            }
        },
        messages: {
            vn_net: {
                required: "<span class='help'>地址不能为空</span>"
            },
            vn_gate: {
                required: "<span class='help'>地址不能为空</span>"
            },
            vn_start: {
                required: "<span class='help'>地址不能为空</span>"
            },
            vn_end: {
                required: "<span class='help'>地址不能为空</span>"
            }
        }
    });
    
    
    loadRouterList();
});


function loadRouterList() {
    var vnetid = '';
    $('input[name="switchrow"]:checked').each(function () {
        vnetid = $(this).parent().parent().attr("id");
    });
    $('#modalcontent').attr('vnetid', vnetid);
    $.ajax({
        type: 'get',
        async: false,
        url: 'RouterAction/TableRTs',
        dataType: 'json',
        success: function (array) {
            var vmlistHtml = '';
            if (0 == array.length) {
                vmlistHtml = '<option>没有路由器可连接</option>';
                $('#vn_router').attr('disabled', true).addClass('oc-disable');
                $('#vn_router').html(vmlistHtml);
                $('#linkRouter').attr('disabled', true);
                $('#linkRouter').addClass('btn-forbidden');
            } else {
                $('#vn_router').attr('disabled', false);
                $('#linkRouter').attr('disabled', false).removeClass('oc-disable');
                $('#linkRouter').removeClass('btn-forbidden');
                for (var i = 0; i < array.length; i++) {
                	if(array[i].power==1){
                		vmlistHtml = vmlistHtml + '<option value="' + array[i].uuid + '">rt-'
                		+ array[i].uuid.substring(0, 8) + '&nbsp;(' + decodeURIComponent(array[i].rtname) + ')</option>';
                	}
                }
                $('#vn_router').html(vmlistHtml);
            }
        }
    });
}

///展示一个提示
function showMessageNoAutoClose(content) {
   var showid = $.sticky(content,{'autoclose':false});
   arrayid = localStorage.getItem('alertStr');
   if(arrayid == null)
   {
      arrayid="";
   }
   setTimeout("",500);
   arrayid += '<div class="sticky" id="'
			+ showid.id
			+ '" style="height: 38px; display: block;"><span class="close sticky-close" rel="'
			+ showid.id + '" title="Close">×</span><div class="sticky-note" rel="'
			+ showid.id
			+ '">'+ content +'</div></div>---';
   localStorage.setItem('alertStr',arrayid);
   return showid.id;
}