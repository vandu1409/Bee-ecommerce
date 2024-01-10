package com.beeecommerce.service;

import com.beeecommerce.entity.StaticPage;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.model.dto.CartDto;
import com.beeecommerce.model.dto.StaticPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface StaticPageService {
    void create(StaticPageDto staticPageDto) throws AuthenticateException;

    Page<StaticPageDto> getAll(Pageable pageable);

    StaticPageDto getById(Long id);

    void deleteById(Long id);

    void update(StaticPageDto staticPageDto) throws AuthenticateException;

    StaticPageDto getByPath(String path);
}
