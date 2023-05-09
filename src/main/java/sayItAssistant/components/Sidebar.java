package sayItAssistant.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.*;

import java.util.ArrayList;

import sayItAssistant.History;
import sayItAssistant.Question;

public class Sidebar extends JPanel {

    private Color SideBackColor = new Color(18,18,18);
    public static History historyObj = new History();
    private ArrayList<Question> historyList = historyObj.getHistory();
    public static DefaultListModel<String> historyListModel = new DefaultListModel<>();
    public static JList<String> historyJList;
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
        JScrollPane scrollList = new JScrollPane(historyJList);

        scrollList.setBorder(new CompoundBorder(
            new LineBorder(SideBackColor, 1),
            new EmptyBorder(0, 0, 0, 0)
        ));

        this.add(historyTitleBox, BorderLayout.NORTH);
        this.add(scrollList, BorderLayout.CENTER);
        historyJList.setBackground(SideBackColor);
        historyJList.setForeground(Color.WHITE);
        this.setBackground(SideBackColor);
    }

    private DefaultListModel<String> toStringList(ArrayList<Question> historyList) {
        DefaultListModel<String> returnList = new DefaultListModel<>();
        for(Question QA : historyList) {
            returnList.addElement(QA.getQuestionString());
        }
        return returnList;
    }

    // Update history and update list in sidebar
    public static void updateHistory() {
        historyObj = new History();
        historyListModel.add(0,historyObj.getHistory().get(0).getQuestionString());
        historyJList.validate();
        historyJList.repaint();
    }
}
