package com.sai.javafx.calendar;

import javafx.scene.paint.Color;

import java.util.Locale;

public class CalendarProperties {

    private final FXCalendar calendar;

    public CalendarProperties(FXCalendar calendar) {
        this.calendar = calendar;
        calendar.setLocale(Locale.ENGLISH);
    }

    public Color getBaseColor() {
        return calendar.getBaseColor();
    }

    public FXCalendarUtility getFXCalendarUtility() {
        return calendar.getFXCalendarUtility();
    }

    public Locale getLocale() {
        return calendar.getLocale();
    }

    public boolean getShowWeekNumber() {
        return calendar.getShowWeekNumber();
    }
}