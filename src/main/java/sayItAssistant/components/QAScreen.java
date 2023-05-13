package sayItAssistant.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/*+----------------------------------------------------------------------
||
||  Class QAScreen
||||
||        Purpose: Serves as the component for the QAScreen on the UI
||
|+-----------------------------------------------------------------------
||
||          Field:
||					QABackColor - background color for the QAScreen
||					QAText - Text area for displaying question and answer
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					QAScreen()- default constructor
||					Creates QAScreen which displays question and answer
||
||  Class Methods:
||					updateQAScreen() - methods to update the QAScreen with most recent
||                                     question and answer
||
++-----------------------------------------------------------------------*/
public class QAScreen extends JPanel {
    private Color QABackColor = new Color(40,40,40);
    public static JTextArea QAText;

    /*---------------------------------------------------------------------
    |  Constructor QAScreen()
    |
    |         Purpose: Creates the QAScreen
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize QAScreen component
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    public QAScreen(){
        this.setBackground(QABackColor);

        QAText = new JTextArea();
        QAText.setPreferredSize(new Dimension(400, 600));
        QAText.setEditable(false);

        QAText.setBorder(BorderFactory.createEmptyBorder(40,0,40,0));
        QAText.setFont(new Font("Trebuchet MS", Font.BOLD, 20));

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

    /*---------------------------------------------------------------------
    |  Method updateQAScreen()
    |
    |         Purpose: Updates QAScreen
    |
    |   Pre-condition: history is updated with most recent question and answer
    |
    |  Post-condition: QAText shows the most recent question and answer
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    public static void updateQAScreen() {
        String outputString = Sidebar.historyObj.getHistory().get(0).getQuestionString() + "\n\n" + 
                              Sidebar.historyObj.getHistory().get(0).getAnswerObject().getAnswerString();
        QAText.setText(outputString);
    }

    public static void updateRemoveQAScreen() {
        String outputString;
        if(Sidebar.currentQuestionIndex == 0) {
            outputString = "";
        } else {
            outputString = Sidebar.historyObj.getHistory().get(Sidebar.currentQuestionIndex - 1).getQuestionString() + "\n\n" + 
                           Sidebar.historyObj.getHistory().get(Sidebar.currentQuestionIndex - 1).getAnswerObject().getAnswerString();
        }
        //System.out.println("" + Sidebar.currentQuestionIndex);
        QAText.setText(outputString);
    }
}
