package com.example.MyRefrigerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    MySQLiteOpenHelper_recipe dbHelper;
    SQLiteDatabase mdb;
    ArrayList<Kinds> al = new ArrayList<>();
    ArrayAdapter<String> aa;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        Intent intent= getIntent();
        dbHelper= new MySQLiteOpenHelper_recipe(this,"food.db",null,1);
        mdb= dbHelper.getWritableDatabase();


//        aa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,al);
        al.add(new Kinds(R.drawable.meat,"육류"));
        al.add(new Kinds(R.drawable.vegetable,"채소"));
        al.add(new Kinds(R.drawable.fish,"생선"));
        al.add(new Kinds(R.drawable.fruit,"과일"));
        al.add(new Kinds(R.drawable.sauce,"소스류"));
        al.add(new Kinds(R.drawable.drink,"음료"));
        al.add(new Kinds(R.drawable.frozen,"냉동식품"));

        // adapter
        KakaoAdapter adapter = new KakaoAdapter(getApplicationContext(), R.layout.row, al);
        lv = (ListView)findViewById(R.id.lv_list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(listener);
    }
    public void recipe_onClick(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }
    public void onBackPressed()
    {
        Intent intent= new Intent(CategoryActivity.this,TestActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//아이템 클릭 시 클릭 위치에 따라 info키로 값을 전해줌
            Intent intent = new Intent(CategoryActivity.this, FoodinfoActivity.class);
            if(position==0) { intent.putExtra("info", "meat"); }
            else if(position==1) { intent.putExtra("info","vegetable"); }
            else if(position==2) { intent.putExtra("info","fish"); }
            else if(position==3) { intent.putExtra("info","fruit"); }
            else if(position==4) { intent.putExtra("info","sauce"); }
            else if(position==5) { intent.putExtra("info","drink"); }
            else if(position==6) { intent.putExtra("info","frozen"); }
            startActivity(intent);
        }
    };

    public void add_click(View view) {
        Intent intent= new Intent(CategoryActivity.this,AddActivity.class);
        startActivity(intent);
    }
}

class KakaoAdapter extends BaseAdapter {

    Context context;     // 현재 화면의 제어권자
    int layout;              // 한행을 그려줄 layout
    ArrayList<Kinds> al;     // 다량의 데이터
    LayoutInflater inf; // 화면을 그려줄 때 필요
    public KakaoAdapter(Context context, int layout, ArrayList<Kinds> al) {
        this.context = context;
        this.layout = layout;
        this.al = al;
        this.inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//constext에서 layoutflater가져오기
    }
    @Override
    public int getCount() { // 총 데이터의 개수를 리턴
        return al.size();
    }// 총 데이터의 개수를 리턴
    @Override
    public Object getItem(int position) { // 해당번째의 데이터 값
        return al.get(position);
    }// 해당번째의 데이터 값
    @Override
    public long getItemId(int position) { // 해당번째의 고유한 id 값
        return position;
    }// 해당번째의 고유한 id 값

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {// 해당번째의 행에 내용을 셋팅(데이터와 레이아웃의 연결관계 정의)

        if (convertView == null)
            convertView = inf.inflate(layout, null);

        ImageView iv = (ImageView)convertView.findViewById(R.id.image);
        TextView tvName=(TextView)convertView.findViewById(R.id.tv);

        Kinds m = al.get(position);

        iv.setImageResource(m.img);
        tvName.setText(m.name);
        return convertView;
    }

}

class Kinds {
    int img;
    String name = "";

    public Kinds(int img, String name) {
        this.img = img;
        this.name = name;
    }
    public Kinds() {}// 기존 코드와 호환을 위해서 생성자 작업시 기본생성자도 추가
}