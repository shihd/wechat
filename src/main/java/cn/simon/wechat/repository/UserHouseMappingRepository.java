package cn.simon.wechat.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.simon.wechat.model.UserHouseMapping;
import cn.simon.wechat.model.UserHousePK;

public interface UserHouseMappingRepository extends JpaRepository<UserHouseMapping, UserHousePK> {

	public Collection<UserHouseMapping> findByIdUserId(String userId);

	public Collection<UserHouseMapping> findByIdUserIdAndIsDefault(String userId, String isDefault);
}
