<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/create/createpool.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">

	<div class="modal-content" id="modalcontent" poolid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">新建资源池<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div  class="item" >
						<div class="control-label">资源池名称</div><br>
						
						<div  class="controls">
							<input type="text" id="pool_name" name="pool_name"  autofocus="">
						</div>
					</div><br>
					
					<div class="item">
						<div class="control-label">资源池类型</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="pool_type" name="pool_type">
									<option value="beyondCloud">beyondCloud</option>
									<option value="beyondDocker">beyondDocker</option>
									<option value="vSphere">vSphere</option>
									<option value="KVM">KVM</option>
								</select>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="control-label">数据中心</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="dcselect" name="dcselect">
								
								</select>
							</div>
						</div>
					</div>
                    <div class="item">
						<div class="control-label">资源池描述</div>
						<div class="controls">
							<textarea rows="5" style="width:300px" id="pool_desc" name="pool_desc"></textarea>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="createPoolAction" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>