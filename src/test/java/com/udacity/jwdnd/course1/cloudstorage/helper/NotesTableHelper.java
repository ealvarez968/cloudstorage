package com.udacity.jwdnd.course1.cloudstorage.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NotesTableHelper extends TableHelper {


    public NotesTableHelper(WebDriver driver) {
        super(driver);
    }

    public NotesTableHelper(WebDriver driver, String tableId){
        super(driver, tableId);

    }

    public boolean findInRow(List<WebElement> rows, String noteTitle, String noteDescription){
        for(WebElement column : rows){
            if(noteTitle == column.findElements(By.tagName("td")).get(1).getText()){
                if(noteDescription == column.findElements(By.tagName("td")).get(2).getText()){
                    return true;
                }
            }

        }

        return false;
    }
}
