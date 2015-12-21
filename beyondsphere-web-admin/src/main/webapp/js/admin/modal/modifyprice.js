$(document).ready(function () {
	fillbank();
	function fillbank(){
		$.ajax({
			type: 'get',
	        async: true,
	        url: basePath+'unitPriceAction/priceList',
	        dataType: 'json',
	        success: function (array) {
	            if (array.length > 1) {
	                for(var i = 0; i < array.length; i++){
	                	switch(array[i].pricetype){
	            		case 'cpu':
	            			$('#cpu-lable').val('CPU');
	            			$('#cpu').val('￥'+array[i].pricevalue);
	            			break;
	            		case 'mem':
	            			$('#mem-lable').val('内存');
	            			$('#memory').val('￥'+array[i].pricevalue);
	            			break;
	            		case 'volume':
	            			$('#volume-lable').val('硬盘');
	            			$('#volume').val('￥'+array[i].pricevalue);
	            			break;
	            		case 'snapshot':
	            			$('#snapshot-lable').val('备份');
	            			$('#snapshot').val('￥'+array[i].pricevalue);
	            			break;
	                	}
	                }
	            }
	        }
		});
	}
	
	$('#modifyPriceAction').on('click', function(){
		var cpuPrice = $('#cpu').val();
		var memPrice = $('#memory').val();
		var volumePrice = $('#volume').val();
		var snapshotPrice = $('#snapshot').val();
		$.ajax({
			type: 'post',
            url: basePath+'unitPriceAction/update',
            data: {
            	cpuType : 'cpu',
            	cpuPrice : cpuPrice.substr(1),
            	memType : 'mem',
            	memPrice : memPrice.substr(1),
            	volumeType : 'volume',
            	volumePrice : volumePrice.substr(1),
            	snapshotType : 'snapshot',
            	snapshotPrice : snapshotPrice.substr(1)
            },
            dataType: 'text',
            success: function () {
            	bootbox.alert('修改价格成功！');
            	$('#priceModalContainer').modal('hide');
            },
            error: function(){
            	bootbox.alert('修改价格失败！');
            }
		});
	});
});
