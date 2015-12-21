<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent" basePath="${basePath}">
	<div class="intro">
		<h1>财务分析&nbsp;Finance Analysis</h1>
		<p class="lead" style="margin-top:10px">财务分析功能为您提供云平台当前的成本核算，收益分析，收益预估等信息，
		为您制定决策提供有价值的参考信息。</p>
	</div>
	<ol class="breadcrumb oc-crumb" style="margin-bottom:0px">
		<li><a href="${basePath}account"><span class="glyphicon glyphicon-tasks cool-red"></span><span class="ctext">Finance</span></a></li>
		<li class="active"><a href="${basePath}costtype/detail">cost-${costtypeid}</a></li>
	</ol>
	<div class="once-pane" id="cost_type">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh" id="flash_typeDetailList">
				<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
			</button>
			<button id="typedetailmodify" class="btn btn-primary">
				修改
			</button>
			<button id="typedetaildelete" class="btn btn-primary">
				删除
			</button>
			<input class="search" id="type_search" value="">
		</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th id="select" class="select" width="4%">全选</th>
					<!-- <th width="15%">ID</th> -->
					<th width="20%">分类</th>
					<th width="25%">分类划分</th>
					<th width="51%">分类描述</th>
				</tr>
			</thead>
			<tbody id="typetablebody" typeid="${costtypeid}">
			</tbody>
		</table>
	</div>
	
	<div id="CostDetailModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>