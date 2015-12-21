<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content detail" id="platformcontent" resourceUuid="${resourceUuid}" resourceType="${resourceType}" resourceName="${resourceName}" platformBasePath="${basePath}">
	<div class="intro">
		<h1>备份&nbsp;Snapshots</h1>
		<p class="lead" style="margin-top:10px">
			<em>备份&nbsp;(Snapshot)</em>用于对正在运行的主机做在线备份。一个主机可以有一个备份链，每条备份链包括多个备份点，您可以随时从任意一个备份点恢复数据。
		</p>
	</div>
	<ol class="breadcrumb oc-crumb">
		<li><a href="${basePath}snapshot"><span class="glyphicon glyphicon-camera cool-blue"></span><span class="ctext">SNAPSHOTS</span></a></li>
		<li class="active"><a href="${basePath}snapshot/detail">${showId}</a></li>
	</ol>
	<div class="col-md-4">
		<div class="detail-item">
			<div class="title">
				<h3>基本属性&nbsp;<a href="javascript:void(0)" class="btn-refresh"><span class="glyphicon glyphicon-refresh"></span></a>
					<!-- <div class="btn-group">
						<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							<span class="glyphicon glyphicon-tasks"></span>
						</button>
						<ul class="dropdown-menu">
						</ul>
					</div> -->
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
		<div class="detail-item detail-right">
			<div class="title">
				<h3 class="uppercase">备份链:&nbsp;&nbsp;${showId}
					<a href="javascript:void(0)" class="bk-refresh"><span class="glyphicon glyphicon-refresh"></span></a>
				</h3>
			</div>
			<div id="snapshot-graph" class="snapshot-graph">
			</div>
			<div class="snapshot-legend">
				<div><span class="circle new"></span>新建备份<span class="circle full" style="margin-left:10px"></span>备份点</div>
			</div>
		</div>
	</div>
	<div id="SnapshotModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>