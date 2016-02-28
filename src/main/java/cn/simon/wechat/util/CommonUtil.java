package cn.simon.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import antlr.collections.List;
import cn.simon.wechat.model.Oauth;
import cn.simon.wechat.model.SNSUserInfo;
import cn.simon.wechat.model.Token;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class CommonUtil {

	// 凭证获取（GET）
	public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// 用户授权页面获取openid
	public final static String oauth_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（ GET、 POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject( 通过 JSONObject.get(key)的方式获取JSON对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/ POST）
			conn.setRequestMethod(requestMethod);
			// 当outputStr不为 null时，向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放 资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
			System.out.println(buffer.toString());
		} catch (ConnectException ce) {
			System.out.println("连接超时：" + ce);
		} catch (Exception e) {
			System.out.println("https请求异常：" + e);
		}
		return jsonObject;
	}

	/**
	 * 获取接口访问凭证
	 * 
	 * @param appid凭证
	 * @param appsecret密钥
	 * @return
	 * 
	 */
	public static Token getToken(String appid, String appsecret) {
		Token token = null;
		String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		// 发起GET请求获取凭证
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				token = null;
				// 获取token失败
				System.out.println(
						"获取token失败errcode:" + jsonObject.getInt("errcode") + " errmsg:" + jsonObject.getInt("errcode"));
			}
		}
		return token;
	}

	public static Oauth getOauth(String appid, String appsecret, String code) {
		Oauth oauth = new Oauth();
		String requestUrl = oauth_url.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
		System.out.println(requestUrl);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				oauth = new Oauth();
				oauth.setAccess_token(jsonObject.getString("access_token"));
				oauth.setExpires_in(jsonObject.getString("expires_in"));
				oauth.setOpenid(jsonObject.getString("openid"));
				oauth.setRefresh_token(jsonObject.getString("refresh_token"));
				oauth.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				e.printStackTrace();
				oauth = null;
				int errorCode = jsonObject.getInt("errcode");
				System.out.println("errcode:" + errorCode);
				String errorMsg = jsonObject.getString("errmsg");
				System.out.println("errmsg:" + errorMsg);
			}
		}
		return oauth;
	}

	/**
	 * 获取网页授权用户信息
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
		SNSUserInfo snsUserInfo = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				snsUserInfo = new SNSUserInfo();
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				snsUserInfo.setSex(jsonObject.getInt("sex"));
				snsUserInfo.setCountry(jsonObject.getString("country"));
				snsUserInfo.setProvince(jsonObject.getString("province"));
				snsUserInfo.setCity(jsonObject.getString("city"));
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
			} catch (Exception e) {
				e.printStackTrace();
				snsUserInfo = null;
				int errorCode = jsonObject.getInt("errcode");
				System.out.println("errcode:" + errorCode);
				String errorMsg = jsonObject.getString("errmsg");
				System.out.println("errmsg:" + errorMsg);
			}
		}
		return snsUserInfo;
	}
}