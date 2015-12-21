<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content detail" id="platformcontent" hostUuid="${hostid}" basePath="${basePath}">
	<div class="intro">
		<h1>服务器&nbsp;Servers</h1>
		<p class="lead" style="margin-top:10px">
			<em>服务器&nbsp;(Server)</em>是云平台中最小的物理单元，是高性能的刀片式服务器，为主机提供运行环境。
		</p>
	</div>
	<ol class="breadcrumb oc-crumb">
		<li><a href="${basePath}host"><span class="glyphicon glyphicon-tasks cool-red"></span><span class="ctext">Servers</span></a></li>
		<li class="active"><a href="${basePath}host/detail">${showId}</a></li>
	</ol>
	<div class="col-md-4">
		<div class="detail-item">
			<div class="title">
				<h3>基本属性
					<div class="btn-group">
						<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							<span class="glyphicon glyphicon-tasks"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a id="modify"><span class="glyphicon glyphicon-pencil"></span>修改</a></li>
						</ul>
					</div>
				</h3>
			</div>
			<dl id="basic-list" class="my-horizontal"></dl>
		</div>
		<div class="detail-item">
			<div class="title">
				<h3>关联资源
					<!-- <div class="btn-group">
						<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							<span class="glyphicon glyphicon-tasks"></span>
						</button>
						<ul class="dropdown-menu">
						</ul>
					</div> -->
				</h3>
			</div>
			<dl id="depend-list" class="my-horizontal"></dl>
		</div>
	</div>
	<div class="col-md-8">
		<div class="detail-item detail-right" style="padding:30px 50px">
			<div class="title">
				<h3 style="padding-left:10px">
					<a style="font-size:18px"><span class="glyphicon glyphicon-stats"></span>&nbsp;监控</a>
					<input type="checkbox" class="oc-switch" data-on-text="开" data-off-text="关" data-on-color="success"/>
					<span class="oc-tip">实时数据</span>
					<div class="btn-group" id="chart-group">
						<button class="btn btn-monitor selected" id="sixhours">6小时</button>
						<button class="btn btn-monitor" id="oneday">一天</button>
						<button class="btn btn-monitor" id="twoweeks">两周</button>
						<button class="btn btn-monitor" id="onemonth">一月</button>
					</div>
				</h3>
			</div>
			<h4 class="chart-title">
				CPU<div class="chart-label">间隔：<span class="chart-span">5 分钟<span></div>
			</h4>
			<div id="chart-area-1">
				<div id="cpupic"></div>
			</div>
			<h4 class="chart-title">
				内存<div class="chart-label">间隔：<span class="chart-span">5 分钟<span></div>
			</h4>
			<div id="chart-area-2">
				<div id="memorypic"></div>
			</div>
			<h4 class="chart-title">
					硬盘
					<div class="chart-label">
						间隔： <span class="chart-span"> 5 分钟 <span>
					</div>
				</h4>
				<div id="chart-area-4">
					<div id="vbdpic"></div>
				</div>
			<h4 class="chart-title">
				网络<div class="chart-label">间隔：<span class="chart-span">5 分钟<span></div>
			</h4>
			<div id="chart-area-3">
				<div id="pifpic"></div>
			</div>
			
		</div>
	</div>
	<div id="InstanceModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>