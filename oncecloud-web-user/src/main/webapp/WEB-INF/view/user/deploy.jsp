<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../common/head.jsp" %>
	<link rel="stylesheet" href="${basePath}assets/css/font-awesome-4.2.0/css/font-awesome.min.css" />
	<link rel="stylesheet" href="${basePath}assets/css/cloudeploy/cloudeploy.css" />
	<link rel="stylesheet" href="${basePath}assets/css/cloudeploy/app-list.css" />
	<link rel="stylesheet" href="${basePath}assets/css/cloudeploy/app-panel.css" />
</head>
<body class="cloud-body">
	<%@ include file="../common/sidebar.jsp" %>
	<%@ include file="content/deployc.jsp" %>
	<%@ include file="../common/js.jsp" %>
	<script src="${basePath}assets/js/jquery/json/jquery.json-2.4.min.js"></script>
	<script src="${basePath}assets/js/jquery/dateFormat/jquery.dateFormat.js"></script>
	<script src="${basePath}assets/js/cloudeploy/uri.js"></script>
	<script src="${basePath}assets/js/cloudeploy/map.js"></script>
	<script src="${basePath}assets/js/cloudeploy/common.js"></script>
	<script src="${basePath}assets/js/cloudeploy/app/app-main.js"></script>
</body>
</html>