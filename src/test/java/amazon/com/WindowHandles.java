package amazon.com;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;

public class WindowHandles {
    ChromeDriver driver;

    @BeforeTest
    public void openUrl() {
        driver = new ChromeDriver();
        driver.navigate().to("https://www.seleniumacademy.com/cookbook/Config.html");
        driver.manage().window().maximize();
    }

    @Test
    public void handleWindows() {
        String currentWin = driver.getWindowHandle();
        WebElement btn = driver.findElement(By.id("visitbutton"));
        btn.click();
        for (String singlewindow : driver.getWindowHandles()) {
            String WinTitle = driver.switchTo().window(singlewindow).getTitle();
            if (WinTitle.equals("Visit Us")) {
                Assert.assertEquals("Visit Us", driver.getTitle());
                System.out.println(driver.findElement(By.tagName("p")).getText());
                driver.close();
                break;
            }

        }
        driver.switchTo().window(currentWin);
        System.out.println(driver.findElement(By.tagName("h2")).getText());
    }

    @AfterMethod
    public void ScreenShot(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File("./ScreenShots/" + result.getName() + ".png"));
        }
    }

    @AfterTest
    public void closeWebsite() {
        driver.quit();
    }
}