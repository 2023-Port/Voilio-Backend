package com.techeer.port.voilio.s3.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Manager {

  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  public String upload(MultipartFile multipartFile, String dirName) throws IOException {
    File convertFile = new File(multipartFile.getOriginalFilename());

    if (convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(multipartFile.getBytes());
      }
      multipartFile.transferTo(convertFile);
      String uploadUrl = upload(convertFile, dirName);
      return uploadUrl;
    }
    return null;
  }

  private String upload(File uploadFile, String dirName) {
    String fileName = dirName + "/" + uploadFile.getName();
    String uploadImageUrl = putS3(uploadFile, fileName);
    removeNewFile(uploadFile);
    return uploadImageUrl;
  }

  private String putS3(File uploadFile, String fileName) {
    amazonS3Client.putObject(
        new PutObjectRequest(bucket, fileName, uploadFile)
            .withCannedAcl(CannedAccessControlList.PublicRead));
    return amazonS3Client.getUrl(bucket, fileName).toString();
  }

  private void removeNewFile(File targetFile) {
    if (targetFile.delete()) {
      log.info("파일이 삭제되었습니다.");
    } else {
      log.info("파일이 삭제되지 못했습니다.");
    }
  }

  private Optional<File> convert(MultipartFile file) throws IOException {
    File convertFile = new File(file.getOriginalFilename());
    log.info("convertFile :", convertFile);
    if (convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      }
      return Optional.of(convertFile);
    }

    return Optional.empty();
  }
}
