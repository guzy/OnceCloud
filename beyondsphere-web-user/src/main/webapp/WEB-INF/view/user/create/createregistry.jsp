<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/bwizard.css" />
<script src="${basePath}js/common/bwizard.js"></script>
<script src="${basePath}js/user/create/createregistry.js"></script>
<div class="modal-dialog" style="width:1000px;height:auto">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">
				创建仓库<a class="close" data-dismiss="modal" aria-hidden="true"><span
					class="glyphicon glyphicon-remove"></span></a>
			</h4>
		</div>
		<div class="modal-body">
			<div class="row" style="margin:0; background-color:#cfeaf8">
				<div class="col-md-7">
					<div id="wizard" class="wizard">
						<ol style="width:100%">
							<li class="li-disable">选择映像</li>
							<li class="li-disable">基本设置</li>
						</ol>
						<div>
							<div class="wizard-inner">
								<div class="once-toolbar" style="margin:0 0 10px 0">
									<div class="provider">
										模板提供方: 
										 <a href="#" class="provider-filter selected">容器</a>
									</div>
									<div class="toolbar-right">
										<table>
											<tr>
												<td>页数&nbsp;<a id="currentPtpl"></a>&nbsp;/&nbsp;<a
													id="totalPtpl"></a></td>
												<td style="padding-left:10px">
													<div class="pagination-small">
														<ul class="pagination" id="tplpage" style="display:inline"></ul>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</div>
								<div class="imagelist" id="imagelist"></div>
							</div>
							<div class="wizard-action">
								<button class="btn btn-default btn-next" type="button">下一步</button>
							</div>
						</div>
					
						<div>
							<form id="basicinfo-form">
								<div class="wizard-inner">
									<div class="item">
										<div class="control-label">名称</div>
										<div class="controls">
											<input type="text" id="registry_name" name="registry_name">
											<p class="alert alert-info" id="count-alert"
												style="margin:10px 0; width:240px; padding:10px">不能含有非法字符</p>
										</div>
									</div>
									<div class="item">
										<div class="control-label">IP地址</div>
										<div class="controls">
											<input type="text" style="width:57px!important" id="registry_ip_a" name="registry_ip_a">
											<input type="text" style="width:57px!important" id="registry_ip_b" name="registry_ip_b">
											<input type="text" style="width:57px!important" id="registry_ip_c" name="registry_ip_c">
											<input type="text" style="width:57px!important" id="registry_ip_d" name="registry_ip_d">
											<span class="unit"  class="error" id="registry_ip"></span>
											<p class="alert alert-info" id="count-alert"
												style="margin:10px 0; width:240px; padding:10px">类似于255.255.255.255</p>
										</div>
									</div>
									<div class="item">
										<div class="control-label">端口号</div>
										<div class="controls">
											<input type="text" id="registry_port" name="registry_port">
											<p class="alert alert-info" id="count-alert"
												style="margin:10px 0; width:240px; padding:10px">端口号介于0~65535之间</p>
										</div>
									</div>
								</div>
								<div class="wizard-action">
									<button class="btn btn-default btn-back" type="button">上一步</button>
									<button id="createRegistryAction" class="btn btn-primary btn-create"
										type="button">创建</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="col-md-5">
					<div class="illustrate">
						<h4>配置详情</h4>
						<table class="table table-bordered once-table">
							<tbody>
								<tr>
									<td>映像</td>
									<td id="selectedImage"></td>
								</tr>
								<tr>
									<td>主机类型</td>
									<td id="selectedType">小型A</td>
								</tr>
								<tr>
									<td>CPU</td>
									<td id="selectedCore">1&nbsp;核</td>
								</tr>
								<tr>
									<td>内存</td>
									<td id="selectedCap">1&nbsp;G</td>
								</tr>
								<tr>
									<td>名称</td>
									<td id="selectedName"></td>
								</tr>
								<tr>
									<td>数量</td>
									<td id="selectedCount">1</td>
								</tr>
								<tr>
									<td>创建方式</td>
									<td id="selectMode" model="">快照创建</td>
								</tr>
								<tr style="display:none">
									<td width="20%">单价</td>
									<td>0.1<span class="unit">元/小时</span></td>
								</tr>
							</tbody>
						</table>
						<h4 style="display:none">
							总价&nbsp; <span class="price">0.10</span> <span class="unit">元/小时</span>
							<span class="unit">（合<strong>72.00</strong>元/月）
							</span>
						</h4>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
