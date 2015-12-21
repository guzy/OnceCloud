<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent" basePath="${basePath}">
	<div class="intro">
		<h1>监测管理&nbsp;Monitor</h1>
		<p class="lead" style="margin-top:10px">监测管理包括应用、APM等使用情况的管理</p>
	</div>
	<ul class="nav nav-tabs once-tab">
		<li class="tab-filter active" type="cost_overview">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>应用</a>
		</li>
		<li class="tab-filter" type="cost_detail">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>APM</a>
		</li>
	</ul>
	<div class="once-pane" id="cost_overview" style="height:500px;">
		<div class="alert alert-info">
			针对应用拓扑图编排的监测管理。
		</div>
		<div style="height:100%;">
			<iframe name="ifr1" id="ifr1" frameborder="0" scrolling="auto"  src="http://133.133.10.27:8080/cloudeploy/" width="100%" height="100%"></iframe> 
		</div>
	</div>
	<div class="once-pane" id="cost_detail" style="visibility:hidden; height:600px;">
		<div class="alert alert-info">
			针对平台访问运行状态的监测管理。
		</div>
		<div style="height:100%">
			<iframe name="ifr2" id="ifr2" frameborder="0" scrolling="auto"  src="http://133.133.133.87:8080/web/APMIndex.jsp" width="100%" height="100%"></iframe>
		</div>
	</div>
	
</div>