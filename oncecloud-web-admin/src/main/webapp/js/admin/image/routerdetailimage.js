///add by cyh 
$(function(){
	init();
})


function init() {
    $('.view-types').on('click', '.view-type', function (event) {
        event.preventDefault();
        $('a', $('.view-types')).removeClass('current');
        $(this).addClass('current');
        var type = $(this).attr("oc-type");
        if (type == 'text') {
            $("#textview").show();
            $("#imageview").hide();
        } else {
            $("#textview").hide();
            $("#imageview").show();
        }
    });

   
    $(".graph-actions a").mouseenter(function () {
        if (!$(this).hasClass("btn-forbidden")) {
            $(this).find(".text").show();
        }
    }).mouseleave(function () {
        $(this).find(".text").hide();
    });

    $(".components").on("mouseenter",".component-router-sg",function(){
    	 $(this).find("#changefirewall").show();
    });
    
    $(".components").on("mouseleave",".component-router-sg",function(){
    	 $(this).find("#changefirewall").hide();
    });
    
    $("#changefirewall").click(function(){
    	bingfirewall("rt");
    });

    $('#startup').on('click', function (event) {
        event.preventDefault();
        showbox(0);
    });
    $('#destroy').on('click', function (event) {
        event.preventDefault();
        showbox(1);
    });
    $('#shutdown').on('click', function (event) {
        event.preventDefault();
        showbox(2);
    });

    $("#componentsId").on("click", "#routernoip", function () {
        bindpublicip("rt");
    });

    $("#componentsId").on("click", "#deleteIp", function () {
        unbingpublicip("rt");
    });
    
    $(".components").on("mouseenter","#addvnet",function(){
    	 $(this).find(".text").show();
    }).on("mouseleave","#addvnet",function(){
    	 $(this).find(".text").hide();
    });
    
   /* $(".components").on("mouseenter",".add-instance-btn",function(){
   	 $(this).find(".text").show();
   }).on("mouseleave","#addinstance",function(){
   	 $(this).find(".text").hide();
   });*/
    
    $(".components").on("click", "#addvnet", function () {
        addvnet();
    });
    
    $(".components").on("click", ".btn-delete-vxnet", function () {
    	unlink($(this).attr("vnetid"));
    });
    
    $(".components").on("click", ".btn-delete-instance", function () {
    	unlinkinstance($(this).attr("intanceuuid"));
    });
    
    $(".components").on("click", ".add-instance-btn", function () {
    	addinstance($(this).attr("vnetid"));
    });
    
    
    
    ////过滤器功能
  
    
    $('.rule-refresh').on('click', function (event) {
    	filterreloadList(1);
    });

    $('#filtertablebody').on('change', 'input:checkbox', function (event) {
        event.preventDefault();
        allDisable();
        var count = 0;
        $('input[name="rulerow"]:checked').each(function () {
            count++;
        });
        if (count > 0) {
            $('#deleterule').removeClass('btn-disable').attr('disabled', false);
        }
    });
    
    $('#createrule').on('click', function (event) {
        event.preventDefault();
        $('#RouterModalContainer').load($(this).attr('url'), '', function () {
            $('#RouterModalContainer').modal({
                backdrop: false,
                show: true
            });
        });
    });
    
    $('#deleterule').on('click', function (event) {
        event.preventDefault();
        var firewallId =  $("#hide_innerfirewall").val();
        $('input[name="rulerow"]:checked').each(function () {
            var ruleId = $(this).parent().parent().attr("ruleid");
            deleteRule(ruleId, firewallId);
        });
        removeAllCheck();
    });
    
    $('#filterconfirm').on('click', function (event) {
        event.preventDefault();
        var firewallId = $("#hide_innerfirewall").val();
        updateFirewall(firewallId);
        removeAllCheck();
    });
    
    $('#limit').unbind();
    $('#limit').on('focusout', function () {
        var limit = $("#limit").val();
        var reg = /^[0-9]*[1-9][0-9]*$/;
        if (!reg.test(limit)) {
            $("#limit").val(10);
        }
        filterreloadList(1);
    });

    
    $('#filtertablebody').on('click', '.operate', function (event) {
        event.preventDefault();
        var thistd = $(this);
        var ruleId = $(this).parent().attr('ruleid');
        var ruleState = $(this).parent().attr('state');
        var firewallId =  $("#hide_innerfirewall").val();
        $.ajax({
            type: 'get',
            url: '/FirewallAction/OperateRule',
            data: {ruleId: ruleId},
            dataType: 'json',
            success: function (obj) {
                if (obj.isSuccess) {
                    if (ruleState == '1') {
                        $(thistd).parent().attr('state', '0');
                        $(thistd).parent().addClass('idle');
                        $(thistd).html('<a>启用</a>');
                    } else if (ruleState == '0') {
                        $(thistd).parent().attr('state', '1');
                        $(thistd).parent().removeClass('idle');
                        $(thistd).html('<a>禁用</a>');
                    }
                    $('#filterconfirm').removeClass('btn-default').addClass('btn-primary');
                    $('#suggestion').show();
                }
            }
        });
    });
}

