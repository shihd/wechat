package cn.simon.wechat.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.simon.wechat.common.DataStore;
import cn.simon.wechat.model.CityInfo;
import cn.simon.wechat.model.HouseInfo;
import cn.simon.wechat.model.UserHouseMapping;
import cn.simon.wechat.model.UserHousePK;
import cn.simon.wechat.model.UserInfo;
import cn.simon.wechat.repository.CityInfoRepository;
import cn.simon.wechat.repository.HouseInfoRepository;
import cn.simon.wechat.repository.UserHouseMappingRepository;
import cn.simon.wechat.repository.UserInfoRepository;
import cn.simon.wechat.service.HouseInfoService;

@Service
public class HouseInfoServiceImpl implements HouseInfoService {

	@Autowired
	private HouseInfoRepository houseInfoRepository;

	@Autowired
	private CityInfoRepository cityInfoRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private UserHouseMappingRepository userHouseMappingRepository;

	@Override
	public void saveUserHouseMapping(String wechatOpenId, String houseName, String city, String court, String building,
			String no) {
		// TODO Auto-generated method stub
		HouseInfo houseInfo = new HouseInfo();
		houseInfo.setCity(city);
		houseInfo.setCourt(court);
		houseInfo.setBuilding(building);
		houseInfo.setNo(no);

		Collection<HouseInfo> houseInfoCollection = this.houseInfoRepository.findByCityAndCourtAndBuildingAndNo(
				houseInfo.getCity(), houseInfo.getCourt(), houseInfo.getBuilding(), houseInfo.getNo());
		if (houseInfoCollection == null || houseInfoCollection.isEmpty()) {
			houseInfo.setHouseId("h_" + new Date().getTime());
			houseInfo.setCountry(DataStore.Country_China);
			CityInfo cityInfo = this.cityInfoRepository.findOne(houseInfo.getCity());
			houseInfo.setProvince(cityInfo.getProvinceId());
			this.houseInfoRepository.save(houseInfo);
		} else {
			Iterator<HouseInfo> houseIt = houseInfoCollection.iterator();
			houseInfo = houseIt.next();
		}

		Collection<UserInfo> userInfoCollection = this.userInfoRepository.findByWechatOpenId(wechatOpenId);
		Iterator<UserInfo> userIt = userInfoCollection.iterator();
		UserInfo userInfo = userIt.next();

		UserHousePK id = new UserHousePK();
		id.setUserId(userInfo.getUserId());
		id.setHouseId(houseInfo.getHouseId());

		UserHouseMapping mapping = this.userHouseMappingRepository.findOne(id);
		if (mapping == null) {
			mapping = new UserHouseMapping();
			mapping.setId(id);
			mapping.setHouseName(houseName);
			mapping.setIsDefault("FALSE");
			mapping.setStatus("0");
			this.userHouseMappingRepository.save(mapping);
		}
	}

	@Override
	public Collection<HouseInfo> findHouseInfoByUserId(String userId) {
		// TODO Auto-generated method stub
		Collection<HouseInfo> houseInfoCollection = null;
		Collection<UserHouseMapping> userHouseMappingCollection = this.userHouseMappingRepository
				.findByIdUserId(userId);
		if (null != userHouseMappingCollection && !userHouseMappingCollection.isEmpty()) {
			houseInfoCollection = new ArrayList<HouseInfo>();
			for (UserHouseMapping mapping : userHouseMappingCollection) {
				houseInfoCollection.add(this.houseInfoRepository.findOne(mapping.getId().getHouseId()));
			}
		}

		return houseInfoCollection;
	}

	@Override
	public HouseInfo findHouseInfo(String houseId) {
		// TODO Auto-generated method stub
		return this.houseInfoRepository.findOne(houseId);
	}

	@Override
	public void SetDefaultHouse(String userId, String houseId) {
		// TODO Auto-generated method stub
		Collection<UserHouseMapping> userHouseMappingCollection = this.userHouseMappingRepository
				.findByIdUserId(userId);
		for (UserHouseMapping userHouseMapping : userHouseMappingCollection) {
			if (userHouseMapping.getId().getHouseId().equals(houseId)) {
				userHouseMapping.setIsDefault("TRUE");
			} else {
				userHouseMapping.setIsDefault("FALSE");
			}
		}
		this.userHouseMappingRepository.save(userHouseMappingCollection);
	}

	@Override
	public void deleteUserHouseMapping(String userId, String houseId) {
		// TODO Auto-generated method stub
		UserHousePK id = new UserHousePK();
		id.setUserId(userId);
		id.setHouseId(houseId);
		this.userHouseMappingRepository.delete(id);
	}

	@Override
	public HouseInfo findUserDefaultHouseInfo(String userId) {
		// TODO Auto-generated method stub
		HouseInfo houseInfo = null;
		Collection<UserHouseMapping> userHouseMappingCollection = this.userHouseMappingRepository
				.findByIdUserIdAndIsDefault(userId, "TRUE");
		if (null != userHouseMappingCollection && !userHouseMappingCollection.isEmpty()) {
			String houseId = userHouseMappingCollection.iterator().next().getId().getHouseId();
			houseInfo = this.findHouseInfo(houseId);
		}

		return houseInfo;
	}

	@Override
	public HouseInfo modifyUserHouseInfo(String userId, HouseInfo houseInfo) {
		// TODO Auto-generated method stub
		houseInfo.setCountryId("CHN");
		houseInfo.setCountry("中国");

		Collection<UserHouseMapping> userHouseMappings = this.userHouseMappingRepository.findByIdUserId(userId);

		if (null == userHouseMappings || userHouseMappings.isEmpty() == true) {
			houseInfo.setHouseId("h_" + new Date().getTime());
			this.houseInfoRepository.save(houseInfo);
			UserHouseMapping userHouseMapping = new UserHouseMapping();
			UserHousePK id = new UserHousePK();
			id.setUserId(userId);
			id.setHouseId(houseInfo.getHouseId());
			userHouseMapping.setId(id);
			userHouseMapping.setIsDefault("TRUE");
			this.userHouseMappingRepository.save(userHouseMapping);
		} else {
			UserHouseMapping userHouseMapping = userHouseMappings.iterator().next();
			houseInfo.setHouseId(userHouseMapping.getId().getHouseId());
			this.houseInfoRepository.save(houseInfo);
		}

		return houseInfo;
	}
}
