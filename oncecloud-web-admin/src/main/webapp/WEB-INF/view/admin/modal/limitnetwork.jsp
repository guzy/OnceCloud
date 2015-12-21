<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/admin/modal/limitnetwork.js"></script>
<div class="modal-dialog" style="width:600px; margin-top:100px">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title" id="modalnetlimit">网卡限速</h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
					<div class="alarm-price-info">
				<div class="unit-price alert alert-info" style="">可限制带宽或总量，单位：Mbps</div>
			</div>
			<form class="form form-horizontal" id="net-form">
				<fieldset>
					<div class="item">
						<div class="control-label">限制量：</div>
						<div class="controls">
							<input type="text" id="netSpeed" name="netSpeed"  >
						</div>
					</div>
					<div class="item">
						
						<div class="styled-select">
							<select id="selnet">
							<option value="rate">带宽</option>
							<option value="burst">总量</option>
							</select>
							</div>
					</div>
					
				</fieldset>
				
			</form>
		</div>
		<div class="modal-footer">
			<button id="limitNetwork" class="btn btn-primary" type="button">确定</button>
			<button id="cancelNetLimit" class="btn btn-default" type="button" data-dismiss="modal">取消</button>
		</div>
	</div>
</div>