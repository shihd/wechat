package com.andson.http.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	/**
	 * 将JSONObjec对象转换成Map-List集合
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> JsonToMap(JSONObject json) {
		Map<String, Object> columnValMap = new HashMap<String, Object>();
		Set<Object> jsonKeys = json.keySet();
		for (Object key : jsonKeys) {
			Object JsonValObj = json.get(key);
			if (JsonValObj instanceof JSONArray) {
				columnValMap.put((String) key, JsonToList((JSONArray) JsonValObj));
			} else if (key instanceof JSONObject) {
				columnValMap.put((String) key, JsonToMap((JSONObject) JsonValObj));
			} else {
				columnValMap.put((String) key, JsonValObj);
			}
		}
		return columnValMap;
	}

	/**
	 * 将JSONArray对象转换成Map-List集合
	 * 
	 * @param jsonArr
	 * @return
	 */
	public static Object JsonToList(JSONArray jsonArr) {
		List<Object> jsonObjList = new ArrayList<Object>();
		for (Object obj : jsonArr) {
			if (obj instanceof JSONArray) {
				jsonObjList.add(JsonToList((JSONArray) obj));
			} else if (obj instanceof JSONObject) {
				jsonObjList.add(JsonToMap((JSONObject) obj));
			} else {
				jsonObjList.add(obj);
			}
		}
		return jsonObjList;
	}

}
