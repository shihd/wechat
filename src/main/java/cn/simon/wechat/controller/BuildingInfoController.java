package cn.simon.wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.simon.wechat.model.BuildingInfo;
import cn.simon.wechat.service.BuildingInfoService;

@RestController
public class BuildingInfoController {

	@Autowired
	private BuildingInfoService buildingInfoService;

	@RequestMapping(value = "/buildingInfo/{buildingId}", method = RequestMethod.GET)
	public BuildingInfo findBuildingInfoByBuildingId(@PathVariable("buildingId") String buildingId) {
		return this.buildingInfoService.findBuildingInfo(buildingId);
	}
}
