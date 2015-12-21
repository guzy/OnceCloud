<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basepath}js/common/jquery.validate.js"></script>
<script src="${basepath}zTreeStyle/jquery.ztree.all-3.5.min.js"></script>
<script src="${basepath}js/admin/modal/setauth.js"></script>
<link rel="stylesheet" href="${basePath}zTreeStyle/zTreeStyle.css" />

<div class="modal-dialog" style="margin-top:100px; width:550px;hight:600px;">
	<div class="modal-content" id="modalcontent" hostid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">角色权限配置管理<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body">
			<div class="alert alert-danger" style="margin:10px">对角色 : <span id="roleNameSpan"> </span>  进行权限配置</div>
			<input type="hidden" id="roleIdinput" />
			<div class="text-center" style="margin-left: 50px">
			<div id="treeAuth"  class="ztree"></div>
			</div>
			
			
		</div>
		<div class="modal-footer">
			<button id="authset" type="button" class="btn btn-primary">保存更改</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>