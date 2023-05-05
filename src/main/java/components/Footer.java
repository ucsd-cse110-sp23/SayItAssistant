package main.java.components;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Footer extends JPanel { // This class contains recording buttons
    JButton speakNewQuestion;
    JButton stopRecording;
    boolean recordingStatus = false;
    private TargetDataLine targetDataLine;
    private AudioFormat audioFormat;

    // Create method to receive microphone input; Adapted from Lab 5 code
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
                    File audioFile = new File("recording.wav");
                    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        );
        t.start();
    }

    private void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 2;
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
    
    // Add listeners to buttons
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
    }

    public Footer(){
        // Set Question recording buttons to the left of the footer
        JPanel leftHalf = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        this.setPreferredSize(new Dimension(270, 35));
        // Create new question button
        speakNewQuestion = new JButton("New Question");
        // Set Font
        speakNewQuestion.setFont(new Font("Sans-serif", Font.ITALIC, 10));
        speakNewQuestion.setBackground(Color.WHITE);
        leftHalf.add(speakNewQuestion);

        // Create stop recording button
        stopRecording = new JButton("Stop Recording");
        // Set Font
        stopRecording.setFont(new Font("Sans-serif", Font.ITALIC, 10));
        stopRecording.setBackground(Color.WHITE);
        leftHalf.add(stopRecording);

        // Adjust the position of the buttons in the panel
        leftHalf.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        setLayout(new BorderLayout());
        this.add(leftHalf, BorderLayout.WEST);

        //TODO: Add a right half for delete buttons. Follows a similar format as above

        audioFormat = getAudioFormat();
        addListeners();
        revalidate();
    };
}
