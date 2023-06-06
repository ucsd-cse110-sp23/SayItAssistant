package sayItAssistant.components;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;



class FieldPanel extends JPanel {
    
    private JTextField firstNameField, lastNameField, displayNameField,emailAddressField, SMTPField, TLSPortField, emailPasswordField;
    
    public FieldPanel() {
        setLayout(new GridLayout(3, 2));
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();
        add(firstNameLabel );
        add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();
        add(lastNameLabel);
        add(lastNameField);

        JLabel displayNameLabel = new JLabel("Display Name:");
        displayNameField = new JTextField();
        add(displayNameLabel);
        add(displayNameField);


        JLabel emailAddressLabel = new JLabel("Email Address:");
        emailAddressField = new JTextField();
        add(emailAddressLabel);
        add(emailAddressField);

        JLabel SMTPLabel = new JLabel("SMTP:");
        SMTPField = new JTextField();
        add(SMTPLabel);
        add(SMTPField);

        ////emailPasswordField;
        JLabel TLSPortLabel = new JLabel("TLS Port:");
        TLSPortField = new JTextField();
        add(emailAddressLabel);
        add(emailAddressField);

        JLabel emailpasswordLabel = new JLabel("Password:");
        emailPasswordField = new JTextField();
        add(emailpasswordLabel);
        add(emailPasswordField);

        JLabel verifyLabel = new JLabel("Verify Password:");
        verifyField = new JTextField();
        add(verifyLabel);
        add(verifyField);
    }
    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getVerify() {
        return verifyField.getText();
    }
}

public class Email  extends JFrame {
   
    private FieldPanel fieldPanel;
    public 

    
}
