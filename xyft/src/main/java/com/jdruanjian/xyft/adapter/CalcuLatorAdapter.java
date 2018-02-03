package com.jdruanjian.xyft.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jdruanjian.xyft.R;
import com.jdruanjian.xyft.model.entity.CalcuLatorList;

import java.util.ArrayList;


public class CalcuLatorAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<CalcuLatorList> calcuLatorLists;

    public CalcuLatorAdapter(FragmentActivity mActivity, ArrayList<CalcuLatorList> lotteryLists) {
        this.mActivity = mActivity;
        this.calcuLatorLists = lotteryLists;

    }



    @Override
    public int getCount() {
        return calcuLatorLists.size();
    }

    @Override
    public Object getItem(int position) {
        return calcuLatorLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.listitem_calculate, null);
            holder = ViewHolder.findAndCacheViews(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CalcuLatorList up = calcuLatorLists.get(position);
           holder.tv_period.setText(up.period);
           holder.tv_pay_num.setText(up.pay_num);
           holder.tv_now_pay.setText(up.now_pay);
           holder.tv_sum_pay.setText(up.sum_pay);
           holder.tv_now_bonus.setText(up.now_bonus);
           holder.tv_now_money.setText(up.now_money);
           holder.tv_rec_rate.setText(up.rec_rate);

        return convertView;
    }
  //  返回说明:msg，msgContext,datas:now_bonus当前奖金,now_money当前收益,now_pay当前投入,pay_num投入倍数,period追号期数,rec_rate收益比率,sum_pay累计投入

    public static class ViewHolder {
        TextView tv_period;
        TextView tv_pay_num;
        TextView tv_now_pay;
        TextView tv_now_money;
        TextView tv_sum_pay;
        TextView tv_rec_rate;
        TextView tv_now_bonus;

        public static ViewHolder findAndCacheViews(View view) {
            ViewHolder holder = new ViewHolder();
            holder.tv_period = (TextView) view.findViewById(R.id.tv_period);
            holder.tv_pay_num = (TextView) view.findViewById(R.id.tv_pay_num);
            holder.tv_now_pay = (TextView) view.findViewById(R.id.tv_now_pay);
            holder.tv_sum_pay = (TextView) view.findViewById(R.id.tv_sum_pay);
            holder.tv_now_bonus = (TextView) view.findViewById(R.id.tv_now_bonus);
            holder.tv_now_money = (TextView) view.findViewById(R.id.tv_now_money);
            holder.tv_rec_rate = (TextView) view.findViewById(R.id.tv_rec_rate);
            view.setTag(holder);
            return holder;
        }
    }

}
