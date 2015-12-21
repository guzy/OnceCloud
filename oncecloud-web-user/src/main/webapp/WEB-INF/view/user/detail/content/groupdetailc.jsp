<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content detail" id="platformcontent" groupUuid="${groupUuid}" basePath="${basePath}">
	<div class="intro">
		<h1>分组管理&nbsp;Group</h1>
		<p class="lead">
			使用<em>分组管理&nbsp;(Group)</em>，您可以对自己的虚拟机进行分组管理，并针对虚拟机制定分组规则。在虚拟机操作界面可以将已经对虚拟机添加到分组。
		</p>
	</div>
	<ol class="breadcrumb oc-crumb">
		<li><a href="${basePath}group"><span class="glyphicon glyphicon-flash cool-green"></span><span class="ctext">GROUPS</span></a></li>
		<li class="active"><a href="${basePath}group/detail">${showId}</a></li>
	</ol>
	<div class="col-md-4">
		<div class="detail-item">
			<div class="title">
				<h3>基本属性&nbsp;<a href="javascript:void(0)" class="btn basic-refresh"><span class="glyphicon glyphicon-refresh"></span></a>
					<div class="btn-group">
						<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							<span class="glyphicon glyphicon-tasks"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a id="modify"><span class="glyphicon glyphicon-pencil"></span>修改</a></li>
							<li><a id="basic-unbind"><span class="glyphicon glyphicon-remove"></span>解绑所有资源</a></li>
						</ul>
					</div>
				</h3>
			</div>
			<dl id="basic-list" class="my-horizontal"></dl>
		</div>
	</div>
	<div class="col-md-8">
		<div class="detail-item detail-right">
			<div class="once-pane" style="padding:0">
				<div class="title"><h3 class="uppercase">组内资源</h3><span class="oc-update" id="suggestion" style="display: none;">修改尚未更新，请点击"应用修改"</span></div>
				<div class="once-toolbar">
					<button class="btn btn-default rule-refresh"><span class="glyphicon glyphicon-refresh" style="margin-right:0"></span></button>
					<button id="addVMtoGroup" class="btn btn-default" url="${basePath}user/modal/bindgroup">+&nbsp;添加资源</button>
					<button id="deleteVMfromGroup" class="btn btn-default btn-disable" disabled><span class="glyphicon glyphicon-trash"></span>删除资源</button>
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
							<th id="select" class="select" width="10%">全选</th>
							<th>ID</th>
							<th>名称</th>
							<th>运行状态</th>
							<th>运行时间</th>
						</tr>
					</thead>
					<tbody id="tablebody">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="GroupModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
	<div id="ResourceModalContainer" type="new" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div> 
</div>