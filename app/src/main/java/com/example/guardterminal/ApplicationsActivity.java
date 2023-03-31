package com.example.guardterminal;

import androidx.appcompat.R.layout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsActivity extends AppCompatActivity {
    List<Applications> applicationsList;
    RecyclerView recyclerView;
    Spinner dateSpinner;
    AdapterApplications adapterApplications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);
        recyclerView = findViewById(R.id.recyclerView);
        dateSpinner = findViewById(R.id.spinner_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApplicationsActivity.this));
        new Data().execute();
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<Applications> applications = new ArrayList<>();
                String selectedItem = dateSpinner.getSelectedItem().toString();
                for(Applications app: applicationsList){
                    if(app.getDate().equals(selectedItem)){
                        applications.add(app);
                    }
                }
                adapterApplications = new AdapterApplications(ApplicationsActivity.this,applications);
                recyclerView.setAdapter(adapterApplications);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private class Data extends AsyncTask<Void,Void, List<Applications>>{
        @Override
        protected List<Applications> doInBackground(Void... voids) {
            return new ApiHelper().getApplications();
        }
        @Override
        protected void onPostExecute(List<Applications> applications) {
            super.onPostExecute(applications);

            applicationsList = applications;

            List<String> dates = new ArrayList<>();
            for(Applications item: applications)
            {
                if(!dates.contains(item.getDate()))
                    dates.add(item.getDate());
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ApplicationsActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dates);
            dateSpinner.setAdapter(spinnerAdapter);

            adapterApplications = new AdapterApplications(ApplicationsActivity.this,applications);
            recyclerView.setAdapter(adapterApplications);

        }
    }
}