package utils;

import com.relevantcodes.extentreports.LogStatus;
import extentreports.ExtentTestManager;
import org.testng.Reporter;

/**
 * Lazy implementation of log
 */
public class LogTest {

    public static void log(String message) {
        ExtentTestManager.getTest().log(LogStatus.INFO, message);
        Reporter.log(message);
        System.out.println(message);

    }
}