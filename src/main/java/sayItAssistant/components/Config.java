package sayItAssistant.components;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
/*+----------------------------------------------------------------------
||
||  Class
||    Config
||
||        Purpose: Serves as the component for the Config on the UI
||
|+-----------------------------------------------------------------------
||
||          Field:
||					properties - properties
||					configPath - path to config
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					Config(String configPath) - default constructor
||					Creates Config which displays properties and configPath
||
||  Class Methods:
||					getProperty(String key) - methods to get the property
||					setProperty(String key, String value) - methods to set the property
||					store() - methods to store the property
||
++-----------------------------------------------------------------------*/


public abstract class Config {
    private Properties properties;
    private String configPath;

    public Config(String configPath) {
        this.configPath = configPath;
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(configPath);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void store() {
        try {
            properties.store(new FileOutputStream(configPath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
