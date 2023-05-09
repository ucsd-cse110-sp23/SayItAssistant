package sayItAssistant.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class QAScreen extends JPanel {
    private Color QABackColor = new Color(40,40,40);
    public static JTextArea QAText;

    public QAScreen(){
        this.setBackground(QABackColor);

        QAText = new JTextArea();
        QAText.setPreferredSize(new Dimension(400, 600));
        QAText.setEditable(false);

        QAText.setBorder(BorderFactory.createEmptyBorder(40,0,40,0));
        QAText.setFont(new Font("Sans-serif", Font.BOLD, 18));

        JPanel QAPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        QAPanel.setPreferredSize(new Dimension(570, 600));
        QAPanel.setOpaque(true);
        QAPanel.setBackground(QABackColor);

        QAPanel.add(QAText);
        

        QAText.setBackground(QABackColor);
        QAText.setForeground(Color.WHITE);
        QAText.setLineWrap(true);
        QAText.setWrapStyleWord(true);

        this.add(QAPanel, BorderLayout.NORTH);
        
    }

    //Has to be run after sidebar is updated. Relies on updated history class.
    public static void updateQAScreen() {
        String outputString = Sidebar.historyObj.getHistory().get(0).getQuestionString() + "\n\n\n" + 
                              Sidebar.historyObj.getHistory().get(0).getAnswerObject().getAnswerString();
        QAText.setText(outputString);
    }
}
