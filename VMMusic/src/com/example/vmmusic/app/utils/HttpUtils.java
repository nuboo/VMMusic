package com.example.vmmusic.app.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.util.Date;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.vmmusic.app.model.Music;

import android.content.Context;
import android.util.Log;

public class HttpUtils {

	private static int SOCKET_TIME_OUT = 20000;
	private String url = " http://192.168.15.247:90/home/api/";
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
	 * 
	 * @param params
	 * @return
	 */
	public String hashMapString(HashMap<String, String> params) {
		UrlParam = new StringBuilder();
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

	/**
	 * 无文件post
	 * 
	 * @param httpUrl
	 * @param map
	 * @return 返回的String result
	 */


	public String NewpostData(String httpUrl, Map<String, String> map) {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader in = null;
		String result = "";
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(App.url+httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(10000); // 连接超时为10秒
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);// 设置请求数据类型并设置boundary部分；
			connection.connect();
			DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
			if(map!=null){
				Set<Map.Entry<String, String>> paramEntrySet = map.entrySet();
				Iterator paramIterator = paramEntrySet.iterator();
				while (paramIterator.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) paramIterator.next();
					String key = entry.getKey();
					String value = entry.getValue();
					ds.writeBytes(twoHyphens + boundary + end);
					// ds.writeBytes("Content-Disposition: form-data; " + "name=\""
					// + key + "\"" + end + end + value + end+ boundary + end);
					ds.writeBytes("Content-Disposition: form-data; " + "name=\"" + key + "\"" + end + end + value);
					ds.writeBytes(end);
					ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
					ds.flush();
				}
			}
			
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes(
					"Content-Disposition: form-data; " + "name=\"file" + "\";filename=\"" + "image1.png" + "\"" + end);
			ds.writeBytes(end);

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			int statusCode = connection.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				char[] buf = new char[1024];
				int len = -1;
				while ((len = in.read(buf, 0, buf.length)) != -1) {
					stringBuilder.append(buf, 0, len);
				}
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.i("Post", "result===========>" + stringBuilder.toString());
		return stringBuilder.toString();
	}

	/**
	 * 异步的Get请求
	 *
	 * @param urlStr
	 * @param callBack
	 */
	public void doGetAsyn(Context context, final String urlStr, final CallBack callBack) {
		if (Network.checkNetWork(context)) {// 判断网络
			new Thread() {
				public void run() {
					try {
						String result = doGet(urlStr);
						if (callBack != null) {
							callBack.onRequestComplete(result);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}.start();
		} else {
			T.showShort(context, "网络不可用");
		}
	}

	/**
	 * 异步的Post请求
	 *
	 * @param urlStr
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param callBack
	 *            回调
	 * @throws Exception
	 */
	public void doPostAsyn(Context context, final String urlStr, final String params, final CallBack callBack) {
		if (Network.checkNetWork(context)) {// 判断网络
			new Thread() {
				public void run() {
					try {
						String result = doPost(urlStr, params);
						if (callBack != null) {
							callBack.onRequestComplete(result);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();

		} else {
			T.showShort(context, "网络不可用");
		}
	}

	/**
	 * Get请求，获得返回数据
	 *
	 * @param urlStr
	 * @return
	 * @throws Exception
	 */
	public String doGet(String urlStr) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else {
				throw new RuntimeException(" responseCode is not 200 ... ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
			}
			conn.disconnect();
		}

		return null;

	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @throws Exception
	 */
	public String doPost(String url, String param) {
		PrintWriter out = null;
		StringBuilder stringBuilder = new StringBuilder();// 初始化StringBuilder对象
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
			conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
			conn.connect();// 建立连接
			if (param != null && !param.trim().equals("")) {
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(param);
				// flush输出流的缓冲
				out.flush();
			}
			// 获取输入流，得到响应内容
			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));// 使用BuffereadReader读取接收到的数据
				String line = bufferedReader.readLine();
				while (line != null && line.length() > 0) {
					stringBuilder.append(line);
					line = bufferedReader.readLine();
				}
				bufferedReader.close();
				inputStream.close();
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			if (out != null) {
				out.close();
			}
		}
		return stringBuilder.toString();
	}
	
		
	
	
	
	String boundary = "******";
	String end = "\r\n";
	String twoHyphens = "--";
	/**
	 * @param httpurl
	 *            上传的post地址
	 * @param Music music 对象
	 *            文字数据
	 * @param filePath
	 *            图片路径
	 * @return
	 */
	public String upLoadMusic(String httpurl, Music music, String filePath) {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader in = null;
		if (httpurl != null && filePath != null) {
			try {
				URL url = new URL(httpurl);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(10000);// 设置超时
				con.setRequestMethod("POST");// post方法
				con.setUseCaches(false);// 不适用缓存
				con.setDoOutput(true);// 允许输出
				con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);// 构建表单
				con.setRequestProperty("charset", "utf-8");
				con.connect();
				DataOutputStream ds = new DataOutputStream(con.getOutputStream());
				//createTable(ds, music);
				ds.writeBytes(twoHyphens + boundary + end);
				// 编辑文件信息
				Date date = new Date();
				ds.writeBytes("Content-Disposition: form-data; " + "name=\"file" + "\";filename=\"" + date.toString()
						+ ".jpg" + "\"" + end);
				ds.writeBytes(end);
				FileInputStream inputStream = new FileInputStream(new File(filePath));
				byte[] bytes = new byte[1024];
				int length = -1;
				while ((length = inputStream.read(bytes)) != -1) {
					ds.write(bytes, 0, length);
					ds.flush();
				}
				ds.writeBytes(end);
				ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
				ds.flush();
				ds.close();
				inputStream.close();
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				int statusCode = con.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {
					char[] buf = new char[1024];
					int len = -1;
					while ((len = in.read(buf, 0, buf.length)) != -1) {
						stringBuilder.append(buf, 0, len);
					}
				}
				in.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}
	/**
	 * 根据hashmap拼接表单
	 * @param ds
	 * @param map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createTable(DataOutputStream ds, HashMap<String, String> map) {
		if (ds != null && map != null && map.size() > 0) {
			Set<Map.Entry<String, String>> paramEntrySet = map.entrySet();
			Iterator paramIterator = paramEntrySet.iterator();
			while (paramIterator.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) paramIterator.next();

				String key = entry.getKey();
				String value = entry.getValue();

				try {
					ds.writeBytes(twoHyphens + boundary + end);
					ds.writeBytes("Content-Disposition: form-data; " + "name=\"" + key + "\"" + end + end + value);

					ds.writeBytes(end);

					ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

					ds.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}
}
