package cn.simon.wechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.simon.wechat.model.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

}
