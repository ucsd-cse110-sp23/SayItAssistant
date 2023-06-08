package sayItAssistant.data;

public interface EmailProcess {
    public void generate(Question question, String serverURL, DataBase questionDatabase);
    public void extract(String string);
    public void sendUI(String URL, Question question, DataBase questionDatabase);
    public void send(String URL);
}
