package cn.simon.wechat.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.simon.wechat.model.CityInfo;

public interface CityInfoRepository extends JpaRepository<CityInfo, String> {

	public Collection<CityInfo> findByOrderByCityIdAsc();

	public Collection<CityInfo> findByProvinceIdOrderByCityIdAsc(String provinceId);
}
