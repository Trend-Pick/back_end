package fashion.look_book.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    private final AmazonS3 amazonS3;


    public Map<String, String> upload (MultipartFile multipartFile) throws IOException {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        // filename을 랜덤으로 생성

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        // file 사이즈 알려주는거

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
        // S3의 메서드인데 파일 stream을 열어서 S3에 파일을 업로드 하는 기능

        String s3Url = amazonS3.getUrl(bucket, s3FileName).toString();

        Map<String, String> result = new HashMap<>();

        result.put("s3FileName", s3FileName);
        result.put("s3Url", s3Url);

        return result;
    }

    public void deleteImage(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
