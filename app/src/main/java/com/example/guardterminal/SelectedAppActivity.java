package com.example.guardterminal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SelectedAppActivity extends AppCompatActivity {
    private TextView tvName,tvDate,tvType,tvSubdivision,tvArrival, tvLeaving;
    private Button btnArrival, btnLeaving, btnAccept, btnReject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_app);
        Applications app = (Applications)getIntent().getSerializableExtra("app");

        tvName = findViewById(R.id.name_app);
        tvDate = findViewById(R.id.date_app);
        tvType = findViewById(R.id.type_app);
        tvSubdivision = findViewById(R.id.subdivision_app);

        btnArrival = findViewById(R.id.btnArrival);
        btnLeaving = findViewById(R.id.btnLeaving);
        btnAccept = findViewById(R.id.btnAccept);
        btnReject = findViewById(R.id.btnReject);

        tvArrival = findViewById(R.id.tvArrival);
        tvLeaving = findViewById(R.id.tvLeaving);

        tvName.setText(app.getName());
        tvDate.setText(app.getDate());
        tvType.setText(app.getType());
        tvSubdivision.setText(app.getSubdivision());
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Accept().execute(app.getId());
            }
        });

        TimePickerDialog timePickerDialog = new TimePickerDialog(SelectedAppActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvArrival.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                    }
                }, hour, minute, true);
        TimePickerDialog timePickerDialog1 = new TimePickerDialog(SelectedAppActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvLeaving.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                    }
                }, hour, minute, true);
        btnArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        btnLeaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog1.show();
            }
        });
    }
    public class Accept extends AsyncTask<Integer, Void, Boolean>{


        @Override
        protected Boolean doInBackground(Integer... integers) {
            return new ApiHelper().AcceptApp(integers[0],tvArrival.getText().toString(),tvLeaving.getText().toString());
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Toast.makeText(SelectedAppActivity.this, "Одобрено", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SelectedAppActivity.this,ApplicationsActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}