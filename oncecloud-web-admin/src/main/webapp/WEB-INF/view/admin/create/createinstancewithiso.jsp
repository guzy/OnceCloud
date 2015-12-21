<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/bwizard.css" />
<script src="${basePath}js/common/bwizard.js"></script>
<script src="${basePath}js/admin/create/createinstancewithiso.js"></script>
<div class="modal-dialog" style="width: 1000px">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">
				创建新主机<a class="close" data-dismiss="modal" aria-hidden="true"><span
					class="glyphicon glyphicon-remove"></span></a>
			</h4>
		</div>
		<div class="modal-body">
			<div class="row" style="margin: 0; background-color: #cfeaf8">
				<div class="col-md-9" style="width:60%; padding:0px">
					<div id="wizard" class="wizard">
						<ol>
							<li class="li-disable">基本信息</li>
							<li class="li-disable">安装文件</li>
							<li class="li-disable">CPU和内存</li>
							<li class="li-disable">硬盘设置</li>
						</ol>
						<div>
							<div class="wizard-inner">
								<form id="basicinfo-form">
									<div class="item">
										<div class="control-label">主机名称</div>
										<div class="controls">
											<input type="text" id="instance_name" name="instance_name"
												value="">
										</div>
									</div>
								</form>
								<div class="hty-div">
									<div class="hty-label">
										<div class="provider">选择资源池</div>
									</div>
									<div class="poollist" id="poollist"></div>
								</div>
							</div>
							<div class="wizard-action">
								<button class="btn btn-default btn-first-next" type="button">下一步</button>
							</div>
						</div>
						<div>
							<div class="wizard-inner">
								<div class="hty-div">
									<div class="hty-label">
										<div class="provider">选择镜像文件</div>
										<div style="padding-left:120px"><img src="img/loading.gif" id="loadinggif" class="hty-loadpic"></div>
										<div class="hty-select-con" id="select-iso">
											<select name="isoselect" class="hty-dropdown" id="isoselect">
											</select>
										</div>
									</div>
									<div class="hty-label">
										<div class="provider">选择镜像类型</div>
										<div class="hty-select-con" id="iso-type">
											<select name="isotypeselect" class="hty-dropdown" id="isotypeselect">
												<option value="1">Linux</option>
												<option value="0">windows</option>
f											</select>
										</div>
									</div>
								</div>
							</div>
							<div class="wizard-action">
								<button class="btn btn-default btn-back" type="button">上一步</button>
								<button class="btn btn-default btn-second-next" type="button">下一步</button>
							</div>
						</div>
					<div>
							<div class="wizard-inner" style="margin-left:100px">
								<h6>CPU</h6>
								<div class="cpu options">
									<div class="types-options cpu-options selected" core="1">
										1核</div>
									<div class="types-options cpu-options " core="2">2核</div>
									<div class="types-options cpu-options " core="4">4核</div>
								</div>
								<h6  style="margin-top:50px">内存</h6>
								<div class="memory options">
									<div class="types-options memory-options selected" style="line-height: 32px;" capacity="1">
										1G</div>
									<div class="types-options memory-options" capacity="2">
										2G</div>
									<div class="types-options memory-options" capacity="4">
										4G</div>
									<div class="types-options memory-options" capacity="6">
										6G</div>
									<div class="types-options memory-options" capacity="8">
										8G</div>
									<div class="types-options memory-options" capacity="12">
										12G</div>
								</div>
							</div>
							<div class="wizard-action">
								<button class="btn btn-default btn-back" type="button">上一步</button>
								<button class="btn btn-default btn-next" type="button">下一步</button>
							</div>
						</div>
						<div>
							<div class="wizard-inner">
								<div class="hty-div">
									<div class="hty-label">
										<div class="provider">选择硬盘</div>
									</div>
									<div style="padding-left:120px"><img src="img/loading.gif" id="loadinggifdisk" class="hty-loadpic"></div>
									<div class="volumelist" id="volumelist"></div>
									<div class="hty-label">
											<div class="provider">容量</div>
									</div>
									<div class="controls size" style="margin-left:10px">
										<div id="slider" style="width: 319px; display: inline-block"></div>
										<input id="size" type="text" class="mini" value="10"
											style="margin-left: 10px"> <span class="help inline">GB</span>
										<br />
										<span class="help" style="margin-left:-10px">10GB</span>
										<span class="help" style="margin-left:235px">200GB</span>
									</div>
								</div>
							</div>
							<div class="wizard-action">
								<button class="btn btn-default btn-back" type="button">上一步</button>
								<button id="createvmAction" class="btn btn-primary btn-create"
									type="button">创建</button>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-3" style="width:35%">
					<div class="illustrate">
						<h4>配置详情(制作模板请安装agent)</h4>
						<table class="table table-bordered once-table" style="width:350px">
							<tbody>
								<tr>
									<td>主机名称</td>
									<td id="htyinstance"></td>
								</tr>
								<tr>
									<td>资源池</td>
									<td id="htypool" pooluuid=""></td>
								</tr>
								<tr>
									<td>镜像文件</td>
									<td id="htyiso" isouuid=""></td>
								</tr>
								<tr>
									<td>镜像类型</td>
									<td id="isotype" typevlaue="1">Linux</td>
								</tr>
								<tr>
									<td>CPU</td>
									<td id="htycore" cpu="1">1&nbsp;核</td>
								</tr>
								<tr>
									<td>内存</td>
									<td id="htycap" cap="1">1&nbsp;G</td>
								</tr>
								<tr>
									<td>挂载存储</td>
									<td id="htystorage" diskuuid=""></td>
								</tr>
								<tr>
									<td>硬盘大小</td>
									<td id="htyvolum" volum="10">10G</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
