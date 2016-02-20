package cn.simon.wechat.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.simon.wechat.model.CityInfo;
import cn.simon.wechat.repository.CityInfoRepository;
import cn.simon.wechat.service.CityInfoService;

@Service
public class CityInfoServiceImpl implements CityInfoService {

	@Autowired
	private CityInfoRepository cityInfoRepository;

	@Override
	public Collection<CityInfo> findAllCityInfo() {
		// TODO Auto-generated method stub
		return this.cityInfoRepository.findByOrderByCityIdAsc();
	}

	@Override
	public CityInfo findCityInfo(String cityId) {
		// TODO Auto-generated method stub
		return this.cityInfoRepository.findOne(cityId);
	}

	@Override
	public Collection<CityInfo> findCityInfoByProvinceId(String provinceId) {
		// TODO Auto-generated method stub
		return this.cityInfoRepository.findByProvinceIdOrderByCityIdAsc(provinceId);
	}

}
