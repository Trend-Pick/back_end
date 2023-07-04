package fashion.look_book.service;

import fashion.look_book.domain.LikeStatus;
import fashion.look_book.domain.Member;
import fashion.look_book.domain.Picture;
import fashion.look_book.domain.Like;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class LikeServiceTest {

    @Autowired EntityManager em;
    @Autowired LikeService likeService;
    @Autowired PictureService pictureService;

    @Test
    public void test_like_dislike() { // 좋아요 누르기, 취소하는 버튼 기능
        Member member = createMember("one", "1111", "first", 24, true);
        Picture picture = new Picture(member);
        em.persist(picture);
        Like like = new Like(member, picture, LikeStatus.DISLIKE);
        em.persist(like);

        System.out.println("========================================");
        System.out.println(like.getStatus()); // DISLIKE

        likeService.change_like(member.getId(), picture.getId(), like);

        System.out.println("========================================");
        System.out.println(like.getStatus()); // LIKE

        likeService.change_like(member.getId(), picture.getId(), like);

        System.out.println("========================================");
        System.out.println(like.getStatus()); // DISLIKE

        likeService.change_like(member.getId(), picture.getId(), like);

        System.out.println("========================================");
        System.out.println(like.getStatus()); // LIKE
    }

    @Test
    public void 좋아요_개수() {
        Member member1 = createMember("one", "1111", "first", 24, true);
        Picture picture = new Picture(member1);
        em.persist(picture);

        Like like1 = new Like(member1, picture, LikeStatus.LIKE);
        em.persist(like1);

        Member member2 = createMember("two", "2222", "second", 25, true);
        Like like2 = new Like(member2, picture, LikeStatus.LIKE);
        em.persist(like2);

        Member member3 = createMember("three", "3333", "third", 12, false);
        Like like3 = new Like(member3, picture, LikeStatus.DISLIKE);
        em.persist(like3);

        Member member4 = createMember("four", "4444", "fourth", 24, true);
        Like like4 = new Like(member4, picture, LikeStatus.LIKE);
        em.persist(like4);

        System.out.println("========================================");
        System.out.println(picture.getLikes());

        System.out.println("========================================");
        System.out.println(pictureService.like_number(picture));
    } // member 중복 걸리니까 다시 하기  // add로 넣어줘야 될듯?

    private Member createMember(String name, String password, String nickname, Integer age, boolean sex) {
        Member member = new Member(name, password, nickname, age, sex);
        em.persist(member);
        return member;
    }
}