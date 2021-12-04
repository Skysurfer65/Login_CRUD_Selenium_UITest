import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Login_CRUD_Selenium_Test {
    //Initiate driver variables
    WebDriver driverChrome;
    WebDriver driverEdge;
    //JavaScript executor
    JavascriptExecutor jSE;
    //Create array of users and passwords
    //goodUsers and goodPasswords has to be one element longer then badUsers and badPassword
    //to be able to do test of three attempts on valid userID
    String[] goodUsers = new String[]{"bax1", "Bax2", "admin", " spaces1 ", "Åäö20", "longUserID01234567890123456789"};
    String[] goodPasswords = new String[]{"Bax1#", "2aX#", "Bax3%", "40bAx?", "20Åäö&", "LongPass##012345"};
    String[] badUsers = new String[]{"", "axl", "richard", "adam1@", "baxen1#", "pat rik", "tooLongID0123456789012345678901"};
    String[] badPasswords = new String[]{"", "P1#", "password1#", "Pass#", " Password1#", "Pass word1#", "TooLongPass#34567"};
    String text = "";


    public WebDriver invokeEdge(){
        //Make instance of edgedriver
        System.setProperty("webdriver.edge.driver", "E:\\Selenium\\edgedriver_win64\\msedgedriver.exe");
        driverEdge = new EdgeDriver();
        //Setup browser
        driverEdge.manage().deleteAllCookies();
        driverEdge.manage().window().maximize();
        driverEdge.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driverEdge.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        //Get URL (local html file)
        driverEdge.get("C:\\Users\\Bax Musik\\Desktop\\Coders\\alg_övningar\\Login_CRUD\\src\\html\\index.html");
        //driverEdge.get("http://127.0.0.1:5500//Login_CRUD//src//html//index.html");
        return driverEdge;
    }

    public WebDriver invokeChrome(){
        //Make instance of chromedriver
        System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\chromedriver_win32\\chromedriver.exe");
        //Setup browser
        driverChrome = new ChromeDriver();
        driverChrome.manage().deleteAllCookies();
        driverChrome.manage().window().maximize();
        driverChrome.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driverChrome.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        //Get URL (local html file)
        driverChrome.get("C:\\Users\\Bax Musik\\Desktop\\Coders\\alg_övningar\\Login_CRUD\\src\\html\\index.html");
        return driverChrome;
    }

    public static void main(String[] args) throws InterruptedException{

        //Make intsance of this test class
        Login_CRUD_Selenium_Test browser = new Login_CRUD_Selenium_Test();
        //Invoke Edge
        WebDriver driver = browser.invokeEdge();
        //Create acoounts
        browser.createUser(driver);
        //Login
        browser.loginUser(driver);
        //Test error messages
        browser.errorMessages(driver);
        //Close driver
        driver.close();
        //Invoke Chrome and do same tests as above
        driver = browser.invokeChrome();
        browser.createUser(driver);
        browser.loginUser(driver);
        browser.errorMessages(driver);
        driver.close();
    }

    public void createUser(WebDriver driver) throws InterruptedException{
        Thread.sleep(1000);
        driver.findElement(By.id("createOrLogin")).click();
        //Create accounts
        try {
            for(int i = 0; i < goodUsers.length; i++){
                //Set data textfields
                driver.findElement(By.id("userID")).sendKeys(goodUsers[i]);
                driver.findElement(By.id("password")).sendKeys(goodPasswords[i]);
                Thread.sleep(100);
                driver.findElement(By.id("actionButton")).click();
                Thread.sleep(100);
                driver.findElement(By.id("reset")).click();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println("Exception while creating account");
        }
        //Instantiate javascript executor
        jSE = (JavascriptExecutor)driver;
        //Call js getUsers function
        Object test = jSE.executeScript("return getUsers();");
        //Print array of users
        text = "\nCreate account test passed and users array have the following objects:";
        text+= "\n**********************************************************************\n";
        text+= test;
        System.out.println(text);
        //Object test = jSE.executeScript("alert(GetUsers());");
        //errorMessages(driver);
    }

    public void loginUser(WebDriver driver) throws InterruptedException{
        Thread.sleep(500);
        driver.findElement(By.id("createOrLogin")).click();
        try {
            for(int i = 0; i < goodUsers.length; i++){
                //Set data in textfields
                driver.findElement(By.id("userID")).sendKeys(goodUsers[i]);
                driver.findElement(By.id("password")).sendKeys(goodPasswords[i]);
                Thread.sleep(100);
                driver.findElement(By.id("actionButton")).click();
                //If admin
                if (goodUsers[i].equalsIgnoreCase("admin")){
                    Thread.sleep(1000);
                    driver.findElement(By.id("logout")).click();
                }
                Thread.sleep(100);
                driver.findElement(By.id("reset")).click();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println("Exception while login");
        }
        text = "\nLogin with 6 user ID's and valid passwords success";
        text+= "\n**************************************************\n";
        System.out.println(text);
    }

    public void errorMessages(WebDriver driver) throws InterruptedException{
        String errorMsg = "";
        String warningMsg = "";
        Thread.sleep(1000);

        //Test "create account" error
        //**************************
        driver.findElement(By.id("createOrLogin")).click();
        //userID invalid
        try {
            for(int i = 0; i < badUsers.length; i++){
                //Set data in textfields
                driver.findElement(By.id("userID")).sendKeys(badUsers[i]);
                driver.findElement(By.id("password")).sendKeys(goodPasswords[i]);
                Thread.sleep(100);
                driver.findElement(By.id("actionButton")).click();
                Thread.sleep(100);
                Alert alert = driver.switchTo().alert();
                errorMsg = driver.switchTo().alert().getText();
                alert.accept();
                driver.findElement(By.id("reset")).click();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println("Exception while creating account, user ID");
        }
        text = "\nCreate account test passed, user ID errors";
        text+= "\n******************************************\n";
        text+= "User ID tested with user in use, to short, no number, \n";
        text+= "special character and empty. Resulting in the following msg.";
        System.out.println(text +"\n"+ errorMsg);

        //password invalid
        try {
            for(int i = 0; i < badPasswords.length; i++){
                //Set data in textfields
                driver.findElement(By.id("userID")).sendKeys(goodUsers[i]);
                driver.findElement(By.id("password")).sendKeys(badPasswords[i]);
                Thread.sleep(100);
                driver.findElement(By.id("actionButton")).click();
                Thread.sleep(100);
                Alert alert = driver.switchTo().alert();
                errorMsg = driver.switchTo().alert().getText();
                alert.accept();
                driver.findElement(By.id("reset")).click();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println("Exception while creating account, password");
        }
        text = "\nCreate account test passed, password errors";
        text+= "\n*******************************************\n";
        text+= "Password tested with to short, to long, no capital letter, no number \n";
        text+= "and empty space in password. Resulting in the following msg.";
        System.out.println(text +"\n"+ errorMsg);

        //Test login error
        //**************************
        driver.findElement(By.id("createOrLogin")).click();
        //Bad user ID
        try {
            for(int i = 0; i < badUsers.length; i++){
                //Set data in textfields
                driver.findElement(By.id("userID")).sendKeys(badUsers[i]);
                driver.findElement(By.id("password")).sendKeys(badPasswords[i]);
                Thread.sleep(100);
                driver.findElement(By.id("actionButton")).click();
                Thread.sleep(100);
                Alert alert = driver.switchTo().alert();
                errorMsg = driver.switchTo().alert().getText();
                alert.accept();
                driver.findElement(By.id("reset")).click();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println("Exception while login, user ID");
        }
        text = "\nLogin test, bad user ID";
        text+= "\n***********************\n";
        text+= "User ID tested with invalid user ID, to short, no number, no \n";
        text+= "special character and empty space. Resulting in the following msg.";
        System.out.println(text +"\n"+ errorMsg);

        //Bad password
        try {
            for(int i = 0; i < goodUsers.length - 1; i++){
                //Set data in textfields
                driver.findElement(By.id("userID")).sendKeys(goodUsers[i]);
                driver.findElement(By.id("password")).sendKeys(badPasswords[i]);
                Thread.sleep(100);
                driver.findElement(By.id("actionButton")).click();
                Thread.sleep(100);
                Alert alert = driver.switchTo().alert();
                errorMsg = driver.switchTo().alert().getText();
                alert.accept();
                driver.findElement(By.id("reset")).click();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println("Exception while login, password");
        }
        text = "\nLogin test, bad password";
        text+= "\n************************\n";
        text+= "Password tested with invalid password, to short, to long, no capital letter, \n";
        text+= "no number and empty space in password.. Resulting in the following msg.";
        System.out.println(text +"\n"+ errorMsg);

        //Bad password, three attempts on good userID
        try {
            for(int i = 0; i < 3 ; i++){
                //Set data in textfields
                driver.findElement(By.id("userID")).sendKeys(goodUsers[0]);
                driver.findElement(By.id("password")).sendKeys(badPasswords[i]);
                Thread.sleep(100);
                driver.findElement(By.id("actionButton")).click();
                Thread.sleep(100);
                if(i == 2){
                    warningMsg = driver.switchTo().alert().getText();
                } else{
                    errorMsg = driver.switchTo().alert().getText();
                }
                Alert alert = driver.switchTo().alert();
                alert.accept();
                driver.findElement(By.id("reset")).click();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.println("Exception while login, three times wrong password");
        }
        text = "\nLogin test, bad password";
        text+= "\n************************\n";
        text+= "Same user ID tested three times with invalid password, to short, to long, no capital letter, \n";
        text+= "no number and empty space in password.. Resulting in the following msg.";
        System.out.println(text +"\n"+ warningMsg +"\n"+ errorMsg);
    }
}