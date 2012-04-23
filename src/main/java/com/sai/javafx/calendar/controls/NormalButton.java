package com.sai.javafx.calendar.controls;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.scene.control.Button;

/**
 * NormalButton
 * @author Sai.Dandem
 *
 */
public class NormalButton extends Button {
    public NormalButton(String txt){
        super(txt);
        //getStyleClass().add("calendarButton");
        super.setSkin(new ButtonSkin(this));
    }
}
