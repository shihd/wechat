$(document).ready(function() {
	$("#modifyUserInfo").html("");
	$("#userName").val($simon.operator.userName);
	$("#mobile").val($simon.operator.mobile);
});

function modifyUserInfo() {
	var userName = $("#userName").val();
	var mobile = $("#mobile").val();
	if (userName == "") {
		$("#modifyUserInfo").html("<font color=\"red\">请输入您的真实姓名！</font>")
		return;
	}
	if (mobile == "") {
		$("#modifyUserInfo").html("<font color=\"red\">请输入您的手机号码！</font>")
		return;
	}

	$simon.operator = $simon.put({
		url : path + "/userInfo/" + userId,
		data : {
			userName : userName,
			mobile : mobile
		}
	});

	$("#content").load("service/repair.html");
}
