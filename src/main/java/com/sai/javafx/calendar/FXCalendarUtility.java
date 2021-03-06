package com.sai.javafx.calendar;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

public class FXCalendarUtility {

    private static final Logger LOG = Logger.getLogger(FXCalendarUtility.class.getCanonicalName());

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");
    private static final SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");


    public static Calendar getCalendarSetToToday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c;
    }

    public static Calendar getDateCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static Calendar getDate(Integer day, Integer month, Integer year) {
        try {
            String str_date = day + "/" + (month + 1) + "/" + year;
            Date date = DATE_FORMAT.parse(str_date);
            Calendar c = getCalendar();
            c.setTime(date);
            return c;
        } catch (ParseException e) {
            LOG.fine(e.getMessage());
        }
        return null;
    }

    public static Date convertStringtoDate(String str) {
        try {
            return DISPLAY_DATE_FORMAT.parse(str);
        } catch (ParseException e) {
            LOG.severe(e.getMessage());
            return null;
        }
    }

    public static String getFormattedDate(Integer day, Integer month, Integer year) {
        try {
            String str_date = day + "/" + (month + 1) + "/" + year;
            Date date = DATE_FORMAT.parse(str_date);
            return DISPLAY_DATE_FORMAT.format(date);
        } catch (ParseException e) {
            LOG.fine(e.getMessage());
        }
        return null;
    }

    public static String[] getShortestWeekDays(Locale locale) {
        String[] SHORTEST_WEEK_DAYS = getDayNames("xs", locale);
        // If Monday is first day of week.
        if (Calendar.getInstance(locale).getFirstDayOfWeek() == 2) {
            String dum = SHORTEST_WEEK_DAYS[1];
            for (int i = 1; i < 7; i++) {
                SHORTEST_WEEK_DAYS[i] = SHORTEST_WEEK_DAYS[i + 1];
            }
            SHORTEST_WEEK_DAYS[7] = dum;
        }
        return SHORTEST_WEEK_DAYS;
    }

    public static String[] getShortMonths(Locale locale) {
        return getMonthNames("s", locale);
    }


    public static String[] getMonths(Locale locale) {
        return getMonthNames(null, locale);
    }

    private static String[] getDayNames(String type, Locale locale) {
        if (type != null && type.equalsIgnoreCase("xs")) {
            String[] days = new DateFormatSymbols(locale).getShortWeekdays();
            String[] xsDays = new String[days.length];
            for (int i = 0; i < days.length; i++) {
                xsDays[i] = (days[i].equals("")) ? days[i] : days[i].charAt(0) + "";
            }
            return xsDays;
        }
        if (type != null && type.equalsIgnoreCase("s")) {
            return new DateFormatSymbols(locale).getShortWeekdays();
        } else {
            return new DateFormatSymbols(locale).getWeekdays();
        }
    }

    private static String[] getMonthNames(String type, Locale locale) {
        if (type != null && type.equalsIgnoreCase("s")) {
            return new DateFormatSymbols(locale).getShortMonths();
        } else {
            return new DateFormatSymbols(locale).getMonths();
        }
    }

    public static void setBaseColorToNode(Node node, Color baseColor) {
        node.setStyle("-fx-base:" + rgbToHex(baseColor) + ";");
    }


    public static String rgbToHex(Color color) {
        int i = (int) Math.round(color.getRed() * 255D);
        int j = (int) Math.round(color.getGreen() * 255D);
        int k = (int) Math.round(color.getBlue() * 255D);
        return "#" + toHex(i) + toHex(j) + toHex(k);
    }

    private static String toHex(int code) {
        String str = "0123456789ABCDEF";
        return str.charAt(code / 16) + "" + str.charAt(code % 16);
    }

    public static Group getDateImage() {
        Group gp = new Group();
        StackPane img = new StackPane();
        double imgSize = 15.0;
        double imgSizeQuar = imgSize / 4;
        img.setPrefSize(imgSize, imgSize);
        img.getStyleClass().add("calendar-image");
        img.setAlignment(Pos.TOP_LEFT);

        /* Vertical Lines */
        Line l = getLine(0, 0, 0, imgSize, imgSizeQuar, 0);
        Line l1 = getLine(0, 0, 0, imgSize, imgSizeQuar * 2, 0);
        Line l2 = getLine(0, 0, 0, imgSize, imgSizeQuar * 3, 0);
        /* Horizontal Lines */
        Line l3 = getLine(0, 0, imgSize, 0, 0, imgSizeQuar);
        Line l4 = getLine(0, 0, imgSize, 0, 0, imgSizeQuar * 2);
        Line l5 = getLine(0, 0, imgSize, 0, 0, imgSizeQuar * 3);
        /* Circle */
        Circle c = new Circle();
        c.getStyleClass().add("calendar-image-circle");
        c.setRadius(imgSizeQuar / 2);
        c.setTranslateX(imgSizeQuar * 3);
        c.setTranslateY(imgSizeQuar);
        img.getChildren().addAll(l, l1, l2, l3, l4, l5, c);

        gp.getChildren().add(img);
        gp.setTranslateX(5);
        gp.setTranslateY(1);
        return gp;
    }

    private static Line getLine(double startX, double startY, double endX, double endY, double translateX, double translateY) {
        Line l = new Line();
        l.getStyleClass().add("calendar-image-line");
        l.setStartX(startX);
        l.setStartY(startY);
        l.setEndX(endX);
        l.setEndY(endY);
        l.setSmooth(true);
        l.setTranslateX(translateX);
        l.setTranslateY(translateY);
        return l;
    }

    private static Calendar getCalendar() {
        return Calendar.getInstance();
    }
}