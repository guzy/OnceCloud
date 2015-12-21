reloadvolumelist();
function reloadvolumelist(){
	$('#volumebody').html("");
    $.ajax({
        type: 'post',
        url: '/VolumeAction/AvailableVolumes',
        dataType: 'json',
        success: function (array) {
            if (array.length >= 1) {
                var tableStr = "";
                for (var i = 0; i < array.length; i++) {
                    var obj = array[i];
                    var id = obj.volumeId;
                    var showid = "volume-" + id.substr(0, 8);
                    var mytr = '<tr id="' + id + '">'
                        + '<td class="rcheck"><input type="checkbox" name="volumerow" onclick="chk(this);"></td><td>' 
                        + showid + '</td><td>' 
                        + obj.volumeName + '</td><td>' 
                        + obj.volumeSize + '</td>';
                    mytr += '</tr>';
                    tableStr = tableStr + mytr;
                }
                $('#volumebody').html(tableStr);
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        	bootbox.alert(XMLHttpRequest.responseText);
        }
   });
}
$('#mountvolume').on('click', function(event){
	var vmuuid = $("#platformcontent").attr("instanceUuid");
	var volumeuuid = '';
	$('input[name="volumerow"]:checked').each(function () {
		volumeuuid = $(this).parent().parent().attr('id');
    });
	if(volumeuuid == ''){
		bootbox.alert('请选中要挂载的硬盘！');
		return;
	}
	$.ajax({
	    type: 'post',
	    url: '/VolumeAction/Bind',
	    data: {volumeUuid: volumeuuid, vmUuid: vmuuid},
	    dataType: 'text',
	    success: function () {
	    	$('#ModifyModalContainer').modal('hide');
	    	bindvolumesshow();
	    }
	});
});


/*选择表格多选框事件*/
function chk(obj){  
     var boxArray = document.getElementsByName('volumerow');  
     for(var i=0;i<=boxArray.length-1;i++){  
          if(boxArray[i]==obj && obj.checked){  
             boxArray[i].checked = true;  
          }else{  
             boxArray[i].checked = false;  
          }  
     }   
}  