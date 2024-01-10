package com.beeecommerce.mapper;

import com.beeecommerce.model.core.Locality;
import com.beeecommerce.model.dto.LocalityDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LocalityMapper implements Function<Locality, LocalityDto> {
    @Override
    public LocalityDto apply(Locality locality) {
        return new LocalityDto(
                locality.getId(),
                locality.getName(),
                locality.getCode()
        );
    }
}
