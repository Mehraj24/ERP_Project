package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtil {
XSSFWorkbook wb;
//creating constructor for reading excel path
public ExcelFileUtil(String excelpath) throws Throwable
{
	FileInputStream fi=new FileInputStream(excelpath);
	wb=new XSSFWorkbook(fi);	
}
//count number of rows in a sheet
public int rowCount(String sheetname)
{
	return wb.getSheet(sheetname).getLastRowNum();	
}
//method for reading cell data
public String getCellData(String sheetname, int row, int column)
{
	String data="";
	if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
	{
		//get integer type cell
		int celldata=(int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
		//convert celldata integer type cell data into string
		data=String.valueOf(celldata);
	}
	else
	{
		data =wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
	}
	return data;
}

//method for writing status into new workbook

public void setCellData(String sheetname, int row, int column, String status, String writeExcel) throws Throwable 
{
	//get sheet from wb
	XSSFSheet ws=wb.getSheet(sheetname);
	//get row from sheet
	XSSFRow rownum=ws.getRow(row);
	//create cell in row
	XSSFCell cell=rownum.createCell(column);
	//write status
	cell.setCellValue(status);
	if(status.equalsIgnoreCase("Pass"))
	{
		XSSFCellStyle style=wb.createCellStyle();
		XSSFFont font=wb.createFont();
		//get green color
		font.setColor(IndexedColors.GREEN.getIndex());
		font.setBold(true);
		style.setFont(font);
		rownum.getCell(column).setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("Fail"))
	{
		XSSFCellStyle style = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setColor(IndexedColors.RED.getIndex());
		font.setBold(true);
		style.setFont(font);
		rownum.getCell(column).setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("Blocked"))
	{
		XSSFCellStyle style = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setColor(IndexedColors.BLUE.getIndex());
		font.setBold(true);
		style.setFont(font);
		rownum.getCell(column).setCellStyle(style);
	}
	FileOutputStream fo =new FileOutputStream(writeExcel);
	wb.write(fo);	
}
}
/*
public static void main(String args[]) throws Throwable
{
	ExcelFileUtil xl =new ExcelFileUtil("D:/Employee.xlsx");
	int rc=xl.rowCount("Employee");
	System.out.println(rc);
	//Iterate all rows
	for(int i=1;i<=rc;i++)
	{
		String fname=xl.getCellData("Employee", i, 0);
		String mname=xl.getCellData("Employee", i, 1);
		String lname = xl.getCellData("Employee", i, 2);
		String eid = xl.getCellData("Employee", i, 3);
		System.out.println(fname+"    "+mname+"    "+lname+"   "+eid);
		//write status as pass
		//xl.setCellData("Employee", i, 4, "pass", "D:/Results.xlsx");
		//xl.setCellData("Employee", i, 4, "fail", "D:/Results.xlsx");
		xl.setCellData("Employee", i, 4, "blocked", "D:/Results.xlsx");		
	}	
}
*/

