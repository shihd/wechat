package cn.simon.wechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.simon.wechat.model.OrderInfo;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, String> {

}
