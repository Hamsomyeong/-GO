package com.example.MyRefrigerator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    Context context;

    public static String _id = "_id";
    public static String Kinds = "kinds";
    public static String Name = "Name";
    public static String Recipe = "Recipe";
    public static String Supplies = "Supplies";
    public static String Image = "Image";

    private final static String TAG = "DataBaseHelper"; // Logcat에 출력할 태그이름
    // database 의 파일 경로
    private static String DB_PATH = "";
    private static String DB_NAME = "WorldFoodList.db";
    private SQLiteDatabase mDataBase;
    private Context mContext;


    public MySQLiteOpenHelper(Context context) {
        super(context,DB_NAME,null,1);


        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
        dataBaseCheck();
    }

    private void dataBaseCheck() {
        File dbFile = new File(DB_PATH + DB_NAME);
        if (!dbFile.exists()) {
            dbCopy();
        }
    }


    @Override
    public synchronized void close() {
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // db를 assets에서 복사해온다.
    private void dbCopy() {

        try {
            File folder = new File(DB_PATH);
            if (!folder.exists()) {
                folder.mkdir();
            }

            InputStream inputStream = mContext.getAssets().open(DB_NAME);
            String out_filename = DB_PATH + DB_NAME;
            OutputStream outputStream = new FileOutputStream(out_filename);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = inputStream.read(mBuffer)) > 0) {
                outputStream.write(mBuffer,0,mLength);
            }
            outputStream.flush();;
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("dbCopy","IOException 발생함");
        }
    }
}


//    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
//        super(context, name, factory, version);
//        this.context = context;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE WorldFood (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+"Kinds TEXT, Name TEXT, Supplies TEXT, Recipe TEXT, Image TEXT );");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS WorldFood");
//        onCreate(db);
//    }
//}
