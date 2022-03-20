package io.my.messagesystem.service;

import io.my.messagesystem.model.Message;
import io.my.messagesystem.repository.MessageRepository;
import io.my.messagesystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message getMessageById(Long id) {
        return messageRepository.findById(id).get();
    }

    public void updateMessage(Message message) {
        messageRepository.save(message);
    }

    public void deleteMessageById(Long id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getAllInfo() {
        return messageRepository.findAll();
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
