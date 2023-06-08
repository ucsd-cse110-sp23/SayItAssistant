package sayItAssistant.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sayItAssistant.data.History;
import sayItAssistant.data.Question;
/*+----------------------------------------------------------------------
||
||  Class Sidebar
||||
||        Purpose: Serves as the component for the Sidebar on the UI
||
|+-----------------------------------------------------------------------
||
||          Field:
||          SideBackColor - background color for the sidebar
||          historyObj - object containing the history of questions and answers
||          historyList - List of the history of questions and answers
||          historyListModel - DefaultListModel representation of history
||          historyJList - List containing history data for displayal
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					Sidebar()- default constructor
||					Creates the Sidebar which displays history of questions and answers
||
||  Class Methods:
||					toStringList() - method to convert ArrayList to DefaultListModel
||                  updateHistory() - method to update history
||
++-----------------------------------------------------------------------*/
public class Sidebar extends JPanel {

    private Color SideBackColor = new Color(18,18,18);
    public static History historyObj = new History();
    public static ArrayList<Question> historyList = historyObj.getHistory();
    public static DefaultListModel<String> historyListModel = new DefaultListModel<>();
    public static JList<String> historyJList;
    public static int currentQuestionIndex;


    /*---------------------------------------------------------------------
    |  Constructor Sidebar()
    |
    |         Purpose: Creates the Sidebar
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize Sidebar component
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    public Sidebar(){
        setLayout(new BorderLayout());

        JPanel historyTitleBox = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        historyTitleBox.setPreferredSize(new Dimension(150, 50));

        historyTitleBox.setBorder(new CompoundBorder(
            new LineBorder(SideBackColor, 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        historyTitleBox.setBackground(new Color(40,40,40));
        historyTitleBox.setOpaque(true);
        
        JLabel historyLabel = new JLabel("History");
        historyLabel.setForeground(Color.WHITE);
        historyLabel.setFont(new Font("Sans-serif", Font.BOLD, 18));
        historyTitleBox.add(historyLabel);

        historyListModel = toStringList(historyList);
        
        historyJList = new JList<>(historyListModel);

        historyJList.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent evt) {
                valueChangedAnswer();
            }
        });
        
        JScrollPane scrollList = new JScrollPane(historyJList);
        scrollList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollList.setBorder(new CompoundBorder(
            new LineBorder(SideBackColor, 1),
            new EmptyBorder(0, 0, 0, 0)
        ));

        this.add(historyTitleBox, BorderLayout.NORTH);
        this.add(scrollList, BorderLayout.CENTER);
        historyJList.setFont(new Font("Trebuchet MS",Font.BOLD,14));
        historyJList.setBackground(SideBackColor);
        historyJList.setForeground(Color.WHITE);
        this.setBackground(SideBackColor);
    }

    /*---------------------------------------------------------------------
    |  Method toStringList()
    |
    |         Purpose: Returns a DefaultListModel representation of ArrayList
    |
    |   Pre-condition: None
    |
    |  Post-condition: None
    |
    |      Parameters: ArrayList<Question> historyList
    |
    |         Returns: A DefaultListModel representation of historyList
    *-------------------------------------------------------------------*/
    private DefaultListModel<String> toStringList(ArrayList<Question> historyList) {
        DefaultListModel<String> returnList = new DefaultListModel<>();
        for(Question QA : historyList) {
            returnList.addElement(QA.getQuestionString());
        }
        return returnList;
    }

    /*---------------------------------------------------------------------
    |  Method updateHistory()
    |
    |         Purpose: Updates history and refreshes the historyJList to display elementes add to history
    |
    |   Pre-condition: None
    |
    |  Post-condition: History is updated with added element
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    public static void updateAddHistory() {
    	historyObj = new History();
        historyListModel.add(0,historyObj.getHistory().get(0).getQuestionString());
        historyList.add(0,historyObj.getHistory().get(0));
        historyJList.validate();
        historyJList.repaint();
        historyJList.setSelectedIndex(0);
        QAScreen.updateQAScreen();
    }
    /*---------------------------------------------------------------------
    |  Method  getIndex 
    |
    |  Purpose:  Returns the current index of the historyJList
    |
    |  Pre-condition: None
    |
    |  Post-condition: None
    |
    |  Parameters: None
    |
    |  Returns:  int currentQuestionIndex
    *-------------------------------------------------------------------*/
    public static int getIndex() {
    	return currentQuestionIndex;
    }
    /*---------------------------------------------------------------------
    |  Method updateRemoveHistory()
    |
    |         Purpose: Updates history and refreshes the historyJList to display elementes removed from history
    |
    |   Pre-condition: None
    |
    |  Post-condition: History is updated with removed element
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/

    public static void updateRemoveHistory() {
        if(!historyJList.isSelectionEmpty()) {
            historyListModel.remove(currentQuestionIndex);
            historyList.remove(currentQuestionIndex);
            historyObj = new History();
            historyJList.validate();
            historyJList.repaint();
            QAScreen.resetQAScreen();
        }
    }

    /*---------------------------------------------------------------------
    |  Method resetHistory()
    |
    |         Purpose: Resets history and refreshes the historyJList to display empty history
    |
    |   Pre-condition: None
    |
    |  Post-condition: History is reset
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/

    public static void resetHistory() {
        historyListModel.clear();
        historyObj.clearHistory();
        historyList.clear();
        historyJList.validate();
        historyJList.repaint();
        historyObj = new History();
        QAScreen.resetQAScreen();
    }

    /*---------------------------------------------------------------------
    |  Method valueChangedAnswer()
    |
    |         Purpose: Updates the QAScreen to display the selected question and answer
    |
    |   Pre-condition: None
    |
    |  Post-condition: QAScreen is updated
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    public void valueChangedAnswer(){
        Thread thr = new Thread(
            () -> {
                String s = (String) historyJList.getSelectedValue();
                for(int i = 0; i < historyList.size(); i++) {
                    if(historyList.get(i).getQuestionString() == s){
                        QAScreen.QAText.setText(historyList.get(i).getQuestionString() + "\n\n" + historyList.get(i).getAnswerObject().getAnswerString());
                        currentQuestionIndex = i;
                    }
                }
            }
        );
        thr.start();
    }
}
