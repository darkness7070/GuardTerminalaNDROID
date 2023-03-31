package com.example.guardterminal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edittext_main);
        button = findViewById(R.id.button_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().equals(""))
                    new Login().execute(editText.getText().toString());
            }
        });
    }
    private class Login extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            return new ApiHelper().Auth(strings[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            button.setEnabled(false);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Intent intent = new Intent(MainActivity.this,ApplicationsActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(MainActivity.this, "Неверный логин", Toast.LENGTH_SHORT).show();
            }
            button.setEnabled(true);
        }
    }
}