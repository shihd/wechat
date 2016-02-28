package cn.simon.wechat.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.simon.wechat.model.HouseInfo;
import cn.simon.wechat.model.UserHouseMapping;
import cn.simon.wechat.service.HouseInfoService;

@RestController
public class HouseInfoController {

	@Autowired
	private HouseInfoService houseInfoService;

	@RequestMapping(value = "/{wechatOpenId}/houseInfo", method = RequestMethod.POST)
	public void saveUserHouseMapping(@PathVariable("wechatOpenId") String wechatOpenId, String houseName, String city,
			String court, String building, String no) {
		this.houseInfoService.saveUserHouseMapping(wechatOpenId, houseName, city, court, building, no);
	}

	@RequestMapping(value = "/{userId}/houseInfo", method = RequestMethod.GET)
	public Collection<HouseInfo> findUserHouseMapping(@PathVariable("userId") String userId) {
		return this.houseInfoService.findHouseInfoByUserId(userId);
	}

	@RequestMapping(value = "/houseInfo/{houseId}", method = RequestMethod.GET)
	public HouseInfo findHouseInfoByHouseId(@PathVariable("houseId") String houseId) {
		return this.houseInfoService.findHouseInfo(houseId);
	}

	@RequestMapping(value = "/{userId}/houseInfo/{houseId}", method = RequestMethod.PUT)
	public void setDefaultHouse(@PathVariable("userId") String userId, @PathVariable("houseId") String houseId) {
		this.houseInfoService.SetDefaultHouse(userId, houseId);
	}

	@RequestMapping(value = "/{userId}/houseInfo/{houseId}", method = RequestMethod.DELETE)
	public void deleteUserHouseMapping(@PathVariable("userId") String userId, @PathVariable("houseId") String houseId) {
		this.houseInfoService.deleteUserHouseMapping(userId, houseId);
	}

	@RequestMapping(value = "/userInfo/{userId}/houseInfo/default", method = RequestMethod.GET)
	public HouseInfo findUserDefaultHouseInfo(@PathVariable("userId") String userId) {
		return this.houseInfoService.findUserDefaultHouseInfo(userId);
	}

	@RequestMapping(value = "/userInfo/{userId}/houseInfo", method = RequestMethod.PUT)
	public HouseInfo modifyUserHouseInfo(@PathVariable("userId") String userId, HouseInfo houseInfo) {
		return this.houseInfoService.modifyUserHouseInfo(userId, houseInfo);
	}
}
