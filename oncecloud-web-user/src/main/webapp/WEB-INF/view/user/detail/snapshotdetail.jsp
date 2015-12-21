<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="../../common/head.jsp" %>
	<link rel="stylesheet" href="${basePath}css/jquery.contextMenu.css" />
	<link rel="stylesheet" href="${basePath}css/snapshot.css" />
</head>
<body class="cloud-body">
	<%@ include file="../../common/sidebar.jsp" %>
    <%@ include file="content/snapshotdetailc.jsp" %>
	<%@ include file="../../common/js.jsp" %>
	<script src="${basePath}js/common/jquery.contextMenu.js"></script>
	<script src="${basePath}js/user/detail/snapshotdetail.js"></script>
</body>
</html>