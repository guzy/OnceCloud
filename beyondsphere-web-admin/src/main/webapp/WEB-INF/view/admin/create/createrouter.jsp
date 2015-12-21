<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/admin/create/createrouter.js"></script>
<input type="hidden" id="bath_url" value="${bathPath}"/>
<div class="modal-dialog" style="margin-top:100px; width:550px">
	<div class="modal-content" id="modalcontent" rtid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">新建路由器<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<!-- <div class="alert alert-info modal-alert">
				<h2>总价: <span id="price-hour">0.02</span><span class="unit">元/小时</span>× <span id="hour-number">1</span> = <span id="price-total">0.02</span><span class="unit">元/小时</span><span class="unit none">（合<strong><span id="price-month">14.40</span></strong>元/月）</span>
				</h2>
			</div>
			<div class="alert alert-warning modal-alert" style="height: 0px; overflow: hidden; padding: 0px 10px;">
			</div> -->
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item">
						<div class="control-label">路由名称</div>
						<div class="controls">
							<input type="text" id="rt_name" name="rt_name" autofocus="">
						</div>
					</div>
					<div class="item">
						<div class="control-label" style="width:150px">选择用户</div>
						<div class="controls" style="margin-left:170px">
							<div class="select-con">
								<select class="dropdown-select" id="rt_user" name="rt_user">				
  								</select>
  							</div>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer" style="margin-top:0">
			<button id="createRouterAction" type="button" class="btn btn-primary" >确定</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>