<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/bwizard.css" />
<script src="${basePath}js/common/bwizard.js"></script>
<script src="${basePath}js/user/create/createinstance.js"></script>
<div class="modal-dialog" style="width:1000px;height:auto">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">
				创建新主机<a class="close" data-dismiss="modal" aria-hidden="true"><span
					class="glyphicon glyphicon-remove"></span></a>
			</h4>
		</div>
		<div class="modal-body">
			<div class="row" style="margin:0; background-color:#cfeaf8">
				<div class="col-md-7">
					<div id="wizard" class="wizard">
						<ol style="width:100%">
							<li class="li-disable">选择映像</li>
							<li class="li-disable">选择配置</li>
							<li class="li-disable">基本设置</li>
						</ol>
						<div>
							<div class="wizard-inner">
								<div class="once-toolbar" style="margin:0 0 10px 0">
									<div class="provider">
										模板提供方: <a href="#" class="provider-filter selected"
											type="system">系统</a> <a href="#" class="provider-filter"
											type="user">自有</a>
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
							<div class="wizard-inner">
								<h5>推荐类型</h5>
								<div class="types">
									<div class="types-item selected">
										<div class="inner">
											<a><span class="glyphicon glyphicon-ok"></span></a>
										</div>
										<h6 class="type-name">小型A</h6>
									</div>
									<div class="types-item">
										<div class="inner">
											<a><span class="glyphicon glyphicon-ok"></span></a>
										</div>
										<h6 class="type-name">小型B</h6>
									</div>
									<div class="types-item">
										<div class="inner">
											<a><span class="glyphicon glyphicon-ok"></span></a>
										</div>
										<h6 class="type-name">中型A</h6>
									</div>
									<div class="types-item">
										<div class="inner">
											<a><span class="glyphicon glyphicon-ok"></span></a>
										</div>
										<h6 class="type-name">中型B</h6>
									</div>
									<div class="types-item">
										<div class="inner">
											<a><span class="glyphicon glyphicon-ok"></span></a>
										</div>
										<h6 class="type-name">大型A</h6>
									</div>
									<div class="types-item">
										<div class="inner">
											<a><span class="glyphicon glyphicon-ok"></span></a>
										</div>
										<h6 class="type-name">大型B</h6>
									</div>
								</div>
								<h6>CPU</h6>
								<div class="cpu options">
									<div class="types-options cpu-options selected" core="1">
										1核</div>
									<div class="types-options cpu-options " core="2">2核</div>
									<div class="types-options cpu-options " core="4">4核</div>
									<div class="types-options cpu-options " core="8">8核</div>
									<div class="types-options cpu-options " core="16">16核</div>
								</div>
								<h6>内存</h6>
								<div class="memory options">
									<div class="types-options memory-options" capacity="0.5">
										512MB</div>
									<div class="types-options memory-options selected" capacity="1">
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
									<div class="types-options memory-options" capacity="16">
										16G</div>
									<div class="types-options memory-options" capacity="24">
										24G</div>
									<div class="types-options memory-options" capacity="32">
										32G</div>
								</div>
							</div>
							<div class="wizard-action">
								<button class="btn btn-default btn-back" type="button">上一步</button>
								<button class="btn btn-default btn-next" type="button">下一步</button>
							</div>
						</div>
						<div>
							<form id="basicinfo-form">
								<div class="wizard-inner">
									<div class="item">
										<div class="control-label">主机名称</div>
										<div class="controls">
											<input type="text" id="instance_name" name="instance_name"
												value="">
										</div>
									</div>
									<div class="item">
										<div class="control-label">主机数量</div>
										<div class="controls">
											<input type="text" id="count" name="count" value="1">
											<p class="alert alert-info" id="count-alert"
												style="margin:10px 0; width:240px; padding:10px">建议主机数量为1，输入多个主机时，会增加主机创建失败的几率。</p>
										</div>
									</div>
									<div class="item">
										<div class="control-label">创建方式</div>
										<div class="controls">
											<div style="padding-top:10px">
												<label class="checkbox-inline">
											      <input class="create-model" type="radio" name="createmodel" id="snap" 
											         value="1" checked> 快照创建
											    </label>
											    <label class="checkbox-inline">
											      <input class="create-model" type="radio" name="createmodel" id="clone" 
											         value="2"> 克隆创建
											    </label>
											    <p class="alert alert-info" id="model-alert"
												style="margin:10px 0; width:240px; padding:10px">建议快照创建，克隆创建花费的时间比较长，大概在几分钟。</p>
											</div>
										</div>
									</div>
									<div class="item keypair">
										<div class="control-label">用户名</div>
										<div class="controls">
											<input type="disable" id="vmDefaultUser" name="">
										</div>
									</div>
									<div class="item passwd" style="">
										<div class="control-label">密码</div>
										<div class="controls">
											<input type="password" id="login_passwd" value=""
												name="login_passwd"> <label class="inline">
												<input type="checkbox" id="display-pwd" name="toggle_passwd">
												&nbsp;显示密码
											</label>
											<p class="alert alert-info" id="pw-alert"
												style="margin:10px 0; width:240px; padding:10px">密码至少8位，包括大小写字母和数字。</p>
										</div>
									</div>
								</div>
								<div class="wizard-action">
									<button class="btn btn-default btn-back" type="button">上一步</button>
									<button id="createvmAction" class="btn btn-primary btn-create"
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
