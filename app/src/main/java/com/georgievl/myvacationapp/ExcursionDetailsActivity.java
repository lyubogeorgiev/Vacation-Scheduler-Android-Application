package com.georgievl.myvacationapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    Button btnDeleteExcursion;
    Date excursionDate;
    int vacationId;
    EditText etExcursionTitle;
    String excursionTitle;
    TextView tvAssociatedVacationName;
    int excursionId = -1;
    Excursion currentExcursion;


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
        tvAssociatedVacationName = findViewById(R.id.tv_associatedVacationName);

        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(getApplicationContext());

        vacationId = getIntent().getIntExtra("vacationId", -1);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
        Date vacationStartDate = db.vacationDao().getVacation(vacationId).getStartDate();
        Date vacationEndDate = db.vacationDao().getVacation(vacationId).getEndDate();

        btnExcursionDate.setText(sdf.format(vacationStartDate));

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



                dpd.getDatePicker().setMinDate(vacationStartDate.getTime());
                dpd.getDatePicker().setMaxDate(vacationEndDate.getTime());

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

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            excursionId = extras.getInt("id");
        }



        if (excursionId != -1) {
            currentExcursion = db.excursionDao().getExcursion(excursionId);
            etExcursionTitle.setText(currentExcursion.getExcursionTitle());
            excursionDate = currentExcursion.getExcursionDate();

            btnExcursionDate.setText(sdf.format(excursionDate.getTime()));
        }

        btnDeleteExcursion = findViewById(R.id.btn_deleteExcursion);

        btnDeleteExcursion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (excursionId != -1) {
                    db.excursionDao().delete(currentExcursion);

                    Intent intent = new Intent(ExcursionDetailsActivity.this, ExcursionsListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No Vacation Selected", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(ExcursionDetailsActivity.this, ExcursionsListActivity.class);
                intent.putExtra("vacationId", vacationId);
                startActivity(intent);
            }
        });


        btnSaveExcrusion = findViewById(R.id.btn_saveExcursion);



        btnSaveExcrusion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excursionTitle = etExcursionTitle.getText().toString();

                if (excursionTitle.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Empty title!", Toast.LENGTH_LONG).show();
                } else {
                    Excursion currentExcursion = new Excursion(0,excursionTitle, excursionDate, vacationId);

                    db.excursionDao().insert(currentExcursion);

                    Intent intent = new Intent(ExcursionDetailsActivity.this, ExcursionsListActivity.class);
                    startActivity(intent);
                }

                Intent intent = new Intent(ExcursionDetailsActivity.this, ExcursionsListActivity.class);
                intent.putExtra("vacationId", vacationId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            Intent intent = new Intent(ExcursionDetailsActivity.this, ExcursionsListActivity.class);
            intent.putExtra("vacationId", vacationId);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.excursionNotification) {

            String dateFromScreen=btnExcursionDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate=null;
            try {
                myDate=sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long date=myDate.getTime();
            Intent intent=new Intent(ExcursionDetailsActivity.this, MyReceiver.class);
            intent.putExtra("key",currentExcursion.getExcursionTitle() + " is today!");
            PendingIntent sender= PendingIntent.getBroadcast(ExcursionDetailsActivity.this,++MainActivity.numAlert,intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, date, sender);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}