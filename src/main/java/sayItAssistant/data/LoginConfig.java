package sayItAssistant.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import sayItAssistant.components.Config;

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

public class LoginConfig extends Config {
    
    public LoginConfig() {
        super("./DB/login.properties");
    }
}
