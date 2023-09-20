package fashion.look_book.controller;

import fashion.look_book.Dto.Board.CommentDtoContent;
import fashion.look_book.Dto.LoginDtos.ChangePWDto;
import fashion.look_book.Dto.MyPage.*;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.MemberImg;
import fashion.look_book.domain.Picture;
import fashion.look_book.domain.Post;
import fashion.look_book.login.SessionConst;
import fashion.look_book.service.*;
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
    private final LikeService likeService;

    @GetMapping("/my_page")
    public MyPagePictureDto MyPagePictures() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<Picture> pictures = pictureService.MyPagePicture(member.getId());

        List<MyPictureDto> pictureList = pictures.stream().map((p) -> {
            String imgUrl = p != null ? p.getImgUrl() : null;
            if (imgUrl != null) {
                try {
                    return new MyPictureDto(imgUrl, p.getId(), likeService.LikeNumber(p.getId()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }).collect(Collectors.toList());
        String imgUrl;
        if (member.getMemberImg() == null) {
            imgUrl = null;
        } else {
            imgUrl = member.getMemberImg().getImgUrl();
        }

        return new MyPagePictureDto(imgUrl, member, pictureList);
    }



    @GetMapping("/my_page/post")
    public MyPagePostDto MyPagePosts() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<Post> posts = postService.MyPagePost(member.getId());

        List<MyPostDto> postList = posts.stream().map((p) -> {
            if (p != null) {
                try {
                    if (p.getPostImg() == null) {
                        return new MyPostDto(p.getTitle(), p.getContent(), null, p.getCreatedDate(), p.getId());
                    }
                    else {
                    return new MyPostDto(p.getTitle(), p.getContent(), p.getPostImg().getImgUrl(), p.getCreatedDate(), p.getId());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }).collect(Collectors.toList());

        String imgUrl;

        if (member.getMemberImg() == null) {
            imgUrl = null;
        } else {
            imgUrl = member.getMemberImg().getImgUrl();
        }

        return new MyPagePostDto(imgUrl, member, postList);
    }

    @GetMapping("/update/member/picture")
    public MemberPictureDto UpdatePicturePage() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long memberId = member.getId();

        MemberImg memberImg = memberImgService.findByMemberId(memberId);
        String imgUrl = memberImg.getImgUrl();

        return new MemberPictureDto(imgUrl);
    } // 이거 트랜잭션으로 해보기

    @PatchMapping("/update/member/picture") // 대표사진 수정 누르기
    @ResponseBody
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

    @PatchMapping("/change_password")
    public Long ChangePassword(@Validated @RequestBody ChangePWDto changePWDto) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Long memberId = member.getId();
        memberService.updateMember(memberId, changePWDto.getPassword());
        return member.getId();
    }
}
