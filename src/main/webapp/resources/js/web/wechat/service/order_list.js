$(document).ready(function() {
	init();
});

function init() {
	var repairOrders = $simon.get({
		url : path + "/userInfo/" + userId + "/repairOrder"
	});
	if (!isEmpty(repairOrders)) {
		var html = "";
		$
				.each(
						repairOrders,
						function(index, value) {
							html += "<div style=\"margin-bottom: 10px\">";
							html += "<div class=\"row\" style=\"background-color: #ffffff\">";
							html += "<div class=\"col-xs-5\" style=\"line-height: 44px; font-size: 18px; color: #000000\">";
							if (value.state == "1") {
								html += "订单已完成";
							} else {
								html += "订单未完成";
							}
							html += "</div>";
							html += "<div class=\"col-xs-7\" style=\"padding-right: 1em; line-height: 44px; font-size: 15px; color: #000000\">"
									+ formatdateTime(value.createDate)
									+ "</div>";
							html += "</div>";
							html += "<div class=\"row\" style=\"height: 1px; background-color: #ababab\"></div>";
							html += "<div class=\"row\" style=\"padding-left: 1em; padding-right: 1em; background-color: #ffffff\">";
							html += "<div class=\"col-xs-12\" style=\"font-size: 15px; line-height: 30px;\">";
							html += "订单编号：<span>" + value.orderId + "</span>";
							html += "</div>";
							html += "</div>";
							html += "<div class=\"row\" style=\"padding-left: 1em; padding-right: 1em; background-color: #ffffff\">";
							html += "<div class=\"col-xs-12\" style=\"font-size: 15px; line-height: 30px;\">";
							html += value.content + "</div>";
							html += "</div>";
							html += "<div class=\"row\" style=\"background-color: #ffffff; padding-bottom: 10px\">";
							html += "<div class=\"col-xs-8\"></div>";
							html += "<div class=\"col-xs-4\">";
							html += "<input type=\"button\" class=\"btn btn-default\" value=\"删 除\" onclick=\"deleteOrder('"
									+ value.orderId + "')\" />";
							html += "</div>";
							html += "</div>";
							html += "</div>";
						});
		$("#orderList").html(html);
	} else {
		$("#orderList").html("");
	}
}

function deleteOrder(orderId) {
	$simon.del({
		url : path + "/repairOrder/" + orderId
	});
	init();
}

function formatdateTime(time) {
	var t = new Date(time);
	var tf = function(i) {
		return (i < 10 ? '0' : '') + i
	};
	var format = 'yyyy-MM-dd HH:mm:ss';
	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
		switch (a) {
		case 'yyyy':
			return tf(t.getFullYear());
			break;
		case 'MM':
			return tf(t.getMonth() + 1);
			break;
		case 'mm':
			return tf(t.getMinutes());
			break;
		case 'dd':
			return tf(t.getDate());
			break;
		case 'HH':
			return tf(t.getHours());
			break;
		case 'ss':
			return tf(t.getSeconds());
			break;
		}
	});
}
