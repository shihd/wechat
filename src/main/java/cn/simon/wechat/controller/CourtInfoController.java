package cn.simon.wechat.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.simon.wechat.model.CourtInfo;
import cn.simon.wechat.service.CourtInfoService;

@RestController
public class CourtInfoController {

	@Autowired
	private CourtInfoService courtInfoService;

	@RequestMapping(value = "/courtInfo", method = RequestMethod.GET)
	public Collection<CourtInfo> findCourtInfo(String cityId) {
		return this.courtInfoService.findCourtInfoByCity(cityId);
	}

	@RequestMapping(value = "/courtInfo/{courtId}", method = RequestMethod.GET)
	public CourtInfo findCourtInfoByCourtId(@PathVariable("courtId") String courtId) {
		return this.courtInfoService.findCourtInfo(courtId);
	}
}
