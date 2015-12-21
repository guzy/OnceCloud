<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent">
	<div class="intro">
	    <input type="hidden" id="powerstate">
		<h1>高可用管理&nbsp;High Availability</h1>
		<p class="lead" style="margin-top:10px">
			<em>高可用管理&nbsp;(HA)</em>指的是通过尽量缩短因日常维护操作（计划）和突发的系统崩溃（非计划）所导致的停机时间，
			以提高系统和应用的可用性。
		</p>
	</div>
	<div class="once-pane">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh">
				<span class="glyphicon glyphicon-refresh" style="margin-right:0"></span>
			</button>
			<div class="checkbox box-style">
              <label>
                <input id="ha-box" type="checkbox" disabled> 启用 HA
              </label>
            </div>
            
			<div class="btn-group">
              <button class="btn btn-default dropdown-toggle" id="ha-service" 
                type="button" data-toggle="dropdown" disabled>HA服务管理&nbsp;&nbsp;
                <span class="caret"></span>
              </button>
              <ul class="dropdown-menu menu-style" role="menu">
                <%-- <li>
                  <a id="host-monitor" url="${basePath}admin/modal/ha/monitor">
                    <span class="glyphicon glyphicon-eye-open"></span>主机监控
                  </a>
                </li> --%>
                <li>
                  <a id="access-control" url="${basePath}admin/modal/ha/access">
                    <span class="glyphicon glyphicon-edit"></span> 接入控制
                  </a>
                </li>
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
					<th width="4%"></th>
					<th width="14%">ID</th>
					<th width="8%">名称</th>
					<th width="6%">主服务器</th>
					<th width="6%">数据中心</th>
					<th width="7%">CPU总量</th>
					<th width="7%">内存总量</th>
					<th width="8%">启用HA管理</th>
					<th width="8%">启用主机监控</th>
					<th width="16%">启用控制策略</th>
					<th width="16%">创建时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="PoolModalContainer" type="" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>
