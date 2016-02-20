var path = "";
var openid = "";
var house = "";

$(document).ready(function() {
	init();
	back();
	cityList();
	cityChose();
});

function init() {
	path = $.cookie("path");
	openid = $simon.getQueryVariable("openid");
	house = $simon.getQueryVariable("houseid");
}

function back() {
	$("#back").bind(
			"click",
			function() {
				window.location = path
						+ "/wechat/myHome/house_chose.html?openid=" + openid
						+ "&houseid=" + house;
			});
}

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
	$(".city li")
			.bind(
					"click",
					function() {
						window.location = path
								+ "/wechat/myHome/court_chose.html?openid="
								+ openid + "&houseid=" + house + "&cityid="
								+ $(this).attr("id");
					});
}