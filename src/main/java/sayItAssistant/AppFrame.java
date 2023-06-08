package sayItAssistant;

import java.awt.BorderLayout;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import sayItAssistant.components.EmailConfig;
import sayItAssistant.components.Footer;
import sayItAssistant.components.Login;
import sayItAssistant.components.QAScreen;
import sayItAssistant.components.Sidebar;
import sayItAssistant.functions.ValidationListener;

/*+----------------------------------------------------------------------
||
||  Class AppFrame
||||
||        Purpose: Main window that contains the entire GUI of the app
||
|+-----------------------------------------------------------------------
||
||          Field:
||          mainScreen - QAScreen
||          historyList - Sidebar
||          buttons - Footer
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					AppFrame()- default constructor
||					Creates the AppFrame by combining all UI Components
||
||  Class Methods:
||					main() - entry point for executing the app
||
++-----------------------------------------------------------------------*/
class AppFrame extends JFrame {

    private QAScreen mainScreen;
    private Sidebar historyList;
    private Footer buttons;

    /*---------------------------------------------------------------------
    |  Constructor AppFrame()
    |
    |         Purpose: Creates the AppFrame
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize AppFrame
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    public AppFrame() {
        setLayout(new BorderLayout()); // Layout for AppFrame


        // Creating the sidebar to the left of the screen
        historyList = new Sidebar();

        // Creating the main screen to the right of the screen
        mainScreen = new QAScreen();

        JSplitPane splitScreen = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, historyList, mainScreen);

        // Set width of sidebar
        splitScreen.setDividerLocation(230);
        // Set divider to non-adjustable
        splitScreen.setDividerSize(0);
        splitScreen.setContinuousLayout(true);

        this.add(splitScreen, BorderLayout.CENTER);
        // Footer
        buttons = new Footer();
        this.add(buttons, BorderLayout.SOUTH);

    }



    /*---------------------------------------------------------------------
    |  Method main()
    |
    |         Purpose: Entry point for execution of app
    |
    |   Pre-condition: None
    |
    |  Post-condition: GUI is created and executed
    |
    |      Parameters: String[] args - command line arguments
    |
    |         Returns: None
    *-------------------------------------------------------------------*/

    public static void main(String[] args) throws IOException {
        Server.startServer();
        SwingUtilities.invokeLater(() -> {


            Login login = new Login();
            login.setTitle("Login");
            login.setSize(600, 400);
            login.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            login.setVisible(true);

            if(login.checkAutoLogin()) {
                login.autoLogin();
                login.setVisible(false);
                openBaseApp();
            }


            login.addValidationListener(new ValidationListener() {
                @Override
                public void onValidationCompletion(int status) {
                    if (status == 0) {
                        login.setVisible(false);
                        openBaseApp();
                    }
                }
            });

            login.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    login.dispose();
                    try {
                        Server.stopServer();
                        System.out.println("Server closed");
                    } catch (IOException e1) {
                        System.out.println("Server failed to shut down");
                    }
                    System.exit(0);
                }
            });


        });
    }

    private static void openBaseApp() {
        Server.database = Login.returnDatabase();
        Server.contextBuilder();
        Server.database.writeToFile();
        Server.database.getEmailSettings();
        AppFrame baseApp = new AppFrame();
        baseApp.setTitle("SayIt Assistant 2");
        baseApp.setSize(815, 600);
        baseApp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //baseApp.setResizable(false);

        baseApp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EmailConfig emailDetails = new EmailConfig();
                Server.database.emailSettings(emailDetails.getProperty("DisplayName"), emailDetails.getProperty("EmailAddress"), 
                                              emailDetails.getProperty("FirstName") , emailDetails.getProperty("LastName"), 
                                              emailDetails.getProperty("Password"), emailDetails.getProperty("SMTP"), 
                                              emailDetails.getProperty("TLSPort"));
                baseApp.dispose();
                try {
                    Server.stopServer();
                    System.out.println("Server closed");
                } catch (IOException e1) {
                    System.out.println("Server failed to shut down");
                }
                System.exit(0);
            }
        });

        baseApp.setVisible(true);
    }
}
