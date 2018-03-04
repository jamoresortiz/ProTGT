package com.joandma.protgt.Models.ModelsApiSMSPubli;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamoresmacbook on 4/3/18.
 */

public class Sms {
    private String api_key;
    private List<Message> messages;
    private String report_url;
    private String contact;
    private int fake;

    public Sms() {
        this.messages = new ArrayList<>();
    }

    public Sms(String api_key, List<Message> messages, String report_url, String contact, int fake) {
        this.api_key = api_key;
        this.messages = messages;
        this.report_url = report_url;
        this.contact = contact;
        this.fake = fake;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getReport_url() {
        return report_url;
    }

    public void setReport_url(String report_url) {
        this.report_url = report_url;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getFake() {
        return fake;
    }

    public void setFake(int fake) {
        this.fake = fake;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sms sms = (Sms) o;

        if (fake != sms.fake) return false;
        if (api_key != null ? !api_key.equals(sms.api_key) : sms.api_key != null) return false;
        if (messages != null ? !messages.equals(sms.messages) : sms.messages != null) return false;
        if (report_url != null ? !report_url.equals(sms.report_url) : sms.report_url != null)
            return false;
        return contact != null ? contact.equals(sms.contact) : sms.contact == null;
    }

    @Override
    public int hashCode() {
        int result = api_key != null ? api_key.hashCode() : 0;
        result = 31 * result + (messages != null ? messages.hashCode() : 0);
        result = 31 * result + (report_url != null ? report_url.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + fake;
        return result;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "api_key='" + api_key + '\'' +
                ", messages=" + messages +
                ", report_url='" + report_url + '\'' +
                ", contact='" + contact + '\'' +
                ", fake=" + fake +
                '}';
    }
}
