<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent" basePath="${basePath}">
	<div class="intro">
		<h1>财务分析&nbsp;Finance Analysis</h1>
		<p class="lead" style="margin-top:10px">财务分析功能为您提供云平台当前的成本核算，收益分析，收益预估等信息，
		为您制定决策提供有价值的参考信息。</p>
	</div>
	<ol class="breadcrumb oc-crumb" style="margin-bottom:0px">
		<li><a href="${basePath}account"><span class="glyphicon glyphicon-tasks cool-red"></span><span class="ctext">Finance</span></a></li>
		<li class="active"><a href="${basePath}cost/detail">Type-${costid}</a></li>
	</ol>
	<div class="once-pane" id="cost_type">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh" id="flash_detaillist">
				<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
			</button>
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle"
					data-toggle="dropdown">
					更多操作... <span class="caret" style="margin-left: 15px"></span>
				</button>
				<ul class="dropdown-menu" id="dropdown-menu">
					 <li><a id="detailmodify"><span
							class="glyphicon glyphicon-trash"></span>修改</a></li> 
					<!-- <li><a id="detaildelete"><span
							class="glyphicon glyphicon-trash"></span>删除</a></li> -->
				</ul>
			</div>
			<input class="search" id="search" value="">
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
					<th id="select" class="select" width="4%">全选</th>
					<!-- <th width="8%">ID</th> -->
					<th width="10%">分类划分</th>
					<th width="14%">名称</th>
					<th width="10%">类型</th>
					<th width="9%">单价（元）</th>
					<th width="13%">计费方式</th>
					<th width="10%">资源个数</th>
					<th width="15%">开始时间</th>
					<th width="15%">结束时间</th>
				</tr>
			</thead>
			<tbody id="typetablebody" typeid="${costid}">
			</tbody>
		</table>
	</div>
	
	<div id="DetailModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>