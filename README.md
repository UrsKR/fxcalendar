This is a fork of http://code.google.com/p/javafx-calendar/.

As the original, it is licensed under the Apache License v2.0.

Initial work was sponsored by **[IDOS AE GmbH](http://www.idos.de)**.
When setting out, my goal was to make the code more flexible, so one could use only parts of it and use it with different layout managers.

## To Build
* You need JavaFX 2.1 and JDK 7
* Define a property ``${path-to-javafx}`` in your Maven ``settings.xml``, pointing to the ``rt/lib`` folder of your local JavaFX installation.
* Define a property ``${jdk}`` in you Maven ``settings.xml``, pointing to your JDK installation folder.

## To See
* Run ``com.sai.javafx.calendar.demo.FXCalendarDemo``