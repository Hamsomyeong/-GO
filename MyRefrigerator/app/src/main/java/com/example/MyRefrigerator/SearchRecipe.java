package com.example.MyRefrigerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class SearchRecipe extends AppCompatActivity {

    TextView tv1;
    TextView tv2;
    TextView tv3;
    SQLiteDatabase mdb;
    Cursor mCursor;
    MySQLiteOpenHelper mHelper;
    ImageView iv;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);
        mHelper = new MySQLiteOpenHelper(this);
        mdb = mHelper.getWritableDatabase();
        mCursor = mdb.rawQuery("SELECT*FROM WorldFood", null);

        tv1 = (TextView)findViewById(R.id.text1);
        tv2 = (TextView)findViewById(R.id.text2);
        tv3 = (TextView)findViewById(R.id.text3);
        iv = (ImageView)findViewById(R.id.image);

        Intent intent = getIntent();
        String str1 = intent.getStringExtra("Name");
        String str2 = intent.getStringExtra("Supplies");
        String str3 = intent.getStringExtra("Recipe");


        tv1.setText(str1);
        tv2.setText(str2);
        tv3.setText(str3);

        String Image = intent.getStringExtra("Image");

        AssetManager as = getResources().getAssets();
        InputStream is = null;

        AssetManager as1 = getResources().getAssets();
        InputStream is1 = null;

        try{
            is = as.open(Image+".PNG");
            bitmap = BitmapFactory.decodeStream(is);

            iv.setImageBitmap(bitmap);

            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            is1 = as1.open(Image+".png");
            bitmap = BitmapFactory.decodeStream(is1);

            iv.setImageBitmap(bitmap);

            is1.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}