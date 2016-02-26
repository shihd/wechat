package cn.simon.wechat.util;

import java.security.MessageDigest;

/**
 * MD5加密。
 * @Date 2016-02-25 上午11:39:14
 * @version
 */
public class MD5Util {

	/**
	 * 将字符串按MD5加密后返回。
	 * @param ms 要加密的字符串
	 * @return
	 */
	public static String getMD5Message(String ms) {
		try {
			java.security.MessageDigest alg = MessageDigest.getInstance("MD5");
			alg.update(ms.getBytes());
			byte[] digesta = alg.digest();
			return bytesToHexString(digesta);
		} catch (java.security.NoSuchAlgorithmException ex) {
			return null;
		}
	}

	public static String bytesToHexString(byte[] in) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < in.length; i++) {
			sb.append(byteToHexString(in[i]));
		}
		return sb.toString();
	}

	public static String byteToHexString(byte b) {
		StringBuffer result = new StringBuffer(2);
		String temp = Integer.toHexString(b >= 0 ? b : (b + 256));
		if (temp.length() == 1) {
			result.append("0");
		}
		result.append(temp);
		return result.toString();
	}
}