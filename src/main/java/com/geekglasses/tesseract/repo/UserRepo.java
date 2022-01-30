package com.geekglasses.tesseract.repo;

import com.geekglasses.tesseract.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByChatId(Long chatId);
}
