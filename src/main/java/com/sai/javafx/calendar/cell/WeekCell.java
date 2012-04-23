package com.sai.javafx.calendar.cell;

import javafx.scene.text.Text;

/**
 * WeekCell
 *
 * @author Sai.Dandem
 */
public class WeekCell extends AbstractCell {


    public WeekCell(String id, String content, double width, double height) {
        super();
        setCellId(id);
        setCellWidth(width - 1);
        setCellHeight(height);

        super.txt = new Text(content);
        txt.getStyleClass().add("fx-calendar-weektext");
        getChildren().add(txt);
    }


    public void setContent(String str) {
        super.txt.setText(str);
    }

    @Override
    public void setCellId(String id) {
        super.setId(id);
    }

    @Override
    public void setCellWidth(double width) {
        super.setPrefWidth(width);
    }

    @Override
    public void setCellHeight(double height) {
        super.setPrefHeight(height);
    }

    public void setCellStyle(String styleClass) {
        getStyleClass().add(styleClass);
    }

}
