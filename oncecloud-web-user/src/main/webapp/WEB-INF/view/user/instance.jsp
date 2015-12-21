<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../common/head.jsp" %>
	<link rel="stylesheet" href="${basePath}css/instance.css" />
</head>
<body class="cloud-body">
	<%@ include file="../common/sidebar.jsp" %>
	<%@ include file="content/instancec.jsp" %>
	<%@ include file="../common/js.jsp" %>
	<script src="${basePath}js/user/instance/instancelist.js"></script>
	
	<c:if test="${fn:contains(operationAuth,'create')}">
	   <script src="${basePath}js/user/instance/createvm.js"></script>
	</c:if>
	
	<c:if test="${fn:contains(operationAuth,'destroy')}">
	    <script src="${basePath}js/user/instance/destoryvm.js"></script>
	</c:if>
	
	<c:if test="${fn:contains(operationAuth,'startup')}">
	    <script src="${basePath}js/user/instance/startandshutdownvm.js"></script>
    </c:if>
    
	<script src="${basePath}js/user/instance/updatevms.js"></script>
</body>
</html>