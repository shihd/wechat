package cn.simon.wechat.common;

import java.util.HashMap;
import java.util.Map;

import com.andson.model.UserInfo;

import cn.simon.wechat.model.AccessToken;

public class DataStore {

	public static AccessToken accessToken = null;

	public final static String Appid = "wx79aae489fd4597ca";

	public final static String AppSecret = "a199958b060d6ff04a2f9dc54cad9b10";

	public final static String Country_China = "CHN";
	
	public final static String ANDSON_URL = "http://www.iotyfw.com/andsonap/webServices/app/";
	
	public final static String ANDSON_SUCCESS_STATUS = "0";

	// String 属性为用户ID，UserInfo属性为成功登录后ANDSON包中的UserInfo对象
	public static Map<String, UserInfo> userInfoMap = new HashMap<String, UserInfo>();

	// String 属性为用户ID，token属性为成功登录ANDSON的TOKEN
	public static Map<String, String> tokenMap = new HashMap<String, String>();
}