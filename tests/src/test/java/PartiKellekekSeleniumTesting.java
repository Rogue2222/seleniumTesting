import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

import org.openqa.selenium.support.ui.*;


import org.junit.*;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PartiKellekekSeleniumTesting {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators    complex xpath 14
    private final By cookieAcceptLocator = By.xpath("//button[@class='cookie-alert__btn-allow btn btn-primary icon--a-check m-2']");
    private final By profileBeforeLoginLocator = By.xpath("//div[@class='header__top-right col-auto col-xl d-flex justify-content-end align-items-center']/button[@class='profile__dropdown-btn js-profile-btn btn dropdown__btn']");
    private final By emailInputLocator = By.xpath("//div[@class='form-group login-box__input-field']/input[@id='shop_user_login']");
    private final By passwordInputLocator = By.xpath("//div[@class='form-group login-box__input-field mb-4']/input[@id='shop_pass_login']");
    private final By loginButtonLocator = By.xpath("//div[@class='login-box__btns-wrap form-group form-group-lg']/button[@type='submit']");
    private final By profileButtonLocator = By.xpath("//div[@class='header__top-right col-auto col-xl d-flex justify-content-end align-items-center']/button[@id='profile__dropdown-btn']");
    private final By changeDataButtonLocator = By.xpath("//li/a[@class='login-box-btn login-box__btn-reg btn btn-secondary' and contains(text(), 'Adat')]");
    private final By exitButtonLocator = By.xpath("//div/a[@class='login-box-btn login-box__btn-logout btn btn-primary']");
    private final By infoElementLocator = By.tagName("h1");

    private final By passwordChangeInputLocator = By.xpath("//div[@class='form-group new-customer__password']/input[@id='passwd1']");
    private final By passwordChangeConfirmationInputLocator = By.xpath("//div[@class='form-group new-customer__password-again']/input[@id='passwd2']");
    private final By postalCodeLocator = By.xpath("//div[@class='form-group new-customer__default-zip']/input[@id='default_irany']");
    private final By termsCheckboxLocator = By.xpath("//div[@class='custom-control custom-checkbox']/label[@for='terms']");
    private final By changeButtonLocator = By.xpath("//div/button[@id='button_mod']");
    private final By okAlertLocator = By.xpath("//div[@class='reg-ok-alert alert alert-success icon--b-check']");


    private WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    private void acceptCookies() {
        WebElement cookieAccept = waitAndReturnElement(cookieAcceptLocator);
        cookieAccept.click();
    }

    // WebDriver configuration
    @Before
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        this.driver.manage().window().maximize();
        this.wait = new WebDriverWait(this.driver, 10);
    }

    // Static Page test, Reading the page title, Explicit wait, Filling or reading textarea content
    @Test
    public void testStaticHomePage() {
        this.driver.get("https://www.partikellekek.hu/shop_contact.php");

        WebElement infoElement = waitAndReturnElement(infoElementLocator);
        String title = this.driver.getTitle();
        String infoElementText = infoElement.getText();

        assertTrue("Title should contain 'Inform?ci?k - Parti kell?kek sz?linapra, l?nyb?cs?ra, leg?ny'", title.contains("Parti"));
        assertTrue("The featuredOfferText should be 'Inform?ci?k'", infoElementText.contains("INFO"));
    }

    // Fill simple form and send (eg. Login)   Fill input 2
    private void login() {
        takeScreenshot("loginBefore");
        WebElement profileBeforeLogin = waitAndReturnElement(profileBeforeLoginLocator);
        profileBeforeLogin.click();

//      email: stefon@ereirqu.com
//      pass: test_1Selenium@

        // Fill email field
        WebElement emailInput = waitAndReturnElement(emailInputLocator);
        emailInput.sendKeys("stefon@ereirqu.com");

        WebElement passwordInput = waitAndReturnElement(passwordInputLocator);
        passwordInput.sendKeys("test_1Selenium@");

        // Press Login button
        WebElement loginButton = waitAndReturnElement(loginButtonLocator);
        loginButton.click();
    }

    @Test
    public void testPartyKellekekLogin() {
        this.driver.get("https://www.partikellekek.hu");

        acceptCookies();

        login();

        // Press the profile button again
        WebElement profileButton = waitAndReturnElement(profileButtonLocator);
        profileButton.click();

        // Now we should be able to see the change data button
        WebElement changeDataButton = waitAndReturnElement(changeDataButtonLocator);

        assertTrue("Change data button should be enabled after login", changeDataButton.isEnabled());
    }

    // Logout
    @Test
    public void testPartyKellekekLogout() {
        this.driver.get("https://www.partikellekek.hu");

        acceptCookies();

        login();

        WebElement profileButton = waitAndReturnElement(profileButtonLocator);
        profileButton.click();


        WebElement exitButton = waitAndReturnElement(exitButtonLocator);
        exitButton.click();


        WebElement profileBeforeLogin = waitAndReturnElement(profileBeforeLoginLocator);
        profileBeforeLogin.click();

        WebElement emailInput = waitAndReturnElement(emailInputLocator);

        assertTrue("Email input should be enabled after login", emailInput.isDisplayed());
    }

   // Fill input 3, WebDriver configuration, Filling or reading Radio button, Form sending with user
    @Test
    public void testSendigForm() {
        this.driver.get("https://www.partikellekek.hu");

        acceptCookies();

        login();

        WebElement profileButton = waitAndReturnElement(profileButtonLocator);
        profileButton.click();

        WebElement changeDataButton = waitAndReturnElement(changeDataButtonLocator);
        changeDataButton.click();

        // Confirm password
        WebElement passwordChangeInput = waitAndReturnElement(passwordChangeInputLocator);
        passwordChangeInput.sendKeys("test_1Selenium@");

        WebElement passwordChangeConfirmationInput = waitAndReturnElement(passwordChangeConfirmationInputLocator);
        passwordChangeConfirmationInput.sendKeys("test_1Selenium@");

        // JavaScript scroll down
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 500)");

        WebElement postalCode = waitAndReturnElement(postalCodeLocator);
        postalCode.clear();
        postalCode.sendKeys("0000");

        js.executeScript("window.scrollTo(0, 500)");

        // Radio button (accept the terms)
        WebElement termsCheckbox = waitAndReturnElement(termsCheckboxLocator);
        termsCheckbox.click();

        // Click the change button
        WebElement changeButton = waitAndReturnElement(changeButtonLocator);
        changeButton.click();

        WebElement okAlert = waitAndReturnElement(okAlertLocator);

        assertTrue("Alert should be displayed after form submission", okAlert.isDisplayed());
        assertTrue("Alert text should contain 'Adataid sikeresen megv?ltoztattuk!'", okAlert.getText().contains("sikeres"));
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    // Some helper functions for taking screenshots and logging page source
    private void takeScreenshot(String name) {
        try {
            Path path = Paths.get(System.getProperty("user.dir"), "screenshots", name + ".png");
            Files.createDirectories(path.getParent());

            if (Files.exists(path)) {
                Files.delete(path);
            }

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), path);
        } catch (IOException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    private void logPageSource() {
        logPageSource("page_source.html", 0);
    }

    private void logPageSource(String filename, int truncateLength) {
        try {
            String pageSource = driver.getPageSource();
            String timestamp = new java.util.Date().toString();

            // Truncate for console
            String truncated = pageSource.length() > truncateLength
                    ? pageSource.substring(0, truncateLength) + "... [TRUNCATED]"
                    : pageSource;

            // Console log
            System.out.printf("[%s] Page Source Preview (%d chars):\n%s\n",
                    timestamp,
                    pageSource.length(),
                    truncated);

            // Save full source to file
            java.nio.file.Files.write(
                    java.nio.file.Paths.get(filename),
                    pageSource.getBytes()
            );
            System.out.println("Full page source saved to: " + filename);

        } catch (Exception e) {
            System.out.println("Could not save page source: " + e.getMessage());
        }
    }

}