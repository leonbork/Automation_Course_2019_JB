package listeners;

import annotioans.MaxRetryCount;
import com.relevantcodes.extentreports.LogStatus;
import extentreports.ExtentTestManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import templates.BaseTest;

public class RetrayAnalyzer implements IRetryAnalyzer {
    int currentRetry = 0;

    @Override
    public boolean retry(ITestResult result) {
        MaxRetryCount failureRetryCount = result.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(MaxRetryCount.class);
        int maxRetryCount = (failureRetryCount == null) ? 1 : failureRetryCount.value();
        if (++currentRetry > maxRetryCount) {
            currentRetry = 0;
            result.setStatus(ITestResult.FAILURE);
            extendReportsFailOperations(result);
            return false;
        } else {
            return true;
        }
    }

    public void extendReportsFailOperations(ITestResult iTestResult) {
        Object testClass = iTestResult.getInstance();
        WebDriver webDriver = ((BaseTest) testClass).driver;
        String base64Screenshot = "data:image/png;base64,"
                + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
        ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed",
                ExtentTestManager.getTest().addScreenCapture(base64Screenshot));
    }
}
