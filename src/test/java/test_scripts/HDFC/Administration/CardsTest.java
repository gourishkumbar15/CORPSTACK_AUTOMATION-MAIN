package test_scripts.HDFC.Administration;

import org.testng.Assert;
import org.testng.annotations.Test;
import generic.HDFCBaseTest;
import generic.HDFC_Test_DataByPropertyFILE;
import pom_scripts.HDFCLoginPagePOM;
import pom_scripts.SignOutPOM;
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
public class CardsTest extends HDFCBaseTest {
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
            
            SignOutPOM signOutPage = new SignOutPOM(driver);
            // Login to the application
            HDFCLoginPagePOM loginPage = new HDFCLoginPagePOM(driver);
            loginPage.login(HDFC_Test_DataByPropertyFILE.getData("hdfc_username"), HDFC_Test_DataByPropertyFILE.getData("hdfc_password"));
            
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
            signOut();

        } catch (Exception e) {
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
