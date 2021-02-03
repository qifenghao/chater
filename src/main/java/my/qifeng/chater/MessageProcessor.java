package my.qifeng.chater;

public interface MessageProcessor
{
    void processMessage(String sender, String message);
    void processError(String errorMessage);
    void processInfo(String infoMessage);
}
