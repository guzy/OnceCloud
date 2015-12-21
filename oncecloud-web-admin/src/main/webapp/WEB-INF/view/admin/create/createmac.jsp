<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/create/createmac.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:400px">
	<div id="modalcontent" userid="" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">新建网卡<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<div class="alarm-price-info">
				<div class="unit-price alert alert-info" style="">每台虚拟机最多添加6块网卡</div>
			</div>
			<!-- <form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item" id="mvlan">
						<div class="control-label">vlan号</div>
						<div class="controls">
							<input type="text" id="vlan" name="vlan" autofocus="" style="width:100px ! important">
						</div>
					</div>
                    <div class="item" id="mphysical">
						<div class="control-label">物理网卡</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="physical" name="physical">
								</select>
							</div>
						</div>
					</div>
				</fieldset>
			</form> -->
		</div>
		<div class="modal-footer">
			<button id="createMac" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>
