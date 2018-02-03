package com.jdruanjian.xyft.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jdruanjian.xyft.BaseActivity;
import com.jdruanjian.xyft.R;
import com.jdruanjian.xyft.adapter.CalcuLatorAdapter;
import com.jdruanjian.xyft.model.basicmodel.CalcuLatorModel;
import com.jdruanjian.xyft.model.entity.CalcuLatorList;
import com.jdruanjian.xyft.model.net.BasicResponse;
import com.jdruanjian.xyft.model.net.CalcuLatorAPI;
import com.jdruanjian.xyft.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 龙龙 on 2017/9/11.
 */

@SuppressLint("Registered")
public class Calculator_Activity extends BaseActivity {
    @BindView(R.id.pour_number)
    EditText pourNumber;
    @BindView(R.id.period_number)
    EditText periodNumber;
    @BindView(R.id.start_douber)
    EditText startDouber;
    @BindView(R.id.bonus_total)
    EditText bonusTotal;
    @BindView(R.id.interest_rate)
    EditText interestRate;
    @BindView(R.id.every_period_money)
    EditText everyPeriodMoney;
    @BindView(R.id.btn_calculate)
    Button btnCalculate;
    @BindView(R.id.btn_calculate_1)
    Button btnCalculate1;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.tv_back)
    TextView tvBack;
    private String notes_num, period, start_num, bonus, rate, period_money;
    private int type = 0;
    private ArrayList<CalcuLatorList> calcuLatorList;
    private CalcuLatorModel calcuLatorModel;
    private CalcuLatorAdapter calcuLatorAdapter;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        ButterKnife.bind(this);
        setTitleResult1("计算器");
        showBackBtn1();
        tvBack.setTextColor(getResources().getColor(R.color.praise));
        setBar();
        calcuLatorList = new ArrayList<CalcuLatorList>();
        calcuLatorAdapter = new CalcuLatorAdapter(this, calcuLatorList);
        lv.setAdapter(calcuLatorAdapter);
        notes_num = pourNumber.getText().toString();
        period = periodNumber.getText().toString();
        start_num = startDouber.getText().toString();
        bonus = bonusTotal.getText().toString();
        rate = interestRate.getText().toString();
        period_money = everyPeriodMoney.getText().toString();
    }

    ///    notes_num 注数,period 期数,start_num 起始,bonus 奖金,rate 利率,period_money 每期
    @OnClick({R.id.btn_calculate, R.id.btn_calculate_1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_calculate:
                type = 1;
                if (validate()) {
                    doRequest();
                }else {
                    showdialog1();
                }


                break;
            case R.id.btn_calculate_1:
                type = 2;
                if (validate1()) {
                    doRequest();
                }else {
                    showdialog1();
                }
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

        CalcuLatorAPI api = new CalcuLatorAPI(this, notes_num, period, start_num, bonus, rate, period_money, type, new BasicResponse.RequestListener() {
            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    calcuLatorModel = (CalcuLatorModel) response.model;
                    if (calcuLatorModel.msgContext.equals("datasSuc")) {
                        calcuLatorList.clear();
                        calcuLatorList.addAll((ArrayList<CalcuLatorList>) calcuLatorModel.datas);
                        Log.e("heihei", calcuLatorList.size() + "");
                        calcuLatorAdapter.notifyDataSetChanged();
                    }
                    if (calcuLatorModel.msgContext.equals("oneMillion")) {
                        calcuLatorList.clear();
                        calcuLatorList.addAll((ArrayList<CalcuLatorList>) calcuLatorModel.datas);
                        Log.e("heihei", calcuLatorList.size() + "");
                        calcuLatorAdapter.notifyDataSetChanged();
                        showdialog();
//                        Toast.makeText(getApplication(),"累计投入超过一百万，购买计划不合适",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    calcuLatorModel = (CalcuLatorModel) response.model;
                    calcuLatorList.clear();
                    calcuLatorAdapter.notifyDataSetChanged();
                    showdialog();
                    //Toast.makeText(getApplication(),"累计投入超过一百万，购买计划不合适",Toast.LENGTH_SHORT).show();
                }
            }
        });
        api.executeRequest(0);
    }

    // 验证方法
    private boolean validate() {
        notes_num = pourNumber.getText().toString();

        if (TextUtils.isEmpty(notes_num)) {
  //          Toast.makeText(Calculator_Activity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        period = periodNumber.getText().toString();
        if (TextUtils.isEmpty(period)) {
   //         Toast.makeText(Calculator_Activity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        start_num = startDouber.getText().toString();
        if (TextUtils.isEmpty(start_num)) {
  //         Toast.makeText(Calculator_Activity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        bonus = bonusTotal.getText().toString();
        if (TextUtils.isEmpty(bonus)) {
 //           Toast.makeText(Calculator_Activity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        rate = interestRate.getText().toString();
        if (TextUtils.isEmpty(rate)) {
 //           Toast.makeText(Calculator_Activity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // 验证方法
    private boolean validate1() {
        notes_num = pourNumber.getText().toString();

        if (TextUtils.isEmpty(notes_num)) {
 //           Toast.makeText(Calculator_Activity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        period = periodNumber.getText().toString();
        if (TextUtils.isEmpty(period)) {
//            Toast.makeText(Calculator_Activity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        start_num = startDouber.getText().toString();
        if (TextUtils.isEmpty(start_num)) {
 //           Toast.makeText(Calculator_Activity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        bonus = bonusTotal.getText().toString();
        if (TextUtils.isEmpty(bonus)) {
//            Toast.makeText(Calculator_Activity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        period_money = everyPeriodMoney.getText().toString();
        if (TextUtils.isEmpty(period_money)) {
 //           Toast.makeText(Calculator_Activity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showdialog() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_noexist, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);

        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        // 设置全屏
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void showdialog1() {

        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_incomplete, null);
        TextView tv_ensure = (TextView) localView.findViewById(R.id.tv_ensure);

        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.CENTER);
        // 设置全屏
        WindowManager windowManager = this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
