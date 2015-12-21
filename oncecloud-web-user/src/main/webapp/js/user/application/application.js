/*切换选项卡*/
$('.once-tab').on('click', '.tab-filter', function (event) {
    $('li', '.once-tab').removeClass('active');
    $(this).addClass('active');
    tabchange();
});
tabchange();
function tabchange(){
	var type=getAccountType();
	if(type==0){
		$("#cost_overview").show();
		$("#cost_detail").hide();
		$("#cost_detail").css('visibility','hidden');
    }else{
    	$("#cost_overview").hide();
    	$("#cost_detail").show();
		$("#cost_detail").css('visibility','visible');
    }
}
/*获取类型*/
function getAccountType(){
	var accounttype = $('.once-tab').find('.active').attr("type");
	if(accounttype == 'cost_overview'){
		accounttype = 0;
	}else{
		accounttype = 1;
	}
	return accounttype;
}