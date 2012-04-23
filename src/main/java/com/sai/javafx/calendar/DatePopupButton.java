package com.sai.javafx.calendar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;


public class DatePopupButton {
    private final Button popupButton = new Button();

    public DatePopupButton(final DatePickerPopup popup, final DateSelection selection) {
        popupButton.getStyleClass().add("dateButton");
        popupButton.setGraphic(FXCalendarUtility.getDateImage());
        popupButton.setFocusTraversable(false);
        popupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent paramT) {
                if (popup.isVisible()) {
                    popup.hide();
                } else {
                    popup.setInitialDate(selection.getSelection());
                    popup.show();
                }
            }
        });
    }

    public Node getComponent() {
        return popupButton;
    }
}