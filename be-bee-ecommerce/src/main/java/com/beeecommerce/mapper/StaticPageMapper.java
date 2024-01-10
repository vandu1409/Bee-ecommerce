package com.beeecommerce.mapper;

import com.beeecommerce.entity.StaticPage;
import com.beeecommerce.model.dto.StaticPageDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StaticPageMapper implements Function<StaticPage, StaticPageDto> {

    @Override
    public StaticPageDto apply(StaticPage staticPage) {
        return StaticPageDto.builder()
                .id(staticPage.getId())
                .content(staticPage.getContent())
                .title(staticPage.getTitle())
                .path(staticPage.getPath())
                .build();
    }

    public StaticPageDto noneContent(StaticPage staticPage) {
        return StaticPageDto.builder()
                .id(staticPage.getId())
                .title(staticPage.getTitle())
                .path(staticPage.getPath())
                .build();
    }
}
