package com.geekglasses.tesseract.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;
}
