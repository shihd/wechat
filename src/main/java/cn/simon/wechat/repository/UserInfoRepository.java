package cn.simon.wechat.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.simon.wechat.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
	
	@Query("select t from UserInfo t where t.wechatOpenId = :wechatOpenId")
	public Collection<UserInfo> findByWechatOpenId(@Param("wechatOpenId") String wechatOpenId);
}
