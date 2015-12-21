<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../common/head.jsp" %>
</head>
<body class="cloud-body">
	<%@ include file="../common/sidebar.jsp" %>
	<%@ include file="content/imagec.jsp" %>
	<%@ include file="../common/js.jsp" %>
	<script src="${basePath}js/user/image/imagelist.js"></script>
	<c:if test="${fn:contains(operationAuth,'delete')}">
	     <script src="${basePath}js/user/image/deleteimage.js"></script>
	</c:if>
</body>
</html>