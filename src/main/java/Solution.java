
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class Solution {

    public static String searchAndGetFirstResult(
            WebDriver driver, String url, String searchTerm) {

        driver.get(url);

        WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.name("q")
            )
        );

        searchBox.sendKeys(searchTerm + Keys.ENTER);

        try {
            List<WebElement> results = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector(".search-result h3")
                )
            );

            return results.isEmpty()
                ? "" : results.get(0).getText();

        } catch (org.openqa.selenium.TimeoutException e) {
            return "";
        }
    }

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();

        options.setBinary("/usr/bin/chromium");
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        try {
            String mockUrl = new File("mock-search.html")
                .getAbsoluteFile()
                .toURI()
                .toString();

            System.out.println(
                "Starting Selenium automation..."
            );

            String result = searchAndGetFirstResult(
                driver,
                mockUrl,
                "Selenium WebDriver"
            );

            System.out.println(
                "First result: " + result
            );

        } finally {
            driver.quit();
        }
    }
}