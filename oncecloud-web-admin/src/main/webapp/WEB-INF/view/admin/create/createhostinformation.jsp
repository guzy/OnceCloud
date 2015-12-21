<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/create/createhostinformation.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:650px">
	<div class="modal-content" id="modalcontent" vnetid="">
		<div class="modal-header">
			<h4 class="modal-title">录入主机信息<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item" style="margin-bottom:30px">
						<div class="control-label" style="width:150px">选择用户</div>
						<div class="controls" style="margin-left:170px">
							<div class="select-con">
								<select class="dropdown-select" id="vn_user" name="vn_router">				
  								</select>
  							</div>
						</div>
					</div>
					
					<div class="item" style="margin-bottom:30px">
						<div class="control-label" style="width:150px">选择Vlan</div>
						<div class="controls" style="margin-left:170px">
							<div class="select-con">
								<select class="dropdown-select" id="vn_vlan" name="vn_router">				
  								</select>
  							</div>
						</div>
					</div>
					
					<div class="item">
						<div class="control-label" style="width:150px">IP段</div>
						<div class="controls" id="ip-range" style="margin-left:170px">
							<input type="text" style="width:60px" id="vn_1net">
							<input type="text" style="width:60px" id="vn_2net">
							<input type="text" style="width:60px" id="vn_3net">
							<input type="text" style="width:60px" id="vn_start" value="2" >&nbsp;&nbsp;-&nbsp;
							<input type="text" style="width:60px" id="vn_end" value="254" >
							<div class="help">
								请为连入路由的主机设置ip段，例如：192.168.*.<起始地址>-<结束地址>
							</div>
						</div>		
					</div>	
					
					<div class="item">
						<div class="control-label" style="width:150px">路由IP地址</div>
						<div class="controls" style="margin-left:170px">
							<input type="text" style="width:60px" id="vn_1net1" class="oc-disable" disabled>
							<input type="text" style="width:60px" id="vn_2net1" class="oc-disable" disabled>
							<input type="text" style="width:60px" id="vn_3net1" class="oc-disable" disabled>
							<input type="text" style="width:60px" value="1" id="ip_end">
							<div class="help">
								请为您的路由器指定一个网络地址。
							</div>
						</div>
					</div>
					
					<div class="item">
						<div class="control-label" style="width:150px">路由子网掩码</div>
						<div class="controls" style="margin-left:170px">
							<input type="text" style="width:60px" class="oc-disable" value="255" disabled >
							<input type="text" style="width:60px" class="oc-disable" value="255" disabled >
							<input type="text" style="width:60px" class="oc-disable" value="255" disabled>
							<input type="text" style="width:60px" id="vn_gate" value="0" >
							<div class="help">
								请为您的路由器指定一个子网掩码。
							</div>
						</div>
					</div>	
					
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="createInformation" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>