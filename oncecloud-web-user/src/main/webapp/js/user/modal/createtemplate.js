 //全局的websocket变量
var paragraph = 1*1024*1024;//64*1024
var file;
var startSize,endSize = 0;
var i = 0; j = 0;
var count =0;

$(function () {
    connectupiso();
});

$(window).unload(function() {
	disconnectupiso();
});

function connectupiso() {
    if ('WebSocket' in window) {
        console.log('Websocket supported');
        var host = window.location.host;
        socket2 = new WebSocket('ws://' + host + '/upDockerMessagingService');
        console.log('Connection attempted');

        socket2.onopen = function () {
            console.log('Connection open');
        };

        socket2.onclose = function () {
            console.log('Disconnecting connection');
        };
        
        socket2.onerror = function(e) {
        	 console.log('has a error!');
        }

        socket2.onmessage = function (event) {
            console.log(event.data);
            var obj = JSON.parse(event.data);
            if(obj.messageType =="ws_up_file")
        	{
        	    sendArraybuffer(obj);
        	}
        };

    } else {
        console.log('Websocket not supported');
    }
}


function disconnectupiso() {
    socket2.close();
    console.log("Disconnected");
}


function sendfileStart(name,poolId){
	 socket2.send(JSON.stringify({
	        'fileStartName': name,'poolId':poolId
	  }));
}

function sendfileEnd(){
	 socket2.send(JSON.stringify({
	        'sendover': 'sendover'
	  }));
}

function sendArraybuffer(obj){
	//console.log("服务器说" + obj.content + (obj.content=="OK"));
    if(obj.content == "OK"){
	    if(endSize < file.size){
	    	var blob;
	        startSize = endSize;
	        endSize += paragraph ;
	        if (file.webkitSlice) {
	              blob = file.webkitSlice(startSize, endSize);
	        } else if (file.mozSlice) {
	              blob = file.mozSlice(startSize, endSize);
	        }
	        else{
	        	 blob = file.slice(startSize, endSize);
	        }
	        var reader = new FileReader();
	        reader.readAsArrayBuffer(blob);
	        reader.onload = function loaded(evt) {
            var ArrayBuffer = evt.target.result;
            i++;
            var isok = (i / count) *100;
            $("#showId").text("已经上传"+isok.toFixed(2) +"%");
            $("#progressid").width(isok.toFixed(2) +"%");
            socket2.send(ArrayBuffer);
            }
	    } else{
            startSize = endSize = 0;
            i = 0;
            $("#showId").text("上传完成！");
            $("#progressid").width("100%");
            $(".btn-next").prop("disabled",false);
            sendfileEnd();
            $("#showId").text("已经上传完成，执行第二步：转存到云存储目录，请稍等");
	    }
    } else if(obj.content == "TRUE"){
	    $("#showId").text("已经全部完成！");
    } else if(obj.content == "FALSE"){
	    $("#showId").text("转存失败！");
    } else if(obj.content == "NOTPOOL"){
	    $("#showId").text("资源池配置不正确！");
    } else if(obj.content == "ISEXIT"){
	    $("#showId").text("该ISO文件名已经存在！");
    }
    //reloadList(1);
}


$(document).ready(function(){
	
	$.ajax({
        type: 'post',
        url: 'ClusterAction/AllCluster',
        dataType: 'json',
        success: function (array) {
            $.each (array, function (index, obj){
				var pooluuid = obj.poolid;
				var poolname = decodeURIComponent(obj.poolname);
				$('#pool_select').append('<option value="'+pooluuid+'">'+poolname+'</option>');
            });
        }
	});
	
	if (window.File && window.FileReader && window.FileList && window.Blob)
		{
		 $('#upfile').on('click', function (event) {
		        event.preventDefault();
		        if($("#pool_select").val()!=null)
	        	{
			        file = document.getElementById("upiso").files[0];
			        var filename = file.name.split(".");
			        if(!validate(filename[0])){
			        	return;
			        }
			        if("zip" != filename[filename.length-1]){
			        	if("tar.gz" != (filename[filename.length-2]+"."+filename[filename.length-1])){
			        		bootbox.alert("只能上传.zip文件或者tar.gz文件！");
				        	return;
			        	}
			        }
			        if(file!=null)
			        {
			        	 console.log(file);
			        	 count = file.size/paragraph;
			        	 $("#pid").show();
			        	 $("#showId").text("开始上传！");
			        	sendfileStart(file.name,$("#pool_select").val());
			        }
			        else
		        	{
			        	bootbox.alert("请先选择需要上传的文件！");
		        	}
	        	}
		       
		    });
		}
	    else {
	    	bootbox.alert('您的浏览器不支持html5 Web socket 上传功能.');
		}
});

$("#wizard").bwizard({
	backBtnText : "",
	nextBtnText : ""
});

$('.btn-back').on('click', function(event) {
	event.preventDefault();
	$("#wizard").bwizard("back");
});

$('.btn-next').on('click', function(event) {
	event.preventDefault();
	$("#wizard").bwizard("next");
});

$('#finished').on('click', function(event){
	event.preventDefault();
	$('#TemplateModalContainer').modal('hide');
});

$('.btn-create').on('click', function(event){
	file = $('#upiso').val().split('\\');
	filename = file[file.length-1];
	var clusteruuid = $("#pool_select").val();
	var templatename = $('#templatename').val();
	var templateversion = $('#templateversion').val();
	templatename += ":"+templateversion;
	if(validate(templatename)&&validate(templateversion)){
		$('#imagestatus').show();
		$.ajax({
	        type: 'post',
	        url: '/TemplateAction/makeTemplate',
	        data: {
	        	clusterUuid:clusteruuid,
	        	fileName:filename,
	        	templateName:templatename
	        },
	        dataType: 'text',
	        success: function (result) {
	        	$('#imagemessage').removeClass("alert-warning");
	        	$('#imagemessage').addClass("alert-success");
	        	$('#imagemessage').text('制作完成！');
	        	$('#templateuuid').val(result);
	        }
		});
    }
	
});

$('#publishDoc').on('click', function(event){
	var clusteruuid = $("#pool_select").val();
	var templateuuid = $('#templateuuid').val();
	var templatename = $('#templatename').val();
	var templateversion = $('#templateversion').val();
	templatename += ":"+templateversion;
	$('#pushstatus').show();
	$.ajax({
        type: 'post',
        url: '/TemplateAction/template',
        data: {
        	clusterUuid:clusteruuid,
        	templateUuid:templateuuid,
        	templateName:templatename
        },
        dataType: 'text',
        success: function (result) {
        	if(result){
        		$('#pushmessage').removeClass("alert-warning");
	        	$('#pushmessage').addClass("alert-success");
	        	$('#pushmessage').text('发布成功！');
        	}
        }
	});
});

function validate(filename){
	var reg = new RegExp("[u4e00-u9fa5]")
    var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]")
    if(!reg.test(filename) && !pattern.test(filename)){
    	bootbox.alert("上传的文件名不能包含汉字和特殊字符");
    	return false;
    }else{
    	return true;
    }
}