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
    private History historyObj = new History(false);
    private ArrayList<Question> historyList = historyObj.getHistory();

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

        ArrayList<String> historyStringList = toStringList(historyList);
        JList<String> historyJList = new JList<>(historyStringList.toArray(new String[0]));
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

    private ArrayList<String> toStringList(ArrayList<Question> historyList) {
        ArrayList<String> returnList = new ArrayList<>();
        for(Question QA : historyList) {
            returnList.add(QA.getQuestionString());
        }
        return returnList;
    }
}
