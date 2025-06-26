package org.example;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginValid {

    public static void main(String[] args) {
        // 1. Deschidem browserul Chrome
        WebDriver driver = new ChromeDriver();
        // 2. Navigăm la pagina de login
        driver.get("https://the-internet.herokuapp.com/login");
        // 3. Găsim câmpul de username (prin ID) și completăm cu username valid
        // selectors
        driver.findElement(By.id("username")).sendKeys("tomsmith");

//        WebElement uname = driver.findElement(By.id("username"));
//        uname.sendKeys("tomsmith");

        // 4. Găsim câmpul de parolă și completăm cu parola validă
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!!");
        // 5. Găsim și apăsăm butonul de login
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // 6. Căutăm elementul care afișează mesajul de confirmare
        WebElement mesaj = driver.findElement(By.id("flash"));
        // 7. Preluăm textul din acel element
        String text = mesaj.getText();
        // 8. Verificăm dacă textul conține mesajul corect
        if (text.contains("You logged into a secure area!")) {
            System.out.println("TEST PASSED: login valid");
        } else {
            System.out.println("TEST FAILED: mesajul nu e cel așteptat");
        }
        // 9. Închidem browserul
        driver.quit();
		System.out.println("Modificari impreuna cu Yumy...!");
    }

}
