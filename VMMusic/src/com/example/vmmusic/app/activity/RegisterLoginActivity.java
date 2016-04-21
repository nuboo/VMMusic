package com.example.vmmusic.app.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.vmmusic.R;
import com.example.vmmusic.app.listener.AuthListener;
import com.example.vmmusic.app.listener.BaseUiListener;
import com.example.vmmusic.app.utils.AccessTokenKeeper;
import com.example.vmmusic.app.utils.App;
import com.example.vmmusic.app.utils.HttpUtils;
import com.example.vmmusic.app.utils.T;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * 注册登录页面 Created by awx19 on 2016/4/7.
 */
public class RegisterLoginActivity extends Activity {

	private static final String VERIFY = "getverify";
	private static final String REGISTER = "register";
	private static final String LOGIN = "login";
	public static final String TOKEN="token";
	private int type;// 0注册，1登录，2获取验证码
	private JSONObject orignJSON;
	private int status;
	// 登录注册成功

	/**
	 * 登录注册选择按钮
	 */
	RadioGroup radioGroup;
	RadioButton radioButton_login;
	RadioButton radioButton_register;
	/**
	 * 注册手机号码
	 */
	EditText editText_phone;
	/**
	 * 输入的验证码
	 */
	EditText editText_code;
	/**
	 * 注册密码
	 */
	EditText editText_register_password;
	/**
	 * 登录用户名
	 */
	EditText editText_user_name;
	/**
	 * 登录密码
	 */
	EditText editText_login_password;
	/**
	 * 获得验证码
	 */
	TextView textView_code;
	/**
	 * 登录按钮
	 */
	Button button_login;
	/**
	 * 注册按钮
	 */
	Button button_register;
	LinearLayout login_linear;
	LinearLayout register_linear;
	LinearLayout third_party_linear;
	/**
	 * 第三方登录
	 */
	public static Tencent mTencent;
	private AuthInfo mAuthInfo;
	/**
	 * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
	 */
	private SsoHandler mSsoHandler;
	private Oauth2AccessToken mAccessToken;
	App app;

