package com.andson.http.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.andson.model.UserInfo;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class HttpUtil {

	public static final String APPLICATION_XML = "application/xml";
	public static final String APPLICATION_ATOM_XML = "application/atom+xml";
	public static final String APPLICATION_XHTML_XML = "application/xhtml+xml";
	public static final String APPLICATION_SVG_XML = "application/svg+xml";
	public static final String APPLICATION_JSON = "application/json";
	public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
	public static final String MULTIPART_FORM_DATA = "multipart/form-data";
	public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
	public static final String TEXT_PLAIN = "text/plain";
	public static final String TEXT_XML = "text/xml";
	public static final String TEXT_HTML = "text/html";

	public static final int DEFAULT_TIMEOUT_SECONDS = 30;
	public static final String CHARSET = "UTF-8";
	public static MediaType MEDIA_TYPE = MediaType.parse(String.format("%s; charset=%s", APPLICATION_JSON, CHARSET));

	public static abstract class HttpRequestCallBack {

		private Map<String, Object> resultMap;

		public Map<String, Object> getResultMap() {
			return this.resultMap;
		}

		protected void onFailure(String resJsonString, Request request, IOException ex) throws Exception {
		}

		protected abstract void onSuccess(String resJsonString) throws Exception;
	}

	// 发送http请求
	public static void sendCommonHttpRequest(UserInfo userInfo, String requestUrl, Map<String, Object> requestParams,
			HttpRequestCallBack httpRequestCallBack, int timeoutSeconds) {
		{
			String paramsBodyStr = buildPostBodyParams(userInfo, requestParams);
			byte[] paramsBodyByte = AESUtil.encrypt(paramsBodyStr.getBytes());
			post(requestUrl, paramsBodyByte, httpRequestCallBack, timeoutSeconds);
		}
	}

	public static Map<String, Object> getBaseRequestParams(UserInfo userInfo) {
		Map<String, Object> requestParams = new HashMap<String, Object>();
		requestParams.put("mobileId", userInfo.getMobileId()); // 设备唯一标识
		requestParams.put("userId", userInfo.getUserId()); // userId
		requestParams.put("tokenId", userInfo.getTokenId());// tokenId
		String mobileLocale = Locale.getDefault().toString();// 当前语言环境
		String timeZone = TimeZone.getDefault().getID(); // 当前时区ID
		requestParams.put("mobileLocale", mobileLocale);
		requestParams.put("timeZone", timeZone);
		requestParams.put("timeStamp", System.currentTimeMillis());
		return requestParams;
	}

	private static void post(String requestUrl, byte[] paramsByte, HttpRequestCallBack httpRequestCallBack,
			int timeoutSeconds) {
		OkHttpClient okHttpclient = new OkHttpClient();
		okHttpclient.setConnectTimeout(timeoutSeconds, TimeUnit.SECONDS);
		RequestBody requestBody = RequestBody.create(MEDIA_TYPE, paramsByte);
		Request request = new Request.Builder().url(requestUrl).post(requestBody).build();
		// okHttpclient.newCall(request).enqueue(responseCallback);
		IOException error = null;
		boolean failure = true;
		try {
			Response response = okHttpclient.newCall(request).execute();
			try {
				if (response.isSuccessful()) {
					failure = false;
					byte[] resBytes = AESUtil.decrypt(response.body().bytes());
					final String resJsonStr = new String(resBytes);
					if (null != httpRequestCallBack) {
						try {
							httpRequestCallBack.onSuccess(resJsonStr);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					error = new IOException("Failure");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException ex) {
			ex.printStackTrace();

		}
		if (failure) {
			JSONObject failureJson = new JSONObject();
			// failureJson.put("url" , request.urlString());
			// failureJson.put("error" , ex.toString());
			final String resJsonStr = failureJson.toString();
			if (null != httpRequestCallBack) {
				try {
					httpRequestCallBack.onFailure(resJsonStr, request, error);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected static void get(String requestUrl, Map<String, Object> mapParam, Callback responseCallback,
			int timeoutSeconds) {
		OkHttpClient okHttpclient = new OkHttpClient();
		okHttpclient.setConnectTimeout(timeoutSeconds, TimeUnit.SECONDS);
		Request request = new Request.Builder().url(buildGetQueryParams(requestUrl, mapParam)).get().build();
		okHttpclient.newCall(request).enqueue(responseCallback);
	}

	private static String buildPostBodyParams(UserInfo userInfo, Map<String, Object> requestParams) {
		// // 构建请求参数
		Map<String, Object> params = getBaseRequestParams(userInfo);
		params.putAll(requestParams);
		params.put("isMultiple", 1); // 网关
		JSONObject jsonParam = new JSONObject();
		try {
			if (null != params) {
				for (Entry<String, Object> e : params.entrySet()) {
					String paramName = e.getKey();
					Object paramValue = e.getValue();
					if (StringUtil.isNotBlank(paramValue)) {
						jsonParam.put(paramName, paramValue);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonParam.toString();
	}

	private static String buildGetQueryParams(String requestUrl, Map<String, Object> params) {
		StringBuffer appendStr = new StringBuffer();
		if (-1 == requestUrl.indexOf("?")) {
			appendStr.append("?");
		}
		if (null != params) {
			for (Entry<String, Object> e : params.entrySet()) {
				String paramName = e.getKey();
				if (!StringUtil.isBlank(paramName)) {
					Object paramValue = e.getValue();
					appendStr.append(paramName).append("=").append(StringUtil.emptyOpt(paramValue)).append("&");
				}
			}
			appendStr.substring(0, appendStr.length() - 1);
		}
		return appendStr.insert(0, requestUrl).toString();
	}
}
