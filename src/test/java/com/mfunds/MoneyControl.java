package com.mfunds;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class MoneyControl {

	public static void main(String[] args) throws InterruptedException, IOException {
		
	getData("https://www.moneycontrol.com/mutual-funds/nav/uti-nifty200-momentum-30-index-fund-direct-plan-growth/MUT3614",0);
	getData("https://www.moneycontrol.com/mutual-funds/nav/icici-prudential-bharat-22-fof-direct-plan-growth/MPI3793",1);
	getData("https://www.moneycontrol.com/mutual-funds/nav/quant-small-cap-fund-direct-plan-growth/MES056",2);
	getData("https://www.moneycontrol.com/mutual-funds/nav/sbi-nifty-smallcap-250-index-fund-regular-plan-growth/MSB1869",3);
	getData("https://www.moneycontrol.com/mutual-funds/nav/icici-prudential-smallcap-fund-direct-plan-growth/MPI1146",4);

	}
	
	public static void getData(String url,int sheetindex) throws InterruptedException, IOException {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(url);
		Thread.sleep(3000);
		
		try {
			
			String amount = driver.findElement(By.xpath("//div[@class='leftblok']/span[@class='amt']")).getText()
					.replaceFirst("\\W", "").trim();
			
			String change = driver.findElement(By.xpath("//div[@class='leftblok']/span[@class='amt']/following-sibling::span")).getText()
					.replaceFirst("\\W", "").trim();
			
			String date = driver.findElement(By.xpath("//div[@class='leftblok']/span[@class='amt']/following-sibling::div")).getText()
					.replaceFirst("\\W", "").trim();
			
			
			FileInputStream fis= new FileInputStream("C:\\Users\\New\\eclipse-workspace\\TestProjet\\ExcelData\\Portfolio.xlsx");
			
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet=workbook.getSheetAt(sheetindex);
			if(sheet.getPhysicalNumberOfRows()>=1) 
			{
	        sheet.shiftRows(0, sheet.getPhysicalNumberOfRows(), 1);
	        }
			 Row rowHeading = sheet.createRow(0);
		        rowHeading.createCell(0).setCellValue(date);
		        rowHeading.createCell(1).setCellValue(amount);
		        rowHeading.createCell(2).setCellValue(change);
		        FileOutputStream out = new FileOutputStream("C:\\Users\\New\\eclipse-workspace\\TestProjet\\ExcelData\\Portfolio.xlsx");
		        workbook.write(out);
		        out.flush();
		        out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		driver.quit();
		

		
		
	}

}
