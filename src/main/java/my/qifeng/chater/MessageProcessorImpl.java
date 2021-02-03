package my.qifeng.chater;

import javax.swing.*;

public class MessageProcessorImpl implements MessageProcessor {
    private JTextArea jTextArea;

    public MessageProcessorImpl(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }

    @Override
    public void processMessage(String sender, String message) {
        jTextArea.append("<<< From " + sender + "\n" + message + "\n");
    }

    @Override
    public void processError(String errorMessage) {
        jTextArea.append("ERROR: " + errorMessage + "\n");
    }

    @Override
    public void processInfo(String infoMessage) {
        jTextArea.append(infoMessage + "\n");
    }
}
