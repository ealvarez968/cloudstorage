package com.udacity.jwdnd.course1.cloudstorage.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NotesTableHelper extends TableHelper {


    public NotesTableHelper(WebDriver driver, String tableId) {
        super(driver, tableId);
    }

    public NotesTableHelper(WebDriver driver){
        super(driver);
    }

    public boolean isFindInRow(List<WebElement> rows, String noteTitle, String noteDescription){
        for(WebElement column : rows){
            if(noteTitle.equals(column.findElements(By.tagName("td")).get(1).getText())){
                if(noteDescription.equals(column.findElements(By.tagName("td")).get(2).getText())){
                    return true;
                }
            }

        }

        return false;
    }

    public boolean isFindInRow(List<WebElement> rows, String noteTitle){
        for(WebElement column : rows){
            if(noteTitle.equals(column.findElements(By.tagName("td")).get(1).getText())){
                    return true;
            }
        }
        return false;
    }

    public int getTitleRowIndex(String noteTitle){
        int i = 0;
        for(WebElement column : getRows()){
            if(noteTitle.equals(column.findElements(By.tagName("td")).get(1).getText())){
                return i;
            }
            i++;
        }
        return -1;
    }
}
