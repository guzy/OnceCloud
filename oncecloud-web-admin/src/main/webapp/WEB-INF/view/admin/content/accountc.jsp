<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent" basePath="${basePath}">
	<div class="intro">
		<h1>财务分析&nbsp;Finance Analysis</h1>
		<p class="lead" style="margin-top: 10px">财务分析功能为您提供云平台当前的成本核算，收益分析，收益预估等信息，
			为您制定决策提供有价值的参考信息。</p>
	</div>
	<ul class="nav nav-tabs once-tab">
		<li class="tab-filter active" type="finance"><a
			href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>成本分析</a>
		</li>
		<li class="tab-filter" type="overview"><a
			href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>收费清单</a>
		</li>
		<!-- <li class="tab-filter" type="forecast"><a
			href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>收费预测</a>
		</li> -->
		<li class="tab-filter" type="costaccount"><a
			href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>成本核算</a>
		</li>
		<li class="tab-filter" type="costmanager"><a
			href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>成本配置</a>
		</li>
		<li class="tab-filter" type="typecost">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>成本均摊</a>
		</li>
	</ul>
	<div class="once-pane" id="finance">
		<div class="once-toolbar">
			<div class="alert alert-info">
				成本分析，用来分析平台内当前的成本投入，以及平台当前的收益情况以及趋势变化。</div>
			<!-- <button class="btn btn-default btn-refresh" id="stylelist_flash">
				<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
			</button> -->
			<div class="panel panel-info over-panel">
				<div class="panel-heading">
					<b>2015年度成本分析</b>
				</div>
				<div class="panel-body">
					<div style="margin-left: -12px; padding-left: 0px;" id="profitPie"
						data-highcharts-chart="3"></div>
				</div>
			</div>
			<div class="panel panel-info over-panel">
				<div class="panel-heading">
					<b>硬件资源收益分析</b>
				</div>
				<div class="panel-body">
					<div style="margin-left: -12px; padding-left: 0px;"
						id="profitChart" data-highcharts-chart="3"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="once-pane" id="cost_type">
		<div class="once-toolbar">
			<div class="alert alert-info">
				成本配置，用来维护云平台的成本信息，包括硬件成本和软件成本。可以添加，修改和删除成本信息。</div>
			<button class="btn btn-default btn-refresh" id="stylelist_flash">
				<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
			</button>

			<button id="create" class="btn btn-primary">+&nbsp;添加成本</button>
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle"
					data-toggle="dropdown">
					更多操作... <span class="caret" style="margin-left: 15px"></span>
				</button>
				<ul class="dropdown-menu" id="dropdown-menu">
					<li><a id="typemodify"><span
							class="glyphicon glyphicon-trash"></span>修改</a></li>
					<li><a id="typedelete"><span
							class="glyphicon glyphicon-trash"></span>删除</a></li>
				</ul>
			</div>
			<input class="search" id="type_search" value="">
		</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th id="select" class="select" width="4%">全选</th>
					<!-- <th width="15%">ID</th> -->
					<th width="45%">分类</th>
					<th width="51%">分类描述</th>
				</tr>
			</thead>
			<tbody id="typetablebody">
			</tbody>
		</table>
	</div>
	<div class="once-pane" id="cost_detail">
		<div class="once-toolbar">
			<div class="alert alert-info">
				成本核算，用来查看云平台的成本汇总信息，按照软件和硬件分类统计分类中的所有花费，并通过ID连接给出每个大类下的详细成本信息。</div>
			<button class="btn btn-default btn-refresh" id="flash_allPriceList">
				<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
			</button>
			<input class="search" id="detail_search" value="">
		</div>
		<div class="panel panel-info over-panel" >
				<div class="panel-heading">
					<b>年度成本分析</b>
				</div>
				<div class="panel-body">
					<div style="margin-left:-12px; padding-left:0px;width:1093px"
						id="cost_yearChart" data-highcharts-chart="3" ></div>
				</div>
			</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<!-- <th width="15%">ID</th> -->
					<th width="25%">分类</th>
					<th width="35%">今年投入金额&nbsp;(元)</th>
					<th width="40%">总投入金额&nbsp;(元)</th>
				</tr>
			</thead>
			<tbody id="costtablebody">
			</tbody>
		</table>
	</div>
	<div class="once-pane" id="typecost">
		<div class="once-toolbar">
			<div class="alert alert-info">
				成本均摊，展示成本收费列表。计价单位按照“月”收费，产品购买至少需要一月以上，使用不满一月，按照一月收费。
			</div>
		    <div style="float: left;">
			    <button class="btn btn-default btn-refresh" id="flash_allPriceList">
					<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
				</button>
			</div>
			<div style="float: left;margin-left: 50px; margin-top:5px" id="typecost_div">
				<input type="radio" name="typecostRadios" id="typecostRadios1" value="1" checked="checked" onclick="costByType();">投入成本记录
				<input type="radio" name="typecostRadios" id="typecostRadios2" value="2" style="margin-left: 20px" onclick="costByType();">成本收费记录
			</div>
		</div>
		<table class="table table-bordered once-table" id="costtypetablebody" style="margin-top: 60px">
			<thead>
				<tr>
					<th width="25%">计费项</th>
					<th width="25%">所属分类</th>
					<th width="20%"> 投入金额(元/月)</th>
					<th width="30%">统计时间</th>
				</tr>
			</thead>
			<tbody id="detailCost">
				<!-- <tr>
					<td rowspan="14" style="text-align: center; vertical-align: middle">主机</td>
					<td rowspan="3" style="text-align: center; vertical-align: middle">一核</td>
					<td>512M</td>
					<td>元/年</td>
					<td>384</td>
				</tr>
				<tr>
					<td>1GB</td>
					<td>元/年</td>
					<td>540</td>
				</tr>
				<tr>
					<td>2GB</td>
					<td>元/年</td>
					<td>1020</td>
				</tr>
				
				<tr>
					<td rowspan="3" style="text-align: center; vertical-align: middle">两核</td>
					<td>2GB</td>
					<td>元/年</td>
					<td>1776</td>
				</tr>
				<tr>
					<td>4GB</td>
					<td>元/年</td>
					<td>2376</td>
				</tr>
				<tr>
					<td>8GB</td>
					<td>元/年</td>
					<td>3576</td>
				</tr>
				
				<tr>
					<td rowspan="3" style="text-align: center; vertical-align: middle">四核</td>
					<td>4G</td>
					<td>元/年</td>
					<td>3552</td>
				</tr>
				<tr>
					<td>8GB</td>
					<td>元/年</td>
					<td>4752</td>
				</tr>
				<tr>
					<td>16GB</td>
					<td>元/年</td>
					<td>7512</td>
				</tr>
				
				<tr>
					<td rowspan="3" style="text-align: center; vertical-align: middle">八核</td>
					<td>8GB</td>
					<td>元/年</td>
					<td>7104</td>
				</tr>
				<tr>
					<td>16GB</td>
					<td>元/年</td>
					<td>9504</td>
				</tr>
				<tr>
					<td>32GB</td>
					<td>元/年</td>
					<td>14304</td>
				</tr>
				<tr>
					<td rowspan="2" style="text-align: center; vertical-align: middle">十六核</td>
					<td>16GB</td>
					<td>元/年</td>
					<td>14208</td>
				</tr>
				<tr>
					<td>32GB</td>
					<td>元/年</td>
					<td>19008</td>
				</tr>
				<tr>
					<td style="text-align: center;">数据盘</td>
					<td style="text-align: center;">线性计费</td>
					<td>1GB</td>
					<td>元/GB/年</td>
					<td>3.6</td>
				</tr>
				<tr>
					<td style="text-align: center;">SSD硬盘</td>
					<td style="text-align: center;">线性计费</td>
					<td>1GB</td>
					<td>元/GB/年</td>
					<td>19.2</td>
				</tr>
				<tr>
					<td rowspan="6" style="text-align: center; vertical-align: middle">公网带宽</td>
					<td rowspan="6" style="text-align: center; vertical-align: middle">按固定带宽阶梯计费（6Mbps及以上带宽月价算法为125+(n-5)*80,其中n表示带宽值）</td>
					<td>1Mbps</td>
					<td>元/1Mbps/年</td>
					<td>276</td>
				</tr>
				<tr>
					<td>2Mbps</td>
					<td>元/2Mbps/年</td>
					<td>552</td>
				</tr>
				<tr>
					<td>3Mbps</td>
					<td>元/3Mbps/年</td>
					<td>852</td>
				</tr>
				<tr>
					<td>4Mbps</td>
					<td>元/4Mbps/年</td>
					<td>1152</td>
				</tr>
				<tr>
					<td>5Mbps</td>
					<td>元/5Mbps/年</td>
					<td>1500</td>
				</tr>
				<tr>
					<td>6Mbps及以上</td>
					<td>元/6Mbps/年</td>
					<td>960</td>
				</tr> -->
			</tbody>
		</table>
		<div class="alert alert-info" id="profit_info" style="display:none;margin-top:50px;margin-bottom:0px">每月用户分摊总成本=每月实际投入总成本×每月数据中心cpu使用率×1.5,每个用户分摊的成本=cpu分摊成本+内存分摊成本+硬盘分摊成本<br>
		（用户每个月的分摊费用都会分摊到cpu、内存、硬盘上，cpu、内存、硬盘投入比为3：5：2 ,且根据用户cpu、内存、硬盘的资源使用率计算得出）</div>
		<table class="table table-bordered once-table" id="cost" style="visibility: hidden; margin-top:10px;">
			<thead>
				<tr>
					<th width="30%">企业名称</th>
					<th width="25%">总计&nbsp;(元)</th> 
					<th width="25%">统计的月份</th> 
				</tr>
			</thead>
			<tbody id="profitDetail">
			</tbody>
		</table>
	</div>
	
	<div class="once-pane" id = "profit">
		<div class="once-toolbar">
			<div class="alert alert-info profit-info" id="once-tab-title"
				style="margin: 0px 0px 10px 0px; padding: 7px 20px 7px 20px">
			</div>
			<div style="float: left;">
				<button class="btn btn-default btn-refresh">
					<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
				</button>

				<input class="search" id="search" value="">
			</div>
			<div style="float: left;margin-left: 50px" id="radio_div">
				<input type="radio" name="optionsRadios" id="optionsRadios1" value="1" checked="checked" onclick="reloadList(1)">1天
				<input type="radio" name="optionsRadios" id="optionsRadios2" value="2" style="margin-left: 20px" onclick="reloadList(1)">1月
				<input type="radio" name="optionsRadios" id="optionsRadios3" value="3" style="margin-left: 20px" onclick="reloadList(1)">1季度
				<input type="radio" name="optionsRadios" id="optionsRadios4" value="4" style="margin-left: 20px" onclick="reloadList(1)">1年
			</div>
		</div>
		<!--收费清单水表  -->
		<div class="panel panel-info over-panel" id="water_condiv" style="height:430px">
			<div class="panel-heading">
				<b>收费清单水表图</b>
			</div>
			<div class="panel-body" style="overflow-y:scroll;height:390px">
				<div class="watercontainer">
					<div class="water_container">
						<div class="water_container" id="waterDiv">
							<!-- <div class="water">
								<p class="water_title">总价（元）</p>
								<div class="water_number">
									<p>8</p>
									<p>8</p>
									<p>8</p>
									<p>8</p>
									<p>8</p>
									<p>8</p>
								</div>
								<div class="zhuji">
									<p>主机</p>
									<p>22.00</p>
								</div>
								<div class="yingpan">
									<p>硬盘</p>
									<p>200.00</p>
								</div>
								<div class="kuandai">
									<p>宽带</p>
									<p>50.00</p>
								</div>
								<p class="water_name">苏州工业园区租房管理公司</p>
							</div> -->
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="once-toolbar">
			<div class="toolbar-right">
				<table>
					<tr>
						<td>每页&nbsp;</td>
						<td><input id="limit" name="limit" class="page" value="10"></td>
						<td>&nbsp;个&nbsp;页数&nbsp;<a id="currentP"></a>&nbsp;/&nbsp;<a
							id="totalP"></a></td>
						<td style="padding-left: 10px"><div>
								<ul id="pageDivider" style="display: inline"></ul>
							</div></td>
					</tr>
				</table>
			</div>
		</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th width="20%">企业名称</th>
					<th width="15%">cpu&nbsp;(元)</th>
					<th width="15%">内存&nbsp;(元)</th>
					<th width="15%">硬盘&nbsp;(元)</th>
					<th width="15%">总计&nbsp;(元)</th>
					<th width="20%">统计月份</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="CostModalContainer" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>