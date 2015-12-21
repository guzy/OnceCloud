<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}bootstrap/js/bootstrap.min.js"></script>
<script src='${basePath}bootstrap/js/bootbox.min.js'></script>
<script src="${basePath}bootstrap/js/bootstrap-datetimepicker.js"></script>
<script src="${basePath}bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="${basePath}bootstrap/js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/common/jquery-ui-1.10.4.custom.min.js"></script>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/common/sticky.full.js"></script>
<script src='${basePath}js/common/uuid.js'></script>
<script src="${basePath}js/common/oncecloud.js"></script>
<script src="${basePath}js/common/message.js"></script>
<script>
	var basePath = "${basePath}";
	$(function(){
		$(document).ajaxComplete(function(event,request, settings){
			if(request.status == 403)
				alert("您没有权限进行此操作！");
		 });
	/*     .ajaxSend(function(event, request, settings){
	    	console.log("开始请求: " + settings.url );
	    }); */
	})
	
</script>
