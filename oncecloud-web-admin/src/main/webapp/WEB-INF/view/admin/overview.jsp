<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../common/head.jsp" %>
	<link rel="stylesheet" href="${basePath}css/overview.css" />
</head>
<body class="cloud-body">
	<%@ include file="../common/sidebar.jsp" %>
	<%@ include file="content/overviewc.jsp" %>
	<%@ include file="../common/js.jsp" %>
	<script src="${basePath}js/common/highcharts.js"></script>
	<script src="${basePath}js/admin/overview/overview.js"></script>
</body>
</html>