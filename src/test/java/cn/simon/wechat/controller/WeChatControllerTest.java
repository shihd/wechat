package cn.simon.wechat.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import cn.simon.wechat.util.SignUtil;

public class WeChatControllerTest {

	@Test
	public void test() {
		boolean flag = SignUtil.checkSignature("c060b678ffcf51c72968be30e400556d807a283b", "1450010745", "503570232");
		System.out.println(flag);
	}

}
