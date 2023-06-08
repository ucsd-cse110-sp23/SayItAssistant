package sayItAssistant.components;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import sayItAssistant.data.DataBase;
import sayItAssistant.data.Question;

public class FooterCommander {
    Question question;
    String serverURL;
    EmailServerProcess emailProcess;

    public FooterCommander(Question question, String serverURL) {
        this.question = question;
        this.serverURL = serverURL;
        this.emailProcess = new EmailServerProcess();
    }
    public void notifyDelete(DataBase questionDatabase) {
        try {
            if(!Sidebar.historyJList.isSelectionEmpty())  {
                String query = String.valueOf(Sidebar.getIndex());
                URL url = new URL(serverURL + "?=" + query);
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

    public void notifyClearAll(DataBase questionDatabase) {
        try {
            URL url = new URL(serverURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.getInputStream();
            questionDatabase.clearAll();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        Sidebar.resetHistory();
    }

    public void notifyNewQuestion(DataBase questionDatabase) {
        try {
            URL url = new URL(serverURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // Check for comma in question and replace with a colon.
            // This ensures that intonation while saying the command doesn't produce a comma
            String softQuestionCopy = question.getQuestionString();
            if(softQuestionCopy.contains(",")) {
                softQuestionCopy = softQuestionCopy.replace(",",":");
            }
            OutputStreamWriter out = new OutputStreamWriter(
                    conn.getOutputStream()
            );
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

    public void checkEmailSettings(String lowerQuestionString) {
        if (lowerQuestionString.startsWith("setup email") || lowerQuestionString.startsWith("set up email") ||
        lowerQuestionString.startsWith("setup e-mail") || lowerQuestionString.startsWith("set up e-mail")) {
        Email email = new Email();
        }
    }

    public void checkCreateEmail(String lowerQuestionString, DataBase questionDataBase) {
        if( lowerQuestionString.startsWith("create email") || lowerQuestionString.startsWith("create e-mail")) {
            emailProcess.generate(question, serverURL, questionDataBase);
            //Sidebar.historyJList.setSelectedIndex(0);
        }
    }

    public void checkSendEmail(String lowerQuestionString, DataBase questionDataBase) {
        if( lowerQuestionString.startsWith("send email") || lowerQuestionString.startsWith("send e-mail")) {
            emailProcess.extract(lowerQuestionString);
            emailProcess.send(serverURL);
            emailProcess.sendUI(serverURL, question, questionDataBase );
            //Sidebar.historyJList.setSelectedIndex(0);
        }
    }
}
