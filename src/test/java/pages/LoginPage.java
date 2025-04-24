package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class LoginPage {
    WebDriver driver;
    // Constructor – primim driverul din test
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    // Localizatori
    By usernameField = By.id("username");
    By passwordField = By.id("password");
    By submitButton = By.cssSelector("button[type='submit']");
    // Acțiune: login
    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(submitButton).click();
    }
}
