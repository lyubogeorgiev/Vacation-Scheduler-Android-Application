package com.georgievl.myvacationapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionId;

    private String excursionTitle;

    private Date excursionDate;

    private int vacationId;

    public Excursion(int excursionId, String excursionTitle, Date excursionDate, int vacationId) {
        this.excursionId = excursionId;
        this.excursionTitle = excursionTitle;
        this.excursionDate = excursionDate;
        this.vacationId = vacationId;
    }

    public int getExcursionId() {
        return excursionId;
    }

    public String getExcursionTitle() {
        return excursionTitle;
    }

    public void setExcursionTitle(String excursionTitle) {
        this.excursionTitle = excursionTitle;
    }

    public Date getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(Date excursionDate) {
        this.excursionDate = excursionDate;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }
}
