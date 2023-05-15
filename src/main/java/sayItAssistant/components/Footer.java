package sayItAssistant.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

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
    JButton deleteCurrent;
    JButton deleteAll;
    boolean recordingStatus = false;
    Audio audio = new Audio();
    public final String URL = "http://localhost:8100/";

    private void deleteCurrentQuestion() {
        Sidebar.updateRemoveHistory();
        QAScreen.updateRemoveQAScreen();
    }

    private void deleteAllQuestions() {
        Sidebar.resetHistory();
        QAScreen.resetQAScreen();
    }


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
                speakNewQuestion.setBackground(Color.WHITE);
                Question question = audio.stopRecording();
                URL url;
				try {
					url = new URL(URL);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	                conn.setRequestMethod("POST");
	                conn.setDoOutput(true);
	                OutputStreamWriter out = new OutputStreamWriter(
	                  conn.getOutputStream()
	                );
	                out.write(question.getQuestionString() + "," + question.getAnswerObject().getAnswerString());
	                out.flush();
	                out.close();
	                Sidebar.updateAddHistory();
	                QAScreen.updateQAScreen();
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
                
                
            }
        );

        deleteCurrent.addActionListener(
            (ActionEvent e)-> {
                deleteCurrentQuestion();
            }
        );
        
        deleteAll.addActionListener(
            (ActionEvent e) -> {
                deleteAllQuestions();
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
        speakNewQuestion = new JButton("New Question");
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

        // Set Delete buttons to the right of the footer
        JPanel rightHalf = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        // Create delete current question button
        deleteCurrent = new JButton("Delete");
        // Set features
        deleteCurrent.setFont(new Font("Sans-serif", Font.BOLD, 10));
        deleteCurrent.setBackground(Color.WHITE);
        rightHalf.add(deleteCurrent);

        // Create clear all questions button
        deleteAll = new JButton("Clear All");
        // Set features
        deleteAll.setFont(new Font("Sans-serif", Font.BOLD, 10));
        deleteAll.setBackground(Color.RED);
        rightHalf.add(deleteAll);

        //Adjust position
        rightHalf.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        this.add(rightHalf, BorderLayout.EAST);
        
        addListeners();
        revalidate();
    }
}
