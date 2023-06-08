package sayItAssistant.components;
/*+----------------------------------------------------------------------
||
||  Class 
||    EmailConfig
||
||        Purpose: Serves as the component for the EmailConfig on the UI
||
|+-----------------------------------------------------------------------
||
||          Field:
||					EmailConfig - email config
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					EmailConfig() - default constructor
||					Creates EmailConfig which displays email config
||
||  Class Methods:
||					setEmailDetails(emailFieldPanel details) - methods to set the email details
||					setSendEmailEmpty() - methods to set the send email empty
||
++-----------------------------------------------------------------------*/

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
