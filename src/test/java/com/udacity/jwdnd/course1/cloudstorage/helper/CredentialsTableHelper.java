package com.udacity.jwdnd.course1.cloudstorage.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CredentialsTableHelper extends TableHelper{
    public CredentialsTableHelper(WebDriver driver, String tableId) {
        super(driver, tableId);
    }

    public boolean isFindInRow(List<WebElement> rows, String credUrl, String credUsername, String credPassword){
        for(WebElement column : rows){
            if(credUrl.equals(column.findElements(By.tagName("td")).get(1).getText())){
                if(credUsername.equals(column.findElements(By.tagName("td")).get(2).getText())){
                    if(credPassword.equals(column.findElements(By.tagName("td")).get(3).getAttribute("code"))){
                        return true;
                    }
                }
            }

        }

        return false;
    }

    public int getCredentialRowIndex(String credUrl, String credUsername, String credPassword){
        int i = 0;
        for(WebElement column : getRows()){
            if(credUrl.equals(column.findElements(By.tagName("td")).get(1).getText())){
                if(credUsername.equals(column.findElements(By.tagName("td")).get(2).getText())){
                    if(credPassword.equals(column.findElements(By.tagName("td")).get(3).getAttribute("code"))){
                        return i;
                    }
                }
            }
            i++;
        }
        return -1;
    }
}
