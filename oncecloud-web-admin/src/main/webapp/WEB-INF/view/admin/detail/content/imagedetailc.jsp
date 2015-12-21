<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="content detail" id="platformcontent" imageUuid="${imageUuid}" basePath="${basePath}" userLevel="${user.userLevel}">
	<div class="intro">
		<h1>映像&nbsp;Images</h1>
		<p class="lead">
			<em>映像&nbsp;(Image)</em>是带有操作系统的主机模板。云平台官方会提供主流的 Linux、Windows
			模板，并根据上游厂商更新版本时及时制作新模板。用户也可以在将自己名下的主机制作成模板，以备后用。
		</p>
	</div>
	<ol class="breadcrumb oc-crumb">
		<li><a href="${basePath}image"><span class="glyphicon glyphicon-inbox cool-blue"></span><span class="ctext">IMAGE</span></a></li>
		<li class="active">
		<c:choose>
			<c:when test="${imageType=='user'}">
				<c:choose>
					<c:when test="${user.userLevel.ordinal()==0}">
						用户
					</c:when>
					<c:otherwise>
						自有
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				系统
			</c:otherwise>
		</c:choose>
		<li class="active"><a href="${basePath}image/detail">${showId}</a></li>
	</ol>
	<div class="col-md-4">
		<div class="detail-item">
			<div class="title">
				<h3>基本属性&nbsp;<a href="javascript:void(0)" class="btn-refresh"><span class="glyphicon glyphicon-refresh"></span></a>
				<c:if test="${user.userLevel.ordinal()==0}">
					<div class="btn-group">
						<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							<span class="glyphicon glyphicon-tasks"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a id="modify"><span class="glyphicon glyphicon-pencil"></span>修改</a></li>
						</ul>
					</div>
				</c:if>
				</h3>
			</div>
			<dl id="basic-list" class="my-horizontal"></dl>
		</div>
		<div class="detail-item">
			<div class="title">
				<h3>关联资源&nbsp;<a href="javascript:void(0)" class="btn-refresh"><span class="glyphicon glyphicon-refresh"></span></a>
				</h3>
			</div>
			<dl id="depend-list" class="my-horizontal"></dl>
		</div>
	</div>
	<div class="col-md-8">
	</div>
	<div id="ImageModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>