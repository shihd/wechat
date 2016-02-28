$(document).ready(function() {
	courtList();
	courtChose();
});

function courtList() {
	var courts = $simon.get({
		url : path + "/courtInfo",
		data : {
			cityId : cityId
		}
	});

	var html = "";
	html += "<ul class=\"court\">";

	$.each(courts,
			function(index, value) {
				html += "<li id=\"" + value.courtId + "\">" + value.courtName
						+ "</li>";
			});

	html += "</ul>";

	$("#courtList").html(html);
}

function courtChose() {
	$(".court li").bind("click", function() {
		courtId = $(this).attr("id");
		$("#content").load("service/house_info.html");
	});
}