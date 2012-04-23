package com.sai.javafx.calendar;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.util.Locale;

public class CalendarProperties {

    private final SimpleObjectProperty<Color> baseColor = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Locale> locale = new SimpleObjectProperty<>();
    private boolean showWeekNumber = false;
    private final FXCalendar calendar;

    public CalendarProperties(FXCalendar calendar) {
        this.calendar = calendar;
        setLocale(Locale.ENGLISH);
    }

    public Color getBaseColor() {
        return calendar.getBaseColor();
    }

    public Locale getLocale() {
        return locale.get();
    }

    public void setLocale(Locale locale) {
        this.locale.set(locale);
    }

    public SimpleObjectProperty<Locale> localeProperty() {
        return locale;
    }

    public boolean getShowWeekNumber() {
        return showWeekNumber;
    }

    public void setShowWeekNumber(boolean showWeekNumber) {
        this.showWeekNumber = showWeekNumber;
    }

}