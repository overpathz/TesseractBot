package com.geekglasses.tesseract.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Article {
    private String title;
    private String url;
    private int identifier;
    private List<String> tags;
}
