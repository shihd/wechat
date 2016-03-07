package cn.simon.wechat.controller;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.simon.wechat.common.DataStore;
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
			// 用户名密码正确调用Andson接口
			if(userInfo.getUserPass().equals(MD5Util.getMD5Message(userPass))){
				userInfo.setUserPass(userPass);
				Map<String, Object> result = this.userInfoService.andsonLogin(userInfo);
				System.out.println("调用andson接口返回："+result+"==========================");
				// 调用接口成功
				if(result.get("status").equals(DataStore.ANDSON_SUCCESS_STATUS)){
					return "success";
				}else{
					return "interfaceFailure";
				}
			}
		}
		return "failure";
	}
	
	@RequestMapping(value = "/userInfo/register", method = RequestMethod.POST)
	public String userRegister(UserInfo userInfo) {
		try{
			// 判断用户名是否存在
			UserInfo _userInfo = this.userInfoService.findUserInfo(userInfo.getUserId());
			if(_userInfo == null){
				String originPass = userInfo.getUserPass(); // 明文密码
				userInfo.setUserPass(MD5Util.getMD5Message(userInfo.getUserPass()));
				this.userInfoService.addUserInfo(userInfo);
				userInfo.setUserPass(originPass);
				
				Map<String, Object> result = this.userInfoService.andsonRegister(userInfo);
				System.out.println("调用andson接口返回："+result+"==========================");
				// 调用接口成功
				String status = (String)result.get("status");
				if(status.equals(DataStore.ANDSON_SUCCESS_STATUS)){
					return "success";
				}else{
					this.userInfoService.delUserInfo(userInfo);
					return status;
				}
			}else
				return "-3"; // 用户名已存在
		}catch(Exception ex){
			ex.printStackTrace();
			this.userInfoService.delUserInfo(userInfo);
			return "-999"; // 异常信息
		}
	}
}
