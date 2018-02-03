package com.jdruanjian.xyft.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.jdruanjian.xyft.BaseActivity;
import com.jdruanjian.xyft.R;


/**
 * Created by Longlong on 2017/12/14.
 */

@SuppressLint("Registered")
public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        setBar();
        showBackBtn();
        setTitlebar("关于我们");
    }
}
