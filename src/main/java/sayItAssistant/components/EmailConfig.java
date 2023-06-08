package sayItAssistant.components;

import sayItAssistant.components.Email;

public class EmailConfig extends Config {

    public EmailConfig() {
        super("./DB/email.properties");
    }

    public void setEmailDetails(emailFieldPanel details) {
        this.setProperty("LastName", details.getLastName());
        this.setProperty("FirstName", details.getFirstName());
        this.setProperty("DisplayName", details.getdisplayName());
        this.setProperty("EmailAddress", details.getEmailAddress());
        this.setProperty("SMTP", details.getSMTP());
        this.setProperty("TLSPort", details.getTLSPort());
        this.setProperty("Password", details.getEmailPassword());
    }

    public void setSendEmailEmpty() {
        this.setProperty("SendEmail", "");
    }
    
}
