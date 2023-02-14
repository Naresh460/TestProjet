package com;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class TelanaganaRegistarion2 {
	static WebDriver driver;
	static File file;
	static String dateName;
	static String subFolder;
	DateFormat dateFormat_report = new SimpleDateFormat("dd-mm-yyyy h-m-s");
	Date date_report = new Date();		
	
	
	@AfterMethod
	public void tearDDown() {			
		driver.quit();
	}

	@Test
	public void getEC() throws InterruptedException, IOException {
		System.out.println("Test Suite");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));		
		driver.get("https://registration.telangana.gov.in");
		Thread.sleep(5000);
		String parentwindoww = driver.getWindowHandle();		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div[1]/div[2]/div/div/div/div[4]/a")).click();
		Thread.sleep(3000);
		Set<String> childw = driver.getWindowHandles();
		for (String childd : childw) {
			if (!parentwindoww.equalsIgnoreCase(childd)) {
				// System.out.println(childw);
				driver.switchTo().window(childd);
				driver.findElement(By.xpath("//button[@type='button']")).click();
				// driver.get("https://registration.telangana.gov.in/EncumbranceCertificate/Search_Document.htm");
				Thread.sleep(3000);
				driver.findElement(By.id("doct")).sendKeys("10476");
				driver.findElement(By.id("regyear")).sendKeys("2021");
				driver.findElement(By.id("sroVal")).sendKeys("1528");
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@id=\"ui-id-1\"]/li")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[@id=\"docno\"]/div[4]/button")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//button[@class='btn']")).click();
				Thread.sleep(5000);
				driver.findElement(By.xpath("//*[@id=\"bean\"]/div[3]/div[13]/button[1]")).click();
				Thread.sleep(3000);
				driver.findElement(By.id("checkall2")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//button[text()='Submit']")).click();
				Thread.sleep(3000);

				screenShot();
				sendEmail();

			}
		}

	}

	public static void sendEmail() {
		
		
		String from = "bluebird.460@gmail.com";
		String to = "nareshhyd0@gmail.com";
		

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		// Get the Session object.// and pass username and password

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("bluebird.460@gmail.com", "svzvmtgwynvyvxgq");

			}

		});

		// Used to debug SMTP issues
		session.setDebug(true);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("My Land EC");

			// Now set the actual message
			// message.setText("This is actual message");

			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText("Please find the attached EC for the Day" +dateName);

			// 4) create new MimeBodyPart object and set DataHandler object to this object
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			//String filename = "file"; // change accordingly
			DataSource source = new FileDataSource(file);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(dateName);
			
			// 5) create Multipart object and add MimeBodyPart objects to this object
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);

			message.setContent(multipart);

			System.out.println("sending...");
			// Send message
			Transport.send(message);
			
		} catch (MessagingException mex) {			
			mex.printStackTrace();
		
		}
	}
	
	public String screenShot() throws IOException {
		
		dateName = new SimpleDateFormat("dd-MM-YYYY-hhmmss").format(new Date());
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1500))
				.takeScreenshot(driver);
		file = new File(System.getProperty("user.dir") + "\\Screenshots\\EC-" + dateName + ".jpeg");
		ImageIO.write(screenshot.getImage(), "JPEG", file);
		System.out.println("Screenshot taken");
		return file.getAbsolutePath();
		
	}

}
