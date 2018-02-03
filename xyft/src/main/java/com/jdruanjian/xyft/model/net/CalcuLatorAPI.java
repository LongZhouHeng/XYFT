package com.jdruanjian.xyft.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.xyft.BaseApplication;
import com.jdruanjian.xyft.Constants;
import com.jdruanjian.xyft.R;
import com.jdruanjian.xyft.model.basicmodel.CalcuLatorModel;

import org.json.JSONException;
import org.json.JSONObject;

public class CalcuLatorAPI extends BasicRequest {

///    notes_num 注数,period 期数,start_num 起始,bonus 奖金,rate 利率,period_money 每期
    private final BasicResponse.RequestListener mListener;
    private CalcuLatorModel model;
    private String notes_num,period,start_num,bonus,rate,period_money;
    private int type;
    public CalcuLatorAPI(Activity activity, String notes_num, String period, String start_num, String bonus,
                         String rate, String period_money, int type, BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.notes_num = notes_num;
        this.period = period;
        this.start_num = start_num;
        this.bonus = bonus;
        this.rate = rate;
        this.period_money = period_money;
        this.type = type;
    }

    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("notes_num",notes_num);
        jSONObject.put("period",period);
        jSONObject.put("start_num",start_num);
        jSONObject.put("bonus",bonus);
        jSONObject.put("rate",rate);
        jSONObject.put("period_money",period_money);
        jSONObject.put("type",type);
        return jSONObject;
    }

    private static String getHttpUrl() {
        return Constants.CALCULATOR;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), CalcuLatorModel.class);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<CalcuLatorModel>(model));

            } else {
                mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,"请求错误"));
            }
        }catch(Exception e){
            mListener.onComplete(new BasicResponse<String>(
                    BasicResponse.FAIL, model.msg));
        }
    }

    @Override
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        mListener.onComplete(new BasicResponse<String>(BasicResponse.FAIL,
                BaseApplication.getInst().getString(
                        R.string.errcode_network_unavailable)));
    }
    @Override
    public void onStart(int what) {

    }
    @Override
    public void onFinish(int what) {

    }

}
