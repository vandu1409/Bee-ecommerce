package com.beeecommerce.controller;

import com.beeecommerce.constance.TypeUpLoad;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import com.beeecommerce.service.AmazonClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.beeecommerce.constance.ApiPaths.UploadPath.UPLOAD_LINK;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final AmazonClientService amazonClientService;

    @PostMapping(UPLOAD_LINK)
    public ResponseObject<String> uploadFile(
            @RequestPart(value = "file") MultipartFile file) throws ApplicationException {
        String path = amazonClientService.uploadFile(file, TypeUpLoad.IMAGE);

        return ResponseHandler.response(path);
    }


    @DeleteMapping(UPLOAD_LINK)
    public ResponseObject<String> deleteFile(
            @RequestBody String fileUrl) {
        amazonClientService.deleteFileFromS3Bucket(fileUrl);
        return ResponseHandler.response("Delete file successfully");
    }

}
