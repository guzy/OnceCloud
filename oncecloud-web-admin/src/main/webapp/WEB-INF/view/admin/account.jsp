<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../common/head.jsp" %>
	<link rel="stylesheet" href="${basePath}css/account.css" />
	<link rel="stylesheet" href="${basePath}css/water_meter.css" />
</head>
<body class="cloud-body">
	<%@ include file="../common/sidebar.jsp" %>
	<%@ include file="content/accountc.jsp" %>
	<%-- <%@ include file="create/createcost.jsp" %> --%>
	<%@ include file="../common/js.jsp" %>
	<script src="${basePath}js/common/highcharts.js"></script>
	<script src="${basePath}js/admin/account/account.js"></script>
	<script src="${basePath}js/admin/account/cost.js"></script>
	<script src="${basePath}js/admin/account/forecast.js"></script>
</body>
</html>