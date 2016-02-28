var path = "";
var openid = "";
var house = "";

$(document).ready(function() {
	init();
	addHouse();
	back();
	houseList();
	// wx.config({
	// debug: true, //
	// 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	// appId: 'wx6d3f0b437baeb045', // 必填，公众号的唯一标识
	// timestamp: (new Date()).valueOf(), // 必填，生成签名的时间戳
	// nonceStr: '', // 必填，生成签名的随机串
	// signature: '',// 必填，签名，见附录1
	// jsApiList: [] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	// });
	// wx.hideOptionMenu();
});

function init() {
	path = $.cookie("path");
	openid = $simon.getQueryVariable("openid");
	house = $simon.getQueryVariable("houseid");
}

function addHouse() {
	$("#addHouse").bind(
			"click",
			function() {
				window.location = path
						+ "/wechat/myHome/add_house_address.html?openid="
						+ openid + "&houseid=" + house;
			});
}

function back() {
	$("#back").bind(
			"click",
			function() {
				window.location = path + "/wechat/myHome.html?openid=" + openid
						+ "&houseid=" + house;
			});
}

function houseList() {
	var houses = $simon.get({
		url : path + "/" + openid + "/houseInfo"
	});

	var html = "";
	html += "<ul class=\"court\">";

	$.each(houses, function(index, value) {
		html += "<li id=\"" + value.id.houseId + "\">" + value.houseName;
		if (value.status == '0') {
			html += "(待审核)";
		}
		html += "</li>";
	});

	html += "</ul>";

	$("#houseList").html(html);
}