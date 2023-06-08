
package sayItAssistant.components;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


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
    private EmailConfig emailDetails = new EmailConfig();
    public emailFieldPanel() {
        setLayout(new GridLayout(7,2));
        
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(emailDetails.getProperty("FirstName"));
        add(firstNameLabel);
        add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(emailDetails.getProperty("LastName"));
        add(lastNameLabel);
        add(lastNameField);

        JLabel displayNameLabel = new JLabel("Display Name:");
        displayNameField = new JTextField(emailDetails.getProperty("DisplayName"));
        add(displayNameLabel);
        add(displayNameField);

        JLabel emailAddressLabel = new JLabel("Email Address:");
        emailAddressField = new JTextField(emailDetails.getProperty("EmailAddress"));
        add(emailAddressLabel);
        add(emailAddressField);

        JLabel TLSPortLabel = new JLabel("TLS Port:");
        TLSPortField = new JTextField(emailDetails.getProperty("TLSPort"));
        add(TLSPortLabel);
        add(TLSPortField);

        JLabel emailpasswordLabel = new JLabel("Email Password:");
        emailPasswordField = new JTextField(emailDetails.getProperty("Password"));
        add(emailpasswordLabel);
        add(emailPasswordField);

        JLabel SMTPLabel = new JLabel("SMTP:");
        SMTPField = new JTextField(emailDetails.getProperty("SMTP"));
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
    JButton saveButton;
    JButton cancelButton;
    EmailConfig emailDetailConfig;
  
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
    private EmailConfig emailDetails;
    public Email(){
        this.setSize(800, 600);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // Close only setup window on exit
        this.setVisible(true);

        emailDetails = new EmailConfig();
         
        emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailPanel.setPreferredSize(new Dimension(500, 200));
        emailPanel.setLayout(new GridLayout(2, 4));  

        
        emailFieldPanel = new emailFieldPanel();
        emailPanel.add(emailFieldPanel);

        buttonPanel = new emailButtonPanel();
        emailPanel.add(buttonPanel);
        addListeners();

        this.add(emailPanel);
        revalidate();
    }    
    
    private void addListeners() {
        buttonPanel.saveButton.addActionListener(
            (ActionEvent e) -> {
                emailDetails.setEmailDetails(emailFieldPanel);
                emailDetails.store();
                this.setVisible(false);
            }
        );

        buttonPanel.cancelButton.addActionListener(
            (ActionEvent e) -> {
                this.setVisible(false);
            }
        );
    }
}

