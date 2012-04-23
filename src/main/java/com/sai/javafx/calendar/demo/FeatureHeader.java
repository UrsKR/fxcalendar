package com.sai.javafx.calendar.demo;

import javafx.scene.control.Label;

public class FeatureHeader extends Label {
	public FeatureHeader(String str){
		super(str);
		setStyle("-fx-font-weight :bold;-fx-font-size: 16px;-fx-font-family: verdana,arial,helvetica,tahoma,sans-serif;");
	}
}
