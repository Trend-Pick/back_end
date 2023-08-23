package fashion.look_book.controller;

import fashion.look_book.Dto.Vote.GetVoteDto;
import fashion.look_book.Dto.Vote.MonthlyRankingDto;
import fashion.look_book.Dto.Vote.RankingPictureDto;
import fashion.look_book.Dto.Vote.WeeklyRankingDto;
import fashion.look_book.domain.*;
import fashion.look_book.login.SessionConst;
import fashion.look_book.service.LikeService;
import fashion.look_book.service.MemberService;
import fashion.look_book.service.PictureService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://3.35.99.247:3000", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class VoteController {

    private final PictureService pictureService;
    private final MemberService memberService;
    private final LikeService likeService;
    private final HttpSession session;

    @GetMapping("/vote")
    public GetVoteDto Vote() {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Picture picture = pictureService.PictureByRandom(member);
        String url = picture.getImgUrl();
        Long id = picture.getId();

        return new GetVoteDto(url, id);
    }

    // 받아와서 좋아요 누르면 좋아요 상태로 like만들고
    @PostMapping("/vote/like/{pictureId}")
    public String LikePicture(@PathVariable Long pictureId) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Picture picture = pictureService.findOne(pictureId);

        Like like = new Like(member, picture, LikeStatus.LIKE, LocalDateTime.now());
        likeService.saveLike(like);
        picture.getLikes().add(like);

        return "ok"; // 다시 /vote 로 리다이렉트
    }

    // 별로에요 누르면 dislike로 만들기
    @PostMapping("/vote/dislike/{pictureId}")
    public String disLikePicture(@PathVariable Long pictureId) {
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Picture picture = pictureService.findOne(pictureId);

        Like like = new Like(member, picture, LikeStatus.DISLIKE, LocalDateTime.now());
        likeService.saveLike(like);
        picture.getLikes().add(like);

        return "ok"; // 다시 /vote 로 리다이렉트
    }

    // 누적 좋아요 순위
    @GetMapping("pictures_ranking")
    public List<RankingPictureDto> PictureList() {
        List<Long> pictures = pictureService.RankingOfPicture();
        List<String> RankingUrl = new ArrayList<>();
        List<Long> RankingNumber = new ArrayList<>();
        List<RankingPictureDto> PictureList = new ArrayList<>();
        RankingPictureDto rankingPictureDto;

        for(int i = 0; i < 3; i++) {
            Picture picture = pictureService.findOne(pictures.get(i));
            Member member = picture.getPicture_member();
            RankingUrl.add(picture.getImgUrl());
            RankingNumber.add(likeService.LikeNumber(pictures.get(i)));
            if(member.getMemberImg() == null) {
                rankingPictureDto = new RankingPictureDto(member.getNickname(), null,
                        RankingUrl.get(i), RankingNumber.get(i));
            }
            else {
                rankingPictureDto = new RankingPictureDto(member.getNickname(), member.getMemberImg().getImgUrl(),
                        RankingUrl.get(i), RankingNumber.get(i));
            }
            PictureList.add(rankingPictureDto);
        }

        return PictureList;
    }

    // 주간 좋아요 순위
    @GetMapping("weekly_ranking")
    public List<WeeklyRankingDto> weeklyRanking() {
        List<Object[]> pictures = likeService.RankingOfWeek();
        List<WeeklyRankingDto> weeklyList = new ArrayList<>();

        for (Object[] result : pictures) {
            Picture picture = (Picture) result[0];
            Long likeDislikeDifference = (Long) result[1];

            Member member = picture.getPicture_member();

            String ImgUrl = picture.getImgUrl();
            WeeklyRankingDto weeklyRankingDto;
            if(member.getMemberImg() == null) {
                weeklyRankingDto = new WeeklyRankingDto(member.getNickname(),
                        null, ImgUrl, likeDislikeDifference);
            }
            else {
                weeklyRankingDto = new WeeklyRankingDto(member.getNickname(),
                        member.getMemberImg().getImgUrl(), ImgUrl, likeDislikeDifference);
            }
            weeklyList.add(weeklyRankingDto);
        }

        return weeklyList;
    }

    // 월간 좋아요 순위
    @GetMapping("monthly_ranking")
    public List<MonthlyRankingDto> monthlyRanking() {
        List<Object[]> pictures = likeService.RankingOfMonth();
        List<MonthlyRankingDto> monthlyList = new ArrayList<>();

        for (Object[] result : pictures) {
            Picture picture = (Picture) result[0];
            Long likeDislikeDifference = (Long) result[1];

            Member member = picture.getPicture_member();

            String imgUrl = picture.getImgUrl();
            MonthlyRankingDto monthlyRankingDto;
            if(member.getMemberImg() == null) {
                monthlyRankingDto = new MonthlyRankingDto(member.getNickname(),
                        null, imgUrl, likeDislikeDifference);
            }
            else {
                monthlyRankingDto = new MonthlyRankingDto(member.getNickname(),
                        member.getMemberImg().getImgUrl(), imgUrl, likeDislikeDifference);
            }
            monthlyList.add(monthlyRankingDto);
        }

        return monthlyList;
    }
}
