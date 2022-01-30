package com.geekglasses.tesseract.util;

import com.geekglasses.tesseract.model.Article;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestTesseractRestService {
    private static final String HABR_ARTICLES = "http://localhost:8080/articles";

    private static int cachedPostId = -20;

    @SneakyThrows
    public Article getArticle() {
        List<String> tags = new ArrayList<>(List.of("java"));
        return new Article("", "https://habr.com/ru/company/luxoft/news/t/585650/", -1, tags);
    }

    @SneakyThrows
    public boolean isNewPost() {
        int lastArticleId = getLastArticleId();
        if (lastArticleId != cachedPostId) {
            cachedPostId = lastArticleId;
            return true;
        }
        cachedPostId = lastArticleId;
        return false;
    }

    @SneakyThrows
    private int getLastArticleId() {
        Document page = Jsoup.connect(HABR_ARTICLES).get();
        Elements articles = page.getElementsByClass("tm-articles-list__item");
        Element article = articles.get(0);
        return Integer.parseInt(article.attributes().get("id"));
    }
}
