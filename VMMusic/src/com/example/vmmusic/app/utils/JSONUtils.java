package com.example.vmmusic.app.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
	private String code;
	private JSONObject orignJSON;
	
	
	
	/**
	 * 解析验证码
	 * @param result 验证码result
	 * @return String
	 */
	public String jsonCode(String result){
		try {
			orignJSON=new JSONObject(result);
			int status=orignJSON.getInt("status");
			
			switch (status) {
			case 1://成功
				code=orignJSON.getString("radoms");
				break;
			case 2:
				code="网络连接超时,请重新获取";
				break;
			default:
				break;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}
}
