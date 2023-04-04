package com.example.guardterminal;

import androidx.appcompat.R.layout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    List<Applications> applicationsList;
    RecyclerView recyclerView;
    Spinner dateSpinner;
    Spinner typeSpinner;
    Spinner subdivisionSpinner;
    AdapterApplications adapterApplications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);
        recyclerView = findViewById(R.id.recyclerView);
        dateSpinner = findViewById(R.id.spinner_date_rv);
        typeSpinner = findViewById(R.id.spinner_type_rv);
        subdivisionSpinner = findViewById(R.id.spinner_subdivision_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApplicationsActivity.this));
        new Data().execute();
        typeSpinner.setOnItemSelectedListener(this);
        dateSpinner.setOnItemSelectedListener(this);
        subdivisionSpinner.setOnItemSelectedListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        List<Applications> applications = new ArrayList<>();
        for(Applications app: applicationsList){
            String dateSelected = dateSpinner.getSelectedItem().toString();
            String typeSelected = typeSpinner.getSelectedItem().toString();
            String subdSelected = subdivisionSpinner.getSelectedItem().toString();
            if(app.getDate().equals(dateSelected) || dateSelected.equals("Любая")) {
                if(app.getType().equals(typeSelected) || typeSelected.equals("Любой"))
                    if(app.getSubdivision().equals(subdSelected) || subdSelected.equals("Любое"))
                        applications.add(app);
            }
        }
        adapterApplications = new AdapterApplications(ApplicationsActivity.this,applications,new AdapterApplications.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Applications app = applicationsList.get(position);
                Intent intent = new Intent(ApplicationsActivity.this,SelectedAppActivity.class);
                intent.putExtra("app", app);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapterApplications);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
            List<String> types = new ArrayList<>();
            List<String> subdivisions = new ArrayList<>();
            dates.add("Любая");
            types.add("Любой");
            subdivisions.add("Любое");
            for(Applications item: applications)
            {
                if(!dates.contains(item.getDate()))
                    dates.add(item.getDate());
                if(!types.contains(item.getType()))
                    types.add(item.getType());
                if(!subdivisions.contains(item.getSubdivision()))
                    subdivisions.add(item.getSubdivision());
            }



            ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(ApplicationsActivity.this, layout.support_simple_spinner_dropdown_item,types);
            ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(ApplicationsActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dates);
            ArrayAdapter<String> subdivisionAdapter = new ArrayAdapter<String>(ApplicationsActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,subdivisions);

            typeSpinner.setAdapter(typeAdapter);
            subdivisionSpinner.setAdapter(subdivisionAdapter);
            dateSpinner.setAdapter(dateAdapter);

            adapterApplications = new AdapterApplications(ApplicationsActivity.this, applications, new AdapterApplications.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Applications app = applicationsList.get(position);
                    Intent intent = new Intent(ApplicationsActivity.this,SelectedAppActivity.class);
                    intent.putExtra("app", (Parcelable) app);
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapterApplications);

        }
    }
}