<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/user/modal/mountvolume.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:400px">
	<div id="modalcontent" userid="" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">挂载硬盘<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body">
			<table class="table table-bordered once-table">
			<thead>
				<tr>
				    <th width="4%"></th>
					<th width="14%">ID</th>
					<th width="12%">名称</th>
					<th width="12%">容量（GB）</th>
				</tr>
			</thead>
			<tbody id="volumebody">
			</tbody>
		</table>
		</div>
		<div class="modal-footer">
			<button id="mountvolume" type="button" class="btn btn-primary">确定</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>
