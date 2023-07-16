package fashion.look_book.controller;

import fashion.look_book.Dto.Vote.GetVoteDto;
import fashion.look_book.domain.Like;
import fashion.look_book.domain.LikeStatus;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.Picture;
import fashion.look_book.login.SessionConst;
import fashion.look_book.service.LikeService;
import fashion.look_book.service.MemberService;
import fashion.look_book.service.PictureService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VoteController {

    private final MemberService memberService;
    private final PictureService pictureService;
    private final LikeService likeService;
    private final HttpSession session;

    @GetMapping("/vote")
    public GetVoteDto Vote() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Picture picture = pictureService.PictureByRandom(member);

        Long pictureId = picture.getId();

        return new GetVoteDto(pictureId);
    }


    // 받아와서 좋아요 누르면 좋아요 상태로 like만들고
    @PostMapping("/vote/like/{pictureId}")
    public String LikePicture(@PathVariable Long pictureId) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Picture picture = pictureService.findOne(pictureId);

        Like like = new Like(member, picture, LikeStatus.LIKE);
        likeService.saveLike(like);
        picture.getLikes().add(like);

        return "ok"; // 다시 /vote 로 리다이렉트
    }

    // 별로에요 누르면 dislike로 만들기
    @PostMapping("/vote/dislike/{pictureId}")
    public String disLikePicture(@PathVariable Long pictureId) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Picture picture = pictureService.findOne(pictureId);

        Like like = new Like(member, picture, LikeStatus.DISLIKE);
        likeService.saveLike(like);
        picture.getLikes().add(like);

        return "ok"; // 다시 /vote 로 리다이렉트
    }

    /*@GetMapping("/list_pictures")
    public int PictureList(@PathVariable Long pictureId) {
        pictureService.findOne(pictureId);

        int count = likeService.LikeNumber(pictureId);
        return count;
    }*/
}
