package com.SeleniumPractice.SeleniumPractice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class CountWords {

	@Test
	public void countingWords() throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
		 WebElement element = driver.findElement(By.xpath("//input[@name = \"q\"]"));
		 Workbook wb = new HSSFWorkbook();
	        // Enter something to search for
	        element.sendKeys("Cheese!");

	        // Now submit the form. WebDriver will find the form for us from the element
	        element.submit();
  
	        System.out.println("Page title is: " + driver.getTitle());
	        System.out.println(driver.getCurrentUrl());
	        
	        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) {
	                return d.getTitle().toLowerCase().startsWith("cheese!");
	            }
	        });

	       
	        System.out.println("Page title is: " + driver.getTitle());
	        
          
	        
            List<WebElement> listElements = (ArrayList<WebElement>) driver.findElements(By.xpath("//div[@class='r']/a[contains(.,'ese')]"));
            System.out.println(listElements.size());
            int countSheet = 1;
            for(int i = 0; i < listElements.size();i++) {

            	 List<WebElement> listElements2 = (ArrayList<WebElement>) driver.findElements(By.xpath("//div[@class='r']/a[contains(.,'ese')]"));
            	 listElements2.get(i).click();
            	 Sheet sheet = wb.createSheet("Page " + countSheet++);
            	 
            	 Row row = sheet.createRow(0);
            	 Cell url = row.createCell(0);
            	 url.setCellValue("Opened url");
            	 Cell siteName = row.createCell(1);
            	 siteName.setCellValue("Site name");
            	 Cell countWord = row.createCell(2);
            	 countWord.setCellValue("Count of words Cheese");
            
            	 Row row2 = sheet.createRow(1);
            	 Cell urlValue = row2.createCell(0);
            	 urlValue.setCellValue(driver.getCurrentUrl());
            	 Cell siteNameValue = row2.createCell(1);
            	 siteNameValue.setCellValue(driver.getTitle());
            	 Cell countWordValue = row2.createCell(2);
            	 countWordValue.setCellValue(StringUtils.countMatches(driver.getPageSource().toLowerCase(),"cheese"));
            	 sheet.autoSizeColumn(0);
            	 sheet.autoSizeColumn(1);
            	 sheet.autoSizeColumn(2);
            	 System.out.println("Opened url " + driver.getCurrentUrl());
            	 System.out.println("Site name: " + driver.getTitle());
            	 System.out.println("Count of words Cheese: " + StringUtils.countMatches(driver.getPageSource().toLowerCase(),"cheese")); 
             	driver.navigate().back();
             	 try (OutputStream fileOut = new FileOutputStream("CheeseCount.xlsx")) {
      		        wb.write(fileOut);
      		    } catch (IOException e) {
      				// TODO Auto-generated catch block
      				e.printStackTrace();
      			}
             	Thread.sleep(5000);

            }
            if(countSheet < 10) {
            	WebElement page2 = driver.findElement(By.xpath("//a[@class = 'fl' and @aria-label = 'Page 2']"));
            	page2.click();
            	List<WebElement> listElementNext = driver.findElements(By.xpath("//div[@class = 'r']"));
            	for(int i = 0; countSheet < 11; i++) {
            		List<WebElement> listElementNext2 = driver.findElements(By.xpath("//div[@class = 'r']"));
            		
            		listElementNext2.get(i).click();
               	 Sheet sheet = wb.createSheet("Page " + countSheet++);
           	 
               	 Row row = sheet.createRow(0);
               	 Cell url = row.createCell(0);
               	 url.setCellValue("Opened url");
               	 Cell siteName = row.createCell(1);
               	 siteName.setCellValue("Site name");
               	 Cell countWord = row.createCell(2);
               	 countWord.setCellValue("Count of words Cheese");
              
               	 Row row2 = sheet.createRow(1);
               	 Cell urlValue = row2.createCell(0);
               	 urlValue.setCellValue(driver.getCurrentUrl());
               	 Cell siteNameValue = row2.createCell(1);
               	 siteNameValue.setCellValue(driver.getTitle());
               	 Cell countWordValue = row2.createCell(2);
               	 countWordValue.setCellValue(StringUtils.countMatches(driver.getPageSource().toLowerCase(),"cheese"));
               	 sheet.autoSizeColumn(0);
               	 sheet.autoSizeColumn(1);
               	 sheet.autoSizeColumn(2);
               	 System.out.println("Opened url " + driver.getCurrentUrl());
               	 System.out.println("Site name: " + driver.getTitle());
               	 System.out.println("Count of words Cheese: " + StringUtils.countMatches(driver.getPageSource().toLowerCase(),"cheese")); 
                	driver.navigate().back();
                	 try (OutputStream fileOut = new FileOutputStream("CheeseCount.xlsx")) {
         		        wb.write(fileOut);
         		    } catch (IOException e) {
         				// TODO Auto-generated catch block
         				e.printStackTrace();
         			}
                	Thread.sleep(5000);
            	}
            }
            driver.close();
            driver.quit();
            
 	       
            
	}
}
