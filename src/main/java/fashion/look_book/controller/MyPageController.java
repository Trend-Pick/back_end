package fashion.look_book.controller;

import fashion.look_book.Dto.Board.CommentDtoContent;
import fashion.look_book.Dto.LoginDtos.ChangePWDto;
import fashion.look_book.Dto.MyPage.*;
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
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://3.35.99.247:3000", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final HttpSession session;
    private final MemberImgService memberImgService;
    private final PostService postService;
    private final PictureService pictureService;
    private final MemberService memberService;

    @GetMapping("/my_page") // 처음 페이지이고, 사진 최신 6개 보여주기
    public MyPagePictureDto MyPagePictures() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<Picture> pictures = pictureService.MyPagePicture(member.getId());

        List<MyPictureDto> pictureList = pictures.stream()
                .map(p -> {
                    String imgUrl = Optional.ofNullable(p.getPicture_member().getMemberImg())
                            .map(MemberImg::getImgUrl)
                            .orElse(null);
                    try {
                        return new MyPictureDto(p.getImgUrl());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());

        return new MyPagePictureDto(member, pictureList);
    }



    @GetMapping("/my_page/post") // 최신 Post 6개 보여주기
    public MyPagePostDto MyPagePosts() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<Post> posts = postService.MyPagePost(member.getId());

        List<MyPostDto> postList = posts.stream()
                .map(p -> {
                    String imgUrl = Optional.ofNullable(p.getPost_member().getMemberImg())
                            .map(MemberImg::getImgUrl)
                            .orElse(null);
                    try {
                        return new MyPostDto(p.getContent(), p.getPostImg().getImgName(), p.getPostTime());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());

        return new MyPagePostDto(member, postList);
        // content 내용 길면 자르기
    }

    @GetMapping("/update/member/picture")
    public MemberPictureDto UpdatePicturePage() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        String imgUrl = member.getMemberImg().getImgUrl();

        if (member.getMemberImg() == null) {
            return new MemberPictureDto(null);
        }

        return new MemberPictureDto(imgUrl);
    }

    @PutMapping("/update/member/picture") // 대표사진 수정 누르기
    public void UpdatePicture(@RequestParam MultipartFile newImg) throws Exception {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long memberId = member.getId();

        MemberImg memberImg = memberImgService.findByMemberId(memberId);
        if(memberImg != null) {
            Long memberImgId = memberImg.getId();
            memberImgService.updateImg(memberImgId, newImg);
        }
        else {
            memberImgService.saveImg(member, newImg);
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
