<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/instance2router.css" />
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/instance/add2Router.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:650px">
	<div class="modal-content" id="modalvmid" vmid="">
		<div class="modal-header">
			<h4 class="modal-title">虚拟主机连接到路由器<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<div class="row" style="margin:0; background-color:#cfeaf8">
				<div id="wizard" class="wizard">
					<ol style="width:100%" class="bwizard-steps clearfix" role="tablist">
							<li class="li-disable active" role="tab" style="z-index: 3;" aria-selected="true"><span class="label badge-success">1</span><a class="hidden-phone" href="javascript:void(0)" style="cursor: default;">选择Vxlan</a></li>
							<li class="li-disable" role="tab" style="z-index: 1;" aria-selected="true"><span class="label">2</span><a class="hidden-phone" href="javascript:void(0)" style="cursor: default;">网络设置</a></li>
					</ol>
					<div>
						<div class="wizard-inner">
							<div class="item" style="margin-bottom:30px">
								<div class="control-label" style="width:150px">路由器：</div>
								<div class="controls" style="margin-left:170px">
									<div class="select-con">
										<select class="dropdown-select" id="vn_router" name="vn_router">				
  										</select>
  									</div>
								</div>
							</div>
							<div class="once-toolbar" style="margin:0 0 10px 0">
								<div class="provider">
									请选择Vxlan：
								</div>
							</div>
							<div id="vxlanlist" class="imagelist"></div>
						</div>
						<div class="wizard-action">
							<button class="btn btn-default btn-next" type="button" id="one_next">下一步</button>
						</div>
					</div>
					<div>
						<form id="create-form" class="form form-horizontal">
							<div class="wizard-inner">
								<div class="item">
									<div class="control-label" style="width:150px">网络地址</div>
									<div class="controls" style="margin-left:170px">
										<input type="text" style="width:60px!important" class="oc-disable" value="192" disabled >
										<input type="text" style="width:60px!important" class="oc-disable" value="168" disabled >
										<input type="text" style="width:60px!important" class="oc-disable" name="net_val" disabled>
										<input type="text" style="width:60px!important" id="ip_end" value="2">
										<div class="help">
											请为您的虚拟主机指定一个网络地址。
										</div>
									</div>
								</div>
								<div class="item">
									<div class="control-label" style="width:150px">网关地址</div>
									<div class="controls" style="margin-left:170px">
										<input type="text" style="width:60px!important" class="oc-disable" value="192" disabled >
										<input type="text" style="width:60px!important" class="oc-disable" value="168" disabled >
										<input type="text" style="width:60px!important" class="oc-disable" name="net_val" disabled>
										<input type="text" style="width:60px!important" class="oc-disable" name="gate_val" disabled>
										<div class="help">
											这个地址可以被用作该虚拟主机的网关。
										</div>
									</div>
								</div>	
								<div class="item">
									<div class="control-label" style="width:150px">子网掩码</div>
									<div class="controls" style="margin-left:170px">
										<input type="text" style="width:60px!important" class="oc-disable" value="255" disabled >
										<input type="text" style="width:60px!important" class="oc-disable" value="255" disabled >
										<input type="text" style="width:60px!important" class="oc-disable" value="255" disabled>
										<input type="text" style="width:60px!important" id="mask_end" value="0" >
										<div class="help">
											这个地址被用作该虚拟主机的子网掩码。
										</div>
									</div>
								</div>	
							</div>
							<div class="wizard-action">
								<button id="two_up" class="btn btn-default btn-back" type="button">上一步</button>
								<button id="insaddRouter" class="btn btn-primary btn-create"
									type="button">提交</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>