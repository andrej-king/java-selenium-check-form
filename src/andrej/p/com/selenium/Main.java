package andrej.p.com.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Main {

    public static void main(String[] args) {
        long timeOut = 20; // seconds
        int allowErrorSize = 2;
        String site = "https://prelive.aptimea.com/form/questionnaire?current=/webform/questionnaire";
        String[] allowErrors = new String[]{"Le champ Mot de passe est requis.", "Le champ Votre email pour sauvegarder vos réponses est requis."};

        String genderId             = "edit-je-suis-0";
        String birthDropDownId      = "edit-je-suis-ne-e-en-annee-year";
        String sportTimeId          = "edit-je-fais-du-sport-chaque-semaine-2";
        String foodPreferencesId    = "edit-je-suis-2-2";
        String supplementsId        = "edit-mes-traitements-medicaux-sont";
        String weightId             = "edit-user-weight";
        String heightId             = "edit-user-height";
        String residenceId          = "edit-je-vis-0";
        String childrenId           = "edit-j-ai-enfants-nombre-";
        String goalId               = "edit-patient-goals-28";
        String fnameId              = "edit-first-name";
        String lnameId              = "edit-last-name";
        String cookieClassName      = "agree-button";
        String btnNextId            = "edit-wizard-next";

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";

        System.setProperty("webdriver.gecko.driver", "D://Selenium_jars_and_drivers/drivers/firefox/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeOut);

        try {
            driver.get(site);
            driver.manage().window().maximize();

            if (isElementPresent(driver, By.className(cookieClassName))) {
                wait.until(presenceOfElementLocated(By.className(cookieClassName))).click();
                driver.findElement(By.className(cookieClassName)).click();
                Thread.sleep(1000); // sleep for close cookie window
            }

            WebElement gender           = driver.findElement(By.id(genderId));
            Select birthDropDown        = new Select(driver.findElement(By.id(birthDropDownId)));
            WebElement sportTime        = driver.findElement(By.id(sportTimeId));
            WebElement foodPreferences  = driver.findElement(By.id(foodPreferencesId));
            WebElement supplements      = driver.findElement(By.id(supplementsId));
            WebElement weight           = driver.findElement(By.id(weightId));
            WebElement height           = driver.findElement(By.id(heightId));
            WebElement residence        = driver.findElement(By.id(residenceId));
            WebElement children         = driver.findElement(By.id(childrenId));
            WebElement goal             = driver.findElement(By.id(goalId));
            WebElement fname            = driver.findElement(By.id(fnameId));
            WebElement lname            = driver.findElement(By.id(lnameId));
            WebElement btnNext          = driver.findElement(By.id(btnNextId));


            gender.click();
            birthDropDown.selectByValue("1986");
            sportTime.click();
            foodPreferences.click();
            residence.click();
            goal.click();
            weight.sendKeys("80");
            height.sendKeys("180");
            children.sendKeys("1");
            supplements.sendKeys("442");
            fname.sendKeys("John");
            lname.sendKeys("Doe");
            btnNext.click();

            try {
                WebElement errorMessages = wait.until(presenceOfElementLocated(By.cssSelector(".messages__wrapper")));
                List<WebElement> errorList = errorMessages.findElements(By.tagName("li"));
                if (!checkErrors(errorList, allowErrors, allowErrorSize)) {
                    for (WebElement li : errorList) {
                        System.out.println(ANSI_RED + li.getText());
                    }
                } else {
                    System.out.println(ANSI_GREEN + "Success");
                }
                System.out.print(ANSI_RESET);
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            driver.close();
        }
    }

    // Checking for errors including valid
    public static boolean checkErrors(List<WebElement>  errorList, String[] allowErrors, int allowErrorSize) {
        int errorsSize = errorList.size();

        if (errorsSize == allowErrorSize) {
            boolean contains;
            for (WebElement li : errorList) {
                contains = Arrays.asList(allowErrors).contains(li.getText());
                if (!contains) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    // Checking for the existence of an element
    public static boolean isElementPresent(WebDriver driver, By by){
        try{
            driver.findElement(by);
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }
}
