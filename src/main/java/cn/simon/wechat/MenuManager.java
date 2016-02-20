package cn.simon.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.simon.wechat.model.Button;
import cn.simon.wechat.model.ComplexButton;
import cn.simon.wechat.model.Menu;
import cn.simon.wechat.model.Token;
import cn.simon.wechat.model.ViewButton;
import cn.simon.wechat.util.CommonUtil;
import cn.simon.wechat.util.MenuUtil;

/**
 * 菜单管理类
 * 
 * @author Simon
 *
 */
public class MenuManager {

	/**
	 * 定义菜单结构
	 * 
	 * @return
	 */
	private static Menu getMenu() {

		ComplexButton btn1 = new ComplexButton();
		btn1.setName("我的外遮阳");
		ViewButton btn11 = new ViewButton();
		btn11.setName("预约维修");
		btn11.setUrl(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6d3f0b437baeb045&redirect_uri=http://shengfuluo.com/wechat/wechat/service.html&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
		ViewButton btn12 = new ViewButton();
		btn12.setName("改造升级");
		btn12.setUrl(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6d3f0b437baeb045&redirect_uri=http://shengfuluo.com/wechat/wechat/upgrading.html&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
		btn1.setSub_button(new Button[] { btn11, btn12 });

		ViewButton btn2 = new ViewButton();
		btn2.setName("我的家");
		btn2.setUrl(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6d3f0b437baeb045&redirect_uri=http://shengfuluo.com/wechat/wechat/myHome.html&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");

		// ViewButton btn3 = new ViewButton();
		// btn3.setName("我的小区");
		// btn3.setUrl("http://sunflower.miniedu.com.cn/wechat/wechat/myProperty.html");

		Menu menu = new Menu();
		menu.setButton(new Button[] { btn1, btn2 });

		return menu;
	}

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wx6d3f0b437baeb045";
		// 第三方用户唯一凭证密钥
		String appSecret = "408cf70e1469c156a1b46ee965d085de";

		// 调用接口获取凭证
		Token token = CommonUtil.getToken(appId, appSecret);

		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());
			// 判断菜单创建结果
			if (result)
				System.out.println("菜单创建成功！");
			else
				System.out.println("菜单创建失败！");
		}
	}
}
