package cn.simon.wechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.simon.wechat.model.PromotionOrder;

@Repository
public interface PromotionOrderRepository extends JpaRepository<PromotionOrder, String> {

}
