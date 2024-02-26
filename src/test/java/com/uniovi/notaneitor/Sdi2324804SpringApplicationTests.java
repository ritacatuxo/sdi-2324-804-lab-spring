package com.uniovi.notaneitor;

import com.uniovi.notaneitor.pageobjects.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2324804SpringApplicationTests {
	static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	//static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
	static String Geckodriver = "C:\\Users\\UO284185\\Downloads\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";




	static WebDriver driver = getDriver(PathFirefox, Geckodriver);
	static String URL = "http://localhost:8090";
	public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckodriver);
		driver = new FirefoxDriver();
		return driver;
	}

	@BeforeEach
	public void setUp(){
		driver.navigate().to(URL);
	}
	//Después de cada prueba se borran las cookies del navegador
	@AfterEach
	public void tearDown(){
		driver.manage().deleteAllCookies();
	}
	//Antes de la primera prueba
	@BeforeAll
	static public void begin() {}
	//Al finalizar la última prueba
	@AfterAll
	static public void end() {
		//Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}




	// 10 pruebas básicas

	@Test
	@Order(1)
	void PR01A() {
		PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
	}

	@Test
	@Order(2)
	void PR02B() {
		List<WebElement> welcomeMessageElement = PO_HomeView.getWelcomeMessageText(driver,
				PO_Properties.getSPANISH());
		Assertions.assertEquals(welcomeMessageElement.get(0).getText(),
				PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH()));
	}

	@Test
	@Order(3)
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
	}
	//PR03. Opción de navegación. Pinchar en el enlace Identifícate en la página home
	@Test
	@Order(4)
	public void PR03() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	}

	//PR04. Opción de navegación. Cambio de idioma de Español a Inglés y vuelta a Español
	@Test
	@Order(5)
	public void PR04() {
		PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish",
				PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
	}

	//PR05. Prueba del formulario de registro. registro con datos correctos
	@Test
	@Order(6)
	public void PR05() {
		//Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		//Rellenamos el formulario.
		PO_SignUpView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");
		//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
		String checkText = "Notas del usuario";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}

	//PR06A. Prueba del formulario de registro. DNI repetido en la BD
	// Propiedad: Error.signup.dni.duplicate
	@Test
	@Order(7)
	public void PR06A() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.duplicate",
				PO_Properties.getSPANISH() );
		//Comprobamos el error de DNI repetido.
		String checkText = PO_HomeView.getP().getString("Error.signup.dni.duplicate",
				PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}

	//PR06B. Prueba del formulario de registro. Nombre corto.
	// Propiedad: Error.signup.dni.length
	@Test
	@Order(8)
	public void PR06B() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length",
				PO_Properties.getSPANISH() );
		//Comprobamos el error de Nombre corto de nombre corto .
		String checkText = PO_HomeView.getP().getString("Error.signup.name.length",
				PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}

	//PR06C. Prueba del formulario de registro. DNI corto.
	// Propiedad: Error.signup.dni.length
	@Test
	@Order(9)
	public void PR06C() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "9999", "Josefo", "Perez", "77777", "77777");
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.length",
				PO_Properties.getSPANISH() );
		//Comprobamos el error de Nombre corto de dni corto .
		String checkText = PO_HomeView.getP().getString("Error.signup.dni.length",
				PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}

	//PR06D. Prueba del formulario de registro. Constraseña corta.
	// Propiedad: Error.signup.dni.length
	@Test
	@Order(10)
	public void PR06D() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_SignUpView.fillForm(driver, "99999990B", "Josefo", "Perez", "7", "7");
		List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.password.length",
				PO_Properties.getSPANISH() );
		//Comprobamos el error de Nombre corto de contraseña corta .
		String checkText = PO_HomeView.getP().getString("Error.signup.password.length",
				PO_Properties.getSPANISH());
		Assertions.assertEquals(checkText , result.get(0).getText());
	}


	//PR07 - Identificación válida con usuario de ROL usuario
	@Test
	@Order(11)
	public void PR07() {
		//Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario
		PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
		//Comprobamos que entramos en la pagina privada de Alumno
		String checkText = "Notas del usuario";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}


	//PR08 - Identificación válida con usuario de ROL profesor
	@Test
	@Order(12)
	public void PR08() {
		//Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario.
		PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
		//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
		String checkText = "Notas del usuario";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}

	//PR09 - Identificación válida con usuario de ROL Administrador
	@Test
	@Order(13)
	public void PR09() {
		//Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario.
		PO_LoginView.fillLoginForm(driver, "99999988F", "123456");
		//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
		String checkText = "Notas del usuario";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}

	//PR10 - Identificación inválida con usuario de ROL alumno
	@Test
	@Order(14)
	public void PR10() {
		//Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario.
		PO_LoginView.fillLoginForm(driver, "99999990", "123456");
		//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
		String checkText = "Identifícate";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());
	}

	//PR11 - Identificación válida y desconexión con usuario de ROL usuario
	@Test
	@Order(15)
	public void PR11() {

		// --- identificación válida

		//Vamos al formulario de login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//Rellenamos el formulario.
		PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
		//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
		String checkText = "Notas del usuario";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());

		// --- desconexión
		// click en el botón de logout
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");

		checkText = "Identifícate";
		result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());

	}


	//PR12. Loguearse, comprobar que se visualizan 4 filas de notas y desconectarse usando el rol de estudiante
	@Test
	@Order(16)
	public void PR12() {
		// login
		PO_PrivateView.doLogin(driver, "99999990A", "123456");

		// Comprobamos que entramos en la pagina privada del Profesor
		PO_PrivateView.checkIsStudent(driver);

		// comprobar el número de filas de notas
		PO_PrivateView.contarNotas(driver, 4);

		// desconectarse
		PO_PrivateView.doLogout(driver);
	}

	//PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion = Nota A2.
	@Test
	@Order(17)
	public void PR13() {
		//login
		PO_PrivateView.doLogin(driver, "99999990A", "123456");

		// Comprobamos que entramos en la pagina privada del student
		PO_PrivateView.checkIsStudent(driver);

		//Contamos las notas
		By enlace = By.xpath("//td[contains(text(), 'Nota A4')]/following-sibling::*[2]");
		driver.findElement(enlace).click();

		//Esperamos por la ventana de detalle
		String checkText = "Detalles de la nota";
		List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
		Assertions.assertEquals(checkText, result.get(0).getText());

		// logout
		PO_PrivateView.doLogout(driver);
	}

	//P14. Loguearse como profesor y Agregar Nota A2.
	@Test
	@Order(18)
	public void PR14() {
		//login
		PO_PrivateView.doLogin(driver, "99999993D", "123456");

		// Comprobamos que entramos en la pagina privada del Profesor
		PO_PrivateView.checkIsProfessor(driver,"text", "99999993D");

		// añadir nota
		String checkText = "Nota sistemas distribuidos";
		PO_PrivateView.doClickAñadirNotas(driver,  checkText, "8");

		// ir a la ultima pagina
		PO_PrivateView.irAPagina(driver, 4);

		//Comprobamos que aparece la nota en la página
		PO_PrivateView.existeNota(driver, checkText);

		// logout
		PO_PrivateView.doLogout(driver);
	}


	// Eliminar una nota
	@Test
	@Order(19)
	public void PR15() {

		//login
		PO_PrivateView.doLogin(driver, "99999993D", "123456");

		// Comprobamos que entramos en la pagina privada del Profesor
		PO_PrivateView.checkIsProfessor(driver,"text", "99999993D");

		// listamos las notas
		PO_PrivateView.doClickListNotas(driver);

		// ir a la ultima pagina
		PO_PrivateView.irAPagina(driver, 4);

		// eliminar la nota
		PO_PrivateView.borrarNota(driver, "Nota sistemas distribuidos");

		// logout
		PO_PrivateView.doLogout(driver);
	}




	}
