package sayItAssistant.components;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
