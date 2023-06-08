package sayItAssitantTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sayItAssistant.components.LoginConfig;
import sayItAssistant.mocking.MockLoginConfig;

class LoginConfigTest {

	@BeforeEach
	void setUp() throws Exception {
		File dbFile = new File("./mockingData/LoginConfigTest.properties");
		FileWriter fw = new FileWriter(dbFile);
		fw.write("#Wed Jun 07 22:21:31 PDT 2023\n");
		fw.flush();
		fw.write("autoLoginEnabled=false\n");
		fw.flush();
		fw.write("password=\n");
		fw.flush();
		fw.write("username=\n");
		fw.flush();
		fw.close();
	}

	@AfterEach
	void tearDown() throws Exception {
		File dbFile = new File("./mockingData/LoginConfigTest.properties");
		dbFile.delete();
	}
	
	// Test if constructor loads the proper data from .properties file
	@Test
	void constructorTest() {
		LoginConfig con = new MockLoginConfig();
		assertEquals("false", con.getProperty("autoLoginEnabled"));
		assertEquals("", con.getProperty("password"));
		assertEquals("", con.getProperty("username"));
	}
	
	// Test if setter method properly set properties
	@Test
	void setterTest() {
		LoginConfig con = new MockLoginConfig();
		assertEquals("false", con.getProperty("autoLoginEnabled"));
		assertEquals("", con.getProperty("password"));
		assertEquals("", con.getProperty("username"));
		
		con.setProperty("autoLoginEnabled", "true");
		con.setProperty("username", "usertest1");
		con.setProperty("password", "1234");
		
		assertEquals("true", con.getProperty("autoLoginEnabled"));
		assertEquals("usertest1", con.getProperty("username"));
		assertEquals("1234", con.getProperty("password"));
	}
	
	// Test if store method properly save current data into .properties file
	@Test
	void storeTest() throws Exception {
		LoginConfig con = new MockLoginConfig();		
		con.setProperty("autoLoginEnabled", "true");
		con.setProperty("username", "usertest1");
		con.setProperty("password", "1234");
		con.store();
		
		con = new MockLoginConfig();
		assertEquals("true", con.getProperty("autoLoginEnabled"));
		assertEquals("usertest1", con.getProperty("username"));
		assertEquals("1234", con.getProperty("password"));
	}

}