///过滤器功能 start
function filterreloadList(page) {
    var limit = $('#limit').val();
    var firewallId =  $("#hide_innerfirewall").val();
    getfilterRuleList(page, limit, "", firewallId);
    if (page == 1) {
        options = {
            currentPage: 1
        };
        $('#pageDivider').bootstrapPaginator(options);
    }
    allDisable();
}


function removeAllCheck() {
    $('input[name="rulerow"]:checked').each(function () {
        $(this)[0].checked = false;
        $(this).change();
    });
}

function allDisable() {
    $('#deleterule').addClass('btn-disable').attr('disabled', true);
}

function getfilterRuleList(page, limit, search, firewallId) {
    $('#filtertablebody').html("");
    $.ajax({
        type: 'get',
        url: '/FirewallAction/RuleList',
        data: {page: page, limit: limit, search: search, uuid: firewallId},
        dataType: 'json',
        success: function (array) {
            var totalnum = array[0].total;
            var totalp = 1;
            if (totalnum != 0) {
                totalp = Math.ceil(totalnum / limit);
            }
            options = {
                totalPages: totalp
            };
            $('#pageDivider').bootstrapPaginator(options);
            pageDisplayUpdate(page, totalp);
            if (array[0].confirm == 0) {
                $('#filterconfirm').removeClass('btn-default').addClass('btn-primary');
                $('#suggestion').show();
            }
            var tableStr = "";
            for (var i = 1; i < array.length; i++) {
                var obj = array[i];
                var ruleId = obj.ruleId;
                var ruleName = decodeURIComponent(obj.ruleName);
                var rulePriority = obj.rulePriority;
                var ruleProtocol = obj.ruleProtocol;
                var ruleSport = obj.ruleSport;
                var ruleEport = obj.ruleEport;
                var ruleIp = obj.ruleIp;
                var ruleState = obj.ruleState;
                var opStr = '';
                var trClass = "";
                if (ruleState == 1) {
                    opStr = '<td class="operate"><a href="javascript:void(0)">禁用</a></td>';
                } else if (ruleState == 0) {
                    opStr = '<td class="operate"><a href="javascript:void(0)">启用</a></td>';
                    trClass = 'class="idle"';
                }
                if (ruleIp == "") {
                    ruleIp = "所有地址";
                }
                var thistr = '<tr ruleid="' + ruleId + '" state="' + ruleState + '"' + trClass + '><td class="rcheck"><input type="checkbox" name="rulerow"></td><td name="rulename">'
                    + ruleName + '</td><td name="priority">' + rulePriority + '</td><td name="protocol">'
                    + ruleProtocol + '</td><td name="sport">' + ruleSport + '</td><td name="eport">'
                    + ruleEport + '</td><td name="ip">' + ruleIp + '</td>' + opStr + '</tr>';
                tableStr += thistr;
            }
            $('#filtertablebody').html(tableStr);
        }
    });
}


