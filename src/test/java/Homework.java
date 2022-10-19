import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Homework {

    static final String CHROMEDRIVER_PATH = "/Users/shirazelbaz/Downloads/chromedriver";
    private static ChromeDriver driver;
    private static NgWebDriver ngWebDriver;

    @BeforeClass
    public void beforeAll(){
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
        driver = new ChromeDriver();
        ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://dgotlieb.github.io/Selenium/synchronization.html");
    }

    @Test
    public void test01_implicit(){
        System.out.println(driver.findElement(By.id("checkbox")).isDisplayed());
        driver.findElement(By.id("btn")).click();
        System.out.println(driver.findElement(By.id("message")).isDisplayed());
    }

    @Test
    public void test02_threadSleep() throws InterruptedException {
        driver.findElement(By.id("hidden")).click();
        Thread.sleep(3000);
        System.out.println(driver.findElement(By.id("finish1")).isDisplayed());
    }

    @Test
    public void test03_explicit(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.findElement(By.id("rendered")).click();
        String reavledText = driver.findElement(By.id("finish2")).getText();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish2")));
        Assert.assertEquals(reavledText, "This is a new element");
    }

    @Test
    public void test04_angularJS(){
        driver.navigate().to("https://dgotlieb.github.io/AngularJS/main.html");
        ngWebDriver.waitForAngularRequestsToFinish();
        WebElement firstName = driver.findElement(ByAngular.model("firstName"));
        firstName.clear();
        firstName.sendKeys("shizilizabeth");
        String toAssert = driver.findElement(ByAngular.binding("firstName")).getText();
        Assert.assertEquals(toAssert, "shizilizabeth");
    }

    // 3--> PageLoadTimeOut is used to wait until selenium will load the full page

}
