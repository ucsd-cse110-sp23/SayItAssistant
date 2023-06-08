package sayItAssistant.components;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import sayItAssistant.data.DataBase;
import sayItAssistant.data.EmailUtil;
import sayItAssistant.data.Question;

public class EmailServerProcess {
    public void generate(Question question, String serverURL, DataBase questionDatabase) {
        try {
            URL url = new URL(serverURL);
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

    public void extract(String string) {
        String regex = "\\b[A-Za-z0-9._%+-]+\\s*(?:at|@)\\s*[A-Za-z0-9-]+\\s*(?:dot|\\.)\\s*(?:com|\\b[A-Za-z]{3}\\b)\\b";
        Pattern pattern = Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(string);
        EmailConfig emailDetails = new EmailConfig();
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

    public void sendUI(String URL, Question question, DataBase questionDatabase) {
        try {
            URL url = new URL(URL);
            EmailConfig emailDetails = new EmailConfig();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(
                    conn.getOutputStream()
            );
            String softQuestionCopy = question.getQuestionString();
            softQuestionCopy = softQuestionCopy.replace(" at ", "@").replace(" dot ", ".");
            if(EmailUtil.emailStatus == 0) {
                out.write(softQuestionCopy + "," + "Email Sent!");
            } else {
                out.write(softQuestionCopy + "," + "Error Sending Email" + " SMTP Host: " + emailDetails.getProperty("SMTP").toString());
            }
            out.flush();
            out.close();
            conn.getInputStream();
            questionDatabase.addQuestion(question);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Sidebar.updateAddHistory();
    }

    public void send(String URL) {
        try {
            URL url = new URL(URL);
            if(!Sidebar.historyJList.isSelectionEmpty())  {
                String query = String.valueOf(Sidebar.getIndex());
                url = new URL(URL + "?=" + query);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("TRACE");
                conn.getInputStream();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
