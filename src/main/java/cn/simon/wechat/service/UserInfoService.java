package cn.simon.wechat.service;

import java.util.Collection;

import cn.simon.wechat.model.Oauth;
import cn.simon.wechat.model.UserInfo;

public interface UserInfoService {

	public UserInfo addUserInfoByWechat(String wechatOpenId, String accessToken);

	public UserInfo findUser(String wechatOpenId, String accessToken);

	public Oauth getOauth(String code);

	public UserInfo findUserInfo(String userId);

	public UserInfo modifyUserInfo(UserInfo userInfo);

	public Collection<UserInfo> findUserInfo(UserInfo userInfo);
	
	public UserInfo addUserInfo(UserInfo userInfo);

}
