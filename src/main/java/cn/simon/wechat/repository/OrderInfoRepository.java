package cn.simon.wechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.simon.wechat.model.OrderInfo;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, String> {

}
