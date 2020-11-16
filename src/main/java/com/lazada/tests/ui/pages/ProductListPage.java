package com.lazada.tests.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductListPage {

    WebDriver driver;

    public ProductListPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getHeader() {
        return driver.findElement(By.tagName("h1")).getText();
    }

    public List<WebElement> getProductsByProductId(String productId) {
        return driver.findElements(By.cssSelector("div[data-item-id='" + productId + "']"));
    }

}
