<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/user/modal/bindgroup.js"></script>
<div class="modal-dialog" style="width:600px; margin-top:100px">
	<div class="modal-content" groupUuid = ${groupUuid }>
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">选择要绑定分组[${showid}]的虚拟机资源<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove" id="resourceRemove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:15px 30px">
			<div id="alert" class="alert alert-warning" style="margin-bottom:10px; padding:10px; color:#c09853">每个虚拟机只能绑定一个分组</div>
			<div class="row" style="margin:0">
				<div class="col-md-12 once-toolbar" style="margin:0 0 10px 0; padding:0">
					<div class="toolbar-right">
						<table>
							<tr>
								<td>&nbsp;页数&nbsp;<a id="currentPS"></a>&nbsp;/&nbsp;<a id="totalPS"></a></td>
								<td style="padding-left:10px">
									<div class="pagination-small"><ul id="bindPS" style="display:inline"></ul></div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="instancelist" id="instancelist">
			</div>
		</div>
		<div class="modal-footer">
			<button id="bindGroupToVM" class="btn btn-primary" type="button" data-dismiss="modal" disabled>提交</button>
			<button id="cancelbind" class="btn btn-default" type="button" data-dismiss="modal">取消</button>
		</div>
	</div>
</div>