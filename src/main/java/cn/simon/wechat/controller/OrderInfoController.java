package cn.simon.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.simon.wechat.model.OrderInfo;
import cn.simon.wechat.service.OrderInfoService;

@RestController
public class OrderInfoController {

	@Autowired
	private OrderInfoService orderInfoService;

	@RequestMapping(value = "/orderInfo", method = RequestMethod.POST)
	public void addOrderInfo(OrderInfo orderInfo) {
		this.orderInfoService.addOrderInfo(orderInfo);
	}
}
