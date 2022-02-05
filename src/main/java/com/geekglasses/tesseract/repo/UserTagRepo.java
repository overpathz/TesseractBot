package com.geekglasses.tesseract.repo;

import com.geekglasses.tesseract.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTagRepo extends JpaRepository<UserTag, Long> {
    List<UserTag> findAllByChatId(Long chatId);
    UserTag findByChatIdAndTag(Long chatId, String tag);
    List<UserTag> findAllByChatIdAndTag(Long chatId, String tag);
}
