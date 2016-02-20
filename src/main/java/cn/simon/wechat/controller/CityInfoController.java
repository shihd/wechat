package cn.simon.wechat.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.simon.wechat.model.CityInfo;
import cn.simon.wechat.service.CityInfoService;

@RestController
public class CityInfoController {

	@Autowired
	private CityInfoService cityInfoServiceImpl;

	@RequestMapping(value = "/cityInfo", method = RequestMethod.GET)
	public Collection<CityInfo> findCityInfo() {
		return this.cityInfoServiceImpl.findAllCityInfo();
	}

	@RequestMapping(value = "/cityInfo/{cityId}", method = RequestMethod.GET)
	public CityInfo findCityInfoByCityId(@PathVariable("cityId") String cityId) {
		return this.cityInfoServiceImpl.findCityInfo(cityId);
	}

	@RequestMapping(value = "/provinceInfo/{provinceId}/cityInfo", method = RequestMethod.GET)
	public Collection<CityInfo> findCityInfoByProvinceId(@PathVariable("provinceId") String provinceId) {
		return this.cityInfoServiceImpl.findCityInfoByProvinceId(provinceId);
	}
}
