# Selenium UI automation sample project
The test suite contains selenium based automated test cases for https://www.lazada.sg ecommerce web portal. 
It validates main menu items, navigation to product pages and product data. 
Samples test cases have not been developed for all the scenarios but using excel based data provider, most of the scenarios can be accomplized. 
Following features are included in the project.

  - Selenium based UI navigation
  - Excel based data providers for test cases
  - TestNG parallel execution
  - Gradle managed project dependency and artifacts

 

# How to run test cases
The test suite can be run in the same way as any testNG test case is run. 
Throughout the development phase of the test suite, Integrated development environment (IDE) based test execution was done most of the time. 
Bellow steps are mainly focus on IntellijIdea based test execution.
  - Open build.gradle using the IDE and open the file as a project.This will execute necessary steps to attach the code to the IDE and prepare development environment.
  - Provide JDK ( Higher than java 7 ) when IDE requests it.
  - Internet connectivity is required as the dependencies are downloaded when the project is built.
  - Correct webdriver ( firefox driver or chromedriver) for selenium should be added to src/test/resources/drivers
  - A run configuration for IntellijIdea is required to be added in Edit configuration-->templates-->TestNG -->VM options : -ea -Dtestng.dtd.http=true