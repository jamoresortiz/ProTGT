package com.joandma.protgt.Models.ModelsApiSMSPubli;

/**
 * Created by jamoresmacbook on 4/3/18.
 */

public class Message {
    private String from;
    private String to;
    private String text;
    private String custom;

    public Message(String from, String to, String text, String custom) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.custom = custom;
    }

    public Message() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (from != null ? !from.equals(message.from) : message.from != null) return false;
        if (to != null ? !to.equals(message.to) : message.to != null) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        return custom != null ? custom.equals(message.custom) : message.custom == null;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (custom != null ? custom.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", text='" + text + '\'' +
                ", custom='" + custom + '\'' +
                '}';
    }
}
