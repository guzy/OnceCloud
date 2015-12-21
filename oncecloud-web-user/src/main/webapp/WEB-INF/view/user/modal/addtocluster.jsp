<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<script src="${basePath}js/common/jquery-ui-1.10.4.custom.min.js"></script>
<script src="${basePath}js/common/uuid.js"></script>
<script src="${basePath}js/user/modal/addtocluster.js"></script>
<style type="text/css">
	.image-item {
		margin-top: 7px;
	}
	.host-disable {
		border: 1px solid #eee;
		background: #eee;
		color: #838f96;
	}
</style>
<div class="modal-dialog" style="margin-top:100px">
	<div class="modal-content">
		<div class="modal-header">
			<h4 class="modal-title">添加到集群<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" jsonstr='${uuidjsonstr}' hosttype='${hostype}' id="hostjson">
			<div class="table-inner">
				<div class="once-pane sr-pane" style="padding:10px 30px 0">
					<div class="alarm-price-info">
						<div class="unit-price alert alert-info" style="">一个集群最多添加16台服务器</div>
					</div>
					<div class="item">
						<div class="control-label">目标集群</div>
						<div class="controls">
							<div class="select-con" style="padding:0 15px">
								<select class="dropdown-select" id="choosepool" name="sr_type">
									<!--<option value="bfs">BFS</option>-->
								</select>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div style="margin:0 65px 10px; padding:10px 25px; font-size:14px" id="host-alert">
			</div>
			<div id="choosemaster" style="padding:0 90px 5px">
			</div>
		</div>
		<div class="modal-footer">
			<button id="add2PoolAction" type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
        	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      	</div>
	</div>
</div>