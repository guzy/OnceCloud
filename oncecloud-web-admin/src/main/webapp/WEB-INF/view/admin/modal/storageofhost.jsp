<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/admin/modal/storageofhost.js"></script>
<div class="modal-dialog" style="margin-top:100px">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">服务器&nbsp;${hostname}&nbsp;存储列表<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body">
			<div class="table-inner">
				<div class="once-pane sr-pane">
					<table class="table table-bordered once-table">
						<thead>
							<tr>
								<th>ID</th>
								<th>地址</th>
								<th>挂载目录</th>
								<th>文件系统</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tablebodydetail" hostuuid="${hostuuid}">
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
