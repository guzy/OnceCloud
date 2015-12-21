
$('#typetablebody').on('click', '.id', function (event) {
    event.preventDefault();
    var costtypeid = $(this).parent().parent().attr('typeid');
   // var costtypeid = 11;
    var form = $("<form></form>");
    form.attr("action", "costtype/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="costtypeid" value="' + costtypeid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#costtablebody').on('click', '.id', function (event) {
    event.preventDefault();
    var costtypeid = $(this).parent().parent().attr('typeid');
    //var costtypeid = 11;
    var form = $("<form></form>");
    form.attr("action", "cost/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="costid" value="' + costtypeid + '" />');
    form.append(input);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#profitDetail').on('click', '.uid', function (event) {
    event.preventDefault();
    var userid = $(this).parent().parent().attr('userid');
    var username = $(this).parent().parent().attr('username');
    //var costtypeid = 11;
    var form = $("<form></form>");
    form.attr("action", "userprofit/detail");
    form.attr('method', 'post');
    var input = $('<input type="text" name="userid" value="' + userid + '" />');
    var input2 = $('<input type="text" name="username" value="' + username + '" />');
    form.append(input);
    form.append(input2);
    form.css('display', 'none');
    form.appendTo($('body'));
    form.submit();
});

$('#create').on('click', function (event) {
    event.preventDefault();
    $('#CostModalContainer').load('account/create', '', function () {
        $('#CostModalContainer').modal({
            backdrop: false,
            show: true
        });
    });
});

$('#typemodify').on('click', function (event) {
    event.preventDefault();
    var boxs=$('#typetablebody :checkbox:checked');
    if(boxs.length==0){
    	alert("请选择分类！");
    	return;
    }else if(boxs.length==1){
    	$('#CostModalContainer').load('admin/modal/modifycost?type=1', '', function () {
            $('#CostModalContainer').modal({
                backdrop: false,
                show: true
            });
            
            var $box=$('#typetablebody :checkbox:checked').parent().parent();
            $('#typename').val($box.attr('typename'));
            $('#descText').val($box.attr('typedesc'));
        });
    }else{
    	alert("请选择单个分类进行操作！");
    	return;
    }
    
});

$('#typedelete').on('click', function (event) {
    var $boxs=$('#typetablebody :checkbox:checked');
	if($boxs.length==0){
    	alert("请选择分类 划分！");
    	return;
    }else{
    	var ids='';
    	$boxs.each(function(){
    		ids+=$(this).parent().parent().attr('typeid')+',';
    	});
    	ids=ids.substring(0, ids.length-1);
    	$.ajax({
    		type: 'post',
    		url: "CostAction/DeleteType",
    		data: {
    			costTypeIds:ids
    		},
    		dataType: 'json',
    		success: function(data){
    			load_typeList();
    		}
    	});
    }
    
	
});
