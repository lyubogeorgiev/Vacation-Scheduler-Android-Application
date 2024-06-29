package com.georgievl.myvacationapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "vacations")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private int vacationId;
    private String vacationTitle;
    private String accommodationPlace;
    private Date startDate;
    private Date endDate;

    public Vacation(int vacationId, String vacationTitle, String accommodationPlace, Date startDate, Date endDate) {
        this.vacationId = vacationId;
        this.vacationTitle = vacationTitle;
        this.accommodationPlace = accommodationPlace;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getVacationId() {
        return vacationId;
    }

    public String getVacationTitle() {
        return vacationTitle;
    }

    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }

    public String getAccommodationPlace() {
        return accommodationPlace;
    }

    public void setAccommodationPlace(String accommodationPlace) {
        this.accommodationPlace = accommodationPlace;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
