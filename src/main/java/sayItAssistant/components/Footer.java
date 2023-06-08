package sayItAssistant.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import sayItAssistant.data.DataBase;
import sayItAssistant.data.EmailUtil;
import sayItAssistant.data.Question;
import sayItAssistant.functions.Audio;
/*+----------------------------------------------------------------------
||
||  Class Footer
||||
||        Purpose: Serves as the component for the Footer on the UI
||
|+-----------------------------------------------------------------------
||
||          Field:
||					speakNewQuestion - new question button
||					stopRecording - stop recording button
||                  recordingStatus - recording status
||                  audio - instance of the Audio class
||                  questionDatabase - question database
||                  URL - url
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					Footer() - default constructor
||					Creates Footer which displays new question and stop recording buttons
||
||  Class Methods:
||				  addListeners() - methods to add listeners
||                emailGenerateServerProcess(Question question) - methods to generate email server process
||                emailSendServerProcess(Question question) - methods to send email server process
||                emailExtractor(String string, EmailConfig emailDetails) - methods to extract email
||
++-----------------------------------------------------------------------*/
public class Footer extends JPanel { // This class contains recording buttons
    JButton speakNewQuestion;
    JButton stopRecording;
    //JButton deleteCurrent;
    //JButton deleteAll;
    boolean recordingStatus = false;
    Audio audio = new Audio();
    public static DataBase questionDatabase = new DataBase();
    public final String URL = "http://localhost:8100/";

    /*---------------------------------------------------------------------
    |  Method addListeners()
    |
    |         Purpose: Adds action listeners to buttons
    |
    |   Pre-condition: Buttons are initialized
    |
    |  Post-condition: New question button and stop recording has action listeners
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    public void addListeners() {
        speakNewQuestion.addActionListener(
                (ActionEvent e) -> {
                	speakNewQuestionListener();
                }
        );

        stopRecording.addActionListener(
                (ActionEvent e) -> {
                	stopRecordingListener();
                }
        );

    }
    
    /*---------------------------------------------------------------------
    |  Method speakNewQuestionListener()
    |
    |         Purpose: Adds action listeners to speakNewQuestion button
    |
    |   Pre-condition: Buttons are initialized
    |
    |  Post-condition: New question button has action listeners
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    private void speakNewQuestionListener() {
        speakNewQuestion.setBackground(Color.GREEN);
        audio.startRecording();
    }
    
    /*---------------------------------------------------------------------
    |  Method stopRecordingListener()
    |
    |         Purpose: Adds action listeners to stopRecording button
    |
    |   Pre-condition: Buttons are initialized
    |
    |  Post-condition: stop recording has action listeners
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    private void stopRecordingListener() {
        if (audio.getIsMicOn()) {
            speakNewQuestion.setBackground(Color.WHITE);
            Question question = audio.stopRecording();
            questionDatabase = new DataBase();
            EmailConfig emailConfig = new EmailConfig();
            URL url;
            String lowerQuestionString = question.getQuestionString().toLowerCase();
            FooterCommander FooterCommander = new FooterCommander(question, URL);


            if(lowerQuestionString.startsWith("delete")){
                FooterCommander.notifyDelete(questionDatabase);
            }
            
            if(lowerQuestionString.startsWith("clear all")){
                FooterCommander.notifyClearAll(questionDatabase);
            }

            if(lowerQuestionString.startsWith("question")) {
                FooterCommander.notifyNewQuestion(questionDatabase);
            }
            
            FooterCommander.checkEmailSettings(lowerQuestionString);
            
            FooterCommander.checkCreateEmail(lowerQuestionString, questionDatabase);

            FooterCommander.checkSendEmail(lowerQuestionString, questionDatabase);

            
        }
    }

    

    /*---------------------------------------------------------------------
    |  Constructor Footer()
    |
    |         Purpose: Creates Footer with the new question and stop recording buttons
    |                  on the left side and delete current question and delete all on 
    |                  on the right side
    |
    |   Pre-condition: None
    |
    |  Post-condition: Initialize Footer component
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    public Footer() {

        this.setPreferredSize(new Dimension(270, 35));

        // Set Question recording buttons to the left of the footer
        JPanel leftHalf = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        // Create new question button
        speakNewQuestion = new JButton("Start");
        // Set Font
        speakNewQuestion.setFont(new Font("Sans-serif", Font.BOLD, 10));
        speakNewQuestion.setBackground(Color.WHITE);
        leftHalf.add(speakNewQuestion);

        // Create stop recording button
        stopRecording = new JButton("Stop Recording");
        // Set Font
        stopRecording.setFont(new Font("Sans-serif", Font.BOLD, 10));
        stopRecording.setBackground(Color.WHITE);
        leftHalf.add(stopRecording);

        // Adjust the position of the buttons in the panel
        leftHalf.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        setLayout(new BorderLayout());
        this.add(leftHalf, BorderLayout.WEST);
        
        addListeners();
        revalidate();
    }
}
