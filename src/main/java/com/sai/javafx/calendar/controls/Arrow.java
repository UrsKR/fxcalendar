package com.sai.javafx.calendar.controls;

import com.sai.javafx.calendar.FXCalendarUtility;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Arrow Control
 *
 * @author Sai.Dandem
 */
public class Arrow extends StackPane {
    private SimpleObjectProperty<Color> fillColor = new SimpleObjectProperty<Color>();

    public Arrow() {
        this(Side.BOTTOM);
    }

    public Arrow(Side side) {
        getStyleClass().add("fx-calendar-arrow");
        setFillColor(Color.WHITE);
        setScaleX(1.2);
        setScaleY(1.2);
        switch (side) {
            case LEFT:
                setRotate(90);
                break;
            case TOP:
                setRotate(180);
                break;
            case RIGHT:
                setRotate(270);
                break;
            default:
                setRotate(0);
        }
    }

    public SimpleObjectProperty<Color> fillColorProperty() {
        return fillColor;
    }

    public Color getFillColor() {
        return fillColor.get();
    }

    public void setFillColor(Color fillColor) {
        this.fillColor.set(fillColor);
        setStyle("-fx-background-color: " + FXCalendarUtility.rgbToHex(fillColor) + ";");
    }
}