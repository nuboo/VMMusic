package com.example.vmmusic.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.vmmusic.R;
import com.example.vmmusic.app.utils.T;

/**
 * 注册登录页面
 * Created by awx19 on 2016/4/7.
 */
public class RegisterLoginActivity extends Activity {
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
    /**
     * 第三方登录按钮
     */
    TextView qq_btn;
    TextView wb_btn;
    TextView wx_btn;

    LinearLayout login_linear;
    LinearLayout register_linear;
    LinearLayout third_party_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_and_login);
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
        qq_btn = (TextView) findViewById(R.id.qq_btn);
        wb_btn = (TextView) findViewById(R.id.wb_btn);
        wx_btn = (TextView) findViewById(R.id.wx_btn);
        login_linear = (LinearLayout) findViewById(R.id.login_linear);
        register_linear = (LinearLayout) findViewById(R.id.register_linear);
        third_party_linear = (LinearLayout) findViewById(R.id.third_party_linear);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        textView_code.setOnClickListener(onClickListener);
        button_login.setOnClickListener(onClickListener);
        button_register.setOnClickListener(onClickListener);
        qq_btn.setOnClickListener(onClickListener);
        wb_btn.setOnClickListener(onClickListener);
        wx_btn.setOnClickListener(onClickListener);
    }

    /**
     * radioButton选择监听
     */
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.login:
                    login_linear.setVisibility(View.VISIBLE);
                    third_party_linear.setVisibility(View.VISIBLE);
                    register_linear.setVisibility(View.GONE);
                    editText_phone.setText("");
                    editText_code.setText("");
                    editText_register_password.setText("");
                    break;
                case R.id.register:
                    login_linear.setVisibility(View.GONE);
                    third_party_linear.setVisibility(View.GONE);
                    register_linear.setVisibility(View.VISIBLE);
                    editText_user_name.setText("");
                    editText_login_password.setText("");
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
                case R.id.qq_btn://QQ
                    break;
                case R.id.wb_btn://微博
                    break;
                case R.id.wx_btn://微信
                    break;
            }
        }
    };

    /**
     * 获得验证码
     */
    private void getCode() {
        if (!TextUtils.isEmpty(getContent(editText_phone))) {//判断是否为空

        } else {
            T.showShort(getApplicationContext(), "手机号不能为空");
        }
    }

    /**
     * 登录
     */
    private void login() {
        if (checkEdit_login()) {//判断是否为空

        }
    }

    /**
     * 注册
     */
    private void register() {
        if (checkEdit_register()) {//判断是否为空

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
     * @return
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
     * @return
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
}
