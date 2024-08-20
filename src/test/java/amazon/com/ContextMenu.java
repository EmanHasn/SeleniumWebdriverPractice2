package amazon.com;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class ContextMenu {
    ChromeDriver driver;

    @BeforeTest
    public void OpenUrl() {
        driver = new ChromeDriver();
        driver.navigate().to("https://swisnl.github.io/jQuery-contextMenu/demo/on-dom-element.html");
        driver.manage().window().maximize();
    }

    @Test
    public void DealWithContext() throws InterruptedException {
        WebElement rightClickBtn = driver.findElement(By.cssSelector("span.context-menu-one"));
        Actions Builder = new Actions(driver);
        Builder.contextClick(rightClickBtn).perform();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement Edit = (WebElement) js.executeScript("return document.querySelector('body > ul > li.context-menu-item.context-menu-icon.context-menu-icon-edit')");
        Edit.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @AfterMethod
    public void TakeScreenShot(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File("./ScreenShots" + result.getName() + ".png"));
        }
    }

    @AfterTest
    public void QuitUrl() {
        driver.quit();
    }

}
