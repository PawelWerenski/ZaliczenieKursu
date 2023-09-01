package pl.coderslab.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyAccount {
    private final WebDriver driver;
    public MyAccount(WebDriver driver) {
        this.driver = driver;

    }
    public String getUserName() {
        return driver.findElement(By.cssSelector("#_mobile_user_info > div > a.account > span")).getText();
    }
    public void goToMyAddressPage() {
        driver.findElement(By.cssSelector("#footer_account_list > li:nth-child(4) > a")).click();
    }
    public void goToCreateNewAddressesPage() {
        driver.findElement(By.cssSelector("#content > div.addresses-footer > a > span")).click();
    }
}
