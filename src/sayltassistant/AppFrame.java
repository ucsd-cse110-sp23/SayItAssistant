package sayltassistant;

import javax.swing.*;
import java.awt.*;

class QAScreen extends JPanel {
    QAScreen(){};
}

class Sidebar extends JPanel {
    Sidebar(){};
}

class Footer extends JPanel { // This class contains recording buttons
    JButton speakNewQuestion;
    JButton stopRecording;
    Footer(){
        // Set Question recording buttons to the left of the footer
        JPanel leftHalf = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        this.setPreferredSize(new Dimension(270, 30));
        // Create new question button
        speakNewQuestion = new JButton("New Question");
        // Set Font
        speakNewQuestion.setFont(new Font("Sans-serif", Font.ITALIC, 10));
        leftHalf.add(speakNewQuestion);

        // Create stop recording button
        stopRecording = new JButton("Stop Recording");
        // Set Font
        stopRecording.setFont(new Font("Sans-serif", Font.ITALIC, 10));
        leftHalf.add(stopRecording);

        setLayout(new BorderLayout());
        this.add(leftHalf, BorderLayout.WEST);

        //TODO: Add a right half for delete buttons. Follows a similar format as above
    };
}

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
        splitScreen.setResizeWeight(0);
        // TODO: Make divider MANUALLY non-adjustable by user

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
