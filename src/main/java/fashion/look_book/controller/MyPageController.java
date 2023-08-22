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

        List<MyPictureDto> pictureList = pictures.stream().map((p) -> {
            String imgUrl = p != null ? p.getImgUrl() : null;
            if (imgUrl != null) {
                try {
                    return new MyPictureDto(imgUrl);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }).collect(Collectors.toList());
        String imgUrl;
        if (member.getMemberImg() == null) {
            imgUrl = "https://trendpick-photo.s3.ap-northeast-2.amazonaws.com/7494fad9-a612-4f9f-9011-00595ef7ace9-1.jfif";
        } else {
            imgUrl = member.getMemberImg().getImgUrl();
        }

        return new MyPagePictureDto(imgUrl, member, pictureList);
    }



    @GetMapping("/my_page/post") // 최신 Post 6개 보여주기
    public MyPagePostDto MyPagePosts() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<Post> posts = postService.MyPagePost(member.getId());

        List<MyPostDto> postList = posts.stream().map((p) -> {
            if (p != null) {
                try {
                    return new MyPostDto(p.getTitle(), p.getContent(), p.getPostTime());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }).collect(Collectors.toList());

        String imgUrl;

        if (member.getMemberImg() == null) {
            imgUrl = "https://trendpick-photo.s3.ap-northeast-2.amazonaws.com/7494fad9-a612-4f9f-9011-00595ef7ace9-1.jfif";
        } else {
            imgUrl = member.getMemberImg().getImgUrl();
        }

        return new MyPagePostDto(imgUrl, member, postList);
    }

    @GetMapping("/update/member/picture")
    public MemberPictureDto UpdatePicturePage() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        String imgUrl;
        if (member.getMemberImg() == null) {
            imgUrl = "https://trendpick-photo.s3.ap-northeast-2.amazonaws.com/7494fad9-a612-4f9f-9011-00595ef7ace9-1.jfif";
        } else {
            imgUrl = member.getMemberImg().getImgUrl();
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
