<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/bwizard.css" />
<link rel="stylesheet" href="${basePath}css/host.css" />
<script src="${basePath}js/common/bwizard.js"></script>
<script src="${basepath}js/common/jquery.validate.js"></script>
<script src="${basepath}js/user/create/createhost.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div class="modal-content" id="modalcontent" hostid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">连接服务器<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
		    <div class="row" style="margin: 0; background-color: #cfeaf8">
		    	<div id="wizard" class="wizard">
		    		<ol id="container">
						<li class="li-disable">服务器配置</li>
						<li class="li-disable" >电源管理</li>
					</ol>
					
					<div>
						<div class="wizard-inner">
							<form class="form form-horizontal" id="server-form">
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
													<!-- <option value="beyondCloud">beyondCloud</option> -->
													<option value="beyondDocker">beyondDocker</option>
													<!-- <option value="vSphere">vSphere</option>
													<option value="KVM">KVM</option> -->
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
						<div class="wizard-action">
							<button id="btn_next" class="btn btn-default btn-next" type="button" >下一步</button>
							<button id="btn_submit" type="button" class="btn btn-primary btn-submit">提交</button>
							<button id="btn_cancle" type="button" class="btn btn-default btn-cancle" data-dismiss="modal">取消</button>
						</div>
					</div>
					
					<div>
						<div class="wizard-inner">
							<form class="form form-horizontal" id="power-form">
								<fieldset>
									<div class="item" id = "hostItem" style="display:none">
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
											<input type="hidden" id = "power_status" value="-1">
											<input type="text" id="server_status" value="运行中" disabled class="oc-disable">
										</div>
									</div>
								</fieldset>
							</form>	
						</div>
						<div class="wizard-action">
							<button class="btn btn-default btn-back" type="button">上一步</button>
							<button id="addServerAction" type="button" class="btn btn-primary btn-submit">提交</button>
							<button type="button" class="btn btn-default btn-cancle" data-dismiss="modal">取消</button>
							<button id="powercheck" type="button" class="btn btn-danger btn-power">电源校验</button>
						</div>
					</div>
					
		    	</div>
		    </div>
		</div>
	</div>
</div>