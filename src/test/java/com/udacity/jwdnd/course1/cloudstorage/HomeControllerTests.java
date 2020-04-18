package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.helper.CredentialsTableHelper;
import com.udacity.jwdnd.course1.cloudstorage.helper.FormHelper;
import com.udacity.jwdnd.course1.cloudstorage.helper.NotesTableHelper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
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


        FormHelper formHelper = new FormHelper(driver);

        formHelper.logIn("username", "ealvarez", "password", "aqswdefr1");


        driver.findElement(By.id("nav-credentials-tab")).click();
        pause(900);
        driver.findElement(By.id("btnCredentialModal")).click();

        pause(900);


        formHelper.setTextByInputId("credential-url", testUrlCredential);
        formHelper.setTextByInputId("credential-username", testUsernameCredential);
        formHelper.setTextByInputId("credential-password", testPasswordCredential);


        driver.findElement(By.id("credentialSubmit")).submit();

        Assertions.assertEquals("Your credential for "+testUrlCredential+" has been added successfully", driver.findElement(By.id("lblMsg")).getText());

        driver.findElement(By.id("nav-credentials-tab")).click();
        pause(900);

        CredentialsTableHelper credentialsTable = new CredentialsTableHelper(driver,"credentialTable");

        List<WebElement> columns =  credentialsTable.getColumns(credentialsTable.getRows().size()-1);
        Assertions.assertEquals(testUrlCredential, columns.get(1).getText());
        Assertions.assertEquals(testUsernameCredential, columns.get(2).getText());
        Assertions.assertEquals("Testing123", columns.get(3).getAttribute("code"));


    }

    @Test
    public void shouldEditCredential() {
        driver.get("http://localhost:" + this.port + "/login");
        String testUrlCredential = "http://www.newurl.com";
        String testUsernameCredential = "newUser@gmail.com";
        String testPasswordCredential = "newPassword";

        FormHelper formHelper = new FormHelper(driver);
        formHelper.logIn("username", "ealvarez", "password", "aqswdefr1");


        driver.findElement(By.id("nav-credentials-tab")).click();
        pause(900);

        CredentialsTableHelper credentialTable = new CredentialsTableHelper(driver,"credentialTable");


        List<WebElement> columns = credentialTable.getColumns(credentialTable.getRows().size()-1);
        columns.get(0).findElement(By.tagName("button")).click();
        pause(900);

        formHelper.setTextByInputId("credential-url", testUrlCredential);
        formHelper.setTextByInputId("credential-username", testUsernameCredential);
        formHelper.setTextByInputId("credential-password", testPasswordCredential);

        driver.findElement(By.id("credentialSubmit")).submit();
        Assertions.assertEquals("Your credential for "+testUrlCredential+" has been updated successfully", driver.findElement(By.id("lblMsg")).getText());


        driver.findElement(By.id("nav-credentials-tab")).click();
        pause(900);

        Assertions.assertTrue( credentialTable.isFindInRow(credentialTable.getRows(),
                testUrlCredential, testUsernameCredential, testPasswordCredential ));


    }

    @Test
    public void shouldDeleteCredential(){
        driver.get("http://localhost:" + this.port + "/login");
        String testUrlCredential = "https://www.gmail.com";
        String testUsernameCredential = "micorreo@gmail.com";
        String testPasswordCredential = "aqswdefr1234";



        FormHelper formHelper = new FormHelper(driver);
        formHelper.logIn("username", "ealvarez", "password", "aqswdefr1");


        driver.findElement(By.id("nav-credentials-tab")).click();
        pause(900);

        CredentialsTableHelper credentialTable = new CredentialsTableHelper(driver,"credentialTable");

        //find the credential in the table to delete.
        List<WebElement> columns = credentialTable.getColumns(
                credentialTable.getCredentialRowIndex(testUrlCredential,
                        testUsernameCredential, testPasswordCredential));

        columns.get(0).findElement(By.tagName("a")).click();
        pause(900);

        Assertions.assertEquals("Your credential for "+testUrlCredential+" has been deleted successfully",
                driver.findElement(By.id("lblMsg")).getText());

        driver.findElement(By.id("nav-credentials-tab")).click();
        pause(900);

        Assertions.assertFalse( credentialTable.isFindInRow(credentialTable.getRows(), testUrlCredential,
                testUsernameCredential, testPasswordCredential));

    }

    @Test
    public void shouldSaveNote() {
        driver.get("http://localhost:" + this.port + "/login");
        String testNoteTitle = "Shopping";
        String testNoteDescription = "Sugar";

        FormHelper formHelper = new FormHelper(driver);
        formHelper.logIn("username", "ealvarez", "password", "aqswdefr1");

        driver.findElement(By.id("nav-notes-tab")).click();
        pause(900);
        driver.findElement(By.id("btnNoteModal")).click();

        pause(900);

        formHelper.setTextByInputId("note-title", testNoteTitle);

        formHelper.setTextByInputId("note-description", testNoteDescription);


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


        FormHelper formHelper = new FormHelper(driver);
        formHelper.logIn("username", "ealvarez", "password", "aqswdefr1");


        driver.findElement(By.id("nav-notes-tab")).click();
        pause(900);

        NotesTableHelper notesTable = new NotesTableHelper(driver,"noteTable");


        List<WebElement> columns = notesTable.getColumns(notesTable.getRows().size()-1);
        columns.get(0).findElement(By.tagName("button")).click();
        pause(900);


        formHelper.setTextByInputId("note-title", testNoteTitle);

        formHelper.setTextByInputId("note-description", testNoteDescription);

        driver.findElement(By.id("noteSubmit")).submit();

        Assertions.assertEquals("Your note "+testNoteTitle+" has been updated successfully",
                driver.findElement(By.id("lblMsg")).getText());

        driver.findElement(By.id("nav-notes-tab")).click();
        pause(900);

        Assertions.assertTrue( notesTable.isFindInRow(notesTable.getRows(), testNoteTitle, testNoteDescription));


    }

    @Test
    public void shouldDeleteNote(){
        driver.get("http://localhost:" + this.port + "/login");
        String testNoteTitle = "Titulo 1";



        FormHelper formHelper = new FormHelper(driver);
        formHelper.logIn("username", "ealvarez", "password", "aqswdefr1");


        driver.findElement(By.id("nav-notes-tab")).click();
        pause(900);

        NotesTableHelper notesTable = new NotesTableHelper(driver,"noteTable");


        List<WebElement> columns = notesTable.getColumns(notesTable.getTitleRowIndex(testNoteTitle));
        columns.get(0).findElement(By.tagName("a")).click();
        pause(900);

        Assertions.assertEquals("Your note "+testNoteTitle+" has been deleted successfully",
                driver.findElement(By.id("lblMsg")).getText());

        driver.findElement(By.id("nav-notes-tab")).click();
        pause(900);

        Assertions.assertFalse( notesTable.isFindInRow(notesTable.getRows(), testNoteTitle));

    }



    private void pause(int millis){
        try{
            Thread.sleep(millis);
        }catch (Exception e){

        }

    }
}
