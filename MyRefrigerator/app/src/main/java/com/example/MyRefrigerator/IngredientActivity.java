package com.example.MyRefrigerator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {
    ListView lv;
    MySQLiteOpenHelper_recipe dbHelper;
    SQLiteDatabase mdb;
    Cursor mCursor;
    private static  final int MENU_DELETE= Menu.FIRST+2;
    ArrayList<String> al;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        lv= (ListView)findViewById(R.id.list);

        dbHelper= new MySQLiteOpenHelper_recipe(this,"food.db",null,1);
        mdb = dbHelper.getWritableDatabase();
        mCursor= mdb.rawQuery("SELECT * FROM meat UNION SELECT * FROM vegetable " +
                "UNION SELECT * FROM fruit UNION SELECT * FROM sauce " +
                "UNION SELECT * FROM drink UNION SELECT * FROM frozen",null);

        al= new ArrayList<>();
        aa= new ArrayAdapter(this,android.R.layout.simple_list_item_1,al);
        lv.setAdapter(aa);

        while (mCursor.moveToNext()) {
            al.add(mCursor.getString(1));
            aa.notifyDataSetChanged();
        }
    }

    public void return_click(View view) {

        Intent i= new Intent(IngredientActivity.this,MainActivity2.class);
        startActivity(i);
    }

    public void view_click(View view) {
        Intent intent= new Intent(IngredientActivity.this,CategoryActivity.class);

        startActivity(intent);
        finish();
    }

}
