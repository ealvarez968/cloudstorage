package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.helper.CredentialsTableHelper;
import com.udacity.jwdnd.course1.cloudstorage.helper.NotesTableHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldSaveCredential() {
        driver.get("http://localhost:" + this.port + "/login");
        String testUrlCredential = "http://www.gmail.com";
        String testUsernameCredential = "test@gmail.com";
        String testPasswordCredential = "Testing123";


        WebElement username = driver.findElement(By.id("username"));
        username.clear();
        username.sendKeys("ealvarez");

        WebElement password = driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys("aqswdefr1");

        driver.findElement(By.tagName("form")).submit();
        driver.findElement(By.id("nav-credentials-tab")).click();
        pause(900);
        driver.findElement(By.id("btnCredentialModal")).click();

        pause(900);

        WebElement credentialUrl = driver.findElement(By.id("credential-url"));
        credentialUrl.clear();
        credentialUrl.sendKeys(testUrlCredential);

        WebElement credentialUsername = driver.findElement(By.id("credential-username"));
        credentialUsername.clear();
        credentialUsername.sendKeys(testUsernameCredential);

        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        credentialPassword.clear();
        credentialPassword.sendKeys(testPasswordCredential);

        driver.findElement(By.id("credentialSubmit")).submit();

        Assertions.assertEquals("Your credential for "+testUrlCredential+" has been added successfully", driver.findElement(By.id("lblMsg")).getText());

        driver.findElement(By.id("nav-credentials-tab")).click();
        pause(900);

        CredentialsTableHelper credentialsTable = new CredentialsTableHelper(driver,"credentialTable");

        List<WebElement> columns =  credentialsTable.getColumns(credentialsTable.getRows().size()-1);
        Assertions.assertEquals(testUrlCredential, columns.get(1).getText());
        Assertions.assertEquals(testUsernameCredential, columns.get(2).getText());
        Assertions.assertEquals(testPasswordCredential, columns.get(3).getAttribute("code"));


    }

    @Test
    public void shouldSaveNote() {
        driver.get("http://localhost:" + this.port + "/login");
        String testNoteTitle = "Shopping";
        String testNoteDescription = "Sugar";


        WebElement username = driver.findElement(By.id("username"));
        username.clear();
        username.sendKeys("ealvarez");

        WebElement password = driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys("aqswdefr1");

        driver.findElement(By.tagName("form")).submit();
        driver.findElement(By.id("nav-notes-tab")).click();
        pause(900);
        driver.findElement(By.id("btnNoteModal")).click();

        pause(900);

        WebElement noteTitle = driver.findElement(By.id("note-title"));
        noteTitle.clear();
        noteTitle.sendKeys(testNoteTitle);

        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.clear();
        noteDescription.sendKeys(testNoteDescription);


        driver.findElement(By.id("noteSubmit")).submit();


        Assertions.assertEquals("Your note "+testNoteTitle+" has been added successfully",
                    driver.findElement(By.id("lblMsg")).getText());


        driver.findElement(By.id("nav-notes-tab")).click();
        pause(900);

        NotesTableHelper notesTable = new NotesTableHelper(driver,"noteTable");

        List<WebElement> columns = notesTable.getColumns(notesTable.getRows().size()-1);
        Assertions.assertEquals(testNoteTitle, columns.get(1).getText());
        Assertions.assertEquals(testNoteDescription, columns.get(2).getText());

    }

    @Test
    public void shouldEditNote(){

        driver.get("http://localhost:" + this.port + "/login");
        String testNoteTitle = "NewTitle";
        String testNoteDescription = "NewDescription";


        WebElement username = driver.findElement(By.id("username"));
        username.clear();
        username.sendKeys("ealvarez");

        WebElement password = driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys("aqswdefr1");

        driver.findElement(By.tagName("form")).submit();
        driver.findElement(By.id("nav-notes-tab")).click();
        pause(900);

        NotesTableHelper notesTable = new NotesTableHelper(driver,"noteTable");


        List<WebElement> columns = notesTable.getColumns(notesTable.getRows().size()-1);
        columns.get(0).findElement(By.tagName("button")).click();
        pause(900);

        WebElement noteTitle = driver.findElement(By.id("note-title"));
        noteTitle.clear();
        noteTitle.sendKeys(testNoteTitle);

        WebElement noteDescription = driver.findElement(By.id("note-description"));
        noteDescription.clear();
        noteDescription.sendKeys(testNoteDescription);

        driver.findElement(By.id("noteSubmit")).submit();

        Assertions.assertEquals("Your note "+testNoteTitle+" has been updated successfully",
                driver.findElement(By.id("lblMsg")).getText());

        driver.findElement(By.id("nav-notes-tab")).click();
        pause(900);


        Assertions.assertTrue( notesTable.findInRow(notesTable.getRows(), testNoteTitle, testNoteDescription));


    }

    private boolean findInRow(List<WebElement> rows, String title, String description){
        for(WebElement column : rows){
            if(title == column.findElements(By.tagName("td")).get(1).getText()){
                if(description == column.findElements(By.tagName("td")).get(1).getText()){
                    return true;
                }
            }

        }

        return false;
    }

    private WebElement getTbody(String tableId){
        return driver.findElement(By.id(tableId)).findElement(By.tagName("tbody"));
    }

    private List<WebElement> getRows(WebElement tbody){
        return tbody.findElements(By.tagName("tr"));
    }

    private void pause(int millis){
        try{
            Thread.sleep(millis);
        }catch (Exception e){

        }

    }
}