function deleteRule(ruleId, firewallId) {
    var thistr = $("#filtertablebody").find('[ruleid="' + ruleId + '"]');
    $.ajax({
        type: 'post',
        url: '/FirewallAction/DeleteRule',
        data: {ruleId: ruleId, firewallId: firewallId},
        dataType: 'json',
        success: function (obj) {
            if (obj.result == true) {
                $(thistr).remove();
                $('#deleterule').addClass('btn-disable').attr('disabled', true);
                $("#filterconfirm").removeClass('btn-default').addClass('btn-primary');
                $("#suggestion").show();
               // getFirewallBasicList();
            }
        }
    });
}

function updateFirewall(firewallId) {
    $.ajax({
        type: 'get',
        url: '/FirewallAction/UpdateFirewallForRouteInner',
        data: {firewallId: firewallId},
        dataType: 'json',
        success: function (obj) {
            if (obj.result == true) {
                $("#filterconfirm").removeClass('btn-primary').addClass('btn-default');
                $("#suggestion").hide();
            }
        }
    });
}

///过滤器功能 end



function addvnet()
{
    $('#RouterModalContainer').load($("#platformcontent").attr('basepath')+'vnet/bindtorouter', '', function () {
        $('#RouterModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
}

function addinstance(vnetid)
{
    $('#RouterModalContainer').load($("#platformcontent").attr('basepath')+'vnet/bindvmimage',  {vnetid: vnetid}, function () {
        $('#RouterModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
}


function unlink(uuid) {
	
	    var infoList = $("#platformcontent").attr("showid");
	    var showMessage = '';
	    var showTitle = '';
	   
        showMessage = '<div class="alert alert-info" style="margin:10px 10px 0">离开路由器后,该网络内将无法访问外部网络.是否继续</div>';
        showTitle = "离开路由器" + '&nbsp;' + infoList + '?';
	    
	    bootbox.dialog({
	        className: "oc-bootbox",
	        message: showMessage,
	        title: showTitle,
	        buttons: {
	            main: {
	                label: "确定",
	                className: "btn-primary",
	                callback: function () {
	                	 $.ajax({
	                            type: 'post',
	                            url: '/VnetAction/UnlinkRouter',
	                            data: {vnetId: uuid},
	                            dataType: 'text',
	                            complete: function () {
	                            	getVxnets();
	                            }
	                        });
	                }
	            },
	            cancel: {
	                label: "取消",
	                className: "btn-default",
	                callback: function () {
	                }
	            }
	        }
	    });
   
}

function unlinkinstance(uuid) {

    var showMessage = '';
    var showTitle = '';
   
    showMessage = '<div class="alert alert-info" style="margin:10px 10px 0">解绑网络后将无法联网，确定 ?</div>';
    showTitle = "提示" + '&nbsp;';
    
    bootbox.dialog({
        className: "oc-bootbox",
        message: showMessage,
        title: showTitle,
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    $.ajax({
                        type: 'post',
                        url: '/VMAction/UnbindNet',
                        data: {uuid: uuid},
                        dataType: 'json',
                        success: function (obj) {
                            if (obj.result) {
                            	getVxnets();
                            }
                        }
                    });
                }
            },
            cancel: {
                label: "取消",
                className: "btn-default",
                callback: function () {
                }
            }
        }
    });

}


function showbox(type) {
    var infoList =  $("#platformcontent").attr("showid");
    var infoArray = new Array("启动路由器", "销毁路由器", "关闭路由器");
    var showMessage = '';
    var showTitle = '';
    if (type == 2) {
        showMessage = '<div class="alert alert-info" style="margin:10px 10px 0">1. 强制关机会丢失内存中的数据<br/>'
            + '2. 为保证数据的完整性，请在强制关机前暂停所有文件的写操作，或进行正常关机。</div>'
            + '<div class="item" style="margin:0"><div class="controls" style="margin-left:100px">'
            + '<label class="inline"><input type="checkbox" id="force">&nbsp;强制关机</label></div></div>';
        showTitle = infoArray[type] + '&nbsp;' + infoList + '?';
    } else {
        showMessage = '<div class="alert alert-info" style="margin:10px">'
            + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;'
            + infoArray[type] + '&nbsp;' + infoList + '?</div>';
        showTitle = '提示';
    }
    bootbox.dialog({
        className: "oc-bootbox",
        message: showMessage,
        title: showTitle,
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary",
                callback: function () {
                    var uuid = $("#platformcontent").attr("routerUuid");
                    if (type == 0) {
                        startRouter(uuid);
                    } else if (type == 1) {
                        destroyRouter(uuid);
                    } else if (type == 2) {
                        var force = $('#force')[0].checked;
                        shutdownRouter(uuid, force);
                    }
                }
            },
            cancel: {
                label: "取消",
                className: "btn-default",
              
            }
        }
    });
}

///绑定公网ip
function bindpublicip(bdtype) {
    var infoList = $("#platformcontent").attr("showid");
    var tablelist = "";
    $.ajax({
        type: 'get',
        url: '/EIPAction/AvailableEIPs',
        dataType: 'json',
        success: function (array) {
            if (array.length > 0) {
                $.each(array, function (index, item) {
                    if (index == 0) {
                        tablelist = tablelist + '<div class="image-item selected" eid="' + item.eipUuid + '" eip="' + item.eipIp + '"><div class="image-left">' + decodeURIComponent(item.eipName) + '</div>IP:&nbsp;&nbsp;' + item.eipIp + '</div>';
                    } else {
                        tablelist = tablelist + '<div class="image-item" eid="' + item.eipUuid + '" eip="' + item.eipIp + '"><div class="image-left">' + decodeURIComponent(item.eipName) + '</div>IP:&nbsp;&nbsp;' + item.eipIp + '</div>';
                    }
                });
            }

            ///1 查询出所有可用的 公网ip。
            var showMessage = '<div class="alert alert-warning" style="margin:10px 30px">默认情况下，除了少数安全端口之外，主机的大部分端口都是关闭的，您需要在防火墙中打开相应的下行规则以允许外网访问。</div>'
                + '<div class="epubliciplist" id="epubliciplist" style="margin:20px 30px">' + tablelist + '</div>';

            var showTitle = '选择路由器&nbsp;' + infoList + '&nbsp;要绑定的公网IP';

            bootbox.dialog({
                className: "oc-bootbox",
                message: showMessage,
                title: showTitle,
                buttons: {
                    main: {
                        label: "确定",
                        className: "btn-primary",
                        callback: function () {
                            var uuid = $("#epubliciplist").find(".selected").attr("eid");
                            var eipIp = $("#epubliciplist").find(".selected").attr("eip");
                            var router = $("#platformcontent").attr("routerUuid");
                          if (bdtype == "rt") {
                                $.ajax({
                                    type: 'post',
                                    url: '/EIPAction/Bind',
                                    data: {vmUuid: router, eipIp: eipIp, bindType: bdtype},
                                    dataType: 'json',
                                    success: function (obj) {
                                        if (obj.result == true) {
                                            $("#routerip .component-id").text(eipIp);
                                            $("#routernoip").hide();
                                            $("#routerip").show();
                                        } else {

                                        }
                                    },
                                    error: function () {
                                    }
                                });
                            }

                        }
                    },
                    cancel: {
                        label: "取消",
                        className: "btn-default",
                        callback: function () {
                        }
                    }
                }
            });
            $("#epubliciplist").parents(".modal-dialog").width(500);
            $('.epubliciplist').on('click', '.image-item', function (event) {
                event.preventDefault();
                $('div', $('.epubliciplist')).removeClass('selected');
                $(this).addClass('selected');
            });
        },
        error: function () {

        }
    });


}

function unbingpublicip(bdtype) {
    var vmuuid = "";
    var eipIp = "";
    if (bdtype = "rt") {
        vmuuid = $("#platformcontent").attr("routerUuid");
        eipIp = $("#routerip .component-id").text();
    }
    $.ajax({
        type: 'post',
        url: '/EIPAction/UnBind',
        data: {eipIp: eipIp, vmUuid: vmuuid, bindType: bdtype},
        dataType: 'json',
        success: function (obj) {
            if (bdtype = "rt") {
                $("#routerip .component-id").text("");
                $("#routernoip").show();
                $("#routerip").hide();
            }
        },
        error: function () {
        }
    });
}

function bingfirewall(bdtype) {
	var infoList = $("#platformcontent").attr("showid");
    var tablelist = "";
    $.ajax({
        type: 'post',
        url: '/FirewallAction/AvailableFirewalls',
        dataType: 'json',
        success: function (array) {
            if (array.length > 0) {
                $.each(array, function (index, item) {
                    if (index == 0) {
                        tablelist = tablelist + '<div class="image-item selected" firewallId="' + item.firewallId + '" firewallName="' + decodeURIComponent(item.firewallName) + '"><div class="image-left">' + "fw-" + item.firewallId.substring(0, 8) + '</div>&nbsp;&nbsp;' + decodeURIComponent(item.firewallName) + '</div>';
                    } else {
                        tablelist = tablelist + '<div class="image-item" firewallId="' + item.firewallId + '" firewallName="' + decodeURIComponent(item.firewallName) + '"><div class="image-left">' + "fw-" + item.firewallId.substring(0, 8) + '</div>&nbsp;&nbsp;' + decodeURIComponent(item.firewallName) + '</div>';
                    }
                });
            }

            ///1 查询出所有可用的 公网ip。
            var showMessage = '<div class="epubliciplist" id="epubliciplist" style="margin:20px 30px">' + tablelist + '</div>';

            var showTitle = '选择路由器&nbsp;' + infoList + '&nbsp;要绑定的防火墙';

            bootbox.dialog({
                className: "oc-bootbox",
                message: showMessage,
                title: showTitle,
                buttons: {
                    main: {
                        label: "确定",
                        className: "btn-primary",
                        callback: function () {
                            var firewallId = $("#epubliciplist").find(".selected").attr("firewallId");
                            var firewallName = $("#epubliciplist").find(".selected").attr("firewallName");
                            var router = "[" + $("#platformcontent").attr("routerUuid") + "]";
                            if (bdtype == "rt") {
                                $.ajax({
                                    type: 'post',
                                    url: '/FirewallAction/Bind',
                                    data: {firewallId: firewallId, vmUuidStr: router, bindType: bdtype},
                                    dataType: 'json',
                                    success: function (response) {
                                        if (response.isSuccess) {
                                            $("#firewalldiv").find(".sg-name").text(firewallName);
                                        }
                                        else {
                                          
                                        }
                                    },
                                    error: function () {
                                    }
                                });
                            }

                        }
                    },
                    cancel: {
                        label: "取消",
                        className: "btn-default",
                        callback: function () {
                        }
                    }
                }
            });
            $("#epubliciplist").parents(".modal-dialog").width(500);
            $('.epubliciplist').on('click', '.image-item', function (event) {
                event.preventDefault();
                $('div', $('.epubliciplist')).removeClass('selected');
                $(this).addClass('selected');
            });
        },
        error: function () {

        }
    });
}





function startRouter(uuid) {
    $.ajax({
        type: 'post',
        url: '/RouterAction/StartUp',
        data: {uuid: uuid},
        dataType: 'json',
        complete:function(){
        	getRouterBasicList();
        }
    });
}

function destroyRouter(uuid) {
    $.ajax({
        type: 'post',
        url: '/RouterAction/Destroy',
        data: {uuid: uuid},
        dataType: 'text',
        success: function (obj) {
            if (obj == "no") {
                bootbox.dialog({
                    message: '<div class="alert alert-danger" style="margin:10px"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;无法删除，依然有私有网络依赖于该路由器</div>',
                    title: "提示",
                    buttons: {
                        main: {
                            label: "确定",
                            className: "btn-primary",
                            callback: function () {
                            }
                        }
                    }
                });
            }else
            	{
            	  window.location.href = '/router';
            	}
            
        }
    });
}

function shutdownRouter(uuid, force) {
    $.ajax({
        type: 'post',
        url: '/RouterAction/ShutDown',
        data: {uuid: uuid, force: force},
        dataType: 'text',
        complete:function(data){
        	reloadList(1);
        }
    });
}