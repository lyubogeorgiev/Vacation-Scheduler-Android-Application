package com.georgievl.myvacationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.georgievl.myvacationapp.database.VacationDatabaseBuilder;
import com.georgievl.myvacationapp.entities.Vacation;

import java.util.List;

public class VacationsListActivity extends AppCompatActivity {

    Button btnAddVacation;
    RecyclerView recyclerView;
    VacationDatabaseBuilder db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacations_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAddVacation = findViewById(R.id.btn_addVacation);

        btnAddVacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationsListActivity.this, VacationDetailsActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.rv_vacationsList);
        db = VacationDatabaseBuilder.getDatabase(getApplicationContext());

        List<Vacation> allVacations = db.vacationDao().getAllVacations();

        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vacationAdapter.setVacations(allVacations);
    }
}