var path = "";
var openid = "";
var house = "";
var city = "";

$(document).ready(function() {
	init();
	back();
	courtList();
	courtChose();
});

function init() {
	path = $.cookie("path");
	openid = $simon.getQueryVariable("openid");
	house = $simon.getQueryVariable("houseid");
	city = $simon.getQueryVariable("cityid");
}

function back() {
	$("#back").bind(
			"click",
			function() {
				window.location = path
						+ "/wechat/myHome/add_house_address.html?openid="
						+ openid + "&houseid=" + house;
			});
}

function courtList() {
	var courts = $simon.get({
		url : path + "/courtInfo",
		data : {
			cityId : city
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
	$(".court li").bind(
			"click",
			function() {
				window.location = path
						+ "/wechat/myHome/house_info.html?openid=" + openid
						+ "&houseid=" + house + "&cityid=" + city + "&courtid="
						+ $(this).attr("id");
			});
}