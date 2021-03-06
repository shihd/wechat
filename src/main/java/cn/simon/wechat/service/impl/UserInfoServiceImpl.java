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
		this.userInfoRepository.save(userInfo);
		return userInfo;
	}
	
	@Override
	public void delUserInfo(UserInfo userInfo){
		this.userInfoRepository.delete(userInfo);
	}
	
	/**
	 * andson登录接口
	 * 
	 * @param userInfo
	 * @return Map
	 */
	@Override
	public Map<String, Object> andsonLogin(UserInfo userInfo){
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("userName", userInfo.getUserId()); // 用户名
		requestParams.put("password", userInfo.getUserPass()); // 密码
		requestParams.put("mobileId", UUIDUtil.getUUID()); // 手机唯一标识
		requestParams.put("isInputPassword", 1); // 是否手输 0-否；1-是
		requestParams.put("mobileLocale", Locale.getDefault().toString()); // 客户端语言环境
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
		Map<String, Object> result = httpRequestCallBack.getResultMap();
		
		// 调用接口成功需要做的事情
		if(result.get("status").equals(DataStore.ANDSON_SUCCESS_STATUS)){
			String userId = String.valueOf(result.get("userId"));
			String tokenId = String.valueOf(result.get("tokenId"));
			int isChildUser = new Integer((String)result.get("isChildUser"));
			userInfoAndson.setUserId(userId);
			userInfoAndson.setUserName(userInfo.getUserId());
			userInfoAndson.setPassword(userInfo.getUserPass());
			userInfoAndson.setMobileId(UUIDUtil.getUUID());
			userInfoAndson.setTokenId(tokenId);
			userInfoAndson.setMobileLocale(Locale.getDefault().toString());
			userInfoAndson.setIsChildUser(isChildUser);
			DataStore.userInfoMap.put(userId, userInfoAndson);
			DataStore.tokenMap.put(userId, tokenId);
		}
		return result;
	}
	
	public UserInfo userRegister(UserInfo userInfo){
		return null;
	}
	
	/**
	 * andson注册接口
	 * 
	 * @param userInfo
	 * @return Map
	 */
	public Map<String, Object> andsonRegister(UserInfo userInfo){
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("mobileId", UUIDUtil.getUUID()); // 手机唯一标识
		requestParams.put("mobileLocale", Locale.getDefault().toString()); // 客户端语言环境
		requestParams.put("userName", userInfo.getUserId()); // 用户名/手机号/邮箱
		requestParams.put("bindMedium", userInfo.getEmail());  // 绑定号码（手机号或邮箱）
		//requestParams.put("gateWayUDID", value); // 网关唯一标识符
		requestParams.put("password", userInfo.getUserPass()); // 用户密码
		// requestParams.put("verificationCode", value); // 图片上的验证码
		String requestUrl = DataStore.ANDSON_URL+"user/register"; // Andson注册请求接口地址
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