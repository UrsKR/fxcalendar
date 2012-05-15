package com.sai.javafx.calendar;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.util.Locale;

public class CalendarProperties {

    private final SimpleObjectProperty<Color> baseColor = new SimpleObjectProperty<>(Color.web("#313131"));
    private final SimpleObjectProperty<Locale> locale = new SimpleObjectProperty<>(Locale.getDefault());
    private boolean showWeekNumber = false;

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

    public Color getBaseColor() {
        return baseColor.get();
    }

    public void setBaseColor(Color color) {
        this.baseColor.set(color);
    }

    public SimpleObjectProperty<Color> baseColorProperty() {
        return baseColor;
    }
}