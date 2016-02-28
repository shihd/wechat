package cn.simon.wechat.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.simon.wechat.model.CourtInfo;
import cn.simon.wechat.repository.CourtInfoRepository;
import cn.simon.wechat.service.CourtInfoService;

@Service
public class CourtInfoServiceImpl implements CourtInfoService {

	@Autowired
	private CourtInfoRepository courtInfoRepository;

	@Override
	public Collection<CourtInfo> findCourtInfoByCity(String cityId) {
		// TODO Auto-generated method stub
		return this.courtInfoRepository.findByCityIdOrderByCourtNameAsc(cityId);
	}

	@Override
	public CourtInfo findCourtInfo(String courtId) {
		// TODO Auto-generated method stub
		return this.courtInfoRepository.findOne(courtId);
	}

}
