var provinceId = "";
var provinceName = "";
var cityId = "";
var cityName = "";

$(document).ready(function() {
	$("#userName").val($simon.operator.userName);
	$("#mobile").val($simon.operator.mobile);
	init();
	imgFile();
});

function init() {
	var house = $simon.get({
		url : path + "/userInfo/" + userId + "/houseInfo/default"
	});
	if (!isEmpty(house)) {
		$("#address").val(house.address);
		changeProvince(house.provinceId, house.province);
		changeCity(house.cityId, house.city);
	}
}

function changeProvince(proId, proName) {
	provinceId = proId;
	provinceName = proName;
	$("#province").html(provinceName);
	cityId = "";
	$("#city").html("城市");
	var cityList = $simon.get({
		url : path + "/provinceInfo/" + provinceId + "/cityInfo"
	});
	var html = "";
	$.each(cityList, function(index, value) {
		html += "<li><a href=\"javascript:changeCity('" + value.cityId + "','"
				+ value.cityName + "')\">" + value.cityName + "</a></li>";
	});
	$("#cityList").html(html);
}

function changeCity(cId, cName) {
	cityId = cId;
	cityName = cName;
	$("#city").html(cityName);
}

function submitRepairInfo() {
	if (isEmpty($("#userName").val())) {
		$("#msgContent").html("请输入您的真实姓名！");
		$("#message").modal();
		return;
	}
	if (isEmpty($("#mobile").val())) {
		$("#msgContent").html("请输入您的手机号码！");
		$("#message").modal();
		return;
	}
	if (isEmpty(provinceId)) {
		$("#msgContent").html("请选择省份！");
		$("#message").modal();
		return;
	}
	if (isEmpty(cityId)) {
		$("#msgContent").html("请选择城市！");
		$("#message").modal();
		return;
	}
	if (isEmpty($("#address").val())) {
		$("#msgContent").html("请输入您的详细地址！");
		$("#message").modal();
		return;
	}
	if (isEmpty($("#proDesc").val())) {
		$("#msgContent").html("请输入您的问题描述！");
		$("#message").modal();
		return;
	}

	modifyUserInfo();
	var house = modifyHouseInfo();

	var address = $("#address").val();
	var content = $("#proDesc").val();
	$simon.post({
		url : path + "/userInfo/" + userId + "/repairOrder",
		data : {
			houseId : house.houseId,
			content : content
		}
	});

	$("#msgContent").html("维修申请已提交！");
	$("#message").modal();
}

function modifyUserInfo() {
	var userName = $("#userName").val();
	var mobile = $("#mobile").val();

	$simon.operator = $simon.put({
		url : path + "/userInfo/" + userId,
		data : {
			userName : userName,
			mobile : mobile
		}
	});
}

function modifyHouseInfo() {
	var address = $("#address").val();
	var house = $simon.put({
		url : path + "/userInfo/" + userId + "/houseInfo",
		data : {
			provinceId : provinceId,
			province : provinceName,
			cityId : cityId,
			city : cityName,
			address : address
		}
	});
	return house;
}

function imgFile() {
	$("#imgFile").bind("click", function() {
		$("#file").click();
	});
}

function showFile() {
	var file = $("#file").val();
	var fileName = getFileName(file);
	var html = fileName
			+ "&nbsp;&nbsp;<a href=\"javascript:emptyFile()\">删除</a>";
	$("#fileInfo").html(html);
}

function getFileName(o) {
	var pos = o.lastIndexOf("\\");
	return o.substring(pos + 1);
}

function emptyFile() {
	$("#fileInfo").html("");
	$("#file").val("");
}
