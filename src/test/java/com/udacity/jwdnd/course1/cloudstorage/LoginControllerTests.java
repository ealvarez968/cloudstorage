package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.helper.FormHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTests {

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
    public void shouldLogIn() {
        driver.get("http://localhost:" + this.port + "/login");

        FormHelper formHelper = new FormHelper(driver);
        formHelper.logIn("username", "ealvarez", "password", "aqswdefr1");

        Assertions.assertEquals("Home", driver.getTitle());
    }

    @Test
    public void shouldFailLogIn() {
        driver.get("http://localhost:" + this.port + "/login");

        FormHelper formHelper = new FormHelper(driver);
        formHelper.logIn("username", "ealvarez1", "password", "aqswdefr1");

        Assertions.assertEquals("Login", driver.getTitle());
        Assertions.assertEquals("Invalid username or password", driver.findElement(By.className("alert-danger")).getText());
    }

    @Test
    public void shouldFailAccess() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void shouldSignUp() {
        driver.get("http://localhost:" + this.port + "/login");

        driver.findElement(By.tagName("a")).click();

        FormHelper formHelper = new FormHelper(driver);

        formHelper.setTextByInputId("inputFirstName","First");
        formHelper.setTextByInputId("inputLastName","Last");
        formHelper.setTextByInputId("inputUsername","myuser");
        formHelper.setTextByInputId("inputPassword","password");

        driver.findElement(By.tagName("form")).submit();

        driver.findElements(By.tagName("a")).get(0).click();
        formHelper.logIn("username", "myuser", "password", "password");
        Assertions.assertEquals("Home", driver.getTitle());
    }

}
