<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/create/createusb.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:500px">
	<div id="createUsbDiv" uuid="${uuid}" mtype="${type}" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">新建USB配置<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<!--提示 -->
			<!-- <div class="alarm-price-info">
				<div class="unit-price alert alert-info" style="">每台虚拟机最挂载6个usb</div>
			</div> -->
			<!--表单 -->
			<div class="modal-body" style="padding: 10px 0">
				<form class="form form-horizontal" id="create-form">
					<fieldset>
						<div class="item" style="margin-bottom: 30px">
							<div class="control-label" style="width: 150px">选择usb</div>
							<div class="controls" style="margin-left: 170px">
								<div class="select-con">
									<select class="dropdown-select" id="vm_usb" name="vm_usb">
									</select>
								</div>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<!-- <form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item" id="musb">
						<div class="control-label">usb名称</div>
						<div class="controls">
							<input type="text" id="usbname" name="usbname"">
						</div>
					</div>
                    <div class="item" id="tusb">
						<div class="control-label">usb类型</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="physical" name="usbtype">
									<option value="NTFS">NTFS</option>
									<option value="EXT3">EXT3</option>
									<option value="EXT4">EXT4</option>
									<option value="FAT">FAT</option>
								</select>
							</div>
						</div>
					</div>
					<div class="item" id="uusb">
						<div class="control-label">挂载路径</div>
						<div class="controls">
							<input type="text" id="urlusb" name="urlusb"">
						</div>
					</div>
				</fieldset>
			</form> -->
		</div>
		<div class="modal-footer">
			<button id="createUsb" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>
