package Selpack;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;


public class registrationpage {
	
	WebDriver driver;
	public registrationpage(WebDriver driver){
		
		this.driver= driver;
		PageFactory.initElements(driver,this );
	}

	@FindBy(xpath="//*[@id='content']/form/div[1]/table/tbody/tr[1]/td[2]/input") public WebElement FnameB;
	@FindBy(xpath="//*[@id='content']/form/div[1]/table/tbody/tr[2]/td[2]/input") public WebElement LnameB;

	
	
	
}
