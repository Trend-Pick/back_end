package fashion.look_book.controller;

import fashion.look_book.Dto.Board.PostDtoTitle;
import fashion.look_book.Dto.Vote.GetVoteDto;
import fashion.look_book.Dto.Vote.MonthlyRankingDto;
import fashion.look_book.Dto.Vote.RankingPictureDto;
import fashion.look_book.Dto.Vote.WeeklyRankingDto;
import fashion.look_book.domain.*;
import fashion.look_book.login.SessionConst;
import fashion.look_book.service.LikeService;
import fashion.look_book.service.PictureService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VoteController {

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
        List<Long> RankingId = new ArrayList<>();
        List<Long> RankingNumber = new ArrayList<>();
        List<RankingPictureDto> PictureList = new ArrayList<>();
        RankingPictureDto rankingPictureDto;

        for(int i = 0; i < 3; i++) {
            RankingId.add(pictures.get(i));
            RankingNumber.add(likeService.LikeNumber(pictures.get(i)));
            rankingPictureDto = new RankingPictureDto(RankingId.get(i), RankingNumber.get(i));
            PictureList.add(rankingPictureDto);
        }

        return PictureList;
    }

    // 주간 좋아요 순위
    @GetMapping("weekly_ranking")
    public List<WeeklyRankingDto> weeklyRanking() {
        List<Picture> pictures = likeService.RankingOfWeek();
        List<Long> weekRankingId = new ArrayList<>();
        List<Long> weekRankingNumber = new ArrayList<>();
        List<WeeklyRankingDto> weeklyList = new ArrayList<>();
        WeeklyRankingDto weeklyRankingDto;

        for(int i = 0; i < pictures.size(); i++) {
            weekRankingId.add(pictures.get(i).getId());
            weekRankingNumber.add(likeService.LikeNumber(weekRankingId.get(i)));
            weeklyRankingDto = new WeeklyRankingDto(weekRankingId.get(i), weekRankingNumber.get(i));
            weeklyList.add(weeklyRankingDto);
        }

        return weeklyList;
    }

    // 월간 좋아요 순위
    @GetMapping("monthly_ranking")
    public List<MonthlyRankingDto> monthlyRanking() {
        List<Picture> pictures = likeService.RankingOfMonth();
        List<Long> monthRankingId = new ArrayList<>();
        List<Long> monthRankingNumber = new ArrayList<>();
        List<MonthlyRankingDto> monthlyList = new ArrayList<>();
        MonthlyRankingDto monthlyRankingDto;

        for(int i = 0; i < pictures.size(); i++) {
            monthRankingId.add(pictures.get(i).getId());
            monthRankingNumber.add(likeService.LikeNumber(monthRankingId.get(i)));
            monthlyRankingDto = new MonthlyRankingDto(monthRankingId.get(i), monthRankingNumber.get(i));
            monthlyList.add(monthlyRankingDto);
        }

        return monthlyList;
    }
    // 오류 있음 (찾아오는건 그건데 영향을 받네?)
}
