$(document).ready(function () {
	
	//初始化所有的计数器
	initCounter();
	
	//初始化接入控制策略
	initAccessPolicy();
	
	/*选用启用控制接入时把四种策略设置为可选值 */
	$('#start-access').on('click',function(event){
		if($(this).is(':checked')){
			$("input[type='radio']").removeAttr("disabled","disabled");
		}else{
			$("input[type='radio']").attr("disabled","disabled");
			$("input[type='radio']").prop("checked",false);
		}
	});
	
	/*选中第一种策略时，默认按照第一种插槽策略执行*/
	$('#optionsRadios1').on('change',function(event){
		if($(this).is(':checked')){
			$('#optionsSolt1').attr("checked",true);
		}else{
			$('#optionsSolt1').prop("checked",false);
			$('#optionsSolt2').prop("checked",false);
		}
	});
	
	/* 确定方法策略时执行条件 */
    $('#startHaAction').on('click', function (event) {
    	//资源池id
    	var poolUuid = '';
    	$('input[name="poolrow"]:checked').each(function () {
    		poolUuid = $(this).parent().parent().attr('poolid');
        });
    	if($('#start-access').is(':checked')){
        	//预留主机数量
        	var host_spinner='';
        	//预留插槽策略
        	var slot_policy='';
        	//插槽的cpu数目
        	var cpu_polt_count='';
        	//插槽的memory数目
        	var memory_polt_count='';
        	//预留cpu所占百分比
        	var cpu_percent='';
        	//预留内存所占百分比
    		var memory_percent='';
    		//指定迁移主机
    		var migratory_host_uuid='';
    		//控制接入策略
        	var access_policy = $("input[name='optionsRadios']:checked").val();
        	
        	//保存不同策略的参数
        	switch(access_policy){
        		case '1':
        			host_spinner = $('#host-spinner').val();
            		slot_policy = $("input[name='solt']:checked").val();
            		if(slot_policy == "1"){
            		}else if(slot_policy == "2"){
            			cpu_polt_count = $('#cpu-solt-spinner').val();
            			memory_polt_count = $('#memory-solt-spinner').val();
            		}
        			break;
        		case '2':
        			cpu_percent = $('#cpu-spinner').val();
            		memory_percent = $('#memory-spinner').val();
        			break;
        		case '3':
        			$('input[name="hostrow"]').each(function () {
            			migratory_host_uuid += ',' + $(this).parent().parent().attr('migratoryhostid');
            	    });
        			break;
        		default:
        			break;
        	}
        	if(access_policy == 3 && migratory_host_uuid == ''){
        		bootbox.alert('选择指定迁移主机时，主机列表不能为空');
        		return;
        	}
        	//传给服务器的json参数
        	var access_params = {pool_uuid:poolUuid, access_control_flag:access_policy, 
        			            left_host:host_spinner, slot_policy:slot_policy,
        			            slot_cpu:cpu_polt_count, slot_memory:memory_polt_count, 
        			            cpu_percent:cpu_percent,memory_percent:memory_percent,migratory_host_uuid:migratory_host_uuid};
        	
            if ($('#create-form').valid()) {
                    $.ajax({
                        type: 'post',
                        url: 'HaAction/UpdateHa',
                        data: access_params,
                        dataType: 'json',
                        complete: function (data) {
                          console.log(data);
                          $('#ha-box').prop("checked",false);
                          $('#ha-box').attr('disabled', "disabled");
                     	  $('#ha-service').attr('disabled', "disabled");
                     	  bootbox.alert(data.responseText);
                 		  window.location.reload();
                        }
                    });
                }
    	}else{
    		bootbox.alert('请开启控制接入策略，再继续操作！');
    	}
    });
    
    $('#create-form').validate({
        rules: {
        	ha_path: {
                required: true,
            }
        },
        messages: {
        	ha_path: {
                required: "<span class='help'>请填写高可用路径</span>",
            }
        }
    });
    
    /*添加指定的迁移主机*/
    $('#addhost').on('click', function(event){
    	event.preventDefault();
    	$('#HostModalContainer').attr('type', 'host');
    	$('#HostModalContainer').load($(this).attr('url'), '', function() {
    		$('#HostModalContainer').modal({
    			backdrop : false,
    			show : true
    		});
    	});
    });
    
    /*删除指定的迁移主机*/
    $('#removehost').on('click', function(event){
    	$('input[name="hostrow"]:checked').each(function () {
    		$(this).parent().parent().remove();
	    });
    });
});

