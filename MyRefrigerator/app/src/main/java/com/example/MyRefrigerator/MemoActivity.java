    package com.example.MyRefrigerator;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Context;
    import android.content.SharedPreferences;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.graphics.drawable.PaintDrawable;
    import android.os.Bundle;
    import android.preference.PreferenceManager;
    import android.view.ContextMenu;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ListView;
    import android.widget.Toast;

    import org.json.JSONArray;
    import org.json.JSONException;

    import java.util.ArrayList;

    public class MemoActivity extends AppCompatActivity {
        EditText et;
        ListView lv;
        Button btn;
        ArrayList<String> al;
        ArrayAdapter<String> aa;
        MySQLiteOpenHelper_recipe dbHelper;
        SQLiteDatabase mdb;
        Cursor[] cursors = new Cursor[7];
        private static final String SETTINGS_PLAYER_JSON = "settings_item_json";
        private static  final int MENU_DELETE= Menu.FIRST+2;//컨텍스트메뉴 아이디
        int j=0;
        @Override
     protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_memo);
            et = (EditText) findViewById(R.id.et_name);
            lv = (ListView) findViewById(R.id.list);
            btn = (Button) findViewById(R.id.btn);
            btn.setOnClickListener(listener);
            al= getStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);
            aa=new ArrayAdapter(this, android.R.layout.simple_list_item_1,al);

            lv.setAdapter(aa);
            registerForContextMenu(lv);

            dbHelper= new MySQLiteOpenHelper_recipe(this,"food.db",null,1);
            mdb = dbHelper.getWritableDatabase();
            cursors[0] = mdb.rawQuery("SELECT * FROM meat", null);
            cursors[1] = mdb.rawQuery("SELECT * FROM vegetable",null);
            cursors[2] = mdb.rawQuery("SELECT * FROM fish", null);
            cursors[3] = mdb.rawQuery("SELECT * FROM fruit", null);
            cursors[4] = mdb.rawQuery("SELECT * FROM sauce", null);
            cursors[5] = mdb.rawQuery("SELECT * FROM drink", null);
            cursors[6] = mdb.rawQuery("SELECT * FROM frozen", null);

        }
  View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdb = dbHelper.getWritableDatabase();
                String str = et.getText().toString();
                al.add(str);
                aa.notifyDataSetChanged();
                et.setText("");
                setStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, al);

                for(int i=0; i<cursors.length; i++) {
                    while(cursors[i].moveToNext()) {
                        if (cursors[i].getString(0).equals(str)) {
                            lv.setSelector(new PaintDrawable(0xffff0000));
                            Toast.makeText(MemoActivity.this, "hi", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        }

    };
    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();

        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
    }

    private ArrayList getStringArrayPref(Context context, String key) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList urls = new ArrayList();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
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
        switch(item.getItemId())
        {
            case MENU_DELETE: al.remove(position);
            setStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, al);
            aa.notifyDataSetChanged();
        }
        lv.deferNotifyDataSetChanged();
        return super.onContextItemSelected(item);
    }


        public void reset_click(View view) {
        al.clear();
        aa.notifyDataSetChanged();
        }
    }