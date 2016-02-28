package cn.simon.wechat.service;

import javax.servlet.http.HttpServletRequest;

public interface WechatService {

	public String validate(String signature, String timestamp, String nonce, String echostr) throws Exception;

	public String process(String signature, String timestamp, String nonce, HttpServletRequest request);

	public String processRequest(HttpServletRequest request);
}
