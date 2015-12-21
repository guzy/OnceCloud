<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/admin/modal/modifyUSB.js"></script>
<link rel="stylesheet" href="${basePath}css/alarm.css" />
<div class="modal-dialog" style="margin-top:100px; width:650px">
	<div id="modalcontent" class="modal-content" mtype="${type}" uuid="${uuid}">
		<div class="modal-header">
			<h4 id="modaltitle" class="modal-title">
				USB接口设备<a class="close" data-dismiss="modal" aria-hidden="true"><span
					class="glyphicon glyphicon-remove"></span></a>
			</h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<div class="alarm-price-info">
				<div class="unit-price alert alert-info" style="">以下所有操作均有一定时间延迟，请耐心等候</div>
			</div>
			<div class="once-toolbar" style="margin:10px">
				<button class="btn btn-default" id="refresh">
					<span class="glyphicon glyphicon-refresh" style="margin-right:0"></span>
				</button>
				<button id="create" class="btn btn-primary">+&nbsp;新建</button>
				<button class="btn btn-default" id="delete" disabled>
					<span class="glyphicon glyphicon-trash"></span>删除
				</button>
			</div>
			<table class="table table-bordered once-table" style="margin:10px;width:97%">
				<thead>
					<tr>
						<th width="6%">
							<!-- checkbox -->
						</th>
						<th width="25%">设备</th>
						<th width="25%">名称</th>
						<th width="15%">格式</th>
						<th width="35%">路径</th>
					</tr>
				</thead>
				<tbody id="usbbody">
					<tr>
						<td><input TYPE="checkbox"></td>
						<td>U-SADWWE213s</td>
						<td>USB1</td>
						<td>NFS</td>
						<td>/tmp/vides1</td>
					</tr>
					<tr>
						<td><input TYPE="checkbox"></td>
						<td>U-SADWWE213s</td>
						<td>USB2</td>
						<td>NFS</td>
						<td>/tmp/vides1</td>
					</tr>
					<tr>
						<td><input TYPE="checkbox"></td>
						<td>U-SADWWE213s</td>
						<td>USB3</td>
						<td>NFS</td>
						<td>/tmp/vides1</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
