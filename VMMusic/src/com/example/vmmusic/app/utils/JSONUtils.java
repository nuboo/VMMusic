package com.example.vmmusic.app.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.vmmusic.app.model.User;

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
	
	private static final String[] USERINFO={
		"logo","username","fans","attention","voice",
		
	};
	/**
	 * 解析用户数据
	 * @param result
	 * @return
	 */
    public User jsonPerson(String result){
    	User user =new User();
    	try {
			JSONObject jsonObject=new JSONObject(result);
			user.setLogo(jsonObject.optString(USERINFO[0], "VM"));
			user.setName(jsonObject.optString(USERINFO[1], "Vmuser"));
			user.setFans(jsonObject.optInt(USERINFO[2], 0));
			user.setAttention(jsonObject.optInt(USERINFO[3], 0));
			user.setVoice(jsonObject.optInt(USERINFO[4], 0));
	    	
    	
    	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return user;
    }
	
}
