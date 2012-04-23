package com.sai.javafx.calendar;

import com.sai.javafx.calendar.cell.DateCell;
import com.sai.javafx.calendar.cell.WeekCell;
import com.sai.javafx.calendar.controls.Arrow;
import com.sai.javafx.calendar.controls.BaseNavigatorArrowButton;
import com.sai.javafx.calendar.controls.NormalButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BasePane extends Group {
    private DatePicker datePicker;
    private final DatePickerPane datePickerPane;
    private final CalendarProperties properties;
    private StackPane navigatorPane;
    private StackPane weekPane;
    private StackPane deskPane;
    private StackPane footerPane;
    private Label displayLabel;
    private ObservableList<WeekCell> weekCellList = FXCollections.observableArrayList();
    private ObservableList<DateCell> dateCellList = FXCollections.observableArrayList();
    public static final String WEEKNUMER_LABEL = "Wk.";
    private BaseNavigatorArrowButton prevMonthBtn;

    public BasePane(DatePicker datePicker, DatePickerPane datePickerPane, CalendarProperties properties) {
        this.datePicker = datePicker;
        this.datePickerPane = datePickerPane;
        this.properties = properties;
        configureNavigator();
        configureWeekHeader();
        configureDesk();
        configureFooter();
    }

    /*
      * *********************************************************************************************************************
      * ****************************** MONTH NAVIGATOR
      * ******************************
      * *******************************************
      * *******************************
      * *******************************************
      */
    private void configureNavigator() {
        navigatorPane = new StackPane();
        /*
           * Changes to be done in BasePaneNavigator on change of selectedMonth
           * and selectedYear in DatePicker.
           */
        ChangeListener<Object> listener = new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
                setLabelText();
            }
        };

        datePicker.selectedMonthProperty().addListener(listener);
        datePicker.selectedYearProperty().addListener(listener);

        FXCalendarUtility.setBaseColorToNode(navigatorPane, properties.getBaseColor());
        navigatorPane.setPrefWidth(datePickerPane.getBounds().getWidth());
        navigatorPane.setPrefHeight(26);
        navigatorPane.getStyleClass().add("fx-calendar-navigator");

        /* Displaying the Month & Year of the selected date. */
        displayLabel = new Label();
        displayLabel.getStyleClass().add("fx-calendar-navigator-label");
        displayLabel.setGraphic(new Arrow());
        setLabelText();
        displayLabel.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                datePickerPane.showTopPane();
            }
        });

        /* Calculating the distance for the arrow buttons from the center. */
        double pos = (datePickerPane.getBounds().getWidth() / 2) - 12;

        /* Getting the Next Month Button. */
        BaseNavigatorArrowButton nextMonthBtn = new BaseNavigatorArrowButton(Side.RIGHT, properties.getBaseColor());
        nextMonthBtn.setTranslateX(pos);
        nextMonthBtn.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                datePicker.incrementMonth();
            }
        });

        /* Getting the Previous Month Button. */
        prevMonthBtn = new BaseNavigatorArrowButton(Side.LEFT, properties.getBaseColor());
        prevMonthBtn.setTranslateX(-pos);
        prevMonthBtn.setOnMouseClicked(new EventHandler<Event>() {
            @Override
            public void handle(Event arg0) {
                if (!(datePicker.getSelectedMonth() == 0 && datePicker.getSelectedYear() == 1)) {
                    datePicker.decrementMonth();
                }
            }
        });

        navigatorPane.getChildren().addAll(displayLabel, nextMonthBtn, prevMonthBtn);
        getChildren().add(navigatorPane);
    }

    public void setLabelText() {
        displayLabel.setText(properties.getFXCalendarUtility().getMonths(properties.getLocale())[this.datePicker.getSelectedMonth()] + " "
                + this.datePicker.getSelectedYear());
    }

    /*
      * *********************************************************************************************************************
      * ****************************** WEEK HEADER ******************************
      * *
      * *************************************************************************
      * *******************************************
      */
    private void configureWeekHeader() {
        weekPane = new StackPane();

        FXCalendarUtility.setBaseColorToNode(weekPane, properties.getBaseColor());
        weekPane.setPrefWidth(datePickerPane.getBounds().getWidth());
        weekPane.setPrefHeight(18);
        weekPane.getStyleClass().add("fx-calendar-weekpane");

        int count = properties.getShowWeekNumber() ? 8 : 7;

        TilePane tp = new TilePane();
        tp.setPrefColumns(count);

        generateWeekCells(count);
        for (WeekCell weekCell : weekCellList) {
            tp.getChildren().add(weekCell);
        }

        weekPane.getChildren().add(tp);
        weekPane.setTranslateY(navigatorPane.getPrefHeight());
        getChildren().add(weekPane);
    }

    private void generateWeekCells(int count) {
        Rectangle2D cellBounds = calculateBounds();
        WeekCell cell;
        List<WeekCell> wkCells = new ArrayList<WeekCell>(count);
        if (properties.getShowWeekNumber()) {
            cell = new WeekCell("week_num", WEEKNUMER_LABEL, cellBounds.getWidth(), cellBounds.getHeight());
            FXCalendarUtility.setBaseColorToNode(cell.getTxt(), Color.BLUE);
            wkCells.add(cell);
        }

        String[] wks = properties.getFXCalendarUtility().getShortestWeekDays(properties.getLocale());
        for (int i = 1; i < wks.length; i++) {
            cell = new WeekCell("week_" + wks[i], wks[i], cellBounds.getWidth(), cellBounds.getHeight());
            FXCalendarUtility.setBaseColorToNode(cell.getTxt(), properties.getBaseColor());
            wkCells.add(cell);
        }
        weekCellList.addAll(wkCells);
    }

    public void setWeekLabels() {
        String[] wks = properties.getFXCalendarUtility().getShortestWeekDays(properties.getLocale());
        int pos = properties.getShowWeekNumber() ? 1 : 0;
        for (int i = 1; i < wks.length; i++) {
            weekCellList.get(pos).setContent(wks[i]);
            pos++;
        }
    }

    private Rectangle2D calculateBounds() {
        int divFactor = getColCount();
        double width = datePickerPane.getBounds().getWidth() / divFactor;
        double height = 18;
        return new Rectangle2D(0, 0, width, height);
    }

    /*
      * *********************************************************************************************************************
      * ****************************** DATE DESK ******************************
      * **
      * ************************************************************************
      * *******************************************
      */

    private void configureDesk() {
        deskPane = new StackPane();
        FXCalendarUtility.setBaseColorToNode(deskPane, properties.getBaseColor());
        deskPane.setPrefWidth(datePickerPane.getBounds().getWidth());
        deskPane.setPrefHeight(120);
        deskPane.getStyleClass().add("fx-calendar-desk");

        TilePane tp = new TilePane();
        tp.setPrefColumns(getColCount());

        generateDateCells();

        for (DateCell dateCell : dateCellList) {
            tp.getChildren().add(dateCell);
        }

        generateDates();

        /*
           * Changes to be done in BasePaneDesk on change of selectedMonth and
           * selectedYear in DatePicker.
           */
        ChangeListener<Object> listener = new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
                generateDates();
            }
        };

        datePicker.selectedDateProperty().addListener(listener);
        datePicker.selectedMonthProperty().addListener(listener);
        datePicker.selectedYearProperty().addListener(listener);

        deskPane.getChildren().add(tp);
        deskPane.setTranslateY(navigatorPane.getPrefHeight() + weekPane.getPrefHeight());
        getChildren().add(deskPane);

    }

    private int getColCount() {
        return properties.getShowWeekNumber() ? 8 : 7;
    }

    private void generateDateCells() {
        int count = getColCount();
        Rectangle2D cellBounds = calculateDeskBounds();
        DateCell dateCell;
        List<DateCell> dateCells = new ArrayList<DateCell>(count * 6);

        for (int i = 0; i < (count * 6); i++) {
            dateCell = new DateCell("cell" + i, cellBounds.getWidth(), cellBounds.getHeight());
            FXCalendarUtility.setBaseColorToNode(dateCell, properties.getBaseColor());
            // For Week Number cells
            if (properties.getShowWeekNumber() && i % 8 == 0) {
                FXCalendarUtility.setBaseColorToNode(dateCell.getTxt(), Color.BLUE);
                dateCell.setWeekNumCell(true);
                dateCell.getTxt().getStyleClass().add("fx-calendar-weektext");
            }
            // For actual Date cells
            else {
                // TODO : Anything to configure on date cell.
            }

            dateCells.add(dateCell);
        }
        dateCellList.addAll(dateCells);
    }

    public void generateDates() {

        Calendar firstDayOfMonth = FXCalendarUtility.getDate(1, datePicker.getSelectedMonth(), datePicker.getSelectedYear());
        Calendar paneFirstDate = (Calendar) firstDayOfMonth.clone();

        // If Monday is first day of week.
        if (Calendar.getInstance(properties.getLocale()).getFirstDayOfWeek() == 2) {
            int diff = 0;
            if (firstDayOfMonth.get(Calendar.DAY_OF_WEEK) == 1) {
                diff = 6;
            } else {
                diff = firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 2;
            }
            paneFirstDate.add(Calendar.DAY_OF_YEAR, -diff);
        } else {
            // If Sunday is first day of week.
            paneFirstDate.add(Calendar.DAY_OF_YEAR, -(firstDayOfMonth.get(Calendar.DAY_OF_WEEK) - 1));
        }

        Calendar dummyDate = (Calendar) paneFirstDate.clone();
        Calendar systemDate = FXCalendarUtility.getCalendarSetToToday();

        int fxDate = datePicker.getSelectedDate();
        int fxMonth = datePicker.getSelectedMonth();
        int fxYear = datePicker.getSelectedYear();

        for (final DateCell dateCell : dateCellList) {
            if (!dateCell.isWeekNumCell()) {
                dateCell.getStyleClass().remove("fx-calendar-basic-datecell-selected");
                dateCell.getTxt().setText(dummyDate.get(Calendar.DAY_OF_MONTH) + "");

                // Setting the date details of the cell.
                dateCell.setCellDate(dummyDate.get(Calendar.DAY_OF_MONTH));
                dateCell.setCellMonth(dummyDate.get(Calendar.MONTH));
                dateCell.setCellYear(dummyDate.get(Calendar.YEAR));

                // Highlighting the current month cells.
                if (dummyDate.get(Calendar.MONTH) == datePicker.getSelectedMonth()) {
                    dateCell.getTxt().setDisable(false);
                } else {
                    dateCell.getTxt().setDisable(true);
                    // Not showing the dates below 01/01/01
                    if ((datePicker.getSelectedMonth() == 0 && datePicker.getSelectedYear() == 1) && dateCell.getCellMonth() != 1) {
                        dateCell.setCellYear(0);
                    }
                }

                // Highlighting the current system date.
                if (systemDate.get(Calendar.DAY_OF_MONTH) == dummyDate.get(Calendar.DAY_OF_MONTH) && systemDate.get(Calendar.MONTH) == dummyDate.get(Calendar.MONTH)
                        && systemDate.get(Calendar.YEAR) == dummyDate.get(Calendar.YEAR)) {
                    dateCell.setCellFocused(true);
                } else {
                    dateCell.setCellFocused(false);
                }

                // Highlighting the Selected date.
                if (fxDate == dummyDate.get(Calendar.DAY_OF_MONTH) && fxMonth == dummyDate.get(Calendar.MONTH) && fxYear == dummyDate.get(Calendar.YEAR)) {
                    // Overriding the dotted line with selected class.
                    if (dateCell.getCellFocused()) {
                        dateCell.setCellFocused(false);
                    }
                    dateCell.getStyleClass().add("fx-calendar-basic-datecell-selected");
                }

                // Setting the event handler for the selected date.
                dateCell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        int year = dateCell.getCellYear();
                        int month = dateCell.getCellMonth();
                        int date = dateCell.getCellDate();
                        Calendar calendar = FXCalendarUtility.getDate(date, month, year);
                        datePicker.select(calendar.getTime());
                    }
                });

                // Incrementing the date.
                dummyDate.add(Calendar.DAY_OF_YEAR, 1);
            } else {
                // Updating the week number
                if (dummyDate.get(Calendar.DAY_OF_WEEK) == 1) {
                    dateCell.getTxt().setText((dummyDate.get(Calendar.WEEK_OF_YEAR) - 1) + "");
                    dateCell.getTxt().getStyleClass().add("fx-calendar-weektext");
                }
            }
        }
    }

    private Rectangle2D calculateDeskBounds() {
        int divFactor = getColCount();
        double width = datePickerPane.getBounds().getWidth() / divFactor;
        double height = 120 / 6;
        return new Rectangle2D(0, 0, width, height);
    }

    /*
      * *********************************************************************************************************************
      * ****************************** FOOTER ******************************
      * *****
      * *********************************************************************
      * *******************************************
      */
    private void configureFooter() {
        footerPane = new StackPane();
        FXCalendarUtility.setBaseColorToNode(footerPane, properties.getBaseColor());
        footerPane.setPrefWidth(datePickerPane.getBounds().getWidth());
        footerPane.setPrefHeight(32);
        footerPane.getStyleClass().add("fx-calendar-footer");
        NormalButton todayButton = new NormalButton("Today");
        todayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Calendar today = FXCalendarUtility.getCalendarSetToToday();
                datePicker.select(today.getTime());
            }
        });
        footerPane.getChildren().add(todayButton);
        footerPane.setTranslateY(navigatorPane.getPrefHeight() + weekPane.getPrefHeight() + deskPane.getPrefHeight());
        getChildren().add(footerPane);
    }
}