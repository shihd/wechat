package cn.simon.wechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.simon.wechat.model.BuildingInfo;

public interface BuildingInfoRepository extends JpaRepository<BuildingInfo, String> {

}
