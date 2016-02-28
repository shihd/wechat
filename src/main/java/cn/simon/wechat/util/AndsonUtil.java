package cn.simon.wechat.util;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.andson.http.util.HttpUtil;
import com.andson.http.util.JsonUtil;
import com.andson.model.UserInfo;
import com.andson.http.util.HttpUtil.HttpRequestCallBack;
import com.squareup.okhttp.Request;

import cn.simon.wechat.model.Device;
import net.sf.json.JSONObject;

public class AndsonUtil {

	private static final String host = "www.iotyfw.com";
	private static final int port = 80;
	private static final String baseRequestUrl = String.format("%s://%s:%d/andsonap/webServices/app/", "http", host,
			port);
	private static final int timeoutSeconds = 30;

	private HttpRequestCallBack httpRequestCallBack = new HttpRequestCallBack() {

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

	public Collection<Device> getDeviceList(UserInfo userInfo, String tokenId) {

		String requestUrl = String.format("%s%s", baseRequestUrl, "device/allCategoryFindDevices");
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("tokenId", tokenId);
		requestParams.put("userId", userInfo.getUserId());
		HttpUtil.sendCommonHttpRequest(userInfo, requestUrl, requestParams, httpRequestCallBack, timeoutSeconds);

		return (Collection<Device>) httpRequestCallBack.getResultMap().get("devices");
	}
}
