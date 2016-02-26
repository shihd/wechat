$(document).ready(function() {
	cityList();
	cityChose();
});

function cityList() {

	var provinces = $simon.get({
		url : path + "/provinceInfo"
	});

	var cities = $simon.get({
		url : path + "/cityInfo"
	});

	var html = "";
	html += "<ul class=\"province\">";

	$.each(provinces, function(index, value) {
		html += "<li id=\"" + value.provinceId + "\"><p>" + value.provinceName
				+ "</p><ul class=\"city\">";
		$.each(cities, function(cityIndex, cityInfo) {
			if (cityInfo.provinceId == value.provinceId) {
				html += "<li id=\"" + cityInfo.cityId + "\">"
						+ cityInfo.cityName + "</li>";
			}
		});
		html += "</ul></li>";
	});

	html += "</ul>";

	$("#cityList").html(html);
}

function cityChose() {
	$(".city li").bind("click", function() {
		cityId = $(this).attr("id");
		$("#content").load("service/court_chose.html");
	});
}