package com.example.MyRefrigerator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.Comparator;

public class TestActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView gridView;


    MySQLiteOpenHelper_recipe dbHelper;
    SQLiteDatabase mdb;
    Cursor meatCursor,vegetableCursor,fishCursor,fruitCursor,sauceCursor,drinkCursor,frozenCursor;
    private static  final int MENU_DELETE= Menu.FIRST+2;


    GridAdapter ga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_test);

        gridView = (GridView)findViewById(R.id.grid);
        gridView.setOnItemClickListener(this);

        dbHelper= new MySQLiteOpenHelper_recipe(this,"food.db",null,1);
        mdb = dbHelper.getWritableDatabase();

        meatCursor= mdb.rawQuery("SELECT * FROM meat",null);
        vegetableCursor= mdb.rawQuery("SELECT * FROM vegetable",null);
        fishCursor= mdb.rawQuery("SELECT * FROM fish",null);
        fruitCursor= mdb.rawQuery("SELECT * FROM fruit",null);
        sauceCursor= mdb.rawQuery("SELECT * FROM sauce",null);
        drinkCursor= mdb.rawQuery("SELECT * FROM drink",null);
        frozenCursor= mdb.rawQuery("SELECT * FROM frozen",null);

        ga = new GridAdapter();
        gridView.setAdapter(ga);

        Cursor cur[]= {meatCursor,vegetableCursor,fishCursor,fruitCursor,sauceCursor,drinkCursor,frozenCursor};
        for(int i=0;i<cur.length; i++)
        {
            String str="";
            while(cur[i].moveToNext())
            {
                if(cur[i] == cur[0])
                    str = "meat";
                else if(cur[i] == cur[1])
                    str = "vegetable";
                else if(cur[i] == cur[2])
                    str = "fish";
                else if(cur[i] == cur[3])
                    str = "fruit";
                else if(cur[i] == cur[4])
                    str = "sauce";
                else if(cur[i] == cur[5])
                    str = "drink";
                else if(cur[i] == cur[6])
                    str = "frozen";
                ga.addItme(new Griditem((cur[i].getString(1)), cur[i].getString(3), str));
                ga.notifyDataSetChanged();
            }
        }

        //오름 차순으로 비교
        Comparator<Griditem> TextAsc = new Comparator<Griditem>() {
            @Override
            public int compare(Griditem o1, Griditem o2) {
                return o1.getData().compareTo(o2.getData());
            }
        };

        Collections.sort(ga.item, TextAsc);
        ga.notifyDataSetChanged();

    }



    public void view_click(View view) {
        Intent intent= new Intent(TestActivity.this, CategoryActivity.class);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent i= new Intent(TestActivity.this, MainActivity2.class);
        startActivity(i);
        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Griditem mitem = ga.item.get(position);
        String name = mitem.getName();
        String name_trim = name.trim();

        Intent intent = new Intent(TestActivity.this, in_refrigerator.class);
        intent.putExtra("Ingredient",name_trim);
        startActivity(intent);


    }
}