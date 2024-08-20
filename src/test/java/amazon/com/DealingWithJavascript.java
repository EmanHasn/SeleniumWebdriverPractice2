package amazon.com;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class DealingWithJavascript {
    ChromeDriver driver;

    @BeforeTest
    public void openUrl() {
        driver = new ChromeDriver();
        driver.navigate().to("https://the-internet.herokuapp.com/javascript_alerts");
        driver.manage().window().maximize();
    }

    @Test
    public void FindPageName() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement btn = (WebElement) js.executeScript("return document.querySelector('#content > div > ul > li:nth-child(1) > button') ; ");
        btn.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        WebElement alertTxt = (WebElement) js.executeScript("return document.querySelector('#result');");
        System.out.println(alertTxt.getText());
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
    public void QuitUrl() {
        driver.quit();
    }
}
