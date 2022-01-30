package com.geekglasses.tesseract.repo;

import com.geekglasses.tesseract.entity.TagLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagLinkRepo extends JpaRepository<TagLink, Long> {
    List<TagLink> findAllByTag(String tag);
}
