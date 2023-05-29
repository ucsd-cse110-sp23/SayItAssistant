package sayItAssistant.components;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;


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



public class Login extends JPanel {
    private JButton loginButton, createButton;
    private FieldPanel fieldPanel;
    private ButtonPanel buttonPanel;
  
    public Login() {
        JPanel LoginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        LoginPanel.setPreferredSize(new Dimension(500, 200));
        LoginPanel.setLayout(new GridLayout(2, 2));

        fieldPanel = new FieldPanel();
        LoginPanel.add(fieldPanel);
  
        buttonPanel = new ButtonPanel();
        LoginPanel.add(buttonPanel);
  
        loginButton = buttonPanel.getLoginButton();
        createButton = buttonPanel.getCreateButton();
        this.add(LoginPanel);
    }  
    
}
