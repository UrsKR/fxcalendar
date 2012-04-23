package com.sai.javafx.calendar.cell;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * AbstractCell
 * @author Sai.Dandem
 *
 */
public abstract class AbstractCell extends StackPane {

    protected Text txt;
    public AbstractCell(){
        super();
    }
    public abstract void setCellId(String id);
    public abstract void setCellWidth(double width);
    public abstract void setCellHeight(double height);
    public abstract void setCellStyle(String styleClass);


    /**
     * @return the txt
     */
    public Text getTxt() {
        return txt;
    }

    /**
     * @param txt the txt to set
     */
    public void setTxt(Text txt) {
        this.txt = txt;
    }

}
