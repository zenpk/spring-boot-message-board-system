package io.my.messagesystem.service;

import io.my.messagesystem.model.Message;

import java.util.List;

public interface MessageService {
    Message getMessageById(Long id);

    void updateMessage(Message message);

    void deleteMessageById(Long id);

    List<Message> getAllInfo();

    void saveMessage(Message message);
}
