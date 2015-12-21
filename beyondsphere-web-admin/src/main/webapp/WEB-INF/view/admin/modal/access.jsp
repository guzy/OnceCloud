<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/ha.css" />
<script src="${basepath}js/common/jquery.validate.js"></script>
<script src="${basepath}js/admin/modal/access.js"></script>
<div class="modal-dialog" style="margin-top:100px; width:50%">
	<div class="modal-content" id="modalcontent" poolid="">
		<div class="modal-header">
			<h4 class="modal-title" id="modaltitle">接入控制<a class="close" data-dismiss="modal" aria-hidden="true"><span class="glyphicon glyphicon-remove"></span></a></h4>
		</div>
		<div class="modal-body" style="padding:10px 0">
			<div class="page-header">
	          <h3><input id="start-access" type="checkbox">启用接入控制</h3>
	        </div>
	           <p>接入控制是beyondSphere HA用于确保群集内的故障切换容量的一种策略，
	           	增加已确定主机故障的比例将增加群集中的可使用性限制和预留容量。</p>
	        <form class="form form-horizontal" id="create-form"> 	
				<div class="panel-group" id="accordion">
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <input type="radio" name="optionsRadios" id="optionsRadios1" 
					         value="1">
				        <a data-toggle="collapse" data-parent="#accordion" 
				          href="#collapseOne">
				                           定义故障切换容量
				        </a>
				      </h4>
				    </div>
				    <div id="collapseOne" class="panel-collapse collapse">
				      <div class="panel-body">
				        <p>“群集允许的主机故障数量”，是指集群会根据选择主机的数量，保留足够的资源，允许该数量主机发生故障后能够完成接管作业。</p>
				        <p>预留的故障切换容量：<input class="spinner" id="host-spinner" type="text" value="1"/>&nbsp;&nbsp;主机</p>
				        <p>插槽大小策略：</p>
				        <p><input type="radio" name="solt" id="optionsSolt1" value="1"> 涵盖所有已打开电源的虚拟机</p>
				        <p>根据最大CPU/内存预留和所有已打开电源的虚拟机的开销来计算插槽大小。</p>
				        <p><input type="radio" name="solt" id="optionsSolt2" value="2"> 固定插槽大小</p>
				        <p>明确指定插槽的大小：</p>
				        <p>CPU插槽大小：<input class="spinner" id="cpu-solt-spinner" type="text" value="25"/>&nbsp;&nbsp;MHz</p>
				        <p>内存插槽大小：<input class="spinner" id="memory-solt-spinner" type="text" value="25"/>&nbsp;&nbsp;MB</p>
				      </div>
				    </div>
				  </div>
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <input type="radio" name="optionsRadios" id="optionsRadios2" 
					         value="2">
				        <a data-toggle="collapse" data-parent="#accordion" 
				          href="#collapseTwo">
				                          百分比定义故障切换容量
				        </a>
				      </h4>
				    </div>
				    <div id="collapseTwo" class="panel-collapse collapse">
				      <div class="panel-body">
				      	  <p>“预留群集资源的百分比”可以根据需要为集群保留多少CPU和内存资源。在主机发生故障时，在预留资源上完成接管作业，默认为25%。</p>
				          <p>预留的故障切换CPU容量：<input class="spinner" id="cpu-spinner" type="text" value="25"/>&nbsp;&nbsp;%</p>
				          <p>预留的故障切换内存容量：<input class="spinner" id="memory-spinner" type="text" value="25"/>&nbsp;&nbsp;%</p>
				      </div>
				    </div>
				  </div>
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <input type="radio" name="optionsRadios" id="optionsRadios3" 
					         value="3">
				        <a data-toggle="collapse" data-parent="#accordion" 
				          href="#collapseThree">
				                          指定故障切换主机
				        </a>
				      </h4>
				    </div>
				    <div id="collapseThree" class="panel-collapse collapse">
				      <div class="panel-body">
				        <p>“指定故障切换主机”，是指你可以选择一台主机专门用来做HA的备机，任何主机故障后，虚拟机都会优先在该台主机上启动。</p>
				        <div class="panel-body-host">
					        <a id="addhost" url="${basePath}admin/modal/ha/migratehost">
				              <span class="glyphicon glyphicon-plus add-appoint-host"></span>
				            </a>
				            &nbsp;&nbsp;
				            <a id="removehost" href="#">
					          <span class="glyphicon glyphicon-remove remove-appoint-host"></span>
					        </a>
				            <table class="table table-bordered once-table">
							  <thead>
								<tr>
									<th width="6%"></th>
									<th width="47%">名称</th>
									<!-- <th width="47%">主服务器</th> -->
								</tr>
							  </thead>
							  <tbody id="hosttable">
							  </tbody>
						    </table>
				        </div>
				      </div>
				    </div>
				  </div>
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        <input type="radio" name="optionsRadios" id="optionsRadios4" 
					         value="0">
				        <a data-toggle="collapse" data-parent="#accordion" 
				          href="#collapseFour">
				                          默认接入策略
				        </a>
				      </h4>
				    </div>
				    <div id="collapseFour" class="panel-collapse collapse">
				      <div class="panel-body">
				        <p>“默认接入策略”，指服务器发生故障以后，服务器中包含的虚拟就可以按照一定规则分配到同一资源池呢，处于开机状态且运行正常的服务器上</p>
				      </div>
				    </div>
				  </div>
				</div>
			</form> 
		</div>

		<div class="modal-footer footer">
			<button class="btn btn-success" id="startHaAction" type="button" data-dismiss="modal">确定</button>
	       	<button class="btn btn-danger" type="button"  data-dismiss="modal">取消</button>
	    </div>
	</div>
	
	<div id="HostModalContainer" type="" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div> 
</div>