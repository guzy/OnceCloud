<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/maplocation.css" />
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=ead79fa4d2e6f6597c9cedc91732fc00"></script>
<script src="${basePath}js/admin/maplocation/maplocation.js"></script>
<div class="content" id="platformcontent" basePath="${basePath}">
	<div class="intro">
		<h1>地理位置&nbsp;Map Location</h1>
		<p class="lead" style="margin-top:10px">地理位置功能为您提供云平台服务器部署分布的位置信息。</p>
	</div>
	<div class="once-pane" id="cost_type">
		<div id="mapContainer" ></div>	
	</div>
</div>