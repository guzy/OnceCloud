<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content" id="platformcontent" novnc="${vncServer}" platformBasePath="${basePath}">
<input name="hidden-area" type="hidden" value="${user.userId}">
	<div class="intro">
		<!-- 应用部署 -->
		<h1>仓库管理&nbsp;Repository</h1>
		<p class="lead">
			<!-- 通过应用部署应用程序、服务或组件，你可以将其分发以便安装于其他计算机、设备、服务器或云中。 -->
			通过容器管理，可以接管docker平台，能够方便的把应用部署在容器上，并通过端口映射对外界提供web服务。
		</p>
	</div>
	<!-- http://133.133.10.27:8080/cloudeploy -->
	<!-- <iframe   name="ifr1" id="ifr1" frameborder="0" scrolling="auto"  src="http://192.168.248.129:9000/" width="100%" height="100%"></iframe> -->

	<div class="once-pane">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh">
				<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
			</button>

			<%-- <button id="create" class="btn btn-primary"
				url="${basePath}platform/create">+&nbsp;新建</button>

			<button class="btn btn-default btn-disable" id="startup" disabled>
				<span class="glyphicon glyphicon-play"></span>启动
			</button>

			<button class="btn btn-default btn-disable" id="shutdown" disabled>
				<span class="glyphicon glyphicon-stop"></span>关机
			</button>

			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle"
					data-toggle="dropdown">
					更多操作... <span class="caret" style="margin-left: 15px"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a class="btn-forbidden" id="restart"><span
							class="glyphicon glyphicon-repeat"></span>重启</a></li>

					<li><a class="btn-forbidden" id="destroy"><span
							class="glyphicon glyphicon-trash"></span>销毁</a></li>
				</ul>
			</div>
			<input class="search" id="search" value=""> --%>
			<div class="toolbar-right">
				<table>
					<tr>
						<td>每页&nbsp;</td>
						<td><input id="limit" name="limit" class="page" value="10"></td>
						<td>&nbsp;个&nbsp;页数&nbsp;<a id="currentP"></a>&nbsp;/&nbsp;<a
							id="totalP"></a></td>
						<td style="padding-left: 10px">
							<div>
								<ul class="pagination" id="pageDivider" style="display: inline"></ul>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<!-- <div class="alert alert-info" id="once-tab-title">
				请通过页面进行关机操作，否则可能会导致关机状态无法识别，请点击虚拟机状态校验。</div> -->
		</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th width="12%">ID</th>
					<th width="12%">名称</th>
					<th width="10%">版本</th>
					<th width="10%">镜像位置</th>
					
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="ContainerModalContainer" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>
