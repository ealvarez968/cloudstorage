package com.udacity.jwdnd.course1.cloudstorage;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        WebElement username = driver.findElement(By.id("username"));
        username.clear();
        username.sendKeys("ealvarez");

        WebElement password = driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys("aqswdefr1");

        driver.findElement(By.tagName("form")).submit();



        Assertions.assertEquals("Home", driver.getTitle());
    }

    @Test
    public void shouldFailLogIn() {
        driver.get("http://localhost:" + this.port + "/login");
        WebElement username = driver.findElement(By.id("username"));
        username.clear();
        username.sendKeys("ealvarez1");

        WebElement password = driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys("aqswdefr1");

        driver.findElement(By.tagName("form")).submit();
        Assertions.assertEquals("Login", driver.getTitle());
        Assertions.assertEquals("Invalid username or password", driver.findElement(By.className("alert-danger")).getText());
    }

}
