package cn.simon.wechat.service;

import java.util.Collection;

import org.springframework.stereotype.Service;
import cn.simon.wechat.model.CourtInfo;

@Service
public interface CourtInfoService {

	public Collection<CourtInfo> findCourtInfoByCity(String cityId);

	public CourtInfo findCourtInfo(String courtId);
}
