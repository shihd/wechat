package cn.simon.wechat.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.simon.wechat.model.Oauth;
import cn.simon.wechat.model.UserInfo;
import cn.simon.wechat.service.UserInfoService;
import cn.simon.wechat.util.MD5Util;

@RestController
public class UserInfoController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping(value = "/userInfo/", method = RequestMethod.GET)
	public Collection<UserInfo> findUserInfo(UserInfo userInfo) {
		return this.userInfoService.findUserInfo(userInfo);
	}

	@RequestMapping(value = "/oauth2", method = RequestMethod.GET)
	public Oauth getOauth(String code, HttpServletResponse response) {
		return this.userInfoService.getOauth(code);
	}

	@RequestMapping(value = "/userInfo/{userId}", method = RequestMethod.GET)
	public UserInfo findUserInfoByUserId(@PathVariable("userId") String userId) {
		return this.userInfoService.findUserInfo(userId);
	}

	@RequestMapping(value = "/userInfo/{userId}", method = RequestMethod.PUT)
	public UserInfo modifyUserInfo(@PathVariable("userId") String userId, UserInfo userInfo) {
		return this.userInfoService.modifyUserInfo(userInfo);
	}

	@RequestMapping(value = "/userInfo/", method = RequestMethod.POST)
	public UserInfo addUserInfo(UserInfo userInfo) {
		return this.userInfoService.addUserInfo(userInfo);
	}
	
	/**
	 * 用户登录
	 * 
	 * @param userName
	 * @param userPass
	 * @return
	 */
	@RequestMapping(value = "/userInfo/{userName}/login", method = RequestMethod.GET)
	public String userLogin(@PathVariable("userName") String userName, String userPass){
		UserInfo userInfo = this.userInfoService.findUserInfo(userName);
		if(userInfo != null){
			if(userInfo.getUserPass().equals(MD5Util.getMD5Message(userPass)))
				return "success";
		}
			
		return "failure";
	}
	
	@RequestMapping(value = "/userInfo/register", method = RequestMethod.POST)
	public UserInfo userRegister(UserInfo userInfo) {
		userInfo.setUserPass(MD5Util.getMD5Message(userInfo.getUserPass()));
		userInfo.setWechatOpenId("tttttt");
		return this.userInfoService.addUserInfo(userInfo);
	}
}
