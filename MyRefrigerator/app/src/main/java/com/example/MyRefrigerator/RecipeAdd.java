package com.example.MyRefrigerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeAdd extends AppCompatActivity {

    MySQLiteOpenHelper mHelper;
    SQLiteDatabase mdb;
    ContentValues values = new ContentValues();

    RadioGroup rg_updata;
    RadioButton rb;
    String str_rb;

    EditText et_nameupdata, et_suppliesupdata, et_recipeupdata;

    TextView tv_dbcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add);

        mHelper = new MySQLiteOpenHelper(this);
        mdb = mHelper.getWritableDatabase();

        rg_updata = (RadioGroup)findViewById(R.id.rg_updata);
        rg_updata.setOnCheckedChangeListener(rb_listener);

        et_nameupdata = (EditText)findViewById(R.id.et_nameupdata);
        et_suppliesupdata = (EditText)findViewById(R.id.et_suppliesupdata);
        et_recipeupdata = (EditText)findViewById(R.id.et_recipeupdata);

        tv_dbcheck = (TextView)findViewById(R.id.tv_dbcheck);
    }
    public void onClickRecipeAdd(View view){
        EtText ettext = new EtText();
        ettextinfo(ettext);
        values.put(MySQLiteOpenHelper.Kinds, str_rb);
        dbadd(ettextinfo(ettext));
        mdb.insert("WorldFood", null, values);
    }
    private void dbadd(EtText ettext){
        values.put(MySQLiteOpenHelper.Name, ettext.et_name);
        values.put(MySQLiteOpenHelper.Supplies, ettext.et_supplies);
        values.put(MySQLiteOpenHelper.Recipe, ettext.et_recipe);
    }

    RadioGroup.OnCheckedChangeListener rb_listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            rb = (RadioButton)findViewById(rg_updata.getCheckedRadioButtonId());
            str_rb =rb.getText().toString();
            Toast.makeText(RecipeAdd.this, str_rb, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    EtText ettextinfo(EtText ettext){
        String et_name = et_nameupdata.getText().toString();
        String et_supplies = et_suppliesupdata.getText().toString();
        String et_recipe = et_recipeupdata.getText().toString();
        ettext.ettextinfo(et_name, et_supplies, et_recipe);
        return ettext;
    }
}
class EtText{
    String et_name;
    String et_supplies;
    String et_recipe;
    EtText(){

    }
    EtText ettextinfo(String et_name, String et_supplies, String et_recipe){
        this.et_name = et_name;
        this.et_supplies = et_supplies;
        this.et_recipe = et_recipe;
        return this;
    }
}