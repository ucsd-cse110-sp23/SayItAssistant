package sayItAssistant.data;


import sayItAssistant.components.EmailConfig;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

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
