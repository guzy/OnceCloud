<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/uuid.js"></script>
<script src="${basePath}js/admin/modal/imageshare.js"></script>
<link rel="stylesheet" href="${basePath}css/alarm.css" />
<div class="modal-dialog" style="margin-top:100px">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">
				共享镜像<a class="close" data-dismiss="modal" aria-hidden="true"><span
					class="glyphicon glyphicon-remove"></span> </a>
			</h4>
		</div>
		<div class="modal-body">
			<form class="form form-horizontal" id="modify-form">
				<fieldset>
					<div class="item">
						<div class="alarm-price-info">
							<div class="unit-price alert alert-info" style="">当存储完全相同并且不存在已选模板时，才能共享该模板</div>
						</div>
						<div style="padding-left:150px">
							<div class="control-label">可选择的资源池</div>
							<div class="controls">
								<div class="select-con">
									<select class="dropdown-select" name="htypool" id="htypool">
									</select>
								</div>
							</div>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="poolshareimage" type="button" class="btn btn-primary">提交</button>
			<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		</div>
	</div>
</div>