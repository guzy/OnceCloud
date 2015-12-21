<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent">
	<div class="intro">
		<h1 style="font-size:22px; margin-top:30px">操作日志&nbsp;Activities</h1>
		<p class="lead" style="margin-top:10px"></p>
	</div>
	<ul class="nav nav-tabs once-tab" style="width:32%;float:left">
    	<li class="tab-filter active" data-status="4"><a href="javascript:void(0)"><span class="glyphicon glyphicon-list"></span>全部</a></li>
  		<li class="tab-filter" data-status="1"><a href="javascript:void(0)"><span class="glyphicon glyphicon-ok"></span>成功</a></li>
  		<li class="tab-filter" data-status="0"><a href="javascript:void(0)"><span class="glyphicon glyphicon-remove"></span>失败</a></li>
	</ul>
	<div class="once-toolbar" style="float:left;width:200px;">
		<input class="search" id="search" value="">
	</div>
	<div class="once-pane" style="padding: 60px 30px 0">
		<ol id="log-area">
		</ol>
		<button class="btn btn-default" id="load-log" start="10" has-log="false"><span class="glyphicon glyphicon-search" style="margin-right:7px"></span>查看更多...</button> 
	</div>
</div>