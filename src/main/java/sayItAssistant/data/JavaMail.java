package sayItAssistant.data;


import sayItAssistant.components.EmailConfig;
/*+----------------------------------------------------------------------
||
||  Class JavaMail
||
||         Author:  Bella Jeong
||
||        Purpose: Represents a class for sending email
||
|+-----------------------------------------------------------------------
||
||          Field:
||					fromEmail - email address of sender
||					password - password of sender
||					toEmail - email address of receiver
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					JavaMail() - default constructor
||					Creates JavaMail which displays fromEmail, password, and toEmail
||
||  Class Methods:
||					sendEmail() - methods to send email
||
++-----------------------------------------------------------------------*/
public class JavaMail {
    String fromEmail;
    String password;
    String toEmail;

    JavaMail() {
        EmailConfig emailDetails = new EmailConfig();
        this.fromEmail = emailDetails.getProperty("EmailAddress");
        this.password = emailDetails.getProperty("Password");
        this.toEmail = emailDetails.getProperty("SendEmail");
    }

    public static void sendEmail() {

    }
}
