package org.example;

import com.codeborne.selenide.Configuration;

public class WebDriverConfig {

    public static void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 30000;
    }
}



