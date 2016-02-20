package cn.simon.wechat.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.simon.wechat.model.ProvinceInfo;

public interface ProvinceInfoRepository extends JpaRepository<ProvinceInfo, String> {

	public Collection<ProvinceInfo> findByCountryIdOrderByProvinceIdAsc(String provinceId);
}
