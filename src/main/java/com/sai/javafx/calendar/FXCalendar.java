package com.sai.javafx.calendar;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FXCalendar extends HBox implements DateSelection {

    private SimpleIntegerProperty selectedDate = new SimpleIntegerProperty();
    private SimpleIntegerProperty selectedMonth = new SimpleIntegerProperty();
    private SimpleIntegerProperty selectedYear = new SimpleIntegerProperty();
    private final SimpleObjectProperty<Color> baseColor = new SimpleObjectProperty<>();
    private SimpleDoubleProperty dateTextWidth = new SimpleDoubleProperty(74);
    private SimpleObjectProperty<Date> value = new SimpleObjectProperty<>();
    private DateTextField dateTxtField;
    private Popup popup;
    private DatePicker datePicker;
    private final String DEFAULT_STYLE_CLASS = "fx-calendar";
    private DatePickerPane datePickerPane;
    private CalendarProperties properties;

    public FXCalendar() {
        properties = new CalendarProperties(FXCalendar.this);
        super.getStyleClass().add(DEFAULT_STYLE_CLASS);
        this.baseColor.set(Color.web("#313131"));
        setAlignment(Pos.CENTER);
        configureCalendar();
        configureListeners();
    }

    private void configureCalendar() {
        final DateFormatValidator dateFormatValidator = new DateFormatValidator();
        popup = new Popup();
        popup.setAutoHide(true);
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);

        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (KeyCode.UP.equals(event.getCode()) || KeyCode.DOWN.equals(event.getCode()) || KeyCode.ENTER.equals(event.getCode())) {
                    initiatePopUp();
                    showPopup();
                } else if (KeyCode.TAB.equals(event.getCode())) {
                    hidePopup();
                }
            }
        });
        createDateTextField(dateFormatValidator);
        Button popupButton = createPopUpButton();
        getChildren().addAll(dateTxtField, popupButton);
    }

    private Button createPopUpButton() {
        Button popupButton = new Button();
        popupButton.getStyleClass().add("dateButton");
        popupButton.setGraphic(FXCalendarUtility.getDateImage());
        popupButton.setFocusTraversable(false);
        popupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent paramT) {
                initiatePopUp();
                showPopup();
            }
        });
        return popupButton;
    }

    private void createDateTextField(final DateFormatValidator dateFormatValidator) {
        dateTxtField = new DateTextField();
        dateTxtField.prefWidthProperty().bind(dateTextWidth);
        ChangeListener<Boolean> focusOutListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                // Handling only when focus is out.
                if (!arg2) {
                    String value = dateTxtField.getText();
                    if (!dateFormatValidator.isValid(value)) {
                        clear(); // TODO : Error styling for invalid date format.
                        dateTxtField.setText(value);
                    } else {
                        Date date = FXCalendarUtility.convertStringtoDate(value);
                        if (date != null) {
                            setValue(date);
                        } else {
                            // TODO : Error styling the text field for invalid date
                            // entry.
                            clear();
                        }
                    }
                }
            }
        };
        dateTxtField.focusedProperty().addListener(focusOutListener);
    }

    private void configureListeners() {
        /*
           * Changes to be done in text box on change of seletedDate ,
           * selectedMonth and selectedYear in DatePicker.
           */
        ChangeListener<Object> listener = new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<? extends Object> arg0, Object arg1, Object arg2) {
                showDateInTextField();
            }
        };

        selectedDateProperty().addListener(listener);
        selectedMonthProperty().addListener(listener);
        selectedYearProperty().addListener(listener);
        showDateInTextField();

        properties.localeProperty().addListener(new ChangeListener<Locale>() {
            @Override
            public void changed(ObservableValue<? extends Locale> arg0, Locale arg1, Locale arg2) {
                if (datePicker != null) {
                    refreshLocale(arg2);
                }
            }
        });

        /* Adding listeners for styles. */
        getStyleClass().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> paramChange) {
                dateTxtField.getStyleClass().clear();
                dateTxtField.getStyleClass().addAll("text-input", "text-field");
                for (String clazz : getStyleClass()) {
                    if (!clazz.equals(DEFAULT_STYLE_CLASS)) {
                        dateTxtField.getStyleClass().add(clazz);
                    }
                }
            }
        });
    }

    public void showDateInTextField() {
        int date = selectedDateProperty().get();
        int month = selectedMonthProperty().get();
        int year = selectedYearProperty().get();
        if (date != 0 && month != -1 && year != 0) {
            dateTxtField.setText(FXCalendarUtility.getFormattedDate(date, month, year));
        } else {
            dateTxtField.setText("");
        }
    }

    public void refreshLocale(Locale locale) {
        datePickerPane.getBasePane().setLabelText();
        datePickerPane.getBasePane().setWeekLabels();
        datePickerPane.getTopPane().setTopMonths();
    }

    /**
     * Method to initiate the pop up before showing.
     */
    private void initiatePopUp() {
        if (datePicker == null) {
            Date intialDate = getInitialDateForPicker();
            datePicker = new DatePicker(intialDate, properties, this);
            datePickerPane = new DatePickerPane(datePicker, properties);
            popup.getContent().add(datePickerPane);
        }
        datePickerPane.getBasePane().generateDates();
        datePickerPane.showBasePane();
    }

    private Date getInitialDateForPicker() {
        Date currentDate = getValue();
        if (currentDate == null) {
            return new Date();
        }
        return currentDate;
    }

    /**
     * Method to show the pop up.
     */
    private void showPopup() {
        Parent parent = getParent();
        Bounds childBounds = getBoundsInParent();
        Bounds parentBounds = parent.localToScene(parent.getBoundsInLocal());
        double layoutX = childBounds.getMinX() + parentBounds.getMinX() + parent.getScene().getX() + parent.getScene().getWindow().getX();
        double layoutY = childBounds.getMaxY() + parentBounds.getMinY() + parent.getScene().getY() + parent.getScene().getWindow().getY();
        popup.show(this, layoutX, layoutY);
    }

    /**
     * Method to hide the pop up.
     */
    public void hidePopup() {
        popup.hide();
    }

    /**
     * @return the baseColor
     */
    public Color getBaseColor() {
        return baseColor.get();
    }

    public void setBaseColor(Color color) {
        this.baseColor.set(color);
    }

    /**
     * @return baseColor Property
     */
    public SimpleObjectProperty<Color> baseColorProperty() {
        return baseColor;
    }



    /**
     * @return the dateTextWidth
     */
    public Double getDateTextWidth() {
        return dateTextWidth.get();
    }

    public void setDateTextWidth(Double width) {
        this.dateTextWidth.set(width);
    }

    /**
     * @return dateTextWidth Property
     */
    public SimpleDoubleProperty dateTextWidthProperty() {
        return dateTextWidth;
    }

    public int getSelectedDate() {
        return selectedDate.get();
    }

    public int getSelectedMonth() {
        return selectedMonth.get();
    }

    public int getSelectedYear() {
        return selectedYear.get();
    }

    public void setSelectedDate(int selectedDate) {
        this.selectedDate.set(selectedDate);
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth.set(selectedMonth);
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear.set(selectedYear);
    }

    public SimpleIntegerProperty selectedDateProperty() {
        return selectedDate;
    }

    public SimpleIntegerProperty selectedMonthProperty() {
        return selectedMonth;
    }

    public SimpleIntegerProperty selectedYearProperty() {
        return selectedYear;
    }

    /**
     * @return the value
     */
    public Date getValue() {
        return this.value.get();
    }

    public void setValue(Date date) {
        this.value.set(date);
        if (date != null) {
            Calendar calendar = FXCalendarUtility.getDateCalendar(date);
            selectedDateProperty().set(calendar.get(Calendar.DAY_OF_MONTH));
            selectedMonthProperty().set(calendar.get(Calendar.MONTH));
            selectedYearProperty().set(calendar.get(Calendar.YEAR));
        } else {
            selectedDateProperty().set(0);
            selectedMonthProperty().set(0);
            selectedYearProperty().set(0);
        }
    }

    /**
     * Method to clear the value in the calendar.
     */
    public void clear() {
        setValue(null);
    }

    public SimpleObjectProperty<Date> valueProperty() {
        return value;
    }

    public TextField getTextField() {
        return dateTxtField;
    }

    @Override
    public void select(Date time) {
        setValue(time);
        getTextField().requestFocus();
        showDateInTextField();
        hidePopup();
    }

    public CalendarProperties getCalendarProperties() {
        return properties;
    }
}