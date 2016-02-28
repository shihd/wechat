$(document).ready(function() {
	showList();
	addHouse();
});

function addHouse() {
	$("#addHouse").bind("click", function() {
		$("#content").load("service/city_chose.html");
	});
}

function showList() {
	var html = "";
	var list = $simon.get({
		url : path + "/" + userId + "/houseInfo"
	});
	if (!isEmpty(list)) {
		$
				.each(
						list,
						function(index, value) {
							html += "<div class=\"row\" style=\"margin-top: 10px; border-top: 2px solid #5490e1; margin-left: 10px; margin-right: 10px; background-color: #fff; padding-left: 2em; padding-right: 2em\">";
							html += "<div style=\"height: 30px; line-height: 30px; font-size: 15px; color: #5490e1\">";
							html += value.court;
							html += "&nbsp;";
							html += value.building + "-" + value.no;
							html += "</div><div style=\"height: 1px; background-color: #dcdcdc; margin-left: -1em; margin-right: -1em\"></div><div id=\"";
							html += value.houseId;
							html += "\"><div style=\"float: left\" onclick=\"chooseHouse('";
							html += value.houseId;
							html += "')\"><div style=\"height: 30px; line-height: 30px;\">";
							html += value.country + "&nbsp;" + value.province
									+ "&nbsp;" + value.city;
							html += "</div><div style=\"height: 30px; line-height: 30px;\">";
							html += (value.address == null ? "" : value.address);
							html += "</div></div><div style=\"float: right; line-height: 75px; font-size: 30px; color: #5490e1\" onclick=\"housePerformance('";
							html += value.houseId;
							html += "')\"><i class=\"fa fa-angle-double-right\"></i></div></div></div>";
						});
		$("#addressList").html(html);
	}
}

function chooseHouse(house) {
	houseId = house;
	$("#content").load("service/repair.html");
}

function housePerformance(houseNo) {
	houseCode = houseNo;
	$("#content").load("service/house_performance.html");
}