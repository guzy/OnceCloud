var  features ="";
$(document).ready(function () {
	//初始化地图对象，加载地图
	var marker;
	var map = new AMap.Map("mapContainer",{
		resizeEnable: true,
		//二维地图显示视口
		view: new AMap.View2D({
	//		center:new AMap.LngLat(116.397428,39.90923),//地图中心点
			zoom:18 //地图显示的缩放级别
		})
	});	
	//设置城市中心点
	map.setCity("苏州");
	
	//初始化地图标记
	$.ajax({
        type: 'get',
        url: 'MapLocationAction/getAllUsermap',
        dataType: 'json',
        success: function (jsonArray) {
        	for (var i = 0; i < jsonArray.length; i++) {
                var jsonObj = jsonArray[i];
        		var lng = jsonObj.lng;
        		var lat = jsonObj.lat;
        		var username = jsonObj.username;
        		new AMap.Marker({	
        			map:map,
        			topWhenMouseOver :true,
        			icon: "http://webapi.amap.com/images/marker_sprite.png",
        			position:new AMap.LngLat(lng,lat)
        		});
        		//用户的虚拟机
        		var ocvms = jsonObj.ocvms;
        		var myvms=ocvms.length+"台";
//        		for(var j=0;j<ocvms.length;j++){
//        			myvms=myvms+ocvms[j].vmName+"<br>";
//        		}
//        		if(myvms==""){
//        			myvms="无";
//        		}
        		if(ocvms.length==0){
        			myvms="无";
        		}
        		var marker = new AMap.Marker({
        		       map:map,
        		       topWhenMouseOver :true,
        		       extData:{username:username,myvms:myvms},
        		       position:new AMap.LngLat(lng,lat), //基点位置
        		       offset:new AMap.Pixel(0,0), //相对于基点的偏移位置
        		       content:"<div class='mydiv' style='white-space:nowrap;box-shadow: 0px 3px 14px rgba(0, 0, 0, 0.5);background: none repeat scroll 0px 0px #FFF;border-radius: 5px;padding: 5px;text-align: left;border: 2px solid #C0C0C0;'>"+username+"</div>"   //自定义Marker的内容，即一个DOM对象
        		});
        		
        		
        		AMap.event.addListener(marker,'click',function(e){
        			var infoWindow;
        			if(!infoWindow){ 
        				infoWindow = new AMap.InfoWindow({autoMove: true}); 
        			}
        			var content="<div class='panel panel-info view-panel'>"
//						+"<div class='panel-heading'>"
//						+"<b>"+e.target.getExtData().username+"的虚机</b>"
//						+"</div>"
						+"<div class='panel-body' id='enterprises'>虚拟机 ："+e.target.getExtData().myvms
						+"</div>"
						+"</div>";
        			infoWindow.setContent(content);
        			infoWindow.open(map,e.lnglat);
        		});
        		map.setDefaultCursor(("pointer") );
        	}
        }     
    });
});



