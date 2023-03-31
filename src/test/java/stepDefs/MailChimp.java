package stepDefs;

import io.cucumber.java.After;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import static org.junit.Assert.assertEquals;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class MailChimp {

    private WebDriver driver;
    private WebDriverWait wait;
    @Given("i test in {string}")
    public void iTestInBrowser(String browser) {

        if(browser.equalsIgnoreCase("chrome")){

            System.setProperty("webdriver.chrome.driver", "C:\\selenium\\chromedriver.exe");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.get("https://login.mailchimp.com/signup/");

        } else if (browser.equalsIgnoreCase("edge")) {

            System.setProperty("webdriver.edge.driver", "C:\\selenium\\msedgedriver.exe");
            driver = new EdgeDriver();
            driver.get("https://login.mailchimp.com/signup/");
        }
    }
    @And("i set {string}")
    public void isetMail(String mail) {
        driver.findElement(By.id("email")).sendKeys(mail);
    }

    @And("also set {int}")
    public void alsosetUsername(int username) {
        driver.findElement(By.id("new_username")).click();
        driver.findElement(By.id("new_username")).clear();

        if(username >= 1){
            String chara = "abcdefghijklmnopqrs";
            String rando = "";
            Random random = new Random();
            char[] newname = new char[username];
            for(int i = 0; i < username; i++){
                newname[i] = chara.charAt(random.nextInt(chara.length()));
            }
            for (int i = 0; i < newname.length; i++) {
                rando += newname[i];
            }
            driver.findElement(By.id("new_username")).sendKeys("hej" + rando);
        } else {
            driver.findElement(By.id("new_username")).sendKeys("CHi");
        }
    }
    @And("set {string}")
    public void setPassword(String password) {
        driver.findElement(By.id("new_password")).sendKeys(password);
    }
    @When("i press the button")
    public void iPressTheButton() {
        driver.findElement(By.cssSelector(".dijit")).click();
        driver.findElement(By.id("create-account-enabled")).click();
        driver.findElement(By.id("create-account-enabled")).click();
    }
    @Then("i get {string} and {string} this outcome")
    public void iGetResultAndExpectThisOutcome(String result,String expect) {
        if (result.equalsIgnoreCase("successes")) {
            waitDisplay(By.cssSelector(".\\!margin-bottom--lv3"));
            WebElement successes = driver.findElement(By.cssSelector(".\\!margin-bottom--lv3"));
            String actual = successes.getText();
            String expected = expect;

            assertEquals(expected,actual);

        } else if(result.equalsIgnoreCase("fail")){

            waitDisplay(By.cssSelector("[class='invalid-error']"));
            WebElement fail = driver.findElement(By.cssSelector("[class='invalid-error']"));
            String failmess = fail.getText();

            if(failmess.equals("Enter a value less than 100 characters long")){

                String expected = expect;
                String actual = failmess;
                assertEquals(expected,actual);

            } else if (failmess.equals("Great minds think alike - someone already has this username. If it's you, log in.")) {

                String expected = expect;
                String actual = failmess;
                assertEquals(expected,actual);

            } else if (failmess.equals("An email address must contain a single @.")) {

                String expected = expect;
                String actual = failmess;
                assertEquals(expected,actual);
            }
        }
    }
    @After
    public void tearDown(){
        driver.close();
        driver.quit();

    }
    private boolean waitDisplay(By by){
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        return element.isDisplayed();
    }
}
