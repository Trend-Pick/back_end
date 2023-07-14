package fashion.look_book.controller;

import fashion.look_book.Dto.Vote.GetVoteDto;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.Picture;
import fashion.look_book.login.SessionConst;
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
    private final HttpSession session;

    @GetMapping("/vote")
    public GetVoteDto Vote() {

        List<Member> members = memberService.MemberByRandom();

        Member findMember1 = members.get(0);
        Member findMember2 = members.get(1);

        Picture picture1 = pictureService.PictureByRandom(findMember1);
        Picture picture2 = pictureService.PictureByRandom(findMember2);

        Long id1 = picture1.getId();
        Long id2 = picture2.getId();

        return new GetVoteDto(id1, id2);
    }


    @PostMapping("/vote/{pictureId}")
    public void VotePicture(@PathVariable Long pictureId) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Picture picture = pictureService.findOne(pictureId);


    }
}
