package sayItAssistant.components;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/*+----------------------------------------------------------------------
||
||  Class LoginConfig
||
||        Purpose: Handles .properties file to determine if auto login
||                 is appropriate
||
|+-----------------------------------------------------------------------
||
||          Field:
||					configPath: Path of login.properties file. 
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					LoginConfig() - Defualt Constructor
||                  Opens and reads login.properties.
||
||  Class Methods:
||					getProperty - returns value of specified key
||                  setProperty - sets value of specified key to specified
||                                value
||                  store - writes new property values to login.properties file 
||
++-----------------------------------------------------------------------*/

public class LoginConfig {
    private Properties properties;
    String configPath = "./DB/login.properties";

    public LoginConfig() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(configPath);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String Key) {
        return properties.getProperty(Key);
    }

    public void setProperty(String Key, String Value) {
        properties.setProperty(Key, Value);
    }

    public void store() {
        try {
            properties.store(new FileOutputStream(configPath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
