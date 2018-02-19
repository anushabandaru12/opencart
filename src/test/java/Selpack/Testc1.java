package Selpack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class Testc1{

	WebDriver driver;
	Properties pro;
	String Name;
	String emailc;
	registrationpage regPage;
	String url;
//*********************************************************
	
  @BeforeClass
  public void Intial() throws IOException 
  {
  /*System.setProperty("webdriver.chrome.driver","D://drivers.sel//chromedriver.exe");
  driver = new ChromeDriver();
 */ 
	  url = "http://10.159.34.58:4444/wd/hub";
      try {
          DesiredCapabilities capabilities = new DesiredCapabilities();
          capabilities.setBrowserName("chrome");
          capabilities.setPlatform(Platform.WINDOWS);
          driver = new RemoteWebDriver(new URL(url), capabilities);
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
          
 File file = new File("C://Users//AN252981//workspace//Topgear//Objrepo.properties");	
  FileInputStream fis = new FileInputStream(file);
  pro = new Properties();
  pro.load(fis);
  }
  
 //************************************************************ 
  
  @Test(priority=1)
  public void opencart() throws IOException
  {
	  
	  driver.get(pro.getProperty("Opencart.URL"));
	    driver.findElement(By.xpath(pro.getProperty("Xpath1"))).click();
	    driver.manage().window().maximize();
 		
  }
  
  //***********************************************************************
  
 @Test(dataProvider="userData",priority=2)
  public void Launchbrowser(String FirstName, String LastName,String email, String phone, String company, String Address1,String City, String Postcode,String Passwordnm,String Confirm) throws IOException, BiffException
  {
	 
	 regPage =new registrationpage(driver);

//Elements to assign
    driver.findElement(By.xpath(pro.getProperty("Xpathfname"))).sendKeys(FirstName);
   // regPage.FnameB.sendKeys(FirstName);
        
    Name= driver.findElement(By.xpath(pro.getProperty("Xpathfname"))).getAttribute("value");
    
    WebElement Lname = driver.findElement(By.xpath(pro.getProperty("XpathLname")));
    Lname.sendKeys(LastName);
	 String Emailadd=System.nanoTime()+email;
	 System.out.println("The changed email is " + Emailadd);
	 driver.findElement(By.xpath(pro.getProperty("Email"))).sendKeys(Emailadd); 
    emailc=driver.findElement(By.xpath(pro.getProperty("Email"))).getAttribute("value");
    
    WebElement phonenumber = driver.findElement(By.xpath(pro.getProperty("phonenum")));
    phonenumber.sendKeys(phone); 
    WebElement companyname = driver.findElement(By.xpath(pro.getProperty("CompanyN")));
    companyname.sendKeys(company); 
    WebElement addressad= driver.findElement(By.xpath(pro.getProperty("Address")));
    addressad.sendKeys(Address1);
    WebElement cityname= driver.findElement(By.xpath(pro.getProperty("cityna")));
    cityname.sendKeys(City);
    WebElement pinc= driver.findElement(By.xpath(pro.getProperty("pincode1")));
    pinc.sendKeys(Postcode);
 //dropdown for country 
    WebElement Conty = driver.findElement(By.name("country_id"));
    Select country = new Select(Conty);
    country.selectByVisibleText("India");
    Conty.sendKeys(Keys.ENTER);
 //dropdown for and state   
    WebElement Region = driver.findElement(By.name("zone_id"));
    Select state = new Select(Region);
    state.selectByVisibleText("Andhra Pradesh");
   Region.sendKeys(Keys.ENTER);
   
   WebElement pwd = driver.findElement(By.name("password"));
   pwd.sendKeys(Passwordnm);
   WebElement Cnfm = driver.findElement(By.name("confirm"));
		   Cnfm.sendKeys(Confirm);
 //checkbox
   driver.findElement(By.xpath(pro.getProperty("Privcy"))).click();
   driver.findElement(By.xpath(pro.getProperty("Continue"))).click();
 //Validation for account creation
   String Title=driver.getTitle();
  // Assert.assertEquals(Title,"Account created" );
   System.out.println(Title);
  }
  
 //*********************************************************************************** 
  
@DataProvider(name="userData")
public Object[][] getDataFromDataprovider() throws BiffException, IOException{
	
//To read Excel
	 File regestraion = new File("C://Users//AN252981//Desktop//extra docs//Selenium top//RegestrationDetails.xls");
	   Workbook W1 = Workbook.getWorkbook(regestraion);
	   Sheet sheet = W1.getSheet("Reg");
	   
// To get values from Excel   
	   int rows = sheet.getRows();
	   int columns = sheet.getColumns();
	
       String[][] inputData= new String[rows][columns];
		 for(int i=0;i<=rows-1;i++){
		        for(int j=0;j<=columns-1;j++){
		        	Cell c = sheet.getCell(j,i);
					inputData[i][j] = c.getContents();
					System.out.println(c.getContents());
		        }
		 }
		 return inputData;
}

//***************************************************************************************

@Test(priority=3)
	public void steps() throws IOException, InterruptedException{
	Thread.sleep(3000);
	driver.findElement(By.xpath(pro.getProperty("contact"))).click();
	
	String lognam=driver.findElement(By.name(pro.getProperty("Namelog"))).getAttribute("value");
	Assert.assertEquals(Name, lognam);
	System.out.println("Verified");
	
	String logmail=driver.findElement(By.name(pro.getProperty("emaillog"))).getAttribute("value");
	Assert.assertEquals(emailc,logmail );
	System.out.println("Verified");
	driver.findElement(By.name(pro.getProperty("Enquiry.name"))).sendKeys(pro.getProperty("Enquirydesc"));
	
/*//Scanner	
	driver.findElement(By.name("captcha")).clear();
	System.out.println("Enter captcha: ");
	Scanner scan= new Scanner(System.in);
	String capname= scan.nextLine();
	driver.findElement(By.name("captcha")).sendKeys(capname);*/
	
Thread.sleep(20000);	
//Continue
	driver.findElement(By.xpath(pro.getProperty("contactContin"))).click();
	Thread.sleep(3000);
	driver.findElement(By.xpath(pro.getProperty("NextCont"))).click();
	Thread.sleep(3000);
//homepage	
	driver.findElement(By.className(pro.getProperty("Homeimage"))).click();
	 driver.findElement(By.xpath(pro.getProperty("Reviewtab.xpath"))).click();
}
//****************************************************************************************	 
	 
@Test(dataProvider="reviewdata",priority=4)
public void ratingsteps(String revname, String revrew, String revrating) throws InterruptedException{
	int rate=Integer.parseInt(revrating);
driver.findElement(By.name(pro.getProperty("Reviewname"))).clear();
driver.findElement(By.name(pro.getProperty("Reviewname"))).sendKeys(revname);	 
driver.findElement(By.xpath(pro.getProperty("reviewdesc.xpath"))).clear();
driver.findElement(By.xpath(pro.getProperty("reviewdesc.xpath"))).sendKeys(revrew);
int revrewlen=revrew.length();
Thread.sleep(3000);
if (rate==1)
{
	driver.findElement(By.xpath(pro.getProperty("rating1.xpath"))).click();
}
else 
	if(rate==2)
{
	driver.findElement(By.xpath(pro.getProperty("rating2.xpath"))).click();
}
else 
	if(rate==3)
{
	driver.findElement(By.xpath(pro.getProperty("rating3.xpath"))).click();
}	
else 
	if(rate==4)
{
	driver.findElement(By.xpath(pro.getProperty("rating4.xpath"))).click();
}
else 
	if(rate==5)
{
	driver.findElement(By.xpath(pro.getProperty("rating5.xpath"))).click();
}

/*//captcha
driver.findElement(By.name("captcha")).clear();
System.out.println("Enter captcha: ");
Scanner scan= new Scanner(System.in);
String captc= scan.nextLine();
driver.findElement(By.name("captcha")).sendKeys(captc);*/
Thread.sleep(10000);

driver.findElement(By.id(pro.getProperty("review.continue"))).click();
Thread.sleep(3000);
if (revrewlen<25){
Assert.assertTrue(driver.findElement(By.xpath(pro.getProperty("Warning.xpath"))).getText().contains("Warning"),"Incorrect warning msg displayed");
}
else{
Assert.assertTrue(driver.findElement(By.xpath(pro.getProperty("Successwarning.xpath"))).getText().contains("approval"),"Incorrect success msg displayed");

}
	
}
//**********************************************************************************************

@DataProvider(name="reviewdata")
public Object[][] reviewrate() throws BiffException, IOException{
	 	
//To read Excel
	 	 File Rating = new File("C://Users//AN252981//Desktop//extra docs//Selenium top//Review rating.xls");
	 	   Workbook W2 = Workbook.getWorkbook(Rating);
	 	   Sheet sheet1 = W2.getSheet("Rev");
	 	   
// To get values from Excel   
	 	   int rows = sheet1.getRows();
	 	   int columns = sheet1.getColumns(); 	
	        String[][] inputData= new String[rows][columns];
	 		 for(int i=0;i<=rows-1;i++){
	 		        for(int j=0;j<=columns-1;j++){
	 		        	Cell c = sheet1.getCell(j,i);
	 					inputData[i][j] = c.getContents();
	 					System.out.println(c.getContents());
	 		        }
	 		 }

	 		 return inputData; 
}	
//******************************************************************************
@Test(priority=5)
public void wishlist() throws InterruptedException, IOException {
driver.findElement(By.xpath(pro.getProperty("wishlink.xpath"))).click();
System.out.println("added to wishlist");
//x mark click
Thread.sleep(3000);
driver.findElement(By.xpath(pro.getProperty("closlink.wishlnk.xpath"))).click();
Thread.sleep(3000);
//wishlist link
driver.findElement(By.id(pro.getProperty("wishlist.link.id"))).click();

//retrieving values from pounds
String TestFile = "D:\\temp.txt";
File FC = new File(TestFile);
FC.createNewFile();

WebElement pound=driver.findElement(By.xpath(pro.getProperty("pound.xpath")));
pound.click();
String poundvalue=driver.findElement(By.xpath(pro.getProperty("poundcurrval.xpath"))).getText();

WebElement euro=driver.findElement(By.xpath(pro.getProperty("euro.xpath")));
euro.click();
String euroval=driver.findElement(By.xpath(pro.getProperty("eurocurrval.xpath"))).getText();

WebElement dollar=driver.findElement(By.xpath(pro.getProperty("dollar.xpath")));
dollar.click();
String dollarval=driver.findElement(By.xpath(pro.getProperty("dollarcurrval.xpath"))).getText();

FileWriter FW = new FileWriter(TestFile);
BufferedWriter BW = new BufferedWriter(FW);

BW.write(poundvalue);
BW.newLine();
BW.write(euroval);
BW.newLine();
BW.write(dollarval);
BW.close();

//adding to cart
driver.findElement(By.xpath(pro.getProperty("addtocart.xpath"))).click();
Thread.sleep(3000);
driver.findElement(By.xpath(pro.getProperty("closesucess.xpath"))).click();
Thread.sleep(3000);
driver.findElement(By.className("button")).click();

//logout
driver.findElement(By.partialLinkText(pro.getProperty("logout.partiallinktext"))).click();
String Logout=driver.getTitle();
Assert.assertEquals(Logout,"Account Logout");
System.out.println(Logout);
}

@AfterClass 
public void Final(){
	driver.quit();
}

}

       

         
         
  
  
  
 

