$(document).ready(function() {
	
});

function sqrz() {

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