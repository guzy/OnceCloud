$(document).ready(function () {
    $("#ServerModalContainer").on("hidden", function () {
        $(this).removeData("modal");
        $(this).children().remove();
    });

    initSelect();

    $("#choosepool").change(function (event) {
        initAlert();
    });

    function initAlert() {
        var hmaster = $("#choosepool").find("option:selected").attr("hasmaster");
        if (hmaster == '0') {
            $("#host-alert").html("选择资源池主节点");
            $('div', $('#choosemaster')).removeClass('host-disable');
            $('div', $('#choosemaster')).removeClass('selected');
            $('#choosemaster').find(':first').addClass('selected');
            $('.image-item').on('click', function (event) {
                event.preventDefault();
                $('div', $('#choosemaster')).removeClass('selected');
                $(this).addClass('selected');
            });
        } else if (hmaster == '1') {
            $('div', $('#choosemaster')).removeClass('selected');
            $('div', $('#choosemaster')).addClass('host-disable');
            $("#host-alert").html("目标资源池已存在主节点");
            $('.image-item').unbind('click');
        }
    }

    $('#add2PoolAction').on('click', function (event) {
        event.preventDefault();
        var selectedpool = document.getElementById('choosepool');
        var hmaster = selectedpool.options[selectedpool.selectedIndex].getAttribute('hasmaster');
        var poolUuid = $('#choosepool').val();
        var masterUuid = $('#choosemaster').find('.selected').attr('uuid');
        var boxesc = document.getElementsByName("hostrow");
        var pooltype = $("#hostjson").attr('hosttype');
        var ustr = '';
        if(pooltype == 'beyondCloud'){
        	ustr = '[';
        }
        if (hmaster == '0') {
            ustr = ustr + masterUuid;
            for (var i = 0; i < boxesc.length; i++) {
                if (boxesc[i].checked == true) {
                    var hostuuid = $(boxesc[i]).parent().parent().attr('hostid');
                    if (hostuuid != masterUuid) {
                        ustr = ustr + ',' + hostuuid;
                    }
                }
            }
        } else if (hmaster == '1') {
            var c = 1;
            for (var i = 0; i < boxesc.length; i++) {
                if (boxesc[i].checked == true) {
                    var hostuuid = $(boxesc[i]).parent().parent().attr('hostid');
                    if (c == 1) {
                        ustr = ustr + hostuuid;
                        c++;
                    } else {
                        ustr = ustr + ',' + hostuuid;
                    }
                }
            }
        }
        if(pooltype == 'beyondCloud'){
        	ustr = ustr + ']';
        }
        $.ajax({
            type: 'post',
            url: 'HostAction/AddToPool',
            data: {uuidjsonstr: ustr, hasmaster: hmaster, pooluuid: poolUuid},
            dataType: 'json',
            success: function (array) {
                for (var i = 0; i < array.length; i++) {
                    var obj = array[i];
                    var huid = obj.huid;
                    var puid = obj.puid;
                    var pname = decodeURIComponent(obj.pname);
                    var thistr = $("#tablebody").find('[hostid="' + huid + '"]');
                    var pooltd = thistr.find('[state]');
                    pooltd.attr('state', 'load');
                    pooltd.html('<a>' + pname + '</a>');
                }
            }
        });
        removeAllCheck();
    });

    function removeAllCheck() {
        var boxes = document.getElementsByName("hostrow");
        for (var i = 0; i < boxes.length; i++) {
            boxes[i].checked = false;
            $(boxes[i]).change();
        }
    }

    function initSelect() {
        var uuidJsonStr = $("#hostjson").attr('jsonstr');
        var pooltype = $("#hostjson").attr('hosttype');
        $.ajax({
            type: 'post',
            url: 'HostAction/TablePool',
            data: {uuidjsonstr: uuidJsonStr},
            dataType: 'json',
            success: function (array) {
                var choosepoollist = document.getElementById("choosepool");
                choosepoollist.innerHTML = "";
                var inserthml = "";
                for (var i = 0; i < array.length; i++) {
                    var obj = array[i];
                    var poolUuid = obj.pooluuid;
                    var poolName = decodeURIComponent(obj.poolname);
                    var hasMaster = obj.hasmaster;
                    if(pooltype == obj.pooltype){
                    	inserthml = inserthml + '<option value="' + poolUuid + '" hasmaster=' + hasMaster + '>' + poolName + '</option>';
                    }
                }
                choosepoollist.innerHTML = inserthml;
                inserthml = "";
                var boxesc = document.getElementsByName("hostrow");
                var tableStr = "";
                for (var i = 0; i < boxesc.length; i++) {
                    if (boxesc[i].checked == true) {
                        var thistr = $(boxesc[i]).parent().parent();
                        var hostuuid = thistr.attr('hostid');
                        var hostname = thistr.attr('hostname');
                        var showuuid = "i-" + hostuuid.substring(0, 8);
                        tableStr = tableStr + '<div class="image-item" uuid="'
                            + hostuuid + '"><div class="image-left">' + hostname + '</div>ID:&nbsp;&nbsp;' + showuuid + '</div>';
                    }
                }
                $('#choosemaster').html(tableStr);
                initAlert();
            },
            error: function () {

            }
        });
    }
});