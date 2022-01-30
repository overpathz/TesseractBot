package com.geekglasses.tesseract.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tag_link")
public class TagLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "tag")
    private String tag;
}
