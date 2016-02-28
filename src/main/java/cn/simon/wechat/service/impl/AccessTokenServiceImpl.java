package cn.simon.wechat.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.simon.wechat.common.DataStore;
import cn.simon.wechat.model.AccessToken;
import cn.simon.wechat.model.Token;
import cn.simon.wechat.repository.AccessTokenRepository;
import cn.simon.wechat.service.AccessTokenService;
import cn.simon.wechat.util.CommonUtil;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

	// 第三方用户唯一凭证
	private String appId = DataStore.Appid;
	// 第三方用户唯一凭证密钥
	private String appSecret = DataStore.AppSecret;

	@Autowired
	private AccessTokenRepository accessTokenRepository;

	@Override
	public String getAccessToken() {
		System.out.println(new Date());
		boolean flag = false;
		// TODO Auto-generated method stub
		if (DataStore.accessToken == null) {
			Collection<AccessToken> collection = this.accessTokenRepository.findAll();
			if (collection != null && collection.isEmpty() == false) {
				Iterator<AccessToken> it = collection.iterator();
				DataStore.accessToken = it.next();
				if ((new Date().getTime() - DataStore.accessToken.getUpdateTime().getTime()) > (1000 * 60 * 60)) {
					flag = true;
				}
			} else {
				flag = true;
			}
		} else {
			if ((new Date().getTime() - DataStore.accessToken.getUpdateTime().getTime()) > (1000 * 60 * 60)) {
				flag = true;
			}
		}

		if (flag) {
			System.out.println("getAccessToken");
			Token token = CommonUtil.getToken(this.appId, this.appSecret);
			this.accessTokenRepository.deleteAll();
			AccessToken accessToken = new AccessToken();
			accessToken.setAccessToken(token.getAccessToken());
			accessToken.setUpdateTime(new Date());
			this.accessTokenRepository.save(accessToken);
			DataStore.accessToken = accessToken;
		}

		return DataStore.accessToken.getAccessToken();
	}

}
