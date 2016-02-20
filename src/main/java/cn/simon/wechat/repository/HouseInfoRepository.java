package cn.simon.wechat.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.simon.wechat.model.HouseInfo;

public interface HouseInfoRepository extends JpaRepository<HouseInfo, String> {

	public Collection<HouseInfo> findByCityAndCourtAndBuildingAndNo(String city, String court, String building,
			String no);
	
}
