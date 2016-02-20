package cn.simon.wechat.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.simon.wechat.model.ProvinceInfo;
import cn.simon.wechat.common.DataStore;
import cn.simon.wechat.service.ProvinceInfoService;

@RestController
public class ProvinceController {

	@Autowired
	private ProvinceInfoService provinceInfoService;

	@RequestMapping(value = "/provinceInfo", method = RequestMethod.GET)
	public Collection<ProvinceInfo> findProvinceInfo() {
		return this.provinceInfoService.findProvinceInfoByCountry(DataStore.Country_China);
	}

	@RequestMapping(value = "/provinceInfo/{provinceId}", method = RequestMethod.GET)
	public ProvinceInfo findProvinceInfoByProvinceId(@PathVariable("provinceId") String provinceId) {
		return this.provinceInfoService.findProvinceInfo(provinceId);
	}
}
