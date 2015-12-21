<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/router/addtorouter.js"></script>
<input type="hidden" id="bath_url" value="${bathPath}"/>
<div class="modal-dialog" style="margin-top:100px; width:650px">
	<div class="modal-content" id="modalcontent" vnetid="">
		<div class="modal-header">
			<h4 class="modal-title">连接私有网络到路由器<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item" style="margin-bottom:30px">
						<div class="control-label" style="width:150px">路由器</div>
						<div class="controls" style="margin-left:170px">
							<div class="select-con">
								<select class="dropdown-select" id="vn_router" name="vn_router">				
  								</select>
  							</div>
						</div>
					</div>
					<div class="item">
						<div class="control-label" style="width:150px">网络地址</div>
						<div class="controls" style="margin-left:170px">
							<input type="text" style="width:60px" class="oc-disable" value="192" disabled >
							<input type="text" style="width:60px" class="oc-disable" value="168" disabled >
							<input type="text" style="width:60px" id="vn_net" value="1" >
							<input type="text" style="width:60px" class="oc-disable" value="0" disabled >
							<span style="font-size:16px">&nbsp;/&nbsp;24</span>
							<div class="help">
								请为您的私有网络指定一个网络地址。
							</div>
						</div>
					</div>
					<div class="item">
						<div class="control-label" style="width:150px">网关地址</div>
						<div class="controls" style="margin-left:170px">
							<input type="text" style="width:60px" class="oc-disable" value="192" disabled >
							<input type="text" style="width:60px" class="oc-disable" value="168" disabled >
							<input type="text" style="width:60px" class="oc-disable" id="vn_net1" value="1" disabled>
							<input type="text" style="width:60px" id="vn_gate" value="1" >
							<div class="help">
								这个地址可以被用作该私有网络内其他主机的缺省网关。
							</div>
						</div>
						
					</div>	
					<!-- <div class="item">
						<div class="control-label" style="width:150px">DHCP</div>
						<div class="controls" style="margin-left:170px">
							<label class="oc-inline"><input type="radio" name="dhcp_status" checked value="1">&nbsp;打开</label>
							<label class="oc-inline"><input type="radio" name="dhcp_status" value="0">&nbsp;关闭</label>
						</div>
						<div class="controls" id="ip-range" style="margin-left:170px">
							<input type="text" style="width:60px" class="oc-disable" value="192" disabled >
							<input type="text" style="width:60px" class="oc-disable" value="168" disabled >
							<input type="text" style="width:60px" class="oc-disable" id="vn_net2" value="1" disabled>
							<input type="text" style="width:60px" id="vn_start"value="2" >&nbsp;&nbsp;-&nbsp;
							<input type="text" style="width:60px" id="vn_end"value="254" >
							<div class="help">
								请为DHCP服务指定动态地址范围，例如：192.168.*.<起始地址>-<结束地址>
							</div>
						</div>		
					</div>	 -->
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="linkRouter" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>