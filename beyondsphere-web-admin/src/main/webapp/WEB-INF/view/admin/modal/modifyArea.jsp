<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/create/createarea.js"></script>
<div class="modal-dialog" style="margin-top: 100px; width: 550px">
	<div id="modalcontent" userid="" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">
				区域信息修改<a class="close" data-dismiss="modal" aria-hidden="true"><span
					class="glyphicon glyphicon-remove"></span></a>
			</h4>
		</div>
		<div class="modal-body" style="padding: 10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>

					<div class="item">
						<div class="control-label">ID</div>
						<div class="controls">
							<input type="text" id="area_id" name="update_area_id"
								disabled="disabled" autofocus="">
						</div>
					</div>

					<div class="item">
						<div class="control-label">区域名</div>
						<div class="controls">
							<input type="text" id="area_name" name="update_area_name"
								 autofocus="">
						</div>
					</div>

					<div id="pwd">
						<div class="item">
							<div class="control-label">区域域名</div>
							<div class="controls">
								<input type="text" id="area_domain"
									name="update_area_domain" autofocus="">
							</div>
						</div>
						<div class="item">
							<div class="control-label">描述</div>
							<div class="controls">
								<input type="text" id="area_desc" name="update_area_desc"
									autofocus="">
							</div>
						</div>
					</div>

				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="modifyUapdateArea" type="button" class="btn btn-primary">提交</button>
			<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		</div>
	</div>
</div>
