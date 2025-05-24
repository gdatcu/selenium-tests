package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPageHapifyme {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators pentru elementele de pe pagina de login
    private By emailInput = By.id("emailId");
    private By passwordInput = By.id("passwordId");
    private By loginButton = By.name("login_button");
    private By loginButtonV2 = By.xpath("//input[@name='login_button' or @value='Login']");
    private By loginButtonV3 = By.xpath("//input[@name='login_button' or @type='submit'])[1]");
    private By signUpLink = By.id("signup"); // Link-ul "Need an account? Register here!"

    private By loginError = By.id("log_inv"); // Textul de eroare in caz de failed login

    // Constructorul clasei LoginPage
    public LoginPageHapifyme(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Așteaptă maxim 10 secunde
    }

    // Metode de interacțiune cu elementele
    public void enterEmail(String email) {
        // Așteaptă ca elementul de email să fie vizibil și activ
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordField = driver.findElement(passwordInput);
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement button = driver.findElement(loginButton);
        button.click();
    }

    // Metodă combinată pentru login
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public void clickSignUpLink() {
        WebElement link = driver.findElement(signUpLink);
        link.click();
    }

    // Metode de verificare a stării paginii (opțional, dar util pentru aserțiuni)
    public boolean isLoginPageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Aici poți adăuga metode pentru a verifica mesaje de eroare specifice
    // Exemplu: dacă ai un div cu id="error_message" pentru erorile de login
    // public String getErrorMessage() {
    //     try {
    //         WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error_message")));
    //         return errorMessage.getText();
    //     } catch (Exception e) {
    //         return null;
    //     }
    // }
}