<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/user/modal/mountVmip.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:400px">
	<div id="modalcontent" userid="" class="modal-content" instanceUuid="${modifyUuid}">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">绑定VM的ip地址<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<p id="logconfig-describition" style="margin-left:10px; font-size:14px; text-indent: 28px; line-height:23px;">
					绑定或者更换VM的ip地址
				</p>
				<div class="item" id="mswitchid">
					<div class="control-label">IP地址：</div>
					<div class="controls" id="modify_IP">
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">&nbsp;.
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">&nbsp;.
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">&nbsp;.
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">
					</div>
				</div>
				<div class="item">
					<div class="control-label">子网掩码：</div>
					<div class="controls" id="modify_netmask">
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)" value="255" disabled>&nbsp;.
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)" value="255" disabled>&nbsp;.
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)" value="0">&nbsp;.
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)" value="0">
					</div>
				</div>
				<div class="item">
					<div class="control-label">网关地址：</div>
					<div class="controls" id="modify_gateway">
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">&nbsp;.
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">&nbsp;.
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">&nbsp;.
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">
					</div>
				</div>
				<div class="item">
					<div class="control-label">DNS服务器：</div>
					<div class="controls" id="modify_dns">
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">&nbsp;:
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">&nbsp;:
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">&nbsp;:
						<input type="text" autofocus="" style="width:45px;height:25px;" maxlength="3" onkeypress="return IsNum(event)">
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
