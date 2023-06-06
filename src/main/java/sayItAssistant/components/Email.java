
package sayItAssistant.components;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;



class emailFieldPanel extends JPanel {
    
    private JTextField firstNameField, lastNameField, displayNameField,emailAddressField, SMTPField, TLSPortField, emailPasswordField;
    
    public emailFieldPanel() {
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

        JLabel TLSPortLabel = new JLabel("TLS Port:");
        TLSPortField = new JTextField();
        add(TLSPortLabel);
        add(TLSPortField);

        JLabel emailpasswordLabel = new JLabel("Email Password:");
        emailPasswordField = new JTextField();
        add(emailpasswordLabel);
        add(emailPasswordField);
    }
    
    public String getLastName() {
        return lastNameField.getText();
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getdisplayName() {
        return displayNameField.getText();
    }

    public String getEmailAddress() {
        return emailAddressField.getText();
    }

    public String getSMTP() {
        return SMTPField.getText();
    }

    public String getTLSPort() {
        return TLSPortField.getText();
    }

    public String getEmailPassword() {
        return emailPasswordField.getText();
    }
}

public class Email  extends JFrame {
    public static JPanel emailPanel;
    private emailFieldPanel emailFieldPanel;
    public Email(){
         
        emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailPanel.setPreferredSize(new Dimension(500, 200));
        emailPanel.setLayout(new GridLayout(4, 2));  

        
        emailFieldPanel = new emailFieldPanel();
        emailPanel.add(emailFieldPanel);
        this.add(emailPanel);
        revalidate();
    }   
    
    
}

