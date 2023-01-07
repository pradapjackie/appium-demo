package com.appium.demo.mavenappiumdemo;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.ListViewPage;
import pages.LoginPage;
import java.net.MalformedURLException;
import java.net.URL;

public class MainTestClass {
    private final DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    private AndroidDriver driver;
    private LoginPage loginPage;
    private ListViewPage listViewPage;

    @BeforeSuite
    public void setupDeviceCapabilities() {
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:app", "/src/main/resources/app-debug.apk");
        desiredCapabilities.setCapability("appium:androidVersion", "9.0");
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);
    }

    @BeforeMethod
    public void startAndroidDriver() throws MalformedURLException {
        try {
            driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1, description = "Login with valid username and password")
    public void login_with_valid_username_and_password() {
        loginPage = new LoginPage(driver);
        listViewPage = new ListViewPage(driver);
        loginPage.enter_username_as_email_address("pradap@espresso.com");
        loginPage.enter_password_as_password_field("espresso_password");
        loginPage.click_on_login_button();
        listViewPage.validate_list_view_is_visible();
    }

    @Test(priority = 2, description = "Login with invalid username and password")
    public void login_with_invalid_username() {
        loginPage = new LoginPage(driver);
        listViewPage = new ListViewPage(driver);
        loginPage.enter_username_as_email_address("pradap1@espresso.com");
        loginPage.enter_password_as_password_field("espresso_password");
        loginPage.click_on_login_button();
        loginPage.validate_invalid_login_message();

    }

    @Test(priority = 3, description = "Login with valid username and invalid password")
    public void login_with_invalid_password() {
        loginPage = new LoginPage(driver);
        listViewPage = new ListViewPage(driver);
        loginPage.enter_username_as_email_address("pradap@espresso.com");
        loginPage.enter_password_as_password_field("test");
        loginPage.click_on_login_button();
        loginPage.validate_invalid_login_message();

    }

    @AfterSuite
    public void tearDown() {

        driver.quit();
    }
}
