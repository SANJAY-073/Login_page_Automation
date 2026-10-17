
package com.testing.tests;

import org.junit.Assert;
import org.junit.Test;

import com.testing.base.BaseTest;
import com.testing.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void verifySuccessfulLogin() {

        LoginPage loginPage = new LoginPage(driver, wait);

        loginPage.login("tomsmith", "SuperSecretPassword!");

        String actualMessage = loginPage.getMessage();

        Assert.assertTrue(
            "Success message was not displayed",
            actualMessage.contains(
                "You logged into a secure area!"
            )
        );

        Assert.assertTrue(
            "User was not redirected to the secure page",
            loginPage.getCurrentUrl().contains("/secure")
        );
    }

    @Test
    public void verifyInvalidPassword() {

        LoginPage loginPage = new LoginPage(driver, wait);

        loginPage.login("tomsmith", "wrong123");

        String actualMessage = loginPage.getMessage();

        Assert.assertTrue(
            "Incorrect password error was not displayed",
            actualMessage.contains(
                "Your password is invalid!"
            )
        );

        Assert.assertTrue(
            "User should remain on the login page",
            loginPage.getCurrentUrl().contains("/login")
        );
    }

    @Test
    public void verifyInvalidUsername() {

        LoginPage loginPage = new LoginPage(driver, wait);

        loginPage.login("wronguser", "SuperSecretPassword!");

        String actualMessage = loginPage.getMessage();

        Assert.assertTrue(
            "Incorrect username error was not displayed",
            actualMessage.contains(
                "Your username is invalid!"
            )
        );

        Assert.assertTrue(
            "User should remain on the login page",
            loginPage.getCurrentUrl().contains("/login")
        );
    }
    
@Test
public void verifyEmptyUsername() {

    LoginPage loginPage = new LoginPage(driver, wait);

    loginPage.login("", "SuperSecretPassword!");

    String actualMessage = loginPage.getMessage();

    Assert.assertTrue(
        "Expected username validation message",
        actualMessage.contains("Your username is invalid!")
    );

    Assert.assertTrue(
        "User should remain on the login page",
        loginPage.getCurrentUrl().contains("/login")
    );
}

@Test
public void verifyEmptyPassword() {

    LoginPage loginPage = new LoginPage(driver, wait);

    loginPage.login("tomsmith", "");

    String actualMessage = loginPage.getMessage();

    Assert.assertTrue(
        "Expected password validation message",
        actualMessage.contains("Your password is invalid!")
    );

    Assert.assertTrue(
        "User should remain on the login page",
        loginPage.getCurrentUrl().contains("/login")
    );
}

}
