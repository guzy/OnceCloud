<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent" basePath="${basePath}">
	<div class="intro">
		<h1>概览&nbsp;Overview</h1>
		<p class="lead" style="margin-top:10px">概览展示了管理平台承载的企业个数，虚拟机个数，收益情况，以及近期变化趋势等内容。</p>
	</div>
	<ul class="nav nav-tabs once-tab">
		<li class="tab-filter active" type="general">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-fullscreen"></span>概览</a>
		</li>
		<li class="tab-filter" type="distributed">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>资源消耗</a>
		</li>
	</ul>
	<div class="once-pane" id="tab1_div_1">
		<div class="panel panel-info view-panel">
		   <div class="panel-heading">
		     <b>注册企业</b> 
		   </div>
		   <div class="panel-body" id="enterprises">
		   </div>
		</div>
		<div class="panel panel-info view-panel">
		   <div class="panel-heading">
		      <b>虚拟主机</b>
		   </div>
		   <div class="panel-body" id="VMs">
		   </div>
		</div>
		<div class="panel panel-info view-panel">
		   <div class="panel-heading">
		      <b>集群</b>
		   </div>
		   <div class="panel-body" id="colony">
		   </div>
		</div>
		<div class="panel panel-info view-panel">
		   <div class="panel-heading">
		      <b>主机</b>
		   </div>
		   <div class="panel-body" id="pcs">
		   </div>
		</div>
		<div class="panel panel-info view-panel">
		   <div class="panel-heading">
		      <b>存储</b>
		   </div>
		   <div class="panel-body" id="storage">
		   </div>
		</div>
		<div class="panel panel-info view-panel">
		   <div class="panel-heading">
		      <b>模板</b>
		   </div>
		   <div class="panel-body" id="images">
		   </div>
		</div>
	</div>
	<div class="once-pane" id="tab1_div_2">
		<div class="panel panel-info over-panel">
			<div class="panel-heading">
				<b>注册企业</b>
				<span class="more"><a href="${basePath}user">查看全部</a></span>
			</div>
			<div class="panel-body">
			   	<table class="table">
				   <thead>
				      <tr>
				         <th width="70%">企业名称</th>
				         <th width="30%">注册时间</th>
				      </tr>
				   </thead>
				   <tbody id="enterprisesList"></tbody>
				</table>
			</div>
		</div>
		<div class="panel panel-info over-panel">
			<div class="panel-heading">
				<b>创建虚拟机(Top 5)</b>
				<span class="more"><a href="${basePath}overview/companydetail">查看全部</a></span>
			</div>
			<div class="panel-body">
			   	<table class="table">
				   <thead>
				      <tr>
				         <th width="70%">企业名称</th>
				         <th width="30%">虚拟机总数</th>
				      </tr>
				   </thead>
				   <tbody id="VMList"></tbody>
				</table>
			</div>
		</div>
		<div class="panel panel-info over-panel">
			<div class="panel-heading">
				<b>风险评估</b>
			</div>
			<div class="panel-body">
			   	<div id="container" style="height:245px;"></div>
			</div>
		</div>
	</div>
	<div class="once-pane" id="tab1_div_3">
		<div class="panel panel-info over-panel">
			<div class="panel-heading">
				<b>企业变化</b>
			</div>
			<div class="panel-body">
			   	<div id="companycontainer" style="height:245px;margin-left:-12px; padding-left:0px;"></div>
			</div>
		</div>
		<div class="panel panel-info over-panel">
			<div class="panel-heading">
				<b>虚拟机变化</b>
			</div>
			<div class="panel-body">
			   	<div id="vmcontainer" style="height:245px;margin-left:-12px; padding-left:0px;"></div>
			</div>
		</div>
		<div class="panel panel-info over-panel">
			<div class="panel-heading">
				<b>收益变化</b>
				<span class="more"><a href="${basePath}account">查看全部</a></span>
			</div>
			<div class="panel-body">
			   	<div id="profitcontainer" style="height:245px;margin-left:-12px; padding-left:0px;"></div>
			</div>
		</div>
	</div>
	
	<div class="once-pane" id="tab2_div_1">
		
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th width="20%">名称</th>
					<th width="20%">总数</th>
					<th width="20%">消耗</th>
					<th width="20%">剩余</th>
					<th width="20%">消耗比例</th> 
				</tr>
			</thead>
			<tbody id="sourceUsedList">
			</tbody>
		</table>
	</div>
	<div class="once-pane" id="tab2_div_2">
		<div class="panel panel-default">
			<input type="hidden" id="totalCpu">
			<input type="hidden" id="totalMem">
			<div class="panel-heading">模拟计算</div>
		   <table class="table">
			   <tr>
			      <td align="right">CPU&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</td>
			      <td><input type="radio" name="cpuRadios" id="cpuRadios1" value="1" onclick="calculateFun()">&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;核&nbsp;&nbsp;&nbsp;
					<input type="radio" name="cpuRadios" id="cpuRadios2" value="2" style="margin-left: 20px" onclick="calculateFun()" checked="checked">&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;核&nbsp;&nbsp;&nbsp;
					<input type="radio" name="cpuRadios" id="cpuRadios3" value="4" style="margin-left: 20px" onclick="calculateFun()">&nbsp;&nbsp;&nbsp;4&nbsp;&nbsp;核&nbsp;&nbsp;&nbsp;
				</td>
			    </tr>
			     <tr>
			      <td align="right">内存&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</td>
			      <td><input type="radio" name="memRadios" id="memRadios1" value="1" onclick="calculateFun()">&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;G&nbsp;&nbsp;&nbsp;
					<input type="radio" name="memRadios" id="memRadios2" value="2" style="margin-left: 20px" onclick="calculateFun()" >&nbsp;&nbsp;&nbsp;2&nbsp;&nbsp;G&nbsp;&nbsp;&nbsp;
					<input type="radio" name="memRadios" id="memRadios3" value="4" style="margin-left: 20px" onclick="calculateFun()" checked="checked">&nbsp;&nbsp;&nbsp;4&nbsp;&nbsp;G&nbsp;&nbsp;&nbsp;
					<input type="radio" name="memRadios" id="memRadios4" value="8" style="margin-left: 20px" onclick="calculateFun()" >&nbsp;&nbsp;&nbsp;8&nbsp;&nbsp;G&nbsp;&nbsp;&nbsp;
					<input type="radio" name="memRadios" id="memRadios5" value="16" style="margin-left: 20px" onclick="calculateFun()">&nbsp;&nbsp;&nbsp;16&nbsp;&nbsp;G&nbsp;&nbsp;&nbsp;
				</td>
			    </tr>
			    <tr>
			      <td align="right">可创建主机&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</td>
			      <td><div class="input-group" style="width: 200px">
			         <input type="text" class="form-control" readonly="readonly" id="createVM">
			         <span class="input-group-addon">台</span>
			      </div></td>
			    </tr>
			</table>
		   
		</div>
			
	</div>
</div>