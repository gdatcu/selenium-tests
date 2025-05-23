package tests;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
public class LoginTest {
    WebDriver driver;
    @VisibleForTesting
    @BeforeMethod
    public void setup() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

//            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get("https://the-internet.herokuapp.com/login");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to remote WebDriver");
        }

    }
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File folder = new File("screenshots");
            if (!folder.exists()) {
                folder.mkdir();
            }
            String fileName = "screenshots/screenshot_" + System.currentTimeMillis() + ".png";
            try {
                FileUtils.copyFile(scr, new File(fileName));
                System.out.println("Screenshot salvat: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        driver.quit();
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
                {"tomsmith", "SuperSecretPassword!", true}, // valid
                {"userGresit", "parolaGresita", false} // invalid
        };
    }
    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password, boolean isValid) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        WebElement mesaj = driver.findElement(By.id("flash"));
        String text = mesaj.getText();
        if (isValid) {
            Assert.assertTrue(text.contains("You logged into a secure area!"), "Login valid a eșuat.");
        } else {
            Assert.assertTrue(text.contains("Your username is invalid!"), "Login invalid nu a fost detectat.");
        }
    }
}
