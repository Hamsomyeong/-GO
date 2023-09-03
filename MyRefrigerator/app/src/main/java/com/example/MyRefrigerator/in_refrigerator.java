package com.example.MyRefrigerator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class in_refrigerator extends AppCompatActivity {

    SQLiteDatabase rdb;
    Cursor mCursor;
    MySQLiteOpenHelper mHelper;

    ListView lv;
    ArrayList al = new ArrayList();
    ArrayAdapter aa;

    String Name, supplies, Recipe, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_refrigerator);

        mHelper = new MySQLiteOpenHelper(this);
        rdb = mHelper.getWritableDatabase();



        lv = (ListView) findViewById(R.id.lv_menu);
        aa = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, al);
        lv.setAdapter(aa);


        Intent intent = getIntent();
        String name = intent.getStringExtra("Ingredient");

        //  String sqlSelect = "SELECT * FROM Worldfood WHERE Name ='" + parent.getItemAtPosition(position).toString() + "'";
        String sqlSelect = "SELECT * FROM WorldFood WHERE Supplies LIKE '%" + name + "%'";
        mCursor = rdb.rawQuery(sqlSelect, null);
        while (mCursor.moveToNext()) {

            al.add(mCursor.getString(2));
            lv.deferNotifyDataSetChanged();

        }
        lv.setOnItemClickListener(listener);
    }
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(in_refrigerator.this, SearchRecipe.class);

            String sqlSelect = "SELECT * FROM WorldFood WHERE Name LIKE '%" + parent.getItemAtPosition(position).toString() + "%'";
            mCursor = rdb.rawQuery(sqlSelect, null);

            mCursor.moveToPosition(0);
            Name = mCursor.getString(2);
            supplies = mCursor.getString(3);
            Recipe = mCursor.getString(4);
            image = mCursor.getString(5);

            intent.putExtra("Name", Name);
            intent.putExtra("Supplies", supplies);
            intent.putExtra("Recipe", Recipe);
            intent.putExtra("Image", image);

            startActivity(intent);
        }
    };

}

