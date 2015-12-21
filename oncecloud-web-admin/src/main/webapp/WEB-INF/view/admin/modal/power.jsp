<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basepath}js/common/jquery.validate.js"></script>
<script src="${basepath}js/admin/modal/power.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div class="modal-content" id="modalcontent" hostid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">服务器电源管理<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item">
						<div class="control-label">服务器UUID</div>
						<div class="controls">
						<input type="hidden" id="power_uuid" name="power_uuid"  >
							<input type="text" id="host_uuid" name="host_uuid" disabled class="oc-disable" >
						</div>
					</div>
					<div class="item">
						<div class="control-label">服务器IP</div>
						<div class="controls">
							<input type="text" id="host_ip" name="host_ip" disabled class="oc-disable" >
						</div>
					</div>
					<div class="item">
						<div class="control-label">服务器主板IP</div>
						<div class="controls">
							<input type="text" id="motherboard_ip" name="motherboard_ip" autofocus="" >
						</div>
					</div>
                    <div class="item">
						<div class="control-label">端口</div>
						<div class="controls">
							<input type="text" id="port" name="port" autofocus="" >
						</div>
					</div>
					<div class="item">
						<div class="control-label">账号</div>
						<div class="controls">
							<input type="text" autofocus="" id="power_username" name="power_username">
						</div>
					</div>
					<div id="pwditem" class="item">
						<div class="control-label">密码</div>
						<div class="controls">
							<input type="password" id="power_pwd" name="power_pwd" autofocus="" >
						</div>
					</div>
					<div class="item">
						<div class="control-label">当前状态</div>
						<div class="controls">
							<input type="hidden" id="powerStatus">
							<input type="text" id="server_status" value="运行中" disabled class="oc-disable">
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button class="btn btn-primary" id="validAction" type="button">验证</button>
			<!-- <button id="startServerAction" type="button" class="btn btn-default">启动服务器</button>
			<button id="closeServerAction" type="button" class="btn btn-danger">关闭服务器</button> -->
			<button class="btn btn-success" id="savePower" type="button">保存</button> 
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>