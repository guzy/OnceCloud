getRouterBasicList();
getVxnets();
getpfList();

$('#RouterModalContainer').on('hide', function (event) {
    $(this).removeData("modal");
    $(this).children().remove();
});

$('#pf-refresh').on('click', function (event) {
    event.preventDefault();
    getpfList();
});

$('#modify').on('click', function (event) {
    event.preventDefault();
    var url = basePath + 'common/modify';
    var rtName = $('#rtname').text();
    var rtDesc = $('#rtdesc').text();
    var rtUuid = $('#platformcontent').attr("routerUuid");
    $('#RouterModalContainer').load(url, {
        "modifyType": "rt",
        "modifyUuid": rtUuid,
        "modifyName": rtName,
        "modifyDesc": rtDesc
    }, function () {
        $('#RouterModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#pf_create').on('click', function (event) {
    event.preventDefault();
    var url = basePath + 'router/forwadport';
    $('#RouterModalContainer').load(url, "", function () {
        $('#RouterModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

function openPPTP() {
    var showMessage = '';
    var showTitle = '';
    var result = true;
    showMessage = '<div class="alert alert-info" style="margin:10px;">'
        + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;打开 PPTP服务后请检查防火墙规则，确保 PPTP端口(默认为 UDP 1723)流量可以通过，否则从外网无法接入您的PPTP服务&nbsp;</div>';
    showTitle = '提示';
    var routerUuid = $('#platformcontent').attr("routerUuid");
    bootbox.dialog({
        className: "oc-bootbox",
        message: showMessage,
        title: showTitle,
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary"
                /*callback: function () {
                    $.ajax({
                        type: "post",
                        url: "/RouterAction/OpenPPTP",
                        data: {routerUuid: routerUuid},
                        dataType: "json",
                        success: function (obj) {
                        	if(obj.result) {
                        		$("#openpptp").attr("pptp", "close");
                        		$("#openpptp").html("[关闭]");
                        	}
                        },
                        error: function () {
                        	$("#openpptp").html("[打开]");
                        }
                    });
                }*/
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

function closePPTP() {
    var showMessage = '';
    var showTitle = '';
    var result = true;
    showMessage = '<div class="alert alert-info" style="margin:10px;">'
        + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;确定要关闭PPTP服务&nbsp;?</div>';
    showTitle = '提示';
    var routerUuid = $('#platformcontent').attr("routerUuid");
    bootbox.dialog({
        className: "oc-bootbox",
        message: showMessage,
        title: showTitle,
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary"
               /* callback: function () {
                    $.ajax({
                        type: "post",
                        url: "/RouterAction/ClosePPTP",
                        data: {routerUuid: routerUuid},
                        dataType: "json",
                        success: function (obj) {
                        	if(obj.result) {
                        		$("#openpptp").attr("pptp", "open");
                        		$("#openpptp").html("[打开]");
                        	}
                        },
                        error: function () {
                        	$("#openpptp").html("[关闭]");
                        }
                    });
                }*/
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

$('#openpptp').on('click', function (event) {
    event.preventDefault();
    var rownum = $("#pptp-user tr").length;
    if (rownum == 0) {
		bootbox.dialog({
			message : '<div class="alert alert-danger" style="margin:10px"><span class="glyphicon glyphicon-warning-sign"></span>&nbsp;请先设置账户</div>',
			title : "提示",
			buttons : {
				main : {
					label : "确定",
					className : "btn-primary",
					callback : function() {
					}
				}
			}
		});
	} else {
	    if($("#openpptp").attr("pptp") == "open") {
	    	$("#openpptp").html("");
	    	openPPTP();
	    } else if ($("#openpptp").attr("pptp") == "close") {
	    	$("#openpptp").html("");
	    	closePPTP();
	    }
	}
});

$('#add-pptp-user').on('click', function (event) {
    event.preventDefault();
    var url = basePath + 'pptpuser/create';
    $('#RouterModalContainer').load(url, "", function () {
        $('#RouterModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('.btn-refresh').unbind();
$('.btn-refresh').on('click', function (event) {
    event.preventDefault();
    getRouterBasicList();
});

$('#depend-list').on('click', '#firewallid', function (event) {
    var firewallId = $(this).attr("uuid");
    event.preventDefault();
    var form = $("<form></form>");
    form.attr("action", "firewall/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="firewallId" value="' + firewallId
        + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

function getRouterBasicList() {
    /*$('#basic-list').html("");
    var routerUuid = $('#platformcontent').attr("routerUuid");
    $.ajax({
        type: 'get',
        url: '/RouterAction/RouterDetail',
        data: {
            uuid: routerUuid
        },
        dataType: 'json',
        success: function (obj) {
            var rtName = decodeURIComponent(obj.routerName);
            var rtDesc = decodeURIComponent(obj.routerDesc);
            var rtIp = obj.routerIp;
            $("#platformcontent").attr("rtip", rtIp);
            var rtMac = obj.routerMac;
            var rtStatus = obj.routerStatus;
            if (rtStatus == 2) {
                $('#fe_apply').removeClass('btn-default')
                    .addClass('btn-primary');
                $('#suggestion').show();
            }
            var rtCapacity = obj.routerCapacity;
            var rtPower = obj.routerPower;
            var rtFirewall = obj.routerFirewall;
         
            var rtInnerFirewall = obj.routerInnerFirewall;
            $("#hide_innerfirewall").val(rtInnerFirewall);
            
            var rtFirewallName = decodeURIComponent(obj.routerFirewallName);
            var createDate = obj.createDate;
            var useDate = decodeURIComponent(obj.useDate);
            var stateStr = '';
            var showstr = '';
            var showuuid = "rt-" + routerUuid.substring(0, 8);
            if (rtPower == 0) {
                stateStr = '<td><span id="rt-status" class="icon-status icon-stopped" name="stateicon"></span><span name="stateword">已关机</span></td>';
            } else if (rtPower == 1) {
                stateStr = '<td><span id="rt-status" class="icon-status icon-running" name="stateicon"></span><span name="stateword">活跃</span></td>';
            } else if (rtPower == 2) {
                stateStr = '<td><span id="rt-status" class="icon-status icon-process" name="stateicon"></span><span name="stateword">创建中</span></td>';
            } else if (rtPower == 3) {
                stateStr = '<td><span id="rt-status" class="icon-status icon-process" name="stateicon"></span><span name="stateword">销毁中</span></td>';
            } else if (rtPower == 4) {
                stateStr = '<td><span id="rt-status" class="icon-status icon-process" name="stateicon"></span><span name="stateword">启动中</span></td>';
            } else if (rtPower == 5) {
                stateStr = '<td><span id="rt-status" class="icon-status icon-process" name="stateicon"></span><span name="stateword">关机中</span></td>';
            }
            showstr = "<a class='id'>" + showuuid + '</a>';
            if ("&nbsp;" != rtFirewall) {
                rtFirewall = '<a id="firewallid" uuid="' + rtFirewall + '">fw-'
                    + rtFirewall.substring(0, 8) + '</a>';
            }
            var network;
            if (rtIp == "null") {
                network = '<a class="id">(基础网络)</a>';
            } else {
                network = '<a class="id">(基础网络)&nbsp;/&nbsp;' + rtIp + '</a>';
            }
            var rtEip = obj.eip;
            var rtEipUuid = obj.eipUuid;
            if (rtEip == "") {
                rtEip = "&nbsp;";
            } else {
                rtEip = '<a class="id" id="eip" eipip="' + rtEip + '" eipid="'
                    + rtEipUuid + '">' + rtEip + '</a>';
            }
            var pptpStatus = obj.pptpStatus;
            if (pptpStatus == 0) {
            	$("#openpptp").attr("pptp", "open");
                $("#openpptp").html("[打开]");
            } else {
            	$("#openpptp").attr("pptp", "close");
                $("#openpptp").html("[关闭]");
            }
            $('#basic-list')
                .html('<dt>ID</dt><dd>' + showstr
                    + '</dd><dt>名称</dt><dd id="rtname">' + rtName
                    + '</dd><dt>描述</dt><dd id="rtdesc">' + rtDesc
                    + '</dd><dt>状态</dt><dd>' + stateStr
                    + '</dd><dt>峰值带宽</dt><dd>' + rtCapacity
                    + '&nbsp;Mbps</dd><dt>Mac地址</dt><dd>' + rtMac
                    + '</dd><dt>创建时间</dt><dd class="time">'
                    + createDate
                    + '</dd><dt>运行时间</dt><dd class="time">' + useDate
                    + '</dd>');
            $('#depend-list').html('<dt>网络</dt><dd>' + network
                + '</dd><dt>公网IP</dt><dd>' + rtEip
                + '</dd><dt>防火墙</dt><dd>' + rtFirewall + '</dd>');

            if (rtPower == 0) {
                $("#startup").removeClass("btn-forbidden");
                $("#shutdown").addClass("btn-forbidden");

            } else if (rtPower == 1) {
                $("#startup").addClass("btn-forbidden");
                $("#shutdown").removeClass("btn-forbidden");
            }

            /// 现实ip，防火墙，等各种信息
            var rtEip = obj.eip;
            var rtEipUuid = obj.eipUuid;
            if (rtEip == "") {
                $("#routerip .component-id").text("");
                $("#routerip").hide();
                $("#routernoip").show();
            } else {
                $("#routerip .component-id").text(rtEip);
                $("#routernoip").hide();
                $("#routerip").show();
            }

            $("#firewalldiv").find(".sg-name").text(rtFirewallName);

            $("#routeDiv").find(".private-ip").text(rtIp == "null" ? "" : rtIp);
            $("#routeDiv").find(".router-id").text(showuuid);
       
        }
    });*/
}

$('#depend-list').on('click', '#eip', function (event) {
    event.preventDefault();
    var eip = $(this).attr("eipip");
    var eipUuid = $(this).attr("eipid");
    var form = $("<form></form>");
    form.attr("action", "/elasticip/detail");
    form.attr('method', 'post');
    var input1 = $('<input type="text" name="eip" value="' + eip + '" />');
    var input2 = $('<input type="text" name="eipUuid" value="' + eipUuid
        + '" />');
    form.append(input1);
    form.append(input2);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#apply').on('click', function (event) {
    event.preventDefault();
    var rtstate = $('#rt-status').hasClass('icon-running');
    if (rtstate) {
        var routerUuid = $('#platformcontent').attr("routerUuid");
        $.ajax({
            type: 'get',
            url: '/RouterAction/ApplyRouter',
            data: {
                routerUuid: routerUuid
            },
            dataType: 'json',
            success: function (obj) {
                if (obj.result == true) {
                    $("#apply").removeClass('btn-primary')
                        .addClass('btn-default');
                    $("#suggestion").hide();
                }
            }
        });
    } else {
        var message = '<div class="alert alert-info" style="margin:10px"><span class="glyphicon glyphicon-info-sign"></span>&nbsp;负载均衡处于不活跃状态，无法进行更新</div>';
        bootbox.dialog({
            className: "oc-bootbox",
            message: message,
            title: '状态提示',
            buttons: {
                main: {
                    label: "确定",
                    className: "btn-primary"
                }
            }
        });
    }
});

function needApply() {
    $('#apply').removeClass('btn-default').addClass('btn-primary');
    $('#suggestion').show();
}

$('.filter-once-tab').on('click', '.tab-filter', function (event) {
    event.preventDefault();
    $('li', $('.filter-once-tab')).removeClass('selected');
    $(this).addClass('selected');
    var type = $(this).attr("type").toString();
    $(".pane-filter").hide();
    $("#" + type).show();
    
    if(type=="filtering")
      filterreloadList(1);
});

$('#vxnets-t').on('click', '.id', function (event) {
    event.preventDefault();
    var uuid = $(this).parent().parent().attr('rowid');
    var form = $("<form></form>");
    form.attr("action", "/instance/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="instanceUuid" value="' + uuid
        + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

function getVxnets() {
    $('#vxnets-t').html("");
    var routerUuid = $('#platformcontent').attr("routerUuid");
    /*$.ajax({
        type: 'get',
        async: false,
        url: '/RouterAction/Vxnets',
        data: {
            routerUuid: routerUuid
        },
        dataType: 'json',
        success: function (array) {
            ///cyh可视化 绑定私有网络
            $(".tree").html("");

            if (array.length == 0) {
                var _p = $('<p></p>');
                _p.addClass("none");
                _p.text("当前没有私有网络连接到此路由器，请点击");
                var _a = $('<a href="' + basePath + 'vnet"></a>');
                _a.text("这里");
                _p.append(_a);
                _p.append("创建。");
                $('#vxnets-t').append(_p);

                ///cyh可视化 绑定私有网络
                $(".tree")
                    .append('<div class="component-router-vxnet none" style="height: 125px;">\
						<a id="addvnet" class="btn" href="#" style="top: 38.5px;"><span \
						class="icon icon-vxnet" id="iconid"></span><span class="text">连接路由器</span></a>\
				</div>');
                $("#vnetslist").height(125 * 1);

            } else {
                var i = 0;
                $.each(array, function (index, json) {
                    i++;
                    var divone = $('<div></div>');
                    divone.addClass("static-title");
                    divone.html("私有网络:&nbsp;");
                    var _a = $('<a></a>');
                    _a.addClass("static-title");
                    _a.text(json.vn_name);
                    divone.append(_a);
                    var vnetuuid = json.vn_uuid;
                    var __a = $('<a id="dhcp-od" class="vnet-dhcp" vxuuid="' + vnetuuid + '"></a>');
                    if (json.vn_dhcp == 1) {
                        __a.attr("isopen", "open");
                        __a.addClass("static-title");
                        __a.html("&nbsp;[关闭 DHCP 服务]");
                    } else {
                        __a.attr("isopen", "close");
                        __a.addClass("static-title");
                        __a.html("&nbsp;[打开 DHCP 服务]");
                    }
                    divone.append(__a);
                    var divinner = $('<div></div>');
                    var spanone = $('<span></span>');
                    spanone.addClass("none");
                    spanone.text("网络地址：192.168.");
                    spanone.append(json.vn_net + ".0/24");
                    divinner.append(spanone);
                    var spantwo = $('<span></span>');
                    spantwo.addClass("none");
                    spantwo.text("管理地址：192.168.");
                    spantwo.append(json.vn_net + "." + json.vn_gate);
                    divinner.append(spantwo);
                    var spanthree = $('<span></span>');
                    spanthree.addClass("none");
                    spanthree.text("范围：192.168.");
                    spanthree.append(json.vn_net + "." + json.vn_dhcp_start
                        + " - " + "192.168." + json.vn_net + "."
                        + json.vn_dhcp_end);
                    divinner.append(spanthree);
                    divone.append(divinner);
                    $('#vxnets-t').append(divone);
                    var table = $('<table></table>');
                    table.addClass("table table-bordered once-table");
                    if (json.ocvm.toString() == "null") {
                        var pvm = $('<p></p>');
                        pvm.addClass("none");
                        pvm.text("当前私有网络中没有主机。");
                        $('#vxnets-t').append(pvm);

                        ///cyh可视化 绑定私有网络
                        $(".tree")
                            .append(' <div class="component-router-vxnet" style="height: 125px;">\
        						<a class="btn-delete btn-delete-vxnet" vnetid="'
                                + json.vn_uuid
                                + '" href="#"\
        							style="top: 50.5px;"><span class="glyphicon glyphicon-remove"></span></a><a\
        							class="ip-network" href="#"\
        							data-permalink="" style="top: 72.5px;">192.168.'
                                + json.vn_net
                                + '.0/24 /\
        							192.168.'
                                + json.vn_net
                                + '.'
                                + json.vn_gate
                                + '</a><a class="vxnet-name"\
        							href="#" data-permalink="">'
                                + json.vn_name
                                + '</a>\
        						<div class="graph-component component-vxnet-instances"\
        							style="height: 125px; width: 104px;">\
        							<div class="component-vxnet-instance none"\
        								style="height: 125px;">\
        								<a class="btn add-instance-btn" vnetid="'
                                + json.vn_uuid
                                + '" href="#"\
        									style="margin-top: 29.5px; margin-bottom: 29.5px;"><span\
        									class="glyphicon glyphicon-plus"></span><span class="text">添加主机</span></a>\
        							</div>\
        						</div>\
        					</div>');

                    } else {
                        var thead = $('<thead></thead>');
                        thead
                            .html('<tr><th>主机 ID</th><th>名称</th><th>状态</th><th>IP 地址</th><th>DHCP 选项</th></tr>');
                        table.append(thead);
                        var vmlist = "";
                        $.each(json.ocvm, function (index, jsonocvm) {
                            var stateStr = '';
                            if (jsonocvm.hoststatus == 0) {
                                stateStr = '<span id="rt-status" class="icon-status icon-stopped" name="stateicon"></span>';
                            } else if (jsonocvm.hoststatus == 1) {
                                stateStr = '<span id="rt-status" class="icon-status icon-running" name="stateicon"></span>';
                            } else if (jsonocvm.hoststatus == 2) {
                                stateStr = '<span id="rt-status" class="icon-status icon-process" name="stateicon"></span>';
                            } else if (jsonocvm.hoststatus == 3) {
                                stateStr = '<span id="rt-status" class="icon-status icon-process" name="stateicon"></span>';
                            } else if (jsonocvm.hoststatus == 4) {
                                stateStr = '<span id="rt-status" class="icon-status icon-process" name="stateicon"></span>';
                            } else if (jsonocvm.hoststatus == 5) {
                                stateStr = '<span id="rt-status" class="icon-status icon-process" name="stateicon">';
                            }
                            var hosip = jsonocvm.hostip;
                            if (hosip == "null") {
                                hosip = '<span class="none">手工分配</span>';
                            }
                            var tbody = $('<tbody></tbody>');
                            tbody
                                .html('<tr rowid="'
                                    + jsonocvm.hostid
                                    + '"><td><a class="id">i-'
                                    + jsonocvm.hostid.substring(0, 8)
                                    + '</a></td><td>'
                                    + decodeURIComponent(jsonocvm.hostname)
                                    + '</td><td>'
                                    + stateStr
                                    + '</td><td>'
                                    + hosip
                                    + '</td><td></td>');
                            table.append(tbody);

                            var endip = jsonocvm.hostip;
                            if (endip == "null") {
                                endip = "";
                            } else {
                                endip = endip.substr(endip.length - 1, 1);
                            }

                            vmlist = vmlist
                                + '<div class="component-vxnet-instance"\
									style="margin-top: 32.5px; margin-bottom: 32.5px;">\
									<a class="btn-delete btn-delete-instance" intanceuuid="'
                                + jsonocvm.hostid
                                + '" href="#"><span\
										class="glyphicon glyphicon-remove"></span></a><span class="private-ip">'
                                + endip
                                + '</span><a\
										class="instance-name"\
										href="#"\
										data-permalink="">'
                                + decodeURIComponent(jsonocvm.hostname)
                                + '</a>\
								</div>';
                        });

                        ///cyh可视化 绑定私有网络
                        $(".tree")
                            .append(' <div class="component-router-vxnet" style="height: 125px;">\
        						<a class="btn-delete btn-delete-vxnet" vnetid="'
                                + json.vn_uuid
                                + '" href="#"\
        							style="top: 50.5px;"><span class="glyphicon glyphicon-remove"></span></a><a\
        							class="ip-network" href="#"\
        							data-permalink="" style="top: 72.5px;">192.168.'
                                + json.vn_net
                                + '.0/24 /\
        							192.168.'
                                + json.vn_net
                                + '.'
                                + json.vn_gate
                                + '</a><a class="vxnet-name"\
        							href="#" data-permalink="">'
                                + json.vn_name
                                + '</a>\
        						<div class="graph-component component-vxnet-instances"\
        							style="height: 125px; width: 104px;">'
                                + vmlist
                                + '<div class="component-vxnet-instance none"\
        								style="height: 125px;">\
        								<a class="btn add-instance-btn" vnetid="'
                                + json.vn_uuid
                                + '" href="#"\
        									style="margin-top: 29.5px; margin-bottom: 29.5px;"><span\
        									class="glyphicon glyphicon-plus"></span><span class="text">添加主机</span></a>\
        							</div>\
        						</div>\
        					</div>');

                        $('#vxnets-t').append(table);
                    }

                });

                ///cyh可视化 绑定私有网络
                $(".tree")
                    .append('<div class="component-router-vxnet none" style="height: 125px;">\
						<a id="addvnet" class="btn" href="#" style="top: 38.5px;"><span \
						class="icon icon-vxnet" id="iconid"></span><span class="text">连接路由器</span></a>\
				</div>');
                $("#vnetslist").height(125 * (i + 1));
            }
        }
    });*/
}

function getpfList() {
    var routerUuid = $('#platformcontent').attr("routerUuid");
    var routerip = $("#platformcontent").attr("rtip");
   /* $.ajax({
        type: 'post',
        url: '/RouterAction/ForwardPortList',
        data: {
            routerUuid: routerUuid
        },
        dataType: 'json',
        success: function (array) {
            $("#tablebody").html("");
            var tableStr = "";
            $.each(array, function (index, json) {
                var uuid = json.pf_uuid;
                var name = decodeURIComponent(json.pf_name);
                var protocal = json.pf_protocal;
                var sourceport = json.pf_sourceport;
                var internalIP = json.pf_internalIP;
                var internalport = json.pf_internalport;
                var thistr = '<tr pfuuid="'
                    + uuid
                    + '" pfrouter="'
                    + routerip
                    + '" protocol="'
                    + protocal
                    + '" srcPort="'
                    + sourceport
                    + '" destIP="'
                    + internalIP
                    + '" destPort="'
                    + internalport
                    + '">'
                    + '<td class="rcheck"><input type="checkbox" name="rulerow"></td><td>'
                    + name + '</td><td>' + protocal + '</td><td>'
                    + sourceport + '</td><td>' + internalIP + '</td><td>'
                    + internalport + '</td></tr>';
                tableStr += thistr;
            });
            $('#tablebody').html(tableStr);
        }
    });*/
}

function alldisable() {
    $('#deletepf').addClass('btn-disable').attr('disabled', true);
}

$('#tablebody').on('change', 'input:checkbox', function (event) {
    event.preventDefault();
    var count = 0;
    $('input[name="rulerow"]:checked').each(function () {
        count++;
    });
    if (count == 0) {
        $('#deletepf').addClass('btn-disable').attr('disabled', true);
    } else {
        $('#deletepf').removeClass('btn-disable').attr('disabled',
            false);
    }
});

$("#deletepf").on("click", function () {
    alldisable();
    var routerUuid = $('#platformcontent').attr("routerUuid");
    $('input[name="rulerow"]:checked').each(function () {
        var uuid = $(this).parents("tr").attr("pfuuid");
        var srcIP = $("#platformcontent").attr("rtip");
        var protocol = $(this).parents("tr").attr("protocol");
        var srcPort = $(this).parents("tr").attr("srcPort");
        var destIP = $(this).parents("tr").attr("destIP");
        var destPort = $(this).parents("tr").attr("destPort");
       /* $.ajax({
            type: 'post',
            url: '/RouterAction/DelPortForwarding',
            data: {
                uuid: uuid,
                srcIP: srcIP,
                protocol: protocol,
                srcPort: srcPort,
                destIP: destIP,
                destPort: destPort
            },
            dataType: 'json',
            success: function (json) {
                if (json.result) {
                    getpfList();
                }
            }
        });*/
    });
});

function isClosed(vxuuid) {
    var showMessage = '';
    var showTitle = '';
    var result = true;
    showMessage = '<div class="alert alert-info" style="margin:10px;">'
        + '<span class="glyphicon glyphicon-info-sign"></span>&nbsp;确定要关闭私有网络&nbsp;[vn-'
        + vxuuid.substring(0,8) + ']&nbsp;的DHCP服务&nbsp;?</div>';
    showTitle = '提示';
    bootbox.dialog({
        className: "oc-bootbox",
        message: showMessage,
        title: showTitle,
        buttons: {
            main: {
                label: "确定",
                className: "btn-primary"
                /*callback: function () {
                    $.ajax({
                        type: "post",
                        url: "/RouterAction/DisableDHCP",
                        data: {vxuuid: vxuuid},
                        dataType: "json",
                        success: function (obj) {
                            if (obj.result) {
                                getVxnets();
                            }
                        }
                    });
                }*/
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

function isOpen(vxuuid) {
    $.ajax({
        type: "post",
        url: "/RouterAction/EnableDHCP",
        data: {
            vxuuid: vxuuid
        },
        dataType: "json",
        success: function (obj) {
            if (obj.result) {
                getVxnets();
            }
        }
    });
}

$("#dhcp").on("click", "#dhcp-od", function () {
    var isopen = $(this).attr("isopen");
    var vxuuid = $(this).attr("vxuuid");
    if (isopen == "open") {
        isClosed(vxuuid);
    } else if (isopen == "close") {
        isOpen(vxuuid);
    }
});

function pptpList() {
	var routerUuid = $('#platformcontent').attr("routerUuid");
	/*$.ajax({
		type : "post",
		url : "/RouterAction/PPTPList",
		data : {routerUuid:routerUuid},
		dataType : "json",
		success : function(obj) {
			var tablestr = "";
			var tmpstr = "";
			if (obj.length > 1) {
				tmpstr += '<td><a id="pptp-delete">[删除]&nbsp;</a><a id="pptp-modify">&nbsp;[修改]</a></td></tr>';
			} else {
				tmpstr += '<td><a id="pptp-modify">[修改]</a></td></tr>';
			}
			$.each(obj, function(index, json){
				tablestr += '<tr pptpid="'+ json.pptpid +'"><td id="pname">' + json.name +'</td><td>******</td>';
				tablestr += tmpstr;
			});
			$("#pptp-user").append(tablestr);
		}
	});*/
}

pptpList();

$("#pptp-user").on("click", "#pptp-delete", function(){
	var pptpid = $(this).parents("tr").attr("pptpid");
	var removetr = $(this).parents("tr");
	var rownum = $("#pptp-user tr").length;
	$.ajax({
		type : "post",
		url : "/RouterAction/DeletePPTP",
		data : {pptpid:pptpid},
		dataType : "json",
		success : function(obj) {
			if(obj.result) {
				removetr.remove();
				if(rownum == 2) {
					$("#pptp-user tr:eq(0) td:eq(2)").html('<a id="pptp-modify">&nbsp;[修改]</a>');
				}
			}
		}
	});
});

$("#pptp-user").on("click", "#pptp-modify", function(){
	event.preventDefault();
    var url = basePath + 'pptpuser/modify';
    var pptpid = $(this).parents("tr").attr("pptpid");
    var pname = $(this).parents("tr").find("#pname").html();
    $('#RouterModalContainer').load(url, {"pptpid":pptpid, "pname":pname}, function () {
        $('#RouterModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});
