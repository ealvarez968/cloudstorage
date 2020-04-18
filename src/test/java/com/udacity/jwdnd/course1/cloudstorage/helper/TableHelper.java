package com.udacity.jwdnd.course1.cloudstorage.helper;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

class TableHelper extends TestsHelper {

    private String tableId;

    public TableHelper(WebDriver driver, String tableId){
        super(driver);
        this.tableId = tableId;
    }
    public TableHelper(WebDriver driver){
        super(driver);
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public WebElement getTbody(){
        return getDriver().findElement(By.id(tableId)).findElement(By.tagName("tbody"));
    }

    public List<WebElement> getRows(){
        return getTbody().findElements(By.tagName("tr"));
    }

    public WebElement getRow(int index){
        return getTbody().findElements(By.tagName("tr")).get(index);
    }

    public List<WebElement> getColumns(int index){
        return getRow(index).findElements(By.tagName("td"));
    }

}
