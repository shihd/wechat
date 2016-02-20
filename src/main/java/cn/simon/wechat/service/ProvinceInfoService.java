package cn.simon.wechat.service;

import java.util.Collection;

import cn.simon.wechat.model.ProvinceInfo;

public interface ProvinceInfoService {

	public Collection<ProvinceInfo> findProvinceInfoByCountry(String countryId);

	public ProvinceInfo findProvinceInfo(String provinceId);
}
