var userId = 0;
reloadUserList();

function reloadUserList(){
	$.ajax({
        type: 'get',
        url: 'UserAction/UserListSave',
        dataType: 'json',
        success: function (array) {
            if (array.length > 0) {
                $(array).each(function (index, item) {
                	if(item.userlevel != 0){
                		 $("#select_user").append("<option value='" + item.userid + "'>" + decodeURIComponent(item.username) + "</option>");
                	}
                });
                reloadList(1);
            }
        }
    });
}

function reloadList(page) {
	getFeeList(page);
	if (page == 1) {
		options = {
			currentPage : 1
		};
		$('#pageDivider').bootstrapPaginator(options);
	}
}

function getFeeList(page) {
	var limit = $('#limit').val();
	var search = $('#search').val();
	var type = $('#select_name').val();
	var userId = $('#select_user').val();
	var start_date = $('#start_date').val();
	var end_date = $('#end_date').val();
	$('#tablebody').html("");
	$.ajax({
		type : 'get',
		url : 'StatisticsAction/List',
		data : {
			page : page,
			limit : limit,
			type : type,
			userId : userId,
			start_date : start_date,
			end_date : end_date
		},
		dataType : 'json',
		success : function(array) {
			if(array.length >= 1){
				var totalnum = array[0];
				var totalp = 1;
				if (totalnum != 0) {
					totalp = Math.ceil(totalnum / limit);
				}
				options = {
					totalPages : totalp
				};
				$('#pageDivider').bootstrapPaginator(options);
				pageDisplayUpdate(page, totalp);
				var tableStr = "";
				if(array.length >=2){
					for (var i = 1; i < array.length; i++) {
						var obj = array[i];
						var rsName = decodeURIComponent(obj.rsname);
						var resourceId = obj.rsid;
						var resourceStr = "";
						var iconStr = new Array("running", "stopped");
						var nameStr = new Array("正常运行", "已经销毁");
						var stateStr = '<td><span class="icon-status icon-'
								+ iconStr[obj.status] + '" name="stateicon">'
								+ '</span><span name="stateword">' + nameStr[obj.status]
								+ '</span></td>';
						if (type == "instance") {
							resourceStr = "<a class='viewdetail' href='javascript:void(0)'>i-"
									+ resourceId.substr(0, 8) + "</a>";
						} else if (type == "volume") {
							resourceStr = "<a class='viewdetail' href='javascript:void(0)'>vol-"
									+ resourceId.substr(0, 8) + "</a>";
						} else if (type == "snapshot") {
							resourceStr = "<a class='viewdetail' href='javascript:void(0)'>ss-"
									+ resourceId.substr(0, 8) + "</a>";
						}
						var thistr = '<tr type="'
								+ type
								+ '" resourceId="'
								+ resourceId
								+ '"><td>'
								+ resourceStr
								+ '</td><td>'
								+ rsName
								+ '</td>'
								+ stateStr
								+ '<td>￥'
								+ obj.totalprince
								+ '元</td><td class="time">'
								+ obj.createDate
								+ '</td><td class="time">'
								+ decodeURIComponent(obj.timeused)
								+ '</td></tr>';
						tableStr += thistr;
					}
					$('#tablebody').html(tableStr);
				}
			}
		}
	});
}


$("#select_name").on('change', function(){
	reloadList(1);
});

$("#select_user").on('change', function(){
	reloadList(1);
});

$('.find').on('click',function(event){
	reloadList(1);
});

//弹出日期输入框
$('.start-time').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0
});
//弹出日期输入框
$('.end-time').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0
});
//清空起始日期输入框
$('.start-remove').on('click',function(event){
	$('.start-form').val('');
	$('#start_date').val('');
});
//清空终止日期输入框
$('.end-remove').on('click',function(event){
	$('.end-form').val('');
	$('#end_date').val('');
});