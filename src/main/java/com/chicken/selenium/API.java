package com.chicken.selenium;

import com.chicken.selenium.util.SystemCheck;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;


public class API {
    public WebDriver startChrome() {
        String driverPath = this.getChromeDriverPath();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--test-type");
        chromeOptions.addArguments("--disable-extensions");
        File driverExe = new File(driverPath);
        ChromeDriverService service;
        if(driverExe.exists()) {
            if(!driverExe.canExecute()) {
                throw new Error("The Chrome driver located at " + driverPath + " is not executable.");
            }

            service = (new ChromeDriverService.Builder()).withSilent(true).usingAnyFreePort().usingDriverExecutable(driverExe).build();
        } else {
            service = ChromeDriverService.createDefaultService();
        }

        Runtime.getRuntime().addShutdownHook(new DriverServiceDestroyer(service));
        return new ChromeDriver(service, chromeOptions);
//        return getAPIImpl().startChromeImpl();
    }

    private String getChromeDriverPath() {
        String driverName;
        if(SystemCheck.isWindows()) {
            driverName = "chromedriver.exe";
        } else if(SystemCheck.isLinux()) {
            driverName = "chromedriver" + (SystemCheck.is64bit()?"_x64":"");
        } else {
            assert SystemCheck.isOSX();

            driverName = "chromedriver";
        }
        return "D:\\Softwares\\helium-java-1.8.6\\webdrivers\\" + driverName;
//        return this.resourceLocator.locate(new String[]{"webdrivers", driverName});
    }
}
