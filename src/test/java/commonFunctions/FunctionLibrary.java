package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
public static WebDriver driver;
public static Properties conpro;
	
	//Method for launching any browser
	public static WebDriver startBrowser() throws Throwable
	{
		conpro =new Properties();
		conpro.load(new FileInputStream("./PropertyFiles\\Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();	
		}
		else
		{
			Reporter.log("Browser value is not matching",true);
		}
		return driver;
	}
	//Method for launching any URL
	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));
	}
	//method for wait for any web element 
	public static void waitForElement(String LocatorType,String LocatorValue, String TestData)
	{
		WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
	    if(LocatorType.equalsIgnoreCase("xpath"))
	    {
	    	mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
	    }
	    if(LocatorType.equalsIgnoreCase("id"))
	    {
	    	mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	    }
	    if(LocatorType.equalsIgnoreCase("name"))
	    {
	    	mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
	    }
	}
	
	//Method for any text box
	public static void typeAction(String LocatorType,String LocatorValue, String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
	}
	//method for click any elements like buttons,images,links,check boxes and radio buttons
    public static void clickAction(String LocatorType, String LocatorValue)
    {
    	if(LocatorType.equalsIgnoreCase("xpath"))
    	{
    		driver.findElement(By.xpath(LocatorValue)).click();
    	}
    	if(LocatorType.equalsIgnoreCase("id"))
    	{
    		driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
    	}
    	if(LocatorType.equalsIgnoreCase("name"))
    	{
    		driver.findElement(By.name(LocatorValue)).click();
    	}
    }
    
    //method for validating title
    public static void validateTitle(String Expected_title)
    {
    	String Actual_title=driver.getTitle();
    	try {
    	Assert.assertEquals(Actual_title, Expected_title, "Title is not matching");
    	}catch(Throwable t)
    	{
    		System.out.println(t.getMessage());
    	}
    }
    //Method for closing browser
    public static void closeBrowser()
    {
    	driver.quit();
    }
    //Method for generate date
    public static String generateDate()
    {
    	Date date=new Date();
    	DateFormat df=new SimpleDateFormat("YYYY_MM_DD HH_MM_SS");
    	return df.format(date);   	
    }

    //   STOCK ITEM MODULE
    //Method for selecting items in list boxes
    public static void dropDownAction(String LocatorType, String LocatorValue, String TestData)
    {
    	if(LocatorType.equalsIgnoreCase("name"))
    	{
    		int value=Integer.parseInt(TestData);
    		Select element=new Select(driver.findElement(By.name(LocatorValue)));
    		element.selectByIndex(value);
    	}
    	if(LocatorType.equalsIgnoreCase("xpath"))
    	{
    		int value=Integer.parseInt(TestData);
    		Select element=new Select(driver.findElement(By.xpath(LocatorValue)));
    		element.selectByIndex(value);
    	}
    	if(LocatorType.equalsIgnoreCase("id"))
    	{
    		int value=Integer.parseInt(TestData);
    		Select element=new Select(driver.findElement(By.id(LocatorValue)));
    		element.selectByIndex(value);
    	}
    }
    //Method for capture stock numbers into note pad
    public static void captureStock(String LocatorType, String LocatorValue) throws Throwable
    {
    	String stockNum="";
    	if(LocatorType.equalsIgnoreCase("name"))
    	{
    		stockNum=driver.findElement(By.name(LocatorValue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("xpath"))
    	{
    		stockNum=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("id"))
    	{
    		stockNum=driver.findElement(By.id(LocatorValue)).getAttribute("value");
    	}
    	//Create new Notepad into captureData folder
    	FileWriter fw=new FileWriter("./CaptureData/stockNumber.txt");
    	BufferedWriter bw=new BufferedWriter(fw);
    	bw.write(stockNum);
    	bw.flush();
    	bw.close();
    }
    //Verify stock number from stock table
    public static void stockTable() throws Throwable
    {
    	//Read stock number from above notepad
    	FileReader fr=new FileReader("./CaptureData/stockNumber.txt");
    	BufferedReader br=new BufferedReader(fr);
    	String Exp_data=br.readLine();
    	if(!driver.findElement(By.xpath(conpro.getProperty("Search_textbox"))).isDisplayed())
           driver.findElement(By.xpath(conpro.getProperty("Search_Panel"))).click();
    	driver.findElement(By.xpath(conpro.getProperty("Search_textbox"))).clear();
    	//enter stock number into text box
    	driver.findElement(By.xpath(conpro.getProperty("Search_textbox"))).sendKeys(Exp_data);
        //Click search button
    	driver.findElement(By.xpath(conpro.getProperty("Search_button"))).click();
        Thread.sleep(4000);
        String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
        Reporter.log(Exp_data+"     "+Act_data,true);
        try 
        {
        Assert.assertEquals(Act_data, Exp_data, "Stock Number Not Matching");
        }catch(Throwable t)
        {
        	System.out.println(t.getMessage());
        }
    }
 
    //SUPPLIER MODULE
    //Method for capture supplier number into note pad
    public static void captureSupNum(String LocatorType, String LocatorValue) throws Throwable
    {
    	String SuppNum="";
    	if(LocatorType.equalsIgnoreCase("xpath"))
    	{
    		SuppNum=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("name"))
    	{
    		SuppNum=driver.findElement(By.name(LocatorValue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("id"))
    	{
    		SuppNum=driver.findElement(By.id(LocatorValue)).getAttribute("value");
    	}
    	//Create note pad and write supplier number
    	FileWriter fw=new FileWriter("./CaptureData/SupplierNumber.txt");
    	BufferedWriter bw=new BufferedWriter(fw);
    	bw.write(SuppNum);
    	bw.flush();
    	bw.close();
    }
  //Verify supplier number from supplier table
    public static void supplierTable() throws Throwable
    {
    	//Read supplier number from above notepad
    	FileReader fr=new FileReader("./CaptureData/SupplierNumber.txt");
    	BufferedReader br=new BufferedReader(fr);
    	String Exp_data=br.readLine();
    	if(!driver.findElement(By.xpath(conpro.getProperty("Search_textbox"))).isDisplayed());
    	//click search panel if not display
    	     driver.findElement(By.xpath(conpro.getProperty("Search_Panel"))).click();
    	driver.findElement(By.xpath(conpro.getProperty("Search_textbox"))).clear();
    	driver.findElement(By.xpath(conpro.getProperty("Search_textbox"))).sendKeys(Exp_data);
    	driver.findElement(By.xpath(conpro.getProperty("Search_button"))).click();
    	Thread.sleep(3000);
    	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
    	Reporter.log(Exp_data+"      "+Act_Data,true);
    	try {
    		Assert.assertEquals(Act_Data, Exp_data, "Supplier Number is Not Matching");
    	} catch (AssertionError a) {
    		System.out.println(a.getMessage());
    	}
    }
  //CUSTOMER MODULE
    //Method for capture customer number into note pad
    public static void captureCustNum(String LocatorType, String LocatorValue) throws Throwable
    {
    	String CusNum="";
    	if(LocatorType.equalsIgnoreCase("xpath"))
    	{
    		CusNum=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("name"))
    	{
    		CusNum=driver.findElement(By.name(LocatorValue)).getAttribute("value");
    	}
    	if(LocatorType.equalsIgnoreCase("id"))
    	{
    		CusNum=driver.findElement(By.id(LocatorValue)).getAttribute("value");
    	}
    	//Create note pad and write customer number
    	FileWriter fw=new FileWriter("./CaptureData/CustomerNumber.txt");
    	BufferedWriter bw=new BufferedWriter(fw);
    	bw.write(CusNum);
    	bw.flush();
    	bw.close();
    }
  //Verify customer number from customer table
    public static void customerTable() throws Throwable
    {
    	//Read customer number from above notepad
    	FileReader fr=new FileReader("./CaptureData/CustomerNumber.txt");
    	BufferedReader br=new BufferedReader(fr);
    	String Exp_data=br.readLine();
    	if(!driver.findElement(By.xpath(conpro.getProperty("Search_textbox"))).isDisplayed());
    	//click search panel if not display
    	     driver.findElement(By.xpath(conpro.getProperty("Search_Panel"))).click();
    	driver.findElement(By.xpath(conpro.getProperty("Search_textbox"))).clear();
    	driver.findElement(By.xpath(conpro.getProperty("Search_textbox"))).sendKeys(Exp_data);
    	driver.findElement(By.xpath(conpro.getProperty("Search_button"))).click();
    	Thread.sleep(3000);
    	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
    	Reporter.log(Exp_data+"      "+Act_Data,true);
    	try {
    		Assert.assertEquals(Act_Data, Exp_data, "Customer Number is not matching");
    	} catch (AssertionError a) {
    		System.out.println(a.getMessage());
    	}
    }
}
    



