<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/cost.css" />
<script src="${basePath}js/admin/detail/modifycostdetail.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:600px">
	<div id="modalcontent" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" class="modal-title">修改成本<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<div class="alert alert-info change-info">
				修改成本信息，分类名称代表统计大类，细化名称代表大类的进一步细分。例如硬件资源（大类）下的虚拟化服务器（细化）
			</div>
			<form class="form form-horizontal">
				<fieldset id="modify_detailid">
					<!-- <div class="item" id="type">
						<div class="control-label">分类名称</div>
						<div class="controls">
							<input type="text" name="typename" id="typename" disabled>
							<select class="type" id="typelist">
							</select>
						</div>
					</div> -->
					<div class="item" id="detail">
						<div class="control-label" id="detailname">细化分类名称</div>
						<div class="controls">
							<input type="text" name="typedetailname" id="typedetailname" disabled>
							<!-- <select class="type" id="typedetaillist">
							</select> -->
						</div>
					</div>
					<div class = "once-pane costinfo" id="costdetailinfo">
						<div class="item">
							<div class="control-label">资源名称</div>
							<div class="controls">
								<input type="text" name="resourcename" id="resourcename">
							</div>
						</div>
						<div class="item">
							<div class="control-label">资源类型</div>
							<div class="controls">
								<input type="text" name="resourcetype" id="resourcetype">
							</div>
						</div>
						<div class="item">
							<div class="control-label">单价</div>
							<div class="controls">
								<input type="text" name="unitprice" id="unitprice"><span>&nbsp;&nbsp;元</span>
							</div>
						</div>
						<div class="item">
							<div class="control-label">计费方式</div>
							<div class="controls typeradio">
								<input type="radio" name="priceRadios"  value="0" checked="checked" onclick="">月
								<input type="radio" name="priceRadios"  value="1" style="margin-left: 20px" onclick="">年
								<input type="radio" name="priceRadios"  value="2" style="margin-left: 20px" onclick="">总价
							</div>
						</div>
						<div class="item">
							<div class="control-label">资源个数</div>
							<div class="controls typeradio">
								<input class="spinner" id="resourcenum" type="text"/>&nbsp;&nbsp;
								<!-- <input type="text" name="resourcenum" id="resourcenum"> -->
							</div>
						</div>
						<div class="item">
							<div class="control-label">创建时间</div>
							<div class="controls">
								<div class="input-group date start-time col-md-6" data-date="" data-date-format="yyyy-mm-dd" data-link-field="start_date" data-link-format="yyyy-mm-dd">
					                <input class="form-control start-form" type="text" id="createtime" value="">
					                <span class="input-group-addon"><span class="glyphicon glyphicon-remove start-remove"></span></span>
								    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
					            </div>
							</div>
						</div>
						<div class="item">
							<div class="control-label">销毁时间</div>
							<div class="controls">
								<div class="input-group date end-time col-md-6" data-date="" data-date-format="yyyy-mm-dd" data-link-field="end_date" data-link-format="yyyy-mm-dd">
					                <input class="form-control end-form" type="text" id="destorytime" value="">
					                <span class="input-group-addon"><span class="glyphicon glyphicon-remove end-remove"></span></span>
								    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
					            </div>
							</div>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="modifydetail" type="button" class="btn btn-primary" >提交</button>
        	<button id="cancel" type="button" class="btn btn-default" >取消</button>
      	</div>
	</div>
</div>
