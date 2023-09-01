package pl.coderslab.shop;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class ObjectSteps {
    private WebDriver driver;

    @Given("I'm on the shop authentication page")
    public void imOnTheShopAuthPage() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://mystore-testlab.coderslab.pl/index.php?controller=my-account");
        driver.findElement(By.className("user-info")).click();
    }

    public ObjectSteps(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ObjectSteps() {
    }


    @When("I login using {string} and {string}")
    public void iLoginUsingAnd(String login, String passwd) {
        pl.coderslab.shop.NormalThePage authPage = new pl.coderslab.shop.NormalThePage(driver);
        authPage.loginAs(login, passwd);
    }

    @And("I go to my addresses page")
    public void iGoToMyAddressPage() {
        pl.coderslab.shop.MyAccount myAccountPage = new pl.coderslab.shop.MyAccount(driver);
        myAccountPage.goToMyAddressPage();
        pl.coderslab.shop.CreatingAddressPage myAddressesPage = new pl.coderslab.shop.CreatingAddressPage(driver);
    }

    @And("I attempt to add a new address")
    public void clickingAddNewAddress() {
        driver.findElement(By.cssSelector("#content > div.addresses-footer > a > span")).click();
    }

    @And("I enter new address {word}, {string}, {string}, {word}, {word}")
    public void iEnterNewAddress(String alias, String address, String city, String postCode, String phone) {
        pl.coderslab.shop.CreatingAddressPage x = new pl.coderslab.shop.CreatingAddressPage(driver);
        x.enterNewAddress(alias, address, city, postCode, phone);
    }

    @Then("I can see a new address")
    public void iCanSeeNewAddress () {
        pl.coderslab.shop.MyAddresses myAddress = new pl.coderslab.shop.MyAddresses(driver);
        Assert.assertNotNull(myAddress.alert);
        Assert.assertTrue(myAddress.isAlertVisible());
    }

    @Then ("I verify created address has {word}, {string}, {string}, {word}, {word}")
    public void verifyAddress(String alias, String address, String city, String postCode, String phone) {
        pl.coderslab.shop.MyAddresses myAddress = new pl.coderslab.shop.MyAddresses(driver);
        String[] l = myAddress.address.findElement(By.tagName("address")).getText().split("\n");
        String aliasFromPage = myAddress.address.findElement(By.tagName("h4")).getText();
        Assert.assertEquals(aliasFromPage, alias);
        Assert.assertEquals(l[1], address);
        Assert.assertEquals(l[2], city);
        Assert.assertEquals(l[3], postCode);
        Assert.assertEquals(l[5], phone);
    }

    @And("I remove the address")
    public void deleteAddress() {
        List<WebElement> test = driver.findElements(By.xpath("//*[@data-link-action='delete-address']"));
        int length = test.size();
        test.get(length - 1).click();
    }

    @And ("I can see there is no addresses")
    public void verifyAddressIsDeleted () {
        pl.coderslab.shop.MyAddresses myAddresses = new pl.coderslab.shop.MyAddresses(driver);
        Assert.assertNotNull(myAddresses.alert);
        Assert.assertTrue(myAddresses.isAlertVisible());
        String expectedMessage = "Address successfully deleted!";
        Assert.assertEquals(expectedMessage, myAddresses.alert.getText());
    }

    @And("I close the browser")
    public void closeBrowser () {
        driver.quit();
    }
}
