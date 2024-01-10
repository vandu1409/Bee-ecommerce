package com.beeecommerce.service;

import com.beeecommerce.model.dto.HashtagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HashtagService {
    Page<HashtagDto> getAllHashtags(Pageable pageable);

    HashtagDto getHashtagById(Long id);

    Page<HashtagDto> searchHashtags(Pageable pageable, String keyword);

    void createHashtag(HashtagDto hashtag);

    void deleteHashtag(Long id);
}