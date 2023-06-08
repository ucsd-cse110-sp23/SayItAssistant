package sayItAssistant.mocking;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import sayItAssistant.data.LoginConfig;

public class MockLoginConfig extends LoginConfig{
	
    private Properties properties;
    String configPath = "./mockingData/LoginConfigTest.properties";

    public MockLoginConfig() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(configPath);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getProperty(String Key) {
        return properties.getProperty(Key);
    }
    
    @Override
    public void setProperty(String Key, String Value) {
        properties.setProperty(Key, Value);
    }
    
    @Override
    public void store() {
        try {
            properties.store(new FileOutputStream(configPath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
