package com.beeecommerce.service;

import com.beeecommerce.constance.TypeUpLoad;
import com.beeecommerce.exception.core.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public interface AmazonClientService {

    File convertFile(MultipartFile multipartFile) throws IOException;

    String generateFileName(MultipartFile multipartFile);

    void deleteFileFromS3Bucket(String fileUrl);

    String uploadFile(MultipartFile multipartFile, TypeUpLoad typeUpLoad) throws ApplicationException;
}
