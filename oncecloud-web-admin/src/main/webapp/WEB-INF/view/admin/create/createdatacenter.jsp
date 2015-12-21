<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/create/createdatacenter.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div class="modal-content" id="modalcontent" dcid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">创建数据中心<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item">
						<div class="control-label">名称</div>
						<div class="controls">
							<input type="text" id="dc_name" name="dc_name" autofocus="">
						</div>
					</div>
					<div class="item">
						<div class="control-label">位置</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="dc_location" name="dc_location" style="padding:6px 38px 6px 20px">
									<option value="苏州">苏州</option>
                                    <option value="北京">北京</option>
								</select>
							</div>
						</div>
					</div>
                    <div class="item">
						<div class="control-label">描述</div>
						<div class="controls">
							<textarea rows="5" style="width:300px" id="dc_desc" name="dc_desc"></textarea>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="createDCAction" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>