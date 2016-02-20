package cn.simon.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.simon.wechat.model.BuildingInfo;
import cn.simon.wechat.repository.BuildingInfoRepository;
import cn.simon.wechat.service.BuildingInfoService;

@Service
public class BuildingInfoServiceImpl implements BuildingInfoService {

	@Autowired
	private BuildingInfoRepository buildingInfoRepository;

	@Override
	public BuildingInfo findBuildingInfo(String buildingId) {
		// TODO Auto-generated method stub
		return this.buildingInfoRepository.findOne(buildingId);
	}

}
