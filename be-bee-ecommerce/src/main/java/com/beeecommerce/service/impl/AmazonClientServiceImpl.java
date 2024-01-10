package com.beeecommerce.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.beeecommerce.constance.TypeUpLoad;
import com.beeecommerce.exception.common.FileExtensionInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.repository.ProductImageRepository;
import com.beeecommerce.service.AmazonClientService;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class AmazonClientServiceImpl implements AmazonClientService {
    private AmazonS3 s3client;

    @Value("${app.amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${app.amazonProperties.bucketName}")
    private String bucketName;

    @Value("${app.amazonProperties.accessKey}")
    private String accessKey;

    @Value("${app.amazonProperties.secretKey}")
    private String secretKey;

    private final ProductImageRepository imageRepository;

    @PostConstruct
    public void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }


    @Override
    public File convertFile(MultipartFile multipartFile)
            throws IOException {
        String filename = multipartFile.getOriginalFilename();
        File file = new File(filename);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }


    @Override
    public String generateFileName(MultipartFile multipartFile) {
        return
                "bee_e_commerce " + UUID.randomUUID();
    }


    public void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }


    @Override
    public void deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(bucketName, fileName);
    }


    public String uploadFile(MultipartFile multipartFile, String fileName) {
        try {
            File file = convertFile(multipartFile);
            String fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            boolean isDelete = file.delete();


//            if(!isDelete){
//                throw ApplicationException("Xóa file không thành công!")
//            }


            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public String uploadFile(MultipartFile multipartFile, TypeUpLoad typeUpLoad)
            throws ApplicationException {

        if (typeUpLoad.equals(TypeUpLoad.IMAGE)) {
            imageCheck(multipartFile);
        } else {
            videoCheck(multipartFile);
        }

        return uploadFile(multipartFile, generateFileName(multipartFile));
    }


    public void imageCheck(MultipartFile file) throws FileExtensionInvalidException {
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp"};

        checkFile(imageExtensions, file);
    }

    public void videoCheck(MultipartFile file) throws FileExtensionInvalidException {
        String[] videoExtensions = {"mp4", "avi", "mkv", "mov", "wmv"};
        checkFile(videoExtensions, file);
    }

    public void checkFile(String[] extensions, MultipartFile file)
            throws FileExtensionInvalidException {

        String filename = file.getOriginalFilename().toLowerCase();

        String extension = filename.substring(filename.lastIndexOf(".") + 1);

        Optional<String> value = Arrays
                .stream(extensions)
                .filter(
                        item -> item
                                .equalsIgnoreCase(extension)
                )
                .findFirst();

        if (value.isEmpty()) {
            throw new FileExtensionInvalidException("Vui lòng gửi hình ành!");
        }
    }

    // xóa url trên aws nếu db không có url đấy
    @Scheduled(cron = "0 0 0 1,15 * ?") // 2 tuần
    public void checkAndDeleteObsoleteS3Objects() {
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request()
                .withBucketName(bucketName);

        ListObjectsV2Result objectListing = s3client.listObjectsV2(listObjectsV2Request);

        List<Object> objectList = imageRepository.findAllImageUrls();

        List<String> imageUrls = objectListing
                .getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .filter(
                        item -> !objectList.contains(item)
                ).toList();

        deleteObsoleteS3Objects(imageUrls);
    }

    // Xóa objects trên aws
    public void deleteObsoleteS3Objects(List<String> imageUrls) {
        s3client.deleteObjects(
                new DeleteObjectsRequest(bucketName)
                        .withKeys(imageUrls.toArray(new String[0]))
        );


    }


}
