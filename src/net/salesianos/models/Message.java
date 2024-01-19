package net.salesianos.models;
import java.io.Serializable;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    LocalTime messageTime = LocalTime.now();
    private String sender;
    private String content;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LocalTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(LocalTime messageTime) {
        this.messageTime = messageTime;
    }

    @Override
    public String toString() {
        return "[" + messageTime + "] <" + sender + "> msg: " + content;
    }
}
