package cn.simon.wechat.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import cn.simon.wechat.model.Article;
import cn.simon.wechat.model.ImageMessage;
import cn.simon.wechat.model.MusicMessage;
import cn.simon.wechat.model.NewsMessage;
import cn.simon.wechat.model.TextMessage;
import cn.simon.wechat.model.VideoMessage;
import cn.simon.wechat.model.VoiceMessage;

public class MessageUtil {

	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	public static final String EVENT_TYPE_SCAN = "scan";

	public static final String EVENT_TYPE_LOCATION = "LOCATION";

	public static final String EVENT_TYPE_CLICK = "CLICK";

	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";

	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";

	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";

	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 解析微信发来的请求（XML）
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}
		inputStream.close();
		inputStream = null;
		return map;
	}

	/**
	 * 扩展xstream使其支持CDATA
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * 文本消息对象转换成XML
	 */
	public static String messageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 图片消息对象转换成XML
	 */
	public static String messageToXml(ImageMessage imageMessage) {
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/**
	 * 语音消息对象转换成XML
	 */
	public static String messageToXml(VoiceMessage voiceMessage) {
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}

	/**
	 * 视频消息对象转换成XML
	 */
	public static String messageToXml(VideoMessage videoMessage) {
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}

	/**
	 * 音乐消息对象转换成XML
	 */
	public static String messageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成XML
	 */
	public static String messageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
}
