package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity2 extends AppCompatActivity {

    private ListView lv;
    ArrayAdapter dbArray;
    SQLite sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        lv = (ListView) findViewById(R.id.lvv);

        sql = new SQLite(MainActivity2.this);
        dbArray = new ArrayAdapter<Data>(MainActivity2.this, android.R.layout.simple_list_item_1, sql.getAll());
        lv.setAdapter(dbArray);
    }
}