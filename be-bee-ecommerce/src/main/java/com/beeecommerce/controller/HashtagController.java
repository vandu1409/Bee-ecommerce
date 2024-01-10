package com.beeecommerce.controller;

import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.facade.HashtagFacade;
import com.beeecommerce.model.dto.HashtagDto;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.HashtagPath.HASHTAG_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(HASHTAG_PREFIX)
public class HashtagController {

    private final HashtagFacade hashtagFacade;


    @GetMapping
    public ResponseObject<List<HashtagDto>> getHashtag(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HashtagDto> hashtagDTOS = hashtagFacade.getAll(pageable);
        return ResponseHandler.response(hashtagDTOS);
    }

    @GetMapping("/{id}")
    public ResponseObject<HashtagDto> findById(@PathVariable("id") Long id) throws ParamInvalidException, EntityNotFoundException {
        HashtagDto optionalVoucher = hashtagFacade.findById(id);
        return ResponseHandler.response(optionalVoucher);
    }

    @PostMapping
    public ResponseObject<String> create(@RequestBody HashtagDto hashtagDTO) throws ParamInvalidException {
        hashtagFacade.create(hashtagDTO);
        return ResponseHandler.response("Hashtag created successfully");
    }


    @DeleteMapping("/{id}")
    public ResponseObject<String> delete(@PathVariable("id") Long id) throws ParamInvalidException {
        hashtagFacade.deleteHashtagById(id);
        return ResponseHandler.response("Xóa Thành Công");
    }

    @GetMapping("/search")
    public ResponseObject<List<HashtagDto>> searchHasgtag(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @PathVariable("key") String key
    ) throws ParamInvalidException {
        Pageable pageable = PageRequest.of(page, size);
        Page<HashtagDto> hashtagDTOS = hashtagFacade.searchHashtag(pageable, key);

        return ResponseHandler.response(hashtagDTOS);
    }


}
