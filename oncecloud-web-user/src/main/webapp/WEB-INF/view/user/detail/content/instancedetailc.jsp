<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content detail" id="platformcontent"
	platformBasePath="${basePath}" novnc="${vncServer}"
	instanceUuid="${instanceUuid}">
	<div class="intro">
		<h1>主机&nbsp;Instance</h1>
		<p class="lead">
			云平台为您提供一种随时获取的、弹性的计算能力，这种计算能力的体现就是<em>主机&nbsp;(Instance)</em>。主机就是一台配置好了的服务器，它有您期望的硬件配置、操作系统和网络配置。通常情况下，您的请求可以在10秒到60秒的时间之内完成，所以您完全可以动态地、按需使用计算能力。
		</p>
	</div>
	<div class="row" style="margin: 0; border-bottom: 1px solid #f3f3f3">
		<div class="col-md-4">
			<ol class="breadcrumb oc-crumb img-crumb" style="border-bottom: none">
				<li>
					<a href="${basePath}instance"> 
						<span class="glyphicon glyphicon-cloud cool-orange"> </span> 
						<span class="ctext"> INSTANCE </span>
					</a>
				</li>
				<li class="active">
					<a href="${basePath}instance/detail">${showId} </a>
				</li>
			</ol>
		</div>
		<div class="col-md-8">
			<div class="view-types">
				<span class="title"> 查看方式： </span> 
					<a class="view-type type-text current" href="#" oc-type="text"> 
						<span class="glyphicon glyphicon-text-width"> </span> 文字
					</a> 
					<a class="view-type type-graph" href="#" oc-type="graph"> 
						<span class="glyphicon glyphicon-picture"> </span> 图形
					</a>
			</div>
		</div>
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
							<ul class="dropdown-menu">
								<li><a id="modify"> <span
										class="glyphicon glyphicon-pencil"> </span> 修改
								</a></li>
							</ul>
						</div>
					</h3>
				</div>
				<dl id="basic-list" class="my-horizontal">
				</dl>
			</div>
			<div class="detail-item">
				<div class="title">
					<h3>
						关联资源&nbsp; <a href="javascript:void(0)" class="btn-refresh"> <span
							class="glyphicon glyphicon-refresh"> </span>
						</a>
					</h3>
				</div>
				<dl id="depend-list" class="my-horizontal">
				</dl>
			</div>
		</div>
		<div class="col-md-8">
			<div class="detail-item detail-right" style="padding: 30px 50px">
				<div class="title">
					<h3 style="padding-left: 10px">
						<a style="font-size: 18px"> <span
							class="glyphicon glyphicon-stats"> </span> &nbsp;监控
						</a> <input type="checkbox" class="oc-switch" data-on-text="开"
							data-off-text="关" data-on-color="success" /> <span
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
				<h4 class="chart-title">
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
				</div>
			</div>
		</div>
	</div>
	<div id="imageview" style="display: none">
		<input type="hidden" id="instancestate" value=0 /> <input
			type="hidden" id="vnUuid" value='' /> <input type="hidden"
			id="vnrouterUuid" value='' /> <input type="hidden" id="instanceid"
			value="${showId}" />
		<div class="col-md-8">
			<div class="graph-wrapper">
				<h2 class="graph-title">Instance: ${uuid} 结构示意图</h2>
				<div class="col-md-2 actions">
					<div class="graph-actions">
						<a id="uistart" class="btn btn-forbidden" href="#"> <span
							class="glyphicon glyphicon-play"> </span> <span class="text">
								启动 </span>
						</a> <a id="uiclose" class="btn" href="#"> <span
							class="glyphicon glyphicon-stop"> </span> <span class="text">
								关机 </span>
						</a> <a id="uirestart" class="btn" href="#"> <span
							class="glyphicon glyphicon-refresh"> </span> <span class="text">
								重启 </span>
						</a>
						<!-- <a class="btn" href="#">
	        <span class="glyphicon glyphicon-pencil"></span><span class="text">修改</span></a>
	        <a class="btn btn-forbidden" href="#">
	        <span class="glyphicon glyphicon-cog"></span>
	        <span class="text">更改配置</span></a> -->
						<a id="createimage" class="btn" href="#"
							url="${basePath}image/clone"> <span
							class="glyphicon glyphicon-record"> </span> <span class="text">
								制作映像 </span>
						</a> <a id="backup" class="btn" href="#"
							url="${basePath}snapshot/create"> <span
							class="glyphicon glyphicon-camera"> </span> <span class="text">
								备份 </span>
						</a>
						<!--    <a class="btn" href="#">
	        <span class="glyphicon glyphicon-repeat"></span><span class="text">重置</span></a> -->
						<a id="destroy" class="btn btn-danger" href="#"> <span
							class="glyphicon glyphicon-trash"> </span> <span class="text">
								销毁 </span>
						</a>
						<!--  <a class="btn btn-forbidden" href="#">
	        <span class="glyphicon glyphicon-repeat"></span><span class="text">恢复</span></a> -->
					</div>
				</div>
				<div class="col-md-10 components" id="componentsId">

					<!-- 路由网络 开始 -->
					<div class="graph-component component-instance-network"
						id="instancerouterDiv">
						<div class="graph-component component-instance-router"
							id="componentInstanceRouterDiv">
							<div class="graph-component component-instance-router-network">
								<div class="graph-component component-router-cloud"></div>
								<div class="graph-component component-router-vxnet0"></div>
							</div> 
							<div class="graph-component component-router" id="componentRouterDiv">
								<span class="private-ip"> </span> 
								<a class="router-id" id="btnshowswitch" data-permalink=""> </a>
							</div>
						</div>
						<!-- <div class="graph-component component-managed-vxnet">
							<a class="btn-show-router vxnet-name" href="#" > </a> 
							<a id="vxnetName" class="vxnet-name" href="#" data-permalink="">11 </a> 
							<span class="private-ip"> </span>
						</div> -->
					</div>

					<!-- 路由网络 结束 -->
					<!-- 公网IP开始 -->
					<!-- <div class="graph-component component-instance-network"
						id="instancenetworkDiv" style="display: none">
						<div class="graph-component component-instance-cloud"></div>
						<a id="bingpublic" class="btn" href="#"> <span
							class="glyphicon glyphicon-globe"> </span> <span class="text">
								绑定公网IP </span>
						</a>
						<div class="graph-component component-instance-eip"
							style="display: none" id="unbindpublic">
							<a class="btn-delete" href="#" id="deleteeip"> <span
								class="glyphicon glyphicon-remove"> </span>
							</a> <span class="glyphicon glyphicon-globe"> </span> <a
								class="component-id publicipname" data-permalink="">
								117.121.10.150 </a>
						</div>
						<div class="graph-component component-vxnet0">
							<span class="vxnet-name"> 基础网络 </span>
						</div>
					</div> -->
					<!-- 公网IP结束 -->
					<!-- 防火墙开始 -->
					<!-- <div class="graph-component component-instance-sg"
						id="instancesgDiv" style="display: none">
						<a class="sg-name" data-permalink="" id="firewallId"> 缺省防火墙 </a> <a
							class="btn" href="#" id="bingfirewall"> <span
							class="glyphicon glyphicon-pencil"> </span> <span class="text">
								加载防火墙规则 </span>
						</a>
					</div> -->
					<!-- 防火墙结束 -->
					<!-- 主机开始 -->
					<div class="graph-component component-instance connected"
						id="instanceID">
						<a class="btn-delete" href="#" data-id="vxnet-0"
							id="instanceNetID"> <span class="glyphicon glyphicon-pencil">
						</span>
						</a> <span class="private-ip"> </span> <span class="instance-name">
						</span>
					</div>
					<!-- 主机结束 -->
					<!-- 硬盘开始 -->
					<div class="graph-component component-instance-volumes">
						<div class="col-md-12 tree" id="instance-volumeDiv">
							<div class="graph-resource-snapshots" style="width: 0px;"></div>
						</div>
					</div>
					<!-- 硬盘结束 -->
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="detail-item">
				<div class="title">
					<h3>
						基本属性&nbsp; <a href="javascript:void(0)" class="btn-refresh"> <span
							class="glyphicon glyphicon-refresh"> </span>
						</a>
						<div class="btn-group">
							<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
								<span class="glyphicon glyphicon-tasks"></span>
							</button>
							<ul class="dropdown-menu">
								<li><a id="picture-modify"><span class="glyphicon glyphicon-pencil"></span>修改</a></li>
							</ul> 
						</div>
					</h3>
				</div>
				<dl id="basic-list2" class="my-horizontal">
				</dl>
			</div>
		</div>
	</div>
	<div id="InstanceModalContainer" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
	<div id="ModifyModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
	<div id="IpModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>
