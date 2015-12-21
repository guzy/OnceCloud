<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/user/modal/modify.js"></script>
<div class="modal-dialog" style="margin-top:100px">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">修改属性<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body">
			<form class="form form-horizontal" id="modify-form" type=${modifyType} uuid=${modifyUuid} >
				<fieldset>
					<div class="item">
						<div class="control-label">名称</div>
						<div class="controls">
							<input type="text" id="name" name="name" autofocus="" value="${modifyName}" />
						</div>
					</div>
					<div class="item">
						<div class="control-label">描述</div>
						<div class="controls">
							<textarea rows="3" style="width:300px" id="desc" name="desc">${modifyDesc}</textarea>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="modifyinfomation" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>