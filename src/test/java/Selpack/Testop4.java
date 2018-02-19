package Selpack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Testop4 {

	WebDriver driver;
	Properties pro;
	Properties pro1;
	List<WebElement> productlist;	
	String orderid;

//*********************************************************
	
  @BeforeClass
  public void Intial() throws IOException 
  {
  System.setProperty("webdriver.chrome.driver","D://drivers.sel//chromedriver.exe");
  driver = new ChromeDriver();
  File file = new File("C://Users//AN252981//workspace//Topgear//Objrepo.properties");	
  FileInputStream fis = new FileInputStream(file);
  pro = new Properties();
  pro.load(fis);
  File file1 = new File("C://Users//AN252981//workspace//opencartTestCase//Objrepo1.properties");	
  FileInputStream fis1 = new FileInputStream(file1);
  pro1 = new Properties();
  pro1.load(fis1);
  
  }
//***********************************************************
  @Test

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
  	   
 driver.findElement(By.linkText(pro.getProperty("Home.linktext"))).click();
  
 //featured product validation
 driver.findElement(By.xpath(pro.getProperty("featured.xpath"))).click();
 try
 {
 driver.findElement(By.xpath(pro.getProperty("related.products.xpath"))).click();
 }
	catch(Exception e)
	{
		System.out.println("No Related Products available");
	}
 driver.findElement(By.linkText(pro.getProperty("Home.linktext"))).click();
 Thread.sleep(6000);
 if(driver.findElement(By.xpath(pro.getProperty("Featured.products.xpath"))).isDisplayed())
 {
	productlist =driver.findElements(By.xpath(pro.getProperty("Featured.products.xpath")));
	System.out.println("No. of products:"  +productlist.size());
	} 
 
 int count=0;
	
	for(int i=1; i<=productlist.size();i++)
	{ 
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@class='box-product']/div["+i+"]/div[1]/a/img")).click();
		
	if(!(driver.findElement(By.xpath(pro.getProperty("Alltabs.xpath"))).getText().contains("Related Products")))
		{
		count++;
		}
		driver.navigate().back();
  }
	
	System.out.println("Products having no Related Products Count is :"  +count);
	
	 driver.findElement(By.xpath(pro.getProperty("featured.xpath"))).click();
	 driver.findElement(By.xpath(pro.getProperty("related.products.xpath"))).click();
	 driver.findElement(By.xpath(pro.getProperty("addtocart.realted.xpath"))).click();

//WebDriverWait wait = new WebDriverWait(driver,10);
 
//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pro.getProperty("shoppingcart.ribbon.xpath"))));
Thread.sleep(3000);
	 driver.findElement(By.xpath(pro.getProperty("shoppingcart.ribbon.xpath"))).click();
	 
//************************************************************************	 
//shopping cart page
	 
	WebElement clearquan= driver.findElement(By.name(pro.getProperty("quantity.clear")));
	clearquan.clear();
	clearquan.sendKeys(pro.getProperty("quan.valu"));
driver.findElement(By.xpath(pro.getProperty("update.img.xpath"))).click();

//validation for SCP
String phone =driver.findElement(By.xpath(pro.getProperty("iphone.xpath.shoppingcart"))).getText();
System.out.println(phone);
String modelname = driver.findElement(By.xpath(pro.getProperty("modelname.xpath.shoppingcart"))).getText();
System.out.println(modelname);
String quantitynum = driver.findElement(By.xpath(pro.getProperty("quantity.xpath.shoppingcart"))).getText();
System.out.println(quantitynum);
String unitprice = driver.findElement(By.xpath(pro.getProperty("unitprice.xpath.shoppingcart"))).getText();
System.out.println(unitprice);
String total= driver.findElement(By.xpath(pro.getProperty("total.xpath.shoppingcart"))).getText();
System.out.println(total);

List<String> SCvalues= new ArrayList<String>();
SCvalues.add(phone);
SCvalues.add(modelname);
SCvalues.add(quantitynum );
SCvalues.add(unitprice);
SCvalues.add(total);


driver.findElement(By.xpath(pro.getProperty("checkout.xpath"))).click();
Thread.sleep(3000);

//continue
driver.findElement(By.id(pro.getProperty("continue.id"))).click();
Thread.sleep(3000);
driver.findElement(By.id(pro.getProperty("delivery.continue.xpath"))).click();
Thread.sleep(3000);
driver.findElement(By.id(pro.getProperty("continue.shipping.id"))).click();
Thread.sleep(3000);
driver.findElement(By.name(pro.getProperty("terms.checkbox.name"))).click();
Thread.sleep(3000);
driver.findElement(By.id(pro.getProperty("continue.payment.id"))).click();
//**************************************************

//validation for checkout & product list
String Phonecart = driver.findElement(By.xpath(pro.getProperty("iphone.xpath.checkout"))).getText();
System.out.println(Phonecart);
String modelcart = driver.findElement(By.xpath(pro.getProperty("model.xpath.checkout"))).getText();
System.out.println(modelcart);
String quantycart = driver.findElement(By.xpath(pro.getProperty("quantity.xpath.checkout"))).getText();
System.out.println(quantycart);
String pricecart= driver.findElement(By.xpath(pro.getProperty("price.xpath.checkout"))).getText();
System.out.println(pricecart);
String totalcart= driver.findElement(By.xpath(pro.getProperty("total.xpath.checkout"))).getText();
System.out.println(totalcart);

List<String> checkoutvalues= new ArrayList<String>();
checkoutvalues.add(Phonecart);
checkoutvalues.add(modelcart);
checkoutvalues.add(quantycart );
checkoutvalues.add(pricecart);
checkoutvalues.add(totalcart);

File fil = new File("D:\\Data04.xlsx");
FileOutputStream ExcelOut= new FileOutputStream(fil);
XSSFWorkbook workbook = new XSSFWorkbook();
XSSFSheet sheet = workbook.createSheet();

for(int i=0;i<SCvalues.size();i++)
{
        sheet.createRow(i).createCell(0).setCellValue(SCvalues.get(i));
        }
   
        for (int i=0;i<checkoutvalues.size();i++)
{
sheet.getRow(i).createCell(1).setCellValue(checkoutvalues.get(i));
}
   for(int i=0;i<SCvalues.size();i++)
   {
   if(SCvalues.get(i).contentEquals(checkoutvalues.get(i)))
 
   {
   sheet.getRow(i).createCell(2).setCellValue("true");
   }
   else 
   {
   sheet.getRow(i).createCell(2).setCellValue("false");
   }
   
   }
   workbook.write(ExcelOut);
   ExcelOut.close();
   
Thread.sleep(3000);

driver.findElement(By.id(pro.getProperty("confirm.order.id"))).click();
driver.findElement(By.partialLinkText(pro1.getProperty("orderhistory.partialtxt"))).click();

//******************************************************************

//order history
driver.findElement(By.xpath(pro1.getProperty("view.xpath"))).click();
System.out.println("image clicked");	
Thread.sleep(3000);
String odernum = driver.findElement(By.xpath(pro1.getProperty("ordernum1.xpath"))).getText();
 System.out.println(odernum);
 try
 {
	  Assert.assertNotEquals(orderid, odernum);
 }
catch(Exception e)
	{
	driver.findElement(By.linkText("Logout")).click();
	System.out.println("Logout successful");
	}
driver.findElement(By.xpath(pro1.getProperty("return.xpath"))).click();

driver.findElement(By.id(pro1.getProperty("return.id"))).click();

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
