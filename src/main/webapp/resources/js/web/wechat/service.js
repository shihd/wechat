//var path = "http://shengfuluo.com/wechat";
var path = "http://192.168.1.103:8080/wechat";
var userId = "";
var houseId = "";
var houseCode = "";
var cityId = "";
var courtId = "";

$(document).ready(function() {
	init();
	footer();
	$("#content").load("service/repair.html");
});

function init() {

	$.cookie("path", path, {
		expires : 1
	});

	userId = $simon.getQueryVariable("userId");

	if (isEmpty(userId)) {
		var code = $simon.getQueryVariable("code");

		var oauth = $simon.get({
			url : path + '/oauth2',
			data : {
				code : code
			}
		});

		var openid = oauth.openid;

		var users = $simon.get({
			url : path + "/userInfo/",
			data : {
				wechatOpenId : openid
			}
		});

		if (users != null && users.length != 0) {
			$simon.operator = users[0];
		} else {
			$simon.operator = $simon.post({
				url : path + "/userInfo",
				data : {
					wechatOpenId : openid
				}
			});
		}

		userId = $simon.operator.userId;

	} else {
		$simon.operator = $simon.get({
			url : path + "/userInfo/" + userId
		});
	}

}

function footer() {
	$("#footer>div").bind("click", function() {
		$("#footer>div").removeClass("active");
		$(this).addClass("active");
		$("#content").load($(this).attr("url"));
	});
}
