package cn.simon.wechat.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.simon.wechat.model.RepairOrder;

public interface RepairOrderRepository extends JpaRepository<RepairOrder, String> {

	public Collection<RepairOrder> findByUserIdOrderByCreateDateDesc(String userId);
}
