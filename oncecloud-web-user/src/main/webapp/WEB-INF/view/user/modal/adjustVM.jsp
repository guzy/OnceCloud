<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/user/modal/adjustVM.js"></script>
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">
				更改配置<a class="close" data-dismiss="modal" aria-hidden="true"><span
					class="glyphicon glyphicon-remove"></span></a>
			</h4>
		</div>
		<div class="modal-body">
			<div class="row" style="margin:0; background-color:#cfeaf8">
				<div>
					<div class="wizard-inner"
						style="padding-left:50px; padding-top:30px">
						<h6>CPU</h6>
						<div class="cpu options">
							<div class="types-options cpu-options selected" core="1">
								1核</div>
							<div class="types-options cpu-options " core="2">2核</div>
							<div class="types-options cpu-options " core="4">4核</div>
							<div class="types-options cpu-options " core="8">8核</div>
						</div>
						<h6>内存</h6>
						<div class="memory options">
							<div class="types-options memory-options disabled" capacity="0.5">
								512MB</div>
							<div class="types-options memory-options selected" capacity="1">
								1G</div>
							<div class="types-options memory-options" capacity="2">2G</div>
							<div class="types-options memory-options" capacity="4">4G</div>
							<div class="types-options memory-options" capacity="6">6G</div>
							<div class="types-options memory-options" capacity="8">8G</div>
							<div class="types-options memory-options" capacity="12">
								12G</div>
							<div class="types-options memory-options disabled" capacity="16">16G</div>
							<div class="types-options memory-options disabled" capacity="24">24G</div>
							<div class="types-options memory-options disabled" capacity="32">32G</div>
						</div>
						<div style="padding-left:160px;padding-top:30px;padding-bottom:30px">
							<button id="adjust-vm" type="button"
								class="btn btn-primary" style="margin-right:30px">提交</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
