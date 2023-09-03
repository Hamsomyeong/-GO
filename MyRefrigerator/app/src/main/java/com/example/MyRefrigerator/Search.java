package com.example.MyRefrigerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    MySQLiteOpenHelper mHelper;
    SQLiteDatabase mdb;
    Cursor mCursor;

    ListView lv_web;
    ArrayAdapter<String> aa_web;
    EditText et_search;
    String[] search = {"Google", "Naver", "Youtube"};
    ImageButton btn_search;

    ListView lv_recom;
    ArrayList al_recom = new ArrayList();
    ArrayAdapter aa_recom;

    String[] text_add = {"레시피 추가"};
    ArrayAdapter aa_text;
    boolean list_check;

    int _id;
    String kind, Name, Supplies, Recipe, Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        et_search = (EditText) findViewById(R.id.et_search);
        btn_search = (ImageButton) findViewById(R.id.btn_search);

        aa_web = new ArrayAdapter(Search.this, android.R.layout.simple_list_item_1, search);
        lv_web = (ListView) findViewById(R.id.list_websearch);
        lv_web.setAdapter(aa_web);
        lv_web.setOnItemClickListener(listener_web);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        mHelper = new MySQLiteOpenHelper(this);
        mdb = mHelper.getWritableDatabase();
        lv_recom = (ListView) findViewById(R.id.list_recomsearch);
        lv_recom.setAdapter(aa_recom);
        lv_recom.setOnItemClickListener(listener_recom);
    }

    AdapterView.OnItemClickListener listener_web = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String url = "";
            if (et_search.length() > 0) {
                if (position == 0) {
                    url = "https://www.google.com/search?q=" + et_search.getText().toString();// 구글
                } else if (position == 1) {
                    url = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=" + et_search.getText().toString();// 네이버
                } else if (position == 2) {
                    url = "https://www.youtube.com/results?search_query=" + et_search.getText().toString();//유튜브
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));// 인터넷 연결
                startActivity(intent);
            }
        }
    };

    public void search_onClick(View view){

        String tx_search = et_search.getText().toString();

        al_recom.clear();
        aa_recom = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, al_recom);

        String str = "SELECT * FROM WorldFood WHERE Name LIKE '%" + tx_search + "%'";
        mCursor = mdb.rawQuery(str, null);

        if (tx_search.length() > 0) {
            while (mCursor.moveToNext()) {
                if (mCursor.getString(2).contains(tx_search)) {
                    al_recom.add(mCursor.getString(2));
                    lv_recom.setAdapter(aa_recom);
                    list_check = true;
                }
            }
            if(al_recom.isEmpty() == true){
                aa_text = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, text_add);
                lv_recom.setAdapter(aa_text);
                list_check = false;
            }
        }
    }

    AdapterView.OnItemClickListener listener_recom = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(list_check == true){
                Intent intent = new Intent(Search.this, SearchRecipe.class);
                mdb = mHelper.getWritableDatabase();
                String sqlSelect = "SELECT * FROM WorldFood WHERE Name ='" + parent.getItemAtPosition(position).toString() + "'";
                mCursor = mdb.rawQuery(sqlSelect, null);

                mCursor.moveToPosition(0);
                _id = mCursor.getInt(0);
                kind = mCursor.getString(1);
                Name = mCursor.getString(2);
                Supplies = mCursor.getString(3);
                Recipe = mCursor.getString(4);
                Image = mCursor.getString(5);

                intent.putExtra(MySQLiteOpenHelper._id, _id);
                intent.putExtra(MySQLiteOpenHelper.Kinds, kind);
                intent.putExtra(MySQLiteOpenHelper.Name, Name);
                intent.putExtra(MySQLiteOpenHelper.Supplies, Supplies);
                intent.putExtra(MySQLiteOpenHelper.Recipe, Recipe);
                intent.putExtra(MySQLiteOpenHelper.Image, Image);

                startActivity(intent);
            }else if(list_check == false){
                Intent intent = new Intent(Search.this, RecipeAdd.class);
                startActivity(intent);
            }
        }

    };

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}