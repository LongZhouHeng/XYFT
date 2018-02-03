package com.jdruanjian.xyft.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jdruanjian.xyft.BaseActivity;
import com.jdruanjian.xyft.Constants;
import com.jdruanjian.xyft.MainActivity;
import com.jdruanjian.xyft.R;
import com.jdruanjian.xyft.model.entity.LoginBean;
import com.jdruanjian.xyft.model.entity.RegisterBean;
import com.jdruanjian.xyft.utils.NetworkUtils;
import com.jdruanjian.xyft.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Longlong on 2017/12/21.
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    private LoginBean model;
    private String uid;
    private String unane,endtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setBar();
        showBackBtn();
    }


    @OnClick({R.id.tv_fogetpwd, R.id.tv_toregister, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_fogetpwd:
                Intent intent = new Intent(this,FindPassWordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_toregister:
                Intent intent1 = new Intent(this,RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_login:
                doRequest();
                break;
        }
    }
    public void doRequest() {
        if (isLoading) {
            return;
        }
        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);
            return;
        }
        //    System.out.println("QQQQQQQQ-----=====" + collegeName);
        OkGo.post(Constants.LOGIN)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("username", etUsername.getText().toString())
                .params("appId", "3")
                .params("pwd", etPassword.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        model = JSON.parseObject(s.getBytes(), LoginBean.class);
                        if (model.status.equals("0")) {
                            uid = model.data.uid;
                            unane = model.data.uname;
                            endtime = model.data.effectiveTime;
                            //保存uid
                            saveUserMsg();

                            Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent1);
                            finish();
                        } else {
                            Toast.makeText(getApplication(), model.msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                        Toast.makeText(getApplication(), "数据有误", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void saveUserMsg() {
        PrefUtils.putString(getApplication(), "uid", uid);
        PrefUtils.putString(getApplication(), "name",  unane);
        PrefUtils.putString(getApplication(), "endtime",  endtime);
//        PrefUtils.putString(getApplication(), "utoken", utoken);
//        PrefUtils.putString(getApplication(), "state", state);
        //       PrefUtils.putString(getApplication(), "phone", tvLoginPhone.getText().toString());
        //       PrefUtils.putString(getApplication(), "uid", uid);
//        PrefUtils.putString(getApplication(),"inviteNum",inviteNum);
//        PrefUtils.putString(getApplication(), "member_truename", name);
//        PrefUtils.putString(getApplication(), "member_token", member_token);
//        PrefUtils.putString(getApplication(), "member_is_pwd", member_is_pwd);
//        PrefUtils.putString(getApplication(), "member_pwd_life", member_pwd_life);
        // PrefUtils.putString(getApplication(), "member_token", member_token);
        //PrefUtils.putString(getApplication(),);
    }
}
