<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="http://bootboxjs.com/bootbox.js"></script>
<input name="hidden-area" type="hidden" value="${user.userId}" level="${user.userLevel}">
<div class="content" id="platformcontent">
	<div class="intro">
		<h1>网络&nbsp;Networks</h1>
		<p class="lead" style="margin-top: 10px">
			<em>网络&nbsp;(Networks)</em>通过云平台的SDN技术，您可以快速地搭建您专属的私有云环境。相比于基础网络而言，
			这个网络可以提供100%的安全隔离，而且有丰富的工具帮助您进行自动化管理。要使用私有网络，请创建一个路由器，
			然后再创建一个或多个私有网络并连接到这个路由器，最后创建主机并加入到这些私有网络即可。
		</p>
	</div>
	<ul class="nav nav-tabs once-tab">
		<li class="tab-filter active" type="router">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-fullscreen"></span>路由器</a>
		</li>
		<li class="tab-filter" type="general">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-fullscreen"></span>虚拟局域网vlan</a>
		</li>
		<li class="tab-filter" type="distributed">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>虚拟可扩展局域网Vxlan</a>
		</li>
	</ul>
	<div class="once-pane">
		<div class="alert alert-info" id="once-tab-title"></div>
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh">
				<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
			</button>

			<button id="create" class="btn btn-primary" url="${basePath}image/create">
				+&nbsp;新建
			</button>

			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
					更多操作... <span class="caret" style="margin-left: 15px"></span>
				</button>
				<ul class="dropdown-menu" id="dropdown-menu">
					<%-- <li><a  id="addvm" url="${basePath}vnet/bindvm"><span class="glyphicon glyphicon-cloud"></span>添加主机</a></li> --%>
					<li><a  id="link" url="${basePath}admin/modal/linkrouter"><span class="glyphicon glyphicon-cloud-upload"></span>连接路由器</a></li>
		           	<li><a  id="unlink"><span class="glyphicon glyphicon-cloud-download"></span>离开路由器</a></li>
		           	<li><a  id="delete"><span class="glyphicon glyphicon-trash"></span>删除</a></li>
					<!-- <li><a id="delete"><span class="glyphicon glyphicon-trash"></span>删除</a></li> -->
					<li><a class="btn-forbidden" id="startup"><span class="glyphicon glyphicon-play"></span>启动</a></li>
 					<li><a class="btn-forbidden" id="shutdown"><span class="glyphicon glyphicon-stop"></span>关机</a></li>
		            <li><a class="btn-forbidden" id="destroy"><span class="glyphicon glyphicon-trash"></span>销毁</a></li>
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
						<td style="padding-left: 10px">
							<div>
								<ul class="pagination" id="pageDivider" style="display: inline"></ul>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="table table-bordered once-table" id="vlan_table">
			<thead>
				<tr>
				    <th id="select" class="select" width="4%">全选</th>
					<th width="14%">ID</th>
					<!-- <th width="14%">名称</th> -->
					<th width="8%">状态</th>
					<th width="12%">类型</th>
					<th width="12%" id="th_router">虚拟路由</th>
				    <th width="14%">创建时间</th>
				    <!-- <th>所属资源池</th> -->
				</tr>
			</thead>
			<tbody id="vlan_tablebody">
			</tbody>
		</table>
		
		<table class="table table-bordered once-table" id="router_table">
			<thead>
				<tr id="tablehead">
				    <th width="4%"></th>
					<th width="14%">ID</th>
					<th width="14%">名称</th>
					<th width="15%">用户名</th>
					<th width="10%">状态</th>
					<th width="14%">网络</th>
					<th width="15%">IP段</th>
					<th width="14%">创建时间</th>
				</tr>
			</thead>
			<tbody id="router_tablebody">
				<!-- <tr rowid="91d38125-0c64-4e01-b4ff-f980aec82ead" rtname="test-router">
					<td class="rcheck"><input type="checkbox" name="rtrow"></td>
					<td><a class="id">rt-91d38125</a></td>
					<td>test-router</td>
					<td>软件所</td>
					<td><span class="icon-status icon-running" name="stateicon"></span><span name="stateword">活跃</span></td>
					<td name="sip"><a>(基础网络)&nbsp;/&nbsp;10.0.1.79</a></td>
					<td name="eip">192.168.1.X</td>
					<td class="time">Vlan1</td>
				</tr> -->
			</tbody>
		</table>
	</div>
	<div id="RouterModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
    <div id="NetworkModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
    <div id="VnetModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>
