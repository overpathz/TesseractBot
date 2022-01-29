package com.geekglasses.tesseract.repo;

import com.geekglasses.tesseract.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
