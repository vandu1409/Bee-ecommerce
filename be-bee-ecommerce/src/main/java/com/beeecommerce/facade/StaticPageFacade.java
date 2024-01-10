package com.beeecommerce.facade;

import com.beeecommerce.entity.StaticPage;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.StaticPageDto;
import com.beeecommerce.service.StaticPageService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaticPageFacade {
    private final StaticPageService staticPageService;

    public Page<StaticPageDto> getAll(Pageable pageable) {
        return staticPageService.getAll(pageable);
    }

    public void create(StaticPageDto staticPageDto) throws ParamInvalidException {

        staticPageService.create(staticPageDto);
    }

    public void update(StaticPageDto staticPageDto) throws ParamInvalidException {
        ObjectHelper.checkNullParam(staticPageDto.getId());
        staticPageService.update(staticPageDto);
    }

    public StaticPageDto getById(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        return staticPageService.getById(id);
    }

    public void deleteById(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        staticPageService.deleteById(id);
    }

    public StaticPageDto getByPath(String path) {
        return staticPageService.getByPath(path);
    }
}
