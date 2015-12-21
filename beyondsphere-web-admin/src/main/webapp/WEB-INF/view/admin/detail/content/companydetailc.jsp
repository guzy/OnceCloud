<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content" id="platformcontent" basePath="${basePath}">
	<div class="intro">
		<h1>注册公司&nbsp;Registered Company</h1>
		<p class="lead" style="margin-top:10px">
			<em>注册公司&nbsp;(Registered Company)</em>&nbsp;显示已经在云平台上注册的公司的基本信息。
		</p>
	</div>
    <div class="once-pane">
    	<div class="once-toolbar">
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
				<br>
			</div>
		</div>
        <table class="table table-bordered once-table">
			<thead>
				<tr>
                	<th id="select" class = "select" width="4%">全选</th>
                	<th width="10%">公司</th>
					<th width="10%">注册名称</th>
					<th width="10%">注册虚拟机个数</th>
					<th width="15%">公司邮箱</th>
					<th width="10%">联系电话</th>
					<th width="15%">注册时间</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>
	<div id="UserModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
    </div> 
</div>