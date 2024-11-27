package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

public class fitPeo {

    static WebDriver driver = new ChromeDriver();
    static JavascriptExecutor js = (JavascriptExecutor) driver;

    public static void main(String[] args) throws InterruptedException {

// For launching Chrome browser
        WebDriverManager.chromedriver().setup();

// Navigate to the FitPeo Home page
        driver.get("https://www.fitpeo.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

// For navigating to the revenue calculator page
        driver.findElement(By.xpath("//*[text()='Revenue Calculator']")).click();

// For waiting explicitly for the slider to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='MuiSlider-root MuiSlider-colorPrimary MuiSlider-sizeMedium css-16i48op']")));

// Get the width of the slider element to calculate the offset
        int width = slider.getSize().getWidth();
        System.out.println("Slider Width: " + width);

// Initializing the slider values
        int startingValue = 200; // Starting point of the slider
        int targetValue = 820; // Desired target value
        int maxValue = 2000; // Maximum value of the slider (Ensure this is correct)

// To calculate the difference between starting value and target value
        int differenceValue = targetValue - startingValue;

// To calculate the targetOffset based on the slider width
        int targetOffset = (differenceValue * width) / maxValue;
        System.out.println("Target Offset: " + targetOffset);

// For handling the slider movement to target value
        WebElement sliderHandle = driver.findElement(By.xpath("//*[@type='range']"));

// Creating Actions object for slider interaction
        Actions act = new Actions(driver);

// To click and hold the slider handle, move it to the calculated offset, and
// release
        act.clickAndHold(sliderHandle).moveByOffset(targetOffset, 0).release().perform();

// As observed, the slider targets odd numbers (e.g., 817 instead of 820)
// This can be handled as per the application's behavior if needed

// Scroll down by 500 pixels to bring the next field into view
        js.executeScript("window.scrollBy(0, 500)");

// To enter the text field value
        WebElement textField = driver.findElement(By.xpath("//input[contains(@class,'MuiInputBase') and @type='number']"));

// Wait until the text field is clickable
        wait.until(ExpectedConditions.elementToBeClickable(textField));

        // click the targeted ele
        textField.click();

// send the values with control option
        textField.sendKeys(Keys.chord(Keys.CONTROL, "a"));
// Enter the input value
        textField.sendKeys("560");

        String[] checkboxes = new String[]{"CPT-99091", "CPT-99453", "CPT-99454", "CPT-99474"};
        Arrays.stream(checkboxes).forEach(cons -> {
            clickCheckBox(cons);
        });
        textField.sendKeys(Keys.chord(Keys.CONTROL, "a"));

// Enter the input value
        textField.sendKeys("820");

// Scroll down by 500 pixels to bring the next field into view
        js.executeScript("window.scrollBy(0, 250)");


        try {

            // Locate the header containing the total recurring reimbursement value
            // Replace 'YOUR_HEADER_SELECTOR' with the appropriate CSS selector for the header element
            WebElement headerElement = driver.findElement(By.xpath("//*[text()='Total Recurring Reimbursement for all Patients Per Month:'] /p"));

            // Get the text from the header
            String headerValue = headerElement.getText().trim();

            //Validate that the text matches the expected value
            String expectedValue = "$110700";

            if (headerValue.equals(expectedValue)) {

                System.out.println("Validation successful: The header expected value is " + expectedValue + "and The Actual value is " + headerValue);
            } else {

                System.out.println("Validation failed: Expected '" + expectedValue + "', but got '" + headerValue + "'.");
            }

        } catch (Exception e) {

            // Handle any exceptions
            System.out.println("An error occurred: " + e.getMessage());

        } finally {

            // Close the browser after the check is complete
            driver.quit();
        }
    }

    static void clickCheckBox(String CPTNumber) {
        driver.findElement(By.xpath("//*[text()='" + CPTNumber + "']/..//input")).click();
    }

    static void scrollIntoView(String xpath) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(xpath)));

    }
}