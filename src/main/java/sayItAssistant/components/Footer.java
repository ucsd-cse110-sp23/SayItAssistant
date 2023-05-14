package sayItAssistant.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import sayItAssistant.Whisper;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
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
    private TargetDataLine targetDataLine;
    private AudioFormat audioFormat;
    File audioFile;

    /*---------------------------------------------------------------------
    |  Method startRecording()
    |
    |         Purpose: handles start recording
    |
    |   Pre-condition: Start recording button is clicked
    |
    |  Post-condition: Audio file is created with voice recording
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    private void startRecording() {
        Thread t = new Thread(
            () -> {
                try {
                    DataLine.Info dataLineInfo = new DataLine.Info(
                        TargetDataLine.class,
                        audioFormat
                    );

                    targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                    targetDataLine.open(audioFormat);
                    targetDataLine.start();

                    AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);

                    // WRITE TO FILE
                    audioFile = new File("recording.wav");
                    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
                    
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        );
        t.start();
    }

    /*---------------------------------------------------------------------
    |  Method stopRecording()
    |
    |         Purpose: handles stop recording
    |
    |   Pre-condition: Stop recording button is clicked
    |
    |  Post-condition: Audio file is transcribed, audio file is deleted, and sidebar + QAScreen
    |                  are updated with new question.
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    private void stopRecording() {
        Thread thr = new Thread(
            () -> {
                targetDataLine.stop();
                //File file = new File("src/UCSanDiego.m4a");
                //Whisper whis = new Whisper(file);
                Whisper whis = new Whisper(audioFile);
                try {
                    whis.toTranscribe();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                removeAudio();
                Sidebar.updateAddHistory();
                QAScreen.updateQAScreen();
                targetDataLine.close();
            }
        );
        thr.start();
    }

    private void deleteCurrentQuestion() {
        Sidebar.updateRemoveHistory();
        QAScreen.updateRemoveQAScreen();
    }

    /*---------------------------------------------------------------------
    |  Method getAudioFormat()
    |
    |         Purpose: Creates and Returns the format of the audio
    |
    |   Pre-condition: None
    |
    |  Post-condition: New audio format is created
    |
    |      Parameters: None
    |
    |         Returns: Audio Format
    *-------------------------------------------------------------------*/
    private AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;

        return new AudioFormat(
            sampleRate,
            sampleSizeInBits,
            channels,
            signed,
            bigEndian
        );
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
                startRecording();
            }
        );

        stopRecording.addActionListener(
            (ActionEvent e) -> {
                speakNewQuestion.setBackground(Color.WHITE);
                stopRecording();
            }
        );

        deleteCurrent.addActionListener(
            (ActionEvent e)-> {
                deleteCurrentQuestion();
            }
        );

      
    }
    
    /*---------------------------------------------------------------------
    |  Method removeAudio()
    |
    |         Purpose: Deletes Audio File
    |
    |   Pre-condition: Audio File Exists
    |
    |  Post-condition: Audio File is deleted
    |
    |      Parameters: None
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    private void removeAudio() {
    	audioFile.delete();
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

        audioFormat = getAudioFormat();
        
        addListeners();
        revalidate();
    }
}
