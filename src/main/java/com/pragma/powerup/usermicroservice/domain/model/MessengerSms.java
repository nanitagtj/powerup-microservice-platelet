package com.pragma.powerup.usermicroservice.domain.model;

public class MessengerSms {

    private String phoneClient;
    private String phoneSender;
    private String pin;

    public MessengerSms() {
    }

    public MessengerSms(String phoneClient, String phoneSender, String pin) {
        this.phoneClient = phoneClient;
        this.phoneSender = phoneSender;
        this.pin = pin;
    }

    public String getPhoneClient() {
        return phoneClient;
    }

    public void setPhoneClient(String phoneClient) {
        this.phoneClient = phoneClient;
    }

    public String getPhoneSender() {
        return phoneSender;
    }

    public void setPhoneSender(String phoneSender) {
        this.phoneSender = phoneSender;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}