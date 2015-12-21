<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${basePath}js/common/jquery-1.11.1.min.js"></script>
<script src="${basePath}js/common/sidebar.js"></script>
<div class="navigation">
	<div class="profile">
		<div class="avartar">
			<div id="avartar-top"></div>
			<div id="avartar-bottom"></div>
		</div>
		<p class="user-name">
			<a href="javascript:void(0)">${user.userName}</a>
		</p>
		<div class="user-link">
			<form method="post">
				<a title="首页" href="#"><span class="glyphicon glyphicon-home"></span></a>
			</form>

			<a href="${basePath}logout" title="退出登录"><span
				class="glyphicon glyphicon-off"></span></a> <a id="modifypriceinfo"
				href="#" url="${basePath}admin/modal/modifyprice" title="计费信息修改">
				<span class="glyphicon glyphicon-edit"></span>
			</a> <a id="modifyadmin" href="javascript:void(0);"
				url="${basePath}admin/modal/modifyadmin" title="管理员信息修改"> <span
				class="glyphicon glyphicon-edit"></span></a>
		</div>
		<div id="priceModalContainer" type="" class="modal fade" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
		<div id="adminModalContainer" type="" class="modal fade" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>

	</div>

	<c:if test="${area!=null}">
		<div class="dropdown">
			<button type="button" class="btn dropdown-toggle"
				data-toggle="dropdown" id="selectAllArea">
				${area.areaName} <span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1"
				id="selectArea">
			</ul>
		</div>
	</c:if>

	<div class="sidebar">
		<ul class="nav nav-list">
			<%-- <li <c:if test="${sideActive==0}">class="active"</c:if>><a
				href="${basePath}infrastructure"> <span
					class="glyphicon glyphicon-list-alt cool-red"></span> <span
					class="name">基础架构</span><span class="title">Infrastructure</span>
			</a>
				<div class="cool-border"></div></li> --%>
			<li <c:if test="${sideActive==22}">class="active"</c:if>><a
				href="${basePath}overview"><span
					class="glyphicon glyphicon-shopping-cart cool-red"></span><span
					class="name">概&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;览</span><span
					class="title">overview</span></a>
				<div class="cool-border"></div></li>
			<li <c:if test="${sideActive==23}">class="active"</c:if>><a
				href="${basePath}maplocation"><span
					class="glyphicon glyphicon-shopping-cart cool-red"></span><span
					class="name">地理位置</span><span class="title">maplocation</span></a>
				<div class="cool-border"></div></li>
		</ul>

		<ul class="nav nav-list" style="margin-top: 25px">
			<li <c:if test="${sideActive==1}">class="active"</c:if>><a
				href="${basePath}instance"> <span
					class="glyphicon glyphicon-cloud cool-orange"></span> <span
					class="name">虚拟主机</span><span class="title">Instances</span>
			</a>
				<div class="cool-border"></div></li>

			<li <c:if test="${sideActive==2}">class="active"</c:if>><a
				href="${basePath}image"> <span
					class="glyphicon glyphicon-record cool-blue"></span> <span
					class="name">模板中心</span><span class="title">Images</span>
			</a>
				<div class="cool-border" style="margin-left: 0"></div></li>

			<li <c:if test="${sideActive==222}">class="active"</c:if>><a
				href="${basePath}iso"> <span
					class="glyphicon glyphicon-adjust cool-cyan"></span> <span
					class="name">ISO&nbsp;仓库</span><span class="title">ISOs</span>
			</a>
				<div class="cool-border" style="margin-left: 0"></div></li>
		</ul>

		<ul class="nav nav-list" style="margin-top: 25px">
			<li <c:if test="${sideActive==12}">class="active"</c:if>>
				<div class="cool-border" style="margin-left: 0"></div> <a
				href="${basePath}datacenter"> <span
					class="glyphicon glyphicon-globe cool-green"></span> <span
					class="name">数据中心</span><span class="title">Data Centers</span>
			</a>
				<div class="cool-border"></div>
			</li>
			<li <c:if test="${sideActive==14}">class="active"</c:if>><a
				href="${basePath}pool"> <span
					class="glyphicon glyphicon-tint cool-blue"></span> <span
					class="name">资&nbsp;&nbsp;源&nbsp;&nbsp;池</span><span class="title">Pools</span>
			</a>
				<div class="cool-border"></div></li>

			<li <c:if test="${sideActive==15}">class="active"</c:if>><a
				href="${basePath}ha"> <span
					class="glyphicon glyphicon-star cool-orange"></span> <span
					class="name">高&nbsp;&nbsp;可&nbsp;&nbsp;用</span><span class="title">HA</span>
			</a>
				<div class="cool-border"></div></li>

			<li <c:if test="${sideActive==16}">class="active"</c:if>><a
				href="${basePath}host"> <span
					class="glyphicon glyphicon-tasks cool-red"></span> <span
					class="name">主机管理</span><span class="title">Servers</span>
			</a>
				<div class="cool-border"></div></li>
			<li <c:if test="${sideActive==18}">class="active"</c:if>><a
				href="${basePath}network"> <span
					class="glyphicon glyphicon-indent-left cool-green"></span> <span
					class="name">网络管理</span><span class="title">Networks</span>
			</a>
				<div class="cool-border"></div></li>
			<li <c:if test="${sideActive==17}">class="active"</c:if>><a
				href="${basePath}storage"> <span
					class="glyphicon glyphicon-hdd cool-cyan"></span> <span
					class="name">存储管理</span><span class="title">Storages</span>
			</a>
				<div class="cool-border"></div></li>
				
			<li <c:if test="${sideActive==181}">class="active"</c:if>><a
				href="${basePath}information"> <span
					class="glyphicon glyphicon-hdd cool-cyan"></span> <span
					class="name">主机信息</span><span class="title">HostInformation</span>
			</a>
				<div class="cool-border"></div></li>
		</ul>

		<ul class="nav nav-list" style="margin-top: 25px">
			<li <c:if test="${sideActive==3}">class="active"</c:if>>
				<div class="cool-border" style="margin-left: 0"></div> <a
				href="${basePath}user"> <span
					class="glyphicon glyphicon-user cool-green"></span> <span
					class="name">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户</span><span
					class="title">Users</span>
			</a>
				<div class="cool-border"></div>
			</li>

			<li <c:if test="${sideActive==333}">class="active"</c:if>><a
				href="${basePath}role"> <span
					class="glyphicon glyphicon-lock cool-red"></span> <span
					class="name">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色</span><span
					class="title">Roles</span>
			</a>
				<div class="cool-border"></div></li>

			<%-- <li <c:if test="${sideActive==21}">class="active"</c:if>><a
				href="${basePath}area"> <span
					class="glyphicon glyphicon-lock cool-red"></span> <span
					class="name">区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;域</span><span
					class="title">Area</span>
			</a>
				<div class="cool-border"></div>
			</li> --%>

			<li <c:if test="${sideActive==666}">class="active"</c:if>><a
				href="${basePath}account"> <span
					class="glyphicon glyphicon-flag cool-blue"></span> <span
					class="name">财务分析</span><span class="title">FA</span>
			</a>
				<div class="cool-border"></div></li>
		</ul>


		<ul class="nav nav-list" style="margin-top: 25px">
			<li <c:if test="${sideActive==99}">class="active"</c:if>>
				<div class="cool-border" style="margin-left: 0"></div> <a
				href="${basePath}upxen"> <span
					class="glyphicon glyphicon-upload cool-red"></span> <span
					class="name">在线更新</span><span class="title">UpXen</span>
			</a>
				<div class="cool-border"></div>
			</li>

			<li <c:if test="${sideActive==9}">class="active"</c:if>>
				<div class="cool-border" style="margin-left: 0"></div> <a
				href="${basePath}log"> <span
					class="glyphicon glyphicon-list-alt cool-purple"></span> <span
					class="name">操作日志</span><span class="title">Activities</span>
			</a>
				<div class="cool-border"></div>
			</li>

			<li <c:if test="${sideActive==11}">class="active"</c:if>><a
				href="${basePath}service"> <span
					class="glyphicon glyphicon-question-sign cool-cyan"></span> <span
					class="name">表单处理</span> <span class="title">Services</span>
			</a>
				<div class="cool-border"></div></li>

			<li <c:if test="${sideActive==20}">class="active"</c:if>><a
				href="${basePath}statistics"><span
					class="glyphicon glyphicon-shopping-cart cool-red"></span><span
					class="name">统&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</span><span
					class="title">Statistics</span></a>
				<div class="cool-border"></div></li>
		</ul>
	</div>
</div>