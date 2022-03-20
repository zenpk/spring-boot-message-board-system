package io.my.messagesystem;

import io.my.messagesystem.model.Message;
import io.my.messagesystem.model.Role;
import io.my.messagesystem.model.User;
import io.my.messagesystem.repository.MessageRepository;
import io.my.messagesystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class MessageSystemApplication implements CommandLineRunner {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(MessageSystemApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (messageRepository.count() == 0) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Message example = new Message("Example Title", timeStamp, "zenpk", "This is an example message");
            messageRepository.save(example);
        }
        if (userRepository.count() == 0) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User zenpk = new User("zenpk", passwordEncoder.encode("zenpk"), Arrays.asList(new Role("ROLE_ADMIN")));
            User guest = new User("guest", passwordEncoder.encode("guest"), Arrays.asList(new Role("ROLE_GUEST")));
            userRepository.save(zenpk);
            userRepository.save(guest);
        }
    }
}
