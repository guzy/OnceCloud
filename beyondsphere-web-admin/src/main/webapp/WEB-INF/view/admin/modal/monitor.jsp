<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/ha.css" />
<script src="${basepath}js/common/jquery.validate.js"></script>
<script src="${basepath}js/admin/modal/monitor.js"></script> 
<div class="modal-dialog" style="margin-top:100px; width:50%">
	<div class="modal-content" id="modalcontent" poolid="">
	  <div class="modal-header">
	    <h4 class="modal-title" id="modaltitle">主机监控<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
	  </div>
	  <div class="page-header">
        <h3><input type="checkbox">主机监控
        </h3>
      </div>
      <p>主机监控也是指服务器监控， 就是指将远程服务器运行数据通过各种方式记录下来，并在需要时可以随时调用监控记录进行查看。</p>
	  <div class="modal-footer">
		<button id="startHaAction" type="button" class="btn btn-default">开启主机监控</button>
		<button id="closeHaAction" type="button" class="btn btn-danger">关闭主机监控</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      </div>
	</div>
</div>