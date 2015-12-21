<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/admin/modal/migration.js"></script>

<div class="modal-dialog" style="width:600px; margin-top:100px">
	<input type="hidden" id="migration_type" value="${type}"/>
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">迁移到<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove" id="resourceRemove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:15px 30px">
			<div class="hostlist" id="hostlist">
			</div>
		</div>
		<div class="modal-footer">
			<button id="migratesubmit" class="btn btn-primary" type="button" data-dismiss="modal" disabled>提交</button>
			<button id="cancelbind" class="btn btn-default" type="button" data-dismiss="modal">取消</button>
		</div>
	</div>
</div>