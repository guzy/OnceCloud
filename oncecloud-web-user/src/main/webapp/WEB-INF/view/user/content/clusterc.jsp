<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent">
	<div class="intro">
		<h1>集群&nbsp;Cluster</h1>
		<p class="lead" style="margin-top:10px">
			<em>集群&nbsp;(Cluster)</em>是由多台刀片式服务器组成，主服务器为资源池的唯一访问入口，由它负责对底层的硬件资源统一进行调度。通过主服务器和备用服务器，可以保证资源池的高可用。
		</p>
	</div>
	<div class="once-pane">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh">
				<span class="glyphicon glyphicon-refresh" style="margin-right:0"></span>
			</button>
			<button id="create" class="btn btn-primary"
				url="${basePath}cluster/create">+&nbsp;建立集群</button>
			<%-- <button id="accordance" class="btn btn-success"
				url="${basePath}admin/modal/pool/accordance" isac="${isac}" disabled>
				开启一致性</button> --%>


			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle"
					data-toggle="dropdown">
					更多操作... <span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a class="btn-forbidden" id="update"
						url="${basePath}cluster/create"><span
							class="glyphicon glyphicon-pencil"></span>修改</a></li>
					<!-- <li><a class="btn-forbidden" id="consistency"><span
							class="glyphicon glyphicon-check"></span>校验一致性</a></li> -->
					<li><a class="btn-forbidden" id="delete"><span
							class="glyphicon glyphicon-trash"></span>删除</a></li>
					<li><a class="btn-forbidden" id="repool"><span
							class="glyphicon glyphicon-flash"></span>恢复</a></li>
				</ul>
			</div>
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
								<ul id="pageDivider" style="display:inline">
								</ul>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th id="select" class = "select" width="4%">全选</th>
					<th width="16%">ID</th>
					<th width="16%">名称</th>
					<th width="14%">主服务器</th>
					<th width="14%">数据中心</th>
					<th width="10%">CPU总量</th>
					<th width="10%">内存总量</th>
					<th width="16%">创建时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="ClusterModalContainer" type="" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>
</div>