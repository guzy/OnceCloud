reloadswitchlist();
function reloadswitchlist(){
	var type = getswitchtype();
	var vmid = getvmid();
	$('#vlanbody').html("");
    $.ajax({
        type: 'post',
        url: 'VMAction/switchList',
        data: { vmid: vmid,type: type},
        dataType: 'json',
        success: function (array) {
            if (array.length >= 1) {
                var tableStr = "";
                for (var i = 0; i < array.length; i++) {
                    var obj = array[i];
                    var id = obj.netId;
                    var switchId = obj.switchId;
                    var showid = '';
                    if(type==1){
                    	showid = 'vlan-'+id;
                    }else if(type == 2){
                    	showid = 'Vxlan-'+id;
                    }
                    var mytr = '<tr id="' + id 
                        + '" switchId="' + switchId + '">'
                        + '<td class="rcheck"><input type="checkbox" name="switchrow" onclick="chk(this);"></td>'
                        + '<td><a class="id">' + showid + '</a></td>'
                        + '<td>' + switchId + '</td>';
                    mytr += '</tr>';
                    tableStr = tableStr + mytr;
                }
                $('#vlanbody').html(tableStr);
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert(XMLHttpRequest.responseText);
        }
   });
}

/*切换选项卡*/
$('.once-tab').on('click', '.tab-filter', function (event) {
    $('li', '.once-tab').removeClass('active');
    $(this).addClass('active');
    reloadswitchlist();
});

$("#joinvlan").on("click", function(){	
	var switchid = '', vlanid = '', vifUuid = '';
	var type = getswitchtype();
	var vmid = getvmid();
	$('input[name="switchrow"]:checked').each(function () {
		switchid = $(this).parent().parent().attr('id');
		vlanid = $(this).parent().parent().attr('switchId');
    });
	$('input[name="vifrow"]:checked').each(function () {
    	vifUuid = $(this).parent().parent().attr("rowid");
	});
	$.ajax({
		type: 'post',
		url: "VMAction/joinVlan",
		dataType: 'json',
		data: {
				vmid : vmid,
				vifid : vifUuid,
				switchid : switchid,
				vlanid : vlanid,
				type : type
			   },
	    success: function(data){
	    	$('#ModifyModalContainer').modal('hide');
	    	getmaclist();
		}
	});
});

/*获取交换机的类型*/
function getswitchtype(){
	var switchtype = $('.once-tab').find('.active').attr("type");
	if(switchtype == 'general'){
		switchtype = 1;
	}else{
		switchtype = 2;
	}
	return switchtype;
}

/*选择表格多选框事件*/
function chk(obj){  
     var boxArray = document.getElementsByName('switchrow');  
     for(var i=0;i<=boxArray.length-1;i++){  
          if(boxArray[i]==obj && obj.checked){  
             boxArray[i].checked = true;  
          }else{  
             boxArray[i].checked = false;  
          }  
     }   
}  

function getvmid(){
	var vmid = '';
	$('input[name="vmrow"]:checked').each(function () {
		vmid = $(this).parent().parent().attr('rowid');
    });
	return vmid;
}