package fashion.look_book.controller;
import fashion.look_book.Dto.PIcture.CreatePictureDto;
import fashion.look_book.domain.*;
import fashion.look_book.service.FileService;
import fashion.look_book.service.MemberService;
import fashion.look_book.service.PictureService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PictureController {
    private final PictureService pictureService;
    private final MemberService memberService;
    private final FileService fileService;

    @Value("${itemImgLocation}") // .properties 의 itemImgLocation 값을 itemImgLocation 변수에 넣어
    private String imgLocation;

    //memberId,multipartfile을 같이 보낼 것.

    @PostMapping("/create/picture") // 글쓰기 페이지에서 저장을 누르는거

    public CreatePictureDto savePicture(@RequestParam("member_id") Long memberId,
                                        @RequestParam("cody_img") MultipartFile codyImg)
            throws Exception{
        //    PictureCreateDto pictureDto = new PictureCreateDto.builder()
        // 여기서 날라왔을 때
        Member picture_member = memberService.findOne(memberId);
        Long picture_memberId = picture_member.getId();

        String oriImgName = codyImg.getOriginalFilename();
        String imgName="";
        String imgUrl = "";

        imgName = fileService.uploadFiles(imgLocation,oriImgName,codyImg.getBytes());

        imgUrl = imgLocation+"/"+ imgName;


        Picture picture = Picture.builder()
                .picture_member(picture_member)
                .imgName(imgName).oriImgName(oriImgName).imgUrl(imgUrl).build();

        pictureService.save(picture);
        return new CreatePictureDto(picture);
        //  codyImgService.save(picture_member,codyImg);

    }

    @DeleteMapping("/delete/picture") // picture_id와 member_id 보내줘야함. 그걸 받아서? picture_id만 보내줘도 되긴함. 근데 또 조회할 것도 필요하겠다. 해당 유저가 올린 사진들만 모아서 보여주는식
    //해당 유저가 업로드한 사진만 보내줄 때 또 보내줘야하는게 picture_id다 . 그래야 다시 우리쪽으로 보낼 때 photo_id를 포함해서 보낼 수 있으니까.
    public ResponseEntity deletePicture(@RequestParam(value= "member_id") Long memberId,
                                        @RequestParam(value= "picture_id") Long pictureId)
            throws Exception{
        //    PictureCreateDto pictureDto = new PictureCreateDto.builder()
        // 여기서 날라왔을 때
        Picture picture_member = pictureService.findOne(pictureId);
        Long picture_memberId = picture_member.getId();

        pictureService.delete(picture_member);
        return ResponseEntity.ok().build();
        //  codyImgService.save(picture_member,codyImg);

    }

    @Data
    public class CodyImgCreateDto{

    }


}