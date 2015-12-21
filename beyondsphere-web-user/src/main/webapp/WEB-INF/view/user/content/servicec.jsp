<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content" id="platformcontent" userLevel="${user.userLevel}">
	<div class="intro">
		<h1>表单&nbsp;Services</h1>
		<p class="lead" style="margin-top:10px">
			<em>表单系统&nbsp;(Services)&nbsp;</em>是您与我们最直接有效的交流平台，您可以通过表单系统来咨询任何问题，我们会第一时间为您解决。同时我们也欢迎您提交建议与意见。
		</p>
	</div>
	<div class="once-pane">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh"><span class="glyphicon glyphicon-refresh" style="margin-right:0"></span></button>
				<c:if test="${fn:contains(operationAuth,'apply')}">
				<button id="apply" class="btn btn-primary" url="${basePath}service/create">
					<span class="glyphicon glyphicon-tags"></span>提交表单
				</button>
				</c:if>
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
					<th width="20%">标题</th>
					<th>内容摘要</th>
					<th width="10%">回复数</th>
					<th width="10%">状态</th>
					<th width="15%">创建时间</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="ServiceModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>