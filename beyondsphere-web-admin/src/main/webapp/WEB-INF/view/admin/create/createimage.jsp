<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/create/createimage.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">添加映像<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item">
						<div class="control-label">映像UUID</div>
						<div class="controls">
							<input type="text" id="image_uuid" name="image_uuid" autofocus="">
						</div>
					</div>
					<div class="item">
						<div class="control-label">映像名称</div>
						<div class="controls">
							<input type="text" id="image_name" name="image_name" autofocus="">
						</div>
					</div>
					 <div class="item">
						<div class="control-label">资源池</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="image_server" name="image_server">
								</select>
							</div>
						</div>
					</div>
					
					<div class="item">
						<div class="control-label">主机</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="image_host" name="image_host">
									<option>请选择...</option>
								</select>
							</div>
						</div>
					</div>
					
					 <div class="item">
						<div class="control-label">平台</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="image_os" name="image_os">
									<option value="0">Windows</option>
                                    <option value="1">Linux</option>
								</select>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="control-label">映像密码</div>
						<div class="controls">
							<input type="text" id="image_pwd" name="image_pwd" autofocus="">
						</div>
					</div>
                    <div class="item">
						<div class="control-label">映像描述</div>
						<div class="controls">
							<textarea rows="3" style="width:300px" id="image_desc" name="image_desc"></textarea>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="createImageAction" type="button" class="btn btn-primary">添加</button>
        	<button id="closeImageAction" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>