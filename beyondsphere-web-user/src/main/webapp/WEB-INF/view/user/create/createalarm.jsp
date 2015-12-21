<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/bwizard.css" />
<script src="${basePath}js/common/bwizard.js"></script>
<script src="${basePath}js/user/create/createalarm.js"></script>
<div class="modal-dialog" style="width:580px; margin-top:100px">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">
				创建警告策略<a class="close" data-dismiss="modal" aria-hidden="true"><span
					class="glyphicon glyphicon-remove"></span> </a>
			</h4>
		</div>
		<div class="modal-body">
			<div class="row" style="margin:0">
				<div class="col-md-12">
					<div id="wizard" class="wizard">
						<ol>
							<li class="li-disable">参数设置</li>
							<li class="li-disable">警告规则</li>
						</ol>
						<div>
							<form id="paramsetting-form">
								<div class="wizard-inner">
									<div class="item">
										<div class="control-label">名称</div>
										<div class="controls">
											<input type="text" id="alarm_name" name="alarm_name" value="">
										</div>
									</div>
								</div>
								<div class="wizard-action">
									<button class="btn btn-default btn-next first-next" type="button">下一步</button>
								</div>
							</form>
						</div>
						<div>
							<form id ="rulessetting-form">
							<div class="wizard-inner">
								<div class="item">
									<div class="alarm-rules">
										<ul class="rules" id="rules">
											<li class="rule">
												<div class="select-con">
													<select class="dropdown-select rule-meter" name="meter"
														id="meter">
														<option value='0'>CPU利用率</option>
														<option value='1'>内存利用率</option>
														<option value='2'>磁盘使用率</option>
														<option value='3'>网卡进流量</option>
														<option value='4'>网卡出流量</option>
													</select>
												</div>
												<div class="select-con">
													<select class="dropdown-select rule-condition-type" name="condition_type" id="condition_type">
														<option value="0">&lt;</option>
														<option value="1">&gt;</option>
													</select>
												</div> <input class="short-input" type="text" name="count" id="count" value="70" >
												<label class="alarm-unit" for="thresholds" id="alarm-unit">%</label>
												<a href="#" id="remove-rule" style="display:none;">
													<span class="glyphicon glyphicon-remove delete-rule"></span> 
												</a>
											</li>
										</ul>
										<a class="btn btn-default" id="add-rule" type="button"> 
											<span class="glyphicon glyphicon-plus"></span> 
											<span class="text">添加规则</span>
										</a>
									</div>
								</div>
							</div>
							 
							</form>
							<div class="wizard-action">
								<button class="btn btn-default btn-back" type="button">上一步</button>
								<button id="createAlarmAction" class="btn btn-primary btn-create"
										type="button">创建</button>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
