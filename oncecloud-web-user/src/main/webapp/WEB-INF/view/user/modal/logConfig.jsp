<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/user/modal/logConfig.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:400px">
	<div id="modalcontent" userid="" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">日志保存时限<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<form class="form form-horizontal" id="create-form">
				<p id="logconfig-describition" style="margin-left:10px; font-size:14px; text-indent: 28px; line-height:23px;">
					设置日志保存时限以后，只能保留时间范围内的日志记录。
				</p>
				<div class="item" id="mswitchid">
					<div class="control-label">日志保存时限:</div>
					<div class="controls">
						<input type="text" id="log-save-day" name="log-save-day" autofocus="" style="width:100px">&nbsp;天
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer" style="margin-top:0px;">
			<button id="confirm" type="button" class="btn btn-primary" >确定</button>
        	<button type="button" class="btn btn-default btn-cancle" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>
