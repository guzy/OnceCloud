 //全局的websocket变量
    var paragraph = 1*1024*1024;//64*1024
    var file;
    var startSize,endSize = 0;
    var i = 0; j = 0;
    var count =0;

$(function () {
    connectupxen();
    $("#wizard").bwizard({
		backBtnText : "",
		nextBtnText : ""
	});
    
    $('.btn-back').on('click', function(event) {
		event.preventDefault();
		$("#wizard").bwizard("back");
	});
    
    $('ol').removeClass("clickable");

    $('.li-disable').unbind();
    
	$('.btn-first-next').on('click', function(event) {
		event.preventDefault();
		if ($("#rightHostDiv").children(".lidiv").length > 0) {
			$("#wizard").bwizard("next");
		}
		else
			{bootbox.alert("请至少选择一个服务器进行更新！");}
	});
	
	$("#leftHostDiv").delegate(".lidiv","click",function(){
		$(this).toggleClass("selectedli");
	});
	$("#rightHostDiv").delegate(".lidiv","click",function(){
		$(this).toggleClass("selectedli");
	})
	
	$(".btn-right").click(function(){
		$("#leftHostDiv").children(".selectedli").appendTo($("#rightHostDiv")).removeClass("selectedli");
	});
	
    $(".btn-left").click(function(){
    	$("#rightHostDiv").children(".selectedli").appendTo($("#leftHostDiv")).removeClass("selectedli");
	});
    
    $(".btn-Allright").click(function(){
    	$("#leftHostDiv").children(".lidiv").appendTo($("#rightHostDiv"));
	});
    
    $(".btn-Allleft").click(function(){
    	$("#rightHostDiv").children(".lidiv").appendTo($("#leftHostDiv"));
	});
    
    $(".btn-over").click(function(){
    	$("#waitingdiv").show();
    	$(this).attr("disabled","disabled");
    	updatexen();
    });
});

$(window).unload(function() {
	disconnectupxen();
});


function updatexen(){
  socket2.send(JSON.stringify({
        'UpdateXend': 'UpdateXend'
  }));
}

function connectupxen() {
    if ('WebSocket' in window) {
        console.log('Websocket supported');
        var host = window.location.host;
        socket2 = new WebSocket('ws://' + host + '/beyondsphere/upXenMessagingService');
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
           // console.log(event.data);
            var obj = JSON.parse(event.data);
           // console.log('message type: ' + obj.messageType);
           // console.log('content: ' + obj.content);
            if(obj.messageType =="ws_up_file")
        	{
        	    sendArraybuffer(obj);
        	}
        };

    } else {
        console.log('Websocket not supported');
    }
}


function disconnectupxen() {
	socket2.close();
    console.log("Disconnected");
}



function sendfileStart(name){
	socket2.send(JSON.stringify({
	        'fileStartName': name
	  }));
}

function sendfileEnd(){
	// console.log("结束");
	var hostlist ="";
	$("#rightHostDiv").children(".lidiv").each(function(index,item){
		//console.log(item);
		hostlist+=$(item).attr("tagvalue")+",";
		$("#divhost").append(item);
	});
	//console.log(hostlist);
	socket2.send(JSON.stringify({
	        'fileEndName': 'sendover','hostList':hostlist
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
		            //console.log("发送文件第" + (i) + "部分");
		            
		            //console.log(count);
		            //console.log(i);
		            var isok = (i / count) *100;
		            $("#showId").text("已经上传"+isok.toFixed(2) +"%");
		            $("#progressid").width(isok.toFixed(2) +"%");
		            socket2.send(ArrayBuffer);
		            }
		    }
		    else{
		            startSize = endSize = 0;
		            i = 0;
		            //console.log("发送" + file.name +"完毕");
		            $("#showId").text("文件上传完成！");
		            $("#progressid").width("100%");
		            sendfileEnd();
		            //$("#showId").text("已经上传完成，执行第二步：转存到服务器目录，请稍等");
		            //console.log("发送文件完毕");
		    }
	    }
	    else if(obj.content == "TRUE"){
	    	  $("#emdiv").text("已经更新完成！"); 
	    	  $("#loadinggif").hide();
	    } else if(obj.content == "FALSE"){
	    	  $("#emdiv").text("更新失败！");
	    	  $("#loadinggif").hide();
	    	  $(".btn-over").removeAttr("disabled");
	    } else if(obj.content == "ISEXIT"){
	    	  $("#showId").text("该ISO文件名已经存在！");
	    } else if(obj.content == "UPOVER"){
	    	 $("#wizard").bwizard("next");
	    }
	    	
}


$(document).ready(function(){
	
	$.ajax({
        type: 'get',
        url: 'HostAction/HostListNotInPool',
        dataType: 'json',
        success: function (array) {
        	$('#leftHostDiv').html("");
            $.each (array, function (index, obj){
				var hostUuid = obj.hostUuid;
				var hostName = decodeURIComponent(obj.hostName);
				var hostIP = obj.hostIP;
				$('#leftHostDiv').append('<div class="lidiv" tagvalue="'+hostUuid+'" title="'+hostName+'">'+hostIP+'</div>');
            });
        }
	});
	
	if (window.File && window.FileReader && window.FileList && window.Blob)
		{
		  $('.btn-second-next').on('click', function (event) {
		        event.preventDefault();
		        file = document.getElementById("upxenfile").files[0];
		        if(file!=null)
		        {
		        	 //console.log(file);
		        	 count = file.size/paragraph;
		        	 $("#pid").show();
		        	 $("#showId").text("开始上传！");
		        	 sendfileStart(file.name);
		        } 
		        else
	        	{
		        	bootbox.alert("请先选择需要上传的文件！");
		        }
		    });
		}
	    else {
	    	bootbox.alert('您的浏览器不支持html5 Web Socket 上传功能.');
		}
});