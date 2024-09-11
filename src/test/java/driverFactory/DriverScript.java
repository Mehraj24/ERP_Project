package driverFactory;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript 
{
String inputpath="./FileInput/Hybrid_TestData.xlsx";
String outputpath="./FileOutput/Hybrid_Results.xlsx";
ExtentReports reports;
ExtentTest logger;
String TC_Sheet="MasterTestCases";
public static WebDriver driver;

public void startTest() throws Throwable
{
	String Module_Status="";
	String Module_New="";
	
	//create instance object for Excel file util class
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	//Iterate all rows in TC Sheet
	for(int i=1;i<=xl.rowCount(TC_Sheet);i++)
	{
		if(xl.getCellData(TC_Sheet,i,2).equalsIgnoreCase("Y"))
		{
			//Read corresponding sheet into TC_Module variable
			String TC_Module=xl.getCellData(TC_Sheet, i, 1);		
		    reports = new ExtentReports("./target/Reports/"+TC_Module+"----"+FunctionLibrary.generateDate()+".html");
			logger=reports.startTest(TC_Module);
		    //Iterate all rows in TC_Module
			for(int j=1;j<=xl.rowCount(TC_Module);j++)
			{
				String Description = xl.getCellData(TC_Module, j, 0);
				String Object_Type = xl.getCellData(TC_Module, j, 1);
				String Locator_Type = xl.getCellData(TC_Module, j, 2);
				String Locator_Value = xl.getCellData(TC_Module, j, 3);
				String Test_Data = xl.getCellData(TC_Module, j, 4);
				try 
				{
					if(Object_Type.equalsIgnoreCase("startBrowser"))
					{
						driver=FunctionLibrary.startBrowser();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(Locator_Type,Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(Test_Data);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("dropDownAction"))
					{			
					    FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
					    logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("captureStock"))
					{
						FunctionLibrary.captureStock(Locator_Type,Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("stockTable"))
					{
						FunctionLibrary.stockTable();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("captureSupNum"))
					{
						FunctionLibrary.captureSupNum(Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("supplierTable"))
					{
						FunctionLibrary.supplierTable();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("captureCustNum"))
					{
						FunctionLibrary.captureCustNum(Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("customerTable"))
					{
						FunctionLibrary.customerTable();
						logger.log(LogStatus.INFO, Description);
					}
					//write as pass into status cell in TC_Module
					xl.setCellData(TC_Module, j, 5, "Pass", outputpath);
					logger.log(LogStatus.PASS, Description);
					Module_Status="True";	
				}catch(Exception e)
				{
					File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(screen, new File("./target/Screenshot/"+TC_Module+"----"+FunctionLibrary.generateDate()+".png"));
					System.out.println(e.getMessage()); 
					//write as Fail into status cell in TC_Module
					xl.setCellData(TC_Module, j, 5, "Fail", outputpath);
					logger.log(LogStatus.FAIL, Description);
					Module_New="False";
				}	
				if(Module_Status.equalsIgnoreCase("True"))
				{
					//Write pass into TCSheet in status cell
					xl.setCellData(TC_Sheet, i, 3, "Pass", outputpath);
				}
				if(Module_New.equalsIgnoreCase("False"))
				{
					//Write fail into TCSheet in status cell
					xl.setCellData(TC_Sheet, i, 3, "Fail", outputpath);
				}
				reports.endTest(logger);
				reports.flush();
			}	
		}
		else
		{
			//Write as blocked into status cell in TC_Sheet
			xl.setCellData(TC_Sheet, i, 3, "Blocked", outputpath);
		}
	}
}
}



