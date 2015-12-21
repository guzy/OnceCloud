<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent">
	<div class="intro">
		<h1>硬盘&nbsp;Volumes</h1>
		<p class="lead">
			<em>硬盘&nbsp;(Volume)</em>为主机提供块存储设备，它独立于主机的生命周期而存在，可以被连接到任意运行中的主机上。当硬盘附加到主机上后，还需要登陆到主机的操作系统中去加载该硬盘。卸载硬盘时请先在主机的操作系统中完成卸载。
		</p>
	</div>
	<div class="once-pane">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh"><span class="glyphicon glyphicon-refresh" style="margin-right:0"></span></button>
			
				<c:if test="${fn:contains(operationAuth,'create')}">
			<button id="create" class="btn btn-primary" url="${basePath}volume/create">+&nbsp;新建</button>
			   </c:if>
			   
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">更多操作...
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
				<c:if test="${fn:contains(operationAuth,'bind')}">
					<li><a class="btn-forbidden" id="bind" url="${basePath}volume/bind"><span class="glyphicon glyphicon-cloud"></span>加载到主机</a></li>
				 </c:if>
				<c:if test="${fn:contains(operationAuth,'unbind')}">
					<li><a class="btn-forbidden" id="unbind"><span class="glyphicon glyphicon-cloud-download"></span>卸载硬盘</a></li>
				 </c:if>
				<c:if test="${fn:contains(operationAuth,'backup')}">
					<li><a class="btn-forbidden backup" id="backup" url="${basePath}snapshot/create?rsid=null&rstype=null&rsname=null"><span class="glyphicon glyphicon-camera"></span>备份</a></li>
				 </c:if>
				<c:if test="${fn:contains(operationAuth,'delete')}">
					<li><a class="btn-forbidden" id="delete"><span class="glyphicon glyphicon-trash"></span>删除</a></li>
				 </c:if>
				</ul>
			</div>
			<input class="search" id="search" value="">
			<div class="toolbar-right">
				<table>
					<tr>
						<td>每页&nbsp;</td>
						<td><input id="limit" name="limit" class="page" value="10"></td>
						<td>&nbsp;个&nbsp;页数&nbsp;<a id="currentP"></a>&nbsp;/&nbsp;<a id="totalP"></a></td>
						<td style="padding-left:10px">
							<div><ul id="pageDivider" style="display:inline"></ul></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th id="select" class="select" width="4%">全选</th>
					<th width="12%">ID</th>
					<th width="12%">名称</th>
					<th>状态</th>
					<th>应用主机</th>
					<th>容量&nbsp;(GB)</th>
					<th>上次备份时间</th>
					<th>运行时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="VolumeModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>