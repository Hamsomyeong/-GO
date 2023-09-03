package com.example.MyRefrigerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    //아이템을 세트로 담기 위한 어레이
    ArrayList<Listitem> item = new ArrayList<Listitem>();
    Context context;
    TextView tvname;
    TextView tvdata;
    Listitem listitem;
    ImageView iv;
    String icon;

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

        context = parent.getContext();
        listitem = item.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.customlayout,parent,false);
        }

        tvname = convertView.findViewById(R.id.tv_name);
        tvdata = convertView.findViewById(R.id.tv_data);
        iv = convertView.findViewById(R.id.iv_icon);

        tvname.setText(listitem.getName());
        tvdata.setText(listitem.getData());
        icon = listitem.getIcon();

        //MainActivity2에서 저장한 테이블명을 통해 이미지뷰에 아이콘 불러옴
        if(icon.equals("meat")){
            iv.setImageResource(R.drawable.meat);
        }else if(icon.equals("vegetable")){
            iv.setImageResource(R.drawable.vegetable);
        }else if(icon.equals("fish")){
            iv.setImageResource(R.drawable.fish);
        }else if(icon.equals("fruit")){
            iv.setImageResource(R.drawable.fruit);
        }else if(icon.equals("sauce")){
            iv.setImageResource(R.drawable.sauce);
        }else if(icon.equals("drink")){
            iv.setImageResource(R.drawable.drink);
        }else if(icon.equals("frozen")){
            iv.setImageResource(R.drawable.frozen);
        }

        return convertView;
    }

    public void addItme(Listitem listitem){
        item.add(listitem);
    }


}
