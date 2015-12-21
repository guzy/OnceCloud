<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent">
	<div class="intro">
		<h1>分组管理&nbsp;Group</h1>
		<p class="lead">
			使用<em>分组管理&nbsp;(Group)</em>，您可以对自己的虚拟机进行分组管理，并针对虚拟机制定分组规则。在虚拟机操作界面可以将已经对虚拟机添加到分组。
		</p>
	</div>
	<div class="once-pane">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh"><span class="glyphicon glyphicon-refresh" style="margin-right:0"></span></button>
			<button id="create" class="btn btn-primary" url="${basePath}group/create">+&nbsp;新建</button>
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">更多操作...
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a class="btn-forbidden" id="modify" url="${basePath}user/modal/modifygroup"><span class="glyphicon glyphicon-edit"></span>修改</a></li>
					<li><a class="btn-forbidden" id="delete"><span class="glyphicon glyphicon-trash"></span>删除</a></li>
					<li><a class="btn-forbidden" id="bindgroup" url="${basePath}user/modal/bindgroup"><span class="glyphicon glyphicon-th"></span>绑定资源</a></li>
				</ul>
			</div>
			<!-- <input class="search" id="search" value=""> -->
			<div class="toolbar-right">
				<table>
					<tr>
						<td>每页&nbsp;</td>
						<td><input id="limit" name="limit" class="page" value="10"></td>
						<td>&nbsp;个&nbsp;页数&nbsp;<a id="currentP"></a>&nbsp;/&nbsp;<a id="totalP"></a></td>
						<td style="padding-left:10px">
							<div><ul class="pagination" id="pageDivider" style="display:inline"></ul></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th id="select" class="select" width="4%">全选</th>
					<th width="10%">ID</th>
					<th width="16%">类别</th>
					<th width="20%">显示颜色</th>
					<th width="30%">详细信息</th>
					<th width="24%">创建时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
		<div id="applet"></div>
	</div>
	<div id="GroupModalContainer" type="new" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>