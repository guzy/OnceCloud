<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent" basePath="${basePath}">
	<div class="intro">
		<h1>计费管理&nbsp;Accounting</h1>
		<p class="lead" style="margin-top:10px">云计算平台为您提供了可以随时查看已经创建的主机、硬盘、备份的的使用状态，并且计算出已经使用的时间。
		统计（Statiatics）实现了上述功能，并且允许查看某个时间段内资源的使用情况。</p>
	</div>
	<div class="once-pane">
		<div class="once-toolbar">
			<div class="alert alert-info" id="once-tab-title">
			</div>
		    <div style="float: left;">
		    <button class="btn btn-default btn-refresh">
				<span class="glyphicon glyphicon-refresh" style="margin-right:0"></span>
			</button>
			
			<input class="search" id="search" value="">
			</div>
			<div style="float: left;margin-left: 50px" id="radio_div">
			<input type="radio" name="optionsRadios" id="optionsRadios1" value="1" checked="checked" onclick="reloadList(1)">1天
			<input type="radio" name="optionsRadios" id="optionsRadios2" value="2" style="margin-left: 20px" onclick="reloadList(1)">1月
			<input type="radio" name="optionsRadios" id="optionsRadios3" value="3" style="margin-left: 20px" onclick="reloadList(1)">1季度
			<input type="radio" name="optionsRadios" id="optionsRadios4" value="4" style="margin-left: 20px" onclick="reloadList(1)">1年</div>
		</div>
		<div class="once-toolbar">
			<div class="toolbar-right">
				<table>
					<tr>
						<td>每页&nbsp;</td>
						<td><input id="limit" name="limit" class="page" value="10"></td>
						<td>&nbsp;个&nbsp;页数&nbsp;<a id="currentP"></a>&nbsp;/&nbsp;<a id="totalP"></a></td>
						<td style="padding-left:10px"><div><ul id="pageDivider" style="display:inline"></ul></div></td>
					</tr>
				</table>
			</div>
		</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th width="15%">ID</th>
					<th width="20%">名称</th>
					<th width="15%">主机</th>
					<th width="15%">硬盘</th>
					<th width="15%">备份</th>
					<th width="20%">总计</th> 
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="StatisticsModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>