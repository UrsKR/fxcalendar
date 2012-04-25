This is a fork of http://code.google.com/p/javafx-calendar/.

As the original, it is licensed under the Apache License v2.0.

Initial work was sponsored by **[IDOS AE GmbH](http://www.idos.de)**.  
When setting out, my goal was to make the code more flexible. I wanted to use it with a German locale, to influence the layout and to use it with [JodaTime]
(http://joda-time.sourceforge.net/).

## To Build
* You need JavaFX 2.1 and JDK 7
* Define a property ``${path-to-javafx}`` in your Maven ``settings.xml``, pointing to the ``rt/lib`` folder of your local JavaFX installation.
* Define a property ``${jdk}`` in you Maven ``settings.xml``, pointing to your JDK installation folder.

## To See
* Run ``FXCalendarDemo``

![Main view](https://github.com/UrsKR/fxcalendar/raw/master/screenshots/calendarmain.PNG)
![Quick navigation](https://github.com/UrsKR/fxcalendar/raw/master/screenshots/calendaryearchooser.PNG)

## To Use
* For a one stop solution, instantiate a ``FXCalendar`` and add it to your pane.
* For a clickable calendar image, use the ``DatePopupButton`` and add its component to your pane.
* If you just want the calendar popup, create your own button, and make it trigger a ``DatePickerPopup``

## Example

```
    CalendarProperties properties = new CalendarProperties();
    DateSelection selection = ...; //This is where the selected date ends up
    DatePickerPopup popup = new DatePickerPopup(representation, properties, selection);
    DatePopupButton popUpButton = new DatePopupButton(popup);
```
