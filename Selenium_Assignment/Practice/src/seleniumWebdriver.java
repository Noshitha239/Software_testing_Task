import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class seleniumWebdriver {
    public static void main(String[] args) {
       
        // Initialize a new ChromeDriver instance
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            // Navigate to the FitPeo homepage
            driver.get("https://www.fitpeo.com");

            // Wait for the page to load and navigate to the Revenue Calculator page
            WebDriverWait wait = new WebDriverWait(driver, 20);
            WebElement revenueCalculatorLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Revenue Calculator")));
            revenueCalculatorLink.click();

            // Wait for the Revenue Calculator page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='range']")));

            // Scroll to the slider section
            WebElement slider = driver.findElement(By.cssSelector("input[type='range']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", slider);

            // Adjust the slider to set its value to 820
            Actions move = new Actions(driver);
            move.dragAndDropBy(slider, 94, 0).perform(); 

            // Update the text field associated with the slider
            WebElement textField = driver.findElement(By.cssSelector("input[type='number']")); 
            textField.clear();
            textField.sendKeys("820");
            Thread.sleep(2000);
            // Validate slider value
            String sliderValue = slider.getAttribute("value");
            assert sliderValue.equals("560");
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='range']")));

            // Scroll to the slider section
            WebElement slider1 = driver.findElement(By.cssSelector("input[type='range']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", slider1);

            // Adjust the slider to set its value to 820
            Actions move1 = new Actions(driver);
            move1.dragAndDropBy(slider1, -39, 0).perform(); 
            WebElement textField2 = driver.findElement(By.cssSelector("input[type='number']")); 
            textField2.clear();
            textField2.sendKeys("560");
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='range']")));

            // Scroll to the slider section
            WebElement slider3 = driver.findElement(By.cssSelector("input[type='Checkbox']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", slider3);
            Thread.sleep(2000);
         // Ensure that the correct value is set in the text field and the slider reflects this value.
            assert textField2.getAttribute("value").equals("560");
            assert slider.getAttribute("value").equals("560");

            // Scroll down further to find the CPT code checkboxes
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String[] cptCodes = {"CPT-99091", "CPT-99453", "CPT-99454", "CPT-99474"};
            for (String cptCode : cptCodes) {
                WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='checkbox']")));
                js.executeScript("arguments[0].scrollIntoView(true);", checkbox);
                if (!checkbox.isSelected()) {
                    checkbox.click();
                }
            }

            // Validate Total Recurring Reimbursement value
            WebElement totalReimbursementHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(), 'Total Recurring Reimbursement for all Patients Per Month:')]")));
            String reimbursementText = totalReimbursementHeader.getText();
            if (reimbursementText.contains("$110700")) {
                System.out.println("Total Recurring Reimbursement is correctly displayed as $110700.");
            } else {
                System.out.println("Total Recurring Reimbursement is not displayed correctly. Found: " + reimbursementText);
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}