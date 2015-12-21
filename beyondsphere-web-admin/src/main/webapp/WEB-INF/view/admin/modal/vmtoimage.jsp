<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/ha.css" />
<script src="${basepath}js/common/jquery.validate.js"></script>
<script src="${basepath}js/admin/modal/vmtoimage.js"></script> 
<div class="modal-dialog" style="margin-top:100px; width:50%">
	<div class="modal-content" id="modalcontent" poolid="">
	  <div class="modal-header">
	    <h4 class="modal-title" id="modaltitle">虚拟机转化为模板<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
	  </div>
	  <div class="page-header">
        <h3>请确认以下信息： </h3>
      </div>
      <p>1. 虚拟机转化模板之前，请确认已经安装Agent，否则无法修改密码等。</p>
      <p>2. Windows 确认安装 Pvdrivers</p>
      <p>3. Linux 去掉网卡配置中MAC信息，否则会导致IP冲突。</p>
	  <div class="modal-footer">
		<button id="confirm" type="button" class="btn btn-success">确认</button>
        <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
      </div>
	</div>
</div>