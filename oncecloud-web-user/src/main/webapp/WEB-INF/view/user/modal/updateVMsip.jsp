<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/user/instance/updatevms.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:600px">
	<div id="modalcontent" class="modal-content" instanceUuid="${modifyUuids}">
		<div class="modal-header">
			<h4 id="modaltitle" class="modal-title">批量绑定VM的ip地址<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<div class="item">
					<div class="control-label">IP地址：</div>
					<div class="controls" id="modify_ip">
							<input type="text" style="width:60px!important" value="192" maxlength="3" onkeypress="return IsNum(event)"  >
							<input type="text" style="width:60px!important" value="168" maxlength="3" onkeypress="return IsNum(event)" >
							<input type="text" style="width:60px!important" value="1" maxlength="3" onkeypress="return IsNum(event)" >
							<input type="text" style="width:60px!important" value="2" maxlength="3" onkeypress="return IsNum(event)" id="ip_end1">
							<span style="font-size:16px">&nbsp;--&nbsp;</span>
							<input type="text" style="width:60px!important" value="" maxlength="3" onkeypress="return IsNum(event)" id="ip_end2"  class="oc-disable" disabled>
							<div class="help">
								请为您的私有网络指定一个网络地址。(必填)
							</div>
					</div>
				</div>
				<div class="item">
					<div class="control-label">子网掩码：</div>
					<div class="controls" id="modify_netmask">
						<input type="text" style="width:60px!important" class="oc-disable" value="255" maxlength="3" onkeypress="return IsNum(event)" disabled>
						<input type="text" style="width:60px!important" class="oc-disable" value="255" maxlength="3" onkeypress="return IsNum(event)" disabled>
						<input type="text" style="width:60px!important" value="255" maxlength="3" onkeypress="return IsNum(event)" >
						<input type="text" style="width:60px!important" value="0" maxlength="3" onkeypress="return IsNum(event)" >
						<div class="help">
							请为您的私有网络指定一个子网掩码。(必填)
						</div>
					</div>
				</div>
				<div class="item">
					<div class="control-label">网关地址：</div>
					<div class="controls" id="modify_gateway">
						<input type="text" style="width:60px!important" value="" maxlength="3" onkeypress="return IsNum(event)" >
						<input type="text" style="width:60px!important" value="" maxlength="3" onkeypress="return IsNum(event)" >
						<input type="text" style="width:60px!important" value="" maxlength="3" onkeypress="return IsNum(event)" >
						<input type="text" style="width:60px!important" value="" maxlength="3" onkeypress="return IsNum(event)" >
						<div class="help">
							请为您的私有网络指定一个网关地址。(选填)
						</div>
					</div>
				</div>
				<div class="item">
					<div class="control-label">DNS服务器：</div>
					<div class="controls" id="modify_dns">
						<input type="text" style="width:60px!important" value="" maxlength="3" onkeypress="return IsNum(event)" >
						<input type="text" style="width:60px!important" value="" maxlength="3" onkeypress="return IsNum(event)" >
						<input type="text" style="width:60px!important" value="" maxlength="3" onkeypress="return IsNum(event)" >
						<input type="text" style="width:60px!important" value="" maxlength="3" onkeypress="return IsNum(event)" >
						<div class="help">
							请为您的私有网络指定一个DNS地址。(选填)
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer" style="margin-top:0px;">
			<button id="confirm" type="button" class="btn btn-primary" >确定</button>
        	<button type="button" class="btn btn-default btn-cancle" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>
