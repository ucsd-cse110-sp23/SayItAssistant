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
||                  deleteCurrent - delete current question button
||                  deleteAll - delete all questions button
||					recordingStatus - true if recording is in progress. False otherwise
||					targetDataLine - type of dataline from which audio can be read
||					audioFormat - format for the audio
||					audioFile - file where audio is saved
||
|+-----------------------------------------------------------------------
||
||   Constructors:
||					Footer()- default constructor
||					Creates Footer which consists of start recording (new question) 
||                  and stop recording buttons
||
||  Class Methods:
||					startRecording() - method to begin recording
||					stopRecording() - method to stop recording
||					getAudioFormat() - method for getting the format of audio
||                  addListeners() - method for handles clicking of buttons
||                  removeAudio() - method for deleting audio file
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
                    speakNewQuestion.setBackground(Color.GREEN);
                    audio.startRecording();
                }
        );

        stopRecording.addActionListener(
                (ActionEvent e) -> {
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
        );

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
