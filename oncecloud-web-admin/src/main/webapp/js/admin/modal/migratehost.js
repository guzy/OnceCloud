$(document).ready(function () {
	var poolUuid = '';
	$('input[name="poolrow"]:checked').each(function () {
    	poolUuid = $(this).parent().parent().attr("poolid");
    });
	getHostList(poolUuid);
	
	$('#confirm').on('click',function(event){
		var hostUuid = '';
		var hostName = '';
		var btable = document.getElementById("hosttable");
    	var row =''; 
		$('input[name="migratehostrow"]:checked').each(function () {
	    	hostUuid = $(this).parent().parent().attr("hostUuid");
	    	var showid = 'host-' + hostUuid.substring(0, 8);
	    	row += '<tr migratoryhostid="'
    		+ hostUuid
    		+ '"><td class="rcheck"><input name="hostrow" type="checkbox"></td>'
    		+ '<td>'
    		+ showid
    		+ '</td></tr>';
	    });
		btable.innerHTML = btable.innerHTML+row;
		hideModal();
	});
	
	$('#cancel').on('click',function(event){
		hideModal();
	});
});

/*隐藏一个模态*/
function hideModal(){
	$('#HostModalContainer').modal('hide');
}

/*获取可以作为指定迁移主机的列表*/
function getHostList(poolUuid) {
	$.ajax({
		type : 'get',
		url : 'HaAction/HostList',
		data : {
			poolUuid : poolUuid
		},
		dataType : 'json',
		success : function(array) {
			if (array.length >= 1) {
				var btable = document.getElementById("migratehosttable");
				var tableStr = '';
				for (var i = 1; i < array.length; i++) {
					var obj = array[i];
					var hostUuid = obj.hostUuid;
					var hostName = obj.hostName;
					var showid = 'host-' + hostUuid.substring(0, 8);
					var mytr = '<tr hostUuid="'
						+hostUuid
						+'" hostName ="'
						+ hostName
						+'"><td class="rcheck"><input name="migratehostrow" type="checkbox"></td>'
			    		+ '<td>'+ showid
			    		+'</td><td>'
			    		+ hostName+ '</td></tr>';
					tableStr = tableStr + mytr;
				}
				btable.innerHTML = tableStr;
			}
		}
	});
}