package com.beeecommerce.service.impl;

import com.beeecommerce.entity.StaticPage;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.mapper.StaticPageMapper;
import com.beeecommerce.model.dto.StaticPageDto;
import com.beeecommerce.repository.StaticPageRepository;
import com.beeecommerce.service.StaticPageService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaticPageServiceImpl implements StaticPageService {
    private final StaticPageRepository staticPageRepository;
    private final StaticPageMapper staticPageMapper;

    @Override
    public void create(StaticPageDto staticPageDto) {
        StaticPage staticPage = new StaticPage();

        ObjectHelper.setStaticPage(staticPageDto::getContent, staticPage::setContent);
        ObjectHelper.setStaticPage(staticPageDto::getPath, staticPage::setPath);
        ObjectHelper.setStaticPage(staticPageDto::getTitle, staticPage::setTitle);

        staticPageRepository.save(staticPage);
    }


    @Override
    public Page<StaticPageDto> getAll(Pageable pageable) {
        return staticPageRepository.findAll(pageable).map(staticPageMapper::noneContent);
    }

    @Override
    public StaticPageDto getById(Long id) {
        return staticPageRepository.findById(id)
                .map(staticPageMapper).orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        staticPageRepository.deleteById(id);
    }

    @Override
    public void update(StaticPageDto staticPageDto) throws AuthenticateException {
        Optional<StaticPage> optionalStaticPage = staticPageRepository.findById(staticPageDto.getId());

        optionalStaticPage.ifPresent(staticPage -> {
            ObjectHelper.setStaticPage(staticPageDto::getContent, staticPage::setContent);
            ObjectHelper.setStaticPage(staticPageDto::getTitle, staticPage::setTitle);
            ObjectHelper.setStaticPage(staticPageDto::getPath, staticPage::setPath);

            staticPageRepository.save(staticPage);

        });

    }

    @Override
    public StaticPageDto getByPath(String path) {
        return staticPageRepository
                .findByPath(path)
                .map(staticPageMapper)
                .orElseThrow(
                        () -> new EntityNotFoundException("Không tìm thấy trang có path [%s]".formatted(path))
                );
    }

}
