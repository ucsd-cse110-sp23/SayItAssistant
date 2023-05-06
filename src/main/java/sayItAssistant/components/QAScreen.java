package sayItAssistant.components;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class QAScreen extends JPanel {
    Color QABackColor = new Color(40,40,40);
    public QAScreen(){
        this.setBackground(QABackColor);
        // Testing text in main screen
        JLabel testText = new JLabel();
        testText.setForeground(Color.WHITE);
        testText.setText("This is a test");
        add(testText);
    }
}
