package com.solution.controller;

import com.solution.model.User;
import com.solution.service.CSV;
import com.solution.service.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static com.solution.service.CSV.*;

@Controller
public class UserController {
    public static final String CSV_UPLOAD = "src/main/resources/files/upload.csv";

    @Autowired
    private Mail sender;

    @GetMapping("/user")
    public String userForm(Model model) {
        model.addAttribute("user", new User());
        return "enterForm";
    }

    @PostMapping("/user")
    public String userSubmit(@ModelAttribute User user) {
        CSV.writeUserToCSV(user);
        return "successfulSubmit";
    }

    @GetMapping("/searchUser")
    public String searchForm(Model model) {
        model.addAttribute("user", new User());
        return "searchUser";
    }

    @PostMapping("/searchUser")
    public String getUserData(User user, Model model) {
        List<User> users = parseCSVtoUsers(CSV.SCV_USERS);
        int index = findByFullName(users, user.getName(), user.getSurname());
        if (index >= 0) {
            model.addAttribute("user", users.get(index));
            return "userInfo";
        }
        return "userNotFound";
    }

    @GetMapping("/upload")
    public String uploadForm() {
        return "uploadFile";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            return "fileIsEmpty";
        }
        Path path = Paths.get(CSV_UPLOAD);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<User> users = parseCSVtoUsers(CSV_UPLOAD);

        writeUserToCSV(users.get(0));
        deleteFile(path);
        model.addAttribute("file", file);
        return "successfulUpload";
    }

    public void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/sendMessage")
    public String sendMessage() {
        List<User> users = parseCSVtoUsers(CSV.SCV_USERS);
        User user = users.get(users.size() - 1);
        if (!StringUtils.isEmpty(user.getEmail())) {
            sendMessage(user);
            return "successfulSending";
        } else {
            return "emailNotFound";
        }
    }

    public void sendMessage(User user) {
        sender.sendMessage(user.getEmail(), "Welcome message", String.format("Hi, %s :)", user.getName()));
    }
}
