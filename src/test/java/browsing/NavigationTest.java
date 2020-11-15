package browsing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.ProductListPage;
import pages.SiteNavigationMenu;
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
    public void beforeClass() {
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

        SiteNavigationMenu siteNavigationMenu = new SiteNavigationMenu(driver);
        WebElement l1 = siteNavigationMenu.getCategoryElementByText(level1);
        siteNavigationMenu.hoverOverCategory(l1);

        WebElement l2 = siteNavigationMenu.getCategoryElementByText(level2);
        if (level3 == null) {
            siteNavigationMenu.clickOnCategory(l2);
        } else {
            siteNavigationMenu.hoverOverCategory(l2);
            WebElement l3 = siteNavigationMenu.getCategoryElementByText(level3);
            siteNavigationMenu.clickOnCategory(l3);
        }

        ProductListPage productListPage = new ProductListPage(driver);

        String actualHeader = productListPage.getHeader();
        Assert.assertEquals(actualHeader, expectedHeader, "Page header doesn't match");

        productIds.forEach(productId -> {
                    int productOccurrences = productListPage.getProductsByProductId(productId).size();
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

        SiteNavigationMenu siteNavigationMenu = new SiteNavigationMenu(driver);
        WebElement l1 = siteNavigationMenu.getCategoryElementByText(navigationSteps.get(0));
        siteNavigationMenu.hoverOverCategory(l1);

        if (navigationSteps.size() == 2) {
            WebElement l2 = siteNavigationMenu.getCategoryElementByText(navigationSteps.get(1));
            siteNavigationMenu.hoverOverCategory(l2);
        }

        for (String expectedCategory : expectedCategories) {
            int categoryOccurrences = siteNavigationMenu.getCategoryElementsByText(expectedCategory).size();
            Assert.assertEquals(categoryOccurrences, 1, "Category found : " + expectedCategory);
        }

        driver.close();
    }


}
