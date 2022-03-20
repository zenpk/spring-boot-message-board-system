package io.my.messagesystem.controller;

import io.my.messagesystem.model.Message;
import io.my.messagesystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Show all messages
    @GetMapping(value = {"/", "/index"})
    public String getAllMessages(Model model) {
        model.addAttribute("messages", messageService.getAllInfo());
        // show different page depending on logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        // Collection<Role> roles = ((UserDetails) principal).getRoles();
        // String role = roles.iterator().next().getName();
        // model.addAttribute("role", role);
        model.addAttribute("username", username);
        return "index";
    }

    // Create message
    @GetMapping("/index/create")
    public String createMessage(Model model) {
        // Create message object to hold message form data
        Message message = new Message();
        model.addAttribute("message", message);
        return "create-message";
    }

    @PostMapping("/index/create/post")
    public String saveMessage(@ModelAttribute("message") Message message, @RequestParam("action") String action) {
        if (message.getTitle().isEmpty()) return "redirect:/index";
        if (action.equals("cancel")) return "redirect:/index";
        // Add sent time
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        message.setCreateTime(timeStamp);
        // Add author
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        message.setAuthor(((UserDetails) principal).getUsername());
        messageService.saveMessage(message);
        return "redirect:/index";
    }

    // Read message
    @GetMapping("/index/read/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        model.addAttribute("message", messageService.getMessageById(id));
        return "read-message";
    }

    // Update message
    @GetMapping("/index/update/{id}")
    public String editMessage(@PathVariable Long id, Model model) {
        // check permission
        Message message = messageService.getMessageById(id);
        String author = message.getAuthor();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        // bad hardcode
        if (!author.equals(username) && !author.equals("guest") && !username.equals("zenpk")) {
            return "redirect:/index?permission";
        }
        model.addAttribute("message", message);
        return "update-message";
    }

    @PostMapping("/index/update/{id}")
    public String updateMessage(@PathVariable Long id, @ModelAttribute("message") Message message, @RequestParam("action") String action) {
        if (action.equals("cancel")) return "redirect:/index";
        Message existingMessage = messageService.getMessageById(id);
        if (message.getTitle().equals(existingMessage.getTitle()) && message.getContent().equals(existingMessage.getContent()))
            return "redirect:/index";
        existingMessage.setTitle(message.getTitle());
        existingMessage.setContent(message.getContent());
        // Add "last edit" feature
        String timeStamp1 = existingMessage.getCreateTime().substring(0, 19);
        String timeStamp2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        existingMessage.setCreateTime(timeStamp1 + " (last edit: " + timeStamp2 + ")");
        messageService.updateMessage(existingMessage);
        return "redirect:/index";
    }

    // Delete message
    @GetMapping("/index/delete/{id}")
    public String deleteMessage(@PathVariable Long id) {
        // check permission
        String author = messageService.getMessageById(id).getAuthor();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        // bad hardcode
        if (!author.equals(username) && !author.equals("guest") && !username.equals("zenpk")) {
            return "redirect:/index?permission";
        }
        messageService.deleteMessageById(id);
        return "redirect:/index";
    }
}
