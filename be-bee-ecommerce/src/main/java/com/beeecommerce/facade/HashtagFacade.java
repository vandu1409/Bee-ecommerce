package com.beeecommerce.facade;

import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.HashtagDto;
import com.beeecommerce.service.HashtagService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagFacade {
    private final HashtagService hashtagService;

    public Page<HashtagDto> getAll(Pageable pageable) {
        return hashtagService.getAllHashtags(pageable);
    }

    public Page<HashtagDto> searchHashtag(Pageable pageable, String key) {

        return hashtagService.searchHashtags(pageable, key);

    }

    public void create(HashtagDto hashtagDTO) throws ParamInvalidException {
        ObjectHelper.checkNullParam(hashtagDTO.getName());


        hashtagService.createHashtag(hashtagDTO);
    }

    public void deleteHashtagById(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        hashtagService.deleteHashtag(id);
    }

    public HashtagDto findById(Long id) throws ParamInvalidException, EntityNotFoundException {
        ObjectHelper.checkNullParam(id);
        return hashtagService.getHashtagById(id);
    }
}