	private HashMap<String, String> map;// 参访请求参数和value
	private MyTask task;// 异步任务

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_and_login);
		app = (App) getApplication();
		// QQ互联
		mTencent = Tencent.createInstance(app.APP_ID, this.getApplicationContext());
		// 创建微博实例
		// mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
		// Constants.REDIRECT_URL, Constants.SCOPE);
		// 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
		mAuthInfo = new AuthInfo(this, app.APP_KEY, app.REDIRECT_URL, app.SCOPE);
		mSsoHandler = new SsoHandler(RegisterLoginActivity.this, mAuthInfo);
		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.login_radio_group);
		radioButton_login = (RadioButton) findViewById(R.id.login);
		radioButton_register = (RadioButton) findViewById(R.id.register);
		editText_phone = (EditText) findViewById(R.id.register_phone);
		editText_code = (EditText) findViewById(R.id.register_code);
		editText_register_password = (EditText) findViewById(R.id.register_password);
		editText_user_name = (EditText) findViewById(R.id.login_user);
		editText_login_password = (EditText) findViewById(R.id.login_password);
		textView_code = (TextView) findViewById(R.id.register_verification);
		button_login = (Button) findViewById(R.id.login_btn);
		button_register = (Button) findViewById(R.id.register_btn);
		login_linear = (LinearLayout) findViewById(R.id.login_linear);
		register_linear = (LinearLayout) findViewById(R.id.register_linear);
		third_party_linear = (LinearLayout) findViewById(R.id.third_party_linear);
		radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
		textView_code.setOnClickListener(onClickListener);
		button_login.setOnClickListener(onClickListener);
		button_register.setOnClickListener(onClickListener);
	}

	/**
	 * QQ互联监听
	 */
	BaseUiListener loginListener = new BaseUiListener(this) {
		
		protected void doComplete(JSONObject values) {
			Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
			initOpenidAndToken(values);
			String token = mTencent.getAccessToken();
			
			if (token != null) {
				Log.i("QQtoken", token.toString());
				Intent intent = new Intent(RegisterLoginActivity.this, HomePageActivity.class);
				startActivity(intent);
			}
		}
	};

	/**
	 * 第三方登录
	 *
	 * @param view
	 */
	public void onClickAuth(View view) {
		if (view.getId() == R.id.wb_btn) {
			mSsoHandler.authorizeClientSso(new AuthListener(this));
			mAccessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
			
			if (mAccessToken != null) {
				Log.i("mAccessToken", mAccessToken.toString());
				Intent intent = new Intent(RegisterLoginActivity.this, HomePageActivity.class);
				startActivity(intent);
			}
		} else if (view.getId() == R.id.qq_btn) {
			mTencent = Tencent.createInstance(app.APP_ID, this.getApplicationContext());
			if (!mTencent.isSessionValid()) {
				mTencent.login(this, "all", loginListener);
			}
		} else if (view.getId() == R.id.wx_btn) {

		}

	}

	/**
	 * 初始化 Openid和token
	 *
	 * @param jsonObject
	 */

	public void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
			//	T.showShort(getApplicationContext(), token);
				mTencent.setOpenId(openId);
			}
		} catch (Exception e) {
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
			Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
		}

		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	/**
	 * radioButton选择监听
	 */
	RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (group.getCheckedRadioButtonId()) {
			case R.id.login:
				jumpToLogin();
				break;
			case R.id.register:
				jumpToRegister();
				break;
			default:// 默认情况下推荐RadioButton选中
				radioButton_login.setChecked(true);
				radioButton_register.setChecked(false);
				break;
			}
		}
	};

	/**
	 * 点击监听
	 */
	View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.register_verification:// 获得验证码
				getCode();
				break;
			case R.id.login_btn:// 登录
				login();
				break;
			case R.id.register_btn:// 注册
				register();
				break;
			}
		}
	};

	/**
	 * 获得验证码
	 */
	@SuppressWarnings("unchecked")
	private void getCode() {
		if (!TextUtils.isEmpty(getContent(editText_phone))) {// 判断是否为空
			String param = getContent(editText_phone);
			map = new HashMap<String, String>();
			map.put("tel", param);
			type = 2;
			task = new MyTask();
			task.execute(VERIFY);
		} else {
			T.showShort(getApplicationContext(), "手机号不能为空");
		}
	}

	/**
	 * 切换到注册页面
	 */
	protected void jumpToRegister() {
		login_linear.setVisibility(View.GONE);
		third_party_linear.setVisibility(View.GONE);
		register_linear.setVisibility(View.VISIBLE);
		editText_user_name.setText("");
		editText_login_password.setText("");
	}

	/**
	 * 切换到登录页面
	 */
	protected void jumpToLogin() {
		login_linear.setVisibility(View.VISIBLE);
		third_party_linear.setVisibility(View.VISIBLE);
		register_linear.setVisibility(View.GONE);
		editText_phone.setText("");
		editText_code.setText("");
		editText_register_password.setText("");
		editText_user_name.setText("textuser");
		editText_login_password.setText("123456");

	}

	/**
	 * 登录
	 */
	private void login() {
		if (checkEdit_login()) {// 判断是否为空

			// 跳转

			String user = getContent(editText_user_name);
			String password = getContent(editText_login_password);
			if(user.equals("textuser")&&password.equals("123456")){
				Intent intent = new Intent(RegisterLoginActivity.this, HomePageActivity.class);
				startActivity(intent);
				 finish();//登录成功后，关闭
			}else{
			loginInfo(user,password);
			}

		}
	}
	/**
	 * 登录
	 * @param user
	 * @param password
	 */
	private void loginInfo(String user, String password) {
		map = new HashMap<String, String>();

		map.put("tel", user);
		map.put("password", password);
		type = 0;
		task = new MyTask();
		task.execute(LOGIN);
		
	}

	/**
	 * 注册
	 */
	@SuppressWarnings("unchecked")
	private void register() {
		if (checkEdit_register()) {// 判断是否为空
			String phoneNum = getContent(editText_phone);// 手机号
			String code = getContent(editText_code);// 验证码
			String password = getContent(editText_register_password);
			
			map = new HashMap<String, String>();
			map.put("tel", phoneNum);
			map.put("verify", code);
			map.put("password", password);
			type = 1;
			task = new MyTask();
			task.execute(REGISTER);
		}
	}

	/**
	 * 获得输入的内容
	 *
	 * @param editText
	 * @return
	 */
	private String getContent(EditText editText) {
		return editText.getText().toString();
	}

	/**
	 * 判断登录输入是否为空
	 *
	 * @return 不为空
	 */
	private boolean checkEdit_login() {
		if (TextUtils.isEmpty(getContent(editText_user_name))) {
			T.showShort(getApplicationContext(), "用户名不能为空");
		} else if (TextUtils.isEmpty(getContent(editText_login_password))) {
			T.showShort(getApplicationContext(), "密码不能为空");
		} else {
			return true;
		}
		return false;
	}

	/**
	 * 判断注册输入是否为空
	 *
	 * @return 不为空
	 */
	private boolean checkEdit_register() {
		if (TextUtils.isEmpty(getContent(editText_code))) {
			T.showShort(getApplicationContext(), "验证码不能为空");
		} else if (TextUtils.isEmpty(getContent(editText_register_password))) {
			T.showShort(getApplicationContext(), "密码不能为空");
		} else {
			return true;
		}
		return false;
	}

	/**
	 * 获取验证码
	 *
	 * @author Administrator
	 */

	class MyTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			HttpUtils httpUtils = HttpUtils.getInstance();
			String result = httpUtils.NewpostData(arg0[0], map);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			switch (type) {
			case 0:// 登录
				status = jsonRegisterAndLogin(result);
				if (status==1) {
					spToken(result);
					// 跳转首页
					Intent intent = new Intent(RegisterLoginActivity.this, HomePageActivity.class);
					startActivity(intent);
					 finish();//登录成功后，关闭
				} else if(status==2){
					T.showShort(RegisterLoginActivity.this, "密码错误");
					
				}else if(status==0){
					T.showShort(RegisterLoginActivity.this, "网络错误");
					
				}else{
					T.showShort(RegisterLoginActivity.this, "用户不存在");
				}
				break;
			case 1:// 注册
				status = jsonRegisterAndLogin(result);
				if (status==1) {
					// jumpToLogin();//跳转登录页面
					T.showShort(RegisterLoginActivity.this, "注册成功");
					//成功后自动登录
					loginInfo(getContent(editText_user_name), getContent(editText_register_password));				
					
				} else if(status==2){
					T.showShort(RegisterLoginActivity.this, "验证码错误或已过期");
				}else if(status==3){
				
					T.showShort(RegisterLoginActivity.this, "用户已存在");
				}else{
					T.showShort(RegisterLoginActivity.this, "网络错误");
				}
				break;
			case 2:// 验证码
				jsonCode(result);
				break;
			default:

				break;
			}

			super.onPostExecute(result);
		}
	}

	;

	/**
	 * 解析验证码
	 *
	 * @param str
	 */
	private void jsonCode(String str) {
		try {
			orignJSON = new JSONObject(str);
			int status = orignJSON.getInt("status");

			switch (status) {
			case 1:// 成功
				editText_code.setText(orignJSON.getString("mobile_code"));
				break;
			case 2:
				T.showShort(RegisterLoginActivity.this, "请间隔5分钟获取验证码");
				break;
			default:
				break;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 登录注册是否成功
	 *
	 * @param str
	 * @return 是否成功
	 */
	private int jsonRegisterAndLogin(String str) {
		int status=0 ;
		try {
			orignJSON = new JSONObject(str);
			status = orignJSON.optInt("status", 0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	private void spToken(String result){
		SharedPreferences sp=getSharedPreferences(App.TOKEN, MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			String token=jsonObject.getString(TOKEN);
			editor.putString(App.TOKEN, token);
			editor.putBoolean(TOKEN, true);
			editor.commit();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
