var path = "http://sunflower.miniedu.com.cn/wechat";
//var path = "http://192.168.1.101:8080/wechat";
var openid = "";
var house = "";

$(document).ready(function() {
	initOperator();
	loadUserInfo();
	houseChose();
});

function initOperator() {

	$.cookie("path", path, {
		expires : 1
	});

	openid = $simon.getQueryVariable("openid");

	if (isEmpty(openid)) {
		var code = $simon.getQueryVariable("code");
		alert(code);
		var oauth = $simon.get({
			url : path + '/oauth2',
			data : {
				code : code
			}
		});

		openid = oauth.openid;
	}

	$simon.operator = $simon.get({
		url : path + "/userInfo/" + openid
	});

}

function loadUserInfo() {
	$("#headImg").attr('src', $simon.operator.headImgUrl);
	$("#nickName").html($simon.operator.nickName);
}

function houseChose() {
	$("#houseChose").bind(
			"click",
			function() {
				window.location = path
						+ "/wechat/myHome/house_chose.html?openid=" + openid
						+ "&houseid=" + house;
			});
}