
package com.testing;

import java.time.Duration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleTitleTest {

    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://the-internet.herokuapp.com/login");
    }

    @Test
    public void verifySuccessfulLogin() {
        driver.findElement(By.id("username"))
                .sendKeys("tomsmith");

        driver.findElement(By.id("password"))
                .sendKeys("SuperSecretPassword!");

        driver.findElement(By.className("radius")).click();

        WebElement message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("flash")
                )
        );

        String actualMessage = message.getText();

        Assert.assertTrue(
                "Success message was not displayed",
                actualMessage.contains(
                        "You logged into a secure area!"
                )
        );

        System.out.println("LOGIN TEST PASSED");
    }

    @Test
    public void verifyInvalidLogin() {
        driver.findElement(By.id("username"))
                .sendKeys("tomsmith");

        driver.findElement(By.id("password"))
                .sendKeys("WrongPassword123");

        driver.findElement(By.className("radius")).click();

        WebElement message = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("flash")
                )
        );

        String actualMessage = message.getText();

        Assert.assertTrue(
                "Invalid login message was not displayed",
                actualMessage.contains(
                        "Your password is invalid!"
                )
        );

        System.out.println("INVALID LOGIN TEST PASSED");
    }
@Test
public void verifyEmptyUsername() {
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("password"))
          .sendKeys("SuperSecretPassword!");
    driver.findElement(By.cssSelector("button.radius")).click();

    WebElement message = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
    );

    System.out.println("Empty username result: " + message.getText());

    Assert.assertTrue(
        "Login should not succeed with an empty username",
        driver.getCurrentUrl().contains("/login")
    );
}

@Test
public void verifyEmptyPassword() {
    driver.findElement(By.id("username"))
          .sendKeys("tomsmith");

    driver.findElement(By.id("password")).clear();

    driver.findElement(By.cssSelector("button.radius")).click();

    WebElement message = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
    );

    System.out.println("Empty password result: " + message.getText());

    Assert.assertTrue(
        "Login should not succeed with an empty password",
        driver.getCurrentUrl().contains("/login")
    );
}@Test
public void verifyEmptyFields() {
    driver.findElement(By.id("username")).clear();

    driver.findElement(By.id("password")).clear();

    driver.findElement(By.cssSelector("button.radius")).click();

    WebElement message = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
    );

    System.out.println("Empty fields result: " + message.getText());

    Assert.assertTrue(
        "Login should not succeed with an empty password and empty username",
        driver.getCurrentUrl().contains("/login")
    );
}

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
