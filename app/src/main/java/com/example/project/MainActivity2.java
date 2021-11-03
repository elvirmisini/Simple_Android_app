package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView users;
    Button back,show,delete;
    DatabaseHelper DB;

    LinearLayout mainlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        users=(TextView) findViewById(R.id.users);
        back=(Button) findViewById(R.id.back);
        show=(Button) findViewById(R.id.show);
        delete=(Button) findViewById(R.id.delete);
        mainlayout=findViewById(R.id.mainLayout);
        DB = new DatabaseHelper(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data="";
                String data1="";
                Cursor cursor=DB.getAllData();
                if (cursor.getCount()==0){
                    Toast.makeText(MainActivity2.this,"No data to show",Toast.LENGTH_SHORT).show();
                }else {
                    while (cursor.moveToNext()){
                        data1+=cursor.getString(1)+"->"+cursor.getString(2)+"\n";
                    }
                    data += data1+ "\n\n";
                    users.setText(data);
                }
            }

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }


    public void delete() {
        Snackbar.make(mainlayout,"Are you sure you want to delete all data?",Snackbar.LENGTH_LONG).setAction("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                SQLiteDatabase obj = new DatabaseHelper(MainActivity2.this).getWritableDatabase();
                obj.execSQL("delete from Users");
                obj.close();
                finish();

            }
        }).show();
    }
}