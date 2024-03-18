import com.beust.jcommander.Parameters;
import net.bytebuddy.build.Plugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.WebDriver;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;




public class Safariweb {
    public WebDriver driver;


    @Before

    public void setup() {

        driver = new SafariDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.MILLISECONDS);
        driver.get("https://www.saucedemo.com/");

    }

    @Test(timeout = 30)
    public void ValidateTitle(){
        String title = driver.getTitle();
        Assert.assertEquals("Swag Labs", title);

        System.out.println(title);

    }


    @Test

    public void validatelogin(){

        //Explicit Wait
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));



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


    @Test
    public void selectvalue(){
        validatelogin();
        WebElement element = driver.findElement(By.xpath("//*[@class='product_sort_container']"));
        Select select = new Select(element);
        select.selectByIndex(3);
        if (select.isMultiple()){
            System.out.println("Multiple Select Dropdown");
        }
        else{
            System.out.println("Single Select Dropdown");
        }

    }
    @Test
    public void selectmultiplevalues(){
        driver.navigate().to("https://only-testing-blog.blogspot.com/2014/01/textbox.html");
        WebElement ele = driver.findElement(By.name("FromLB"));
        Select sel = new Select(ele);
        if (sel.isMultiple()){
            System.out.println("Multiple Select Dropdown");
        }
        else{
            System.out.println("Single Select Dropdown");
        }
        sel.selectByIndex(1);
        sel.selectByIndex(2);
        sel.selectByIndex(3);
        List<WebElement> opt = sel.getAllSelectedOptions();
        for (WebElement element: opt){
            System.out.println(element.getAttribute("value"));
        }

    }
    @Test
    public void useXpathmethods(){
        driver.findElement(By.xpath("//input[contains(@id,'user')]")).sendKeys("problem_user");
        driver.findElement(By.xpath("//input[starts-with(@id,'pass')]")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id ='login-button' and @name='login-button']")).click();

        driver.findElement(By.xpath("//div[text()='Sauce Labs Backpack']")).isDisplayed();


    }
    @Test
    public void useXpathAxes(){
        driver.findElement(By.xpath("//div[@class='login-box']//following::input[2]")).click();
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
        driver.findElement(By.xpath("//button[text()='Show Me Prompt']")).click();
        driver.switchTo().alert().sendKeys("Kingsley");
        String alert = driver.switchTo().alert().getText();
        System.out.println(alert);
    }
    //@Test
    public void useMultipleWindows(){
        driver.navigate().to("https://www.naukri.com");
        String mainWindow = driver.getWindowHandle();

        Set<String> handles = driver.getWindowHandles();

        for (String handle: handles){
            if (!mainWindow.equals(handle)){
                driver.switchTo().window(handle);
                System.out.println(driver.switchTo().window(handle).getTitle());
                driver.close();
            }
        }
        driver.switchTo().window(mainWindow);
        driver.findElement(By.xpath("//div[@class='keywordSugg']")).sendKeys("Automation testing");
        driver.findElement(By.xpath("//button[text()='Search']")).click();
    }


    @Test
    public void usecookiesMethod(){
        validatelogin();

        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println(cookies);

        Cookie user = driver.manage().getCookieNamed("session-username");
        System.out.println(user);

        driver.manage().deleteCookie(user);

        Cookie custom = new Cookie("password","pass");
        driver.manage().addCookie(custom);
        System.out.println(driver.manage().getCookieNamed("password"));
    }

    @Test

    public void useWebtables(){

        driver.navigate().to("https://only-testing-blog.blogspot.com/2014/01/textbox.html");
        driver.findElement(By.xpath("//table[@cellpadding='10']//tr[3]//td[6]//input[@id='submitButton']")).click();

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


    @After
    public void close(){
        //driver.quit();
    }




}


