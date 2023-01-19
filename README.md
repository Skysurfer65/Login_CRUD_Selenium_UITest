# Login Selenium UI Test
Selenium automation of html/javascript login app. The login app is called "Login_CRUD" and
is available as one of my other public repositories.
This Selenium automation is written in **Java Maven** with IntelliJ IDEA
using:
- EdgeDriver
- ChromeDriver
 
Passing a WebDriver as a parameter into different function makes it possible
to expand tests to numerous of web browsers. Login_CRUD has been developed for Microsoft Edge.

# Instructions
**Run main function and watch the spectacle.** I've slowed down the interaction with Thread.sleep()
just to be able to follow events. These could be removed. After completion a logfile could
be followed by reading output in the console or .txt file in folder "logTxt".

Make sure to set paths to your local msedgedriver.exe and chromedriver.exe, these has to be downloaded
from Microsoft and google.

Also make sure to have correct path to Login_CRUD, **index.html**

You could run it in any IDE with support for Java 16 and the important files are **Login_CRUD_Selenium_Test.java**
and the **pom.xml**

# About CI with GitHub Actions
This is WIP.<br/>
At the moment the test VM is built but Selenium Test class don't actually have any "asserts".
In this scenario Selenium is automating GUI interaction and the result is printed in terminal and written
to a log file.

# Author
Richard Fehling, student at EC Utbildning, MVT22<br/>
richard.fehling@learnet.se
