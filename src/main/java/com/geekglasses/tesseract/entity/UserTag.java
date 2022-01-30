package com.geekglasses.tesseract.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_tag")
public class UserTag {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "tag")
    private String tag;
}
