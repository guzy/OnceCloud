$(document).ready(function() {

	 //创建和修改区域信息时，验证合法性
	 
	 $('#area_name').on('focusout', function () {
		nameValidate();
	    });
	 
	 //
	 $('#area_domain').on('focusout', function () {
	        domainValidate();
	    });
	 //
	 $('#area_desc').on('focusout', function () {
		 descValidate();
		 
		
	    });
	 //
	 function nameValidate(){
		 $('#area_name').parent().find('.oc-error').remove();
		 var areaName = document.getElementById("area_name").value;
		 var patrn=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im; 
		 if(patrn.test(areaName)){  
			  
		  $('#area_name').parent().append('<label class="oc-error"><span class="help">名称不能含有非法字符</span></label>'); 
		   return false;     
	       }     
		 return true;  
	 }
	 
	 function descValidate(){
		 $('#area_desc').parent().find('.oc-error').remove();
		 var areaDesc = document.getElementById("area_desc").value;
		 var patrn=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im; 
		 if(patrn.test(areaDesc)){  
			  
		  $('#area_desc').parent().append('<label class="oc-error"><span class="help">描述不能含有非法字符</span></label>'); 
		   return false;      
	       }     
		 return true;  
	 }
	  //
	  function domainValidate() {
		    var flag = false;
	        $('#area_domain').parent().find('.oc-error').remove();
	        var patrn = /^[0-9a-z]+[0-9a-z\.-]*\.[a-z]{2,3}$/i;
	        var areaDomain = document.getElementById("area_domain").value;
	        var oldname = $('#area_domain').val();
	        if (areaDomain == "") {
	            $('#area_domain').parent().append('<label class="oc-error"><span class="help">域名不能为空</span></label>');
	        } else if (!patrn.test(areaDomain)) {
	            $('#area_domain').parent().append('<label class="oc-error"><span class="help">域名格式不正确</span></label>');
	        } else if (areaDomain == oldname) {
	        	flag= true;
	        } else {
	            $.ajax({
	                type: 'get',
	                async: false,
	                url: 'AreaAction/QueryArea',
	                data: {areaDomain: areaDomain},
	                dataType: 'json',
	                success: function (array) {
	                    if (array.length == 1) {
	                        var exist = array[0].exist;
	                        if (exist == true) {
	                            $('#area_domain').parent().append('<label class="oc-error"><span class="help">域名已存在</span></label>');
	                        } else {
	                        	flag= true;
	                        }
	                    }
	                }
	            });
	        }
	        return flag;
	    }
	  // 创建区域信息
	$('#createAreaAction').on('click', function(event) {
		event.preventDefault();
		
		//判断输入是否合法
		
		if(nameValidate()==true && domainValidate() ==true && descValidate()==true){
			
			var areaId = uuid.v4();
			var areaName = document.getElementById("area_name").value;
			var areaDomain = document.getElementById("area_domain").value;
			var areaDesc = document.getElementById("area_desc").value;
				$.ajax({
					type : 'post',
					url : 'AreaAction/Create',
					data : {
						areaId : areaId,
						areaName : areaName,
						areaDomain : areaDomain,
						areaDesc : areaDesc,
					// areaCreateTime : areaCreateTime
					},
					dataType : 'json',
					success : function(array) {
						$('#AreaModalContainer').modal('hide');
						//alert("区域创建成功");
						location.reload() 
					}
				});
		}
		
	});

	//修改区域信息
	 $('#modifyUapdateArea').on('click', function(event) {
		 
	    	event.preventDefault();
	    	
	    //判断输入是否合法
		if(nameValidate()==true && domainValidate() ==true && descValidate()==true){
			
	    	var areaId = document.getElementById("area_id").value;
	    	var areaName = document.getElementById("area_name").value;
			var areaDomain = document.getElementById("area_domain").value;
			var areaDesc = document.getElementById("area_desc").value;
		    // alert(areaId+"1"+areaName+"2"+areaDomain+"3"+areaDesc);
		  
	    	$.ajax({
	            type: 'post',
	            url: 'AreaAction/Update',
	            data: {
	            	areaId : areaId,
	            	areaName : areaName,
					areaDomain : areaDomain,
					areaDesc : areaDesc
	            },
	            dataType: 'text',
	            success: function (result) {
	            	if(result == 0){
	            		//bootbox.alert('修改信息成功');
	            		$('#AreaModalContainer').modal('hide');
	            		location.reload() 
	            	}
	                
	            }
	        });
			}
	    });
	 
	 
});
