package listeners;

import annotioans.MaxRetryCount;
import com.relevantcodes.extentreports.LogStatus;
import extentreports.ExtentManager;
import extentreports.ExtentTestManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import templates.BaseTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestListener extends BaseTest implements ITestListener, IAnnotationTransformer {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.startTest(result.getMethod().getMethodName(), "");

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver webDriver = ((BaseTest) testClass).driver;

        String base64Screenshot = "data:image/png;base64,"
                + ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
        ExtentTestManager.getTest().log(LogStatus.FAIL,result.getThrowable().getMessage());
        ExtentTestManager.getTest().log(LogStatus.FAIL, result.getThrowable().getMessage(),
                ExtentTestManager.getTest().addScreenCapture(base64Screenshot));

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not to be implemented

    }

    @Override
    public void onStart(ITestContext context) {
        context.setAttribute("WebDriver", this.driver);
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();

    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        MaxRetryCount maxRetryCount = getMaxRetryCount(testMethod, testClass, testConstructor);
        if (maxRetryCount != null) {
            annotation.setRetryAnalyzer(RetrayAnalyzer.class);
        }

    }

    private MaxRetryCount getMaxRetryCount(Method testMethod, Class testClass, Constructor testConstructor) {
        if (testMethod != null) {
            return testMethod.getAnnotation(MaxRetryCount.class);
        }
        if (testClass != null) {
            return (MaxRetryCount) testClass.getAnnotation(MaxRetryCount.class);
        }
        if (testConstructor != null) {
            return (MaxRetryCount) testConstructor.getAnnotation(MaxRetryCount.class);
        }
        return null;
    }

}
