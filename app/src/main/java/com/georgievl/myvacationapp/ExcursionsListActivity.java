package com.georgievl.myvacationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.georgievl.myvacationapp.entities.Vacation;

public class ExcursionsListActivity extends AppCompatActivity {

    Button btnAddExcursion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursions_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int currentVacationId = getIntent().getIntExtra("vacationId", -1);

        btnAddExcursion = findViewById(R.id.btn_addExcursion);

        btnAddExcursion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentVacationId == -1) {
                    Toast.makeText(ExcursionsListActivity.this, "No Vacation Selectec", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ExcursionsListActivity.this, ExcursionDetailsActivity.class);
                    intent.putExtra("vacationId", currentVacationId);
                    startActivity(intent);
                }

            }
        });
    }
}