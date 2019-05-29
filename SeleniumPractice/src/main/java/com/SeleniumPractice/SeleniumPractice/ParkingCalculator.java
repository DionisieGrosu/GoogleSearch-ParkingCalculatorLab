package com.SeleniumPractice.SeleniumPractice;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ParkingCalculator {

	@DataProvider(parallel = true)
	public Object[][] ValidDataProvider() {
		return new Object[][] {{"Short-Term Parking","12:00", "AM", "11.14.2010", "04:50", "AM", "12.23.2010", "$ 790.00", "(30 Days, 4 Hours, 50 Minutes)"},
		{"Economy Parking","11:00", "PM", "09.20.2014", "04:50", "AM", "10.22.2016", "$ 249.00", "(29 Days, 5 Hours, 50 Minutes)"},
		{"Long-Term Surface Parking","05:10", "AM", "02.15.2013", "04:50", "PM", "05.23.2013", "$ 780.00", "(90 Days, 11 Hours, 40 Minutes)"},
		{"Valet Parking","10:00", "PM", "02.15.2009", "11:50", "PM", "09.19.2009", "$ 6,402.00", "(213 Days, 1 Hours, 50 Minutes)"},
		{"Long-Term Garage Parking","08:30", "AM", "04.13.2011", "11:50", "AM", "09.25.2013", "$ 1,584.00", "(153 Days, 3 Hours, 20 Minutes)"},
		{"Economy Parking","06:03", "PM", "04.11.2010", "11:50", "AM", "11.25.2012", "$ 1,656.00", "(213 Days, 17 Hours, 47 Minutes)"}};
	}
	
	
	@Test(dataProvider = "ValidDataProvider")
	public void parckingCalc(String selectValue, String timeEntryValue, String radioEntryValue, String inputEntryDateValue, String timeExitValue, String radioExitValue, String inputExitDateValue, String costValue, String calculTimeValue) {
		WebDriver driver = new ChromeDriver();
	
		driver.get("http://adam.goucher.ca/parkcalc/index.php");
		Assert.assertEquals(driver.getTitle(),"Parking Calculator");
		
		Select select = new Select(driver.findElement(By.xpath("//tr[td[contains(text(),'Choose a Lot')]]//select[@id = 'Lot']")));
		select.selectByVisibleText(selectValue);
		
		WebElement inputTimeEntry = driver.findElement(By.xpath("//tr[td[contains(text(),'Entry')]]//input[@name = 'EntryTime']"));
		inputTimeEntry.clear();
		inputTimeEntry.sendKeys(timeEntryValue);
		
		List<WebElement> radioEntryBtn = driver.findElements(By.xpath("//tr[td[contains(text(),'Entry')]]//input[@name = 'EntryTimeAMPM']"));
		selectedBtn(radioEntryBtn,radioEntryValue);
		
		WebElement inputEntryDate = driver.findElement(By.xpath("//tr[td[contains(text(),'Entry')]]//input[@name = 'EntryDate']"));
		inputEntryDate.clear();
		inputEntryDate.sendKeys(inputEntryDateValue);
		
		WebElement inputTimeExit = driver.findElement(By.xpath("//tr[td[contains(text(),'Leaving')]]//input[@name = 'ExitTime']"));
		inputTimeExit.clear();
		inputTimeExit.sendKeys(timeExitValue);
		
		List<WebElement> radioExitBtn = driver.findElements(By.xpath("//tr[td[contains(text(),'Leaving')]]//input[@name = 'ExitTimeAMPM']"));
		selectedBtn(radioExitBtn,radioExitValue);
		
		WebElement inputExitDate = driver.findElement(By.xpath("//tr[td[contains(text(),'Leaving')]]//input[@name = 'ExitDate']"));
		inputExitDate.clear();
		inputExitDate.sendKeys(inputExitDateValue);
		
		WebElement submitBtn = driver.findElement(By.xpath("//input[@type = 'submit' and @value = 'Calculate']"));
		submitBtn.click();
		
		WebElement costValueBold = driver.findElement(By.xpath("//tr[contains(.,'COST')]//b[contains(text(),'$')]"));
		//Assert.assertEquals(costValue.getText(),"");
		WebElement calculTime = driver.findElement(By.xpath("//tr[contains(.,'COST')]//td//b[contains(text(),'Days')]"));
		
		Assert.assertEquals(costValueBold.getText(), costValue);
		Assert.assertEquals(calculTime.getText().trim(), calculTimeValue);
		driver.close();
		
	}
	
	public static void selectedBtn(List<WebElement> radio, String selectedValue) {
		for(WebElement elem : radio) {
			if(StringUtils.equals(elem.getAttribute("value"), selectedValue)) {
				elem.click();
				
				break;
			}
		}
	}
	
}
