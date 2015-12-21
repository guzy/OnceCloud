<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content detail" id="platformcontent" volumeUuid="${volumeUuid}" basePath="${basePath}">
	<div class="intro">
		<h1>硬盘&nbsp;Volumes</h1>
		<p class="lead">
			<em>硬盘&nbsp;(Volume)</em>为主机提供块存储设备，它独立于主机的生命周期而存在，可以被连接到任意运行中的主机上。当硬盘附加到主机上后，还需要登陆到主机的操作系统中去加载该硬盘。卸载硬盘时请先在主机的操作系统中完成卸载。
		</p>
	</div>
	<ol class="breadcrumb oc-crumb">
		<li><a href="${basePath}volume"><span class="glyphicon glyphicon-hdd cool-blue"></span><span class="ctext">VOLUME</span></a></li>
		<li class="active"><a href="${basePath}volume/detail">${showId}</a></li>
	</ol>
	<div class="col-md-4">
		<div class="detail-item">
			<div class="title">
				<h3>基本属性&nbsp;<a href="javascript:void(0)" class="btn-refresh"><span class="glyphicon glyphicon-refresh"></span></a>
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
				<h3>关联资源&nbsp;<a href="javascript:void(0)" class="btn-refresh"><span class="glyphicon glyphicon-refresh"></span></a>
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
	</div>
	<div id="VolumeModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>