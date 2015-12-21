<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
			<a href="${basePath}instance" title="首页"><span class="glyphicon glyphicon-home"></span></a> 
			<a href="${basePath}logout" title="退出登录"><span class="glyphicon glyphicon-off"></span></a>
			<a id="modifyuserinfo" href="#" url="${basePath}user/modal/modifyUserinfo" title="用户信息修改"><span class="glyphicon glyphicon-edit"></span></a>
		</div>
		<div id="userModalContainer" type="" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
	</div>
	<div class="sidebar">
		<c:set var="theString" value="${userAuthHtmlId}" />
		<c:set var="operationAuth" value="${operationAuth}" />
		<input id="activeArea" type="hidden" value="${activeArea}">
		<div class="panel-group" id="accordion">
		   <ul class="nav nav-list">
				<li id="overviewPage"
					<c:if test="${sideActive==0}">class="active"</c:if>><a href="${basePath}overview" style="padding-left:18px;">
		            	<span class="glyphicon glyphicon-globe cool-cyan"></span>
		            	<span class="name">概&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;览</span>
		            	<span class="title enlish">Overview</span>
		            </a></li>
		   </ul>
		   <div class="cool-border"></div>	
		   
		   <div class="panel">
		      <div class="panel-heading">
		         <h4 class="panel-title">
		            <a data-toggle="collapse" data-parent="#accordion"  href="#resource">
						<span class="glyphicon glyphicon-tasks cool-green"></span>
		            	<span class="name">资源管理</span>
		            	<span class="title">Resource</span>
		            </a>
		         </h4>
		      </div>
		      <div id="resource" class="panel-collapse collapse in">
		         <div class="panel-body">
		         	<ul class="nav nav-list">
						<%--  <c:if test="${fn:contains(theString,'platformPage')}"> --%>
							<li id="platformPage"
								<c:if test="${sideActive==20}">class="active"</c:if>><a class="type"
								href="${basePath}cluster"><span
									class="glyphicon glyphicon-tint cool-blue"></span><span
									class="name">集群管理</span><span class="title enlish">Cluster</span></a>
								<div class="cool-border"></div>
							</li>
						<%-- </c:if>  --%>
						 <%-- <c:if test="${fn:contains(theString,'templatePage')}"> --%>
							<li id="templatePage"
								<c:if test="${sideActive==21}">class="active"</c:if>><a class="type"
								href="${basePath}host"><span
									class="glyphicon glyphicon-off cool-red"></span><span
									class="name">主机管理</span><span class="title enlish">Host</span></a>
								<div class="cool-border"></div>
							</li>
						<%-- </c:if>  --%>
					</ul>
		         </div>
		      </div>
		   </div>
		   <div class="cool-border"></div>
		   
		   <div class="panel">
		      <div class="panel-heading">
		         <h4 class="panel-title">
		            <a data-toggle="collapse" data-parent="#accordion"  href="#virtual">
		            	<span class="glyphicon glyphicon-cloud cool-red"></span>
		            	<span class="name">虚机管理</span>
		            	<span class="title">Virtual</span>
		            </a>
		         </h4>
		      </div>
		      <div id="virtual" class="panel-collapse collapse in">
		         <div class="panel-body">
		         	<ul class="nav nav-list">
						<c:if test="${fn:contains(theString,'instancePage')}">
							<li id="instancePage"
								<c:if test="${sideActive==1}">class="active"</c:if>><a class="type"
								href="${basePath}instance"><span
									class="glyphicon glyphicon-cloud cool-orange"></span><span
									class="name">主&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机</span><span class="title enlish">Instances</span></a>
								<div class="cool-border"></div></li>
						</c:if>
						<c:if test="${fn:contains(theString,'volumePage')}">
							<li id="volumePage"
								<c:if test="${sideActive==3}">class="active"</c:if>><a class="type"
								href="${basePath}volume"><span
									class="glyphicon glyphicon-hdd cool-blue"></span><span
									class="name">硬&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;盘</span><span class="title enlish">Volumes</span></a>
								<div class="cool-border"></div></li>
						</c:if>
						<c:if test="${fn:contains(theString,'snapshotPage')}">
							<li id="snapshotPage"
								<c:if test="${sideActive==4}">class="active"</c:if>><a class="type"
								href="${basePath}snapshot"><span
									class="glyphicon glyphicon-camera cool-blue"></span><span
									class="name">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份</span><span class="title enlish">Snapshots</span></a>
								<div class="cool-border"></div></li>
						</c:if>
						<c:if test="${fn:contains(theString,'imagePage')}">
							<li id="imagePage"
								<c:if test="${sideActive==2}">class="active"</c:if>><a class="type"
								href="${basePath}image"><span
									class="glyphicon glyphicon-record cool-blue"></span><span
									class="name">映&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;像</span><span class="title enlish">Images</span></a>
							</li>
						</c:if>
					</ul>
		         </div>
		      </div>
		   </div>
		   <div class="cool-border"></div>
		   <div class="panel">
		      <div class="panel-heading">
		         <h4 class="panel-title">
		            <a data-toggle="collapse" data-parent="#accordion"  href="#container">
						<span class="glyphicon glyphicon-record cool-green"></span>
		            	<span class="name">容器管理</span>
		            	<span class="title">Container</span>
		            </a>
		         </h4>
		      </div>
		      <div id="container" class="panel-collapse collapse">
		         <div class="panel-body">
		         	<ul class="nav nav-list">
		         		<%-- <c:if test="${fn:contains(theString,'repositoryPage')}"> --%>
							<li id="repositoryPage"
								<c:if test="${sideActive==14}">class="active"</c:if>><a class="type"
								href="${basePath}repository"><span
									class="glyphicon glyphicon-flag cool-blue"></span><span
									class="name">仓库管理</span><span class="title enlish">Repository</span></a>
							</li>
						<%-- </c:if> --%>
						 <%-- <c:if test="${fn:contains(theString,'templatePage')}"> --%>
							<li id="templatePage"
								<c:if test="${sideActive==13}">class="active"</c:if>><a class="type"
								href="${basePath}template"><span
									class="glyphicon glyphicon-adjust cool-red"></span><span
									class="name">镜像管理</span><span class="title enlish">Template</span></a>
								<div class="cool-border"></div>
							</li>
						<%-- </c:if>  --%>
						<%-- <c:if test="${fn:contains(theString,'platformPage')}"> --%>
							<li id="platformPage"
								<c:if test="${sideActive==12}">class="active"</c:if>><a class="type"
								href="${basePath}container"><span
									class="glyphicon glyphicon-record cool-purple"></span><span
									class="name">容器管理</span><span class="title enlish">Container</span></a>
								<div class="cool-border"></div>
							</li>
						<%-- </c:if>  --%>
						 <li id="deployPage"
							<c:if test="${sideActive==30}">class="active"</c:if>><a
							class="type" href="${basePath}deploy"><span
								class="glyphicon glyphicon-indent-left cool-orange"></span><span
								class="name">编排部署</span><span class="title enlish">Deploy</span></a>
						</li> 
						<li id="deployPage"
							<c:if test="${sideActive==31}">class="active"</c:if>><a
							class="type" href="${basePath}distribute"><span
								class="glyphicon glyphicon-indent-left cool-orange"></span><span
								class="name">部署展示</span><span class="title enlish">Distribute</span></a>
						</li>
								<li id="registryPage"
									<c:if test="${sideActive==32}">class="active"</c:if>><a
									class="type" href="${basePath}registry"><span
										class="glyphicon glyphicon-record cool-orange"></span><span
										class="name">仓库列表</span><span class="title enlish">Registry</span></a>
								</li>
					</ul>
		         </div>
		      </div>
		   </div>
		   <div class="cool-border"></div>
		   <%--  <div class="panel">
		      <div class="panel-heading">
		         <h4 class="panel-title">
		            <a data-toggle="collapse" data-parent="#accordion"  href="#application">
						<span class="glyphicon glyphicon-bell cool-blue"></span>
		            	<span class="name">应用部署</span>
		            	<span class="title">Application</span>
		            </a>
		         </h4>
		      </div>
		      <div id="application" class="panel-collapse collapse">
		         <div class="panel-body">
		         	  <ul class="nav nav-list">
						<li <c:if test="${sideActive==16}">class="active"</c:if>><a class="type"
							href="${basePath}application"><span
								class="glyphicon glyphicon-bell cool-blue"></span><span
								class="name">应用管理</span><span class="title enlish">Application</span></a>
						</li>
					</ul>
		         </div>
		      </div>
		   </div>
		   <div class="cool-border"></div> --%>
		   <div class="panel">
		      <div class="panel-heading">
		         <h4 class="panel-title">
		            <a data-toggle="collapse" data-parent="#accordion" href="#system">
		               	<span class="glyphicon glyphicon-folder-open cool-orange"></span>
		            	<span class="name">系统管理</span>
		            	<span class="title">System</span>
		            </a>
		         </h4>
		      </div>
		      <div id="system" class="panel-collapse collapse">
		         <div class="panel-body">
		         	  <ul class="nav nav-list">
		         	  	<c:if test="${fn:contains(theString,'alarmPage')}">
							<li id="alarmPage"
								<c:if test="${sideActive==5}">class="active"</c:if>><a class="type"
								href="${basePath}alarm"><span
									class="glyphicon glyphicon-bell cool-red"></span><span
									class="name">监控警告</span><span class="title enlish">Alarms</span></a>
								<div class="cool-border"></div></li>
						</c:if> 
						<c:if test="${fn:contains(theString,'groupPage')}">
							<li id="alarmPage"
								<c:if test="${sideActive==6}">class="active"</c:if>><a class="type"
								href="${basePath}group"><span
									class="glyphicon glyphicon-th cool-green"></span><span
									class="name">分组管理</span><span class="title enlish">Group</span></a>
								<div class="cool-border"></div></li>
						</c:if> 
						<c:if test="${fn:contains(theString,'servicePage')}">
							<li <c:if test="${sideActive==15}">class="active"</c:if>><a class="type"
								href="${basePath}consumption"><span
									class="glyphicon glyphicon-shopping-cart cool-red"></span><span
									class="name">实时消费</span><span class="title enlish">Consumption</span></a>
							</li>
						</c:if>	
						<c:if test="${fn:contains(theString,'logPage')}">
							<li id="logPage" <c:if test="${sideActive==9}">class="active"</c:if>><div
									class="cool-border"></div> <a class="type"
								href="${basePath}log"><span
									class="glyphicon glyphicon-list-alt cool-purple"></span><span
									class="name">日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;志</span><span class="title enlish">Activities</span></a>
								<div class="cool-border"></div></li>
						</c:if>
						<c:if test="${fn:contains(theString,'servicePage')}">
							<li id="servicePage"
								<c:if test="${sideActive==11}">class="active"</c:if>><a class="type"
								href="${basePath}service"><span
									class="glyphicon glyphicon-question-sign cool-cyan"></span><span
									class="name">表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单</span><span class="title enlish">Services</span></a>
								<div class="cool-border"></div></li>
						</c:if>
						<c:if test="${fn:contains(theString,'servicePage')}">
							<li <c:if test="${sideActive==10}">class="active"</c:if>><a class="type"
								href="${basePath}statistics"><span
									class="glyphicon glyphicon-shopping-cart cool-red"></span><span
									class="name">统&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计</span><span class="title enlish">Statistics</span></a>
								<div class="cool-border"></div>
							</li>
						</c:if>
		         	  </ul>
		         </div>
		      </div>
		   </div>
		</div>
	</div>
</div>