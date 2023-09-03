package com.example.MyRefrigerator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FoodinfoActivity extends AppCompatActivity {
    private static  final int MENU_DELETE= Menu.FIRST+2;//컨텍스트메뉴 아이디
    MySQLiteOpenHelper_recipe dbHelper;
    MySQLiteOpenHelper mHelper;
    SQLiteDatabase mdb,rdb;
    Cursor Cursor ;
    SimpleCursorAdapter ca;
    ListView lv;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodinfo);
        Intent intent= getIntent();


        String strCol[]={MySQLiteOpenHelper_recipe.COLUMN_NAME_NAME,MySQLiteOpenHelper_recipe.COLUMN_NAME_DAYDAY};//리스트에 띄울 이름과 보관기한 컬럼을 배열로 만듦
        dbHelper= new MySQLiteOpenHelper_recipe(this,"food.db",null,1);
        mdb= dbHelper.getWritableDatabase();

        mHelper = new MySQLiteOpenHelper(this);
        rdb = mHelper.getWritableDatabase();

        lv= (ListView)findViewById(R.id.list);

        TextView tv= (TextView) findViewById(R.id.text);
        String str= intent.getStringExtra("info");//카테고리 액티비티에서 info키의 값을 string형으로 가져옴
        if(str.equals("meat"))//가져온 값이 같을 시
        {
            tv.setText("육류");
            Cursor= mdb.rawQuery("SELECT * FROM meat",null);//커서는 해당 table의 정보만 가져옴
            name= "meat";// 전역변수에 table명 저장
        }
        else if(str.equals("vegetable"))
        {
            tv.setText("채소류");
            Cursor= mdb.rawQuery("SELECT * FROM vegetable",null);
            name= "vegetable";
        }
        else if(str.equals("fish"))
        {
            tv.setText("생선류");
            Cursor= mdb.rawQuery("SELECT * FROM fish",null);
            name="fish";
        }
        else if(str.equals("fruit"))
        {
            tv.setText("과일류");
            Cursor= mdb.rawQuery("SELECT * FROM fruit",null);
            name="fruit";
        }
        else if(str.equals("sauce"))
        {
            tv.setText("소스류");
            Cursor= mdb.rawQuery("SELECT * FROM sauce",null);
            name="sauce";
        }
        else if(str.equals("drink"))
        {
            tv.setText("음료");
            Cursor=mdb.rawQuery("SELECT * FROM drink",null);
            name="drink";
        }
        else if(str.equals("frozen"))
        {
            tv.setText("냉동식품");
            Cursor=mdb.rawQuery("SELECT * FROM frozen",null);
            name="frozen";
        }
        ca= new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,Cursor,strCol,new int[]{android.R.id.text1,android.R.id.text2},0);//strcol배열 값을 가져옴
        lv.setAdapter(ca);
        lv.setOnItemClickListener(mlistener);
        registerForContextMenu(lv);
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)//컨텍스트 메뉴 생성
    {
        if(v==lv)
        {
            menu.add(Menu.NONE,MENU_DELETE,Menu.NONE,"delete");//
        }
        super.onCreateContextMenu(menu,v,menuInfo);
    }
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position= info.position;
        Cursor.moveToPosition(position);
        String food_name= Cursor.getString(1);

        switch(item.getItemId())
        {
            case MENU_DELETE: mdb.execSQL("DELETE FROM "+name +" WHERE food_name=  '"+food_name+"';");
                Cursor=mdb.rawQuery("SELECT * FROM "+name,null);
                Cursor old= ca.swapCursor(Cursor);
                old.close();
        }
        lv.deferNotifyDataSetChanged();
        return super.onContextItemSelected(item);
    }
    AdapterView.OnItemClickListener mlistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(FoodinfoActivity.this, in_refrigerator.class);
            Cursor cursor = (Cursor) ca.getItem(position);
            String index = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper_recipe.COLUMN_NAME_NAME));
            intent.putExtra("Ingredient",index);
            startActivity(intent);
        }
    };

}