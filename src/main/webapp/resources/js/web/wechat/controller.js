//var path = "http://shengfuluo.com/wechat";
//var path = "http://192.168.1.103:8080/wechat";
var path = "http://localhost:8080/wechat";

$(document).ready(function() {

});

// 用户登录
function login(){
	if (isEmpty($("#username").val())) {
		$("#msgContent").html("请输入您的用户名！");
		$("#message").modal();
		return;
	}
	if (isEmpty($("#userpass").val())) {
		$("#msgContent").html("请输入您的密码！");
		$("#message").modal();
		return;
	}
	var result = $simon.get({
		url : path + "/userInfo/" + $("#username").val() + "/login",
		data : {
			userPass : $("#userpass").val()
		}
	});
	
	if (result != "success"){
		$("#msgContent").html("用户名或密码错误！");
		$("#message").modal();
		return;
	}
	window.location.href = encodeURI("controller/index.html");
}

function createUser(){
	window.location.href = encodeURI("register.html");
}

// 新用户注册
function register(){
	if (isEmpty($("#username").val())) {
		$("#msgContent").html("请输入您的用户名！");
		$("#message").modal();
		return;
	}
	if (isEmpty($("#userpass").val())) {
		$("#msgContent").html("请设置密码！");
		$("#message").modal();
		return;
	}
	if (isEmpty($("#userpass2").val())) {
		$("#msgContent").html("请确认密码！");
		$("#message").modal();
		return;
	}
	if ($("#userpass").val() != $("#userpass2").val()){
		$("#msgContent").html("二次输入的密码不一致！");
		$("#message").modal();
		return;
	}
	var result = $simon.post({
		url : path + "/userInfo/register",
		data : {
			userId : $("#username").val(),
			userPass : $("#userpass").val()
		}
	});
}
