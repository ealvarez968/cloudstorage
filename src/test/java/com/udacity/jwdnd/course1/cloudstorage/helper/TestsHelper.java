package com.udacity.jwdnd.course1.cloudstorage.helper;

import org.openqa.selenium.WebDriver;

class TestsHelper {

    private WebDriver driver;

    public TestsHelper(WebDriver driver){
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

}
