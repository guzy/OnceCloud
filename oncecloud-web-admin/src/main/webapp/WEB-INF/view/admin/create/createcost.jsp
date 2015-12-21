<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/cost.css" />
<script src="${basePath}js/common/jquery.validate.js"></script>
<script src="${basePath}js/admin/create/createcost.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:600px">
	<div id="modalcontent" userid="" class="modal-content">
		<div class="modal-header">
			<h4 id="modaltitle" oldname="" class="modal-title">添加成本<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<div class="alert alert-info change-info">
				添加成本分为三个步骤：首先添加分类：例如硬件 ；其次细化分类，选中细化分类，选择成本分类，进一步添加分类；最后选择详细信息，添加成本详细信息。
				如果已经存在分类和细化分类，亦可以直接添加成本信息。
			</div>
			<form class="form form-horizontal" id="create-form">
				<fieldset>
					<div class="item" id="type">
						<div class="control-label">添加类型</div>
						<div class="controls typeradio">
							<input type="radio" name="costRadios" id="costRadios1" value="1" checked="checked" onclick="addcost()">&nbsp;成本分类
							<input type="radio" name="costRadios" id="costRadios2" value="2" style="margin-left: 20px" onclick="addcost()">&nbsp;细化分类
							<input type="radio" name="costRadios" id="costRadios3" value="3" style="margin-left: 20px" onclick="addcost()">&nbsp;详细信息<br>
						</div>
					</div>
					<div class="item" id="type">
						<div class="control-label">分类名称</div>
						<div class="controls">
							<input type="text" name="typename" id="typename">
							<select class="type" id="typelist">
							</select>
						</div>
					</div>
					<div class="item" id="detail">
						<div class="control-label" id="detailname">细化分类名称</div>
						<div class="controls">
							<input type="text" name="typedetailname" id="typedetailname">
							<select class="type" id="typedetaillist">
							</select>
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
								<input type="radio" name="priceRadios" id="priceRadios1" value="0" checked="checked" onclick="">月
								<input type="radio" name="priceRadios" id="priceRadios2" value="1" style="margin-left: 20px" onclick="">年
								<input type="radio" name="priceRadios" id="priceRadios3" value="2" style="margin-left: 20px" onclick="">总价
							</div>
						</div>
						<div class="item">
							<div class="control-label">资源个数</div>
							<div class="controls typeradio">
								<input class="spinner" id="resourcenum" type="text" value="1"/>&nbsp;&nbsp;
								<!-- <input type="text" name="resourcenum" id="resourcenum"> -->
							</div>
						</div>
						<div class="item">
							<div class="control-label">创建时间</div>
							<div class="controls">
								<div class="input-group date start-time col-md-6" data-date="" data-date-format="yyyy-mm-dd" data-link-field="start_date" data-link-format="yyyy-mm-dd">
					                <input class="form-control start-form" type="text" id="createtime" readonly="readonly" value="">
					                <span class="input-group-addon"><span class="glyphicon glyphicon-remove start-remove"></span></span>
								    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
					            </div>
							</div>
						</div>
						<div class="item">
							<div class="control-label">销毁时间</div>
							<div class="controls">
								<div class="input-group date end-time col-md-6" data-date="" data-date-format="yyyy-mm-dd" data-link-field="end_date" data-link-format="yyyy-mm-dd">
					                <input class="form-control end-form" type="text" id="destorytime" readonly="readonly" value="">
					                <span class="input-group-addon"><span class="glyphicon glyphicon-remove end-remove"></span></span>
								    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
					            </div>
							</div>
						</div>
					</div>
					
					<div class="item" id="desc">
						<div class="control-label">描述信息</div>
						<div class="controls">
							<textarea rows="3" cols="4" id="descText"></textarea>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<button id="createcost" type="button" class="btn btn-primary" >提交</button>
        	<button id="cancel" type="button" class="btn btn-default" >取消</button>
      	</div>
	</div>
</div>
