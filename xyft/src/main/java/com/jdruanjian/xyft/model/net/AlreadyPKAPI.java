package com.jdruanjian.xyft.model.net;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hs.nohttp.rest.Response;
import com.jdruanjian.xyft.BaseApplication;
import com.jdruanjian.xyft.Constants;
import com.jdruanjian.xyft.R;
import com.jdruanjian.xyft.model.basicmodel.KSLotModel;

import org.json.JSONException;
import org.json.JSONObject;

public class AlreadyPKAPI extends BasicRequest {

    private final BasicResponse.RequestListener mListener;
    private KSLotModel model;
    private int PageNum;
    private String PageSize;
    private String table_name;
    public AlreadyPKAPI(Activity activity, String table_name, int PageNum, String PageSize, BasicResponse.RequestListener mListener) {
        super(activity, getHttpUrl());
        this.mListener = mListener;
        this.PageNum = PageNum;
        this.PageSize = PageSize;
        this.table_name = table_name;
    }



    @Override
    public JSONObject getObject() throws JSONException {
        JSONObject jSONObject = super.getObject();
        jSONObject.put("table_name",table_name);
        jSONObject.put("PageNum", PageNum);
        System.out.println("AAAAAAAAAAAA"+PageNum);
        jSONObject.put("PageSize", "20");
        System.out.println("CCCCCCCCCCCC"+PageSize);
        return jSONObject;

    }

    private static String getHttpUrl() {
        return Constants.ALREADY_LOTT;
    }
    public void executeRequest(int what) {
        setBody(mListener);
        BaseApplication.getInst().getRequestQueue().add(what, this, this);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        try {
            Log.d("GGGGGGG", response.toString());
            model = JSON.parseObject(response.get(), KSLotModel.class);
            System.out.println("BBBBBBBBBBBB"+model);
            if (model.msg.equals("success")) {
                mListener.onComplete(new BasicResponse<KSLotModel>(model));
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
    public void onFinish(int what) {

    }

    @Override
    public void onStart(int what) {

    }


}
