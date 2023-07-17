package fashion.look_book.controller;

import fashion.look_book.Dto.MyPage.MemberPictureDto;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.MemberImg;
import fashion.look_book.login.SessionConst;
import fashion.look_book.service.MemberImgService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final HttpSession session;
    private final MemberImgService memberImgService;

    @GetMapping("/my_page") // 처음 페이지이고, 사진 최신 6개 보여주기
    public void MyPagePictures() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        /* member.getPictureList();
        member.getPostList();
        /////////////// 여기부분 쿼리로 받아오기
         */
    }

    @GetMapping("/my_page/post") // 최신 Post 6개 보여주기
    public void MyPagePosts() {

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
            else{ //원본에도 이미지가 있었는데 수정 후 추가된 경우
                MemberImg memberImg = memberImgService.findByMemberId(memberId);
                Long memberImgId = memberImg.getId();
                memberImgService.updateImg(memberImgId, newImg);
            }
        } else{ //원본에는 이미지가 있었는데 수정했을 때는 이미지가 없다면 원래 있던 postImg 삭제 필요
        if(memberImgService.findByMemberId(memberId)!=null)
            memberImgService.deleteImg(memberId);
        }
    }
}
