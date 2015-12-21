<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<input name="hidden-area" type="hidden" value="${user.userId}">
<div class="content" id="platformcontent">
	<div class="intro">
		<h1>映像&nbsp;Images</h1>
		<p class="lead" style="margin-top: 10px">
			<em>映像&nbsp;(Image)</em>是带有操作系统的主机模板。云平台官方会提供主流的 Linux、Windows
			模板，并根据上游厂商更新版本时及时制作新模板。用户也可以在将自己名下的主机制作成模板，以备后用。
		</p>
	</div>
	<ul class="nav nav-tabs once-tab">
		<li class="tab-filter active" type="system">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-star"></span>系统</a>
		</li>
		<li class="tab-filter" type="user">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-user"></span>自有</a>
		</li>
	</ul>
	<div class="once-pane">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh">
				<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
			</button>

			<div class="btn-group" style="display:none">
					<button class="btn btn-default dropdown-toggle"
						data-toggle="dropdown">
						更多操作... <span class="caret" style="margin-left: 15px"></span>
					</button>
					<ul class="dropdown-menu">
					
					<c:if test="${fn:contains(operationAuth,'delete')}">
						<li><a class="btn-forbidden" id="delete"><span
								class="glyphicon glyphicon-trash"></span>删除</a></li>
								</c:if>
					</ul>
			</div>
			
			<input class="search" id="search" placeholder="" value="">
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
					<th width="14%">ID</th>
					<th width="14%">名称</th>
					<th width="8%">容量&nbsp;(GB)</th>
					<th width="12%">平台</th>
					<th width="14%">状态</th>
					<th width="12%" id="image-area">可见范围</th>
				    <th width="14%">创建时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
    <div id="ImageModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>
