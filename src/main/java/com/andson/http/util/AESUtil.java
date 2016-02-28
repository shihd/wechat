package com.andson.http.util;

import com.andsonap.jni.AndsonapJNI;

/**
 * 3DES加密工具类
 * @author tracywindy 
 */
public class AESUtil {
	
	public static byte[] encrypt(byte[] plainBytes) {
		byte[] encryptData = null ;
		try {
			encryptData = AndsonapJNI.jniDESCEncode(plainBytes);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return encryptData;
	}

	public static byte[] decrypt(byte[] encryptBytes){
		byte[] decryptData = null;
		try {
			decryptData    = AndsonapJNI.jniDESCDecode(encryptBytes);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return decryptData;
	}
}

