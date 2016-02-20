package cn.simon.wechat.service;

import java.util.Collection;

import cn.simon.wechat.model.CityInfo;

public interface CityInfoService {

	public Collection<CityInfo> findAllCityInfo();

	public CityInfo findCityInfo(String cityId);

	public Collection<CityInfo> findCityInfoByProvinceId(String provinceId);
}
