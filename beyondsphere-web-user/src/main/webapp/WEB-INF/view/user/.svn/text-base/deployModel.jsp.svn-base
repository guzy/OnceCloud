<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="modal-dialog" style="width: 1000px; height: auto">
	<script src="${basePath}assets/js/cloudeploy/app/app-main.js"></script>
	<script src="${basePath}assets/js/cloudeploy/app/app-orchestration.js"></script>

	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close editCancel" data-dismiss="modal">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">配置容器参数</h4>
			</div>
			<form id="deployDockerValidate">
				<div class="wizard-inner">
					<div class="item">
						<div class="control-label">主机名称</div>
						<div class="controls">
							<input type="text" id="instance_name" name="instance_name"
								value="">
						</div>
					</div>
					<div class="item">
						<div class="control-label">启动命令</div>
						<div class="controls">
							<input type="text" id="start_command" name="start_command"
								value="/bin/bash">
							<p class="alert alert-info" id="count-alert"
								style="margin: 10px 0; width: 240px; padding: 10px">/bin/bash必备，空格添加后可选命令</p>
						</div>
					</div>
					<div class="item">
						<div class="control-label">link</div>
						<div class="controls">
							<input type="text" id="link" name="link" value="">
							<p class="alert alert-info" id="count-alert"
								style="margin: 10px 0; width: 240px; padding: 10px">参照133.133.134.169:5000/henry2:4.0</p>
						</div>
					</div>
					<div class="item">
						<div class="control-label">参数</div>
						<div class="controls">
							<input type="text" id="docker_params" name="docker_params"
								value="">
							<p class="alert alert-info" id="count-alert"
								style="margin: 10px 0; width: 240px; padding: 10px">参照--name=xx
								-t -i</p>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default editCancel"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary editSave">保存</button>
					<button type="button" class="btn btn-primary btn-create"
						id="createDocker">创建</button>
				</div>
			</form>
			<!-- <div class="modal-body">
				<label for="hostName" class="control-label">选择集群:</label>
				<div id="hostNames">
					<ul></ul>
				</div>
				<form class="form-horizontal" role="form" style="margin-top: 15px;">
					<div class="form-group col-sm-6">
						<label for="containerName" class="control-label pull-left"
							style="padding-right: 1em;">容器名称:</label>
						<div class="col-sm-8">
							<input id="containerName" name="containerName"
								class="form-control">
						</div>
					</div>
					<div class="form-group col-sm-6">
						<label for="mountDirectory" class="control-label pull-left"
							style="padding-right: 1em;">挂载目录:</label>
						<div class="col-sm-8">
							<input id="mountDirectory" name="mountDirectory"
								class="form-control">
						</div>
					</div>
					<div class="form-group col-sm-6">
						<label for="expansion" class="control-label pull-left"
							style="padding-right: 1em;">外部扩展:</label>
						<div class="col-sm-8">
							<input id="expansion" name="expansion" class="form-control">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default editCancel"
					data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary editSave">保存</button>
			</div> -->
		</div>
	</div>
</div>