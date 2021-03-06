<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/create/createuser.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div id="modalcontent" userid="" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">新建用户<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item">
						<div class="control-label">用户名</div>
						<div class="controls">
							<input type="text" id="user_name" name="user_name" autofocus="">
						</div>
					</div>
                    
					<div id="pwd">
                    <div class="item">
						<div class="control-label">密码</div>
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
							<input type="text" id="user_email" name="user_email" autofocus="">
						</div>
					</div>
                    <div class="item">
						<div class="control-label">联系电话</div>
						<div class="controls">
							<input type="text" id="user_tel" name="user_tel" autofocus="">
						</div>
					</div>
                    <div class="item">
						<div class="control-label">公司</div>
						<div class="controls">
							<input type="text" id="user_company" name="user_company" autofocus="">
						</div>
					</div>
                    <div class="item">
						<div class="control-label">级别</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="user_level" name="user_level">
									<option value="1">用户</option>
                                    <option value="0">管理员</option>
								</select>
							</div>
						</div>
					</div>
					<div id="pool">
                    <div class="item">
						<div class="control-label">选择资源池</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="pool_select" name="pool_select">
									<option value="0">系统随机分配</option>
								</select>
							</div>
						</div>
					</div>
					</div>
					<div id="role">
                    <div class="item">
						<div class="control-label">选择角色</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="role_select" name="role_select">
								</select>
							</div>
						</div>
					</div>
					</div>
					<div id="area">
                    <div class="item">
						<div class="control-label">选择区域</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="area_select" name="area_select">
								</select>
							</div>
						</div>
					</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="createUserAction" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>
