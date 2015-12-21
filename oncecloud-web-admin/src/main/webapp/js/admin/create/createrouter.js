$(document).ready(function () {
	//加载用户信息
	loadUserList();
});


$('#createRouterAction').on('click', function (event) {
    event.preventDefault();
    var valid = $("#create-form").valid();
    if (valid) {
        var name = $('#rt_name').val();
        var capacity =250;
        var firewall = $('#').val();
        var rtuuid = uuid.v4();
       preCreateRouter(rtuuid, name, capacity, firewall);
        $('#RouterModalContainer').modal('hide');
    }
});

function preCreateRouter(rtuuid, name, capacity, firewall) {
    var showid = "rt-" + rtuuid.substring(0, 8);
    var stateStr = '<td><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">创建中</span></td>';
    var thistr = '<tr rowid="' + rtuuid + '" rtname="' + name + '"><td class="rcheck"><input type="checkbox" name="rtrow"></td>'
        + '<td><a class="id">' + showid + '</a></td><td>' + name + '</td><td></td>' + stateStr + '<td name="sip"><a>(基础网络)</a></td><td name="eip"></td>'
        + '<td class="time"><1分钟</td></tr>';
    $("#router_tablebody").prepend(thistr);
    createRouter(rtuuid, name, capacity, firewall);
}

function createRouter(rtuuid, name, capacity, firewall) {
	var userid=$('#rt_user').val();
    $.ajax({
        type: 'post',
        url: 'RouterAction/Create',
        data: {uuid: rtuuid, name: name, userid: userid},
        dataType: 'json',
        success: function (obj) {
        	if(!obj){
        		alert("创建路由失败！");
        	}
        	$.getScript($('#bath_url').val()+'js/admin/router/router.js', function() {
        		reloadList(1);
        	});
        }
    });
}

$('input:radio').change(function () {
    var rttype = $('input:radio:checked').val();
    setTypeInfo(rttype);
});
function setTypeInfo(rttype) {
    if (250 == rttype) {
        $('#price-hour').text('0.02');
        $('#price-total').text('0.02');
        $('#price-month').text('14.40');
        $('#typeinfo').text('推荐用于1个私有网络，峰值带宽250Mbps');
    } else if (500 == rttype) {
        $('#price-hour').text('0.04');
        $('#price-total').text('0.04');
        $('#price-month').text('28.80');
        $('#typeinfo').text('推荐用于2个私有网络，峰值带宽500Mbps');
    } else if (1000 == rttype) {
        $('#price-hour').text('0.08');
        $('#price-total').text('0.08');
        $('#price-month').text('57.60');
        $('#typeinfo').text('推荐用于3~4个私有网络，峰值带宽1000Mbps');
    }
}

$("#create-form").validate({
    rules: {
        rt_name: {
            required: true,
            maxlength: 20,
            legal: true
        },
        rt_type: {
            required: true
        }
    },
    messages: {
        rt_name: {
            required: "<span class='help'>名称不能为空</span>",
            maxlength: "<span class='help'>名称不能多于20个字符</span>",
            legal: "<span class='help'>主机名称包含非法字符</span>"
        },
        rt_type: {
            required: "<span class='help'>请选择一种类型</span>"
        }
    }
});


function loadUserList() {
    $.ajax({
        type: 'get',
        async: false,
        url: 'HostInformation/unRouterBindUsers',
        dataType: 'json',
        success: function (array) {
            var vmlistHtml = '';
            if (0 == array.length) {
                vmlistHtml = '<option>没有用户可以选择</option>';
                $('#rt_user').attr('disabled', true).addClass('oc-disable');
                $('#rt_user').html(vmlistHtml);
            } else {
                $('#rt_user').attr('disabled', false);
                for (var i = 0; i < array.length; i++) {
                    vmlistHtml = vmlistHtml + '<option value="' + array[i].userid + '">'
                       + decodeURIComponent(array[i].username) + '</option>';
                }
                $('#rt_user').html(vmlistHtml);
            }
        }
    });
}