package org.example;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;


public class fitPeo {

    public static void main(String args[]) throws Exception {

        // Set up WebDriver and launch Chrome browser
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        // Navigate to the FitPeo Homepage
        driver.get("https://www.fitpeo.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Navigate to the revenue calculator page
        driver.findElement(By.xpath("//*[text()='Revenue Calculator']")).click();

        // Wait for the slider element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Locate the value field and the slider handle
        WebElement value = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Total Individual Patient/Month']/following-sibling::p")));

        WebElement sliderHandle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@data-index='0' and @type='range']")));

        // Initialize Actions class for clicking and interacting with the slider
        Actions actions = new Actions(driver);

        // Click on the slider handle to focus it and make it interactable
        actions.moveToElement(sliderHandle).click().perform();

        // Optionally, use Robot for simulating key presses (right arrow)
        Robot robot = new Robot();

        // Set the target value (820) and starting value (200)
        int targetValue = 820;
        int currentValue = Integer.parseInt(value.getText().trim());

        // Starting value from the span
        int maxValue = 2000;

        // Maximum value for the slider
        // Loop and simulate the right arrow key press until the target value is reached
        while (currentValue < targetValue) {

            // Optionally, simulate a right arrow key press to adjust the slider
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_RIGHT);

            // Optionally, add a small delay to simulate human behavior
            Thread.sleep(100);

            // Adjust the sleep time if necessary
            // Check the updated value in the span element
            value = driver.findElement(By.xpath("//*[text()='Total Individual Patient/Month']/following-sibling::p"));
            currentValue = Integer.parseInt(value.getText().trim());

            // Print the current value to monitor progress
            System.out.println("Current Value: " + currentValue);
        }

        // After reaching the target value, print the final value
        System.out.println("Target value reached: " + targetValue);

        // Close the browser after the interaction//
        driver.quit();

    }
}