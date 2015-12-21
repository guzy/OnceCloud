<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basepath}js/common/jquery.validate.js"></script>
<script src="${basepath}js/admin/modal/accordance.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div class="modal-content" id="modalcontent" poolid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">资源一致性管理<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
					<div class="alarm-price-info">
				<div class="unit-price alert alert-info" style="">请填写大于30的整数，单位为秒</div>
			</div>
			<form class="form form-horizontal" id="time-form">
				
					<div class="item">
						<div class="control-label">时间：</div>
						<div class="controls">
							<input type="text" id="ac_time" name="ac_time"  >
						</div>
					</div>	
				
			</form>
		</div>
		<div class="modal-footer">
			<button id="startAcAction" type="button" class="btn btn-danger">开启</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>