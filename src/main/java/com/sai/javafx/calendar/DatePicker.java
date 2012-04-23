package com.sai.javafx.calendar;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;

import java.util.Locale;

public class DatePicker {

    private SimpleIntegerProperty selectedDate = new SimpleIntegerProperty();
    private SimpleIntegerProperty selectedMonth = new SimpleIntegerProperty();
    private SimpleIntegerProperty selectedYear = new SimpleIntegerProperty();

    private FXCalendar fxCalendar;


    public DatePicker(FXCalendar fxCalendar) {
        this.fxCalendar = fxCalendar;
        selectedDate.set(fxCalendar.getSelectedDate());
        selectedMonth.set(fxCalendar.getSelectedMonth());
        selectedYear.set(fxCalendar.getSelectedYear());
        fxCalendar.setLocale(Locale.ENGLISH);
    }

    /* GETTER'S FROM FXCALENDAR * */
    public Color getBaseColor() {
        return this.fxCalendar.getBaseColor();
    }

    public FXCalendarUtility getFXCalendarUtility() {
        return this.fxCalendar.getFXCalendarUtility();
    }

    public Locale getLocale() {
        return this.fxCalendar.getLocale();
    }

    public boolean getShowWeekNumber() {
        return this.fxCalendar.getShowWeekNumber();
    }

    public FXCalendar getFxCalendar() {
        return this.fxCalendar;
    }

    public int getSelectedDate() {
        return selectedDate.get();
    }

    public int getSelectedMonth() {
        return selectedMonth.get();
    }

    public int getSelectedYear() {
        return selectedYear.get();
    }

    public void setSelectedDate(int selectedDate) {
        this.selectedDate.set(selectedDate);
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth.set(selectedMonth);
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear.set(selectedYear);
    }

    public SimpleIntegerProperty selectedDateProperty() {
        return selectedDate;
    }

    public SimpleIntegerProperty selectedMonthProperty() {
        return selectedMonth;
    }

    public SimpleIntegerProperty selectedYearProperty() {
        return selectedYear;
    }

    /* GETTER'S FROM DATEPICKER * */
    public void incrementMonth() {
        int currentMonth = selectedMonth.get();
        if (currentMonth >= (fxCalendar.getFXCalendarUtility().getMonths(this.getLocale()).length - 2)) {
            this.selectedMonth.set(0);
            increaseYear();
        } else {
            increaseMonth(currentMonth);
        }
    }

    public void decrementMonth() {
        int currentMonth = selectedMonth.get();
        if (currentMonth <= 0) {
            this.selectedMonth.set(fxCalendar.getFXCalendarUtility().getMonths(this.getLocale()).length - 2);
            decreaseYear();
        } else {
            decreaseMonth(currentMonth);
        }
    }

    private void increaseMonth(int currentMonth) {
        selectedMonth.set(currentMonth + 1);
    }

    private void decreaseMonth(int currentMonth) {
        selectedMonth.set(currentMonth - 1);
    }

    private void increaseYear() {
        selectedYear.set(selectedYear.get() + 1);
    }

    private void decreaseYear() {
        selectedYear.set(selectedYear.get() - 1);
    }
}