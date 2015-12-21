<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent" userid="${userid}">
	<div class="intro">
		<h1>镜像&nbsp;ISO</h1>
		<p class="lead" style="margin-top:10px">
			<em>镜像&nbsp;(ISO)</em>是用户自己上传的普通的ISO镜像文件，可以用于制作映像。
		</p>
	</div>
    <div class="once-pane">
    	<div class="once-toolbar">
			<button class="btn btn-default btn-refresh"><span class="glyphicon glyphicon-refresh" style="margin-right:0"></span></button>
			<button id="upfileModel" class="btn btn-primary" url="${basePath}admin/modal/iso/upfile">
				<span class="glyphicon glyphicon-user"></span>上传镜像
			</button>
			
          <!--   <div class="btn-group">
				<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">更多操作...
					<span class="caret" style="margin-left:15px"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a class="btn-forbidden" id="delete"><span class="glyphicon glyphicon-trash"></span>删除</a></li>
				</ul>
			</div> -->
           <!--  <input class="search" id="search" value="">
			<div class="toolbar-right">
				<table>
					<tr>
						<td>每页&nbsp;</td>
						<td><input id="limit" name="limit" class="page" value="10"></td>
						<td>&nbsp;个&nbsp;页数&nbsp;<a id="currentP"></a>&nbsp;/&nbsp;<a id="totalP"></a></td>
						<td style="padding-left:10px">
							<div>
								<ul id="pageDivider" style="display:inline">
								</ul>
							</div>
						</td>
					</tr>
				</table>
			</div> -->
		</div>
        <table class="table table-bordered once-table">
			<thead>
				<tr>
                <!-- 	<th width="5%"></th> -->
					<th width="60%">镜像名称</th>
				<th width="40%">所属资源池</th>
			<!-- 			<th width="40%">镜像说明</th> -->
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="UserModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
    </div> 
</div>