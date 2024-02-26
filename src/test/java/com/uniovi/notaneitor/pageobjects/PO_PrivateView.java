package com.uniovi.notaneitor.pageobjects;

import com.uniovi.notaneitor.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PO_PrivateView extends PO_NavView {
    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep)
    {
        //Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
        //Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
        //Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }


    // metodo loguearse ya que se repite al principio de todas las pruebas
    // cuando haces algo --> doXXX()
    // va al formulario de login, rellena el formulario
    static public void doLogin(WebDriver driver, String usernamep, String passwordp){
        //Vamos al formulario de logueo.
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        //Rellenamos el formulario
        PO_LoginView.fillLoginForm(driver, usernamep, passwordp);
    }



    static public void checkIsStudent(WebDriver driver){
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
    }

    static public void checkIsProfessor(WebDriver driver, String type, String text){
        //Comprobamos que entramos en la página privada del Profesor
        PO_View.checkElementBy(driver, type, text);
    }

    static public void doLogout(WebDriver driver){
        //Ahora nos desconectamos comprobamos que aparece el menú de registrarse
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }


    static public void doClickMenuNotas(WebDriver driver){

        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'marks-menu')]/a
        //List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id, 'marksmenu')]/a");
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();

    }

    static public void doClickAñadirNotas(WebDriver driver, String descriptionp, String scorep){

        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'marks-menu')]/a
        doClickMenuNotas(driver);

        // -- añadir la nota

        //Esperamos a que aparezca la opción de añadir nota: //a[contains(@href, 'mark/add')]
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/add')]");
        //Pinchamos en agregar Nota
        elements.get(0).click();

        //rellenar la nota
        String checkText = "Nota sistemas distribuidos";
        PO_PrivateView.fillFormAddMark(driver, 3, descriptionp, scorep);

    }


    static public void doClickListNotas(WebDriver driver){

        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'marks-menu')]/a
        doClickMenuNotas(driver);

        //Pinchamos en la opción de lista de notas
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/list')]");
        elements.get(0).click();
    }

    static public void irAPagina(WebDriver driver, int numPag){
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        elements.get(numPag).click();
    }

    static public void existeNota(WebDriver driver, String text){

        List<WebElement> elements = PO_View.checkElementBy(driver, "text", text);
        Assertions.assertEquals(text, elements.get(0).getText());

    }

    static public void borrarNota(WebDriver driver, String text){
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//td[contains(text(), '" + text + "')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
        elements.get(0).click();
        irAPagina(driver, 4);
        //Y esperamos a que NO aparezca la última "Creando una nota nueva"
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, text,PO_View.getTimeout());
    }

    static public void contarNotas(WebDriver driver, int numeroEsperado){
        List<WebElement> marksList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr",
                PO_View.getTimeout());
        Assertions.assertEquals(numeroEsperado, marksList.size());
    }


}

