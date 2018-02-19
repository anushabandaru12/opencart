package Selpack;

import java.awt.RenderingHints.Key;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Testop2 {

	WebDriver driver;
	Properties pro;
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
        }catch(Exception e){
            e.printStackTrace();
        }
        
        File file = new File("C://Users//AN252981//workspace//Topgear//Objrepo.properties");	
  FileInputStream fis = new FileInputStream(file);
  pro = new Properties();
  pro.load(fis);
  
  }
  
 //************************************************************ 
	
 @Test(priority=1)
 public void logincred() throws IOException, InterruptedException
 {
	 driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	 driver.get(pro.getProperty("Opencart.URL"));
	 driver.manage().window().maximize();
	 driver.findElement(By.linkText(pro.getProperty("login.linktext"))).click();
	 Thread.sleep(3000);
	 driver.findElement(By.name(pro.getProperty("email2.name"))).sendKeys(pro.getProperty("email2.value"));
	 driver.findElement(By.name(pro.getProperty("password.name"))).sendKeys(pro.getProperty("passowrd.value"));
	 driver.findElement(By.xpath(pro.getProperty("login.submit.xpath"))).click();
	 System.out.println("Login Successful");
				
 //Validations		
 	   Assert.assertTrue(driver.findElement(By.xpath(pro.getProperty("validation.username"))).getText().contains(pro.getProperty("username.verify")),"Wrong user");
 	   System.out.println("anusha logged in");	
 }
//*****************************************************************
 	   
