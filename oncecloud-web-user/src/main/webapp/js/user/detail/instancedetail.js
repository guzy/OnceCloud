$(function () {
	initInstance();
})

function initInstance(){
	getInstanceBasicList();
    var cpuChart;
    var memChart;
    var vbdChart;
    var vifChart;
    drawCpuLine('sixhours');
    drawMemoryLine('sixhours');
    drawVbdLine('sixhours');
    drawVifLine('sixhours');
    cpuTimer = setInterval(function () {
        drawCpuLine('sixhours');
    }, 5 * 60 * 1000);
    memTimer = setInterval(function () {
    	drawMemoryLine('sixhours');
    }, 5 * 60 * 1000);
    vbdTimer = setInterval(function () {
        drawVbdLine('sixhours');
    }, 5 * 60 * 1000);
    vifTimer = setInterval(function () {
        drawVifLine('sixhours');
    }, 5 * 60 * 1000);
    init();
}

$('#modify').on('click', function (event) {
    event.preventDefault();
    var url = $("#platformcontent").attr('platformBasePath')
        + 'user/modal/modify';
    var instanceUuid = $("#platformcontent").attr("instanceUuid");
    var instanceName = $("#instancename").text();
    var instanceDesc = $("#instancedesc").text();
    $('#InstanceModalContainer').load(url, {
        "modifyType": "instance",
        "modifyUuid": instanceUuid,
        "modifyName": instanceName,
        "modifyDesc": instanceDesc
    }, function () {
        $('#InstanceModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#picture-modify').on('click', function (event) {
    event.preventDefault();
    var url = $("#platformcontent").attr('platformBasePath')
        + 'user/modal/modify';
    var instanceUuid = $("#platformcontent").attr("instanceUuid");
    var instanceName = $("#instancename").text();
    var instanceDesc = $("#instancedesc").text();
    $('#InstanceModalContainer').load(url, {
        "modifyType": "instance",
        "modifyUuid": instanceUuid,
        "modifyName": instanceName,
        "modifyDesc": instanceDesc
    }, function () {
        $('#InstanceModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#InstanceModalContainer').on('hide', function (event) {
    getInstanceBasicList();
});

$('#basic-list, #basic-list2').on('click', '.console', function (event) {
    event.preventDefault();
    var uuid = $(this).data("uuid");
    var vnc = document.getElementById("platformcontent")
        .getAttribute("novnc");
    var token = uuid.substring(0, 8);
    var url = vnc + "console.html?id=" + token;
    window.open(url, "newwindow", 'height=600,width=810,top=0,left=0');
});



$('#depend-list').on('click', '#vnetip', function (event) {
    event.preventDefault();
    var uuid = $(this).attr("vnetip");
    var routerid = $(this).attr("routerUuid");

    var form = $("<form></form>");
    form.attr("action", "/vnet/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="vnetUuid" value="' + uuid + '" />');
    form.append(input);
    var input2 = $('<input type="text" name="routerId" value="' + routerid
        + '" />');
    form.append(input2);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('.detail-right').on('mouseenter', '.bootstrap-switch', function (event) {
    event.preventDefault();
    $('.oc-tip').css('display', 'inline-block');
});

$('.detail-right').on('mouseleave', '.bootstrap-switch', function (event) {
    event.preventDefault();
    $('.oc-tip').css('display', 'none');
});

$('#depend-list').on('click', '#snapshotid', function (event) {
    event.preventDefault();
    var resourceUuid = $(this).attr('rsuuid');
    var resourceName = $(this).attr('rsname');
    var form = $("<form></form>");
    form.attr("action", "/snapshot/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="resourceUuid" value="'
        + resourceUuid + '" />');
    form.append(input);
    var input2 = $('<input type="text" name="resourceName" value="'
        + resourceName + '" />');
    form.append(input2);
    var input3 = $('<input type="text" name="resourceType" value="instance" />');
    form.append(input3);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#depend-list').on('click', '.volid', function (event) {
    event.preventDefault();
    var volid = $(this).attr('voluuid');
    var form = $("<form></form>");
    form.attr("action", "/volume/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="volumeUuid" value="' + volid
        + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

function thirtymin() {
    resetChart();
    $('.btn-monitor').removeClass('selected');
    $('.chart-span').text("30 秒");
    $('.chart-span').addClass("chart-right");
    $('#chart-group').addClass("chart-right");
    window.clearInterval(cpuTimer);
    window.clearInterval(memTimer);
    window.clearInterval(vbdTimer);
    window.clearInterval(vifTimer);
    drawCpuLine('thirtymin');
    drawMemoryLine('thirtymin');
    drawVbdLine('thirtymin');
    drawVifLine('thirtymin');
    cpuTimer = setInterval(function () {
        drawCpuLine('thirtymin');
    }, 30 * 1000);
    memTimer = setInterval(function () {
        drawMemoryLine('thirtymin');
    }, 30 * 1000);
    vbdTimer = setInterval(function () {
        drawVbdLine('thirtymin');
    }, 30 * 1000);
    vifTimer = setInterval(function () {
        drawVifLine('thirtymin');
    }, 30 * 1000);
}

$("#sixhours").on('click', function (event) {
    event.preventDefault();
    resetSwitch();
    $('.btn-monitor').removeClass('selected');
    $(this).addClass('selected');
    sixhours();
});

function resetChart() {
    $('#chart-area-1').html('<div id="cpupic"></div>');
    $('#chart-area-2').html('<div id="memorypic"></div>');
    $('#chart-area-3').html('<div id="vbdpic"></div>');
    $('#chart-area-4').html('<div id="vifpic"></div>');
}

function resetSwitch() {
    $('.oc-switch').bootstrapSwitch('state', false, false);
}

function sixhours() {
    resetChart();
    $('.chart-span').text("5 分钟");
    $('.chart-span').addClass("chart-right");
    $('#chart-group').addClass("chart-right");
    window.clearInterval(cpuTimer);
    window.clearInterval(memTimer);
    window.clearInterval(vbdTimer);
    window.clearInterval(vifTimer);
    drawCpuLine('sixhours');
    drawMemoryLine('sixhours');
    drawVbdLine('sixhours');
    drawVifLine('sixhours');
    cpuTimer = setInterval(function () {
        drawCpuLine('sixhours');
    }, 5 * 60 * 1000);
    memTimer = setInterval(function () {
    	drawMemoryLine('sixhours');
    }, 5 * 60 * 1000);
    vbdTimer = setInterval(function () {
        drawVbdLine('sixhours');
    }, 5 * 60 * 1000);
    vifTimer = setInterval(function () {
        drawVifLine('sixhours');
    }, 5 * 60 * 1000);
}

$("#oneday").on('click', function (event) {
    event.preventDefault();
    resetSwitch();
    resetChart();
    $('.btn-monitor').removeClass('selected');
    $(this).addClass('selected');
    $('.chart-span').text("15 分钟");
    $('.chart-span').addClass("chart-right");
    $('#chart-group').addClass("chart-right");
    window.clearInterval(cpuTimer);
    window.clearInterval(memTimer);
    window.clearInterval(vbdTimer);
    window.clearInterval(vifTimer);
    drawCpuLine('oneday');
    drawMemoryLine('oneday');
    drawVbdLine('oneday');
    drawVifLine('oneday');
    cpuTimer = setInterval(function () {
        drawCpuLine('oneday');
    }, 15 * 60 * 1000);
    memTimer = setInterval(function () {
    	drawMemoryLine('oneday');
    }, 15 * 60 * 1000);
    vbdTimer = setInterval(function () {
        drawVbdLine('oneday');
    }, 15 * 60 * 1000);
    vifTimer = setInterval(function () {
        drawVifLine('oneday');
    }, 15 * 60 * 1000);
});

$("#twoweeks").on('click', function (event) {
    event.preventDefault();
    resetSwitch();
    resetChart();
    $('.btn-monitor').removeClass('selected');
    $(this).addClass('selected');
    $('.chart-span').text("4 小时");
    $('.chart-span').addClass("chart-right");
    $('#chart-group').addClass("chart-right");
    window.clearInterval(cpuTimer);
    window.clearInterval(memTimer);
    window.clearInterval(vbdTimer);
    window.clearInterval(vifTimer);
    drawCpuLine('twoweeks');
    drawMemoryLine('twoweeks');
    drawVbdLine('twoweeks');
    drawVifLine('twoweeks');
    cpuTimer = setInterval(function () {
        drawCpuLine('twoweeks');
    }, 4 * 3600 * 1000);
    memTimer = setInterval(function () {
    	drawMemoryLine('twoweeks');
    }, 4 * 3600 * 1000);
    vbdTimer = setInterval(function () {
        drawVbdLine('twoweeks');
    }, 4 * 3600 * 1000);
    vifTimer = setInterval(function () {
        drawVifLine('twoweeks');
    }, 4 * 3600 * 1000);
});

$("#onemonth").on('click', function (event) {
    event.preventDefault();
    resetSwitch();
    resetChart();
    $('.btn-monitor').removeClass('selected');
    $(this).addClass('selected');
    $('.chart-span').text("1 天");
    $('.chart-span').addClass("chart-right");
    $('#chart-group').addClass("chart-right");
    window.clearInterval(cpuTimer);
    window.clearInterval(memTimer);
    window.clearInterval(vbdTimer);
    window.clearInterval(vifTimer);
    drawCpuLine('onemonth');
    drawMemoryLine('onemonth');
    drawVbdLine('onemonth');
    drawVifLine('onemonth');
    cpuTimer = setInterval(function () {
        drawCpuLine('onemonth');
    }, 24 * 3600 * 1000);
    memTimer = setInterval(function () {
    	drawMemoryLine('onemonth');
    }, 24 * 3600 * 1000);
    vbdTimer = setInterval(function () {
        drawVbdLine('onemonth');
    }, 24 * 3600 * 1000);
    vifTimer = setInterval(function () {
        drawVifLine('onemonth');
    }, 24 * 3600 * 1000);
});

$('.btn-refresh').on('click', function (event) {
    event.preventDefault();
    getInstanceBasicList();
});

function drawCpuLine(types) {
    var instanceUuid = $("#platformcontent").attr("instanceUuid");
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    cpuChart = new Highcharts.Chart({
        chart: {
            renderTo: 'cpupic',
            type: 'spline',
            animation: Highcharts.svg, // don't animate in old IE
            height: 200,
            events: {
                load: function () {
                }
            }
        },
        title: {
            text: ''
        },
        xAxis: {
            type: 'datetime'
        },
        yAxis: {
            title: {
                text: ''
            },
            min: 0,
            labels: {
                formatter: function () {
                    return this.value + ' %';
                }
            }
        },
        plotOptions: {
            spline: {
                lineWidth: 2.0,
                fillOpacity: 0.1,
                marker: {
                    enabled: false,
                    states: {
                        hover: {
                            enabled: true,
                            radius: 2
                        }
                    }
                },
                shadow: false
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>CPU '
                    + this.series.name
                    + '</b><br>'
                    + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',
                        this.x) + '<br>'
                    + Highcharts.numberFormat(this.y, 2) + ' %';
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: []
    });

    updateCpuData(instanceUuid, types);
}

function drawMemoryLine(types) {
    var instanceUuid = $("#platformcontent").attr("instanceUuid");
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    memoryChart = new Highcharts.Chart({
        chart: {
            renderTo: 'memorypic',
            type: 'spline',
            animation: Highcharts.svg, // don't animate in old IE
            height: 200,
            events: {
                load: function () {
                }
            }
        },
        title: {
            text: ''
        },
        xAxis: {
            type: 'datetime'
        },
        yAxis: {
            title: {
                text: ''
            },
            min: 0,
            labels: {
                formatter: function () {
                    return this.value + ' GB';
                }
            }
        },
        plotOptions: {
            spline: {
                lineWidth: 2.0,
                fillOpacity: 0.1,
                marker: {
                    enabled: false,
                    states: {
                        hover: {
                            enabled: true,
                            radius: 2
                        }
                    }
                },
                shadow: false
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>'
                    + this.series.name
                    + '</b><br>'
                    + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S',
                        this.x) + '<br>'
                    + Highcharts.numberFormat(this.y, 2) + ' GB';
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: []
    });

    updateMemoryData(instanceUuid, types);
}

function drawVbdLine(types) {
    var instanceUuid = $("#platformcontent").attr("instanceUuid");
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    vbdChart = new Highcharts.Chart({
        chart: {
            renderTo: 'vbdpic',
            type: 'spline',
            animation: Highcharts.svg, // don't animate in old IE
            height: 200,
            events: {
                load: function () {
                }
            }
        },
        title: {
            text: ''
        },
        xAxis: {
            type: 'datetime'
        },
        yAxis: {
            title: {
                text: ''
            },
            min: 0,
            labels: {
                formatter: function () {
                    return this.value + ' kbps';
                }
            }
        },
        plotOptions: {
            spline: {
                lineWidth: 2.0,
                fillOpacity: 0.1,
                marker: {
                    enabled: false,
                    states: {
                        hover: {
                            enabled: true,
                            radius: 2
                        }
                    }
                },
                shadow: false
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br>'
                    + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x)
                    + '<br>' + Highcharts.numberFormat(this.y, 2) + ' kbps';
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: []
    });

    updateVbdData(instanceUuid, types);

}

function drawVifLine(types) {
    var instanceUuid = $("#platformcontent").attr("instanceUuid");
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
    vifChart = new Highcharts.Chart({
        chart: {
            renderTo: 'vifpic',
            type: 'spline',
            animation: Highcharts.svg, // don't animate in old IE
            height: 200,
            events: {
                load: function () {
                }
            }
        },
        title: {
            text: ''
        },
        xAxis: {
            type: 'datetime'
        },
        yAxis: {
            title: {
                text: ''
            },
            min: 0,
            labels: {
                formatter: function () {
                    return this.value + ' kb/s';
                }
            }
        },
        plotOptions: {
            spline: {
                lineWidth: 2.0,
                fillOpacity: 0.1,
                marker: {
                    enabled: false,
                    states: {
                        hover: {
                            enabled: true,
                            radius: 2
                        }
                    }
                },
                shadow: false
            }
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br>'
                    + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x)
                    + '<br>' + Highcharts.numberFormat(this.y, 2) + ' kb/s';
            }
        },
        legend: {
            enabled: false
        },
        exporting: {
            enabled: false
        },
        series: []
    });
    updateVifData(instanceUuid, types);
}

function updateCpuData(uuid, type) {
    $.ajax({
        type: 'get',
        url: '/PerformanceAction/CPU',
        data: {
            uuid: uuid,
            type: type
        },
        dataType: 'text',
        success: function (response) {
            var obj = jQuery.parseJSON(response);
            var hasPic = false;
            for (key in obj) {
                hasPic = true;
                var cpuId = key;
                var array = obj[key];
                var cpuData = [];
                for (var c = 0; c < array.length; c++) {
                    var times = array[c].times;
                    var usage = array[c].usage;
                    cpuData.push({
                        x: times,
                        y: usage
                    });
                }
                cpuChart.addSeries({
                    name: cpuId,
                    data: cpuData
                }, false);
            }
            if (hasPic) {
                cpuChart.redraw();
            } else {
                $('#chart-area-1')
                    .html('<div class="no-data">没有数据</div>');
            }
        }
    });
}

function updateMemoryData(uuid, type) {
    $.ajax({
        type: 'get',
        url: '/PerformanceAction/Memory',
        data: {
            uuid: uuid,
            type: type
        },
        dataType: 'text',
        success: function (response) {
            var array = jQuery.parseJSON(response);
            var memoryTotalData = [];
            var memoryUsedData = [];
            var hasPic = false;
            for (var c = 0; c < array.length; c++) {
                hasPic = true;
                var times = array[c].times;
                var total = array[c].total;
                var used = array[c].used;
                memoryTotalData.push({
                    x: times,
                    y: total
                });
                memoryUsedData.push({
                    x: times,
                    y: used
                });
            }
            if (memoryTotalData.length != 0) {
                memoryChart.addSeries({
                    name: "总量",
                    data: memoryTotalData
                }, false);
                memoryChart.addSeries({
                    name: "使用",
                    data: memoryUsedData
                }, false);
            }
            if (hasPic) {
                memoryChart.redraw();
            } else {
                $('#chart-area-2')
                    .html('<div class="no-data">没有数据</div>');
            }
        }
    });
}

function updateVbdData(uuid, type) {
    $.ajax({
        type: 'get',
        url: '/PerformanceAction/VBD',
        data: {
            uuid: uuid,
            type: type
        },
        dataType: 'text',
        success: function (response) {
            var obj = jQuery.parseJSON(response);
            var hasPic = false;

            for (key in obj) {
                hasPic = true;
                var vbdId = key;
                var array = obj[key];
                var vbdReadData = [];
                var vbdWriteData = [];
                
                for (var c = 0; c < array.length; c++) {
                    var times = array[c].times;
                    var read = array[c].read;
                    var write = array[c].write;
                    vbdReadData.push({
                        x: times,
                        y: read
                    });
                    vbdWriteData.push({
                        x: times,
                        y: write
                    });
                }
                vbdChart.addSeries({
                    name: vbdId + " 读",
                    data: vbdReadData
                }, false);
                vbdChart.addSeries({
                    name: vbdId + " 写",
                    data: vbdWriteData
                }, false);
            }
            if (hasPic) {
                vbdChart.redraw();
            } else {
                $('#chart-area-3')
                    .html('<div class="no-data">没有数据</div>');
            }
        }
    });
}

function updateVifData(uuid, type) {
    $.ajax({
        type: 'get',
        url: '/PerformanceAction/VIF',
        data: {
            uuid: uuid,
            type: type
        },
        dataType: 'text',
        success: function (response) {
            var obj = jQuery.parseJSON(response);
            var hasPic = false;
            for (key in obj) {
                hasPic = true;
                var vifId = key;
                var array = obj[key];
                var vifRxData = [];
                var vifTxData = [];
                for (var c = 0; c < array.length; c++) {
                    var times = array[c].times;
                    var rx = array[c].rx;
                    var tx = array[c].tx;
                    vifRxData.push({
                        x: times,
                        y: rx
                    });
                    vifTxData.push({
                        x: times,
                        y: tx
                    });
                }
                vifChart.addSeries({
                    name: "VIF" + vifId + " 下行",
                    data: vifRxData
                }, false);
                vifChart.addSeries({
                    name: "VIF" + vifId + " 上行",
                    data: vifTxData
                }, false);
            }
            if (hasPic) {
                vifChart.redraw();
            } else {
                $('#chart-area-4')
                    .html('<div class="no-data">没有数据</div>');
            }
        },
        error: function () {
        }
    });
}

$('.oc-switch').bootstrapSwitch();

$('.oc-switch').on('switchChange.bootstrapSwitch', function (event, state) {
    if (state == true) {
        thirtymin();
    } else {
        sixhours();
    }
});
function reloadList(page){
	getInstanceBasicList();
}

function getInstanceBasicList() {
    var instanceUuid = $("#platformcontent").attr("instanceUuid");
    $('#basic-list').html("");
    $.ajax({
        type: 'get',
        url: '/VMAction/VMDetail',
        data: {
            uuid: instanceUuid
        },
        dataType: 'json',
        success: function (obj) {
            var instanceName = decodeURIComponent(obj.instanceName);
            var instanceDesc = decodeURIComponent(obj.instanceDesc);
            var instanceState = obj.instanceState;
            var stateStr = '';
            var showstr = '';
            var showuuid = "i-" + instanceUuid.substring(0, 8);
            if (instanceState == 1) {
                stateStr = '<td state="on"><span class="icon-status icon-running" name="stateicon"></span><span name="stateword">正常运行</span></td>';
                showstr = "<a class='id'>" + showuuid
                    + '</a>';
                    /*+'<a class="console" data-uuid=' + instanceUuid+ '><img src="../img/user/console.png"></a>';*/
            } else if (instanceState == 0) {
                stateStr = '<td state="off"><span class="icon-status icon-stopped" name="stateicon"></span><span name="stateword">已关机</span></td>';
                showstr = "<a class='id'>" + showuuid + '</a>';
            } else {
                stateStr = '<td state="off"><span class="icon-status icon-process" name="stateicon"></span><span name="stateword">创建中</span></td>';
                showstr = "<a class='id'>" + showuuid + '</a>';
            }
            $("#instancestate").val(instanceState);
            backfunction(); // add by cyh

            var createDate = obj.createDate;
            var useDate = decodeURIComponent(obj.useDate);
            var instanceCPU = obj.instanceCPU;
            var instanceMemory = obj.instanceMemory;
            if (instanceMemory < 1024) {
                instanceMemory = instanceMemory + "&nbsp;MB";
            } else {
                instanceMemory = instanceMemory / 1024 + "&nbsp;GB";
            }
            var instanceDisk = '';
            var volList = obj.volList;
            if ('&nbsp;' != volList) {
                for (var i = 0; i < volList.length; i++) {
                    instanceDisk = instanceDisk
                        + '<a class="id volid" voluuid="' + volList[i]
                        + '">vol-' + volList[i].substring(0, 8)
                        + '</a><br/>';
                }
            } else {
                instanceDisk = volList;
            }
            var instanceMAC = obj.instanceMAC;
            var vlan = obj.vlan;
            var network = '&nbsp;';
            var backDate = decodeURIComponent(obj.backupdate);
            var bkId = "&nbsp;";
            if (backDate != "") {
                bkId = '<a class="id" id="snapshotid" rsuuid="' + instanceUuid
                    + '" rsname="' + instanceName + '">bk-'
                    + instanceUuid.substring(0, 8) + '</a>';
            } else {
                backDate = "&nbsp;";
            }
            $('#basic-list')
                .html('<dt>ID</dt><dd>' + showstr
                    + '</dd><dt>名称</dt><dd id="instancename">'
                    + instanceName
                    + '</dd><dt>描述</dt><dd id="instancedesc">'
                    + instanceDesc + '</dd><dt>状态</dt><dd>' + stateStr
                    + '</dd><dt>CPU</dt><dd>' + instanceCPU
                    + '&nbsp;核</dd><dt>内存</dt><dd>' + instanceMemory
                    + '</dd><dt>MAC地址</dt><dd>' + instanceMAC
                    + '</dd><dt>创建时间</dt><dd class="time">'
                    + createDate
                    + '</dd><dt>运行时间</dt><dd class="time">' + useDate
                    + '</dd>');
            $('#depend-list')
                .html('<dt>网络</dt><dd><span id="netw">'
                    + network
                    + '</span></dd><dt>硬盘</dt><dd>'
                    + instanceDisk
                    + '</dd><dt>备份链</dt><dd>' + bkId
                    + '</dd><dt>距上次备份时间</dt><dd class="time">'
                    + backDate + '</dd>');
            if (obj.instancevlan != null && obj.instancevlan != "null") {
                // /私有网络
                $("#instancerouterDiv").show();
                $("#vnUuid").val(obj.instancevlan);
                $.ajax({
                    type: 'get',
                    url: '/VnetAction/VnetDetail',
                    data: {
                        uuid: obj.instancevlan
                    },
                    dataType: 'json',
                    success: function (obj) {
                        if(obj.vlantype==1){
                        	$("#btnshowswitch").text("普通交换机,vlan号："+ decodeURIComponent(obj.vlanid));
                        }else if(obj.vlantype==2){
                        	$("#btnshowswitch").text("分布式交换机,vlan号："+ decodeURIComponent(obj.vlanid));
                        }
                    }
                });

            } else {
                // /vlan 是null，说明是基础网络
                $("#instancerouterDiv").hide();

                $("#vnUuid").val("");
                $("#vnrouterUuid").val("");
            }
            // /显示硬盘
            bindvolumesshow();
            $("#instanceID").find(".instance-name").text(instanceName);
            $("#basic-list2").html($('#basic-list').html());
        }
    });
}

