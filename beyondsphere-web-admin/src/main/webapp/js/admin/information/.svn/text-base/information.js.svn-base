reloadList(1);

$(function(){
	$('#create').on('click', function (event) {
	    event.preventDefault();
    	$('#CreateInfoModalContainer').load('information/create', '', function () {
            $('#CreateInfoModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
	});
})


function reloadList(page) {
    var limit = $('#limit').val();
    var search = $('#search').val();
    getInfoList(page, limit);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
}

function getInfoList(page, limit) {
    $('#tablebody').html("");
    $.ajax({
        type: 'get',
        url: 'HostInformation/InformationList',
        data: {page: page, limit: limit},
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
                    var infoid = obj.infoid;
                    var username = decodeURIComponent(obj.username);
                    var vlanid = "Vlan-"+obj.vlanid;
                    var ipsegment = obj.ipsegment;
                    var routerip = obj.routerip;
                    var netmask=obj.netmask;
                    
                    tableStr+= '<tr infoid=' + infoid + '><td class="rcheck"><input type="checkbox" name="inforow"/></td>'
                        + '<td><a class="username">' + username + '</a></td><td>' + vlanid + '</td><td>' + ipsegment 
                        + '</td><td>' + routerip + '</td><td>' + netmask + '</td></tr>';
                }
                $('#tablebody').html(tableStr);
            }
        }
    });
}

$('#delete').on('click', function (event) {
    event.preventDefault();
    var num=0;
    var infoList = "";
    $('input[name="inforow"]:checked').each(function () {
        infoList += "[" + $(this).parent().parent().attr("username") + "]&nbsp;";
        num++;
    });
    
    if(num==0){
    	return false;
    }
    bootbox.dialog({
        message: '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;确认删除这些记录吗?</div>',
        title: "提示",
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $('input[name="inforow"]:checked').each(function () {
                    	deleteInfo($(this).parent().parent().attr("infoid"));
                        
                    });
                    removeAllCheck();
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
function deleteInfo(infoid) {
    $.ajax({
        type: 'get',
        url: 'HostInformation/Delete',
        data: {infoid: infoid},
        dataType: 'json',
        success: function (obj) {
            if (obj) {
                reloadList(1);
            }
        }
    });
}

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='inforow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='inforow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});
function removeAllCheck() {
    $('input[name="inforow"]').each(function () {
        $(this).attr("checked", false);
    });
}
function selectAllCheck() {
    $('input[name="userrow"]').each(function () {
        $(this).attr("checked", true);
    });
}