package cn.simon.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import cn.simon.wechat.repository.OrderInfoRepository;
import cn.simon.wechat.service.OrderInfoService;

public class OrderInfoServiceImpl implements OrderInfoService {
	
	@Autowired
	private OrderInfoRepository orderInfoRepository;
	
	public void addOrderInfo() {
		
	}
}
