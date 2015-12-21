<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	.node circle {
	  fill: #fff;
	  stroke: steelblue;
	  stroke-width: 1.5px;
	}
	
	.node {
	  font: 12px sans-serif;
	}
	
	.link {
	  fill: none;
	  stroke: #ccc;
	  stroke-width: 1.5px;
	}
</style>
<script src="${basePath}js/user/distribute/d3.min.js"></script>
<div class="content" id="platformcontent" novnc="${vncServer}"
	platformBasePath="${basePath}">
	<input name="hidden-area" type="hidden" value="${user.userId}">
	<div class="intro">
		<h1>模板管理&nbsp;Template</h1>
		<p class="lead">
			通过模板管理，可以接管docker平台，能够方便的把应用部署在容器上，并通过端口映射对外界提供web服务。
		</p>
	</div>

	<div class="once-pane">
		<div class="once-toolbar">
			
		</div>
	</div>
	<div id="TemplateModalContainer" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>
