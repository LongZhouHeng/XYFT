package com.jdruanjian.xyft.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jdruanjian.xyft.BaseActivity;
import com.jdruanjian.xyft.Constants;
import com.jdruanjian.xyft.R;
import com.jdruanjian.xyft.model.basicmodel.QueryResultModel;
import com.jdruanjian.xyft.model.entity.RegisterBean;
import com.jdruanjian.xyft.model.entity.ResultListBean;
import com.jdruanjian.xyft.utils.NetworkUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Longlong on 2017/12/21.
 */

@SuppressLint("Registered")
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_qqnum)
    EditText etQqnum;
    private RegisterBean model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setBar();
        showBackBtn();

    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        doRequest();

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
        OkGo.post(Constants.REGISTER)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("username", etUsername.getText().toString())
                .params("appId", "3")
                .params("pwd", etPassword.getText().toString())
                .params("qq", etQqnum.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        model = JSON.parseObject(s.getBytes(), RegisterBean.class);
                        if (model.status.equals("0")) {
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplication(), model.data, Toast.LENGTH_SHORT).show();
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
}
