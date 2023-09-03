package com.example.MyRefrigerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    Griditem griditem;
    Context context;
    TextView tv_name;
    TextView tv_data;
    ImageView iv;
    String str;

    ArrayList<Griditem> item = new ArrayList<Griditem>();


    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        griditem = item.get(position);
        context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridlayout, parent, false);
        }

        tv_name = convertView.findViewById(R.id.textView);
        tv_data = convertView.findViewById(R.id.textView2);
        iv = convertView.findViewById(R.id.iv);

        tv_name.setText(griditem.getName());
        tv_data.setText(griditem.getData());
        str = griditem.getStr();

        if(str.equals("meat")){
            iv.setImageResource(R.drawable.meat);
        }else if(str.equals("vegetable")){
            iv.setImageResource(R.drawable.vegetable);
        }else if(str.equals("fish")){
            iv.setImageResource(R.drawable.fish);
        }else if(str.equals("fruit")){
            iv.setImageResource(R.drawable.fruit);
        }else if(str.equals("sauce")){
            iv.setImageResource(R.drawable.sauce);
        }else if(str.equals("drink")){
            iv.setImageResource(R.drawable.drink);
        }else if(str.equals("frozen")){
            iv.setImageResource(R.drawable.frozen);
        }




        return convertView;
    }

    public void addItme(Griditem griditem) {
        item.add(griditem);
    }

}


