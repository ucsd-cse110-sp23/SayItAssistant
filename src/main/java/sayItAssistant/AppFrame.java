package sayItAssistant;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import com.sun.net.httpserver.HttpServer;

import sayItAssistant.components.Footer;
import sayItAssistant.components.QAScreen;
import sayItAssistant.components.Sidebar;
import sayItAssistant.data.History;
import sayItAssistant.handler.RequestHandler;
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
    public static void main(String[] args) throws IOException{
    	
        AppFrame baseApp = new AppFrame();

        baseApp.setTitle("SayIt Assistant");
        baseApp.setSize(800, 600);
        baseApp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        baseApp.setVisible(true);   
    }
}