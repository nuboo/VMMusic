package com.example.vmmusic.app.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

/**
 * Created by admin on 2016/3/18.
 */
@SuppressWarnings("unused")
public class JSONUtils {

	private Activity activity;

	public JSONUtils() {
		// TODO Auto-generated constructor stub
	}

	public JSONUtils(Activity activity) {
		this.activity = activity;
	}

	/**
	 * 解析json
	 *
	 * @param str
	 *            要解析的json
	 * @param parems
	 * @return String型的结果
	 */
	public static String JsonString(String str, String parems) {
		String verify = null;
		try {
			JSONObject jsonObject = new JSONObject(str);
			verify = jsonObject.getString(parems);
			return verify;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return verify;
	}

	/**
	 * 解析json
	 *
	 * @param str
	 * @param param
	 * @return int型结果
	 */
	public static int JsonInt(String str, String param) {
		int verify = 0;
		try {
			JSONObject jsonObject = new JSONObject(str);
			verify = jsonObject.getInt(param);
			return verify;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return verify;
	}

}