/*初始化所有的计数器*/
function initCounter(){
	$('#cpu-solt-spinner').spinner({
		min:1,
		step:1
	});
	$('#memory-solt-spinner').spinner({
		min:1,
		step:1
	});
	$('#host-spinner').spinner({
		min:1,
		step:1
	});
	$('#cpu-spinner').spinner({
		min:1,
		step:1
	});
	$('#memory-spinner').spinner({
		min:1,
		step:1
	});
}

/*初始化已经保存的控制策略*/
function initAccessPolicy(){
	var accessflag = '';
	var lefthost = '';
	var slotpolicy = '';
	var slotcpu = '';
	var slotmemory = '';
	var cpupercent = '';
	var memorypercent = '';
	var migratoryhostid = '';
    $('input[name="poolrow"]:checked').each(function () {
    	accessflag = $(this).parent().parent().attr("accessflag");
    	lefthost = $(this).parent().parent().attr("lefthost");
    	slotpolicy = $(this).parent().parent().attr("slotpolicy");
    	slotcpu = $(this).parent().parent().attr("slotcpu");
    	slotmemory = $(this).parent().parent().attr("slotmemory");
		cpupercent = $(this).parent().parent().attr("cpupercent");
		memorypercent = $(this).parent().parent().attr("memorypercent");
		migratoryhostid = $(this).parent().parent().attr('migratoryhostid');
    });
	if(accessflag == '0' || accessflag == '1' || accessflag == '2' || accessflag == '3'){
		$('#start-access').prop('checked',true);
		$("input[type='radio']").removeAttr("disabled","disabled");
		switch(accessflag){
			case '0':
				initDefaultPolicy();
				break;
			case '1':
				initSlotPolicy(lefthost, slotpolicy, slotcpu, slotmemory);
				break;
			case '2':
				initPercentPolicy(cpupercent, memorypercent);
				break;
			case '3':
				initMigrateHostPolicy(migratoryhostid);
				break;
			default:
				$("input[type='radio']").attr("disabled","disabled");
				break;
		}
	}
}

/*初始化默认的控制策略*/
function initDefaultPolicy(){
	$('#optionsRadios4').prop('checked',true);
	$('#collapseFour').addClass('in');
}

/*初始化插槽控制策略*/
function initSlotPolicy(lefthost, slotpolicy, slotcpu, slotmemory){
	$('#optionsRadios1').prop('checked',true);
	$('#collapseOne').addClass('in');
	//设置预留主机数
	$('#host-spinner').val(lefthost);
	//添加默认插槽的控制策略
	if(slotpolicy == '1'){
		$('#optionsSolt1').prop('checked',true);
	}else if(slotpolicy == '2'){
		$('#optionsSolt2').prop('checked',true);
		$('#cpu-solt-spinner').val(slotcpu);
		$('#memory-solt-spinner').val(slotmemory);
	}
}

/*初始化百分比控制策略*/
function initPercentPolicy(cpupercent, memorypercent){
	$('#optionsRadios2').prop('checked',true);
	$('#collapseTwo').addClass('in');
	$('#cpu-spinner').val(cpupercent);
	$('#memory-spinner').val(memorypercent);
}

/*初始化指定主机控制策略*/
function initMigrateHostPolicy(migratoryhostid){
	$('#optionsRadios3').prop('checked',true);
	$('#collapseThree').addClass('in');
	var btable = document.getElementById("hosttable");
	var tableStr = '';
	var migratehostIds =  migratoryhostid.split(',');
	for(var i=0; i<migratehostIds.length; i++){
		if(migratehostIds[i].length != 0){
			var showid = 'host-' + migratehostIds[i].substring(0, 8);
			var mytr = '<tr migratoryhostid="'
					   + migratehostIds[i]
					   + '"><td class="rcheck"><input name="hostrow" type="checkbox" onclick="chk(this);"></td><td>'
					   + showid
					   +'</td></tr>';
		   tableStr = tableStr + mytr;
		}
	}
    btable.innerHTML = tableStr;
}

/*选择表格多选框事件*/
function chk(obj){  
     var boxArray = document.getElementsByName('hostrow');  
     var count=0;
     for(var i=0;i<=boxArray.length-1;i++){  
          if(boxArray[i]==obj && obj.checked){  
        	 count++;
          }
     }   
     if(count == 0){
    	 $('#removehost').unbind('click');
     }else{
    	 $('#removehost').bind('click');
     }
}  