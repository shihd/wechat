package cn.simon.wechat.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.simon.wechat.model.RepairOrder;
import cn.simon.wechat.service.RepairOrderService;

@RestController
public class RepairOrderController {

	@Autowired
	private RepairOrderService repairOrderService;

	@RequestMapping(value = "/userInfo/{userId}/repairOrder", method = RequestMethod.POST)
	public void addRepairOrder(@PathVariable("userId") String userId, RepairOrder repairOrder) {
		this.repairOrderService.addRepairOrder(repairOrder);
	}

	@RequestMapping(value = "/userInfo/{userId}/repairOrder", method = RequestMethod.GET)
	public Collection<RepairOrder> findRepairOrder(@PathVariable("userId") String userId) {
		return this.repairOrderService.findRepairOrderByUserId(userId);
	}

	@RequestMapping(value = "/repairOrder/{orderId}", method = RequestMethod.DELETE)
	public void addRepairOrder(@PathVariable("orderId") String orderId) {
		this.repairOrderService.deleteRepairOrder(orderId);
	}
}
