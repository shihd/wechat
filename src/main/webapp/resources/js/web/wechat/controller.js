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
		if(result == "failure")
			$("#msgContent").html("用户名或密码错误！");
		else
			$("#msgContent").html("登录失败，请稍候再试！");
		$("#message").modal();
		return;
	}
	window.location.href = encodeURI("controller/index.html");
}

function newUser(){
	window.location.href = encodeURI("register.html");
}

// 新用户注册
function register(){
	if (isEmpty($("#username").val())) {
		$("#msgContent").html("请输入您的用户名！");
		$("#message").modal();
		return;
	}
	var password = $("#userpass").val();
	if (isEmpty(password)) {
		$("#msgContent").html("请设置密码！");
		$("#message").modal();
		return;
	}
	if(password.length<6 || password.length>25){
		$("#msgContent").html("密码必须包含数字和字符，且必须在6-25位！");
		$("#message").modal();
		return;
	}
	// 验证密码是否符合要求
	var passReg = new RegExp("[a-zA-Z]");
	var len = passReg.test(password);
	passReg = new RegExp("[0-9]");
	len = passReg.test(password);
	passReg = new RegExp("((?=[\x21-\x7e]+)[^A-Za-z0-9])");
	len = passReg.test(password);
	if(!len){
		$("#msgContent").html("密码必须包含数字和字符，且必须在6-25位！");
		$("#message").modal();
		return;
	}
	if (isEmpty($("#userpass2").val())) {
		$("#msgContent").html("请输入确认密码！");
		$("#message").modal();
		return;
	}
	if ($("#userpass").val() != $("#userpass2").val()){
		$("#msgContent").html("二次输入的密码不一致！");
		$("#message").modal();
		return;
	}
	var email = $("#email").val();
	if (isEmpty(email)) {
		$("#msgContent").html("请输入邮箱！");
		$("#message").modal();
		return;
	}
	// 验证邮箱的有效性
	var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(!emailReg.test(email)){
		$("#msgContent").html("请输入有效的e-mail！");
		$("#message").modal();
		return;
	}
	var result = $simon.post({
		url : path + "/userInfo/register",
		data : {
			userId : $("#username").val(),
			userPass : password,
			email : email
		}
	});
	
	if (result != "success"){
		if(result == "-1")
			$("#msgContent").html("请问失败/网络异常，请稍候再试！");
		else if(result == "-3")
			$("#msgContent").html("用户名已经存在！");
		else if(result == "-4")
			$("#msgContent").html("验证码错误！");
		else if(result == "-5")
			$("#msgContent").html("无效邮箱或手机号！");
		else if(result == "-6")
			$("#msgContent").html("无效网关！");
		else if(result == "-7")
			$("#msgContent").html("密码必须包含数字和字符，且必须在6-25位！");
		else if(result == "-12")
			$("#msgContent").html("获取验证码次数过多，请稍候再试！");
		else if(result == "-13")
			$("#msgContent").html("验证码过期！");
		else if(result == "-25")
			$("#msgContent").html("必须由数字字母下划线/手机号/邮箱组成！");
		else
			$("#msgContent").html("系统忙，请稍候再试！");
		$("#message").modal();
		return;
	}else{
		$("#msgContent").html("注册成功，可以登录啦！");
		$("#message").modal();
		window.location.href = encodeURI("controller.html");
	}
}

