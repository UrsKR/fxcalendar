package com.sai.javafx.calendar.controls;

import com.sai.javafx.calendar.FXCalendarUtility;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * YearNavigatorArrowButton
 * @author Sai.Dandem
 *
 */
public class YearNavigatorArrowButton extends Group {

    public YearNavigatorArrowButton(Side side, Color baseColor){
        StackPane sp = new StackPane();
        FXCalendarUtility.setBaseColorToNode(this, baseColor);
        sp.setPrefHeight(16);
        sp.setPrefWidth(16);

        Rectangle rect = new Rectangle(15,15);
        FXCalendarUtility.setBaseColorToNode(rect, baseColor);
        rect.getStyleClass().add("fx-calendar-year-navigator-btn");

        Arrow arrow;
        Group gp = new Group();
        switch(side){
        case LEFT:
            arrow = new Arrow(Side.LEFT);
            gp.setTranslateX(-1);
            break;
        default:
            arrow = new Arrow(Side.RIGHT);
            gp.setTranslateX(1);
        }
        arrow.setFillColor(baseColor);
        gp.getChildren().add(arrow);
        sp.getChildren().addAll(rect,gp);
        getChildren().addAll(sp);
        getStyleClass().add("fx-calendar-year-navigator-btnGrp");

    }
}
