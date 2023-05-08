package sayItAssistant.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import sayItAssistant.History;
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

public class Footer extends JPanel { // This class contains recording buttons
    JButton speakNewQuestion;
    JButton stopRecording;
    boolean recordingStatus = false;
    private TargetDataLine targetDataLine;
    private AudioFormat audioFormat;
    File audioFile;

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
                    audioFile = new File("recording.wav");
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
        //File file = new File("src/UCSanDiego.m4a");
        //Whisper whis = new Whisper(file);
        Whisper whis = new Whisper(audioFile);
        try {
            whis.toTranscribe();
        } catch (IOException e) {
            e.printStackTrace();
        }
        removeAudio();
        Sidebar.updateHistory();
        targetDataLine.close();
    }

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
    
    // Removing audio file
    private void removeAudio() {
    	audioFile.delete();
    }

    public Footer() {
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
    }
}
