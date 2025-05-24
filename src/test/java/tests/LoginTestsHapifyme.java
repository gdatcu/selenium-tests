package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert; // Import pentru aserțiunile TestNG
import org.testng.ITestResult; // Utile pentru metodele @AfterMethod, dacă vrei să lucrezi cu rezultatul testului
import org.testng.annotations.*; // Import pentru toate adnotările TestNG
import pages.LoginPageHapifyme;

import java.time.Duration;

public class LoginTestsHapifyme {

    private WebDriver driver;
    private LoginPageHapifyme loginPage;
    private WebDriverWait wait;

    // ATENȚIE: Înlocuiește cu calea reală către ChromeDriver-ul tău!
//    private final String CHROME_DRIVER_PATH = "C:/path/to/your/chromedriver.exe";
    // ATENȚIE: Înlocuiește cu URL-ul real al paginii tale de login hapifyMe!

//    private final String BASE_URL = "file:///C:/path/to/your/hapifyMe/login_page.html"; // Ex. pentru fișier local
    private final String BASE_URL = "https://test.hapifyme.com/login_register.php"; // Ex. pentru fișier local
    // Sau, dacă este pe un server: "http://localhost:8080/login_register.php" sau "https://yourdomain.com/login"


    @BeforeMethod // Rulează înainte de fiecare metodă de test
    public void setUp() {
//        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // options.addArguments("--headless");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LoginPageHapifyme(driver);
        driver.get(BASE_URL);
    }

    @Test
    public void testLoginCuCredentialeValide() {
        System.out.println("Rulăm testul: testLoginCuCredentialeValide");
        loginPage.login("jane.doe123@example.com", "Abcde@13"); // Înlocuiește cu un email și parolă valide

        // --- Aserțiune pentru login de succes ---
        // Adaptează conform comportamentului real al aplicației.
        try {
            // Așteaptă ca URL-ul să conțină ceva specific paginii de succes (e.g., "dashboard.php")
            // Dacă aplicația nu redirecționează, această aserțiune va eșua.
            Assert.assertTrue(wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(BASE_URL))),
                    "Login eșuat: URL-ul nu s-a schimbat, sugerând că nu a avut loc redirecționarea.");
            System.out.println("URL-ul s-a schimbat, sugerând un login de succes.");

            // Aici poți adăuga și alte aserțiuni, cum ar fi verificarea prezenței unui element de bun venit
            // Exemplu: Assert.assertTrue(driver.findElement(By.id("welcome_message")).isDisplayed(), "Mesajul de bun venit nu a apărut.");

        } catch (Exception e) {
            Assert.fail("Login-ul nu a fost reușit sau nu a avut loc redirecționarea așteptată: " + e.getMessage());
        }
    }

    @Test
    public void testLoginCuCredentialeInvalide() {
        System.out.println("Rulăm testul: testLoginCuCredentialeInvalide");
        loginPage.login("invalid@example.com", "parolaGresita"); // Înlocuiește cu un email și parolă invalide

        // --- Aserțiune pentru login eșuat ---
        // Verifică dacă pagina de login este încă afișată (nu s-a făcut redirecționare).
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Pagina de login a dispărut, dar ne așteptam să rămână afișată pentru login eșuat.");

        // Aici ai putea adăuga o aserțiune pentru un mesaj de eroare specific,
        // dacă aplicația ta afișează unul.
        // Exemplu: String errorMessage = loginPage.getErrorMessage();
        // Assert.assertNotNull(errorMessage, "Nu a apărut niciun mesaj de eroare.");
        // Assert.assertTrue(errorMessage.contains("Invalid"), "Mesajul de eroare nu este cel așteptat.");
        System.out.println("Login-ul a eșuat așa cum era așteptat cu credențiale invalide.");
    }

    @AfterMethod // Rulează după fiecare metodă de test
    public void tearDown(ITestResult result) {
        if (driver != null) {
            System.out.println("Închidem browserul după testul: " + result.getName() + " cu status: " + getTestStatus(result.getStatus()));
            driver.quit();
        }
    }

    // Helper method pentru a obține statusul testului
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