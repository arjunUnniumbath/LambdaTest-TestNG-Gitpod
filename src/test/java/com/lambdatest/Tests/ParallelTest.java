package com.lambdatest.Tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream; 
import javax.imageio.ImageIO;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.remote.LocalFileDetector;

public class ParallelTest {

	// Lambdatest Credentails can be found here at https://www.lambdatest.com/capabilities-generator
	String username = System.getenv("LT_USERNAME") == null ? "arjun1092" : System.getenv("LT_USERNAME"); 
	String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "XydIwxgzRg5lgABNonlvXQiqfRHUuriio6PDToN2B2eHz508r0" : System.getenv("LT_ACCESS_KEY");

    public static WebDriver driver;
    WebDriverWait wait =null;

	@BeforeTest(alwaysRun = true)
	@Parameters(value = { "browser", "version", "platform" })
	protected void setUp(final String browser, final String version, final String platform) throws Exception {
        final DesiredCapabilities capabilities = new DesiredCapabilities();

        // set desired capabilities to launch appropriate browser on Lambdatest
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        capabilities.setCapability(CapabilityType.VERSION, version);
        capabilities.setCapability(CapabilityType.PLATFORM, platform);
        capabilities.setCapability("build", "TestNG Parallel");
        capabilities.setCapability("name", "TestNG Parallel");
        capabilities.setCapability("network", true);
        capabilities.setCapability("video", true);
        capabilities.setCapability("console", true);
        capabilities.setCapability("visual", true);

        System.out.println("capabilities" + capabilities);

        // Launch remote browser and set it as the current thread
        final String gridURL = "https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";
        try {
            driver = new RemoteWebDriver(new URL(gridURL), capabilities);
             ((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());

        } catch (final Exception e) {
            System.out.println("driver error");
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void test() throws Exception {
        // explicit wait object
        try {
            wait = new WebDriverWait(driver, 30);
             JavascriptExecutor js = (JavascriptExecutor) driver;
          
           // login to application
            driver.get("https://www.lambdatest.com/automation-demos/");
             List<WebElement>cookiesList=driver.findElements(By.xpath("//div[@class='cookiesdiv']//div/a"));
           if(cookiesList.size()!=0)
            cookiesList.get(0).click(); 
            driver.findElement(By.id("username")).sendKeys("lambda");
            driver.findElement(By.id("password")).sendKeys("lambda123");
            WebElement element_sbmt = driver.findElement(By.xpath("//button[contains(text(),'Login')]"));
            js.executeScript("arguments[0].scrollIntoView();", element_sbmt);
          js.executeScript("arguments[0].click();", element_sbmt);
            //driver.findElement(By.xpath("//button[contains(text(),'Login')]")).click();
           
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("developer-name")));
            driver.findElement(By.id("developer-name")).sendKeys("arjun1092@gmail.com");
            driver.findElement(By.id("populate")).click();

            // Switching to Alert
            Alert alert = driver.switchTo().alert();
            // Capturing alert message.
            String alertMessage = driver.switchTo().alert().getText();
            // Displaying alert message
            System.out.println("************"+alertMessage);
            // Accepting alert
            alert.accept();

            driver.findElement(By.cssSelector("#month")).click();
            driver.findElement(By.xpath("//input[@id='discounts']")).click();
           WebElement cardDpdwn=driver.findElement(By.cssSelector("#preferred-payment"));
               js.executeScript("arguments[0].scrollIntoView();", cardDpdwn);
            // select valuye from dropdown
             Select dpdwn = new Select(cardDpdwn); 

            dpdwn.selectByVisibleText("Credit or Debit card");

            driver.findElement(By.cssSelector("#tried-ecom")).click();

            // move slider to 9
            
             WebElement slider = driver.findElement(By.xpath("//div[@id='slider']/span"));

            js.executeScript("arguments[0].setAttribute('style', 'left: 88.8889%;')", slider);

            // open new tab and download image

            js.executeScript("window.open('https://www.lambdatest.com/selenium-automation')");

            final ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
           // driver.get("https://www.lambdatest.com/selenium-automation");

             WebElement Element = driver
                    .findElement(By.xpath("//h2[contains(text(),'Integrations With CI/CD Tools')]"));
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Integrations With CI/CD Tools')]")));
            js.executeScript("arguments[0].scrollIntoView();", Element);

            // download image

             WebElement logo = driver.findElement(By.xpath("//a/img[@alt='LambdaTest Jenkins integration']"));
             String logoSRC = logo.getAttribute("src");
             System.out.println("Image url: " +logoSRC);
             URL imageURL = new URL(logoSRC);
             File n = new File("jenkins.svg");
            InputStream is = imageURL.openStream();
             OutputStream os = new FileOutputStream(n);
              byte[] b = new byte[2048];
             int length;
            while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
             }
            
            //  BufferedImage saveImage = ImageIO.read(imageURL);

   
            // ImageIO.write(saveImage, "png", n);
            String FilePath=n.getAbsolutePath().toString();
             System.out.println("file url: " +FilePath);
            //switch to parent tab
            driver.switchTo().window(tabs.get(0));
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//label[@id='img']")));
        
                    WebElement addFile = driver.findElement(By.xpath("//input[@id='file']"));
            addFile.sendKeys(FilePath);
          
        //  addFile.sendKeys("\\workspace\\LambdaTest-TestNG-Gitpod\\jenkins.svg");
            // close the file sucess pop up

            alert = driver.switchTo().alert();
            // Capturing alert message.
            alertMessage = driver.switchTo().alert().getText();
            // Displaying alert message
            System.out.println("*******"+alertMessage);
            Assert.assertTrue(alertMessage.contains("sucessfully!!"));
            // Accepting alert
            alert.accept();

            // click on submit buttton
            WebElement submitbtn=driver.findElement(By.cssSelector("#submit-button"));
            js.executeScript("arguments[0].scrollIntoView();", submitbtn);
            js.executeScript("arguments[0].click();", submitbtn);
            //driver.findElement(By.cssSelector("#submit-button")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='message']")));
            final String message = driver.findElement(By.xpath("//div[@id='message']")).getText();
            Assert.assertTrue(message.contains("successfully"));
        } catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	@AfterTest(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
	}

}
