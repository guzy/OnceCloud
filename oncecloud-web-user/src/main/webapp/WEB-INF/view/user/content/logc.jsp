<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent">
	<div class="intro">
		<h1 style="font-size:22px; margin-top:30px">操作日志&nbsp;Activities</h1>
		<p class="lead" style="margin-top:10px"></p>
	</div>
	<ul class="nav nav-tabs once-tab">
    	<li class="tab-filter active" type="operation-log" data-status="4"><a href="javascript:void(0)"><span class="glyphicon glyphicon-list"></span>操作日志</a></li>
  		<!-- <li class="tab-filter" type="system-log" data-status="1"><a href="javascript:void(0)"><span class="glyphicon glyphicon-list"></span>系统日志</a></li> -->
  		<li class="tab-filter" type="application-log" data-status="0"><a href="javascript:void(0)"><span class="glyphicon glyphicon-list"></span>应用日志</a></li>
	</ul>
	<div class="once-pane"  id="operation-log" style="padding: 10px 30px 0">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh">
				<span class="glyphicon glyphicon-refresh" style="margin-right: 0"></span>
			</button>
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
					更多操作... <span class="caret" style="margin-left: 15px"></span>
				</button>
				<ul class="dropdown-menu" id="dropdown-menu">
					<li><a id="delete"><span class="glyphicon glyphicon-trash"></span>删除</a></li>
					<!-- <li><a id="export"><span class="glyphicon glyphicon-log-out"></span>导出</a></li> -->
					<li><a id="config-save-time"><span class="glyphicon glyphicon-cog"></span>设置保存时限</a></li>
					<li><a id="config-cancel-time"><span class="glyphicon glyphicon-minus-sign"></span>取消保存时限</a></li>
				</ul>
			</div>
			<input class="search" id="search" value="">
			<label class="checkbox-inline">
				日志类型：
		      	<input type="radio" class="operationRadio" id="optionSucess" name="operationRadio"  value="1"> 成功&nbsp;&nbsp;&nbsp;&nbsp;
		      	<input type="radio" class="operationRadio" id="optionFailed" name="operationRadio"  value="0"> 失败
		    </label>
		    
		</div>
		<a id="filedownload" href = "#" style="visibility: hidden"><span id="download">下载</span></a>
		<ol id="log-area">
		</ol>
		<button class="btn btn-default" id="load-log" start="10" has-log="false"><span class="glyphicon glyphicon-search" style="margin-right:7px"></span>查看更多...</button> 
	</div>
	
	
	<!-- <div class="once-pane" id="system-log" style="padding: 10px 30px 0; display:none;">
		<div class="once-toolbar">
			<label class="checkbox-inline">
				日志类型：
		      	<input type="radio" class="systemRadio" id="systemXen" name="systemRadio"  value="1"> 虚拟机日志&nbsp;&nbsp;&nbsp;&nbsp;
		      	<input type="radio" class="systemRadio" id="systemDocker" name="systemRadio"  value="2"> 容器日志
		    </label>
		</div>
	</div> -->
	
	
	<div class="once-pane" id="application-log" style="padding: 10px 30px 0; display:none;">
		<div class="once-toolbar">
			<label class="checkbox-inline">
				应用名称：
				<select id="dockerList" style="margin-right:15px;">
				</select>
				
				日志类型：
				<input type="radio" class="applicationRadio" id="Tomcat" name="applicationRadio"  value="0"> 输出日志&nbsp;&nbsp;&nbsp;&nbsp;
		      	<input type="radio" class="applicationRadio" id="mysql" name="applicationRadio"  value="1" checked> 错误日志
		      	<!-- <input type="radio" class="applicationRadio" id="jetty" name="applicationRadio"  value="1"> Jetty日志&nbsp;&nbsp;&nbsp;&nbsp;
		      	<input type="radio" class="applicationRadio" id="Tomcat" name="applicationRadio"  value="2"> Tomcat日志&nbsp;&nbsp;&nbsp;&nbsp;
		      	<input type="radio" class="applicationRadio" id="mysql" name="applicationRadio"  value="3"> Mysql日志 -->
		    </label>
		</div> 
		<div class="once-body" id="containerLog">
		</div>
	</div>
	<div id="LogModalContainer" type="" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
    </div>
</div>