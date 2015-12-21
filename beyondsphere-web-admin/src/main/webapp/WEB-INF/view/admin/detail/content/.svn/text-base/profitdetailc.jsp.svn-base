<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent" basePath="${basePath}">
	<div class="intro">
		<h1>财务分析&nbsp;Finance Analysis</h1>
		<p class="lead" style="margin-top:10px">财务分析功能为您提供云平台当前的成本核算，收益分析，收益预估等信息，
		为您制定决策提供有价值的参考信息。</p>
	</div>
	<ol class="breadcrumb oc-crumb" style="margin-bottom:0px">
		<li><a href="${basePath}account"><span class="glyphicon glyphicon-tasks cool-red"></span><span class="ctext">Finance</span></a></li>
		<li class="active"><a href="${basePath}profit/detail">${p_username}</a></li>
	</ol>
	<div class="once-pane">
		<div class="alert alert-info change-info">
			通过输入计费开始日期，和终止日期，能够实时查询历史消费记录。
		</div>
		<!--收费清单水表  -->
		<div class="panel panel-info over-panel" id="water_condiv" style="height:430px">
			<div class="panel-heading">
				<b>收费清单水表图</b>
			</div>
			<div class="panel-body" style="height:390px">
				<div class="watercontainer">
					<div class="water_container">
						<div class="water_container" id="waterDiv" style="padding-left:317px;">
							<div class="water">
								<p class="water_title">总价（元）</p>
								<div class="water_number" id="water_number">
								</div>
								<div class="zhuji">
									<p>主机</p>
									<p id="water_instance"></p>
								</div>
								<div class="yingpan">
									<p>硬盘</p>
									<p id="water_volume"></p>
								</div>
								<div class="kuandai">
									<p>备份</p>
									<p id="water_snapshot"></p>
								</div>
								<p class="water_name">${p_username}</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="once-toolbar">
			<button class="btn btn-default" id="flash_profitlist">
				<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
			</button>
			<div class="summary-time">
				<div class="start">
					<label for="start_date" class="col-md-2 control-label statistic-label start-label">开始时间：</label>
		            <div class="input-group date start-time col-md-5" data-date="" data-date-format="yyyy-mm-dd" data-link-field="start_date" data-link-format="yyyy-mm-dd">
		                <input class="form-control start-form" type="text" value="">
		                <span class="input-group-addon"><span class="glyphicon glyphicon-remove start-remove"></span></span>
					    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		            </div>
				    <input type="hidden" id="start_date" value="" /> 
			    </div>
			    <div class="end">
			    	<label for="end_date" class="col-md-2 control-label statistic-label end-label">终止时间：</label>
		            <div class="input-group date end-time col-md-5" data-date="" data-date-format="yyyy-mm-dd" data-link-field="end_date" data-link-format="yyyy-mm-dd">
		                <input class="form-control end-form" type="text" value="">
		                <span class="input-group-addon"><span class="glyphicon glyphicon-remove end-remove"></span></span>
					    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		            </div>
				    <input type="hidden" id="end_date" value="" /> 
			    </div>
		     </div>
		     <button type="button" class="btn btn-primary find" id="searchBun">查询</button>
		</div>
		<table class="table table-bordered once-table" style="margin-top:60px;">
			<thead>
				<tr>
					<th width="15%">资源类型</th>
					<th width="20%">详细信息</th>
					<th width="10%">价格(元)</th>
					<th width="10%">运行状态</th>
					<th width="15%">计费开始时间</th>
					<th width="15%">计费结束时间</th>
					<th width="15%">计费时间</th>
				</tr>
			</thead>
			<tbody id="typetablebody" userid="${p_userid}">
			</tbody>
		</table>
	</div>
</div>
