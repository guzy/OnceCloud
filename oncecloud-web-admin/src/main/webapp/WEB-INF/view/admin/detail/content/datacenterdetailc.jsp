<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent">
	<div class="intro">
		<h1>数据中心&nbsp;Datacenters</h1>
		<p class="lead" style="margin-top:10px">
			<em>数据中心&nbsp;(Datacenter)</em>是云平台中最大的管理单元，它包含一到多个机架以及上面的服务器集群，并包含专线接入，散热，监控等多种配套设施，向外界提供稳定、优质、高效的云计算服务。
		</p>
	</div>
	<ul class="nav nav-tabs once-tab">
		<li class="tab-filter active" type="datacentertab">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-tint"></span>数据中心</a>
		</li>
		<!-- <li class="tab-filter" type="pooltab">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-tint"></span>资源池</a>
		</li> -->
	</ul>
	<input id = "dcid" type="hidden" value="${dcid }">
	<div id="line"></div>
   	<div id="tagdiv"></div>
   	<div id="container">
   		<div id="container-up"></div>
   		<div id="container-down">
			<ul class="list-inline">
			    <li>
			      <div class="panel panel-default">
				    <div class="panel-heading">
				      <h3 class="panel-title">数据中心结构</h3>
				    </div>
				    <div class="panel-body" id="panel-cpu">
				    	<img class="media-object constructure" src="${basePath}css/images/constructure.png">
				    </div>
				  </div>
			    </li>
			    <li>
			  	  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h3 class="panel-title">数据中心容量概览</h3>
				      <span><a class="alarm" id = "datacenter-last-info">提取最新</a></span>
				    </div>
				    <div class="panel-body">
				    
				    	<div class="media">
					        <a class="pull-left" href="${basePath}datacenter">
			                  <img class="media-object" src="${basePath}css/images/data.png">
			                </a>
			                <div class="media-body">
			                  <span>集群：${datacenterInfo.poolNum }</span><br>
			                  <span>主机：${datacenterInfo.hostNum }</span><br>
			                  <span>网络：${datacenterInfo.vlanNum }</span><br>
			                  <%-- <span>存储：${datacenterInfo.storageNum }</span><br> --%>
			                  <span>虚拟机：${datacenterInfo.vmNum }</span>
			                </div>
					    </div>
					    
				    	<div>
				    	  <span>CPU</span>
				    	  <span class="left" id = "cpu-used">已用：0%</span>
				    	</div>
				        <div class="progress">
						   <div class="progress-bar progress-bar-success" role="progressbar" 
						      aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
						      style="width: 90%;">
						   </div>
						</div>
						<div class="panel-bottom">
				    	  <span id = "cpu-left">剩余：100%</span>
				    	  <span class="left">容量：100%</span>
				    	</div>
				    	
				    	<div>
				    	  <span>内存</span>
				    	  <span class="left" id = "mem-used">已用：0%</span>
				    	</div>
				        <div class="progress">
						   <div class="progress-bar progress-bar-danger" role="progressbar" 
						      aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
						      style="width: 20%;">
						   </div>
						</div>
						<div class="panel-bottom">
				    	  <span id = "mem-left">剩余：100%</span>
				    	  <span class="left">容量：100%</span>
				    	</div>
				    	
				    	<div>
				    	  <span>Vlan</span>
				    	  <span class="left" id = "vlan-used">已用：0%</span>
				    	</div>
				        <div class="progress">
						   <div class="progress-bar progress-bar-warning" role="progressbar" 
						      aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
						      style="width: 50%;">
						   </div>
						</div>
						<div class="panel-bottom">
				    	  <span id = "vlan-left">剩余：100%</span>
				    	  <span class="left">容量：100%</span>
				    	</div>
				    	
				    	<div>
				    	  <span>存储</span>
				    	  <span class="left" id = "storage-used">已用：0%</span>
				    	</div>
				        <div class="progress">
						   <div class="progress-bar progress-bar-info" role="progressbar" 
						      aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" 	
						      style="width: 50%;">
						   </div>
						</div>
						<div class="panel-bottom">
				    	  <span id = "storage-left">剩余：100%</span>
				    	  <span class="left">容量：100%</span>
				    	</div>
				    </div>
				  </div>  
			    </li>
			    <li>
			  	  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h3 class="panel-title">警&nbsp;&nbsp;报</h3>
				      <span><a class="alarm" id = "alarm">查看全部</a></span>
				    </div>
				    <div class="panel-body" id="panel-storage">
				      <span>暂无预警信息</span>
				    </div>
				  </div>  
			    </li>
			  </ul>	   			
   		</div>
   	</div>
	</div>
</div>
		