package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    EditText username, age;
    Button save,next,other, other1;
    boolean isusernameValid, isageValid,Correct;
    TextInputLayout usernameError,ageError;
    DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        age = (EditText) findViewById(R.id.age);
        save = (Button) findViewById(R.id.Save);
        next = (Button) findViewById(R.id.Next);
        other = (Button) findViewById(R.id.other);
        other1 = (Button) findViewById(R.id.other1);
        usernameError = (TextInputLayout) findViewById(R.id.usernameError);
        ageError = (TextInputLayout) findViewById(R.id.ageError);

        DB = new DatabaseHelper(this);

        other1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Ky eshte nje toast",Toast.LENGTH_LONG).show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetValidation();
                SQLiteDatabase objDb = new DatabaseHelper(MainActivity.this).getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseModelHelper.username, username.getText().toString());
                contentValues.put(DatabaseModelHelper.age, age.getText().toString());
                try {
                    Boolean checkUser = DB.checkusername(username.getText().toString());
                    if (checkUser==false &&Correct==true){
                        long id = objDb.insert(DatabaseModelHelper.UsersTable, null, contentValues);
                        if (id > 0)
                            Toast.makeText(MainActivity.this, getString(R.string.success_message), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, getString(R.string.user_exists), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    objDb.close();
                }
            }


        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity3.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void SetValidation() {
        // Check for a valid username.
        if (username.getText().toString().isEmpty()) {
            usernameError.setError(getResources().getString(R.string.usernameerror));
            isusernameValid = false;
        } else  {
            isusernameValid = true;
            usernameError.setErrorEnabled(false);
        }

        //Check for a valid age
        if (age.getText().toString().isEmpty()) {
            ageError.setError(getResources().getString(R.string.ageerror));
            isageValid = false;
        } else  {
            isageValid = true;
            ageError.setErrorEnabled(false);
        }
        if (isusernameValid && isageValid) {
            Toast.makeText(getApplicationContext(), R.string.correct, Toast.LENGTH_SHORT).show();
            Correct=true;
        }
    }

}