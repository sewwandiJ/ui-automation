package browsing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;

import static utils.Constants.CHROME_DRIVER;
import static utils.Constants.URL;

public class NavigationTest {

    Object[][] excelData;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", Paths.get(CHROME_DRIVER).toString());
        excelData = TestUtils.getProductBrowsingTestData();
    }

    @DataProvider(name = "productBrowsing", parallel = true)
    public Object[][] testProductBrowsing() {
        return excelData;
    }

    @DataProvider(name = "categoryNavigation", parallel = true)
    public Object[][] categoryNavigation() {
        return TestUtils.getCategoryNavigationTestData(excelData);
    }

    @Test(dataProvider = "productBrowsing")
    public void testProductBrowsing(String level1, String level2, String level3, String expectedHeader, List<String> productIds) {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to(URL);
        WebElement l1 = driver.findElement(By.linkText(level1));
        Actions a = new Actions(driver);
        hover(l1, a);

        WebElement l2 = driver.findElement(By.linkText(level2));
        if (level3 == null) {
            click(a, l2);
        } else {
            hover(l2, a);
            WebElement l3 = driver.findElement(By.linkText(level3));
            click(a, l3);
        }

        String actualHeader = driver.findElement(By.tagName("h1")).getText();
        Assert.assertEquals(actualHeader, expectedHeader, "Page header doesn't match");

        productIds.forEach(productId -> {
                    String productIdCss = "div[data-item-id='" + productId + "']";
                    int productOccurrences = driver.findElements(By.cssSelector(productIdCss)).size();
                    Assert.assertEquals(productOccurrences, 1, "Product found : " + productId);
                }
        );

        driver.close();
    }

    @Test(dataProvider = "categoryNavigation")
    public void testCategoryNavigation(String category, HashSet<String> expectedCategories) {

        List<String> navigationSteps = TestUtils.split(category, ":");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to(URL);
        WebElement l1 = driver.findElement(By.linkText(navigationSteps.get(0)));
        Actions a = new Actions(driver);
        hover(l1, a);

        if (navigationSteps.size() == 2) {
            WebElement l2 = driver.findElement(By.linkText(navigationSteps.get(1)));
            hover(l2, a);
        }

        for (String expectedCategory : expectedCategories) {
            int categoryOccurrences = driver.findElements(By.linkText(expectedCategory)).size();
            Assert.assertEquals(categoryOccurrences, 1, "Category found : " + expectedCategory);
        }

        driver.close();
    }

    private void click(Actions a, WebElement l2) {
        a.moveToElement(l2).click().perform();
    }

    private void hover(WebElement l1, Actions a) {
        a.moveToElement(l1).build().perform();
    }
}
