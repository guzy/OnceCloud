<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/user/create/createvolume.js"></script>
<div class="modal-dialog" style="margin-top:100px">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">新建硬盘<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body">
			<div class="alert alert-info modal-alert" style="display:none">
				<h2>总价: <span id="price-hour">0.02</span><span class="unit">元/小时</span>× <span id="hour-number">1</span> = <span id="price-total">0.02</span><span class="unit">元/小时</span><span class="unit none">（合<strong><span id="price-month">14.40</span></strong>元/月）</span>
				</h2>
			</div>
			<div class="alert alert-warning modal-alert" style="height: 0px; overflow: hidden; padding: 0px 10px;">
			</div>
			<form class="form form-horizontal" id="createinfo-form">
				<fieldset>
					<div class="item">
						<div class="control-label">名称</div>
						<div class="controls">
							<input type="text" id="volume_name" name="volume_name">
						</div>
					</div>
					<div class="item">
						<div class="control-label">数量</div>
						<div class="controls">
							<input type="text" id="count" name="count" value="1">
						</div>
					</div>
					<div class="item">
						<div class="control-label">性能</div>
						<div class="controls">
							<span class="help">IO&nbsp;吞吐&nbsp;36&nbsp;MB/s，单块最小容量10GB、最大容量1T。</span>
						</div>
					</div>
					<div class="item">
						<div class="control-label">容量</div>
						<div class="controls size">
							<div id="slider" style="width:320px; display:inline-block"></div>
							<input id="size" type="text" class="mini" value="10" style="margin-left:10px; width:50px;">
							<span class="help inline">GB</span>
							<span class="help">10GB - 1T</span>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="createvolumeAction" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>