package cn.simon.wechat.util;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

	public static void formatHeader(HttpServletResponse response) {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH");
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.addHeader("Access-Control-Allow-Headers", "*");
	}
}
