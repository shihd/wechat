package cn.simon.wechat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.simon.wechat.model.Oauth;
import cn.simon.wechat.service.WechatService;

@Controller
public class WechatController {

	@Autowired
	private WechatService wechatService;

	@RequestMapping(value = "/wechat", method = RequestMethod.GET)
	public @ResponseBody String wechat(String signature, String timestamp, String nonce, String echostr)
			throws Exception {
		return wechatService.validate(signature, timestamp, nonce, echostr);
	}

	@RequestMapping(value = "/wechat", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String wechat(String signature, String timestamp, String nonce, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return wechatService.process(signature, timestamp, nonce, request);
	}
}
