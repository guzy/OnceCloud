/**调用修改用户信息的窗口*/
$(document).ready(function () {
	
	//
	$("#modifypriceinfo").on('click', function(event) {
		event.preventDefault();
	    $('#priceModalContainer').load($(this).attr('url'), '',
	        function () {
	            $('#priceModalContainer').modal({
	                backdrop: false,
	                show: true
	            });
	    	});
	});
	

	$("#modifyadmin").on('click', function(event) {
		event.preventDefault();
	    $('#adminModalContainer').load($(this).attr('url'), '',
	        function () {
	            $('#adminModalContainer').modal({
	                backdrop: false,
	                show: true
	            });
	    	});
	});
	
	//
	$("#selectAllArea").on('click', function() {
		//alert("11111");
	$.ajax({
        type: 'get',
        url: 'AreaAction/AreaAllList',
        dataType: 'json',
        success: function (array) {
        	//alert("12345");
        	$("#selectArea").html("");
            $.each(array, function (index, json) {
            	url = "http://"+json.areaDomain+":8080/beyondbackend/backdoor";
                $("#selectArea").append('<li><a id="area'+index+'" href='+url+'><sapn chalss="glyphicon glyphicon-tasks"></span>' + json.areaName + '</a></li>');
                     
                $("#area" + index).on("click", function () {
                	 $("#selectAllArea").text(json.areaName);
            		//alert("11111");
                	//记录该用户现在访问的区域，保存到数据库
            	$.ajax({
                    type: 'post',
                    url: 'UserAction/UpdateAreaInfo',
                    data:{userActiveLocation:json.areaId},
                    dataType: 'json',
                    success: function (result) {
                    	if(result==0){
                    		alert("12345");
                    	}else{
                    		alert("失败");
                    	}
                    }
                   });
            	  });
            });
            
        }
       });
	});
	
});
