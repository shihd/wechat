package cn.simon.wechat.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.simon.wechat.model.ProvinceInfo;
import cn.simon.wechat.repository.ProvinceInfoRepository;
import cn.simon.wechat.service.ProvinceInfoService;

@Service
public class ProvinceInfoServiceImpl implements ProvinceInfoService {

	@Autowired
	private ProvinceInfoRepository provinceInfoRepository;

	@Override
	public Collection<ProvinceInfo> findProvinceInfoByCountry(String countryId) {
		// TODO Auto-generated method stub
		return this.provinceInfoRepository.findByCountryIdOrderByProvinceIdAsc(countryId);
	}

	@Override
	public ProvinceInfo findProvinceInfo(String provinceId) {
		// TODO Auto-generated method stub
		return this.provinceInfoRepository.findOne(provinceId);
	}

}
