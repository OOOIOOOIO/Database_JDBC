package dao;

import java.util.HashMap;

public class Session {
	private static HashMap<String, Object> datas = new HashMap<String, Object>();
	
	//Session.get("")
	public static Object get(String key) {
		return datas.get(key);
	}
	
	public static void put(String key,Object data) {
		datas.put(key, data);
	}
}

