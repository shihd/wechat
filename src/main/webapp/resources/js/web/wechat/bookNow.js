var path = "http://localhost:8080/wechat";

// 提交预约
function submitBookInfo() {
	if (isEmpty($("#cityId").val())) {
		$("#msgContent").html("请选择所在城市！");
		$("#message").modal();
		return;
	}
	if (isEmpty($("#name").val())) {
		$("#msgContent").html("请输入您的真实姓名！");
		$("#message").modal();
		return;
	}
	var phone = $("#phone").val();
	if (isEmpty(phone)) {
		$("#msgContent").html("请输入您的手机号码！");
		$("#message").modal();
		return;
	}
	if(phone.length != 11){
		$("#msgContent").html("手机号码格式不正确！");
		$("#message").modal();
		return;
	}
	if (isEmpty($("#address").val())) {
		$("#msgContent").html("请输入您的详细地址！");
		$("#message").modal();
		return;
	}
	var result = $simon.post({
		url : path + "/orderInfo",
		data : {
			name : $("#name").val(),
			phone : $("#phone").val(),
			address : $("#address").val(),
			cityId :  $("#cityId").val()
		}
	});
	
	$("#msgContent").html("恭喜您预约成功，静候佳音！");
	$("#message").modal();
}

// 关闭操作
function closeBook(){
	window.location.href = encodeURI("bookNow.html");
}