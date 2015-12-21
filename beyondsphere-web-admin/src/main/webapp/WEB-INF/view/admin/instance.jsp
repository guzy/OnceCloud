<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../common/head.jsp" %>
	<link rel="stylesheet" href="${basePath}css/instance.css" />
</head>
<body class="cloud-body">
<input type="hidden" id="base_url" value="${basePath}">
	<%@ include file="../common/sidebar.jsp" %>234
	<%@ include file="content/instancec.jsp" %>
	<%@ include file="../common/js.jsp" %>
	<script src="${basePath}js/admin/instance/instance.js"></script>
</body>
</html>