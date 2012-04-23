package com.sai.javafx.calendar;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Popup;

import java.util.Date;

public class DatePickerPopup {

    private final Popup popup = new Popup();
    private final Node parentNode;
    private final CalendarProperties properties;
    private final DateSelection dateSelection;
    private DatePickerPane datePickerPane;
    private DatePicker datePicker;
    private Date initialDate;

    public DatePickerPopup(Node parentNode, CalendarProperties properties, DateSelection dateSelection) {
        this.parentNode = parentNode;
        this.properties = properties;
        this.dateSelection = dateSelection;
        popup.setAutoHide(true);
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);
    }

    public void initiateAndShow() {
        initiatePopUp();
        showPopup();
    }

    public void setInitialDate(Date date) {
        this.initialDate = date;
    }

    public void hidePopup() {
        popup.hide();
    }

    private void initiatePopUp() {
        if (datePicker == null) {
            datePicker = new DatePicker(initialDate, properties, dateSelection);
            datePickerPane = new DatePickerPane(datePicker, properties);
            popup.getContent().add(datePickerPane);
        }
        datePickerPane.getBasePane().generateDates();
        datePickerPane.showBasePane();
    }

    private void showPopup() {
        Parent parent = parentNode.getParent();
        Bounds childBounds = parentNode.getBoundsInParent();
        Bounds parentBounds = parent.localToScene(parent.getBoundsInLocal());
        double layoutX = childBounds.getMinX() + parentBounds.getMinX() + parent.getScene().getX() + parent.getScene().getWindow().getX();
        double layoutY = childBounds.getMaxY() + parentBounds.getMinY() + parent.getScene().getY() + parent.getScene().getWindow().getY();
        popup.show(parentNode, layoutX, layoutY);
    }

    public void refreshDisplayText() {
        if (datePickerPane != null) {
            datePickerPane.getBasePane().setLabelText();
            datePickerPane.getBasePane().setWeekLabels();
            datePickerPane.getTopPane().setTopMonths();
        }
    }
}