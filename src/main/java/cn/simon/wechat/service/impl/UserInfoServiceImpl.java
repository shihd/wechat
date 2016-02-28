package cn.simon.wechat.service.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andson.http.util.HttpUtil;
import com.andson.http.util.HttpUtil.HttpRequestCallBack;
import com.andson.http.util.JsonUtil;
import com.andson.http.util.UUIDUtil;
import com.squareup.okhttp.Request;

import cn.simon.wechat.common.DataStore;
import cn.simon.wechat.model.Oauth;
import cn.simon.wechat.model.SNSUserInfo;
import cn.simon.wechat.model.UserInfo;
import cn.simon.wechat.repository.UserInfoRepository;
import cn.simon.wechat.service.AccessTokenService;
import cn.simon.wechat.service.UserInfoService;
import cn.simon.wechat.util.CommonUtil;
import cn.simon.wechat.util.StringUtil;
import net.sf.json.JSONObject;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private AccessTokenService accessTokenService;

	@Override
	public UserInfo addUserInfoByWechat(String wechatOpenId, String accessToken) {
		// TODO Auto-generated method stub
		Collection<UserInfo> userCollection = this.userInfoRepository.findByWechatOpenId(wechatOpenId);
		if (userCollection == null || userCollection.isEmpty() == true) {
			SNSUserInfo snsUserInfo = CommonUtil.getSNSUserInfo(accessToken, wechatOpenId);
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId("sf_" + new Date().getTime());
			userInfo.setWechatOpenId(wechatOpenId);
			userInfo.setNickName(snsUserInfo.getNickname());
			userInfo.setHeadImgUrl(snsUserInfo.getHeadImgUrl());
			userInfo.setSex(String.valueOf(snsUserInfo.getSex()));
			userInfo.setCountry(snsUserInfo.getCountry());
			userInfo.setProvince(snsUserInfo.getProvince());
			userInfo.setCity(snsUserInfo.getCity());
			userInfo.setIsSign("FALSE");
			userInfo.setUnionId(snsUserInfo.getUnionid());
			this.userInfoRepository.save(userInfo);
			return userInfo;
		} else {
			return userCollection.iterator().next();
		}
	}

	@Override
	public Collection<UserInfo> findUserInfo(UserInfo userInfo) {
		if (!StringUtil.isEmpty(userInfo.getWechatOpenId())) {
			return this.userInfoRepository.findByWechatOpenId(userInfo.getWechatOpenId());
		} else {
			return null;
		}
	}

	@Override
	public UserInfo findUser(String wechatOpenId, String accessToken) {
		// TODO Auto-generated method stub
		UserInfo userInfo = null;
		Collection<UserInfo> userCollection = this.userInfoRepository.findByWechatOpenId(wechatOpenId);
		if (userCollection == null || userCollection.isEmpty() == true) {
			SNSUserInfo snsUserInfo = CommonUtil.getSNSUserInfo(accessToken, wechatOpenId);
			userInfo = new UserInfo();
			userInfo.setUserId("sf_" + new Date().getTime());
			userInfo.setWechatOpenId(wechatOpenId);
			userInfo.setNickName(snsUserInfo.getNickname());
			userInfo.setHeadImgUrl(snsUserInfo.getHeadImgUrl());
			userInfo.setSex(String.valueOf(snsUserInfo.getSex()));
			userInfo.setCountry(snsUserInfo.getCountry());
			userInfo.setProvince(snsUserInfo.getProvince());
			userInfo.setCity(snsUserInfo.getCity());
			userInfo.setIsSign("FALSE");
			userInfo.setUnionId(snsUserInfo.getUnionid());
			this.userInfoRepository.save(userInfo);
		} else {
			Iterator<UserInfo> it = userCollection.iterator();
			userInfo = it.next();
		}
		return userInfo;
	}

	@Override
	public Oauth getOauth(String code) {
		// TODO Auto-generated method stub
		return CommonUtil.getOauth(DataStore.Appid, DataStore.AppSecret, code);
	}

	@Override
	public UserInfo findUserInfo(String userId) {
		// TODO Auto-generated method stub
		return this.userInfoRepository.findOne(userId);
	}

	@Override
	public UserInfo modifyUserInfo(UserInfo userInfo) {
		// TODO Auto-generated method stub
		UserInfo user = this.findUserInfo(userInfo.getUserId());
		if (user != null) {
			if (!StringUtil.isEmpty(userInfo.getWechatOpenId())) {
				user.setWechatOpenId(userInfo.getWechatOpenId());
			}
			if (!StringUtil.isEmpty(userInfo.getNickName())) {
				user.setNickName(userInfo.getNickName());
			}
			if (!StringUtil.isEmpty(userInfo.getUserPass())) {
				user.setUserPass(userInfo.getUserPass());
			}
			if (!StringUtil.isEmpty(userInfo.getIsSign())) {
				user.setIsSign(userInfo.getIsSign());
			}
			if (!StringUtil.isEmpty(userInfo.getHeadImgUrl())) {
				user.setHeadImgUrl(userInfo.getHeadImgUrl());
			}
			if (!StringUtil.isEmpty(userInfo.getSex())) {
				user.setSex(userInfo.getSex());
			}
			if (!StringUtil.isEmpty(userInfo.getCountry())) {
				user.setCountry(userInfo.getCountry());
			}
			if (!StringUtil.isEmpty(userInfo.getProvince())) {
				user.setProvince(userInfo.getProvince());
			}
			if (!StringUtil.isEmpty(userInfo.getCity())) {
				user.setCity(userInfo.getCity());
			}
			if (!StringUtil.isEmpty(userInfo.getUserName())) {
				user.setUserName(userInfo.getUserName());
			}
			if (!StringUtil.isEmpty(userInfo.getMobile())) {
				user.setMobile(userInfo.getMobile());
			}
		}

		this.userInfoRepository.save(user);
		return user;
	}

	@Override
	public UserInfo addUserInfo(UserInfo userInfo) {
		// TODO Auto-generated method stub
		if (!StringUtil.isEmpty(userInfo.getWechatOpenId())) {
			return this.addUserInfoByWechat(userInfo.getWechatOpenId(), accessTokenService.getAccessToken());
		}

		return null;
	}
	
	@Override
	public Map<String, Object> andSonLogin(UserInfo userInfo){
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("userName", userInfo.getUserId());
		requestParams.put("password", userInfo.getUserPass());
		requestParams.put("mobileId", UUIDUtil.getUUID());
		requestParams.put("isInputPassword", 1);
		requestParams.put("mobileLocale", Locale.getDefault().toString());
		String requestUrl = DataStore.ANDSON_URL+"user/login"; // Andson登录请求接口地址
		com.andson.model.UserInfo userInfoAndson = new com.andson.model.UserInfo();
		HttpRequestCallBack httpRequestCallBack = new HttpRequestCallBack() {

			private Map<String, Object> resultMap;

			@Override
			protected void onSuccess(String resJsonString) throws Exception {
				System.out.println("返回结果:" + resJsonString);
				JSONObject jsonObj = JSONObject.fromObject(resJsonString);
				resultMap = JsonUtil.JsonToMap(jsonObj);
			}

			@Override
			protected void onFailure(String resJsonString, Request request, IOException ex) throws Exception {
				System.out.println(resJsonString);
				System.out.println(ex);
			}

			public Map<String, Object> getResultMap() {
				return this.resultMap;
			}
		};
		// 发送http请求
		HttpUtil.sendCommonHttpRequest(userInfoAndson, requestUrl, requestParams, httpRequestCallBack);
		return httpRequestCallBack.getResultMap();
	}
}