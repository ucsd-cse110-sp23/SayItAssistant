
package sayItAssistant.components;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;


/*+----------------------------------------------------------------------
||
||  Class Login
||
||        Purpose: Serves as the component for the Email Setup on the UI
||
|+-----------------------------------------------------------------------
||
||          Field: 
||					saveButton - button to save
||					cancelButton - button to cancel
||					fieldPanel - panel for the fields
||					buttonPanel - panel for the buttons
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					Email()- default constructor
||					Creates Email which displays fields and buttons
||
||  Class Methods:
||					getSaveButton() - methods to get the save button
||					getCanceButton() - methods to get the cancel button
||
++-----------------------------------------------------------------------*/
class emailFieldPanel extends JPanel {
    
    private JTextField firstNameField, lastNameField, displayNameField,emailAddressField, SMTPField, TLSPortField, emailPasswordField;
    
    public emailFieldPanel() {
        setLayout(new GridLayout(7,2));
        
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();
        add(firstNameLabel);
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

        JLabel TLSPortLabel = new JLabel("TLS Port:");
        TLSPortField = new JTextField();
        add(TLSPortLabel);
        add(TLSPortField);

        JLabel emailpasswordLabel = new JLabel("Email Password:");
        emailPasswordField = new JTextField();
        add(emailpasswordLabel);
        add(emailPasswordField);

        JLabel SMTPLabel = new JLabel("SMTP:");
        SMTPField = new JTextField();
        add(SMTPLabel);
        add(SMTPField);

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


    /*---------------------------------------------------------------------
    |  Constructor ButtonPanel()
    |
    |         Purpose: Creates the ButtonPanel
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize ButtonPanel component
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
class emailButtonPanel extends JPanel {
    private JButton saveButton, cancelButton;
  
    public emailButtonPanel() {
      saveButton = new JButton("Save");
      add(saveButton);
  
      cancelButton = new JButton("Cancel");
      add(cancelButton);
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}

/*---------------------------------------------------------------------
|  Constructor Login()
|
|         Purpose: Creates the Set Up Email
|
|   Pre-condition: None
|
|  Post-condition: Initialize Login component
|
|      Parameters: None
|
|         Returns: None
*-------------------------------------------------------------------*/
public class Email  extends JFrame {
    public static JPanel emailPanel;
    private emailButtonPanel buttonPanel;
    private emailFieldPanel emailFieldPanel;
    public Email(){
        this.setSize(1200, 1000); // 1200 width and 1000 height
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
        this.setVisible(true); // Make visible
         
        emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailPanel.setPreferredSize(new Dimension(500, 200));
        emailPanel.setLayout(new GridLayout(2, 4));  

        
        emailFieldPanel = new emailFieldPanel();
        emailPanel.add(emailFieldPanel);

        buttonPanel = new emailButtonPanel();
        emailPanel.add(buttonPanel);

        this.add(emailPanel);
        revalidate();
    }     
}

