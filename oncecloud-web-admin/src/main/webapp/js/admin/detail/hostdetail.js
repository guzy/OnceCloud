$(document).ready(function () {
    var cpuChart;
    var memoryChart;
    var pifChart;
    var vbdChart;
    drawCpuLine('sixhours');
    drawMemoryLine('sixhours');
    drawVbdLine('sixhours');
    drawPifLine('sixhours');
    var cpuTimer = setInterval(function () {
        drawCpuLine('sixhours');
    }, 5 * 60 * 1000);
    var memoryTimer = setInterval(function () {
        drawMemoryLine('sixhours');
    }, 5 * 60 * 1000);
    vbdTimer = setInterval(function () {
        drawVbdLine('sixhours');
    }, 5 * 60 * 1000);
    var pifTimer = setInterval(function () {
        drawPifLine('sixhours');
    }, 5 * 60 * 1000);

    getHostBasicList();

    $('#modify').on('click', function (event) {
	   event.preventDefault();
	   var url = $("#platformcontent").attr('basePath') + 'admin/modal/modify';
	   var instanceUuid = $("#platformcontent").attr("instanceUuid");
	   var instanceName = $("#instancename").text();
	   $('#InstanceModalContainer').load(url, {
	     "modifyType": "instance",
	     "modifyUuid": instanceUuid,
	     "modifyName": instanceName,
	     "modifyDesc": ""
	    }, function () {
		     $('#InstanceModalContainer').modal({
		     backdrop: false,
		     show: true
		     });
	     });
     });
     

    function thirtymin() {
        resetChart();
        $('.btn-monitor').removeClass('selected');
        $('.chart-span').text("30 秒");
        $('.chart-span').addClass("chart-right");
        $('#chart-group').addClass("chart-right");
        window.clearInterval(cpuTimer);
        window.clearInterval(memoryTimer);
        window.clearInterval(vbdTimer);
        window.clearInterval(pifTimer);
        
        drawCpuLine('thirtymin');
        drawMemoryLine('thirtymin');
        drawVbdLine('thirtymin');
        drawPifLine('thirtymin');
        cpuTimer = setInterval(function () {
            drawCpuLine('thirtymin');
        }, 30 * 1000);
        memoryTimer = setInterval(function () {
            drawMemoryLine('thirtymin');
        }, 30 * 1000);
        vbdTimer = setInterval(function () {
            drawVbdLine('thirtymin');
        }, 30 * 1000);
        pifTimer = setInterval(function () {
            drawPifLine('thirtymin');
        }, 30 * 1000);
    }

    $("#sixhours").on('click', function (event) {
        event.preventDefault();
        resetSwitch();
        $('.btn-monitor').removeClass('selected');
        $(this).addClass('selected');
        sixhours();
    });

    function sixhours() {
        resetChart();
        $('.chart-span').text("5 分钟");
        $('.chart-span').addClass("chart-right");
        $('#chart-group').addClass("chart-right");
        window.clearInterval(cpuTimer);
        window.clearInterval(memoryTimer);
        window.clearInterval(vbdTimer);
        window.clearInterval(pifTimer);
        drawCpuLine('sixhours');
        drawMemoryLine('sixhours');
        drawVbdLine('sixhours');
        drawPifLine('sixhours');
        cpuTimer = setInterval(function () {
            drawCpuLine('sixhours');
        }, 5 * 60 * 1000);
        memoryTimer = setInterval(function () {
            drawMemoryLine('sixhours');
        }, 5 * 60 * 1000);
        vbdTimer = setInterval(function () {
            drawVbdLine('sixhours');
        }, 5 * 60 * 1000);
        pifTimer = setInterval(function () {
            drawPifLine('sixhours');
        }, 5 * 60 * 1000);
    }

    function resetChart() {
        $('#chart-area-1').html('<div id="cpupic"></div>');
        $('#chart-area-2').html('<div id="memorypic"></div>');
        $('#chart-area-3').html('<div id="pifpic"></div>');
        $('#chart-area-4').html('<div id="vbdpic"></div>');
    }

    function resetSwitch() {
        $('.oc-switch').bootstrapSwitch('state', false, false);
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
        window.clearInterval(memoryTimer);
        window.clearInterval(vbdTimer);
        window.clearInterval(pifTimer);
        
        drawCpuLine('oneday');
        drawMemoryLine('oneday');
        drawVbdLine('oneday');
        drawPifLine('oneday');
        cpuTimer = setInterval(function () {
            drawCpuLine('oneday');
        }, 15 * 60 * 1000);
        memoryTimer = setInterval(function () {
            drawMemoryLine('oneday');
        }, 15 * 60 * 1000);
        vbdTimer = setInterval(function () {
            drawVbdLine('oneday');
        }, 15 * 60 * 1000);
        pifTimer = setInterval(function () {
            drawPifLine('oneday');
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
        window.clearInterval(memoryTimer);
        window.clearInterval(vbdTimer);
        window.clearInterval(pifTimer);
        drawCpuLine('twoweeks');
        drawMemoryLine('twoweeks');
        drawVbdLine('twoweeks');
        drawPifLine('twoweeks');
        cpuTimer = setInterval(function () {
            drawCpuLine('twoweeks');
        }, 4 * 3600 * 1000);
        memoryTimer = setInterval(function () {
            drawMemoryLine('twoweeks');
        }, 4 * 3600 * 1000);
        vbdTimer = setInterval(function () {
            drawVbdLine('twoweeks');
        }, 4 * 3600 * 1000);
        pifTimer = setInterval(function () {
            drawPifLine('twoweeks');
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
        window.clearInterval(memoryTimer);
        window.clearInterval(pifTimer);
        drawCpuLine('onemonth');
        drawMemoryLine('onemonth');
        window.clearInterval(vbdTimer);
        drawPifLine('onemonth');
        cpuTimer = setInterval(function () {
            drawCpuLine('onemonth');
        }, 24 * 3600 * 1000);
        memoryTimer = setInterval(function () {
            drawMemoryLine('onemonth');
        }, 24 * 3600 * 1000);
        vbdTimer = setInterval(function () {
            drawVbdLine('onemonth');
        }, 24 * 3600 * 1000);
        pifTimer = setInterval(function () {
            drawPifLine('onemonth');
        }, 24 * 3600 * 1000);
    });

    function getHostBasicList() {
        var hostUuid = $("#platformcontent").attr("hostUuid");
        var basiclist = document.getElementById("basic-list");
        basiclist.innerHTML = "";
        $.ajax({
            type: 'get',
            url: '../HostAction/OneHost',
            data: {hostUuid: hostUuid},
            dataType: 'json',
            success: function (response) {
                if (response.length > 0) {
                    var obj = response[0];
                    var hostName = decodeURIComponent(obj.hostName);
                    var hostUuid = obj.hostUuid;
                    var stateStr = '';
                    var showstr = '';
                    var showuuid = "host-" + hostUuid.substring(0, 8);
                    showstr = "<a class='id'>" + showuuid + '</a>';

                    var createDate = obj.createDate;
                    var useDate = decodeURIComponent(obj.useDate);
                    var hostUuid = obj.hostUuid;
                    var hostCPU = obj.hostCPU;
                    var hostMemory = obj.hostMemory + "&nbsp;MB";


                    var hostIP = obj.hostIP;
                    var hostDesc = decodeURIComponent(obj.hostDesc);

                    var hostkernel = decodeURIComponent(obj.hostkernel);
                    var hostXen = decodeURIComponent(obj.hostXen);

                    var network;
                    if (hostIP == null || hostIP == "") {
                        network = '<a>(基础网络)</a>';
                    } else {
                        network = '<a>(基础网络)&nbsp;/&nbsp;'
                            + hostIP + '</a>';
                    }

                    var poolId = obj.poolId;
                    var poolName = decodeURIComponent(obj.poolName);
                    var poolstr = "&nbsp;";
                    if (poolId != "") {
                        poolstr = "<a class='poolclass' poolid='" + poolId + "'>" + poolName + "</a>";
                    }

                    var srstr = "&nbsp;";
                    for (var i = 1; i < response.length; i++) {
                        var itemobj = response[i];
                        srstr += "<a class='srclass' srid='" + itemobj.srId + "'>" + decodeURIComponent(itemobj.srName) + "</a><br/>"
                    }

                    basiclist.innerHTML = '<dt>ID</dt><dd>'
                        + showstr
                        + '</dd><dt>名称</dt><dd id="hostname">'
                        + hostName
                        + '</dd><dt>IP地址</dt><dd>'
                        + network
                        + '</dd><dt>CPU个数</dt><dd>'
                        + hostCPU
                        + '</dd><dt>内存大小</dt><dd>'
                        + hostMemory
                        + '</dd><dt>Kernel版本</dt><dd>'
                        + hostkernel
                        + '</dd><dt>Xen版本</dt><dd>'
                        + hostXen
                        + '</dd><dt>创建时间</dt><dd class="time">'
                        + createDate + '</dd>';

                    $('#depend-list')
                        .html(
                            '<dt>资源池</dt><dd>'
                            + poolstr
                            + '</dd><dt>存储</dt><dd>'
                            + srstr
                            + '</dd>');
                }
            },
            error: function () {
            }
        });
    }

    function drawCpuLine(types) {
        var hostUuid = $("#platformcontent").attr("hostUuid");
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
                height: 300,
                events: {
                    load: function () {
                        // setInterval(function() {
                        //     updateCpuData(instanceUuid, 'sixhours');
                        // }, 5000);
                    }
                }
            },
            title: {
                text: ''
            },
            xAxis: {
                type: 'datetime'
                //tickPixelInterval: 100
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
                    return '<b>CPU ' + this.series.name + '</b><br>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br>' +
                        Highcharts.numberFormat(this.y, 2) + ' %';
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

        updateCpuData(hostUuid, types);
    }

    function drawMemoryLine(types) {
        var hostUuid = $("#platformcontent").attr("hostUuid");
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
                height: 300,
                events: {
                    load: function () {
                        // setInterval(function() {
                        // updateMemoryData(instanceUuid, 'sixhours');
                        // }, 5000);
                    }
                }
            },
            title: {
                text: ''
            },
            xAxis: {
                type: 'datetime'
                //tickPixelInterval: 100
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
                    return '<b>' + this.series.name + '</b><br>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br>' +
                        Highcharts.numberFormat(this.y, 2) + ' GB';
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

        updateMemoryData(hostUuid, types);
    }
    function drawVbdLine(types) {
        var hostUuid = $("#platformcontent").attr("hostUuid");
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

        updateVbdData(hostUuid, types);

    }
    
    function updateVbdData(uuid, type) {
        $.ajax({
            type: 'get',
            url: '../PerformanceAction/VBD',
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
                    $('#chart-area-4')
                        .html('<div class="no-data">没有数据</div>');
                }
            }
        });
    }
    function drawPifLine(types) {
        var hostUuid = $("#platformcontent").attr("hostUuid");
        Highcharts.setOptions({
            global: {
                useUTC: false
            }
        });
        pifChart = new Highcharts.Chart({
            chart: {
                renderTo: 'pifpic',
                type: 'spline',
                animation: Highcharts.svg, // don't animate in old IE
                height: 300,
                events: {
                    load: function () {
                        // setInterval(function() {
                        //     updateVbdData(instanceUuid, 'sixhours');
                        // }, 5000);
                    }
                }
            },
            title: {
                text: ''
            },
            xAxis: {
                type: 'datetime'
                // tickPixelInterval: 100
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
                    return '<b>' + this.series.name + '</b><br>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br>' +
                        Highcharts.numberFormat(this.y, 2) + ' kb/s';
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

        updatePifData(hostUuid, types);

    }

    function updateCpuData(uuid, type) {
        $.ajax({
            type: 'get',
            url: '../PerformanceAction/CPU',
            data: {uuid: uuid, type: type},
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
                        cpuData.push({x: times, y: usage});
                    }
                    cpuChart.addSeries({
                        name: cpuId,
                        data: cpuData
                    }, false);
                }
                if (hasPic) {
                    cpuChart.redraw();
                } else {
                    $('#chart-area-1').html('<div class="no-data">没有数据</div>');
                }
            },
            error: function () {
            }
        });
    }

    function updateMemoryData(uuid, type) {
    	
        $.ajax({
            type: 'get',
            url: '../PerformanceAction/Memory',
            data: {uuid: uuid, type: type},
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
                    memoryTotalData.push({x: times, y: total});
                    memoryUsedData.push({x: times, y: used});
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
                    $('#char5-area-2').html('<div class="no-data">没有数据</div>');
                }
            },
            error: function () {
            }
        });
    }

    function updatePifData(uuid, type) {
    	
        $.ajax({
            type: 'get',
            url: '../PerformanceAction/PIF',
            data: {uuid: uuid, type: type},
            dataType: 'text',
            success: function (response) {
                var obj = jQuery.parseJSON(response);
                var hasPic = false;
                for (key in obj) {
                    hasPic = true;
                    var pifId = key;
                    var array = obj[key];
                    var pifRxData = [];
                    var pifTxData = [];
                    for (var c = 0; c < array.length; c++) {
                        var times = array[c].times;
                        var rx = array[c].rx;
                        var tx = array[c].tx;
                        pifRxData.push({x: times, y: rx});
                        pifTxData.push({x: times, y: tx});
                    }
                    pifChart.addSeries({
                        name: "PIF" + pifId + " 下行",
                        data: pifRxData
                    }, false);
                    pifChart.addSeries({
                        name: "PIF" + pifId + " 上行",
                        data: pifTxData
                    }, false);
                }
                if (hasPic) {
                    pifChart.redraw();
                } else {
                    $('#chart-area-3').html('<div class="no-data">没有数据</div>');
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

    $('.detail-right').on('mouseenter', '.bootstrap-switch', function (event) {
        event.preventDefault();
        $('.oc-tip').css('display', 'inline-block');
    });

    $('.detail-right').on('mouseleave', '.bootstrap-switch', function (event) {
        event.preventDefault();
        $('.oc-tip').css('display', 'none');
    });
});