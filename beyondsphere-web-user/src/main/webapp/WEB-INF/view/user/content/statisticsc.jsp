<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent" basePath="${basePath}">
	<div class="intro">
		<h1>统计&nbsp;Statistics</h1>
		<p class="lead" style="margin-top:10px">云计算平台为您提供了可以随时查看已经创建的主机、硬盘、备份的的使用状态，并且计算出已经使用的时间。
		统计（Statiatics）实现了上述功能，并且允许查看某个时间段内资源的使用情况。</p>
	</div>
	<div class="once-pane">
		<div class="once-toolbar">
		    <button class="btn btn-default btn-refresh">
				<span class="glyphicon glyphicon-refresh" style="margin-right:0"></span>
			</button>
			
			<div class="item">
				<div class="control-label">
					<span class="icon-hand-right">统计项：</span>
				</div>
				<div class="controls">
					<div class="select-con">
						<select class="dropdown-select" id="select_name" name="select_name">
							<option value="instance">主机</option>
							<option value="volume">硬盘</option>
							<option value="snapshot">备份</option>
						</select>
					</div>
				</div>
			</div>
			
			 <div class="summary-time">
				<div class="start">
					<label for="start_date" class="col-md-2 control-label start-label">开始时间：</label>
		            <div class="input-group date start-time col-md-5" data-date="" data-date-format="yyyy-mm-dd" data-link-field="start_date" data-link-format="yyyy-mm-dd">
		                <input class="form-control start-form" type="text" value="">
		                <span class="input-group-addon"><span class="glyphicon glyphicon-remove start-remove"></span></span>
					    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		            </div>
				    <input type="hidden" id="start_date" value="" /> 
			    </div>
			    <div class="end">
			    	<label for="end_date" class="col-md-2 control-label end-label">终止时间：</label>
		            <div class="input-group date end-time col-md-5" data-date="" data-date-format="yyyy-mm-dd" data-link-field="end_date" data-link-format="yyyy-mm-dd">
		                <input class="form-control end-form" type="text" value="">
		                <span class="input-group-addon"><span class="glyphicon glyphicon-remove end-remove"></span></span>
					    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		            </div>
				    <input type="hidden" id="end_date" value="" /> 
			    </div>
		     </div>
		     <button type="button" class="btn btn-primary find">查询</button>
			
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
					<th width="15%">名称</th>
					<th width="15%">状态</th>
					<th width="15%">总价</th>
					<th width="30%">创建时间</th>
					<th width="40%">使用时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="StatisticsModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>