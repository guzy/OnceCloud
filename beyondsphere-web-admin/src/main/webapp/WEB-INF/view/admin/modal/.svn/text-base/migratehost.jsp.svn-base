<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/ha.css" />
<script src="${basepath}js/common/jquery.validate.js"></script>
<script src="${basepath}js/admin/modal/migratehost.js"></script> 
<div class="modal-dialog" style="margin-top:100px; width:80%">
	<div class="modal-content" id="modalcontent" poolid="">
	  <div class="modal-header">
	    <h4 class="modal-title" id="modaltitle">添加迁移主机<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
	  </div>
      <p>选择列表中主机专门用来做HA的备机，任何主机故障后，虚拟机都会优先在添加的主机上启动。</p>
      <table class="table table-bordered once-table">
	    <thead>
		  <tr>
			<th width="6%"></th>
			<th width="47%">名称</th>
			<th width="47%">主服务器</th>
		  </tr>
	    </thead>
	    <tbody id="migratehosttable">
	    </tbody>
      </table>
	  <div class="modal-footer footer">
		<button class="btn btn-success" id="confirm" type="button">确定</button>
        <button class="btn btn-danger" id="cancel" type="button" >取消</button>
      </div>
	</div>
</div>