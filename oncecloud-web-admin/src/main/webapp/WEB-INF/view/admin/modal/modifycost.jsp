<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/cost.css" />
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/modal/modifycost.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:600px">
	<div id="modalcontent" userid="" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">修改成本<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<div class="alert alert-info change-info">
				修改成本信息，分类名称代表统计大类，细化名称代表大类的进一步细分。例如硬件资源（大类）下的虚拟化服务器（细化）
			</div>
			<form class="form form-horizontal" id="create-form">
				<input type="hidden" id="type" value="${type}">
				<div class="item">
					<div class="control-label">分类名称</div>
					<div class="controls">
						<input type="text" name="typename" id="typename">
						<select class="type" id="typelist">
						</select>
					</div>
				</div>
				<div class="once-pane costinfo" id="typeinfo">
					<div class="item">
						<div class="control-label">细化分类名称</div>
						<div class="controls">
							<input type="text" name="typedetailname" id="typedetailname">
						</div>
					</div>
				</div>
				<div class="item">
					<div class="control-label">描述信息</div>
					<div class="controls">
						<textarea rows="3" cols="4" id="descText"></textarea>
					</div>
				</div>
				<!-- <div class="item" id="type">
					<div class="controls">
						<span>分类名称：</span>
						<input type="text" name="typename" id="typename">
						<select id="typelist">
							<option>硬件成本</option>
							<option>IDC服务</option>
							<option>运维成本</option>
							<option>软件成本</option>
						</select><br>
						<div class="once-pane" id="typeinfo">
							<span id="detailname">细化分类名称：</span>
							<input type="text" name="typedetailname" id="typedetailname">
							<select id="typedetaillist">
								<option>服务器</option>
								<option>机柜</option>
								<option>软件</option>
								<option>人力</option>
							</select><br>
						</div>
						<span>描述信息：</span>
							<textarea rows="3" cols="4" id="descText"></textarea>
					</div>
				</div> -->
			</form>
		</div>
		<div class="modal-footer">
			<button id="modifycost" type="button" class="btn btn-primary" >提交</button>
        	<button id="cancel" type="button" class="btn btn-default" >取消</button>
      	</div>
	</div>
</div>
