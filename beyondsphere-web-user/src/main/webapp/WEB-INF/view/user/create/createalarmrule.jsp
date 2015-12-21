<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/user/create/createalarmrule.js"></script>
<div class="modal-dialog" style="margin-top:100px" id="modal-type" alatype="${alarmType}">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">添加警告策略规则<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body">
			<form class="form form-horizontal" id="createrule-form">
				<fieldset>
					<div class="item">
						<div class="control-label">监控项</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select rule-meter" id="rule_name" name="rule_name">
										<option value='0'>CPU利用率</option>
										<option value='1'>内存利用率</option>
										<option value='2'>磁盘使用率</option>
										<option value='3'>网卡进流量</option>
										<option value='4'>网卡出流量</option>
								</select>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="control-label">条件</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="rule_term" name="rule_term">
									<option value="0">&lt;</option>
									<option value="1">&gt;</option>
								</select>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="control-label">阈值</div>
						<div class="controls">
							<input type="text" id="rule_threshold" name="rule_threshold" style="width: 70px !important">
							<label class="alarm-unit" for="rule_threshold" id="alarm-unit">%</label>
						</div>
					</div>
					<!-- <div class="item">
						<div class="control-label">触发条件</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="rule_condition" name="rule_condition">
									<option value="once">满足条件一次</option>
									<option value="multiple">连续多个周期满足条件</option>
								</select>
							</div>
						</div>
					</div> -->
					<!-- <div class="item" id="rperiod" style="display:none">
						<div class="control-label">连续周期数</div>
						<div class="controls">
							<input type="text" id="rule_period" name="rule_period" value="1" style="width: 70px !important">
						</div>
					</div> -->
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="createruleAction" type="button" class="btn btn-primary" style="margin-left:-100px">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>