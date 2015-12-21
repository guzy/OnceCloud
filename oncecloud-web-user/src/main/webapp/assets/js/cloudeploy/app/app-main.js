$(document).ready(function() {
	appMain.init();
	//
		/*$("#tmp-panel").each(function(){	
			$(this).on('dbclick',function(){
				  $('#ContainerModalDeploy').load('deploy/modelParams', '', function () {
				        $('#ContainerModalDeploy').modal({
				            backdrop: false,
				            show: true
				        });
				    });
				});		
			});	*/
	//
		});



var appMain = {
	statusMap : new Map(),
	init : function() {
		appMain.initData();
		appMain.initControlEvent();
		// $("#app-list-btn").click();
		$("#app-orchestration-btn").click();
	},
	initData : function() {
		this.statusMap.put("CREATED", "新建");
		this.statusMap.put("DEPLOYED", "已部署");
		this.statusMap.put("MODIFIED", "已修改");
		this.statusMap.put("新建", "CREATED");
		this.statusMap.put("已部署", "DEPLOYED");
		this.statusMap.put("已修改", "MODIFIED");

		this.statusMap.put("RUNNING", "运行");
		this.statusMap.put("STOPPED", "停止");
		this.statusMap.put("ERROR", "故障");
		this.statusMap.put("运行", "RUNNING");
		this.statusMap.put("故障", "ERROR");
	},

	initControlEvent : function() {
		$("#app-list-btn").click(function() {
			// alert("应用列表暂不可用...");
			loadPage(dURIs.viewsURI.appList, null);
		});
		$("#app-orchestration-btn").click(function() {
			alert("deploy test...");
			// loadPage(dURIs.viewsURI.appOrchestration, null);
			// window.location.href="/user/appDeploy.jsp";
			// location.href="DeployAction/skipDeploy";
			location.href="deploy/skipDeploy";
		});
		$("#domain-btn").click(function() {
			// alert("域名管理暂不可用...");
			loadPage(dURIs.viewsURI.domains, null);
		});
	}
};

