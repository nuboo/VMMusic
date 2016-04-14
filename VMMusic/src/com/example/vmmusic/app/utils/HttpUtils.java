package com.example.vmmusic.app.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpUtils {
	private static int SOCKET_TIME_OUT = 20000;
	
	private HttpClient httpClient;
	private HttpResponse httpResponse;
	private HttpGet httpGet;
	private StringBuilder Url, UrlParam;// URL地址 拼接参数 返回结果
	private String result;
	private BasicHttpParams basicHttpParams;

	private final int TIMEOUT_IN_MILLIONS = 5000;// 连接时间

	private static HttpUtils httpUtils = new HttpUtils();

	/**
	 * 单例饿汉式
	 *
	 * @return
	 */
	public static HttpUtils getInstance() {
		if (httpUtils == null) {
			httpUtils = new HttpUtils();
		}
		return httpUtils;
	}

	/**
	 * 回调
	 */
	public interface CallBack {
		void onRequestComplete(String result);
	}

	/**
	 * 
	 * @param http
	 *            http地址
	 * @param param
	 *            请求参数
	 * @param value
	 *            参数值
	 * @return
	 */
	public String httpGet(String http, String param, String value) {
		UrlParam = new StringBuilder();
		Url = new StringBuilder();
		result = null;
		basicHttpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(basicHttpParams, TIMEOUT_IN_MILLIONS);// 设置连接超时
		HttpConnectionParams.setSoTimeout(basicHttpParams, SOCKET_TIME_OUT);// 设置socket时间
		// 创建一个httpClient实例
		httpClient = new DefaultHttpClient(basicHttpParams);
		// 拼接Url
		Url.append(http + "?" + param + "=" + encodeUTF8(value));

		try {
			httpGet = new HttpGet(Url.toString());
			httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {

				result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");// 将返回数据以utf-8编码成字符串

			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	
	/**
	 * 拼接hashmap字符串
	 * @param params
	 * @return
	 */
	public String hashMapString(HashMap<String,String> params){
		UrlParam=new StringBuilder();
		if (params != null && !params.isEmpty()) {// 如果有参数
			for (String key : params.keySet()) {
				// 拼接参数语句
				UrlParam.append("&" + encodeUTF8(key) + "=" + encodeUTF8(params.get(key)));
			}
			UrlParam.replace(0, 1, "?");

			
		}
		return UrlParam.toString();
	}
	/**
	 * 转译字符串
	 * 
	 * @param str
	 * @return String
	 */
	public String encodeUTF8(String str) {
		try {
			str = URLEncoder.encode(str, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return str;
	}
}
