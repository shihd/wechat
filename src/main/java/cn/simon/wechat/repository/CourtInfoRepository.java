package cn.simon.wechat.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.simon.wechat.model.CourtInfo;

public interface CourtInfoRepository extends JpaRepository<CourtInfo, String> {

	public Collection<CourtInfo> findByCityIdOrderByCourtNameAsc(String cityId);
}
