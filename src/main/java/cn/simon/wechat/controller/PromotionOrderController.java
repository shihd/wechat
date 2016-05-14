package cn.simon.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.simon.wechat.model.OrderInfo;
import cn.simon.wechat.model.PromotionOrder;
import cn.simon.wechat.service.OrderInfoService;
import cn.simon.wechat.service.PromotionOrderService;

@RestController
public class PromotionOrderController {

	@Autowired
	private PromotionOrderService promotionOrderService;

	@RequestMapping(value = "/promotionOrder", method = RequestMethod.POST)
	public void addOrderInfo(PromotionOrder promotionOrder) {
		this.promotionOrderService.addPromotionOrder(promotionOrder);
	}
}
