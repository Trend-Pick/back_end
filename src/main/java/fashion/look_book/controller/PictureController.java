package fashion.look_book.controller;
import com.amazonaws.services.s3.AmazonS3;
import fashion.look_book.Dto.PIcture.CreatePictureDto;
import fashion.look_book.domain.*;
import fashion.look_book.login.SessionConst;
import fashion.look_book.service.FileService;
import fashion.look_book.service.PictureService;
import fashion.look_book.service.S3FileService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@CrossOrigin(origins = "http://3.35.99.247:3000", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class PictureController {
    private final PictureService pictureService;
    private final FileService fileService;
    private final HttpSession session;
    private final AmazonS3 amazonS3;
    private final S3FileService s3FileService;

    @Value("${cloud.aws.s3.bucket}") // .properties 의 itemImgLocation 값을 itemImgLocation 변수에 넣어
    private String imgLocation;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping("/create_picture")
    public CreatePictureDto savePicture(@RequestPart("cody_img") MultipartFile codyImg)
            throws Exception{
        // PictureCreateDto pictureDto = new PictureCreateDto.builder()
        // 여기서 날라왔을 때

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        String oriImgName = codyImg.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        Map<String, String> result = s3FileService.upload(codyImg);
        String s3FileName = result.get("s3FileName");
        String s3Url = result.get("s3Url");
        imgName = s3FileName;
        imgUrl = s3Url;

        Picture picture = Picture.builder()
                .picture_member(member)
                .imgName(imgName).oriImgName(oriImgName).imgUrl(imgUrl).pictureTime(LocalDateTime.now()).build();

        pictureService.save(picture);
        return new CreatePictureDto(picture);
        //  codyImgService.save(picture_member,codyImg);

    }

    @DeleteMapping("/delete_picture")
    public ResponseEntity deletePicture(@RequestParam(value= "picture_id") Long pictureId) throws Exception{

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Picture picture = pictureService.findOne(pictureId);

        if(picture.getPicture_member().getId() == member.getId()) {
            pictureService.delete(picture);
        }
        else {
            return null;
        }

        return ResponseEntity.ok().build();
    }
}