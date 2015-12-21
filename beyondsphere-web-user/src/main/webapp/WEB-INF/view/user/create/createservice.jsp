<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/user/create/createservice.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">提交表单<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="apply-form">
				<fieldset>
					<div class="item">
						<div class="control-label" style="width:80px">标题</div>
						<div class="controls" style="margin-left:100px">
							<input type="text" id="qa_title" name="qa_title">
						</div>
					</div>
					<div class="item">
						<div class="control-label" style="width:80px">描述</div>
						<div class="controls" style="margin-left:100px">
							<textarea rows="10" style="width:400px" id="qa_content" name="qa_content"></textarea>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="applyserviceAction" type="button" class="btn btn-primary" style="margin-left:-50px">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>