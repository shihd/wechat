package cn.simon.wechat.service.impl;

import java.util.Date;
import java.util.Map;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.simon.wechat.model.Oauth;
import cn.simon.wechat.model.TextMessage;
import cn.simon.wechat.service.AccessTokenService;
import cn.simon.wechat.service.UserInfoService;
import cn.simon.wechat.service.WechatService;
import cn.simon.wechat.util.CommonUtil;
import cn.simon.wechat.util.MessageUtil;
import cn.simon.wechat.util.SignUtil;

@Service
public class WechatServiceImpl implements WechatService {

	@Autowired
	private AccessTokenService accessTokenService;

	@Autowired
	private UserInfoService userInfoService;

	@Override
	public String validate(String signature, String timestamp, String nonce, String echostr) throws Exception {
		// TODO Auto-generated method stub
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		}
		return null;
	}

	public String process(String signature, String timestamp, String nonce, HttpServletRequest request) {
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return processRequest(request);
		} else {
			return null;
		}
	}

	public String processRequest(HttpServletRequest request) {

		String respXml = null;

		String respContent = "未知的消息类型";

		try {
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = "您发送的是文本消息！";
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是语音消息！";
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "您发送的是视频消息！";
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = requestMap.get("Event");
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					String accessToken = this.accessTokenService.getAccessToken();
					this.userInfoService.addUserInfoByWechat(fromUserName, accessToken);
					respContent = "谢谢您的关注！";
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {

				} else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {

				} else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {

				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {

				}
			}
			textMessage.setContent(respContent);
			respXml = MessageUtil.messageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respXml;
	}
}
