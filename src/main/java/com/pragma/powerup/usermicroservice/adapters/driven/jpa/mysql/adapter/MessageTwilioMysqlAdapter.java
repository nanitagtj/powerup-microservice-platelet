package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.feignclient.IMessageClient;
import com.pragma.powerup.usermicroservice.domain.clientapi.IMessageClientPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageTwilioMysqlAdapter implements IMessageClientPort {

    private final IMessageClient messageClient;
    @Override
    public void sendPinMessage(String messengerSms) {
        messageClient.sendPinSms(messengerSms);
    }
}
