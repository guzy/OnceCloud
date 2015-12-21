<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${basePath}css/bwizard.css" />
<link rel="stylesheet" href="${basePath}css/upxen.css" />
<div class="content" id="platformcontent" userid="${userid}">
	<div class="intro">
		<h1>更新&nbsp;UPXEN</h1>
		 <p class="lead" style="margin-top:10px">
			<em>更新&nbsp;(UPXEN)</em>用管理员可以直接通过web一键更新。
		</p>  
	</div>
	<div>
    	<div class="row" style="margin: 0; background-color: #fff">
	    	<div class="col-md-1" >
	    	</div>
			<div class="col-md-10 text-center" style="padding:10px 10px;height:400px;width:800px">
				<div id="wizard" class="wizard">
					<ol>
						<li class="li-disable">选择需要更新的服务器</li>
						<li class="li-disable">上传更新文件</li>
						<li class="li-disable">执行更新操作</li>
					</ol>
					<div>
						<div class="wizard-inner">
							<!-- <em style="color:red">*只有离池的服务器才可以进行更新操作,要更新，请先进行离池操作</em> -->
							<div class="row">
								<div class="col-md-5">
									<!-- <b>已离池的服务器列表</b> -->
									<b>服务器列表</b>
								</div>
								<div class="col-md-2">
								</div>
								<div class="col-md-5">
									<b>需要更新的服务器</b>
								</div>
							</div><br/>
							<div class="row hostlist">
								<div class="col-md-5" id="leftHostDiv">
								</div>
								<div class="col-md-2" style="vertical-align : center; ">
									<button class="btn btn-primary btn-right btnfloat" type="button" >右移选中 </button>
									<button class="btn btn-primary btn-left btnfloat" type="button">左移选中</button>
									<button class="btn btn-primary btn-Allright btnfloat" type="button">全部右移</button>
									<button class="btn btn-primary btn-Allleft btnfloat" type="button">全部左移</button>
								</div>
								<div class="col-md-5" id="rightHostDiv">
								</div>
							</div>
						 </div>
						<div class="wizard-action">
							<button class="btn btn-default btn-first-next" type="button">下一步</button>
						</div>
					</div>
					<div>
						<div class="wizard-inner">
							<form class="form form-horizontal" id="create-form">
								<fieldset>
			                    <div class="item">
									<div class="control-label">镜像文件</div>
									<div class="controls">
										<input type="file" id="upxenfile" name="upxenfile" style="padding-top: 7px;"/>
									</div>
								</div>
									<br/><br/>
									<div id="pid" style="display:none;">
									<!-- <div style="padding-left:120px"><img src="img/loading.gif" id="loadinggif" class="hty-loadpic"></div> -->
					                    <div style="text-center" id="showId">已经完成0%</div>
					                    <div class="progress" >
						  				   <div id="progressid" class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
										   </div>
					  				    </div>
								    </div>
								</fieldset>
							 </form>
						</div>
						<div class="wizard-action">
							<button class="btn btn-default btn-back" type="button">上一步</button>
							<button class="btn btn-default btn-second-next" type="button">上传</button>
						</div>
					</div>
					<div>
						<div class="wizard-inner"><br/>
						需要更新的服务器:
							<div  id="divhost" class="item" style="border: 1px solid #eee;height:200px;">
							
							</div>
							<div id="waitingdiv" style="display:none"><em id="emdiv">执行中，请等待...</em><img src="img/loading.gif" id="loadinggif" class="hty-loadpic"></div>
							</div>
							<div class="wizard-action">
								<button class="btn btn-default btn-over" type="button">执行更新操作</button>
							</div>
					</div>
				</div>
			</div>
			<div class="col-md-1" >
	    	</div>
		</div>
    </div>
</div>
