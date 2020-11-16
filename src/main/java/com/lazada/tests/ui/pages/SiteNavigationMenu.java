package com.lazada.tests.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class SiteNavigationMenu {

    WebDriver driver;

    public SiteNavigationMenu(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getCategoryElementByText(String linkText) {
        return driver.findElement(By.linkText(linkText));
    }

    public List<WebElement> getCategoryElementsByText(String linkText) {
        return driver.findElements(By.linkText(linkText));
    }

    public void clickOnCategory(WebElement l2) {
        Actions a = new Actions(driver);
        a.moveToElement(l2).click().perform();
    }

    public void hoverOverCategory(WebElement l1) {
        Actions a = new Actions(driver);
        a.moveToElement(l1).build().perform();
    }

}
