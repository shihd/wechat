$(document).ready(function() {
	init();
	setDefault();
	deleteAddress();
});

function init() {

	if (!isEmpty(houseCode)) {
		var house = $simon.get({
			url : path + "/houseInfo/" + houseCode
		});
		if (!isEmpty(house)) {
			$("#city").html(
					house.country + "&nbsp;" + house.province + "&nbsp;"
							+ house.city);
			$("#addressDetail")
					.html(house.address == null ? "" : house.address);
			$("#court").html(
					house.court + "&nbsp;" + house.building + "-" + house.no);
		}
	}
}

function setDefault() {
	$("#setDefault").bind("click", function() {
		$simon.put({
			url : path + "/" + userId + "/houseInfo/" + houseCode
		});
		$("#content").load("service/address_list.html");
	});
}

function deleteAddress() {
	$("#deleteAddress").bind("click", function() {
		$simon.del({
			url : path + "/" + userId + "/houseInfo/" + houseCode
		});
		$("#content").load("service/address_list.html");
	});
}