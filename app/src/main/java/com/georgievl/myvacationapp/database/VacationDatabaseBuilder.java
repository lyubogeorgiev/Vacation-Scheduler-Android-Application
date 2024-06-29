package com.georgievl.myvacationapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.georgievl.myvacationapp.dao.ExcursionDao;
import com.georgievl.myvacationapp.dao.VacationDao;
import com.georgievl.myvacationapp.entities.Excursion;
import com.georgievl.myvacationapp.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 2)
@TypeConverters(DateConverter.class)
public abstract class VacationDatabaseBuilder extends RoomDatabase{

    public abstract VacationDao vacationDao();
    public abstract ExcursionDao excursionDao();
    private static VacationDatabaseBuilder INSTANCE;

    public static VacationDatabaseBuilder getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    VacationDatabaseBuilder.class, "VacationDB.db")
                    .allowMainThreadQueries().build();
        }

        return INSTANCE;
    }

}
