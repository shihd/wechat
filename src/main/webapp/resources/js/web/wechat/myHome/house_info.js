var path = "";
var openid = "";
var house = "";
var city = "";
var court = "";

$(document).ready(function() {
	init();
	back();
});

function init() {
	path = $.cookie("path");
	openid = $simon.getQueryVariable("openid");
	house = $simon.getQueryVariable("houseid");
	city = $simon.getQueryVariable("cityid");
	court = $simon.getQueryVariable("courtid");
}

function back() {
	$("#back").bind(
			"click",
			function() {
				window.location = path
						+ "/wechat/myHome/court_chose.html?openid=" + openid
						+ "&houseid=" + house + "&cityid=" + city;
			});
}

function sqrz() {
	if (isEmpty($("#houseName").val())) {
		alert("房屋别名不能为空");
	}

	var houseName = $("#houseName").val();
	var building = $("#building").val();
	var no = $("no").val();

	$simon.post({
		url : path + "/" + openid + "/houseInfo",
		data : {
			houseName : houseName,
			city : city,
			court : court,
			building : building,
			no : no
		}
	});

	window.location = path + "/wechat/myHome/house_chose.html?openid=" + openid
			+ "&house=" + house;
}