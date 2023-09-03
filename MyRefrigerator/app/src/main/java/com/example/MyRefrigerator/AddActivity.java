package com.example.MyRefrigerator;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private static final int DIALOG_DATE = 1;
    MySQLiteOpenHelper_recipe dbHelper;
    SQLiteDatabase mdb;
    String table="";
    EditText et_name, et_num;
    Spinner spinner;
    int num;
    String date;
    String month2;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbHelper= new MySQLiteOpenHelper_recipe(this,"food.db",null,1);//food라는 데이터베이스가 있으면 오픈하고 없으면 생성한다.

        mdb = dbHelper.getWritableDatabase();//읽고쓰기 가능한 SQLiteDatabase를 반환받는다
        et_name= (EditText)findViewById(R.id.et_name);
        et_num=(EditText)findViewById(R.id.et_num);




        spinner = (Spinner) findViewById(R.id.spinner);

        String [] item = getResources().getStringArray(R.array.spinnerArray);

        ArrayAdapter<String> adapter = new ArrayAdapter<String >(this, R.layout.support_simple_spinner_dropdown_item,item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                switch (position){
                    case (1):
                        table = "meat";break;
                    case (2):
                        table = "vegetable";break;
                    case (3):
                        table = "fish";break;
                    case (4):
                        table = "fruit";break;
                    case (5):
                        table = "sauce";break;
                    case (6):
                        table = "drink";break;
                    case (7):
                        table = "frozen";break;
                    case (0):
                        num = 1;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void save_click(View view) {//저장버튼 클릭 시 입력한 정보가 데이터베이스에 입력됨

        ContentValues values= new ContentValues();

        if(et_name.getText().toString().length()>0 && num !=0) {
            values.put(MySQLiteOpenHelper_recipe.COLUMN_NAME_NAME, et_name.getText().toString());
            values.put(MySQLiteOpenHelper_recipe.COLUMN_NAME_NUM, et_num.getText().toString());
            values.put(MySQLiteOpenHelper_recipe.COLUMN_NAME_DAYDAY,date);

            mdb.insert(table, null, values);
            finish();

        }else if(num ==1){
            Toast.makeText(this, "저장할 음식과 종류를 선택해 주세요.", Toast.LENGTH_SHORT).show();
        }

    }
    public void date_click(View view) {
        showDialog(DIALOG_DATE);//날짜 설정 다이얼로그 띄우기
    }
    protected Dialog onCreateDialog(int id){
        switch(id){
            case DIALOG_DATE: //DatePickerDialog: 안드로이드에서 제공되는 기본 날짜 선택 대화상자 객체
                DatePickerDialog dpd= new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override //DatePickerDialog 생성 시 OnDateSetListener -> onDateSet콜백 설정
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(getApplicationContext(),
                                year+"년 "+(month+1)+"월 "+dayOfMonth+"일 을 선택했습니다",
                                Toast.LENGTH_SHORT).show();
                        int month1= month+1;
                        month2= String.format("%02d",month1);
                        day=String.format("%02d",dayOfMonth);
                        date= year+"-"+month2+"-"+day;
                    }
                }
                        ,// 사용자가 날짜 설정 후 다이얼로그 빠져나올때 호출할 리스너 등록
                        2020,11,27);//기본값
                return dpd;

        }
        return super.onCreateDialog(id);
    }


}