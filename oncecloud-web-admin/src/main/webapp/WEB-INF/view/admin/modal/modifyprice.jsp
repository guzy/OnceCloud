<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/admin/modal/modifyprice.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div id="modalcontent" userid="" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">价格信息修改<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item">
						<div class="control-label" id = "cpu-lable">CPU</div>
						<div class="controls">
							<input type="text" id="cpu" name="cpu" autofocus="">&nbsp;元/1个/天
						</div>
					</div>
                    <div class="item">
						<div class="control-label" id = "mem-lable">内存</div>
						<div class="controls">
							<input type="text" id="memory" name="memory" autofocus="">&nbsp;元/1GB/天
						</div>
					</div>
                    <div class="item">
						<div class="control-label" id = "value-lable">硬盘</div>
						<div class="controls">
							<input type="text" id="volume" name="volume" autofocus="">&nbsp;元/10GB/天
						</div>
					</div>
					<div class="item">
						<div class="control-label" id = "snapshot-lable">备份</div>
						<div class="controls">
							<input type="text" id="snapshot" name="snapshot" autofocus="">&nbsp;元/1个/天
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="modifyPriceAction" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>
