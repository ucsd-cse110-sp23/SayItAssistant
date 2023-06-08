package sayItAssistant.mocking;

import sayItAssistant.components.Config;

public class MockEmailConfig extends Config{

	
    public MockEmailConfig() {
    	super("./mockingData/email.properties");
    }


    public void setSendEmailEmpty() {
        this.setProperty("SendEmail", "");
    }
}
