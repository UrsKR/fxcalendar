package com.sai.javafx.calendar.demo;

import javafx.scene.control.Label;

public class FeatureLabel extends Label {
	public FeatureLabel(String str){
		super(str);
		setStyle("-fx-font-size: 12px;-fx-font-family: verdana,arial,helvetica,tahoma,sans-serif;");
	}
}
