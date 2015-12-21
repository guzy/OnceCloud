$(document).ready(function () {
	
	var activeArea = $('#activeArea').val();
	cleanActiveArea();
	pointActiveArea(activeArea);
	
	$("#modifyuserinfo").on('click', function(event) {
		event.preventDefault();
	    $('#userModalContainer').load($(this).attr('url'), '',
	        function () {
	            $('#userModalContainer').modal({
	                backdrop: false,
	                show: true
	            });
	    	});
	});
	
});

function cleanActiveArea(){
	$('#resource').removeClass('in');
	$('#virtual').removeClass('in');
	$('#container').removeClass('in');
	$('#application').removeClass('in');
	$('#system').removeClass('in');
}

function pointActiveArea(activeArea){
	$('#'+activeArea).addClass('in');
}