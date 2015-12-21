<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<div class="content detail" id="platformcontent" alarmUuid="${alarmUuid}">
	<div class="intro">
		<h1>监控告警&nbsp;Alarms</h1>
		<p class="lead">
			使用<em>监控告警&nbsp;(Alarms)</em>系统，您可以对自己的虚拟机资源进行状态监控，并针对虚拟机的监控属性制定告警规则，在虚拟机状态异常时发出警告。创建一个告警策略，定义监控规则以及发生告警时的行为，将要监控的虚拟机资源绑定到该策略上，即可令这些虚拟机在规则生效时触发告警。
		</p>
	</div>
	<ol class="breadcrumb oc-crumb">
		<li><a href="${basePath}alarm"><span class="glyphicon glyphicon-flash cool-green"></span><span class="ctext">ALARMS</span></a></li>
		<li class="active"><a href="${basePath}alarm/detail">${showId}</a></li>
	</ol>
	<div class="col-md-4">
		<div class="detail-item">
			<div class="title">
				<h3>基本属性&nbsp;<a href="javascript:void(0)" class="btn basic-refresh"><span class="glyphicon glyphicon-refresh"></span></a>
					<div class="btn-group">
						<button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							<span class="glyphicon glyphicon-tasks"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a id="basic-modify"><span class="glyphicon glyphicon-pencil"></span>修改</a></li>
							<li><a id="basic-resource"><span class="glyphicon glyphicon-plus"></span>添加监控资源</a></li>
							<li><a id="basic-unbind"><span class="glyphicon glyphicon-remove"></span>解绑所有资源</a></li>
							<li><a id="basic-remove"><span class="glyphicon glyphicon-trash"></span>删除</a></li>
						</ul>
					</div>
				</h3>
			</div>
			<dl id="basic-list" class="my-horizontal"></dl>
		</div>
	</div>
	<div class="col-md-8">
		<div class="detail-item detail-right">
			<div class="once-pane" style="padding:0">
				<div class="title"><h3 class="uppercase">规则</h3><span class="oc-update" id="suggestion" style="display: none;">修改尚未更新，请点击"应用修改"</span></div>
				<div class="once-toolbar">
					<button class="btn btn-default rule-refresh"><span class="glyphicon glyphicon-refresh" style="margin-right:0"></span></button>
					<button id="createrule" class="btn btn-default" url="${basePath}alarmrule/create">+&nbsp;新建</button>
					<button id="confirm" class="btn btn-default btn-disable" disabled><span class="glyphicon glyphicon-pencil"></span>修改</button>
					<button id="deleterule" class="btn btn-default btn-disable" disabled><span class="glyphicon glyphicon-trash"></span>删除</button>
					<div class="toolbar-right">
						<table>
							<tr>
								<td>每页&nbsp;</td>
								<td><input id="limit" name="limit" class="page" value="10"></td>
								<td>&nbsp;个&nbsp;页数&nbsp;<a id="currentP"></a>&nbsp;/&nbsp;<a id="totalP"></a></td>
								<td style="padding-left:10px">
									<div><ul id="pageDivider" style="display:inline"></ul></div>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<table class="table table-bordered once-table">
					<thead>
						<tr>
							<th><!--checkbox--></th>
							<th>监控项</th>
							<th>条件</th>
							<th>阈值</th>
							<!-- <th>连续周期数</th> -->
						</tr>
					</thead>
					<tbody id="tablebody">
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="RuleModalContainer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
	<div id="ResourceModalContainer" type="new" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"></div>
</div>