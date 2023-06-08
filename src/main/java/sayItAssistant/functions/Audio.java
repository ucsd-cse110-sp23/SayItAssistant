package sayItAssistant.functions;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

import sayItAssistant.data.Question;

public class Audio {
	
	private TargetDataLine targetDataLine;
	private AudioFormat audioFormat;
	File audioFile;
	boolean isMicOn = false;
	
	public Audio() {
		audioFormat = getAudioFormat();
	}
	
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
    public void startRecording() {
    	isMicOn = true;
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
    public Question stopRecording() {
    	if(!isMicOn) return null;
    	Question q = new Question();
        Thread thr = new Thread(
            () -> {
                targetDataLine.stop();
                //File file = new File("src/UCSanDiego.m4a");
                //Whisper whis = new Whisper(file);
                //Sidebar.updateAddHistory();
                //QAScreen.updateQAScreen();
                targetDataLine.close();
            }
        );
        thr.start();
        Whisper whis = new Whisper(audioFile);
        try {
            q = whis.toTranscribe();
        } catch (IOException e) {
            e.printStackTrace();
        }
        removeAudio();
        isMicOn = false;
        return q;
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
    
    public boolean getIsMicOn() {
    	return isMicOn;
    }
    
}
