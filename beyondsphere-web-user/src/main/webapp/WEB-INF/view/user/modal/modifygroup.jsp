<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/user/modal/modifygroup.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div id="modalcontent" userid="" class="modal-content" groupUuid = "${groupUuid}">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">修改分组<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item">
						<div class="control-label">分组类别</div>
						<div class="controls">
							<input type="text" id="groupName" name="groupName" autofocus="">
						</div>
					</div>
                    
                    <div class="item">
						<div class="control-label">色彩标记</div>
						<div class="controls">
							<input type="color" id="color" name="color"  style="cursor:pointer; border:solid 0px; background-color:#fff; width:54%">
						</div>
					</div>
                    <div class="item">
						<div class="control-label">地点</div>
						<div class="controls">
							<input type="text" id="group_loc" name="group_loc" autofocus="">
						</div>
					</div>
                    <div class="item">
						<div class="control-label">描述信息</div>
						<div class="controls">
							<textarea rows="3" style="width:300px" id="group_des" name="group_des"></textarea>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="modifyGroupAction" type="button" class="btn btn-primary">提交</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>
