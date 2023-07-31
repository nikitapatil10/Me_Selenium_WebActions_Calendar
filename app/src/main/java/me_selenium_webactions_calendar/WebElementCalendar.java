package me_selenium_webactions_calendar;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebElementCalendar {

	WebDriver driver = null;

	/**
	 * use this method to initialize the browser.
	 */
	public WebDriver startBrowser() throws MalformedURLException {
		// Code to Launch Browser using Zalenium in Crio workspace
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		return driver;
	}

	/**
	 * use this method to open the url of an application
	 */
	public void openURL(String browserURL) {

		System.out.println("maximize the window..");
		driver.manage().window().maximize();

		System.out.println("opening website --->" + browserURL);
		driver.get(browserURL);
	}

	/**
	 * use this method to click on Calendar
	 * 
	 * @param webElement
	 */
	public void clickOnCalendar(WebElement webElement) {
		System.out.println("Clicking on Calendar");
		// TODO : Click on the Calendar icon
		webElement.click();
	}

	/**
	 * use this method to select year
	 * 
	 * @throws InterruptedException
	 */
	public void selectYear(String yearToSelect) throws InterruptedException {
		Thread.sleep(2000);
		System.out.println("Clicking on year dropdown");

		driver.findElement(By.xpath("//div[@class='css-1dozdou']/div/button")).click();

		// TODO : Locate all the year value from dropdown
		List<WebElement> years = driver.findElements(By.xpath("//div[contains(@class,'css-5c7sx6')]//button"));
		// TODO : Create a Javascript executor to scroll to particular year
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// TODO : Iterate on each year value using for each loop and if matching year is
		// found, perform click() operation
		for(WebElement year:years)
		{
			if(year.getText().trim().equalsIgnoreCase(yearToSelect))
			{
				js.executeScript("arguments[0].scrollIntoView();", year);
				year.click();
				break;
			}
		}
		System.out.println("Selected year");

	}

	/**
	 * use this method to get the month in number
	 * 
	 * @param month
	 * @return
	 * @throws ParseException
	 */
	private int getMonthIndex(String month) throws ParseException {
		int indexOfMonth = 0;

		// TODO : Create the instance of Calendar class
		Calendar calendar = Calendar.getInstance();
		// TODO : Create the instance of SimpleDateFormat.Pass "MMM" into constructor of
		// SimpleDateFormat
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
		// TODO : Parse the month which you want to select
		Date date = simpleDateFormat.parse(month);
		// TODO : Set the date using Calendar object use method setTime();
		calendar.setTime(date);
		// TODO : Get the index of month which you want to select and assign to
		// indexOfMonth
		indexOfMonth = calendar.get(calendar.MONTH);
		return indexOfMonth;
	}

	/**
	 * use this method to select month
	 * 
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public void selectMonth(String month) throws InterruptedException, ParseException {

		// TODO : Get the index of month which you want to select using getMonthIndex()
		int MonthToSelect = getMonthIndex(month);
		// TODO : Get the present month present on UI using findElement()
		String presentMonth = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/div/div/div[1]/div[1]/div[1]/div")).getText();
		// TODO : Get the index of present month on UI using getMonthIndex()
		int presentMonthIndex = getMonthIndex(presentMonth);
		// TODO : Using do while loop write the logic to select expected month.
		do{
			if(presentMonthIndex==MonthToSelect)
				break;
			else if(presentMonthIndex < MonthToSelect)
			{
				//right button 
				driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/div/div/div[1]/div[2]/button[2]/svg")).click();
				if(driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/div/div/div[1]/div[1]/div[1]/div")).getText().equalsIgnoreCase(month))
				break;
				presentMonthIndex++;
			}
			else if(presentMonthIndex > MonthToSelect)
			{
				//left button 
				driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/div/div/div[1]/div[2]/button[1]/svg/path")).click();
				if(driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/div/div/div[1]/div[1]/div[1]/div")).getText().equalsIgnoreCase(month))
				break;
				presentMonthIndex--;
			}
		} while(presentMonthIndex <= 11 || presentMonthIndex >= 0);
		System.out.println("Selected month");

	}
	/**
	 * use this method to select date
	 */
	public void selectDate(String dateToSelect) {
		// TODO : Locate all the dates
		List<WebElement> days = driver.findElements(By.xpath("/html/body/div[2]/div[2]/div[1]/div/div/div[2]/div/div[2]/div/div/div/button"));
		// TODO : Iterate through all the dates till matching date is found
		for(WebElement day : days)
		{
			if(day.getText().equalsIgnoreCase(dateToSelect))
			{
				day.click();
				break;
			}
		}
		System.out.println("Selected date");

	}

	/**
	 * use this method to close the current window of browser
	 */
	public void closeBrowser() {
		System.out.println("Closing the browser window");
		driver.close();
	}

	public void run() throws InterruptedException, MalformedURLException, ParseException {

		

		// Step - 1 : Call the method startBrowser
		WebDriver driver = this.startBrowser();

		// Step - 2 : Call the method openURL
		this.openURL("https://web-locators-static-site-qa.vercel.app/Calendar");

		// WebElement calendarElement;
		WebElement calendarElement = driver.findElement(By.xpath("//*[@class='Calendar_section2']//button"));
		// TODO : Locate and click on the calendar
		this.clickOnCalendar(calendarElement);
		// TODO : Call method selectYear() and pass the year as parameter
		this.selectYear("2001");
		// TODO : Call method selectMonth() and pass the month as parameter
		this.selectDate("September");
		// TODO : Call method selectDate() and pass the date as parameter
		this.selectDate("22");
		// Call the method closeBrowser
		this.closeBrowser();
	}

}
