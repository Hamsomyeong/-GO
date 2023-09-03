package com.example.MyRefrigerator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    MySQLiteOpenHelper_recipe mydb;
    SQLiteDatabase mdb;

    Cursor[] cursors = new Cursor[7];
    String str_icon;

    private static  final int MENU_DELETE= Menu.FIRST+2;

    Adapter adapter;
    ListView lv;
    int music[]={R.raw.u,R.raw.chui,R.raw.ssuk,R.raw.sa};
    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private Toast toast;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ////////////////////////////////////////////////////////////////////////////////////////////
        Animation Activitytrance = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.alphascale2);

        RelativeLayout a1 = (RelativeLayout)findViewById(R.id.total);

        a1.startAnimation(Activitytrance);
        ////////////////////////////////////////////////////////////////////////////////////////////

        mydb = new MySQLiteOpenHelper_recipe(this, "food.db", null, 1);
        mdb = mydb.getWritableDatabase();

        lv = (ListView)findViewById(R.id.list);

        adapter = new Adapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(MainActivity2.this);
        registerForContextMenu(lv);


        //재료 db에 쓰일 커서
        cursors[0] = mdb.rawQuery("SELECT * FROM meat", null);
        cursors[1] = mdb.rawQuery("SELECT * FROM vegetable",null);
        cursors[2] = mdb.rawQuery("SELECT * FROM fish", null);
        cursors[3] = mdb.rawQuery("SELECT * FROM fruit", null);
        cursors[4] = mdb.rawQuery("SELECT * FROM sauce", null);
        cursors[5] = mdb.rawQuery("SELECT * FROM drink", null);
        cursors[6] = mdb.rawQuery("SELECT * FROM frozen", null);

        //현재날짜 가져옴
        LocalDate LocalTime = LocalDate.now();

        for(int i = 0; i< cursors.length;i++){
            while(cursors[i].moveToNext()){

                //보관 기간 가져옴
                String cursor_data = cursors[i].getString(3);
                LocalDate test = LocalDate.parse(cursor_data, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                //아이콘 표시를 위한 테이블명 저장
                if(cursors[i] == cursors[0])
                    str_icon = "meat";
                else if(cursors[i] == cursors[1])
                    str_icon = "vegetable";
                else if(cursors[i] == cursors[2])
                    str_icon = "fish";
                else if(cursors[i] == cursors[3])
                    str_icon = "fruit";
                else if(cursors[i] == cursors[4])
                    str_icon = "sauce";
                else if(cursors[i] == cursors[5])
                    str_icon = "drink";
                else if(cursors[i] == cursors[6])
                    str_icon = "frozen";


                if(LocalTime.isAfter(test)) {  // 현재날짜와 보관기한 비교 후 보관기한 지난 재료 추가
                    adapter.addItme(new Listitem(cursors[i].getString(1), cursor_data, null, str_icon));
                }else if(LocalTime.isEqual(test)){  //보관기간과 현재날짜가 같은경우 추가
                    adapter.addItme(new Listitem(cursors[i].getString(1), cursor_data, null, str_icon));
                }else if(LocalTime.isBefore(test)){  // 보관기한 남은 재료 추가
                    long day = ChronoUnit.DAYS.between(LocalTime, test);  //현재시간과 보관기한 비교
                    if(day <=3) {  //보관기간이 3일이 안남았으면 추가
                        adapter.addItme(new Listitem(cursors[i].getString(1), cursor_data, null, str_icon));
                    }
                }
                Random ran= new Random();
                int num= ran.nextInt(3);
                if(adapter.getCount()==1)
                {
                    MediaPlayer player= MediaPlayer.create(MainActivity2.this,music[num]);
                    player.start();
                }
                else if(adapter.getCount()==3)
                {
                    MediaPlayer player= MediaPlayer.create(MainActivity2.this,R.raw.neng);
                    player.start();
                }
            }
        }

        //오름 차순으로 비교
        Comparator<Listitem> TextAsc = new Comparator<Listitem>() {
            @Override
            public int compare(Listitem o1, Listitem o2) {
                return o1.getData().compareTo(o2.getData());
            }
        };

        Collections.sort(adapter.item, TextAsc);
        adapter.notifyDataSetChanged();


    }
    public void search_Click(View view){
        Intent intent = new Intent(this,Search.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {  //두번 눌러 완전 종료
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {

            ActivityCompat.finishAffinity(this);
            System.exit(0);
            toast.cancel();
        }

    }

    public void my_refrigerator_onClick(View view){
        //Intent intent = new Intent(MainActivity2.this,IngredientActivity.class);
        Intent intent = new Intent(MainActivity2.this,TestActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(MainActivity2.this, in_refrigerator.class);

        Listitem mData = adapter.item.get(position);

        intent.putExtra("Ingredient",mData.getName().trim());
        startActivity(intent);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        if(v==lv)
        {
            menu.add(Menu.NONE,MENU_DELETE,Menu.NONE,"재료 삭제");
        }
        super.onCreateContextMenu(menu,v,menuInfo);
    }


    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //리스트뷰에서 선택한 항목의 위치가져옴
        int position = info.position;

        //선택한 항목의 위치를 커스텀어댑터에 연결
        Listitem mData = adapter.item.get(position);

        //선택한 항목의 이름, 날짜 가져옴
        String delet_name = mData.getName();
        String delet_name_trim = delet_name.trim();
        String delet_data = mData.getData();
        String delet_data_trim = delet_data.trim();

        cursors[0] = mdb.rawQuery("SELECT * FROM meat", null);
        cursors[1] = mdb.rawQuery("SELECT * FROM vegetable",null);
        cursors[2] = mdb.rawQuery("SELECT * FROM fish", null);
        cursors[3] = mdb.rawQuery("SELECT * FROM fruit", null);
        cursors[4] = mdb.rawQuery("SELECT * FROM sauce", null);
        cursors[5] = mdb.rawQuery("SELECT * FROM drink", null);
        cursors[6] = mdb.rawQuery("SELECT * FROM frozen", null);

        String str = "";

        for(int i = 0; i<cursors.length;i++){
            while (cursors[i].moveToNext()) {
                if (cursors[i].getString(1).contains(delet_name_trim) && cursors[i].getString(3).contains(delet_data_trim)) { //재료db에서 이름과 날짜가 같은 항목을 검색
                    int id = cursors[i].getInt(0);  //일치하는 항목의 id값 가져오기
                    //커서 순서에 따른 db컬럼값 가져오기
                    if(cursors[i] == cursors[0])
                        str = "meat";
                    else if(cursors[i] == cursors[1])
                        str = "vegetable";
                    else if(cursors[i] == cursors[2])
                        str = "fish";
                    else if(cursors[i] == cursors[3])
                        str = "fruit";
                    else if(cursors[i] == cursors[4])
                        str = "sauce";
                    else if(cursors[i] == cursors[5])
                        str = "drink";
                    else if(cursors[i] == cursors[6])
                        str = "frozen";
                    //컬럼값과 id값으로 선택한 항목 삭제
                    mdb.execSQL("DELETE FROM "+ str +" WHERE " + MySQLiteOpenHelper_recipe.COLUMN_NAME_ID + " = " + id + ";");

                }
            }
        }

        //선택한 항목을 리스트뷰에서 삭제
        adapter.item.remove(position);
        adapter.notifyDataSetChanged();

        return super.onContextItemSelected(item);
    }

    public void memo_click(View view) {
    Intent it= new Intent(MainActivity2.this,MemoActivity.class);
    startActivity(it);
    }

    public void setting_click(View view) {

    }
}