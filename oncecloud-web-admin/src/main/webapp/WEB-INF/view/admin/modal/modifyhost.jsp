<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basepath}js/common/jquery.validate.js"></script>
<script src="${basepath}js/admin/modal/modifyhost.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div class="modal-content" id="modalcontent" hostid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">连接服务器<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item">
						<div class="control-label">服务器地址</div>
						<div class="controls">
							<input type="text" id="server_addr" name="server_addr" autofocus="">
						</div>
					</div>
					<div class="item">
						<div class="control-label">服务器名称</div>
						<div class="controls">
							<input type="text" id="server_name" name="server_name" autofocus="">
						</div>
					</div>
					<div class="item">
						<div class="control-label">服务器类型</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="server_type" name="server_type">
									<option value="beyondCloud">beyondCloud</option>
									<option value="vSphere">vSphere</option>
									<option value="KVM">KVM</option>
								</select>
							</div>
						</div>
					</div>
                    <div class="item">
						<div class="control-label">服务器描述</div>
						<div class="controls">
							<textarea rows="3" style="width:300px" id="server_desc" name="server_desc"></textarea>
						</div>
					</div>
					<div class="item">
						<div class="control-label">账号</div>
						<div class="controls">
							<input type="text" autofocus="" class="oc-disable" disabled value="root">
						</div>
					</div>
					<div id="pwditem" class="item">
						<div class="control-label">密码</div>
						<div class="controls">
							<input type="password" id="server_pwd" name="server_pwd" autofocus="">
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="addServerAction" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>