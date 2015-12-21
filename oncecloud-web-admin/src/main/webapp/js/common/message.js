$(function () {
    connect();
    showmiddle();
});

$(window).unload(function() {
	disconnect();
});

function connect() {
    if ('WebSocket' in window) {
        console.log('Websocket supported');
        var host = window.location.host;
        socket = new WebSocket('ws://' + host + '/beyondsphere/messagingService');
        console.log('Connection attempted');

        socket.onopen = function () {
            console.log('Connection open');
        };

        socket.onclose = function () {
            console.log('Disconnecting connection');
        };
        
        socket.onerror = function(e) {
        	 console.log('has a error!');
        }

        socket.onmessage = function (event) {
            console.log(event.data);
            var obj = JSON.parse(event.data);
           // console.log('message type: ' + obj.messageType);
           // console.log('content: ' + obj.content);
         
            eval("(" + obj.messageType + "(obj)" + ")");
        };

    } else {
        console.log('Websocket not supported');
    }
}

function disconnect() {
    socket.close();
    console.log("Disconnected");
}

function send(message) {
    socket.send(JSON.stringify({
        'message': message
    }));
}

function ws_sticky(obj) {
    $.sticky(obj.content);
}

function ws_delete_row(obj) {
    var thistr = $('#tablebody').find('[rowid="' + obj.rowId + '"]');
    $(thistr).remove();
}

function ws_edit_row_console(obj) {
    var thistr = $('#tablebody').find('[rowid="' + obj.rowId + '"]');
    if (thistr.size() == 1) {
        if (obj.option == "add") {
            var thistd = thistr.find('[name="console"]');
            if (thistd.find('.console').size() == 0) {
                thistd.append('<a class="console" data-uuid='
                    + obj.rowId + '><img src="img/user/console.png"></a>');
            }
        } else {
            thistr.find('.console').remove();
        }
    }
}

function ws_edit_row_ip(obj) {
    var thistr = $('#tablebody').find('[rowid="' + obj.rowId + '"]');
    if (thistr.size() == 1) {
        thistr.find('[name="sip"]').html('<a>(' + obj.network + ')&nbsp;/&nbsp;' + obj.ip + '</a>');
    }
}

function ws_edit_row_for_bind_volume(obj) {
    var thistr = $('#tablebody').find('[rowid="' + obj.rowId + '"]');
    if (thistr.size() == 1) {
        thistr.find('[vmuuid]').attr('vmuuid', obj.vmId).html('<a><span class="glyphicon glyphicon-cloud"></span>&nbsp;&nbsp;' + obj.vmName + '</a>');
    }
}

function ws_edit_row_for_unbind_volume(obj) {
    var thistr = $('#tablebody').find('[rowid="' + obj.rowId + '"]');
    if (thistr.size() == 1) {
        thistr.find('[vmuuid]').attr('vmuuid', "").text("");
    }
}

function ws_edit_row_status(obj) {
    var thistr = $('#tablebody').find('[rowid="' + obj.rowId + '"]');
    if (thistr.size() == 1) {
        thistr.find('[name="stateicon"]').removeClass();
        thistr.find('[name="stateicon"]').addClass("icon-status");
        thistr.find('[name="stateicon"]').addClass("icon-" + obj.icon);
        thistr.find('[name="stateword"]').text(obj.word);
    }
}

function ws_edit_row_cpumem(obj) {
	var thistr = $('#tablebody').find('[rowid="' + obj.rowId + '"]');
	if (thistr.size() == 1) {
		thistr.find('[name="cpuCore"]').html(obj.cpu+"&nbsp;核");
		thistr.find('[name="memoryCapacity"]').html(obj.mem+"&nbsp;GB");
	}
}

var arrayid;

///展示一个提示
function showMessageNoAutoClose(content) {
   var showid = $.sticky(content,{'autoclose':false});
   arrayid = localStorage.getItem('alertStr');
   if(arrayid == null)
   {
      arrayid="";
   }
   setTimeout("",500);
   arrayid += '<div class="sticky" id="'
			+ showid.id
			+ '" style="height: 38px; display: block;"><span class="close sticky-close" rel="'
			+ showid.id + '" title="Close">×</span><div class="sticky-note" rel="'
			+ showid.id
			+ '">'+ content +'</div></div>---';
   localStorage.setItem('alertStr',arrayid);
   return showid.id;
}

///根据id，关闭对应的提示
function hideMessageNoAutoClose(obj) {
	var flag = true;
	$("#"+obj.conid).dequeue().slideUp('fast', function(){
			var closest = $(this).closest('.sticky-queue');
			var elem = closest.find('.sticky');
			
			arrayid = localStorage.getItem('alertStr');
			if (arrayid != null) {
				var newstr= '<div class="sticky" id="'
					+ obj.conid
					+ '" style="height: 38px; display: block;"><span class="close sticky-close" rel="'
					+ obj.conid + '" title="Close">×</span><div class="sticky-note" rel="'
					+ obj.conid
					+ '">'+ obj.content +'</div></div>---';
				var indexi = arrayid.indexOf(newstr);
				var newcookie =arrayid.substring(0,indexi) + arrayid.substring(indexi+newstr.length);
				localStorage.setItem('alertStr',newcookie);
				
				$(this).remove();
				if(elem.length == '1'){
					closest.remove();
				}
				flag = false;
			}
	});
	if (flag) {
		if (localStorage.getItem('alertStr') != null) {
			localStorage.removeItem('alertStr');
		}
	}
}

///新页面中，还原没有关闭的提示
function showmiddle(){
	arrayid = localStorage.getItem('alertStr');
	if(arrayid!="" && arrayid !=null)
	{
		var words = arrayid.split('---');
		for(var i=0;i<words.length -1 ;i++)
			{
			    
				var position = 'top-right'; 
				// Make sure the sticky queue exists
				if(!$('body').find('.sticky-queue').html())
					{ $('body').append('<div class="sticky-queue ' + position + '"></div>'); }
				
				// Building and inserting sticky note
				$('.sticky-queue').prepend(words[i]);
				
				
			}
		$(".sticky").css('height', 38);
		$('.sticky-close').on('click', function(){
			$('#' + $(this).attr('rel')).dequeue().slideUp(function(){
				var closest = $(this).closest('.sticky-queue');
				var elem = closest.find('.sticky');
				$(this).remove();
				if (elem.length == '1') {
					closest.remove();
				}
				innercontent = $(this).find(".sticky-note").html();
				innerconid = $(this).attr('rel');
				arrayid = localStorage.getItem('alertStr');
				if (arrayid != null) {
					var newstr= '<div class="sticky" id="'
						+ innerconid
						+ '" style="height: 38px; display: block;"><span class="close sticky-close" rel="'
						+ innerconid+ '" title="Close">×</span><div class="sticky-note" rel="'
						+ innerconid
						+ '">'+ innercontent +'</div></div>---';
					var indexi = arrayid.indexOf(newstr);
					var newcookie =arrayid.substring(0,indexi) + arrayid.substring(indexi+newstr.length);
					localStorage.setItem('alertStr',newcookie);
				}
			});
		});
	}
}
