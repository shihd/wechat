package cn.simon.wechat.service;

import java.util.Collection;

import cn.simon.wechat.model.RepairOrder;

public interface RepairOrderService {

	public void addRepairOrder(RepairOrder repairOrder);

	public Collection<RepairOrder> findRepairOrderByUserId(String userId);

	public void deleteRepairOrder(String orderId);
}
