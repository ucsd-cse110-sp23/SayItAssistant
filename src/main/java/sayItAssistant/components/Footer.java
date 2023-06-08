package sayItAssistant.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sayItAssistant.data.Answer;
import sayItAssistant.data.DataBase;
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
                        if(lowerQuestionString.startsWith("delete")){
                            try {
                                if(!Sidebar.historyJList.isSelectionEmpty())  {
                                    String query = String.valueOf(Sidebar.getIndex());
                                    url = new URL(URL + "?=" + query);
                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setRequestMethod("DELETE");
                                    conn.getInputStream();
                                    questionDatabase.removeQuestion(Sidebar.historyJList.getSelectedIndex());
                                }
                            }catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            Sidebar.updateRemoveHistory();
                        }
                        
                        if(question.getQuestionString().toLowerCase().startsWith("clear all")){
                            try {
                                url = new URL(URL);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("PUT");
                                conn.getInputStream();
                                questionDatabase.clearAll();
                            }catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            Sidebar.resetHistory();
                        }

                        if(lowerQuestionString.startsWith("question")) {
                            try {
                                url = new URL(URL);
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                conn.setDoOutput(true);
                                OutputStreamWriter out = new OutputStreamWriter(
                                        conn.getOutputStream()
                                );
                                // Check for comma in question and replace with a colon.
                                // This ensures that intonation while saying the command doesn't produce a comma
                                String softQuestionCopy = question.getQuestionString();
                                if(softQuestionCopy.contains(",")) {
                                    softQuestionCopy = softQuestionCopy.replace(",",":");
                                }
                                out.write(softQuestionCopy + "," + question.getAnswerObject().getAnswerString());
                                out.flush();
                                out.close();
                                conn.getInputStream();
                                questionDatabase.addQuestion(question);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            Sidebar.updateAddHistory();
                        }
                        
                        if(lowerQuestionString.startsWith("setup email")){
                            Email email = new Email();
                         
                        }
                        if(lowerQuestionString.startsWith("set up email")){
                            Email email = new Email();
                         
                        }
                        if(lowerQuestionString.startsWith("setup e-mail")){
                            Email email = new Email();
                        }
                        if(lowerQuestionString.startsWith("set up e-mail")){
                            Email email = new Email();
                        }
                        // Create E-mail command
                        if(lowerQuestionString.startsWith("create email")) {
                            emailGenerateServerProcess(question);
                        }
                        if(lowerQuestionString.startsWith("create e-mail")) {
                            emailGenerateServerProcess(question);
                        }
                        if(lowerQuestionString.startsWith("send email")) {
                            emailExtractor(lowerQuestionString, emailConfig );
                            emailSendServerProcess(question);
                        }
                        if(lowerQuestionString.startsWith("send e-mail")) {
                            emailExtractor(lowerQuestionString, emailConfig);
                            emailSendServerProcess(question);
                        }

                        
                    }
                }
        );

        /*deleteCurrent.addActionListener(
            (ActionEvent e)-> {
                try {
                    if(!Sidebar.historyJList.isSelectionEmpty())  {
                    String query = String.valueOf(Sidebar.getIndex());
                    URL url = new URL(URL + "?=" + query);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");
                    conn.getInputStream();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            Sidebar.updateRemoveHistory();
            }
        );
        
        deleteAll.addActionListener(
            (ActionEvent e) -> {
            	try {
					URL url = new URL(URL);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	                conn.setRequestMethod("PUT");
	                conn.getInputStream();
            	} catch (Exception ex) {
            		ex.printStackTrace();
            	}
            Sidebar.resetHistory();
            }   
        );*/

    }

    private void emailGenerateServerProcess(Question question) {
        try {
            URL url = new URL(URL);
            EmailConfig emailDetails = new EmailConfig();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(
                    conn.getOutputStream()
            );
            // Check for comma in question and replace with a colon.
            // This ensures that intonation while saying the command doesn't produce a comma
            String softQuestionCopy = question.getQuestionString();
            String softAnswerCopy = question.getAnswerObject().getAnswerString();
            String yourName = emailDetails.getProperty("DisplayName");
            if(softQuestionCopy.contains(",")) {
                softQuestionCopy = softQuestionCopy.replace(",", ":");
            }
            if(softAnswerCopy.contains("[Your Name]")) {
                softAnswerCopy = softAnswerCopy.replace("[Your Name]", yourName );
            }
            out.write(softQuestionCopy + "," + softAnswerCopy);
            out.flush();
            out.close();
            conn.getInputStream();
            questionDatabase.addQuestion(question);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Sidebar.updateAddHistory();
    }

    private void emailSendServerProcess(Question question) {
        try {
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(
                    conn.getOutputStream()
            );
            String softQuestionCopy = question.getQuestionString();
            softQuestionCopy = softQuestionCopy.replace(" at ", "@").replace(" dot ", ".");
            out.write(softQuestionCopy + "," + "");
            out.flush();
            out.close();
            conn.getInputStream();
            questionDatabase.addQuestion(question);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Sidebar.updateAddHistory();
    }

    private void emailExtractor(String string, EmailConfig emailDetails) {
        String regex = "\\b[A-Za-z0-9._%+-]+\\s*(?:at|@)\\s*[A-Za-z0-9-]+\\s*(?:dot|\\.)\\s*(?:com|\\b[A-Za-z]{3}\\b)\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);

        while(matcher.find()) {
            String emailAddress = matcher.group();
            emailAddress = emailAddress.replaceAll("\\s", "")
                                       .replaceAll("dot",".")
                                       .replaceAll("at","@");
            System.out.println(emailAddress);
            emailDetails.setProperty("SendEmail", emailAddress);
            emailDetails.store();
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

        // Set Delete buttons to the right of the footer
        //JPanel rightHalf = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        // Create delete current question button
        //deleteCurrent = new JButton("Delete");
        // Set features
        //deleteCurrent.setFont(new Font("Sans-serif", Font.BOLD, 10));
        //deleteCurrent.setBackground(Color.WHITE);
        //rightHalf.add(deleteCurrent);

        // Create clear all questions button
        //deleteAll = new JButton("Clear All");
        // Set features
        //deleteAll.setFont(new Font("Sans-serif", Font.BOLD, 10));
        //deleteAll.setBackground(Color.RED);
        //rightHalf.add(deleteAll);

        //Adjust position
        //rightHalf.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        //this.add(rightHalf, BorderLayout.EAST);
        
        addListeners();
        revalidate();
    }
}
