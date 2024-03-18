import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Set;

public class example {

    public static WebDriver driver;


    @Before
    public void setup(){

        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*").addArguments("headless browser");
        DesiredCapabilities cp = new DesiredCapabilities();
        cp.setCapability(ChromeOptions.CAPABILITY, options);
        options.merge(cp);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");


    }


    @Test
    public void ValidateTitle(){
        String title = driver.getTitle();
        Assert.assertTrue(title.equals("Swag Labs"));
        System.out.println(title);

    }
    @Test
    public void useActionClass(){
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.id("user-name"));
        actions.moveToElement(element).click(element)
                .sendKeys(element, "standard_user")
                .doubleClick(element)
                .contextClick(element).build().perform();

    }
    @Test
    public void useAlertMethods(){

        driver.navigate().to("https://only-testing-blog.blogspot.com/2014/01/textbox.html");
        driver.findElement(By.xpath("//input[@value='Show Me Alert']")).click();
        driver.switchTo().alert().accept();
    }
    @Test
    public void useMultipleWindows(){
        driver.navigate().to("https://www.naukri.com");
        String mainWindow = driver.getWindowHandle();


        driver.switchTo().window(mainWindow);
        driver.findElement(By.id("qsbWrapper")).sendKeys("Automation Testing");
        driver.findElement(By.xpath("//button[text()='Search']")).click();
    }
    @Test
    public void useIframes(){
        driver.navigate().to("https://the-internet.herokuapp.com/iframe");
        driver.switchTo().frame("mce_0_ifr");
        driver.findElement(By.xpath("//body[@id='tinymce']/p")).sendKeys("Testing iFrame");
    }
    //@Test
    public void useJSExecutor(){

        WebElement user = driver.findElement(By.id("user-name"));
        WebElement pass = driver.findElement(By.id("password"));
        WebElement login = driver.findElement(By.id("login-button"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("argument[0].click();", login);
        String title = js.executeScript("return document.title;").toString();
        String url = js.executeScript("return document.URL;").toString();

        js.executeScript("window.scrollBy(0,600)");
    }


    @Test
    public void validatelogin(){



        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        String font = driver.findElement(By.id("user-name")).getCssValue("font-family");
        System.out.println(font);
        String getText = driver.findElement(By.id("login-button")).getAttribute("value");
        Assert.assertEquals("Login", getText);
        System.out.println(getText);
        if (driver.findElement(By.id("login-button")).isDisplayed()){
            driver.findElement(By.id("login-button")).click();
            System.out.println("This site works");
        }
        else {
            System.out.println("This site does not work");
        }

        String url = driver.getCurrentUrl();
        Assert.assertEquals("https://www.saucedemo.com/inventory.html", url);
    }


    @After
    public void close(){
        //driver.quit();
    }

}
