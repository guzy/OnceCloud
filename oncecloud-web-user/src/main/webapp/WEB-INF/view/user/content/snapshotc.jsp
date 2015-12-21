<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content"  id="platformcontent">
	<div class="intro">
		<h1>备份&nbsp;Snapshots</h1>
		<p class="lead" style="margin-top:10px">
			<em>备份&nbsp;(Snapshot)</em>用于对正在运行的主机做在线备份。一个主机可以有一个备份链，每条备份链包括多个备份点，您可以随时从任意一个备份点恢复数据。
		</p>
	</div>
	<div class="once-pane">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh"><span class="glyphicon glyphicon-refresh" style="margin-right:0"></span></button>
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">更多操作...
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
					<c:if test="${fn:contains(operationAuth,'delete')}">
					<li><a class="btn-forbidden" id="delete"><span class="glyphicon glyphicon-trash"></span>删除</a></li>
					</c:if>
				</ul>
			</div>
			<input class="search" id="search" value="">
			<div class="alert alert-info" id="once-tab-title">
				通过主机界面"更多操作"的"备份"功能建立主机备份，通过硬盘界面的"更多操作"菜单栏下的"备份"功能建立硬盘备份
			</div>
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
					<th width="12%">备份链&nbsp;ID</th>
					<th width="12%">资源名称</th>
					<th>状态</th>
					<th>资源类型</th>
					<th>总量&nbsp;(GB)</th>
					<th>备份点&nbsp;(个)</th>
					<th width="12%">距上次备份时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
</div>