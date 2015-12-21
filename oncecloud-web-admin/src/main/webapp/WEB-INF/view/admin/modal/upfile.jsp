<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basepath}js/common/jquery.validate.js"></script>
<script src="${basepath}js/admin/modal/upfile.js"></script>
<style type="text/css">
.hty-loadpic {
height: 30px;
width: 30px;
vertical-align: middle;
}
</style>
<div class="modal-dialog" style="margin-top:100px; width:550px;hight:600px;">
	<div class="modal-content" id="modalcontent" hostid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">上传镜像<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 10px;">
		<div class="alert alert-info" role="alert">大文件上传，可能需要很长的时间，请耐心等待！</div>
			<form class="form form-horizontal" id="create-form">
				<fieldset>
                    <div class="item">
						<div class="control-label">镜像文件</div>
						<div class="controls">
							<input type="file" id="upiso" name="upiso" />
						</div>
					</div>
					 <div class="item">
						<div class="control-label">资源池</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="pool_select" name="pool_select">
								</select>
							</div>
						</div>
					</div>
					<div id="pid" style="display:none;">
					<!-- <div style="padding-left:120px"><img src="img/loading.gif" id="loadinggif" class="hty-loadpic"></div> -->
                   <div style="text-center" id="showId">已经完成0%</div>
                   <div class="progress" >
  <div id="progressid" class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">

  </div>
  </div>
</div>
				</fieldset>
			</form>
			
			
		</div>
		<div class="modal-footer">
			<button id="upfile" type="button" class="btn btn-primary">上传ISO</button>
			<button id="upfile-finished" class="btn btn-success" type="button" data-dismiss="modal" disabled >完成</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>