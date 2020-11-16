# Selenium UI automation sample project
The test suite contains selenium based automated test cases for https://www.lazada.sg ecommerce web portal. 
It validates main menu items, navigation to product com.lazada.tests.ui.pages and product data. 
Samples test cases have not been developed for all the scenarios. Any scenarios required can be added using excel data provider.
Following features are included in the project.

  - Selenium based UI navigation
  - Excel based data providers for test cases
  - TestNG parallel execution
  - Gradle managed project dependency and artifacts
  - Run on Chrome browser (v86)

# How to run test cases
The test suite can be run in the same way as any testNG test case is run.

## Using IDE
Bellow steps are for IntellijIdea based test execution.
  - Open build.gradle using the IDE and open the file as a project.This will execute necessary steps to attach the code to the IDE and prepare development environment.
  - Provide JDK ( Higher than java 7 ) when IDE requests it.
  - Internet connectivity is required as the dependencies are downloaded when the project is built.
  - Correct webdriver (chromedriver version matching your browser version) for selenium should be added to src/test/resources/drivers
  - A VM option is required to be added in Edit configuration-->templates-->TestNG -->VM options : -ea -Dtestng.dtd.http=true
  - Right click on testng.xml and create a run configuration for tesng.xml. Select 'In whole project' in the dialog box opened.
  - Select above run configuration and run the tests.
  