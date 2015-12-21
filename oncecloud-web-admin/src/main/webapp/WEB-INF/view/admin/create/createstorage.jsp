<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/create/createstorage.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div class="modal-content" id="modalcontent" srid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">添加存储<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item">
						<div class="control-label">存储名称</div>
						<div class="controls">
							<input type="text" id="sr_name" name="sr_name" autofocus="">
						</div>
					</div>
					
					<div class="item">
						<div class="control-label">存储类型</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="sr_type" name="sr_type">
									<option value="bfs">BFS</option>
                                    <option value="ceph">Ceph</option>
                                    <option value="ocfs2">OCFS2</option>
                                    <option value="local_ocfs2">Local_OCFS2</option>
                                    <option value="devicemapper">Devicemapper</option>
								</select>
							</div>
						</div>
					</div>
					
					<div class="item">
						<div class="control-label">存储地址</div>
						<div class="controls">
							<input type="text" id="sr_address" name="sr_address" autofocus="">
						</div>
					</div>
					
					<div class="item">
						<div class="control-label">端口</div>
						<div class="controls">
							<input type="text" id="sr_port" name="sr_port" autofocus="">
						</div>
					</div>
					
					<div class="item">
						<div class="control-label">挂载目录</div>
						<div class="controls">
							<input type="text" id="sr_dir" name="sr_dir" autofocus="">
						</div>
					</div>
					
	                <!-- <div class="item">
						<div class="control-label">所在机架</div>
						<div class="controls">
							<div class="select-con">
								<select class="dropdown-select" id="rack_location" name="rack_location">
								
								</select>
							</div>
						</div>
					</div> -->
					
					<div class="item">
						<div class="control-label">存储描述</div>
						<div class="controls">
							<textarea rows="3" style="width:300px" id="sr_desc" name="sr_desc"></textarea>
						</div>
					</div>
					
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="addSRAction" type="button" class="btn btn-primary">添加</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>