package com.jdruanjian.xyft.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jdruanjian.xyft.R;
import com.jdruanjian.xyft.model.entity.KSlotBean;

import java.util.ArrayList;

/**
 * Created by 龙龙 on 2017/9/6.
 */

public class PKListAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<KSlotBean> kSlotBeen;
    private String flag ;
    public PKListAdapter(Activity mActivity, ArrayList<KSlotBean> kSlotBeen) {

        this.mActivity = mActivity;
        this.kSlotBeen = kSlotBeen;
    }

    @Override
    public int getCount() {
        return kSlotBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return kSlotBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_pk, null);
            holder = ViewHolder.findAndCacheViews(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final KSlotBean ssLotBean = kSlotBeen.get(position);
        String str = ssLotBean.getPeriod();
        String period = str.substring(str.length()-3,str.length());
        holder.tv_numperiod.setText(period);

        String date = ssLotBean.getTime_current();
        String min = date.substring(8,10);
        String second = date.substring(10,12);
        String Date = min+":"+second;
        holder.tv_date.setText(Date);

        String[] num = ssLotBean.getNumber().split(",");
        holder.tv_pknum1.setText(num[0]);
        holder.tv_pknum2.setText(num[1]);
        holder.tv_pknum3.setText(num[2]);
        holder.tv_pknum4.setText(num[3]);
        holder.tv_pknum5.setText(num[4]);
        holder.tv_pknum6.setText(num[5]);
        holder.tv_pknum7.setText(num[6]);
        holder.tv_pknum8.setText(num[7]);
        holder.tv_pknum9.setText(num[8]);
        holder.tv_pknum10.setText(num[9]);
        return convertView;
    }


    public static class ViewHolder {

        TextView tv_numperiod;
        TextView tv_date;
        TextView tv_pknum1;
        TextView tv_pknum2;
        TextView tv_pknum3;
        TextView tv_pknum4;
        TextView tv_pknum5;
        TextView tv_pknum6;
        TextView tv_pknum7;
        TextView tv_pknum8;
        TextView tv_pknum9;
        TextView tv_pknum10;

        private static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.tv_numperiod = (TextView) view.findViewById(R.id.tv_numperiod);
            holder.tv_date = (TextView) view.findViewById(R.id.tv_kstime);
            holder.tv_pknum1 = (TextView) view.findViewById(R.id.tv_pk1);
            holder.tv_pknum2 = (TextView) view.findViewById(R.id.tv_pk2);
            holder.tv_pknum3 = (TextView) view.findViewById(R.id.tv_pk3);
            holder.tv_pknum4 = (TextView) view.findViewById(R.id.tv_pk4);
            holder.tv_pknum5 = (TextView) view.findViewById(R.id.tv_pk5);
            holder.tv_pknum6 = (TextView) view.findViewById(R.id.tv_pk6);
            holder.tv_pknum7 = (TextView) view.findViewById(R.id.tv_pk7);
            holder.tv_pknum8 = (TextView) view.findViewById(R.id.tv_pk8);
            holder.tv_pknum9 = (TextView) view.findViewById(R.id.tv_pk9);
            holder.tv_pknum10 = (TextView) view.findViewById(R.id.tv_pk10);
            view.setTag(holder);
            return holder;
        }
    }



}
