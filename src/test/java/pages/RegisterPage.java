package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators pentru elementele de pe formularul de înregistrare
    private By firstNameInput = By.name("reg_fname");
    private By lastNameInput = By.name("reg_lname");
    private By emailInput = By.name("reg_email");
    private By confirmEmailInput = By.name("reg_email2");
    private By passwordInput = By.name("reg_password");
    private By confirmPasswordInput = By.name("reg_password2");
    private By registerButton = By.name("register_button");
    private By signInLink = By.id("signin"); // Link-ul "Already have an account? Sign in here!"

    private By signUpLink = By.id("signup"); // Link-ul "Already have an account? Sign in here!"

    // Constructor
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Metode de interacțiune
    public void enterFirstName(String firstName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        element.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterConfirmEmail(String confirmEmail) {
        driver.findElement(confirmEmailInput).sendKeys(confirmEmail);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        driver.findElement(confirmPasswordInput).sendKeys(confirmPassword);
    }

    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    // Metodă combinată pentru înregistrare
    public void registerUser(String firstName, String lastName, String email, String password) {
        clickSignUpLink();
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterConfirmEmail(email); // Presupunem că emailul de confirmare este la fel
        enterPassword(password);
        enterConfirmPassword(password); // Presupunem că parola de confirmare este la fel
        clickRegisterButton();
    }

    public void clickSignInLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(signInLink));
        link.click();
    }

    public void clickSignUpLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(signUpLink));
        link.click();
    }


    // Metode de verificare a stării paginii/formularului
    public boolean isRegisterFormDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(registerButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Poți adăuga metode pentru a citi mesaje de eroare specifice formularului de înregistrare,
    // dacă acestea sunt afișate în DOM după o validare eșuată.
    // Exemplu: public String getRegistrationErrorMessage() { ... }
}