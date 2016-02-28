package cn.simon.wechat.common;

import java.util.Map;

import com.andson.model.UserInfo;

import cn.simon.wechat.model.AccessToken;

public class DataStore {

	public static AccessToken accessToken = null;

	public final static String Appid = "wx79aae489fd4597ca";

	public final static String AppSecret = "a199958b060d6ff04a2f9dc54cad9b10";

	public final static String Country_China = "CHN";

	public static Map<String, UserInfo> userInfoMap;
}
