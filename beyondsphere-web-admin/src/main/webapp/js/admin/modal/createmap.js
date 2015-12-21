//初始化地图对象，加载地图
var marker=null;
var map = new AMap.Map("mapContainer",{
	resizeEnable: true,
	//二维地图显示视口
	view: new AMap.View2D({
//		center:new AMap.LngLat(116.397428,39.90923),//地图中心点
		zoom:13 //地图显示的缩放级别
	})
});	
//设置城市中心点
map.setCity("苏州");

$(document).ready(function () {
	var userid ;
	$("input[name='userrow']:checked").each(function(){
		userid = $(this).parent().parent().attr('userid');
		$("#userid").val(userid);
	});
	//初始化地图标记
	$.ajax({
        type: 'post',
        url: 'UserAction/getUsermap',
        data: { userid: userid},
        dataType: 'json',
        success: function (array) {
        	if (array.length == 2&&array[0]!=null&&array[1]!=null) {
        		var lng = array[0];
        		var lat = array[1];
        		marker = new AMap.Marker({				  
        			icon: "http://webapi.amap.com/images/marker_sprite.png",
        			position:new AMap.LngLat(lng,lat)
        		});

        		marker.setMap(map);  //在地图上添加点
        		marker.setDraggable(true);
        		map.setDefaultCursor(("pointer") );
        		$("#add_but").hide();
        	}
        }     
    });
});

//添加点标记
function addMarker(){
	//使用CSS默认样式定义地图上的鼠标样式
	map.setDefaultCursor(("url('http://webapi.amap.com/images/marker_sprite.png'),pointer") );
	//为地图注册click事件获取鼠标点击出的经纬度坐标
	var clickEventListener=AMap.event.addListener(map,'click',function(e){
		var LngLatX =e.lnglat.getLng();//获取Lng值
		var LngLatY =e.lnglat.getLat();  //获取Lat值
		marker = new AMap.Marker({				  
			icon: "http://webapi.amap.com/images/marker_sprite.png",
			position:new AMap.LngLat(LngLatX,LngLatY)
		});
		marker.setMap(map);  //在地图上添加点
		marker.setDraggable(true);
//		marker.show();
		map.setDefaultCursor("pointer");
		AMap.event.removeListener(clickEventListener);
		$("#add_but").hide();
//		map.setFitView(); //调整到合理视野
	});
}

function clearmap(){
	marker.setMap(null);
	marker=null;
	$("#add_but").show();
}

$("#modifyUserAction").on('click',function(){
	var userid = $("#userid").val();
	var lng = null;
	var lat = null;
	if(marker==null||typeof(marker) == "undefined"){
	}else{
		lng = marker.getPosition().getLng();//获取经度
		lat = marker.getPosition().getLat();//获取纬度
	}
	$.ajax({
        type: 'post',
        url: 'UserAction/createMap',
        data: { userid: userid, lng: lng, lat: lat},
        dataType: 'json',
        success: function (result) {
        	if(result){
        		alert("用户地理位置设置成功！");
        		$('#UserModalContainer').modal('hide');
        		clearmap();
        	}else{
        		alert("用户地理位置设置失败！");
        	}
        }     
    });
});


