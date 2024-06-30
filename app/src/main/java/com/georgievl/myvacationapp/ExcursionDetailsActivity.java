package com.georgievl.myvacationapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.georgievl.myvacationapp.database.VacationDatabaseBuilder;
import com.georgievl.myvacationapp.entities.Excursion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetailsActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener startDate;
    final Calendar startCalendar=Calendar.getInstance();
    Button btnExcursionDate;
    Button btnSaveExcrusion;
    Date excursionDate;
    int vacationId;
    EditText etExcursionTitle;
    String excursionTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnExcursionDate = findViewById(R.id.btn_excursionDate);
        etExcursionTitle = findViewById(R.id.et_excursionTitle);


        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        btnExcursionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info=btnExcursionDate.getText().toString();
                try{
                    startCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog dpd = new DatePickerDialog(ExcursionDetailsActivity.this, startDate,startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

//                dpd.getDatePicker().setMinDate(startingDate.getTime() + 86400000);
                dpd.show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                excursionDate = startCalendar.getTime();

                btnExcursionDate.setText(sdf.format(startCalendar.getTime()));
            }

        };

        btnSaveExcrusion = findViewById(R.id.btn_saveExcursion);

        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(getApplicationContext());

        vacationId = getIntent().getIntExtra("vacationId", -1);
        btnSaveExcrusion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excursionTitle = etExcursionTitle.getText().toString();
                Excursion currentExcursion = new Excursion(0,excursionTitle, excursionDate, vacationId);

                db.excursionDao().insert(currentExcursion);

                Intent intent = new Intent(ExcursionDetailsActivity.this, ExcursionsListActivity.class);
                startActivity(intent);
            }
        });
    }
}