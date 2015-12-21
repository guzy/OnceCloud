<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent">
	<div class="intro">
		<h1>存储&nbsp;Storages</h1>
		<p class="lead" style="margin-top:10px">
			<em>存储&nbsp;(Storage)</em>是云平台的共享存储单元，用于存储主机和映像文件，它是一个BFS的存储集群，也可以是其他支持的存储如GlusterFS或OCFS2。
		</p>
	</div>
    <div class="once-pane">
    	<div class="once-toolbar">
			<button class="btn btn-default btn-refresh"><span class="glyphicon glyphicon-refresh" style="margin-right:0"></span></button>
			<button id="create" class="btn btn-primary" url="${basePath}storage/create">
				+&nbsp;添加存储
			</button>
            <div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">更多操作...
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a class="btn-forbidden" id="load" url="${basePath}admin/modal/addtohost"><span class="glyphicon glyphicon-tasks"></span>添加到服务器</a></li>
		            <li><a class="btn-forbidden" id="update" url="${basePath}storage/create"><span class="glyphicon glyphicon-pencil"></span>修改</a></li>
		            <!-- <li><a class="btn-forbidden" id="delete"><span class="glyphicon glyphicon-trash"></span>删除</a></li> -->
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
                	<th width="4%"></th>
					<th width="16%">ID</th>
					<th width="16%">名称</th>
                    <th width="16%">地址</th>
                  	<th width="10%">挂载目录</th>
                    <th width="10%">文件系统</th>
                    <!-- <th width="12%">机架</th> -->
					<th width="16%">创建时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="SRModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div> 