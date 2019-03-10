package ru.otus.l12.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties extends Properties {
    private static AppProperties instance;

    private AppProperties() {
        super();
    }

    public static AppProperties getInstance () {
        if (instance == null) {
            try {
                InputStream inputStream = AppProperties.class.getClassLoader().getResourceAsStream("config.properties");
                instance = new AppProperties();
                instance.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

}
