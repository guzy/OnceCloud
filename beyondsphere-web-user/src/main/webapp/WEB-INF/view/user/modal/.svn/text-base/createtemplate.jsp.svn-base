<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/bwizard.css" />
<link rel="stylesheet" href="${basePath}css/template.css" />
<script src="${basePath}js/common/bwizard.js"></script>
<script src="${basepath}js/common/jquery.validate.js"></script>
<script src="${basepath}js/user/modal/createtemplate.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px;hight:600px;">
	<div class="modal-content" id="modalcontent" hostid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">发布镜像<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body">
			<div class="row" style="margin:0">
				<div>
					<div id="wizard" class="wizard">
						<ol style="width:100%">
							<li class="li-disable">上传文件</li>
							<li class="li-disable">制作镜像</li>
							<li class="li-disable">发布镜像</li>
						</ol>
						<div>
							<div class="wizard-inner">
								<div class="alert alert-info" style="margin:10px 10px" role="alert">大文件上传，可能需要很长的时间，请耐心等待！</div>
								<form class="form form-horizontal" id="create-form" style="margin:0px 10px">
									<fieldset style="padding-left:50px">
					                    <div class="item">
											<div class="control-label">镜像文件</div>
											<div class="controls" style="padding-top:8px">
												<input type="file" id="upiso" name="upiso" />
											</div>
										</div>
										<div class="item">
											<div class="control-label">集群</div>
											<div class="controls">
												<div class="select-con">
													<select class="dropdown-select" id="pool_select" name="pool_select">
													</select>
												</div>
											</div>
										</div>
										<div id="pid" style="display:none;">
					                    <div style="text-center" id="showId">已经完成0%</div>
					                    <div class="progress" >
										   <div id="progressid" class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
										   </div>
									    </div>
									</div>
									</fieldset>
								</form>
							</div>
							<div class="wizard-action">
								<button id="upfile" type="button" class="btn btn-primary btn-upload">上传</button>
								<button id="up-first" class="btn btn-default btn-next" type="button">下一步</button>
							</div>
						</div>
						<div>
							<div class="wizard-inner">
								<div class="alert alert-info" style="margin:10px 10px" role="alert">制作镜像需要一定时间，请耐心等待，点击制作按钮，开始制作镜像</div>
								<form class="form form-horizontal" id="make-form" style="margin:0px 10px">
									<fieldset style="padding-left:50px">
					                    <div class="item">
											<div class="control-label">镜像名称</div>
											<div class="controls">
												<input type="text" id="templatename" name="templatename" />
											</div>
										</div>
										<div class="item">
											<div class="control-label">版本号</div>
											<div class="controls">
												<input type="text" id="templateversion" name="templateversion" />
											</div>
										</div>
										<div class="item" id="imagestatus" style="display:none;">
											<div id="imagemessage" class="alert alert-warning" style="margin:10px 10px" role="alert"><b>镜像制作中，请耐心等待...</b></div>
										</div>
									</fieldset>
								</form>
							</div>
							<div class="wizard-action">
								<button class="btn btn-default btn-back" type="button">上一步</button>
								<button class="btn btn-primary btn-create" type="button">制作</button>
								<button class="btn btn-default btn-next" type="button">下一步</button>
							</div>
						</div>
						<div>
							<div class="wizard-inner">
								<div class="alert alert-info" style="margin:10px 10px" role="alert">制作镜像需要一定时间，请耐心等待，点击发布按钮，开始发布镜像</div>
								<input id="templateuuid" type="hidden" value="">
								<div class="item" id="pushstatus" style="display:none;">
									<div id="pushmessage" class="alert alert-warning" style="margin:10px 10px" role="alert"><b>镜像发布中，请耐心等待...</b></div>
								</div>
							</div>
							<div class="wizard-action">
								<button class="btn btn-default btn-back" type="button">上一步</button>
								<button id="publishDoc" class="btn btn-primary btn-push" type="button">发布</button>
								<button id="finished" class="btn btn-success btn-push" type="button">完成</button> 
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>