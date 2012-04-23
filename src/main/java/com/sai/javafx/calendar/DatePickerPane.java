package com.sai.javafx.calendar;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.StackPane;

public class DatePickerPane extends StackPane {

    private final Rectangle2D calendarBounds = new Rectangle2D(100, 100, 205, 196);
    private BasePane basePane;
    private TopPane topPane;

    public DatePickerPane(DatePicker datePicker, CalendarProperties properties) {
        setPrefHeight(calendarBounds.getHeight());
        setPrefWidth(calendarBounds.getWidth());
        setAlignment(Pos.TOP_LEFT);
        FXCalendarUtility.setBaseColorToNode(this, properties.getBaseColor());
        this.basePane = new BasePane(datePicker, this, properties);
        this.topPane = new TopPane(datePicker, this, properties);
        getChildren().addAll(basePane, topPane);
        showBasePane();
    }

    public BasePane getBasePane() {
        return basePane;
    }

    public TopPane getTopPane() {
        return topPane;
    }

    public void showBasePane() {
        basePane.setVisible(true);
        topPane.setVisible(false);
    }

    public void showTopPane() {
        topPane.resetYearButtons();
        basePane.setVisible(false);
        topPane.setVisible(true);
    }

    public Rectangle2D getBounds() {
        return calendarBounds;
    }
}