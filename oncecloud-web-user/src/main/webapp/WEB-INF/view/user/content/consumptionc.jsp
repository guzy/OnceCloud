<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<div class="content" id="platformcontent" basePath="${basePath}">
    <div class="intro">
        <h1>
            	实时消费&nbsp;Consumption
        </h1>
        <p class="lead" style="margin-top:10px">
            	实时消费提供用户对自己所拥有资源消费情况查看，并且能够查看计算某时段消费情况
        </p>
    </div>
    <!-- <ul class="nav nav-tabs once-tab">
		<li class="tab-filter active" type="cost_overview">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>消费概览</a>
		</li>
		<li class="tab-filter" type="cost_detail">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>计时收费</a>
		</li>
		<li class="tab-filter" type="type_price">
			<a href="javascript:void(0)"><span class="glyphicon glyphicon-globe"></span>成本收费</a>
		</li>
	</ul> -->
     <!--收费清单水表 -->
            <div class="panel panel-info over-panel" id="water_condiv" style="height:430px">
                <div class="panel-heading">
                    <b>
                       	 消费水表图
                    </b>
                </div>
                <div class="panel-body" style="height:390px;padding-left: 82px">
                    <div class="watercontainer">
                        <div class="water_container">
                            <div class="water_container" id="waterDiv">
                                <div class="water">
                                    <p class="water_title">
                                        	总价（元）
                                    </p>
                                    <div class="water_number" id="water_number">
                                    </div>
                                    <div class="zhuji">
                                        <p>CPU</p>
                                        <p id="water_cpu"></p>
                                    </div>
                                    <div class="yingpan">
                                        <p>内存</p>
                                        <p id="water_mem"></p>
                                    </div>
                                    <div class="kuandai">
                                        <p>硬盘</p>
                                        <p id="water_volume"></p>
                                    </div>
                                    <p class="water_name">
                                        	${user.userName}
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-info over-panel">
                <div class="panel-heading">
                    <b>
                        	消费分布图
                    </b>
                </div>
                <div class="panel-body">
                    <div style="margin-left:-12px; padding-left:0px;height:361px" id="profitPie"
                    data-highcharts-chart="3">
                    </div>
                </div>
            </div>
    <div class="once-pane" id="cost_detail" style="visibility:hidden">
        <!-- <div class="once-toolbar">
            <div class="summary-time">
                <div class="start">
                    <label for="start_date" class="col-md-2 control-label statistic-label start-label">
                      	  开始时间：
                    </label>
                    <div class="input-group date start-time col-md-5" data-date="" data-date-format="yyyy-mm-dd"
                    data-link-field="start_date" data-link-format="yyyy-mm-dd">
                        <input class="form-control start-form" type="text" value="">
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-remove start-remove">
                            </span>
                        </span>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar">
                            </span>
                        </span>
                    </div>
                    <input type="hidden" id="start_date" value="" />
                </div>
                <div class="end">
                    <label for="end_date" class="col-md-2 control-label statistic-label end-label">
                       	 终止时间：
                    </label>
                    <div class="input-group date end-time col-md-5" data-date="" data-date-format="yyyy-mm-dd"
                    data-link-field="end_date" data-link-format="yyyy-mm-dd">
                        <input class="form-control end-form" type="text" value="">
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-remove end-remove">
                            </span>
                        </span>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar">
                            </span>
                        </span>
                    </div>
                    <input type="hidden" id="end_date" value="" />
                </div>
            </div>
            <button type="button" class="btn btn-primary find" id="searchBun">
                	查询
            </button>
        </div> -->
        <table class="table table-bordered once-table" style="margin-top:60px">
            <thead>
                <tr>
                	<th width="20%">企业名称</th>
					<th width="15%">cpu&nbsp;(元)</th>
					<th width="15%">内存&nbsp;(元)</th>
					<th width="15%">硬盘&nbsp;(元)</th>
					<th width="15%">总计&nbsp;(元)</th>
					<th width="20%">统计月份</th>
                    <!-- <th width="15%"> 资源类型</th>
                    <th width="20%">详细信息</th>
                    <th width="10%"> 价格（元）</th>
                    <th width="10%"> 运行状态</th>
                    <th width="15%">  计费开始时间</th>
                    <th width="15%"> 计费结束时间</th>
                    <th width="15%"> 计费时间</th> -->
                </tr>
            </thead>
            <tbody id="typetablebody" userid="${user.userId}"></tbody>
        </table>
    </div>
</div>