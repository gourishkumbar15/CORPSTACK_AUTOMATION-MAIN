package test_scripts.Administration;

import org.testng.Assert;
import org.testng.annotations.Test;
import generic.BaseTest;
import pom_scripts.Administration.CardsPagePOM;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

/**
 * Test class for Administration Cards functionality
 */
public class CardsTest extends BaseTest {
    /**
     * Test case for exporting assigned cards
     */
    @Test(description = "Export assigned cards", testName = "TC052_Export_Assigned_Cards", enabled = true)
    @Description("Export assigned cards")
    @Epic("Administration")
    @Feature("Assigned cards")
    @Story("Export assign cards")
    @Severity(SeverityLevel.NORMAL)
    public void testExportAssignedCards() {
        try {
            // Create page object
            CardsPagePOM cardsPage = new CardsPagePOM(driver);
            
            // Test steps
            cardsPage.clickPrivilegedLink();
            cardsPage.clickPALink();
            cardsPage.clickAdministrationLink();
            cardsPage.clickCardsLink();
            cardsPage.clickAssignedCardsLink();
            cardsPage.clickExportIcon();
            cardsPage.clickDownloadButton();
            
            // Verify toast message appears
            Assert.assertTrue(cardsPage.getToastMessageText().length() > 0, "Toast message should appear after download");
            
            // Close toast message
            cardsPage.clickCloseToastMessage();
            
            // Report success
            reportPass("TC052_Export_Assigned_Cards", "Successfully exported assigned cards");
        } catch (Exception e) {
            reportFail("TC052_Export_Assigned_Cards", "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
