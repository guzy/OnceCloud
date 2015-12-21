reloadList(1);

function reloadList(page) {
	var limit = $("#limit").val();
	var search = $('#search').val();
	var type = $('.once-tab').find('.active').attr("type");
	getImageList(page, limit, search, type);
	if (page == 1) {
		options = {
			currentPage : 1
		};
		$('#pageDivider').bootstrapPaginator(options);
	}
	allDisable();
}

function removeAllCheck() {
	$('input[name="imagerow"]').each(function() {
				$(this).attr("checked", false);
				$(this).change();
			});
}

function allDisable() {
	$("#delete").addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function(event) {
			event.preventDefault();
			allDisable();
			var count = 0;
			var pooluuid;
			$('input[name="imagerow"]:checked').each(function(index) {
						count++;
					});
			if (count > 0) {
				$("#delete").removeClass('btn-forbidden');
			}
		});

$('.once-tab').on('click', '.tab-filter', function(event) {
			$('li', '.once-tab').removeClass('active');
			$(this).addClass('active');
			if ($(this).attr('type') == 'system') {
				$('.btn-group').hide();
			} else {
				$('.btn-group').show();
			}
			reloadList(1);
		});

function getImageList(page, limit, search, type) {
	$('#tablebody').html("");
	$.ajax({
		type : 'get',
		url : '/ImageAction/ImageList',
		data : {
			page : page,
			limit : limit,
			search : search,
			type : type
		},
		dataType : 'json',
		success : function(array) {
			if (array.length >= 1) {
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
				for (var i = 1; i < array.length; i++) {
					var obj = array[i];
					var imageName = decodeURIComponent(obj.imagename);
					var imageUId = obj.imageid;
					var imageSize = obj.imagesize;
					var imageplatform = decodeURIComponent(obj.imageplatform);
					var createDate = obj.createDate;
					var showid = "img-" + imageUId.substring(0, 8);
					var type = $('.once-tab').find('.active').attr("type");
					$('#image-area').text("可见范围");
					var typeStr = "公有";
					typeStr = "私有";
					var poolName = obj.poolname;
					var stateStr = '<td><span class="icon-status icon-running" name="stateicon">'
							+ '</span><span name="stateword">可用</span></td>';
					var mytr = '<tr imageUId="'
							+ imageUId
							+ '" imageName="'
							+ imageName
							+ '" imageType="'
							+ type
							+ '"><td class="rcheck"><input type="checkbox" name="imagerow"></td>'
							+ '<td><a class="id">' + showid
							+ '</a></td><td>' + imageName + '</td><td>'
							+ imageSize + '</td><td>' + imageplatform + '</td>'
							+ stateStr + '<td>' + typeStr
							+ '</td><td class="time">' + createDate + '</td>';
					mytr += '</tr>';
					tableStr = tableStr + mytr;
				}
				$('#tablebody').html(tableStr);
			}
		}
	});
}

$('#tablebody').on('click', '.id', function (event) {
    event.preventDefault();
    var imageuuid = $(this).parent().parent().attr('imageUId');
    var imagetype = $(this).parent().parent().attr('imageType');
    var form = $("<form></form>");
    form.attr("action", "/image/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="imageUuid" value="' + imageuuid + '" />');
    var input1 = $('<input type="text" name="imageType" value="' + imagetype + '" />');
    form.append(input);
    form.append(input1);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#select').on('click', function(){
	var selectFlag = $('#select').text();
	if(selectFlag == '全选'){
		$("input[name='imagerow']").each(function(){
			this.checked=true;
		});	
		$('#select').text("取消");
	}else if(selectFlag == '取消'){
		$("input[name='imagerow']").each(function(){
			this.checked=false;
		});	
		$('#select').text("全选");
	}
});