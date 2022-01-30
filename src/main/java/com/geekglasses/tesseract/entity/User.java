package com.geekglasses.tesseract.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "chat_id")
    private Long chatId;
}
