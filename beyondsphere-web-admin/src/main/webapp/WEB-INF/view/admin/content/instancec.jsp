<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent"  novnc="${vncServer}">
	<div class="intro">
		<h1>虚拟主机&nbsp;Instances</h1>
		<p class="lead">
			为您提供一种随时获取的、弹性的计算能力，这种计算能力的体现就是<em>虚拟主机&nbsp;(Instance)</em>。主机就是一台配置好了的服务器，它有您期望的硬件配置、操作系统和网络配置。通常情况下，您的请求可以在60秒左右的时间内完成，所以您完全可以动态地、按需使用计算能力。
		</p>
	</div>
	<div class="once-pane">
		<div class="once-toolbar">
			<button class="btn btn-default btn-refresh"><span class="glyphicon glyphicon-refresh" style="margin-right:0"></span></button>
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">资源配置
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a class="btn-forbidden" id="editNetwork"><span class="glyphicon glyphicon-pencil"></span>网络配置</a></li>
					<li><a class="btn-forbidden" id="limitdisk"><span class="glyphicon glyphicon-hdd"></span>硬盘限速</a></li>
					<li><a class="btn-forbidden" id="clearlimit"><span class="glyphicon glyphicon-hdd"></span>取消硬盘限速</a></li>
					<li><a class="btn-forbidden" id="add2Router"><span class="glyphicon glyphicon-pencil"></span>加入路由</a></li>
					<li><a class="btn-forbidden" id="usbManage"><span class="glyphicon glyphicon-hdd"></span>USB接口设备</a></li>
				</ul>
			</div>
			
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span  id="selecthost">选择服务器</span>
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu" id="select-server">
					<li><a id="hostall">全部</a></li>
				</ul>
			</div>
			<div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">更多操作...
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a class="btn-forbidden" id="startup"><span class="glyphicon glyphicon-play"></span>启动</a></li>
					<li><a class="btn-forbidden" id="pause"><span class="glyphicon glyphicon-pause"></span>暂停</a></li>
					<li><a class="btn-forbidden" id="restart"><span class="glyphicon glyphicon-flash"></span>恢复</a></li>
					<li><a class="btn-forbidden" id="shutdown"><span class="glyphicon glyphicon-stop"></span>关机</a></li>
					<li><a class="btn-forbidden" id="destroy"><span class="glyphicon glyphicon-trash"></span>销毁</a></li>
					<li><a id="creatVMISO"><span class="glyphicon glyphicon-record"></span>新建</a></li>
					<li><a class="btn-forbidden" id="vm-to-template"><span class="glyphicon glyphicon-share"></span>转化为映像</a></li>
					<li><a class="btn-forbidden" id="migration"><span class="glyphicon glyphicon-arrow-right"></span>迁移</a></li>
					<li><a class="btn-forbidden" id="off_migration"><span class="glyphicon glyphicon-play"></span>离线迁移</a></li>
					<li><a class="btn-forbidden" id="touser"><span class="glyphicon glyphicon-user"></span>分配到用户</a></li>
					<li><a id="export"><span class="glyphicon glyphicon-log-out"></span>导出</a></li>					
				</ul>
			</div>
			<!-- <div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span id="selectimportant">选择重要性</span>
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu" id="select-importance">
					<li><a id="all-star">全部</a></li>
					<li><a id="none-star"><span class="glyphicon glyphicon-star-empty"></span></a></li>
					<li><a id="one-star"><span class="glyphicon glyphicon-star"></span></a></li>
					<li><a id="two-star"><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span></a></li>
					<li><a id="three-star"><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span></a></li>
					<li><a id="four-star"><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span></a></li>
					<li><a id="five-star"><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span><span class="glyphicon glyphicon-star"></span></a></li>
				</ul>
			</div> -->
			<button class="btn btn-danger" id="status-check">
				<span class="glyphicon glyphicon-check"></span>状态校验
			</button>
			<a id="filedownload" href = "#" style="visibility: hidden"><span id="download"></span>下载</a>
			<!-- <div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown"><span id="selecttype">选择类型</span>
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a id="select-instance"><span class="glyphicon glyphicon-cloud"></span>主机</a></li>
				</ul>
			</div> -->
			<div class="toolbar-right">
				<table>
					<tr>
						<td>每页&nbsp;</td>
						<td><input id="limit" name="limit" class="page" value="10"></td>
						<td>&nbsp;个&nbsp;页数&nbsp;<a id="currentP"></a>&nbsp;/&nbsp;<a id="totalP"></a></td>
						<td style="padding-left:10px">
							<div><ul class="pagination" id="pageDivider" style="display:inline"></ul></div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="table table-bordered once-table">
			<thead>
				<tr>
					<th id="select" class = "select" width="4%">全选</th>
					<th width="12%">虚拟机编号</th>
					<th>用户</th>
					<th width="12%">名称</th>
					<th width="10%">状态</th>
					<!-- <th>重要性</th> -->
					<th width="8%">CPU</th>
					<th width="8%">内存</th>
					<th width="18%">网络</th>
					<th>运行时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<input type="hidden" id="userid" value="${userid}">
	<div id="InstanceModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
	<div id="ModifyModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
	<div id="add2RouterModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
	<div id="usbManageContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
	<div id="createUsbContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
	
</div>
