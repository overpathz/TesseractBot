package com.geekglasses.tesseract.service;

import com.geekglasses.tesseract.entity.UserTag;
import com.geekglasses.tesseract.repo.UserTagRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTagService {

    private final UserTagRepo userTagRepo;

    public UserTagService(UserTagRepo userTagRepo) {
        this.userTagRepo = userTagRepo;
    }

    public void save(UserTag user) {
        userTagRepo.save(user);
    }

    public UserTag getById(Long id) {
        return userTagRepo.getById(id);
    }

    public List<UserTag> getAll() {
        return userTagRepo.findAll();
    }

    public void delete(UserTag user) {
        userTagRepo.delete(user);
    }

    public List<UserTag> getAllByChatId(Long chatId) {
        return userTagRepo.findAllByChatId(chatId);
    }

    public boolean isLessThanFive(Long chatId) {
        return getAllByChatId(chatId).size() < 5;
    }
}
