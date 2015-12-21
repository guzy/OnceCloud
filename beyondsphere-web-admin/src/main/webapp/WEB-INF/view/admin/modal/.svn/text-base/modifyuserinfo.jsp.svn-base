<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basepath}js/admin/modal/modifyuserinfo.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div id="modalcontent" userid="" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" userid="${userid}" class="modal-title">修改账户<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item">
						<div class="control-label">用户名</div>
						<div class="controls">
							<input type="text" id="user_name" name="user_name" autofocus="" value="${username}" disabled>
						</div>
					</div>
                    <div class="item">
						<div class="control-label">原密码</div>
						<div class="controls">
							<input type="password" id="user_oldpwd" name="user_oldpwd" autofocus="" value="">
						</div>
					</div>
					<div id="pwd">
                    <div class="item">
						<div class="control-label">新密码</div>
						<div class="controls">
							<input type="password" id="user_pwd" name="user_pwd" autofocus="">
						</div>
					</div>
                    <div class="item">
						<div class="control-label">重复密码</div>
						<div class="controls">
							<input type="password" id="user_rpwd" name="user_rpwd" autofocus="">
						</div>
					</div>
					</div>
                    <div class="item">
						<div class="control-label">邮箱</div>
						<div class="controls">
							<input type="text" id="user_email" name="user_email" autofocus="" value="${usermail}">
						</div>
					</div>
                    <div class="item">
						<div class="control-label">联系电话</div>
						<div class="controls">
							<input type="text" id="user_tel" name="user_tel" autofocus="" value="${userphone}">
						</div>
					</div>
                    
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="modifyUserAction" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>

