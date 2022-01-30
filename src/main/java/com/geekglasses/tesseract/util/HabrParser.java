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
public class HabrParser {
    private static final String HABR = "https://habr.com";
    private static final String HABR_ARTICLES = HABR + "/ru/all";

    private static int cachedPostId = -20;

    @SneakyThrows
    public Article getArticle() {
        int articleId = getLastArticleId();
        String specificParsePost = HABR + "/ru/post/" + articleId;
        Document postPage = Jsoup.connect(specificParsePost).get();
        Element rootTags = postPage.getElementsByClass("tm-separated-list__list").get(0);
        Elements tagElements = rootTags.getElementsByTag("li");
        List<String> tags = new ArrayList<>();

        for(Element tag: tagElements) {
            tags.add(tag.text());
        }

        return new Article(postPage.title(), specificParsePost, articleId, tags);
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
