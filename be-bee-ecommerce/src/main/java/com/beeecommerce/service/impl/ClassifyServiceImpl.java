package com.beeecommerce.service.impl;

import com.beeecommerce.entity.Classify;
import com.beeecommerce.entity.ClassifyGroup;
import com.beeecommerce.entity.ClassifyName;
import com.beeecommerce.entity.Product;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.mapper.ClassifyMapper;
import com.beeecommerce.model.dto.ClassifyDto;
import com.beeecommerce.model.dto.ClassifyNameDto;
import com.beeecommerce.model.response.ClassifyResponse;
import com.beeecommerce.repository.ClassifyNameRepository;
import com.beeecommerce.repository.ClassifyRepository;
import com.beeecommerce.repository.ClasstifyGroupRepository;
import com.beeecommerce.service.AmazonClientService;
import com.beeecommerce.service.ClassifyService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClassifyServiceImpl implements ClassifyService {


    private final ClasstifyGroupRepository classtifyGroupRepository;

    private final AmazonClientService amazonClientService;

    private final ClassifyMapper classifyMapper;

    private final ClassifyNameRepository classifyNameRepository;

    private final ClassifyRepository classifyRepository;


    @Override
    public void saveOrUpdate(List<ClassifyDto> classifies, ClassifyNameDto groupNameDto, ClassifyNameDto classifyNameDto, Product product) {

//        if(CheckNullUtil)

        Map<String, List<ClassifyDto>> groupedClassifies = classifies.stream()
                .collect(Collectors.groupingBy(ClassifyDto::getClassifyGroupValue));

        ClassifyName groupName = classifyNameRepository
                .findByName(groupNameDto.getName())
                .orElseGet(() -> classifyNameRepository.save(convertClassifyName(groupNameDto)));

        ClassifyName classifyName = classifyNameRepository
                .findByName(classifyNameDto.getName())
                .orElseGet(() -> classifyNameRepository.save(convertClassifyName(classifyNameDto)));

        for (Map.Entry<String, List<ClassifyDto>> entry : groupedClassifies.entrySet()) {

            ClassifyGroup classifyGroup = ClassifyGroup
                    .builder()
                    .id(entry.getValue().get(0).getClassifyGroupId())
//                    .name(entry.getKey())
                    .product(product)
                    .build();

            classifyGroup.setClassifyName(groupName);
            classifyGroup.setValue(entry.getKey());


            ClassifyGroup dbGroup = classtifyGroupRepository.save(classifyGroup);

            for (ClassifyDto item : entry.getValue()
            ) {
                Classify classify = convertClassify(item, dbGroup);

                classify.setClassifyName(classifyName);

                classifyRepository.save(classify);

            }

        }
    }

    @Override
    public ClassifyResponse getClassify(Long id) {

        Classify classify = classifyRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Không tìm thấy classify!")
                );

        return classifyMapper.applyClassifyResponse(classify);
    }

    public Classify convertClassify(ClassifyDto classifyDto, ClassifyGroup classifyGroup) {
        return Classify.builder()
                .id(classifyDto.getClassifyId())
                .value(classifyDto.getClassifyValue())
                .sellPrice(classifyDto.getPrice())
                .inventory(classifyDto.getInventory())
                .quantity(classifyDto.getInventory())
                .group(classifyGroup)
                .build();
    }

    public ClassifyName convertClassifyName(@NonNull ClassifyNameDto classifyNameDto) {
        return ClassifyName.builder()
                .id(classifyNameDto.getId() == null ? null : classifyNameDto.getId())
                .name(classifyNameDto.getName())
                .build();
    }


    public void addToClassifyDefault(Product product) {
        ClassifyGroup group = ClassifyGroup.builder()
//                .name("default")
                .product(product)
                .build();

        ClassifyGroup dbGroup = classtifyGroupRepository.save(group);


        Classify classify = Classify.builder()
                .value("default")
//                .sellPrice(product.getPrice())
//                .quantity(product.getInventory())
                .group(dbGroup)
                .build();

        classifyRepository.save(classify);
    }


}
