package cn.simon.wechat.service.impl;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.simon.wechat.model.RepairOrder;
import cn.simon.wechat.repository.RepairOrderRepository;
import cn.simon.wechat.service.RepairOrderService;

@Service
public class RepairOrderServiceImpl implements RepairOrderService {

	@Autowired
	private RepairOrderRepository repairOrderRepository;

	@Override
	public void addRepairOrder(RepairOrder repairOrder) {
		// TODO Auto-generated method stub
		repairOrder.setCreateDate(new Date());
		repairOrder.setOrderId("R" + repairOrder.getCreateDate().getTime());
		repairOrder.setState("0");
		this.repairOrderRepository.save(repairOrder);
	}

	@Override
	public Collection<RepairOrder> findRepairOrderByUserId(String userId) {
		// TODO Auto-generated method stub
		return this.repairOrderRepository.findByUserIdOrderByCreateDateDesc(userId);
	}

	@Override
	public void deleteRepairOrder(String orderId) {
		// TODO Auto-generated method stub
		this.repairOrderRepository.delete(orderId);
	}

}