@Test(dataProvider="searchfld",priority=2)	  
public void searchtest(String searchvalue) throws IOException, InterruptedException{
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

 	 WebElement search= driver.findElement(By.name(pro.getProperty("searchfield.name")));
 	 search.sendKeys(searchvalue);
  	 search.sendKeys(Keys.ENTER);
  	 
  	 String Actual=driver.findElement(By.className(pro.getProperty("classname.count"))).getText().substring(18,20);
  	 System.out.println(Actual);
  	 
 //copy to flatfile
  	String countFile = "D:\\count.txt";
  	File CF = new File(countFile);
  	CF.createNewFile();
  	FileWriter FW = new FileWriter(countFile);
  	BufferedWriter BW = new BufferedWriter(FW);
  	BW.write(Actual);
  	BW.newLine();
  	BW.close();
  	
 //Dropdown for category
  	Thread.sleep(3000);
  	WebElement down= driver.findElement(By.name(pro.getProperty("category.dropdown.name")));
   	Select dropdwn=new Select(down);
  	dropdwn.selectByValue(pro.getProperty("valuetext.dropdown"));
  	String dropdowncount=driver.findElement(By.name(pro.getProperty("category.dropdown.name"))).getText();
  	System.out.println(dropdowncount);
  	
 //writing count value in notepad
  	String dropfile = "D:\\dropcount.txt";
  	File counfile = new File(dropfile);
  	counfile .createNewFile();
  	FileWriter Filedown = new FileWriter(dropfile);
  	BufferedWriter downbuffer = new BufferedWriter(Filedown);
  	downbuffer.write(dropdowncount);
  	downbuffer.newLine();
  	downbuffer.close();	
 	  	
//clicking search
  	driver.findElement(By.id(pro.getProperty("searchclick.id"))).click();
  	
//clicking Phones & PDAs  	
  	driver.findElement(By.linkText(pro.getProperty("linktext.phoneslink"))).click();
  	List<WebElement> Phonesv=driver.findElements(By.xpath("//div[@class='product-list']/div"));
  	System.out.println("div data is "+Phonesv.size());
  	int itemcount=Phonesv.size();
  	String numberAsString = Integer.toString(itemcount); 
  	
//writing phones value in notepad
  	String phonesval = "D:\\phoneval.txt";
  	File phnfile = new File(phonesval);
  	phnfile .createNewFile();
  	FileWriter Fileph = new FileWriter(phonesval);
  	BufferedWriter phnbuffer = new BufferedWriter(Fileph);
  	phnbuffer.write(numberAsString);
  	phnbuffer.newLine();
  	phnbuffer.close();	 	
  	
//soryby dropdown
  	WebElement sort=driver.findElement(By.xpath(pro.getProperty("xpath.sortBy")));
  	sort.click();
  	Select sortsel = new Select(sort);
  	sortsel.selectByVisibleText(pro.getProperty("sortselect.text"));
  
//storing add to campare values
  	String campare = "D:\\phonecomp.txt";
  	File compareph = new File(campare);
  	compareph .createNewFile();
  	FileWriter cmpph = new FileWriter(compareph);
  	BufferedWriter phonebuffer = new BufferedWriter(cmpph);
//loop
  	for(int i=1;i<=3;i++){
  		Thread.sleep(2000);
  		driver.findElement(By.xpath("(//div[@class='product-list']//div[@class='compare']/a)["+i+"]")).click();	
  String phonename=null;
  phonename=driver.findElement(By.xpath("(//div[@class='name']/a)["+i+"]")).getText();
   		phonebuffer.write(phonename);
  		phonebuffer.newLine();
  		phonebuffer.flush();
  	}
  	phonebuffer.close();
  	Thread.sleep(3000);
driver.findElement(By.xpath(pro.getProperty("close.compare.xpath"))).click();
driver.findElement(By.xpath(pro.getProperty("productcompare.xpath"))).click();
driver.findElement(By.xpath(pro.getProperty("click.item.xpath"))).click();

//feature selecting

BufferedWriter buwr= new BufferedWriter(new FileWriter("D:\\Filename.txt"));
String desc=driver.findElement(By.xpath(pro.getProperty("5thdesc.xapth"))).getText();
buwr.write(desc);
buwr.close();

//click to addtocart&shopping cart
driver.findElement(By.id(pro.getProperty("id.addtocart"))).click();
driver.findElement(By.xpath(pro.getProperty("shoppingcart.ribbon.xpath"))).click();
Thread.sleep(3000);

//checkout button
driver.findElement(By.xpath(pro.getProperty("xpath.checkout"))).click();
Thread.sleep(3000);
//3 continue buttons
driver.findElement(By.xpath(pro.getProperty("continue1.xpath"))).click();
Thread.sleep(3000);
driver.findElement(By.xpath(pro.getProperty("continue.shipping.xpath"))).click();
Thread.sleep(3000);
driver.findElement(By.xpath(pro.getProperty("continue.shipping.method.xpath"))).click();
Thread.sleep(3000);
//agree checkbox
driver.findElement(By.xpath(pro.getProperty("agree.check.xpath"))).click();
Thread.sleep(3000);
driver.findElement(By.xpath(pro.getProperty("final.continue.xpath"))).click();
Thread.sleep(3000);
driver.findElement(By.id(pro.getProperty("confirmorder.id"))).click();

//navigating back
Thread.sleep(3000);
driver.navigate().back();
String valibck=driver.findElement(By.xpath(pro.getProperty("validate.back.xpath"))).getText();
String validaexpec="Your shopping cart is empty!";
Assert.assertEquals(valibck, validaexpec);

//order history
driver.findElement(By.partialLinkText(pro.getProperty("Order.History.partial"))).click();

//newsletter subscription
driver.findElement(By.xpath(pro.getProperty("newsletter.subs.xpath"))).click();
driver.findElement(By.partialLinkText(pro.getProperty("special.partial.text"))).click();

//logout
driver.findElement(By.partialLinkText(pro.getProperty("logout.partiallinktext"))).click();
String Logout=driver.getTitle();
Assert.assertEquals(Logout,"Account Logout");
System.out.println(Logout);;
		
 }

//********************************************************************

@DataProvider(name="searchfld")
public Object[][] srchfld() throws BiffException, IOException {
//TO read from excel
	 File search = new File("C://Users//AN252981//Desktop//extra docs//Selenium top//searchvalue.xls");
	   Workbook srcapp = Workbook.getWorkbook(search);
	   Sheet srcsht = srcapp.getSheet("Sheet1");
	   
// To get values from Excel 
	   int i=0;
	   int row= srcsht.getRows();
	   String[][] inputData= new String[1][1];
	   Cell c = srcsht.getCell(row-1,i);
	   inputData[i][0] = c.getContents();
	   System.out.println(c.getContents());  
	   return inputData;
}

@AfterClass 
public void Final(){
	
	driver.quit();
}

}


