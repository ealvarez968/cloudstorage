package com.udacity.jwdnd.course1.cloudstorage.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FormHelper extends TestsHelper{


    public FormHelper(WebDriver driver) {
        super(driver);
    }

    public void setTextByInputId(String id, String text){

        WebElement inputText = getDriver().findElement(By.id(id));
        inputText.clear();
        inputText.sendKeys(text);

    }

    public void logIn(String userInputId, String username, String passwordInputId, String password){


        setTextByInputId(userInputId, username);
        setTextByInputId(passwordInputId, password);

        getDriver().findElement(By.tagName("form")).submit();
    }

}
