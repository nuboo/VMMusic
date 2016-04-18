package com.example.vmmusic.app.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.vmmusic.R;
import com.example.vmmusic.app.listener.BaseUiListener;
import com.example.vmmusic.app.utils.App;
import com.example.vmmusic.app.utils.HttpUtils;
import com.example.vmmusic.app.utils.T;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import android.app.Activity;
import android.content.Intent;
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
 * 注册登录页面
 * Created by awx19 on 2016/4/7.
 */
public class RegisterLoginActivity extends Activity {

	
	private static final String VERIFY="getverify";
    private static final String REGISTER="register";
    private static final String LOGIN="login";

    private int type;//0注册，1登录，2获取验证码
    private JSONObject orignJSON;
    private boolean success;//登录注册成功

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
    Tencent mTencent;
    App app;
    private static boolean isServerSideLogin = false;

    private HashMap<String, String> map;//参访请求参数和value
    private MyTask task;//异步任务


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_and_login);
        app = (App) getApplication();
        //QQ互联
        mTencent = Tencent.createInstance(app.APP_ID, this.getApplicationContext());

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
    IUiListener loginListener = new BaseUiListener(this) {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
        }
    };

    /**
     * 第三方登录
     *
     * @param view
     */
    public void onClickAuth(View view) {
        if (view.getId() == R.id.wb_btn) {
        } else if (view.getId() == R.id.qq_btn) {
            mTencent.login(this, "all", loginListener);
        } else if (view.getId() == R.id.wx_btn) {

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTencent.onActivityResult(requestCode, resultCode, data);
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
                case R.id.register_verification://获取验证码
                    getCode();
                    break;
                default://默认情况下推荐RadioButton选中
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
                case R.id.register_verification://获得验证码
                    getCode();
                    break;
                case R.id.login_btn://登录
                    login();
                    break;
                case R.id.register_btn://注册
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
        if (!TextUtils.isEmpty(getContent(editText_phone))) {//判断是否为空
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

    }

    /**
     * 登录
     */
    private void login() {
        if (checkEdit_login()) {//判断是否为空

        	//跳转
        	
        	
        	String user=getContent(editText_user_name);
        	String password=getContent(editText_login_password);
        	map=new HashMap<String, String>();
        	
        	map.put("tel", user);
        	map.put("password", password);
        	type=0;
        	task=new MyTask();
        	task.execute(LOGIN);

        
        }
    }

    /**
     * 注册
     */
    @SuppressWarnings("unchecked")
    private void register() {
        if (checkEdit_register()) {//判断是否为空
            String phoneNum = getContent(editText_phone);//手机号
            String code = getContent(editText_code);//验证码
            String password = getContent(editText_register_password);
            T.showShort(RegisterLoginActivity.this, "tel" + phoneNum + "  code" + code + "  pass" + password);
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

    class MyTask extends AsyncTask< String, Void, String>{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			HttpUtils httpUtils=HttpUtils.getInstance();
			
		String result=	httpUtils.NewpostData(arg0[0],map );
			return result;
		}
    	@Override
    	protected void onPostExecute(String result) {
    		// TODO Auto-generated method stub
    		T.showShort(RegisterLoginActivity.this, result);
    		switch (type) {
			case 0://登录
				success=jsonRegisterAndLogin(result);
				if(success){
					
					//跳转首页
					   Intent intent =new Intent(RegisterLoginActivity.this,HomePageActivity.class);
		            startActivity(intent);
		          //  finish();//登录成功后，关闭
				}else{
					T.showShort(RegisterLoginActivity.this, "登录失败");
					 Intent play =new Intent(RegisterLoginActivity.this,HomePageActivity.class);
			            startActivity(play);
				}
				break;
			case 1://注册
				success=jsonRegisterAndLogin(result);
				if(success){
					  // jumpToLogin();//跳转登录页面
					   
					   Intent intent =new Intent(RegisterLoginActivity.this,HomePageActivity.class);
			            startActivity(intent);
				}else{
					T.showShort(RegisterLoginActivity.this, "注册失败");
				}
				break;
			case 2://验证码
				jsonCode(result);
			
		
				break;

			default:
				
				break;
			}
    		
    	
			
			
    		
    		super.onPostExecute(result);
    	}
    };
    
    

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
                case 1://成功
                    editText_code.setText(orignJSON.getString("mobile_code"));
                    break;
                case 2:
                    T.showShort(RegisterLoginActivity.this, "网络连接超时,请重新获取");
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
    private boolean jsonRegisterAndLogin(String str) {
        int status = 2;
        try {
            orignJSON = new JSONObject(str);
            status = orignJSON.optInt("status", 2);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        switch (status) {
            case 1:
                success = true;
                break;
            case 2:
                success = false;
                break;

            default:
                break;
        }
        return success;
    }
}
