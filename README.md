Prerequisites
Install Java (JDK 8 or above)
Install Maven
Install Node.js
Install Appium
Install Appium Inspector (optional)
Install Android Studio & Emulator

Start Appium Server
Configure device like: 
platformName=Android
deviceName=emulator-5554
app=/path/to/app.apk


Project Overview
This project contains automated test scripts for a mobile application focusing on Onboarding, 
Login, and OTP Verification flows. The framework is built using Appium with Java, following best practices 
like Page Object Model (POM) for maintainability and scalability.


Mobile Automation Appium Project Readme
Mobile Automation Framework (Appium + Java)
 Project Overview

This project contains automated test scripts for a mobile application focusing on Onboarding, Login, and OTP Verification flows. The framework is built using Appium with Java, following best practices like Page Object Model (POM) for maintainability and scalability.

 Features
Skip onboarding tutorial
Validate onboarding screens and navigation
Login with valid credentials
Login failure scenarios (invalid mobile/name/OTP)
OTP verification (6-digit validation)
Error message validation

Tech Stack
Language: Java
Automation Tool: Appium
Test Framework: TestNG
Build Tool: Maven
Design Pattern: Page Object Model (POM)
Platform: Android Emulator / Real Device

 Future Improvements
Integrate Allure Reporting
Add CI/CD (GitHub Actions / Jenkins)
Cross-platform support (iOS)
Data-driven testing

Best Practices Followed
Page Object Model (POM)
Reusable utilities
Clean code structure
Separation of concerns Contact


src
 └── test
      ├── java
      │    ├── base
      │    │    └── BaseTest.java
      │    ├── pages
      │    │    ├── OnboardingPage.java
      │    │    ├── LoginPage.java
      │    │    └── OtpPage.java
      │    ├── tests
      │    │    ├── OnboardingTest.java
      │    │    ├── LoginTest.java
      │    │    └── OtpTest.java
      │    └── utils
      │         ├── DriverManager.java
      │         ├── ConfigReader.java
      │         └── WaitUtils.java
      └── resources
           ├── config.properties
           └── testng.xml

 Framework Design
 Base Class (BaseTest.java)
 Initializes Appium driver
 Handles setup & teardown
 Common reusable methods

 Driver Manager
 Manages driver lifecycle
 Supports emulator/real device configuration

 Page Object Model (POM)
 Each screen is represented as a class:
 Locators stored separately
 Actions wrapped into methods

 Utilities
 WaitUtils → Explicit waits
 ConfigReader → Read config values
 DriverManager → Driver initialization logic

Author
Deependra Rao
 

