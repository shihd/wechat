package cn.simon.wechat.service;

import java.util.Collection;

import cn.simon.wechat.model.HouseInfo;

public interface HouseInfoService {

	public void saveUserHouseMapping(String wechatOpenId, String houseName, String city, String court, String building,
			String no);

	public Collection<HouseInfo> findHouseInfoByUserId(String userId);

	public HouseInfo findHouseInfo(String houseId);

	public void SetDefaultHouse(String userId, String houseId);

	public void deleteUserHouseMapping(String userId, String houseId);

	public HouseInfo findUserDefaultHouseInfo(String userId);

	public HouseInfo modifyUserHouseInfo(String userId, HouseInfo houseInfo);
}
