<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content detail" id="platformcontent"
	platformBasePath="${basePath}" novnc="${vncServer}"
	conUuid="${conUuid}">
	<div class="intro">
		<h1>容器管理&nbsp;Deployment</h1>
		<p class="lead">
			通过容器管理，可以接管docker平台，能够方便的把应用部署在容器上，并通过端口映射对外界提供web服务。
		</p>
	</div>
	<div class="row" style="margin: 0; border-bottom: 1px solid #f3f3f3">
		<div class="col-md-4">
			<ol class="breadcrumb oc-crumb img-crumb" style="border-bottom: none">
				<li>
					<a href="${basePath}container"> 
						<span class="glyphicon glyphicon-record cool-blue"> </span> 
						<span class="ctext"> CONTAINER </span>
					</a>
				</li>
				<li class="active">
					<a href="${basePath}container/detail">${showId} </a>
				</li>
			</ol>
		</div>
		<!-- <div class="col-md-8">
			<div class="view-types">
				<span class="title"> 查看方式： </span> 
					<a class="view-type type-text current" href="#" oc-type="text"> 
						<span class="glyphicon glyphicon-text-width"> </span> 文字
					</a> 
					<a class="view-type type-graph" href="#" oc-type="graph"> 
						<span class="glyphicon glyphicon-picture"> </span> 图形
					</a>
			</div>
		</div> -->
	</div>
	<div id='textview'>
		<div class="col-md-4">
			<div class="detail-item">
				<div class="title">
					<h3>
						基本属性&nbsp; <a href="javascript:void(0)" class="btn-refresh"> <span
							class="glyphicon glyphicon-refresh"> </span>
						</a>
						<div class="btn-group">
							<button class="btn btn-default dropdown-toggle"
								data-toggle="dropdown">
								<span class="glyphicon glyphicon-tasks"> </span>
							</button>
						</div>
					</h3>
				</div>
				<dl id="basic-list" class="my-horizontal">
				</dl>
			</div>
		</div>
		<div class="col-md-8">
			<div class="detail-item detail-right" style="padding: 30px 50px">
				<div class="title">
					<h3 style="padding-left: 10px">
						<a style="font-size: 18px"> <span
							class="glyphicon glyphicon-stats"> </span> &nbsp;监控
						</a> <!-- <input type="checkbox" class="oc-switch" data-on-text="开"
							data-off-text="关" data-on-color="success" /> --> <span
							class="oc-tip"> 实时数据 </span>
						<div class="btn-group" id="chart-group">
							<button class="btn btn-monitor selected" id="sixhours">6小时</button>
							<button class="btn btn-monitor" id="oneday">一天</button>
							<button class="btn btn-monitor" id="twoweeks">两周</button>
							<button class="btn btn-monitor" id="onemonth">一月</button>
						</div>
					</h3>
				</div>
				<h4 class="chart-title">
					CPU
					<div class="chart-label">
						间隔： <span class="chart-span"> 5 分钟 <span>
					</div>
				</h4>
				<div id="chart-area-1">
					<div id="cpupic"></div>
				</div>
				<h4 class="chart-title">
					内存
					<div class="chart-label">
						间隔： <span class="chart-span"> 5 分钟 <span>
					</div>
				</h4>
				<div id="chart-area-2">
					<div id="memorypic"></div>
				</div>
				<!-- <h4 class="chart-title">
					硬盘
					<div class="chart-label">
						间隔： <span class="chart-span"> 5 分钟 <span>
					</div>
				</h4>
				<div id="chart-area-3">
					<div id="vbdpic"></div>
				</div>
				<h4 class="chart-title">
					网络
					<div class="chart-label">
						间隔： <span class="chart-span"> 5 分钟 <span>
					</div>
				</h4>
				<div id="chart-area-4">
					<div id="vifpic"></div>
				</div> -->
			</div>
		</div>
	</div>
	<div id="InstanceModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>
