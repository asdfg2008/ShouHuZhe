package com.crg.tecentdemo;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by crg on 16/10/2.
 */

public class AddressListAdpter extends BaseAdapter {
    private Context mContext;
    private List<String> dataList;

    public AddressListAdpter(Context context, List<String> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override

    public int getCount() {
        Log.d("总的item数量------",dataList.size() + "");
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {

        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (convertView == null){
            hodler = new ViewHodler();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.suggest_address_item,null);
            hodler.titleTV = (TextView) convertView.findViewById(R.id.suggestAddress_item_title);
            hodler.addressTV = (TextView) convertView.findViewById(R.id.suggestAddress_item_address);

            convertView.setTag(hodler);
        }else{
            hodler = (ViewHodler) convertView.getTag();
        }


        String title_address = dataList.get(position);
            hodler.titleTV.setText(title_address.split("==")[0]);
            hodler.addressTV.setText(title_address.split("==")[1]);
        return convertView;
    }

    final class ViewHodler{
        TextView titleTV;
        TextView addressTV;
    }
}
