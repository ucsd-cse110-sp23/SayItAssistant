package sayItAssistant.components;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

/*+----------------------------------------------------------------------
||
||  Class Login
||||
||        Purpose: Serves as the component for the Login on the UI
||
|+-----------------------------------------------------------------------
||
||          Field: 
||					loginButton - button to login
||					createButton - button to create account
||					fieldPanel - panel for the fields
||					buttonPanel - panel for the buttons
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					Login()- default constructor
||					Creates Login which displays fields and buttons
||
||  Class Methods:
||					getLoginButton() - methods to get the login button
||					getCreateButton() - methods to get the create button
||
++-----------------------------------------------------------------------*/


class FieldPanel extends JPanel {
    
    private JTextField emailField, passwordField, verifyField;
    
    public FieldPanel() {
        setLayout(new GridLayout(3, 2));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        add(emailLabel);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField();
        add(passwordLabel);
        add(passwordField);
        JLabel verifyLabel = new JLabel("Verify Password:");
        verifyField = new JTextField();
        add(verifyLabel);
        add(verifyField);
    }
    public String getEmail() {
        return emailField.getText();
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

class ButtonPanel extends JPanel {
    private JButton loginButton, createButton;
  
    public ButtonPanel() {
      loginButton = new JButton("Login");
      add(loginButton);
  
      createButton = new JButton("Create Account");
      add(createButton);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getCreateButton() {
        return createButton;
    }
}
 /*---------------------------------------------------------------------
    |  Constructor Login()
    |
    |         Purpose: Creates the Login
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize Login component
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/

public class Login extends JPanel {
    private JButton loginButton, createButton;
    private FieldPanel fieldPanel;
    private ButtonPanel buttonPanel;
    public static JPanel LoginPanel;
    public static JTextArea successText;
  
    public Login() { 
        LoginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        LoginPanel.setPreferredSize(new Dimension(500, 200));
        LoginPanel.setLayout(new BorderLayout());
        LoginPanel.setLayout(new GridLayout(3, 2));

        fieldPanel = new FieldPanel();
        LoginPanel.add(fieldPanel);

        buttonPanel = new ButtonPanel();
        LoginPanel.add(buttonPanel);

        loginButton = buttonPanel.getLoginButton();
        createButton = buttonPanel.getCreateButton();

        JPanel MessagePanel = new JPanel();
        successText = new JTextArea();
        successText.setPreferredSize(new Dimension(200, 20));
        successText.setEditable(false);
        successText.setFont(new Font("Serief", Font.BOLD, 13));
        MessagePanel.add(successText);
        LoginPanel.add(MessagePanel);

        this.add(LoginPanel);
    }  

    public void AccountCreationSuccess() {
        successText.setForeground(Color.GREEN);
        successText.setText("New account created!"); 
    }

    public void AccountCreationFail() {
        successText.setForeground(Color.RED);
        successText.setText("Account creation failed"); 
    }
    
}
