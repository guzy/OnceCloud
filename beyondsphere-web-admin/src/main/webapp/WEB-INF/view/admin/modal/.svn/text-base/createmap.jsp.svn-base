<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link href="${basepath}css/createmap.css" rel="stylesheet">
<script src="${basepath}js/admin/modal/createmap.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div id="modalcontent" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" class="modal-title">设置地理位置<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0;height: 500px">
			<form class="form form-horizontal" id="create-form">
			<fieldset>
				<input type="hidden" id="userid">
				<div id="mapContainer" ></div>
				<div id="btnDiv">
					<input type="button" value="添加位置标记" onClick="javascript:addMarker()" id="add_but"/>  
					<input type="button" value="清除位置标记" onClick="javascript:clearmap();"/> 
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

