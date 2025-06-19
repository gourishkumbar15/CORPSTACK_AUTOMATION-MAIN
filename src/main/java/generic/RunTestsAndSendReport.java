package generic;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlTest;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunTestsAndSendReport {
    private static final Logger logger = LoggerFactory.getLogger(RunTestsAndSendReport.class);

    public static void main(String[] args) {
        try {
            logger.info("Starting test execution...");
            
            // Create TestNG instance
            TestNG testng = new TestNG();
            
            // Create XML Suite
            XmlSuite suite = new XmlSuite();
            suite.setName("Corpstack Test Suite");
            
            // Create XML Test
            XmlTest test = new XmlTest(suite);
            test.setName("Corpstack Tests");
            
            // Add test classes
            List<XmlClass> classes = new ArrayList<>();
            classes.add(new XmlClass("test_scripts.Employee.CardsTest"));
            classes.add(new XmlClass("test_scripts.Employee.ExpenseTest"));
            classes.add(new XmlClass("test_scripts.Employee.FinanceTest"));
            classes.add(new XmlClass("test_scripts.Privileged.UsersTest"));
            test.setXmlClasses(classes);
            
            // Add suite to TestNG
            List<XmlSuite> suites = new ArrayList<>();
            suites.add(suite);
            testng.setXmlSuites(suites);
            
            // Add listeners
            testng.addListener(new ReportEmailSuiteListener());
            
            // Run tests
            logger.info("Running tests...");
            testng.run();
            
            // Send report
            logger.info("Sending test report...");
            SendReport.main(new String[]{});
            
            logger.info("Test execution and report sending completed successfully.");
        } catch (Exception e) {
            logger.error("Error during test execution: {}", e.getMessage(), e);
            e.printStackTrace();
        }
    }
} 