package com.jdruanjian.xyft.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jdruanjian.xyft.BaseActivity;
import com.jdruanjian.xyft.R;
import com.jdruanjian.xyft.adapter.PKListAdapter;
import com.jdruanjian.xyft.model.basicmodel.KSLotModel;
import com.jdruanjian.xyft.model.entity.KSlotBean;
import com.jdruanjian.xyft.model.net.AlreadyPKAPI;
import com.jdruanjian.xyft.model.net.BasicResponse;
import com.jdruanjian.xyft.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 龙龙 on 2017/8/25.
 */

public class Result_PK_Activity extends BaseActivity{
    View main;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    private PKListAdapter ksListAdapter;
    private ArrayList<KSlotBean> kSlotBeenlist;
    private KSLotModel model;
    private ListView mListView;
    private MaterialRefreshLayout materialRefreshLayout;
    private int PageNum =1;
    private String PageSize = "20" ;
    private String name;
    private String table_name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_pk);
        ButterKnife.bind(this);
        showBackBtn();
        setBar();
        mListView = (ListView) findViewById(R.id.lv);
        kSlotBeenlist = new ArrayList<KSlotBean>();
        ksListAdapter = new PKListAdapter(this, kSlotBeenlist);
        mListView.setAdapter(ksListAdapter);
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.finishRefreshLoadMore();
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //   Toast.makeText(getActivity(), "pull refresh", Toast.LENGTH_LONG).show();
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                        PageNum=1;
                        doRequest();
                    }
                }, 1000);

            }

            @Override
            public void onfinish() {
                //  Toast.makeText(getActivity(), "finish", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                //     Toast.makeText(getActivity(), "load more", Toast.LENGTH_LONG).show();
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();
                        PageNum ++;

                        doRequest();
                    }
                }, 1000);
            }
        });
        materialRefreshLayout.autoRefresh();
    }

    public void doRequest() {
        if (isLoading) {
            return;
        }

        if (!NetworkUtils.isNetworkAvaliable(this)) {
            toastIfActive(R.string.errcode_network_unavailable);

            return;
        }

        AlreadyPKAPI api = new AlreadyPKAPI(this,"xyftpk10",PageNum,PageSize,  new BasicResponse.RequestListener() {
            @Override
            public void onComplete(BasicResponse response) {
                if (response.error == BasicResponse.SUCCESS) {
                    model = (KSLotModel) response.model;
                    if (PageNum == 1) {
                        kSlotBeenlist.clear();
                    }
                    kSlotBeenlist.addAll((ArrayList<KSlotBean>) model.datas);
                    Log.e("heihei", kSlotBeenlist.size() + "");
                    ksListAdapter.notifyDataSetChanged();
                } else {
                    model = (KSLotModel) response.model;
                    kSlotBeenlist.clear();
                    ksListAdapter.notifyDataSetChanged();
                }
            }
        });
        api.executeRequest(0);


    }

}
