<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/admin/modal/modifynetwork.js"></script>
<link rel="stylesheet" href="${basePath}css/alarm.css" />
<div class="modal-dialog" style="margin-top:100px; width:650px">
	<div id="modalcontent" class="modal-content" mtype="${type}" uuid="${uuid}" isrun="${isrun}" modify="physical">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">
				编辑网卡<a class="close" data-dismiss="modal" aria-hidden="true"><span
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
				<button class="btn btn-default" id="modifyvlan" disabled>
					<span class="glyphicon glyphicon-pencil"></span>加入vlan
				</button>
				<!-- <button class="btn btn-default" id="modifynet" disabled>
					<span class="glyphicon glyphicon-tasks"></span>绑定网卡
				</button> -->
				<button class="btn btn-default" id="limitnetwork" disabled>
					<span class="glyphicon glyphicon-sort"></span>网卡限速
				</button>
			</div>
			<table class="table table-bordered once-table" style="margin:10px;width:97%">
				<thead>
					<tr>
						<th width="6%">
							<!-- checkbox -->
						</th>
						<th width="18%">设备</th>
						<th width="43%">mac地址</th>
						<th width="15%">vlan号</th>
						<th width="18%">物理网卡</th>
					</tr>
				</thead>
				<tbody id="netbody">
				</tbody>
			</table>
		</div>
	</div>
</div>
