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
    String TLSPort;
    String SMTPHost;

    public JavaMail() {
        EmailConfig emailDetails = new EmailConfig();
        this.fromEmail = emailDetails.getProperty("EmailAddress");
        this.password = emailDetails.getProperty("Password");
        this.toEmail = emailDetails.getProperty("SendEmail");
        this.TLSPort = emailDetails.getProperty("TLSPort");
        this.SMTPHost = emailDetails.getProperty("SMTP");
    }

    public void sendEmail(String subject) {
        Properties props = new Properties();
        props.put("mail.smtp.host", this.SMTPHost);
        props.put("mail.smtp.port", this.TLSPort );
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toEmail, "Message From SayIt Assistant 2", subject);
    }
}
