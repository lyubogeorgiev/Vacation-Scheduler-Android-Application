package com.georgievl.myvacationapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.georgievl.myvacationapp.database.VacationDatabaseBuilder;
import com.georgievl.myvacationapp.entities.Excursion;
import com.georgievl.myvacationapp.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class VacationDetailsActivity extends AppCompatActivity {

    Button btnListExcursions;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar startCalendar=Calendar.getInstance();
    Button btnStartDate;
    Button btnEndDate;
    Button btnDeleteVacation;
    Date startingDate;
    Date endingDate;
    EditText etVacationTitle;
    EditText etHotelName;
    int vacationId = -1;
    Vacation currentVacation;

    RecyclerView recyclerView;
    VacationDatabaseBuilder db;

    Button btnSaveVacation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnStartDate = findViewById(R.id.btn_startDate);
        btnEndDate = findViewById(R.id.btn_endDate);

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String currentDate=sdf.format(new Date());
        String nextDayDate = sdf.format(new Date(System.currentTimeMillis() + 86400000));

        btnStartDate.setText(currentDate);
        btnEndDate.setText(nextDayDate);

        startingDate = new Date();
        endingDate = new Date(System.currentTimeMillis() + 86400000);

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info=btnStartDate.getText().toString();
                try{
                    startCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog dpd = new DatePickerDialog(VacationDetailsActivity.this, startDate,startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info=btnEndDate.getText().toString();
                try{
                    startCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog dpd = new DatePickerDialog(VacationDetailsActivity.this, endDate,startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));

                dpd.getDatePicker().setMinDate(startingDate.getTime() + 86400000);
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

                startingDate = startCalendar.getTime();

                btnStartDate.setText(sdf.format(startCalendar.getTime()));
            }

        };

        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                startCalendar.set(Calendar.YEAR, year);
                startCalendar.set(Calendar.MONTH, monthOfYear);
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                endingDate = startCalendar.getTime();
                btnEndDate.setText(sdf.format(startCalendar.getTime()));
            }

        };

        btnSaveVacation = findViewById(R.id.btn_saveVacation);
        etVacationTitle = findViewById(R.id.et_vacationTitle);
        etHotelName = findViewById(R.id.et_hotelName);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            vacationId = extras.getInt("id");
        }


        db = VacationDatabaseBuilder.getDatabase(getApplicationContext());

        if (vacationId != -1) {
            currentVacation = db.vacationDao().getVacation(vacationId);
            etVacationTitle.setText(currentVacation.getVacationTitle());
            etHotelName.setText(currentVacation.getAccommodationPlace());
            startingDate = currentVacation.getStartDate();
            endingDate = currentVacation.getEndDate();

//            String myFormat = "MM/dd/yy"; //In which you need put here
//            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

//            startingDate = startCalendar.getTime();

            btnStartDate.setText(sdf.format(startingDate.getTime()));
            btnEndDate.setText(sdf.format(endingDate.getTime()));
        }

        btnDeleteVacation = findViewById(R.id.btn_deleteVacation);

        btnDeleteVacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vacationId != -1) {

                    List<Excursion> associatedExcursions = db.excursionDao().getAssociatedExcursions(vacationId);

                    if (!associatedExcursions.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Can't Delete! There are excursion associated!", Toast.LENGTH_LONG).show();
                    } else {
                        db.vacationDao().delete(currentVacation);

                        Intent intent = new Intent(VacationDetailsActivity.this, VacationsListActivity.class);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No Vacation Selected", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSaveVacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vacationTitle = etVacationTitle.getText().toString();
                String hotelName = etHotelName.getText().toString();

                if (vacationTitle.isBlank() || hotelName.isBlank() ||
                        (startingDate == endingDate)) {
                    Toast.makeText(getApplicationContext(), "Blank Fields", Toast.LENGTH_LONG).show();
                } else {
                    if (vacationId != -1) {
                        //edit vacation
                        currentVacation.setVacationTitle(vacationTitle);
                        currentVacation.setAccommodationPlace(hotelName);
                        currentVacation.setStartDate(startingDate);
                        currentVacation.setEndDate(endingDate);

                        db.vacationDao().update(currentVacation);
                    } else {
                        //insert new vacation
                        Vacation vacation = new Vacation(0, vacationTitle, hotelName, startingDate, endingDate);

                        db.vacationDao().insert(vacation);
                    }


                    Intent intent = new Intent(VacationDetailsActivity.this, VacationsListActivity.class);
                    startActivity(intent);
                }

            }
        });

        btnListExcursions = findViewById(R.id.btn_listExcursions);

        btnListExcursions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vacationId == -1) {
                    Toast.makeText(getApplicationContext(), "No Vacation Selected", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(VacationDetailsActivity.this, ExcursionsListActivity.class);
                    intent.putExtra("vacationId", vacationId);
                    startActivity(intent);
                }

            }
        });

        recyclerView = findViewById(R.id.rv_excursionList);

        if (vacationId != -1) {
            List<Excursion> allExcursions = db.excursionDao().getAssociatedExcursions(vacationId);

            final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
            recyclerView.setAdapter(excursionAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            excursionAdapter.setExcursions(allExcursions);
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vacation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sharings) {
            String excursions = db.excursionDao().getAssociatedExcursions(currentVacation
                            .getVacationId())
                    .stream()
                    .map(Excursion::getExcursionTitle)
                    .collect(Collectors.joining(", " ));

            String vacationDetails = String.format("Vacation name: %s, " +
                    "Hotel name: %s, " +
                    "Vacation start date: %s, " +
                    "Vacation end date: %s, " +
                    "Vacation excursions: %s.",
                    currentVacation.getVacationTitle(),
                    currentVacation.getAccommodationPlace(),
                    currentVacation.getStartDate().toString(),
                    currentVacation.getEndDate().toString(),
                    excursions
                    );
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, vacationDetails);
            // (Optional) Here we're setting the title of the content
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Vacation shared");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}