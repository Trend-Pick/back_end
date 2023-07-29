package fashion.look_book.controller;
import fashion.look_book.Dto.PIcture.CreatePictureDto;
import fashion.look_book.domain.*;
import fashion.look_book.login.SessionConst;
import fashion.look_book.service.FileService;
import fashion.look_book.service.PictureService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
public class PictureController {
    private final PictureService pictureService;
    private final FileService fileService;
    private final HttpSession session;

    @Value("${itemImgLocation}") // .properties 의 itemImgLocation 값을 itemImgLocation 변수에 넣어
    private String imgLocation;

    @PostMapping("/create_picture")
    public CreatePictureDto savePicture(@RequestParam("cody_img") MultipartFile codyImg)
            throws Exception{
        // PictureCreateDto pictureDto = new PictureCreateDto.builder()
        // 여기서 날라왔을 때

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        String oriImgName = codyImg.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        imgName = fileService.uploadFiles(imgLocation, oriImgName, codyImg.getBytes());

        imgUrl = imgLocation+"/"+ imgName;

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