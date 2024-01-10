package com.beeecommerce.controller;

import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.mapper.CategoryMapper;
import com.beeecommerce.model.dto.ClassifyDto;
import com.beeecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {


    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @GetMapping("exception")
    public String exception() throws ApplicationException {
        throw new ApplicationException("Error");
    }

    @PostMapping("hello")
    public Map<String, String> helloPost() {
        return Map.of("message", "Hello World from POST");
    }


    @PostMapping("classify")
    public String demo(@RequestBody List<ClassifyDto> classifyDtos) {

        return "snfgsdfsydf";
    }

    @GetMapping("body")
    public Map<String, Object> body(@RequestBody Map<String, Object> body) {
        return body;
    }
}
