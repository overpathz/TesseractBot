package com.geekglasses.tesseract.service;

import com.geekglasses.tesseract.entity.User;
import com.geekglasses.tesseract.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void save(User user) {this.userRepo.save(user);}

    public User getByChatId(Long chatId) {
        return userRepo.findByChatId(chatId);
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public void delete(User user) {
        userRepo.delete(user);
    }
}
