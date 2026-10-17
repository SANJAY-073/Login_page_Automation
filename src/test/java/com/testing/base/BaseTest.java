
package com.testing.base;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void setUp() {
        driver = new ChromeDriver();

        wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(10)
        );

        driver.get(
            "https://the-internet.herokuapp.com/login"
        );
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
