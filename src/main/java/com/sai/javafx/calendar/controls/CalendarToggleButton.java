package com.sai.javafx.calendar.controls;

import com.sai.javafx.calendar.FXCalendarUtility;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * CalendarToggleButton
 * @author Sai.Dandem
 *
 */
public class CalendarToggleButton extends StackPane {
    private Text txt;
    private StackPane sp;
    public CalendarToggleButton(String text, Object userData){

        setUserData(userData);
        setPrefHeight(18);
        setPrefWidth(44);

        sp =new StackPane();
        sp.getStyleClass().add("fx-calendar-toggleButton");
        sp.setPrefHeight(16);
        sp.setPrefWidth(44);

        txt = new Text(text);
        txt.getStyleClass().add("fx-calendar-toggleButton-txt");
        sp.getChildren().add(txt);

        getChildren().add(sp);
    }

    public void setBaseColor(Color color){
        FXCalendarUtility.setBaseColorToNode(sp, color);
        FXCalendarUtility.setBaseColorToNode(txt, color);
    }

    public void setText(String text){
        txt.setText(text);
    }

    public void setData(Object obj){
        setUserData(obj);
    }

}
