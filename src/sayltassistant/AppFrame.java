package sayltassistant;

import javax.swing.*;
import java.awt.*;

import sayltassistant.components.*;

class AppFrame extends JFrame {
    private QAScreen mainScreen;
    private Sidebar historyList;
    private Footer buttons;

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

    public static void main(String[] args) {
        AppFrame baseApp = new AppFrame();
        baseApp.setTitle("SayIt Assistant");
        baseApp.setSize(800, 600);
        baseApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseApp.setVisible(true);

    }
}
