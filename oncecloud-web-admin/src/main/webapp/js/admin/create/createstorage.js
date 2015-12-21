$(document).ready(function () {
	//初始化判断存储类型，根据存储类型判断是否需要端口
	sr_typeChange();
    fillBlank();
    function fillBlank() {
        var type = $('#SRModalContainer').attr('type');
        if ('edit' == type) {
            $('#modaltitle').html('修改存储<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a>');

            var srid = '';
            var srname = '';
            var srdesc = '';
            var srtype = '';
            var sraddress = '';
            var srdir = '';

            $('input[name="srrow"]:checked').each(function () {
                srid = $(this).parent().parent().attr("srid");
                srname = $(this).parent().parent().attr("srname");
                srdesc = $(this).parent().parent().attr("srdesc");
                srtype = $(this).parent().parent().attr("srtype");
                var thistr = $(this).parent().parent();
                sraddress = thistr.children('td').eq(3).text();
                srdir = thistr.children('td').eq(4).text();

            });
            $('#modalcontent').attr('srid', srid);

            $('#sr_address').val(sraddress).attr('disabled', true).addClass('oc-disable');
            $('#sr_name').val(srname);
            $('#sr_desc').val(srdesc);
            $('#sr_type').val(srtype).attr('disabled', true).addClass('oc-disable');
            $('#sr_dir').val(srdir).attr('disabled', true).addClass('oc-disable');
        }
    }


    $('#addSRAction').on('click', function (event) {
        event.preventDefault();
        var valid = $("#create-form").valid();

        if ($("#sr_type").val() != "ocfs2"&&$("#sr_type").val() != "local_ocfs2") {
            if ($("#sr_address").val() == "") {
                $('#sr_address').parent().find('.oc-error').remove();
                $("#sr_address").parent().append('<label class="oc-error"><span class="help">请填写存储地址</span></label>');
                return false;
            }
        }

        var errorLen = $('#create-form').find('.oc-error').length;
        if (valid && errorLen == 0) {
            var srName = document.getElementById("sr_name").value;
            //alert("111111");
            //alert($("#sr_port").val());
            if($("#sr_port").val()!= ""){
            	var srAddress = $("#sr_address").val()+":"+$("#sr_port").val();
            }else{
            	var srAddress = $("#sr_address").val();
            }
            
            
            var srDesc = $("#sr_desc").val();
            var srType = $("#sr_type").val();
            var srdir = $("#sr_dir").val();
            var type = $('#SRModalContainer').attr('type');
            if ('new' == type) {
                $.ajax({
                    type: 'post',
                    url: 'StorageAction/Add',
                    data: {
                        srname: srName,
                        srAddress: srAddress,
                        srDesc: srDesc,
                        srType: srType,
                        srDir: srdir
                    },
                    dataType: 'json',
                    success: function (array) {
                        if (array.length == 1) {
                        	reloadList(1);
                            /*var obj = array[0];
                            var srid = obj.srid;
                            var srname = decodeURIComponent(obj.srname);
                            var srAddress = obj.srAddress;
                            var createDate = obj.createDate;
                            var srType = obj.srType;
                            var srDir = obj.srDir;
                            var showid = "sr-" + srid.substring(0, 8);
                            var mytr = '<tr srid="' + srid + '" srname="' + srname + '" srtype="' + srType + '" srdedc="' + srDesc + '"><td class="rcheck"><input type="checkbox" name="srrow" srsize=0></td>'
                                + '<td><a>' + showid + '</a></td><td>' + srname + '</td><td>' + srAddress + '</td><td>' + srDir + '</td><td><a>' + srType.toUpperCase() + '</a></td><td class="time">' + createDate + '</td></tr>';
                            $("#tablebody").prepend(mytr);*/
                        }
                        $('#SRModalContainer').modal('hide');
                    },
                    error: function () {

                    }
                });
            } else if ('edit' == type) {
                var srid = $('#modalcontent').attr('srid');
                $.ajax({
                    type: 'post',
                    url: 'StorageAction/Update',
                    data: {srid: srid, srName: srName, srDesc: srDesc},
                    dataType: 'text',
                    success: function () {
                    	reloadList(1);
                        /*var thistr = $("#tablebody").find('[srid="' + srid + '"]');
                        thistr.attr('srname', srName);
                        thistr.attr('srdesc', srDesc);
                        thistr.children('td').eq(2).html(srName);*/
                    }

                });
                $('#SRModalContainer').modal('hide');
            }
        }
    });


    $("#sr_type").change(function () {
        if ($(this).val() == "ocfs2"||$(this).val() == "local_ocfs2") {
            $("#sr_address").addClass('oc-disable');
            $("#sr_address").attr('disabled', "disabled");
            $("#sr_address").val("");
            $('#sr_address').parent().find('label').remove();
        }
        else {
            $("#sr_address").removeClass('oc-disable');
            $("#sr_address").removeAttr('disabled');
        }
    });
    
    //存储类型改变判断
	$("#sr_type").change(function () {
		sr_typeChange();
	});
    
  
    $('#sr_dir').on('focusout', function () {
        dirValid();
    });


    function sr_typeChange(){
    	//存储类型为Devicemapper时不需要挂载目录
		if ($('#sr_type').val() == "devicemapper") {
			$("#sr_dir").addClass('oc-disable');
			$("#sr_dir").attr('disabled', "disabled");
			$("#sr_dir").val("");
			$('#sr_dir').parent().find('label').remove();
		}
		else {
			$("#sr_dir").removeClass('oc-disable');
			$("#sr_dir").removeAttr('disabled');
		}
		//存储类型不是Devicemapper时不需要端口
		if ($('#sr_type').val() != "devicemapper") {
			$("#sr_port").addClass('oc-disable');
			$("#sr_port").attr('disabled', "disabled");
			$("#sr_port").val("");
			$('#sr_port').parent().find('label').remove();
		}
		else {
			$("#sr_port").removeClass('oc-disable');
			$("#sr_port").removeAttr('disabled');
		}
    }
    function dirValid() {
        $('#sr_dir').parent().find('.oc-error').remove();
        var dir = $('#sr_dir').val();
        var temp = /^([\/][\w-]+)*$/;
        if (!temp.test(dir)) {
            $('#sr_dir').parent().append('<label class="oc-error"><span class="help">挂载目录格式不正确</span></label>');
            return false;
        }

        return true;

    }

    $('#sr_address').on('focusout', function () {
        addressValid();
    });

    function addressValid() {
        $('#sr_address').parent().find('.oc-error').remove();
        var ip = $('#sr_address').val();

        if ($("#sr_type").val() != "ocfs2"&&$("#sr_type").val() != "local_ocfs2") {
            if ($("#sr_address").val() == "") {
                $("#sr_address").parent().append('<label class="oc-error"><span class="help">请填写存储地址</span></label>');
                return false;
            }
        }

        if (ip != "") {
            $.ajax({
                type: 'get',
                async: true,
                url: 'StorageAction/QueryAddress',
                data: {address: ip},
                dataType: 'json',
                success: function (array) {
                    if (array.length == 1) {
                        var exist = array[0].exist;
                        if (exist == true) {
                            $('#sr_address').parent().append('<label class="oc-error"><span class="help">该存储地址已存在</span></label>');
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            });
        }
    }

    $("#create-form").validate({
        rules: {
            sr_name: {
                required: true,
                maxlength: 20,
                legal: true
            },
            sr_desc: {
                required: true,
                maxlength: 80,
                legal: true
            },
            sr_address: {
                ip: true,
                minlength: 7,
                maxlength: 15
            },
            sr_dir: {
                required: true,
                maxlength: 80
            }
        },
        messages: {
            sr_name: {
                required: "<span class='help'>存储名称不能为空</span>",
                maxlength: "<span class='help'>存储名称不能超过20个字符</span>",
                legal: "<span class='help'>存储名称包含非法字符</span>"
            },
            sr_desc: {
                required: "<span class='help'>存储描述不能为空</span>",
                maxlength: "<span class='help'>存储描述不能超过80个字符</span>",
                legal: "<span class='help'>存储描述包含非法字符</span>"
            },
            sr_address: {
                ip: "<span class='help'>存储地址格式不正确</span>",
                minlength: "<span class='help'>存储地址不能少于7个字符</span>",
                maxlength: "<span class='help'>存储地址不能超过15个字符</span>"
            },
            sr_dir: {
                required: "<span class='help'>挂在目录不能为空</span>",
                maxlength: "<span class='help'>挂载目录不能超过80个字符</span>"
            }
        }
    });
});