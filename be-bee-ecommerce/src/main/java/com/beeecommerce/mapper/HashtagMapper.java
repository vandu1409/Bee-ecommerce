package com.beeecommerce.mapper;

import com.beeecommerce.entity.Hashtag;
import com.beeecommerce.model.dto.HashtagDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class HashtagMapper implements Function<Hashtag, HashtagDto> {

    @Override
    public HashtagDto apply(Hashtag hashtag) {
        return HashtagDto
                .builder()
                .id(hashtag.getId())
                .name(hashtag.getName())
                .build();
    }
}