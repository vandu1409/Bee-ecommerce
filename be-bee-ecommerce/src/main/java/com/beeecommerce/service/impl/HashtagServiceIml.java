package com.beeecommerce.service.impl;

import com.beeecommerce.entity.Hashtag;
import com.beeecommerce.mapper.HashtagMapper;
import com.beeecommerce.model.dto.HashtagDto;
import com.beeecommerce.repository.HashtagRepository;
import com.beeecommerce.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class HashtagServiceIml implements HashtagService {
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    @Override
    public Page<HashtagDto> getAllHashtags(Pageable pageable) {
        return hashtagRepository.findAll(pageable).map(hashtagMapper);
    }

    @Override
    public HashtagDto getHashtagById(Long id) {
        return hashtagRepository
                .findById(id)
                .map(hashtagMapper)
                .orElseThrow();
    }

    @Override
    public Page<HashtagDto> searchHashtags(Pageable pageable, String keyword) {
        return hashtagRepository.findHashtagsByName(pageable, keyword).map(hashtagMapper);
    }

    @Override
    public void createHashtag(HashtagDto hashtagDto) {

        Hashtag hashtag = new Hashtag();
        hashtag.setName(hashtagDto.getName());

        hashtagRepository.save(hashtag);

    }

    @Override
    public void deleteHashtag(Long id) {
        hashtagRepository.deleteById(id);
    }
}