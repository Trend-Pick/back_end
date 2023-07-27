package fashion.look_book.controller;

import fashion.look_book.Dto.LoginDtos.ChangePWDto;
import fashion.look_book.Dto.MyPage.MemberPictureDto;
import fashion.look_book.Dto.MyPage.MyPagePictureDto;
import fashion.look_book.Dto.MyPage.MyPagePostDto;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.MemberImg;
import fashion.look_book.domain.Picture;
import fashion.look_book.domain.Post;
import fashion.look_book.login.SessionConst;
import fashion.look_book.service.MemberImgService;
import fashion.look_book.service.MemberService;
import fashion.look_book.service.PictureService;
import fashion.look_book.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final HttpSession session;
    private final MemberImgService memberImgService;
    private final PostService postService;
    private final PictureService pictureService;
    private final MemberService memberService;

    @GetMapping("/my_page") // 처음 페이지이고, 사진 최신 6개 보여주기
    public List<MyPagePictureDto> MyPagePictures() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<Picture> pictures = pictureService.MyPagePicture(member.getId());

        List<MyPagePictureDto> pictureList = pictures.stream()
                .map(p -> new MyPagePictureDto(p.getId()))
                .collect(Collectors.toList());

        return pictureList;
    }



    @GetMapping("/my_page/post") // 최신 Post 6개 보여주기
    public List<MyPagePostDto> MyPagePosts() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<Post> posts = postService.MyPagePost(member.getId());

        List<MyPagePostDto> postList = posts.stream()
                .map(p -> new MyPagePostDto(p.getTitle(), p.getContent(), p.getPostTime()))
                .collect(Collectors.toList());

        return postList;
        // content 내용 길면 자르기

    }

    @GetMapping("/update/member/picture") // 대표사진 수정
    public MemberPictureDto UpdatePicturePage() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        MemberImg img = member.getMemberImg();
        Long imgId = img.getId();

        return new MemberPictureDto(imgId);
    }

    @PutMapping("/update/member/picture") // 대표사진 수정 누르기
    public void UpdatePicture(@RequestParam(required=false) MultipartFile newImg) throws Exception {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long memberId = member.getId();

        if(newImg!=null) { //원본에는 이미지가 없었는데 수정했을 때는 이미지가 추가된 경우
            if(memberImgService.findByMemberId(memberId)==null){
                memberImgService.saveImg(newImg, member);
            }
            else { //원본에도 이미지가 있었는데 수정 후 추가된 경우
                MemberImg memberImg = memberImgService.findByMemberId(memberId);
                Long memberImgId = memberImg.getId();
                memberImgService.updateImg(memberImgId, newImg);
            }
        } else { //원본에는 이미지가 있었는데 수정했을 때는 이미지가 없다면 원래 있던 postImg 삭제 필요
        if(memberImgService.findByMemberId(memberId)!=null)
            memberImgService.deleteImg(memberId);
        }
    }

    @PutMapping("/change_password")
    public Long ChangePassword(@Validated @RequestBody ChangePWDto changePWDto) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long memberId = member.getId();
        memberService.updateMember(memberId, changePWDto.getPassword());
        return member.getId();
    }
}
