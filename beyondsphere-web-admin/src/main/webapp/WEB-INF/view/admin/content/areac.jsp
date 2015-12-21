<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent"  novnc="${vncServer}">
	<div class="intro">
		<h1>区域管理&nbsp;Areas</h1>
		<p class="lead">
			<em>区域管理&nbsp;(area)</em>是一台配置好了的服务器，它有您期望的硬件配置、操作系统和网络配置。
		</p>
	</div>
	 <div class="once-pane">
    	<div class="once-toolbar">
			<button class="btn btn-default btn-refresh"><span class="glyphicon glyphicon-refresh" style="margin-right:0"></span></button>
			<button id="create" class="btn btn-primary" url="${basePath}area/create">
				<span class="glyphicon glyphicon-user"></span>新建区域
			</button>
            <div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">更多操作...
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a id="updateArea"><span class="glyphicon glyphicon-edit"></span>修改</a></li> 
					<li><a id="delete"><span class="glyphicon glyphicon-trash"></span>删除</a></li>
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
							<div><ul class="pagination" id="pageDivider" style="display:inline"></ul></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th id="select" class = "select" width="4%">全选</th>
					<th width="15%">ID</th>
					<th width="15%">名称</th>
					<th width="15%">域名</th>
					<th width="20%">描述</th>
					<th width="15%">创建时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="AreaModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>
