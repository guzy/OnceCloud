/**
 * 仓库管理
 */
var group = "all";
reloadList(1);

function reloadList(page) {
	var limit = $('#limit').val();
	var search = $('#search').val();
	getRepoList(page, limit, search);
	if (page == 1) {
		options = {
			currentPage : 1
		};
		$('#pageDivider').bootstrapPaginator(options);
	}
	allDisable();
}

function removeAllCheck() {
	$('input[name="vmrow"]:checked').each(function() {
		$(this)[0].checked = false;
		$(this).change();
	});
}

function allDisable() {
	//$("#startup").attr("disabled", true).addClass('btn-disable');
	//$("#restart").addClass('btn-forbidden');
}

$('#tablebody').on('change', 'input:checkbox', function(event) {
	event.preventDefault();
	allDisable();
	var running = 0;
	var process = 0;
	var stopped = 0;
	var total = 0;
	$('input[name="vmrow"]:checked').each(function() {
		var stateicon = $(this).parent().parent()
				.find('[name="stateicon"]');
		if (stateicon.hasClass('icon-running')) {
			running++;
		} else if (stateicon.hasClass('icon-stopped')) {
			stopped++;
		} else {
			process++;
		}
		total++;
	});
	if (total > 0 && process == 0) {
		$("#destroy").removeClass('btn-forbidden');
		
		if (running > 0 && stopped == 0) {
			$("#shutdown").attr('disabled', false).removeClass('btn-disable');
			$("#restart").removeClass('btn-forbidden');
		} else if (running == 0 && stopped > 0) {
			$("#startup").attr('disabled', false).removeClass('btn-disable');
		}
	}
	if (total == 1 && stopped == 1) {
		$("#backup").removeClass('btn-forbidden');
		$("#image").removeClass('btn-forbidden');
		$('#adjust').removeClass('btn-forbidden');
	}
});

function getRepoList(page, limit, search) {
	$('#tablebody').html("");
	$.ajax({
		type : 'get',
		url : '/RepositoryAction/ContainerRepoList',
		data : {
			page : page,
			limit : limit,
			search : search,
			groupUuid : group
		},
		dataType : 'json',
		success : function(array) {
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
				var repouuid = obj.repoid;
				var reponame = obj.reponame;
				var repoversion = obj.repoversion;
				var repoaddress = obj.repoaddress;
				var thistr = '<tr rowid="'
						+ repouuid + '"><td name="repoid">img-'
						+ repouuid.substr(0,7) + '</td><td name="reponame">' 
						+ reponame + '</td><td name="repoversion">' 
						+ repoversion + '</td><td name="repoaddress">' 
						+ repoaddress + '</td></tr>';
				tableStr += thistr;
			}
			$('#tablebody').html(tableStr);
		}
	});
}
