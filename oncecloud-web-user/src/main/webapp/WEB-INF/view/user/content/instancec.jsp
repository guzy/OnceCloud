<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent" novnc="${vncServer}" platformBasePath="${basePath}">
	<div class="intro">
		<h1>主机&nbsp;Instances</h1>
		<p class="lead">
			云平台为您提供一种随时获取的、弹性的计算能力，这种计算能力的体现就是<em>主机&nbsp;(Instance)</em>。主机就是一台配置好了的服务器，它有您期望的硬件配置、操作系统和网络配置。通常情况下，您的请求可以在60秒左右的时间内完成，所以您完全可以动态地、按需使用计算能力。
		</p>
	</div>
	<div class="once-pane">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh">
				<span class="glyphicon glyphicon-refresh" style="margin-right:0"></span>
			</button>

			<c:if test="${fn:contains(operationAuth,'create')}">
				<button id="create" class="btn btn-primary"
					url="${basePath}instance/create">+&nbsp;新建</button>
			</c:if>

			<c:if test="${fn:contains(operationAuth,'startup')}">
				<button class="btn btn-default btn-disable" id="startup" disabled>
					<span class="glyphicon glyphicon-play"></span>启动
				</button>
			</c:if>

			<c:if test="${fn:contains(operationAuth,'shutdown')}">
				<button class="btn btn-default btn-disable" id="shutdown" disabled>
					<span class="glyphicon glyphicon-stop"></span>关机
				</button>
			</c:if>
			
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span  id="selectgroup">选择分组</span>
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu" id="select-group">
					<li><a id="groupall">全部</a></li>
				</ul>
			</div>
			
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle"
					data-toggle="dropdown">
					更多操作... <span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
					<c:if test="${fn:contains(operationAuth,'restart')}">
						<li><a class="btn-forbidden" id="restart"><span
								class="glyphicon glyphicon-repeat"></span>重启</a></li>
					</c:if>

					<c:if test="${fn:contains(operationAuth,'destroy')}">
						<li><a class="btn-forbidden" id="destroy"><span
								class="glyphicon glyphicon-trash"></span>销毁</a></li>
					</c:if>

					<c:if test="${fn:contains(operationAuth,'adjust')}">
						<li><a class="btn-forbidden" id="adjust"><span
								class="glyphicon glyphicon-cog"></span>更改配置</a></li>
					</c:if>

					<c:if test="${fn:contains(operationAuth,'image')}">
						<li><a class="btn-forbidden" id="image"
							url="${basePath}image/clone?rsid="><span
								class="glyphicon glyphicon-record"></span>制作映像</a></li>
					</c:if>
					<li><a class="btn-forbidden backup" id="backup"
						url="${basePath}snapshot/create?rsid=null&rstype=null&rsname=null"><span
							class="glyphicon glyphicon-camera"></span>备份</a></li>
					<li><a class="btn-forbidden" id="updateVMs"><span
						class="glyphicon glyphicon-cog"></span>批量修改</a></li>
				</ul>
			</div>
			<button class="btn btn-danger" id="status-check">
				<span class="glyphicon glyphicon-check"></span>状态校验
			</button>
			<input class="search" id="search" value="">
			<div class="toolbar-right">
				<table>
					<tr>
						<td>每页&nbsp;</td>
						<td><input id="limit" name="limit" class="page" value="10"></td>
						<td>&nbsp;个&nbsp;页数&nbsp;<a id="currentP"></a>&nbsp;/&nbsp;<a
							id="totalP"></a></td>
						<td style="padding-left:10px">
							<div>
								<ul class="pagination" id="pageDivider" style="display:inline"></ul>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="alert alert-info" id="once-tab-title">
				请通过页面进行关机操作，否则可能会导致关机状态无法识别，请点击虚拟机状态校验。
			</div>
		</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th id="select" class="select" width="4%">全选</th>
					<th width="12%">ID</th>
					<th width="10%">名称</th>
					<th width="10%">状态</th>
					<th width="6%">CPU</th>
					<th width="6%">内存</th>
					<th width="10%">网卡</th>
					<th width="8%">系统类型</th>
					<th width="10%">分组</th>
					<th width="10%">上次备份时间</th>
					<th width="10%">运行时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="InstanceModalContainer" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
		
</div>