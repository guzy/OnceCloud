<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content" id="platformcontent" novnc="${vncServer}"
	platformBasePath="${basePath}">
	<input name="hidden-area" type="hidden" value="${user.userId}">
	<div class="intro">
		<h1>模板管理&nbsp;Template</h1>
		<p class="lead">
			通过模板管理，可以接管docker平台，能够方便的把应用部署在容器上，并通过端口映射对外界提供web服务。
		</p>
	</div>

	<div class="once-pane">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh">
				<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
			</button>
			<button id="upfileModel" class="btn btn-primary" url="${basePath}user/modal/createtemplate">
				<span class="glyphicon glyphicon-user"></span>发布镜像
			</button>
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle"
					data-toggle="dropdown">
					更多操作... <span class="caret" style="margin-left: 15px"></span>
				</button>
				<ul class="dropdown-menu">
                    <li><a class="btn-forbidden" id="pushTemplate"><span
							class="glyphicon glyphicon-trash"></span>发布</a></li>
					<li><a class="btn-forbidden" id="deleteTemplate"><span
							class="glyphicon glyphicon-trash"></span>删除</a></li>
				</ul>
			</div>
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
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th id="select" class="select" width="4%">全选</th>
					<th width="12%">ID</th>
					<th width="10%">位置</th>
					<th width="10%">版本</th>
					<th width="10%">大小</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="TemplateModalContainer" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>
