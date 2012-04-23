package com.sai.javafx.calendar;

import javafx.scene.control.TextField;

/**
 * Simple Cell Class
 *
 * @author Sai.Dandem
 */
public class DateTextField extends TextField {
    public DateTextField() {
        setEditable(true);
        setPrefHeight(22);
        setPromptText("Select Date");
    }
}