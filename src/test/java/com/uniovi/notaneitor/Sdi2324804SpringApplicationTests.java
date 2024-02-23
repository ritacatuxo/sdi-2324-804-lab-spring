package com.uniovi.notaneitor;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Sdi2324804SpringApplicationTests {
	static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	//static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
	static String Geckodriver = "C:\\Users\\Rita Catucho\\Desktop\\segundo cuatri\\SDI\\laboratorios\\semana06\\PL-SDI-Sesión5-material\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";




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
	void PR01() {}

	@Test
	@Order(2)
	void PR02() {}

	@Test
	@Order(3)
	void PR03() {}

	@Test
	@Order(4)
	void PR04() {}

	@Test
	@Order(5)
	void PR05() {}



}
