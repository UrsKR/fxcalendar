package com.sai.javafx.calendar;

import java.util.Date;

public class TodayDefaultSelection implements DateSelection {

    private final DateSelection selection;

    public TodayDefaultSelection(DateSelection selection) {
        this.selection = selection;
    }

    @Override
    public void select(Date time) {
        selection.select(time);
    }

    @Override
    public Date getSelection() {
        Date currentDate = selection.getSelection();
        if (currentDate == null) {
            return new Date();
        }
        return currentDate;
    }
}