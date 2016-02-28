package cn.simon.wechat.util;

import java.util.UUID;

public class UUIDUtil {

	public static String getUUID(){
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", "");
	}
}
