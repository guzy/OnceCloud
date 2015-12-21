<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/admin/modal/limitdisk.js"></script>
<div class="modal-dialog" style="width:600px; margin-top:100px">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title" id="modaldisklimit">硬盘限速</h4>
		</div>
		
		<div class="modal-body" style="padding:10px 0">
					<div class="alarm-price-info">
				<div class="unit-price alert alert-info" style="">
					<p>限制硬盘读速度或写速度。</p>
					<p><span>当前限制：【读速度】</span>
					<span id="readM"></span><span>MBps/</span><span id="readi"></span><span>iops</span>
					<span>，【写速度】</span><span id="writeM"></span><span>MBps/</span><span id="writei"></span><span>iops</span>
					</p>
				</div>
			</div>
			<form class="form form-horizontal" id="disk-form">
				<fieldset>
					<div class="item">
						<div class="control-label">速度：</div>
						<div class="controls">
							<input type="text" id="speed" name="speed"  >
						</div>
					</div>
					<div class="item">
						<div class="control-label">类型：</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="type" name="type">
									<option value="read">读速度</option>
									<option value="write">写速度</option>
								</select>
							</div>
						</div>
					</div>
					<div class="item">
						<div class="control-label">单位：</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="io_type" name="io_type">
									<option value="MBps">MBps</option>
									<option value="iops">iops</option>
								</select>
							</div>
						</div>
					</div>
				</fieldset>

			</form>
						
				
		</div>
		<div class="modal-footer">
			<button id="limitDisk" class="btn btn-primary" type="button">确定</button>
			<button id="cancelLimit" class="btn btn-default" type="button" data-dismiss="modal">取消</button>
		</div>
	</div>
</div>