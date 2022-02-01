package com.geekglasses.tesseract.util;

import com.geekglasses.tesseract.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class TestTesseractRestService {

    private static final String HABR_ARTICLES = "http://localhost:8080/articles";
    private static int cachedPostId = 648648;

    public Article getArticle() {
        List<String> tags = new ArrayList<>(List.of("c++", "java"));
        return new Article("", "https://habr.com/ru/company/luxoft/news/t/585650/", -1, tags);
    }


    public boolean isNewPost() {
        int lastArticleId = getLastArticleId();
        if (lastArticleId != cachedPostId) {
            cachedPostId = lastArticleId;
            return true;
        }
        cachedPostId = lastArticleId;
        return false;
    }


    private int getLastArticleId() {
        Document page = null;
        try {
            page = Jsoup.connect(HABR_ARTICLES).get();
        } catch (IOException e) {
            log.error("Can not get Document by get() method", e);
        }
        Objects.requireNonNull(page, "Parsed Document object should be not null");
        Elements articles = page.getElementsByClass("tm-articles-list__item");
        Element article = articles.get(0);
        return Integer.parseInt(article.attributes().get("id"));
    }
}
