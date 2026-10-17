
package com.testing;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Parameterized.class)
public class DataDrivenLoginTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private String username;
    private String password;
    private boolean shouldLoginSucceed;
    private String expectedMessage;

    public DataDrivenLoginTest(
            String username,
            String password,
            boolean shouldLoginSucceed,
            String expectedMessage) {

        this.username = username;
        this.password = password;
        this.shouldLoginSucceed = shouldLoginSucceed;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][] {
            {
                "tomsmith",
                "wrong123",
                false,
                "Your password is invalid!"
            },
            {
                "wronguser",
                "SuperSecretPassword!",
                false,
                "Your username is invalid!"
            },
            {
                "tomsmith",
                "SuperSecretPassword!",
                true,
                "You logged into a secure area!"
            }
        });
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://the-internet.herokuapp.com/login");
    }

    @Test
    public void verifyLogin() {

        driver.findElement(By.id("username"))
              .sendKeys(username);

        driver.findElement(By.id("password"))
              .sendKeys(password);

        driver.findElement(
                By.cssSelector("button[type='submit']"))
              .click();

        // Wait until the message appears
        WebElement message = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("flash")
            )
        );

        String actualMessage = message.getText();

        // Verify the expected message
        Assert.assertTrue(
            "Expected: " + expectedMessage
            + " but got: " + actualMessage,
            actualMessage.contains(expectedMessage)
        );

        // Verify the URL
        if (shouldLoginSucceed) {

            Assert.assertTrue(
                "Login should succeed",
                driver.getCurrentUrl().contains("/secure")
            );

        } else {

            Assert.assertTrue(
                "Invalid login should remain on login page",
                driver.getCurrentUrl().contains("/login")
            );
        }
    }

    // Runs after each test
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
