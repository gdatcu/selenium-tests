package tests;

import org.openqa.selenium.By;
import pages.LoginPageHapifyme;
import pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

public class RegisterTests {

    private WebDriver driver;
    private RegisterPage registerPage;
    private LoginPageHapifyme loginPage; // Dacă testele încep de pe pagina de login și comută la register
    private WebDriverWait wait;

//    private final String CHROME_DRIVER_PATH = "C:/path/to/your/chromedriver.exe";
    private final String BASE_URL = "https://test.hapifyme.com/login_register.php"; // Pagina inițială unde este formularul

    @BeforeMethod
    public void setUp() {
//        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // options.addArguments("--headless");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        registerPage = new RegisterPage(driver);
        loginPage = new LoginPageHapifyme(driver); // Inițializează LoginPage pentru a putea accesa link-ul de signup

        driver.get(BASE_URL);

        // Deoarece formularul de înregistrare este inițial afișat (display: block; pe #second),
        // nu este necesar să apăsăm pe "Need an account? Register here!"
        // Dacă formularul de login ar fi fost cel implicit, am fi avut nevoie de:
        // loginPage.clickSignUpLink(); // Comută la formularul de înregistrare
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("reg_fname"))); // Așteaptă formularul de register
    }

    @Test
    public void testInregistrareCuDateValide() {
        System.out.println("Rulăm testul: testInregistrareCuDateValide");

        // Generăm date unice pentru fiecare rulare
        String uniqueEmail = "testuser_" + System.currentTimeMillis() + "@example.com";
        String password = "StrongPassword123@";
        String firstName = "User_" + System.currentTimeMillis();
        String lastName = "User_" + System.currentTimeMillis();

        registerPage.registerUser(firstName, lastName, uniqueEmail, password);

        // --- Aserțiune pentru înregistrare de succes ---
        // Această parte este CRITICĂ și depinde de comportamentul aplicației tale după o înregistrare de succes.
        // Exemple de aserțiuni:
        // 1. Redirecționare către o pagină de confirmare/dashboard:
        //    Assert.assertTrue(wait.until(ExpectedConditions.urlContains("success_page.php")),
        //            "Înregistrare eșuată: Nu s-a redirecționat către pagina de succes.");
        // 2. Afișarea unui mesaj de succes pe aceeași pagină:
        //    try {
        //        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success_message")));
        //        Assert.assertTrue(successMessage.isDisplayed() && successMessage.getText().contains("Înregistrare reușită!"),
        //                "Mesajul de succes nu a apărut sau este incorect.");
        //    } catch (Exception e) {
        //        Assert.fail("Nu a apărut niciun mesaj de succes după înregistrare.");
        //    }
        // 3. Comutarea înapoi la formularul de login cu un mesaj de succes:
        //    De exemplu, dacă `login_register.php` după înregistrare mută `id="first"` la `display: block`
        //    și `id="second"` la `display: none`, și afișează un mesaj.
        //    Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Nu s-a comutat la pagina de login după înregistrare.");
        //    (Aici ar trebui să verificăm și un mesaj de succes, dacă există)

        // Deoarece nu știm exact ce face `login_register.php` după înregistrare,
        // vom face o aserțiune generică, dar importantă:
        // Că pagina se schimbă sau că elementele formularului de înregistrare nu mai sunt prezente.
        try {
            // Așteaptă ca elementele formularului de înregistrare să devină "stale" (să dispară sau să nu mai fie atașate DOM-ului)
            // sau ca URL-ul să se schimbe.
            Assert.assertTrue(wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(BASE_URL))) ||
                            wait.until(ExpectedConditions.stalenessOf(driver.findElement(By.name("reg_fname")))),
                    "Înregistrare eșuată: Pagina nu s-a schimbat sau formularul de înregistrare este încă vizibil.");
            System.out.println("Înregistrare de succes, pagina s-a modificat conform așteptărilor.");
        } catch (Exception e) {
            Assert.fail("Înregistrare eșuată: Nu a avut loc modificarea așteptată a paginii: " + e.getMessage());
        }
    }

    @Test
    public void testInregistrareCuEmailuriDiferite() {
        System.out.println("Rulăm testul: testInregistrareCuEmailuriDiferite");
        registerPage.enterFirstName("Maria");
        registerPage.enterLastName("Ionescu");
        registerPage.enterEmail("test@example.com");
        registerPage.enterConfirmEmail("different@example.com"); // Emailuri diferite
        registerPage.enterPassword("Password123!");
        registerPage.enterConfirmPassword("Password123!");
        registerPage.clickRegisterButton();

        // --- Aserțiune pentru înregistrare eșuată (emailuri diferite) ---
        // Presupunem că formularul rămâne afișat și/sau apare un mesaj de eroare.
        Assert.assertTrue(registerPage.isRegisterFormDisplayed(),
                "Formularul de înregistrare a dispărut, dar ne așteptam să rămână afișat pentru emailuri diferite.");

        // Aici ar trebui să verifici prezența și conținutul unui mesaj de eroare specific.
        // Exemplu: if (registerPage.getErrorMessage().contains("Emails don't match")) { ... }
        System.out.println("Înregistrare eșuată așa cum era așteptat cu emailuri diferite.");
    }

    @Test
    public void testInregistrareCuParoleDiferite() {
        System.out.println("Rulăm testul: testInregistrareCuParoleDiferite");
        registerPage.enterFirstName("Andrei");
        registerPage.enterLastName("Vasilescu");
        registerPage.enterEmail("andrei@example.com");
        registerPage.enterConfirmEmail("andrei@example.com");
        registerPage.enterPassword("parola1");
        registerPage.enterConfirmPassword("parola2"); // Parole diferite
        registerPage.clickRegisterButton();

        // --- Aserțiune pentru înregistrare eșuată (parole diferite) ---
        Assert.assertTrue(registerPage.isRegisterFormDisplayed(),
                "Formularul de înregistrare a dispărut, dar ne așteptam să rămână afișat pentru parole diferite.");

        // Verifică un mesaj de eroare specific.
        System.out.println("Înregistrare eșuată așa cum era așteptat cu parole diferite.");
    }

    @Test
    public void testInregistrareFaraCampuriObligatorii() {
        System.out.println("Rulăm testul: testInregistrareFaraCampuriObligatorii");
        // Nu introducem nicio dată
        registerPage.clickRegisterButton();

        // --- Aserțiune pentru înregistrare eșuată (câmpuri goale) ---
        // Browserul ar trebui să afișeze mesaje de validare HTML5 (`required`).
        // Selenium nu interacționează direct cu aceste mesaje, dar testul poate verifica
        // că formularul nu a fost trimis (adică URL-ul nu s-a schimbat și formularul e încă acolo).
        Assert.assertTrue(registerPage.isRegisterFormDisplayed(),
                "Formularul de înregistrare a dispărut, dar ne așteptam să rămână afișat pentru câmpuri goale.");

        // Dacă ai validare pe backend și mesaje de eroare în DOM, verifică-le aici.
        System.out.println("Înregistrare eșuată așa cum era așteptat cu câmpuri goale.");
    }

    @Test
    public void testComutareLaLoginPage() {
        System.out.println("Rulăm testul: testComutareLaLoginPage");
        registerPage.clickSignInLink(); // Apasă pe link-ul "Already have an account? Sign in here!"

        // Verifică dacă formularul de login este acum vizibil
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Nu s-a comutat la formularul de login după apăsarea link-ului 'Sign in here!'.");
        System.out.println("Comutat cu succes la formularul de login.");
    }


//    @AfterMethod
//    public void tearDown(ITestResult result) {
//        if (driver != null) {
//            System.out.println("Închidem browserul după testul: " + result.getName() + " cu status: " + getTestStatus(result.getStatus()));
//            driver.quit();
//        }
//    }

    private String getTestStatus(int status) {
        if (status == ITestResult.SUCCESS) {
            return "SUCCESS";
        } else if (status == ITestResult.FAILURE) {
            return "FAILURE";
        } else if (status == ITestResult.SKIP) {
            return "SKIPPED";
        }
        return "UNKNOWN";
    }
}