<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/user/create/createcontainertemplate.js"></script>
<div class="modal-dialog" id="modal-dialog" style="margin-top:100px; width:550px" rsid="${rsid}">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">发布<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
				<div class="item">
						<div class="control-label">映像地址</div>
						<div class="controls">
							<input type="text" id="template_image_address" name="template_image_address" autofocus="">
						</div>
					</div>
					<div class="item">
						<div class="control-label">映像名称</div>
						<div class="controls">
							<input type="text" id="template_image_name" name="template_image_name" autofocus="">
						</div>
					</div>
                    
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="cloneImageAction2" type="button" class="btn btn-primary" >确定</button>
        	<button id="cancelAction" type="button" class="btn btn-default">取消</button>
      	</div>
	</div>
</div>