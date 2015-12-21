<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent">
	<div class="intro">
		<h1>服务器&nbsp;Servers</h1>
		<p class="lead" style="margin-top:10px">
			<em>服务器&nbsp;(Server)</em>是云平台中最小的物理单元，是高性能的刀片式服务器，为主机提供运行环境。
		</p>
	</div>
    <div class="once-pane">
    	<div class="once-toolbar">
			<button class="btn btn-default btn-refresh"><span class="glyphicon glyphicon-refresh" style="margin-right:0"></span></button>
			<button id="create" class="btn btn-primary" url="${basePath}host/create">
				+&nbsp;连接服务器
			</button>
			<%-- <button id="startServer" class="btn btn-success">启动服务器</button>
			<button id="stopServer" class="btn btn-danger">停止服务器</button>
			<button id="power" class="btn btn-danger" url="${basePath}admin/modal/host/power">
				<span class="glyphicon glyphicon-off"></span>&nbsp;电源管理
			</button> --%>
            <div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">更多操作...
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a class="btn-forbidden" id="add2pool"><span class="glyphicon glyphicon-tint"></span>添加到集群</a></li>
					<%-- <li><a class="btn-forbidden" id="remove4pool"><span class="glyphicon glyphicon-save"></span>从集群移除</a></li>
					<li><a class="btn-forbidden" id="update" url="${basePath}user/modal/host/modify"><span class="glyphicon glyphicon-pencil"></span>修改</a></li>
					<li><a class="btn-forbidden" id="delete"><span class="glyphicon glyphicon-trash"></span>删除</a></li>
					<li><a class="btn-forbidden" id="recover"><span class="glyphicon glyphicon-flash"></span>修复</a></li> --%>
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
					<th width="12%">ID</th>
					<th width="14%">名称</th>
					<th width="12%">地址</th>
					<th width="8%">CPU</th>
                    <th width="10%">内存</th>
                    <th width="10%">集群</th>
                    <th width="6%">存储</th>
					<th width="14%">创建时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="ServerModalContainer" type="" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
    </div> 
</div>