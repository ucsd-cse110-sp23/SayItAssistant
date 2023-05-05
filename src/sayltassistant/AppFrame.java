package sayltassistant;

import javax.swing.*;
import java.awt.*;

class QAScreen extends JPanel {
    Color QABackColor = new Color(40,40,40);
    QAScreen(){
        this.setBackground(QABackColor);
        // Testing text in main screen
        JLabel testText = new JLabel();
        testText.setForeground(Color.WHITE);
        testText.setText("This is a test");
        add(testText);
    };
}

class Sidebar extends JPanel {
    Color SideBackColor = new Color(18,18,18);
    Sidebar(){
        this.setBackground(SideBackColor);
    };
}

class Footer extends JPanel { // This class contains recording buttons
    JButton speakNewQuestion;
    JButton stopRecording;
    Footer(){
        // Set Question recording buttons to the left of the footer
        JPanel leftHalf = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        this.setPreferredSize(new Dimension(270, 35));
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

        // Adjust the position of the buttons in the panel
        leftHalf.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
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
