package com.aqa;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class ShareLaneTests extends BaseTest{
    private void fillAllSignInFieldsAndClick() {
        driver.findElement(By.name("first_name")).sendKeys("TestName");
        driver.findElement(By.name("last_name")).sendKeys("TestFamily");
        driver.findElement(By.name("email")).sendKeys("test@gmail.com");
        driver.findElement(By.name("password1")).sendKeys("Test123456Psw");
        driver.findElement(By.name("password2")).sendKeys("Test123456Psw");
        driver.findElement(By.cssSelector("input[value='Register']")).click();
    }

    private static WebElement getZip_code(By xpath) {
        WebElement zip_code = driver.findElement(xpath);
        return zip_code;
    }

    @Test
    public static void zipCodeShouldBeValid() {
        WebElement zip_code = getZip_code(By.xpath("//input[@name='zip_code']"));
        zip_code.sendKeys("12345", Keys.ENTER);
        WebElement registerButton = getZip_code(By.cssSelector("input[value='Register']"));
        assertTrue(registerButton.isDisplayed(),
                "User was not redirected to the registration page (Redirection issue)");
    }

    @Test
    public static void zipCodeShouldNotBeEmpty() {
        driver.findElement(By.cssSelector("input[value='Continue']")).click();
        WebElement errorMessage = getZip_code(By.className("error_message"));
        assertTrue(errorMessage.isDisplayed(),
                "Error message is not displayed in case of empty zip code");
        assertEquals(errorMessage.getText(),
                ZIP_CODE_ERROR);
    }

    @Test
    public static void userShouldNotBeRegisteredWithInValidZipCode() {
        driver.findElement(By.xpath("//input[@name='zip_code']")).sendKeys("1234");
        driver.findElement(By.cssSelector("input[value='Continue']")).click();
        WebElement message = getZip_code(By.cssSelector(".error_message"));
        Assert.assertTrue(message.isDisplayed());
    }

    @Test
    public static void userShouldNotBeRegisteredWithAAAAaZipCode() {
        final WebElement elementZip = driver.findElement(By.xpath("//input[@name='zip_code']"));
        elementZip.sendKeys("AAAAA");
        driver.findElement(By.cssSelector("input[value='Continue']")).click();
        WebElement message = getZip_code(By.cssSelector(".error_message"));
        Assert.assertTrue(message.isDisplayed());
    }

    @Test
    public void fieldEmailIsRequired(){
        getZip_code(By.xpath("//input[@name='zip_code']")).sendKeys("12345");
        driver.findElement(By.cssSelector("input[value='Continue']")).click();
        driver.findElement(By.name("first_name")).sendKeys("TestLogin");
        driver.findElement(By.name("password1")).sendKeys("Test123456Psw");
        driver.findElement(By.name("password2")).sendKeys("Test123456Psw");
        driver.findElement(By.cssSelector("input[value='Register']")).click();
        WebElement message = driver.findElement(By.cssSelector(".error_message"));

        assertTrue(message.isDisplayed(), "Поле Email не прошло проверку на обязательность");
    }

    @Test
    public void validSignUp(){
        getZip_code(By.xpath("//input[@name='zip_code']")).sendKeys("12345");
        driver.findElement(By.cssSelector("input[value='Continue']")).click();
        fillAllSignInFieldsAndClick();

        String confirmation_message = driver.findElement(By.className("confirmation_message")).getText();
        assertEquals(confirmation_message, "Account is created!");
        System.out.println();

    }
}
